package game.shooting;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import util.ImageManager;

//게임에 등장할 모든 객체들의 최상위 클래스!!
//tick(), render()은 정의하면 안되기 때문에 추상메서드로 선언!
public abstract class GameObject {
	int x;
	int y;
	int width;
	int height;
	int velX;
	int velY;
	Image image; //그려질 이미지 선언
	ImageManager imageManager; //이미지를 가져 올 util 불러오기
	Rectangle rect;//크기를 가져올 수 있다!, 객체로 좌표도 가져올 수 있음
	GamePanel gamePanel;
	
	public GameObject(GamePanel gamePanel, int x, int y, int width, int height, int velX, int velY) {
		this.gamePanel=gamePanel;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.velX=velX;
		this.velY=velY;
		rect=new Rectangle(x, y, width, height);
		imageManager=new ImageManager();
	}
	
	public void rectTick() {
		rect.x=x;
		rect.y=y;
	};
	
	public abstract void tick();
	
	public abstract void render(Graphics2D g); 
}
