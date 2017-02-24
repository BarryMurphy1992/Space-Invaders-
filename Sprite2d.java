import java.awt.*;

import javax.swing.JFrame;

public abstract class Sprite2d extends JFrame{

	protected double x,y;
	protected int xSpeed=0;
	protected Image myImage;
	protected Image myImage1;
	private int framesDrawn =0;

	int windWidth;
	
	
	public Sprite2d(Image i,Image j, int xx, int yy){
		
		myImage = i;
		myImage1 = j;
		
		x=xx;
		y=yy;
		
		this.repaint();
	}
	
	public void setPosition(double xx, double yy){
	
	}
	
	public void paint(Graphics g){
		//g.drawImage(myImage, (int) this.x, (int) this.y, null);
		framesDrawn++;
		if ( framesDrawn%100<50 )
		g.drawImage(myImage, (int)x, (int)y, null);
		else
		g.drawImage(myImage1, (int)x, (int)y, null);

	}
}