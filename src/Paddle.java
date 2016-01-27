import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Paddle {

	public double x;
	public double y;
	public double width;
	public double height;
	public Color paddleColor;
	
	public Paddle(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		paddleColor = Color.white;
	}
	public void draw(Graphics g, double xScale, double yScale) {
		g.setColor(paddleColor);
		g.fillRect((int)(x * xScale), (int)(y * yScale), (int)(width * xScale), (int)(height * yScale));
	}
	
	public void movePaddle(Boolean leftPressed, Boolean rightPressed){
		if (leftPressed)
			x -= 5;
		else if (rightPressed)
			x += 5;
	}
}
