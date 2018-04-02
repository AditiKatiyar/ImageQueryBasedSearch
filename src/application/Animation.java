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
			tt[i].setDuration(Duration.millis(3000 - 500 * i));
			tt[i].setByX(800);
			tt[i].setCycleCount(TranslateTransition.INDEFINITE);
			tt[i].setAutoReverse(true);
			
			ft[i] = new FillTransition(Duration.millis(3000 - 500 * i), dot[i], Color.valueOf("#005662"), Color.valueOf("#26c6da"));
			ft[i].setCycleCount(FillTransition.INDEFINITE);
			ft[i].setAutoReverse(true);
		}
		
		for (int i=0 ; i<4 ; ++i) {
			tt[i].play();
			ft[i].play();
		}
		
	}
	
	public void stopAnimation() {
		for (int i=0 ; i<4 ; ++i) {
			tt[i].stop();
			ft[i].stop();
		}
	}
}
