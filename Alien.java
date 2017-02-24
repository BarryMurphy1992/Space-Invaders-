import java.awt.Image;

import javax.swing.ImageIcon;

public class Alien extends Sprite2d {
	private double direction=1;

	
	public Alien(Image i,Image j, int x, int y){
		
		super(i,j, x, y);
		
	}
	
	public boolean  move(){
		this.x +=direction;
		
		if(this.x<=0 || x>=700){
			return true;
		}
		return false;
		
	}
	
	public void reverseDirection(){
		this.direction*=-1;
		this.y +=10;
	}
	
	public void increaseSpeed(){
		this.direction +=5;
	}
	
}
