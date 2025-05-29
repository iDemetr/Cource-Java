package Lr_6.UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * 
 */
public class FadingJLabel extends JLabel {

    private double alpha = 0f;
    private double alphaTarget = 0f;
    private boolean fadeIn = true;
    private Timer timer;

    /**
     * 
     * @param text
     */
    public FadingJLabel(String text) {
	super(text);

	setForeground(Color.WHITE);
	setFont(new Font("Arial", Font.BOLD, 24));
	setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D) g.create();
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha)); // .SRC_OVER
	super.paintComponent(g2);
	g2.dispose();
    }

    /**
     * 
     * @param duration
     */
    public void fadeIn(int duration) {
	fadeIn = true;
	alpha = 0f;
	startFade(duration);
    }

    /**
     * 
     * @param duration
     */
    public void fadeOut(int duration) {
	fadeIn = false;
	alpha = 1f;
	startFade(duration);
    }

    /**
     * 
     * @param duration
     */
    private void startFade(int duration) {
	if (timer != null && timer.isRunning()) {
	    timer.stop();
	}

	int steps = 50;
	int interval = duration / steps;
	timer = new Timer(interval, new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (fadeIn) {
		    alpha += alphaTarget / steps;
		    if (alpha >= alphaTarget) {
			alpha = alphaTarget;
			timer.stop();
		    }
		} else {
		    alpha -= alphaTarget / steps;
		    if (alpha <= alphaTarget) {
			alpha = alphaTarget;
			timer.stop();
			setVisible(false);
		    }
		}
		repaint();
	    }
	});
	setVisible(true);
	timer.start();
    }

    /**
     * 
     * @return
     */
    public double getAlpha() {
	return alpha;
    }

    /**
     * 
     * @param alpha
     */
    public void setAlpha(double alpha) {

	if (alpha < 0) {
	    alpha = 0;
	} else if (alpha > 1) {
	    alpha = 1;
	}

	this.alpha = alpha;
	alphaTarget = alpha;

	revalidate();
	repaint();
    }
}