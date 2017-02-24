import java.awt.Image;

public class PlayerBullet  extends Sprite2d{
	public boolean isDead = false;

	public PlayerBullet(Image i,Image j, int xx, int yy) {
		super(i,j, xx, yy);
		// TODO Auto-generated constructor stub
	}
	
	public boolean move(){
		this.y-= 5;
		return false;
	}

}
