package game.shooting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

//총알을 정의한다
public class Bullet extends GameObject {
	
	
	public Bullet(GamePanel gamePanel,int x, int y, int width, int height, int velX, int velY) {
		super(gamePanel, x, y, width, height, velX, velY);
		
	}

	@Override
	//총알에 맞는 물리량 변화코드 (부모의 메서드를 재정의함)
	public void tick() {
		this.x+=this.velX;	
		
		rectTick();
		
		//총알과 적군이 서로 교차하는지 조사해보자!!
		//충돌 시 총알 및 적군 제거
		collsionCheck();
	}
	
	public void collsionCheck() {
		//나와 적군들 교차여부 판단하기
		for(int i=0;i<gamePanel.enemyList.size();i++) {
			Enemy enemy=gamePanel.enemyList.get(i);
			boolean result=this.rect.intersects(enemy.rect);
			if(result==true) {
				//컬렉션에서 제거하기
				//총알 없애고
				int myIndex=gamePanel.bulletList.indexOf(this);
				gamePanel.bulletList.remove(myIndex);
				
				//적군없애고
				int youIndex=gamePanel.enemyList.indexOf(enemy);
				gamePanel.enemyList.remove(youIndex);				
			}
		}
	}

	@Override
	//총알을 이미지 대신 그려서 날려보자
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillOval(x,y,width,height);
	}
}
