import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Brick {
	
	public double columnIndex;
	public double rowIndex;
	public double numbricks = 96;
	public double x;
	public double y;
	public double width = 38;
	public double height = 10;
	public double sideGap = 50;
	public double topGap = 50;
	public double brickWidthGap = 5;
	public double brickHeightGap = 5;
	public Color brickColor;
	public Boolean collidable;
	
	public Brick(double columnIndex, double rowIndex, Boolean collidable){
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
		x = sideGap + (columnIndex * this.width) + (columnIndex * brickWidthGap);
		y = topGap + (rowIndex * this.height) + (rowIndex * brickHeightGap);
		this.collidable = collidable;
		this.brickColor = Color.white;
	}
	
	public void destroyBrick(){
		this.brickColor = Color.black;
		collidable = false;
	}
	
	public void updateBrickDimentions(){

	}
	
	public void draw(Graphics g, double xScale, double yScale) {
		g.setColor(brickColor);
		g.fillRect((int)(x * xScale), (int)(y * yScale), (int) (width * xScale), (int)(height * yScale));
	}
	
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }
	
}
