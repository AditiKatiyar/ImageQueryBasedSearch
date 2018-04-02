package application;
	
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application {
	
	private FileChooser fileChooser;
	private File file = null;
	private HBox top, control1;
	private VBox mainLayout, linksLayout, controlLayout, imageContainer, control2, logoContainer;
	private ScrollPane mainScroll;
	private Scene scene;
	private Button uploadButton;
	private Button detectButton;
	private String fileName = "";
	private String filePath = "";
	private ImageView imageView, logo;
	private ProgressIndicator pi;
	private String[] tags;
	private Button[] tagButton;
	private Group linkAnimation;
	private Label label;
	
	private Animation animation;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		try {			
			initialize(stage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialize(Stage stage) throws URISyntaxException {
		// imageView for logo
		Image im = new Image(getClass().getResource("res/PhoenixFinal.png").toURI().toString());
		logo = new ImageView(im);
		logo.setPreserveRatio(true);
				
		
		// container for logo
		logoContainer = new VBox();
		logoContainer.getChildren().add(logo);
		logoContainer.setAlignment(Pos.CENTER);
		
		// imageView
		imageView = new ImageView();
//		imageView.setX(10);
//		imageView.setY(10);
		imageView.setFitHeight(400);
		imageView.setFitWidth(400);
		imageView.maxHeight(400);
		imageView.maxWidth(400);
		imageView.minHeight(400);
		imageView.minWidth(400);
		imageView.setPreserveRatio(true);
		
		// label
		label = new Label("Upload Image");
		
		// conatiner for imageview
		imageContainer = new VBox();
		imageContainer.getChildren().add(label);
		VBox.setMargin(imageView, new Insets(30, 30, 30, 30));

		// fileChooser
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
				);

		// upload button
		uploadButton = new Button("browse");
		uploadButton.setOnAction(e -> {
			control2.getChildren().clear();
			file = null;
			file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				imageContainer.getChildren().clear();
				imageContainer.getChildren().add(imageView);
				fileName = file.getName();
				filePath = file.getAbsolutePath();
				System.out.println(fileName + " " + filePath);
				imageView.setImage(new Image(new File(filePath).toURI().toString()));
			}
		});
		
		

		// progress indicator
		pi = new ProgressIndicator();
		pi.setProgress(0);

		// detect button
		detectButton = new Button("detect");
		detectButton.setOnAction(e -> {
			if (file == null) {
				System.out.println("upload a file");
			}
			else {
				control2.getChildren().clear();
				control2.getChildren().add(pi);
				linksLayout.getChildren().clear();
				MyService myService = new MyService(filePath);
				myService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent t) {
						pi.progressProperty().unbind();
						control2.getChildren().remove(pi);
						control2.getChildren().clear();
						imageView.setImage(new Image(new File("/home/aditi/DarkNet/darknet/predictions.png").toURI().toString()));
						tags = myService.tags;
						updateUI(tags);
					}
				});

				myService.setOnReady(new EventHandler<WorkerStateEvent>() {

					@Override
					public void handle(WorkerStateEvent arg0) {
						// TODO Auto-generated method stub
						control2.getChildren().add(pi);
					}
				});

				pi.progressProperty().bind(myService.progressProperty());


				myService.start();	
			}
		});

		mainLayout = new VBox();

		top = new HBox();
		top.setAlignment(Pos.CENTER);
		

		controlLayout = new VBox();
		HBox.setMargin(controlLayout, new Insets(0, 0, 0, 5));
		control1 = new HBox();		
		control1.getChildren().addAll(uploadButton, detectButton);
		HBox.setMargin(uploadButton, new Insets(0, 15, 0, 0));
		control2 = new VBox();
		controlLayout.getChildren().addAll(control1, control2);		
		VBox.setMargin(control1, new Insets(40, 40, 40, 40));
		VBox.setMargin(control2, new Insets(80, 40, 40, 40));
		
		linkAnimation = new Group();
		
		linksLayout = new VBox();
		linksLayout.setAlignment(Pos.CENTER_LEFT);
//		linksLayout.setPrefWidth(1000);
//		linksLayout.prefWidthProperty().bind(top.prefWidthProperty());
//		linksLayout.maxWidthProperty().bind(top.maxWidthProperty());
		
		// adding scrolling functionality to mainLayout and linksLayout
		mainScroll = new ScrollPane();
		mainScroll.setContent(mainLayout);
		mainScroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		mainScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

		top.getChildren().addAll(imageContainer, controlLayout);

		mainLayout.getChildren().addAll(logoContainer, top,linkAnimation, linksLayout);
		VBox.setMargin(top, new Insets(20, 0, 20, 0));
		VBox.setMargin(logoContainer, new Insets(20, 20, 0, 20));

		scene = new Scene(mainScroll, 1200, 700);

		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		// setting styles of every component
		mainLayout.getStyleClass().add("main-layout");
		mainLayout.prefWidthProperty().bind(mainScroll.widthProperty());
		mainLayout.prefHeightProperty().bind(mainScroll.heightProperty());
		imageContainer.getStyleClass().add("image-container");
		controlLayout.getStyleClass().add("control-layout");
		linksLayout.getStyleClass().add("links-layout");
		imageView.getStyleClass().add("image-view");
		control1.getStyleClass().add("control1");
		control2.getStyleClass().add("control2");
		uploadButton.getStyleClass().add("button1");
		detectButton.getStyleClass().add("button1");
		label.getStyleClass().add("label");
	}
	
	
	public void updateUI(String[] tags) {
		tagButton = new Button[tags.length];
		
		for (int i=0 ; i<tags.length ; ++i) {
			tagButton[i] = new Button(tags[i]);
			tagButton[i].getStyleClass().add("button2");
			final String tag = tags[i];
			tagButton[i].setOnAction(new EventHandler<ActionEvent>() {
				
	            @Override
	            public void handle(ActionEvent event) {
	            	System.out.println("runWebParser");
	            	Dot dot1 = new Dot(50, 35);
					Dot dot2 = new Dot(70, 35);
					Dot dot3 = new Dot(90, 35);
					Dot dot4 = new Dot(110,35);
					linkAnimation.getChildren().addAll(dot1, dot2, dot3, dot4);
					animation = new Animation(dot1, dot2, dot3, dot4);  
	            	runWebParserService(tag);
	            }
	        });
			control2.getChildren().add(tagButton[i]);
			VBox.setMargin(tagButton[i], new Insets(5, 0, 5, 0));
		}
	}
	
	public void runWebParserService(String tag) {
		WebParserService webParserService = new WebParserService(tag);
		System.out.println("running webParserSucceed");
    	webParserService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent arg0) {
				// TODO Auto-generated method stub
				
				animation.stopAnimation();
				linkAnimation.getChildren().clear();
				ArrayList<WebParserService.Pair> list = webParserService.list;
				boolean swich = false;
				linksLayout.getChildren().clear();
				for (int i=0 ; i<list.size() ; ++i) {
					WebParserService.Pair pair = list.get(i);
					if (pair.text.equals("Tools")) {
						swich = true;
						continue;
					}
					
					if (pair.text.equals("2"))
	      				break;
					
					if (swich && pair.isValid()) {
						MyHyperlink link = new MyHyperlink(pair.text+"\n", pair.uri);
						MyHyperlink space = new MyHyperlink("", "");
						linksLayout.getChildren().addAll(link, space);
						
					}
				}
			}
		});
    	webParserService.start();
	}
	
}
