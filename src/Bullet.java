import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
public class Bullet {
	
	public double x;
	public double y;
	public double width = 2;
	public double height = 5;
	public double yVelocity = -1;
	public Color bulletColor;
	public Boolean destroyable = false;
	
	public Bullet (double x, double y){
		
		this.x = x;
		this.y = y;
		this.bulletColor = Color.blue;
	}
	
    public void draw(Graphics g, double xScale, double yScale) {
        g.setColor(bulletColor);
        g.fillRect((int)(x * xScale), (int)(y * yScale), (int)(width * xScale), (int)(height * yScale));
    }
    
    public boolean WallCollisions(){
    	if (this.x + height <= 0){
    		return true;
    	}
		return false;
    }
    
    public void moveBullet(){
    	y += yVelocity;
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }
    
		
}
