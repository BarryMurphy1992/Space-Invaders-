import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.image.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class InvadersApplication extends JFrame implements Runnable, KeyListener {

	public static final Dimension WindowSize = new Dimension (800,600);
	private BufferStrategy strategy;
	public static final int NUMALIENS =30;
	private Alien[] Aliens = new Alien[NUMALIENS];
	private SpaceShip playerSprite ;
	private ArrayList<PlayerBullet> bullets = new ArrayList<PlayerBullet>();
	String workingDirectory=System.getProperty("user.dir");
	private boolean startup= true;
	private boolean isPlaying = false;
	private int alienDeathCounter = 0;
	private int playerScore =0;
	private boolean firstGame = true;
	
	
	public InvadersApplication(){
		System.out.println("work");
		this.setTitle("Space invaders. Controls : Left & Right arrow keys");
		addKeyListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		int x = (int) (screensize.getWidth()/2 - WindowSize.getWidth()/2);
		int y = (int) (screensize.getHeight()/2 - WindowSize.getHeight()/2);
		
		setBounds(x,y,WindowSize.width,WindowSize.height);
		
		setVisible(true);
		
		
		//startGame();
		Thread t = new Thread(this);
		t.start();
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(firstGame){
			firstGame = false;
			startGame();
			return;
		}
		
		
		
		
		if(e.getKeyCode() == e.VK_LEFT || e.getKeyCode() == e.VK_RIGHT){
		 int x=0;
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_LEFT ){
			x=-1;
		}else if(e.getKeyCode() == e.VK_RIGHT){
			x=1;
		}
		
		playerSprite.move(x);
		}
		
		if(e.getKeyCode() == e.VK_SPACE ){
			Image img;
			ImageIcon bulletimg = new ImageIcon( workingDirectory +"\\bullet.png");
			img = bulletimg.getImage();
			PlayerBullet bullet= new PlayerBullet(img,img,(int)playerSprite.x+54/2 , (int)playerSprite.y);
			bullets.add(bullet);
			
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//if(isPlaying == false)return;
		while(true){

			
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean change = false;
			for(int i =0; i<NUMALIENS ; i++){
				if(Aliens[i] != null){
				if(Aliens[i].move()){
					change = true;
					}
				}
			}
			
			if(change)changeDirection();

			
			
			this.repaint();
		}
		
	}
	
	public void paint(Graphics g){
		if(isPlaying == false){
			g.setColor(Color.black);
			g.fillRect(0, 0, WindowSize.width, WindowSize.height);
			g.setColor(Color.red);
			g.drawString("PRESS ANY BUTTON TO START", 300,200);
			return;
		}
		
		
		if(startup == true)return;
		
		
		

		
		g = strategy.getDrawGraphics();
		
		

		
		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, WindowSize.width, WindowSize.height);
		g.setColor(Color.red);

		g.drawString("SCORE: " + playerScore, 10, 50);
		
		
		
		
		for(PlayerBullet b: bullets){
			
			
			b.move();
			if(b.y <0){
				bullets.remove(b);
				continue;
			}
			b.paint(g);
			
			//**THE IMAGE SIZES ARE 50x32 AND 6x16. FOR DIFFERENT SIZES YOU WILL HAVE TO CHANGE THE VALUES BELOW**
			for(int i=0; i< NUMALIENS; i++){
				if(Aliens[i] != null){
			if ( ((Aliens[i].x<b.x && Aliens[i].x +50> b.x) ||
					(b.x<Aliens[i].x && b.x+6>Aliens[i].x))
					&&
					( (Aliens[i].y<b.y && Aliens[i].y+32>b.y) ||
					(b.y<Aliens[i].y && b.y+16>Aliens[i].y) )) {
				 Aliens[i] = null;
				 alienDeathCounter ++;
				 bullets.remove(b);
				 playerScore += 100;
				 break;
				
					}
			
				}
			
			}
		}

		deathCheck();
		if(alienDeathCounter >= NUMALIENS){
			startup=true;
			isPlaying = false;
			startGame();
			
			//This clears all bullets so no new aliens are instantly killed when the next wave comes
			bullets.clear();
			
			return;
		}
		
		for(int i =0; i< NUMALIENS ; i++){
			if(Aliens[i] != null){
			Aliens[i].paint(g);
			}
		
		}
		playerSprite.paint(g);
		
		
		g.dispose();
		strategy.show();
	}
	
	public static void main(String[] args){
		InvadersApplication s =new InvadersApplication();
	
	}
	
	public void changeDirection(){
		for(int i =0; i<NUMALIENS ; i++){
			if(Aliens[i] != null){
			Aliens[i].reverseDirection();
			}
		}
	}
	
	public void startGame(){
		alienDeathCounter =0;
		isPlaying = true;
		
		
		Image img;
		ImageIcon alienImg = new ImageIcon( workingDirectory +"\\alien_ship_1.png");
		img = alienImg.getImage();
		
		Image img1;
		ImageIcon alienImg1 = new ImageIcon( workingDirectory +"\\alien_ship_2.png");
		img1 = alienImg1.getImage();
		
		int xx=50,yy=50;
		
		for (int i=0;i<=NUMALIENS-5;i+=5,yy+=50,xx=50){
			for(int j=i;j<i+5;j++,xx+=120){
				Aliens[j] = new Alien(img,img1,xx,yy);
				Aliens[j].increaseSpeed();
			}
			
		}

		ImageIcon playerImg = new ImageIcon( workingDirectory +"\\player_ship.png");
		img = playerImg.getImage();
		playerSprite = new SpaceShip(img,img,400,500);
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		startup = false;
		
	}
	
	public void deathCheck(){
		for(int i=0; i< NUMALIENS; i++){
			if(Aliens[i] != null){
		if ( ((Aliens[i].x<playerSprite.x && Aliens[i].x +50> playerSprite.x) ||
				(playerSprite.x<Aliens[i].x && playerSprite.x+6>Aliens[i].x))
				&&
				( (Aliens[i].y<playerSprite.y && Aliens[i].y+32>playerSprite.y) ||
				(playerSprite.y<Aliens[i].y && playerSprite.y+16>Aliens[i].y) )) {
			 isPlaying = false;
			 firstGame = true;
			break;
				}
		
			}
		
		}
	}
}

	

