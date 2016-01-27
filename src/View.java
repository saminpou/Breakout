import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class View extends JPanel implements ActionListener, KeyListener, MouseMotionListener, ViewObserver {
	public Model model;
	Timer refreshTimer;
	public double xScale = 1;
	public double yScale = 1;
	public double FPS = 1000/60;
	// construct a PongPanel
	public View(Model m) {
		model = m;
		m.addObserver(this);
		setBackground(Color.BLACK);
		setFocusable(true);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(800, 600));
		refreshTimer = new Timer();
	}
	

	public void paintMenu(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
		g.drawString("Breakout!", 300, 100);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		g.drawString("Created by Sherwin Aminpour", 250, 150);
		g.drawString("UserID: 20529526", 300, 200);
		g.drawString("Username: saminpou", 290, 250);
		g.drawString("Press 'P' to play.", 300, 550);
	}

	// paint the game screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (model.showTitleScreen) {
			paintMenu(g);
		} else {
			g.setColor(Color.WHITE);
			g.drawString("Score: " + model.SCORE, 25, 25);
			g.drawString("FPS: " + FPS, 25, 50);
			model.paddle.draw(g, xScale, yScale);
			for (int x = 0; x < model.NUMBRICKS; x++) {
				model.bricks.get(x).draw(g, xScale, yScale);
			}
			model.ball.draw(g, xScale, yScale);
			if (model.bulletCanCollide)
				model.bullet.draw(g, xScale, yScale);
			if (model.hasWon() || model.hasLost()) {
				model.CLOCK.cancel();
				refreshTimer.cancel();
				StringBuilder dialogMessage = new StringBuilder();
				dialogMessage.append("Your scored ").append(model.SCORE).append("\n\n");
				dialogMessage.append("Play Again?");
				GameOverPopup gameOverDialog = new GameOverPopup(dialogMessage.toString(), "Game Over!");
				if (gameOverDialog.returnAnswer()) {
					model.restart();
					initRefreshClock();

				} else {
					System.exit(0);
				}
			}
		}
	}

	public void initRefreshClock() {
		refreshTimer = new Timer();
		refreshTimer.scheduleAtFixedRate(new RefreshLoop(), 0, (long) (1000/FPS));
	}

	public void update() {
		this.repaint();
	}

	public void actionPerformed(ActionEvent e) {}

	public void updateSize(int resizeWidth, int resizeHeight){
		
		xScale = (double) resizeWidth/model.BOARDWIDTH;
		yScale = (double) resizeHeight/model.BOARDHEIGHT;
	}
	
	public void keyPressed(KeyEvent e) {

		if (model.showTitleScreen) {
			if (e.getKeyCode() == KeyEvent.VK_P) {
				model.showTitleScreen = false;
				model.playing = true;
				refreshTimer.scheduleAtFixedRate(new RefreshLoop(), 0, (long) (1000/FPS));
				model.initCLOCK();
			}
		}

		if (model.playing) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				model.leftPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				model.rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_R) {
				model.ball.y = 700;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				model.createBullet();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (model.playing) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				model.leftPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				model.rightPressed = false;
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		model.paddle.x = e.getX();

	}

	private class RefreshLoop extends TimerTask {
		public void run() {
			update();
		}
	}
	/*
	public void componentResized(ComponentEvent e) {
		System.out.println(e.getComponent().getX());
		System.out.println(e.getComponent().getY());
	}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {	}
	public void componentHidden(ComponentEvent e) {}
	*/

}


