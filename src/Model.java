import java.util.ArrayList;
import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;

public class Model {

	//Board
	public double BOARDWIDTH = 800;
	public double BOARDHEIGHT = 600;
	//FPS
	public double MPS = 1000/60;
	//Bricks
	public double NUMCOLUMNS = 16;
	public double NUMROWS = 6;
	public double NUMBRICKS = 96;
	//Ball
	public double ballX = 400;
	public double ballY = 0;
	public double radius = 5;
	public double velocity = 1;
	//Paddle
	public double paddleX = 350;
	public double paddleY = 550;
	public double paddleW = 100;
	public double paddleH = 15;
	
	//Score
	public int SCORE = 0;
	//Timer
	public Timer CLOCK;
	//Booleans
	Boolean showTitleScreen = true;
	Boolean playing = false;
	Boolean leftPressed = false;
	Boolean rightPressed = false;
	Boolean gameOver = false;
	Boolean bulletCanCollide = false;
	//Game Objects
	public Ball ball;
	public Paddle paddle;
	public Bullet bullet;
	ArrayList<Brick> bricks;
	Vector<ViewObserver> views;
	
	public Model(double velocity){
		this.velocity = velocity;
		ball = new Ball(ballX,ballY,radius,velocity,velocity);
		paddle = new Paddle(paddleX, paddleY, paddleW, paddleH);
		bricks = new ArrayList<Brick>();
		bullet = new Bullet(-10, -10);
		initBricks();
		views = new Vector();
		CLOCK = new Timer();
	}
	public void initCLOCK(){
		CLOCK.scheduleAtFixedRate(new EventLoop(), 0, (long) MPS);
	}
	public Boolean canCollide(){
		
		Brick topLeftBrick = bricks.get(0);
		Brick bottomRightBrick = bricks.get(bricks.size() - 1);
		
		if (ball.x + ball.radius >= topLeftBrick.x && 
			ball.y + ball.radius >= topLeftBrick.y && 
			ball.x <= bottomRightBrick.x + bottomRightBrick.width && 
			ball.y <= bottomRightBrick.y + bottomRightBrick.height 
			){
			return true;

		}
		else{
			return false;
		}
	}
	public void doBrickCollision(){
		for (Brick b : bricks) {
			
			if (ball.yVelocity > 0){
				if (ball.x + ball.radius >= b.x && ball.x <= b.x + b.width && b.y >= ball.y  && b.y <= ball.y + ball.radius && b.collidable){
					ball.yVelocity *= -1;
					b.destroyBrick();
					SCORE += 100;
					break;
				}
			}
			else{
				if (ball.x + ball.radius >= b.x && ball.x <= b.x + b.width && b.y + b.height >= ball.y  && b.y + b.height <= ball.y + ball.radius && b.collidable){
					ball.yVelocity *= -1;
					b.destroyBrick();
					SCORE += 100;
					break;
				}
			}
			
			if (ball.xVelocity < 0){
				if (ball.y + ball.radius >= b.y && ball.y <= b.y + b.height && b.x + b.width >= ball.x && b.x + b.width <= ball.x + ball.radius && b.collidable){
					ball.xVelocity *= -1;
					b.destroyBrick();
					SCORE += 100;
					break;
				}
			}else{
				if (ball.y + ball.radius >= b.y && ball.y <= b.y + b.height && b.x >= ball.x  && b.x <= ball.x + ball.radius && b.collidable){
					ball.xVelocity *= -1;
					b.destroyBrick();
					SCORE += 100;
					break;
				}
			}
			
		}
	}
	
	public void doBulletCollision(){
		for (Brick b : bricks) {
			if (b.getBounds().intersects(bullet.getBounds()) && b.collidable){
				b.destroyBrick();
				SCORE += 50;
				bulletCanCollide = false;
				bullet.x = -10;
				bullet.y = -10;
				break;
			}
		}
	}
	
	public void createBullet(){
		if (bulletCanCollide == false){
			bullet = new Bullet(paddle.x + paddle.width/2, paddle.y + paddle.height/2);
			bulletCanCollide = true;
		}
	}
	
	public boolean hasWon(){
		return SCORE == 9600;
	}
	public void initBricks(){
		for (int x = 0; x < 96; x++){
			bricks.add(new Brick(x % 16, x / 16, true));
		}
	}
    public void addObserver(ViewObserver view) {
        views.add(view);
    }
    public void PromptObservers() {
        for (ViewObserver v : views) {
            v.update();
        }
    } 
    public void restart(){
		ball = new Ball(ballX,ballY,radius,velocity,velocity);
		paddle = new Paddle(paddleX, paddleY, paddleW, paddleH);
		bricks.clear();
		initBricks();
		CLOCK = new Timer();
		CLOCK.scheduleAtFixedRate(new EventLoop(), 0, (int)MPS);
		SCORE = 0;
		leftPressed = false;
		rightPressed = false;
		bulletCanCollide = false;
    }
    public Boolean hasLost(){
    	return ball.y >= BOARDHEIGHT;
    }
    private class EventLoop extends TimerTask {
		public void run() {
			ball.moveBall();
			ball.WallCollisions(BOARDWIDTH, BOARDHEIGHT);
			ball.PaddleCollision(paddle.x, paddle.y, paddle.width, paddle.height);
			if (canCollide())
				doBrickCollision();
			paddle.movePaddle(leftPressed, rightPressed);
					
			if (bulletCanCollide){
				bulletCanCollide = !bullet.WallCollisions();
				bullet.moveBullet();
				doBulletCollision();
			}
			
			
				
		
			//PromptObservers();			
		}
    }
}
