import java.awt.Color;
import java.awt.Graphics;
public class Ball {
	
	public double x;
	public double y;
	public double radius;
	public double xVelocity;
	public double yVelocity;
	public Color ballColor;
	public Boolean collidable;
	
	public Ball (double x, double y,double radius, double xVelocity, double yVelocity){
		
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.ballColor = Color.red;
		this.collidable = true;	
	}
	
    public void draw(Graphics g, double xScale, double yScale) {
        g.setColor(ballColor);
        g.fillOval((int)(x * xScale), (int)(y * yScale), (int)(radius * xScale), (int)(radius * yScale));
    }
    
    public void WallCollisions(double width, double height){
    	
    	if (this.x + radius > width || this.x <= 0){
    		xVelocity *= -1;
    	}
    	if (this.y <= 0 ){
    		yVelocity *= -1;
    	}
    	
    }
    
	public void PaddleCollision(double x, double y, double width, double height){			
		
		if (this.x + radius >= x && 
			this.y + radius >= y && 
			this.x <= x + width && 
			this.y <= y + height){	
			
			yVelocity *= -1;
		}
	}
    
    public void moveBall(){
    	x += xVelocity;
    	y += yVelocity;
    }
    
		
}
