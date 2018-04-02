package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Dot extends Circle{
	
	public Dot(double x, double y) {
		super();
		this.setRadius(5.0f);
		this.setCenterX(x);
		this.setCenterY(y);
		this.setStrokeWidth(20);
		this.getStyleClass().add("dot");
		this.setFill(Color.valueOf("#C2185B"));
	}
}
