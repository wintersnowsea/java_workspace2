package game.shooting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

//실제적으로 게임이 구현되는 영역
public class GamePanel extends JPanel{
	Thread loopThread; //게임엔진역할을 수행할 루프쓰레드
	BgObject bgObject;
	Hero hero;
	ArrayList<Bullet> bulletList=new ArrayList<Bullet>();
	ArrayList<Enemy> enemyList=new ArrayList<Enemy>();
	String[] enemyPath= {"res/mon14.png","res/mon19.png","res/mon20.png","res/mon18.png"};
	
	public GamePanel() {
		setPreferredSize(new Dimension(900,500));
		setBackground(Color.PINK);
		
		createBg(); //배경 생성
		createHero(); //Hero 탄생
		createEnemy(); //적군 생성
		
		loopThread=new Thread() {
			public void run() {
				while(true) {
					gameLoop();
					try {
						loopThread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		loopThread.start(); //Thread 시작(Runnable 진입)
	}
	
	//패널의 그래픽 처리
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		//기존그림지우기
		g2.clearRect(0, 0, 900, 500);
		
		bgObject.render(g2);
		hero.render(g2);
		
		//총알 개수만큼 render()
		for(int i=0;i<bulletList.size();i++) {
			Bullet bullet=bulletList.get(i);
			bullet.render(g2);
		}
		//적군 개수만큼 render()
		for(int i=0;i<enemyList.size();i++) {
			Enemy enemy=enemyList.get(i);
			enemy.render(g2);
		}
	}
	
	//배경 만들기
	public void createBg() {
		bgObject=new BgObject(this, 0, 0, 900, 500, -1, 0);
	}
	
	//주인공 생성
	public void createHero() {
		hero=new Hero(this, 50,100,70,70,0,0);
	}
	
	//적군 생성
	public void createEnemy() {
		for(int i=0;i<enemyPath.length;i++) {
			Enemy enemy=new Enemy(this, 850, 120*i, 70, 70, -3, 0, enemyPath[i]); //마지막은 경로!
			enemyList.add(enemy); //컬렉션에 추가! -> tick(), render()호출하기 위해 배열에 담음
		}
	}
	
	//좌우 방향 처리
	public void moveX(int velX) {
		hero.velX=velX;
	}
	
	//위아래 처리
	public void moveY(int velY) {
		hero.velY=velY;
	}
	
	//총알 발사
	public void fire() {
		Bullet bullet=new Bullet(this, hero.x+hero.width, hero.y+hero.height/4,20,20,10,0);
		bulletList.add(bullet); //컬렉션에 총알 추가
	}
	
	//물리량 변화
	public void tick() {
		bgObject.tick();
		hero.tick();
		
		//총알 개수만큼 tick()
		for(int i=0;i<bulletList.size();i++) {
			Bullet bullet=bulletList.get(i);
			bullet.tick();
		}
		//적군 개수만큼 tick()
		for(int i=0;i<enemyList.size();i++) {
			Enemy enemy=enemyList.get(i);
			enemy.tick();
		}
	}
	
	//렌더링 처리->그림을 그린다
	public void render() {
		repaint();
		
	}
	
	public void gameLoop() {
		//System.out.println("gameLoop() called....");
		tick();
		render();
	}
}
