import java.awt.Image;

public class SpaceShip extends Sprite2d {

	
	public SpaceShip(Image i,Image j, int x, int y){
		super(i,j,x,y);
		
	}
	
	public void move(int input){
		
		
		if(input==-1){
			this.x -=100;
		}else if(input ==1){
			this.x +=100;
		}
		
		if(this.x <0 ){
			this.x=0;
		return;
		}else if(this.x>700){
			this.x=700;
		}
	}
	
}
