package application;

import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Animation {
	private Dot[] dot;
	private TranslateTransition[] tt;
	private FillTransition[] ft;
	
	public Animation(Dot dot1, Dot dot2, Dot dot3, Dot dot4) {
		
		dot = new Dot[4];
		this.dot[0] = dot1;
		this.dot[1] = dot2;
		this.dot[2] = dot3;
		this.dot[3] = dot4;
		
		tt = new TranslateTransition[4];
		ft = new FillTransition[4];
		
		for (int i=0 ; i<4 ; ++i) {
			tt[i] = new TranslateTransition();
			tt[i].setNode(dot[i]);
			tt[i].setDuration(Duration.millis(3500 - 500 * i));
			tt[i].setByX(800);
			tt[i].setCycleCount(TranslateTransition.INDEFINITE);
			tt[i].setAutoReverse(true);
			
			ft[i] = new FillTransition(Duration.millis(3500 - 500*i), dot[i], Color.valueOf("#D32F2F"), Color.valueOf("#7B1FA2"));
			ft[i].setCycleCount(FillTransition.INDEFINITE);
			ft[i].setAutoReverse(true);
		}
		
		for (int i=0 ; i<4 ; ++i) {
			tt[i].play();
			ft[i].play();
		}
		
//		this.dot1 = dot1;
//		this.dot2 = dot2;
//		this.dot3 = dot3;
//		this.dot4 = dot4;
//		
//		
//		tt1 = new TranslateTransition();
//		tt1.setNode(dot1);
//		tt1.setDuration(Duration.millis(35000));		   
//		tt1.setByX(800);
//		tt1.setCycleCount(javafx.animation.Animation.INDEFINITE);
//		tt1.setAutoReverse(true);
//		
//		tt2 = new TranslateTransition();
//		tt2.setNode(dot2);
//		tt2.setDuration(Duration.millis(3000));
//		tt2.setByX(800);
//		tt2.setCycleCount(javafx.animation.Animation.INDEFINITE);
//		tt2.setAutoReverse(true);
//		
//		
//		tt3 = new TranslateTransition();
//		tt3.setNode(dot3);
//		tt3.setDuration(Duration.millis(2500));
//		tt3.setByX(800);
//		tt3.setCycleCount(javafx.animation.Animation.INDEFINITE);
//		tt3.setAutoReverse(true);
//		
//		tt4 = new TranslateTransition();
//		tt4.setNode(dot4);
//		tt4.setDuration(Duration.millis(2000));
//		tt4.setByX(800);
//		tt4.setCycleCount(javafx.animation.Animation.INDEFINITE);
//		tt4.setAutoReverse(true);
//		
//		tt1.play();
//		tt2.play();
//		tt3.play();
//		tt4.play();
		
	}
	
	public void stopAnimation() {
		for (int i=0 ; i<4 ; ++i) {
			tt[i].stop();
			ft[i].stop();
		}
	}
}
