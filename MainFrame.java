package TermProject;

// 2021-2 DB Term Project 20205127 손무경

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{		// 메인프레임 생성

	// 프레임 오차 가로 16, 세로 39
	static int FRAME_WIDTH = 1400 + 16;
	static int FRAME_HEIGHT = 900 + 39;

	// 오디오 파일을 위한 경로
	static File path = new File(".");

	// 게임 사용자명
	static String userName;

	// 게임에 사용되는 이미지 소스들
	Image backGround = new ImageIcon(getClass().getClassLoader().getResource("background.png")).getImage();

	Image startPage = new ImageIcon(getClass().getClassLoader().getResource("STARTPAGE.png")).getImage();
	Image explainPage = new ImageIcon(getClass().getClassLoader().getResource("EXPLAINPAGE.png")).getImage();
	Image gameoverPage = new ImageIcon(getClass().getClassLoader().getResource("GAMEOVER.png")).getImage();

	ImageIcon fire_gif = new ImageIcon(getClass().getClassLoader().getResource("fire.gif"));
	JLabel fire = new JLabel();

	ImageIcon flag02_gif = new ImageIcon(getClass().getClassLoader().getResource("flag02.gif"));
	JLabel flag02 = new JLabel();

	ImageIcon flag03_gif = new ImageIcon(getClass().getClassLoader().getResource("flag03.gif"));
	JLabel flag03 = new JLabel();
	JLabel flag03_2 = new JLabel();

	ImageIcon start_Character_gif = new ImageIcon(getClass().getClassLoader().getResource("start_Character.gif"));

	ImageIcon idle_left_gif = new ImageIcon(getClass().getClassLoader().getResource("Idle_Left.gif"));
	ImageIcon idle_right_gif = new ImageIcon(getClass().getClassLoader().getResource("Idle_Right.gif"));

	ImageIcon moving_right_gif = new ImageIcon(getClass().getClassLoader().getResource("Moving_Right.gif"));
	ImageIcon moving_left_gif = new ImageIcon(getClass().getClassLoader().getResource("Moving_Left.gif"));

	ImageIcon dash_right_gif = new ImageIcon(getClass().getClassLoader().getResource("Dash_Right.gif"));
	ImageIcon dash_left_gif = new ImageIcon(getClass().getClassLoader().getResource("Dash_Left.gif"));

	ImageIcon attack_1_right_gif = new ImageIcon(getClass().getClassLoader().getResource("Attack_1_Right.gif"));
	ImageIcon attack_1_left_gif = new ImageIcon(getClass().getClassLoader().getResource("Attack_1_Left.gif"));

	ImageIcon attack_2_right_gif = new ImageIcon(getClass().getClassLoader().getResource("Attack_2_Right.gif"));
	ImageIcon attack_2_left_gif = new ImageIcon(getClass().getClassLoader().getResource("Attack_2_Left.gif"));

	ImageIcon attack_3_right_gif = new ImageIcon(getClass().getClassLoader().getResource("Attack_3_Right.gif"));
	ImageIcon attack_3_left_gif = new ImageIcon(getClass().getClassLoader().getResource("Attack_3_Left.gif"));

	ImageIcon monster_attack_right_gif = new ImageIcon(getClass().getClassLoader().getResource("MAttack_R.gif"));
	ImageIcon monster_attack_left_gif = new ImageIcon(getClass().getClassLoader().getResource("MAttack_L.gif"));
	ImageIcon monster_death_right_gif = new ImageIcon(getClass().getClassLoader().getResource("MDeath_R.gif"));
	ImageIcon monster_death_left_gif = new ImageIcon(getClass().getClassLoader().getResource("MDeath_L.gif"));
	ImageIcon monster_hit_right_gif = new ImageIcon(getClass().getClassLoader().getResource("MHit_R.gif"));
	ImageIcon monster_hit_left_gif = new ImageIcon(getClass().getClassLoader().getResource("MHit_L.gif"));
	ImageIcon monster_idle_right_gif = new ImageIcon(getClass().getClassLoader().getResource("MIdle_R.gif"));
	ImageIcon monster_idle_left_gif = new ImageIcon(getClass().getClassLoader().getResource("MIdle_L.gif"));
	ImageIcon monster_walk_right_gif = new ImageIcon(getClass().getClassLoader().getResource("MWalk_R.gif"));
	ImageIcon monster_walk_left_gif = new ImageIcon(getClass().getClassLoader().getResource("MWalk_L.gif"));


	ImageIcon explain_attack1_gif = new ImageIcon(getClass().getClassLoader().getResource("explain_attack1.gif"));
	ImageIcon explain_attack2_gif = new ImageIcon(getClass().getClassLoader().getResource("explain_attack2.gif"));
	ImageIcon explain_attack3_gif = new ImageIcon(getClass().getClassLoader().getResource("explain_attack3.gif"));
	ImageIcon explain_moveL_gif = new ImageIcon(getClass().getClassLoader().getResource("explain_moveL.gif"));
	ImageIcon explain_moveR_gif = new ImageIcon(getClass().getClassLoader().getResource("explain_moveR.gif"));
	ImageIcon explain_dash_gif = new ImageIcon(getClass().getClassLoader().getResource("explain_dash.gif"));
	ImageIcon explain_monsterW_gif = new ImageIcon(getClass().getClassLoader().getResource("explain_monsterW.gif"));
	ImageIcon explain_monsterA_gif = new ImageIcon(getClass().getClassLoader().getResource("explain_monsterA.gif"));

	// 사용자 Player 객체
	Player player;

	// 게임 기능 구현에 사용하는 boolean 변수들 (방향 판단 및 논리 판단을 위한 변수)
	boolean player_R;
	boolean player_L = true;

	boolean player_move_R;
	boolean player_move_L;

	boolean player_Attack1;
	boolean player_Attack2;
	boolean player_Attack3;

	boolean dash;

	boolean jumping;
	boolean falling;

	boolean GameOver;

	// 게임 기능 구현에 사용하는 int 변수들 (좌표 및 딜레이를 위한 변수)
	int dash_cooltime = 100;

	int attack_limit;

	int player_X;
	int player_Y;

	int enter_Count;

	int spawn_delay;
	int spawn_Limit;
	int spawn_Count;

	int monster_Count;

	int hit_delay;

	int stage = 1;

	int total_Score = 0;
	int stage_Score = 0;

	// 좌우 원거리 공격 객체를 위한 ArrayList 및 삭제를 위한 ArrayList, 몬스터와 몬스터 삭제 ArrayList
	ArrayList<Attack> attack_L = new ArrayList<>();
	ArrayList<Attack> attack_R = new ArrayList<>();

	ArrayList<Attack> remove_L = new ArrayList<>();
	ArrayList<Attack> remove_R = new ArrayList<>();

	ArrayList<Monster> monster = new ArrayList<>();
	ArrayList<Monster> monster_Remove = new ArrayList<>();

	// Thread 세 가지
	Thread repaintThread;
	Thread Process;
	Thread Process2;

	// 메인 패널
	MainPanel mainPanel;

	// 게임 인터페이스 및 gif구현에 사용되는 JLabel들
	JLabel nameLabel;
	JLabel hpLabel;

	JLabel stageLabel;
	JLabel totalScore;
	JLabel stageScore;
	JLabel monsterLabel;

	JLabel readyLabel;

	JLabel start_Character;

	JLabel explainMoveL;
	JLabel explainMoveR;
	JLabel explainAttack1;
	JLabel explainAttack2;
	JLabel explainAttack3;
	JLabel explainDash;
	JLabel explainMonsterW;
	JLabel explainMonsterA;

	JLabel endScore;

	// 플레이어 초기 좌표및 쓰레드 생성과 실행 라벨들의 위치와 폰트 크기 등 기본적인 설정이 이루어지는 생성자
	MainFrame() {
		// 사용자명 입력
		nameInput();
		// bgm 플레이
		bgm();

		player = new Player(604, 595);

		mainPanel = new MainPanel();
		mainPanel.setLayout(null);

		repaintThread = new Thread(new Repaint());
		repaintThread.start();
		Process = new Thread(new Process());
		Process.start();

		Process2 = new Thread(new Process2());

		start_Character = new JLabel();
		mainPanel.add(start_Character);
		start_Character.setIcon(start_Character_gif);
		start_Character.setBounds(508,227,384,384);


		nameLabel = new JLabel("USER : " + userName, JLabel.LEFT);
		mainPanel.add(nameLabel);
		nameLabel.setBounds(40,30,200,30);
		nameLabel.setFont(new Font("Permanent Marker", Font.PLAIN, 20));
		nameLabel.setVisible(false);

		hpLabel = new JLabel("HP", JLabel.LEFT);
		mainPanel.add(hpLabel);
		hpLabel.setBounds(40,80,40,30);
		hpLabel.setFont(new Font("Permanent Marker", Font.PLAIN, 20));
		hpLabel.setVisible(false);

		stageLabel = new JLabel("STAGE " + stage, JLabel.CENTER);
		mainPanel.add(stageLabel);
		stageLabel.setBounds(550,20,300,70);
		stageLabel.setFont(new Font("Permanent Marker", Font.PLAIN, 50));
		stageLabel.setVisible(false);

		totalScore = new JLabel("TOTAL SCORE : " + totalScore, JLabel.LEFT);
		mainPanel.add(totalScore);
		totalScore.setBounds(1120,30,240,30);
		totalScore.setFont(new Font("Permanent Marker", Font.PLAIN, 20));
		totalScore.setVisible(false);

		stageScore = new JLabel("STAGE SCORE : " + stage_Score, JLabel.LEFT);
		mainPanel.add(stageScore);
		stageScore.setBounds(1120,80,240,30);
		stageScore.setFont(new Font("Permanent Marker", Font.PLAIN, 20));
		stageScore.setVisible(false);

		monsterLabel = new JLabel("MONSTER : " + monster_Count, JLabel.LEFT);
		mainPanel.add(monsterLabel);
		monsterLabel.setBounds(1120,130,240,30);
		monsterLabel.setFont(new Font("Permanent Marker", Font.PLAIN, 20));
		monsterLabel.setVisible(false);

		readyLabel = new JLabel("READY", JLabel.CENTER);
		mainPanel.add(readyLabel);
		readyLabel.setBounds(500,280,400,150);
		readyLabel.setFont(new Font("Permanent Marker", Font.PLAIN, 70));
		readyLabel.setVisible(false);

		endScore = new JLabel();
		mainPanel.add(endScore);
		endScore.setBounds(400,450,600,200);
		endScore.setFont(new Font("Permanent Marker", Font.PLAIN, 60));
		endScore.setForeground(Color.white);
		endScore.setVisible(false);

		mainPanel.add(player);
		player.setBounds(604, 595, 192, 192);
		player.setVisible(false);

		mainPanel.add(fire);
		fire.setIcon(fire_gif);
		fire.setBounds(690, 620, 64, 64);
		fire.setVisible(false);

		mainPanel.add(flag02);
		flag02.setIcon(flag02_gif);
		flag02.setBounds(380, 325, 640, 480);
		flag02.setVisible(false);

		mainPanel.add(flag03);
		flag03.setIcon(flag03_gif);
		flag03.setBounds(0,390,640,480);
		flag03.setVisible(false);

		mainPanel.add(flag03_2);
		flag03_2.setIcon(flag03_gif);
		flag03_2.setBounds(837,387,640,480);
		flag03_2.setVisible(false);

		explainMoveL = new JLabel();
		mainPanel.add(explainMoveL);
		explainMoveL.setIcon(explain_moveL_gif);
		explainMoveL.setBounds(70,100,256,256);
		explainMoveL.setVisible(false);

		explainMoveR = new JLabel();
		mainPanel.add(explainMoveR);
		explainMoveR.setIcon(explain_moveR_gif);
		explainMoveR.setBounds(216,100,256,256);
		explainMoveR.setVisible(false);

		explainAttack1 = new JLabel();
		mainPanel.add(explainAttack1);
		explainAttack1.setIcon(explain_attack1_gif);
		explainAttack1.setBounds(430,100,256,256);
		explainAttack1.setVisible(false);

		explainAttack2 = new JLabel();
		mainPanel.add(explainAttack2);
		explainAttack2.setIcon(explain_attack2_gif);
		explainAttack2.setBounds(630,100,256,256);
		explainAttack2.setVisible(false);

		explainAttack3 = new JLabel();
		mainPanel.add(explainAttack3);
		explainAttack3.setIcon(explain_attack3_gif);
		explainAttack3.setBounds(830,100,256,256);
		explainAttack3.setVisible(false);

		explainDash = new JLabel();
		mainPanel.add(explainDash);
		explainDash.setIcon(explain_dash_gif);
		explainDash.setBounds(1055,100,256,256);
		explainDash.setVisible(false);

		explainMonsterW = new JLabel();
		mainPanel.add(explainMonsterW);
		explainMonsterW.setIcon(explain_monsterW_gif);
		explainMonsterW.setBounds(470,560,192,192);
		explainMonsterW.setVisible(false);

		explainMonsterA = new JLabel();
		mainPanel.add(explainMonsterA);
		explainMonsterA.setIcon(explain_monsterA_gif);
		explainMonsterA.setBounds(750,560,192,192);
		explainMonsterA.setVisible(false);


		mainPanel.addKeyListener(mainPanel);

		Dimension dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());

		this.add(mainPanel);

		this.setLocation((int)dim.getWidth()/2-FRAME_WIDTH/2, (int)dim.getHeight()/2-FRAME_HEIGHT/2);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("Guardian Game");
	}
	// 사용자에게 이름을 입력받는 메소드, 이름이 5자가 넘어가면 다시 입력받음
	public void nameInput() {
		for(;;) {
			userName = (String) JOptionPane.showInputDialog(this, "이름을 입력하세요(5자 이내)", "Guardian Game",JOptionPane.PLAIN_MESSAGE);
			if(userName == null)
				System.exit(0);
			if(userName.length() > 5)
				userName = (String) JOptionPane.showInputDialog(this, "이름을 입력하세요(5자 이내)", "Guardian Game",JOptionPane.PLAIN_MESSAGE);
			else if(userName.length() <= 5) {
				break;
			}
		}
	}
	// bgm이 실행되는 메소드 (무한반복)
	public static void bgm() {
		try {
			File bgm = new File(path + "/SoundSource/bgm.wav");
			Clip clip2 = AudioSystem.getClip();
			clip2.open(AudioSystem.getAudioInputStream(bgm));
			if(true) {
				clip2.loop(0);
			}
			clip2.start();
		} catch (Exception e) {
			System.out.println("Sound Error BGM");
		}
	}
	// main 메소드
	public static void main(String[] args) {
		new MainFrame();
	}
	// 게임의 진행과 게임의 종료를 판단하는 메소드, 게임 점수와 몬스터 숫자를 업데이트 시켜줌
	class GameProcess implements Runnable {
		public void run() {
			while(true) {
				stageLabel.setText("STAGE " + stage);
				totalScore.setText("TOTAL SCORE : " + total_Score);
				stageScore.setText("STAGE SCORE : " + stage_Score);
				monsterLabel.setText("MONSTER : " + monster_Count);

				try {
					if(stage_Score == spawn_Limit * 10 && stage < 5) {
						stageLabel.setText("STAGE " + stage);
						totalScore.setText("TOTAL SCORE : " + total_Score);
						stageScore.setText("STAGE SCORE : " + stage_Score);
						monsterLabel.setText("MONSTER : " + 0);

						readyLabel.setVisible(true);
						Thread.sleep(3000);
						stage_Score = 0;
						if(stage < 5)
							stage++;
						spawn_Count = 0;
						spawn_delay = 0;
					}
				}catch(Exception e) {
					System.out.println("예외처리");
				}
				readyLabel.setVisible(false);

				if(player.HP <= 0 || (stage_Score == spawn_Limit * 10 && stage == 5)) {
					GameOver = true;
				}
				if(GameOver) {
					for(Monster m : monster) {
						mainPanel.remove(m);
					}
					player.setVisible(false);
					fire.setVisible(false);
					flag02.setVisible(false);
					flag03.setVisible(false);
					flag03_2.setVisible(false);
					nameLabel.setVisible(false);
					hpLabel.setVisible(false);
					stageLabel.setVisible(false);
					totalScore.setVisible(false);
					stageScore.setVisible(false);
					monsterLabel.setVisible(false);
					endScore.setText("Your Score : " + total_Score);
					endScore.setHorizontalAlignment(JLabel.CENTER);
					endScore.setVisible(true);
				}
			}

		}
	}

	// 게임의 기능과 이미지 설정이 이루어지는 메소드, 플레이어의 위치 이동과 동작에 따른 이미지 설정이 이루어짐
	class Process implements Runnable {
		public void run() {
			while(!GameOver) {
				try {
					Thread.sleep(10);

					player_X = player.getX();

					if(player_R) {
						player.setIcon(idle_right_gif);
						if(player_move_R && !player_Attack1 && !player_Attack2 && !player_Attack3) {
							player.setIcon(moving_right_gif);
							if(player_X + 128 < 1400)
								player.moveR();
						}
						if(dash && dash_cooltime == 100) {
							player.setIcon(dash_right_gif);
							try {
								for(int i = 0 ; i < 30; i++) {
									if(player.getX() + 128 < 1400)
										player.dashR();
									Thread.sleep(10);
								}
							} catch(InterruptedException e) {
								System.out.println("예외처리");
							}
							dash_cooltime = 0;
						}
						if(player_Attack1) {
							player.setIcon(attack_1_right_gif);
						}
						if(player_Attack2) {
							player.setIcon(attack_2_right_gif);
							attack_limit++;
							if(attack_limit == 16) {
								attack_R.add(new Attack(player.getX() + 113, player.getY() + 135));
							}
							else if(attack_limit == 50) {
								attack_R.add(new Attack(player.getX() + 113, player.getY() + 135));
							}
							else if(attack_limit == 66)
								attack_limit = 0;

						}
						if(player_Attack3) {
							player.setIcon(attack_3_right_gif);
						}
					}

					if(player_L) {
						player.setIcon(idle_left_gif);
						if(player_move_L && !player_Attack1 && !player_Attack2 && !player_Attack3) {
							player.setIcon(moving_left_gif);
							if(player_X + 63> 0)
								player.moveL();
						}
						if(dash && dash_cooltime == 100) {
							player.setIcon(dash_left_gif);
							try {
								for(int i = 0 ; i < 30; i++) {
									if(player.getX() + 63 > 0)
										player.dashL();
									Thread.sleep(10);

								}
							} catch(InterruptedException e) {
								System.out.println("예외처리");
							}
							dash_cooltime = 0;
						}
						if(player_Attack1) {
							player.setIcon(attack_1_left_gif);
						}
						if(player_Attack2) {
							player.setIcon(attack_2_left_gif);
							attack_limit++;
							if(attack_limit == 16) {
								attack_L.add(new Attack(player.getX() + 69, player.getY() + 135));
							}
							else if(attack_limit == 50) {
								attack_L.add(new Attack(player.getX() + 69, player.getY() + 135));
							}
							else if(attack_limit == 66)
								attack_limit = 0;
						}
						if(player_Attack3) {
							player.setIcon(attack_3_left_gif);
						}
					}

					if(!player_Attack2)
						attack_limit = 0;

					int player_X = player.getX() + 95;
					if((player_X < 370  && player.getY() + 168 <= 665 && !jumping && !falling)
							|| (player_X > 1030  && player.getY() + 168 <= 665 && !jumping && !falling)) {
						new Thread(new Runnable() {
							public void run() {
								falling = true;
								int set = 0;

								int under = player.getY() + 168;

								while ( under <= 765) {
									set++;
									if(under + set >= 765) {
										player.setY(596);
										break;
									}
									player.fall(set);
									under = player.getY() + 168;
									try {
										Thread.sleep(10);
									} catch (InterruptedException e) {
										System.out.println("예외처리");
									}
								}
								if(under > 765) {
									player.setY(596);
								}
								falling = false;
							}
						}).start();
					}

				} catch (InterruptedException e) {
					System.out.println("예외처리");
				}
				catch(Exception e){
					System.out.println("예외처리");
				}
			}
		}
	}

	// 몬스터의 생성이 이루어지는 메소드, 몬스터의 동작과 이미지가 결정되어 이루어지고 플레이어 공격에 따른 몬스터 데미지와 죽음이 판단되는 메소드
	class Process2 implements Runnable {
		public void run() {
			while(!GameOver) {
				try {

					spawn_Limit = stage * 10;
					Thread.sleep(10);
					spawn_delay++;
					if(spawn_delay == 150 && spawn_Count < spawn_Limit) {
						int rand = (int)(Math.random() * 4) + 1;
						if(rand == 1) {
							monster.add(new Monster(0,509,1, stage * 5));
							mainPanel.add(monster.get(monster_Count));
							monster_Count++;
							spawn_Count++;
						}
						else if(rand == 2) {
							monster.add(new Monster(1272,509,2,stage * 5));
							mainPanel.add(monster.get(monster_Count));
							monster_Count++;
							spawn_Count++;
						}
						else if(rand == 3) {
							monster.add(new Monster(0,669,3,stage * 5));
							mainPanel.add(monster.get(monster_Count));
							monster_Count++;
							spawn_Count++;
						}
						else {
							monster.add(new Monster(1272,669,4,stage * 5));
							mainPanel.add(monster.get(monster_Count));
							monster_Count++;
							spawn_Count++;
						}

						mainPanel.add(fire);
						mainPanel.add(flag02);
						mainPanel.add(flag03);
						mainPanel.add(flag03_2);
						spawn_delay = 0;
					}
					for(Monster m : monster) {
						if(!m.Attack_M)
							m.move();
						if(m.getY() + 95 >= 765)
							m.setY(667);
						if(m.getY() + 95 <= 604 && m.getX() + 64 >= 220 && m.getX() + 64 <= 1180) {
							new Thread(new Runnable() {
								public void run() {
									int under = m.getY() + 95;
									int set = 0;

									while ( under <= 765) {
										set++;
										if(under + set >= 765) {
											m.setY(667);
											break;
										}
										m.fall(set);
										under = m.getY() + 95;
										try {
											Thread.sleep(10);
										} catch (InterruptedException e) {
											System.out.println("예외처리");
										}
									}
								}
							}).start();
						}
						if(m.Right && m.getX() + 85 > 1400) {
							m.setLeft();
							m.setState(monster_walk_left_gif);
						}
						if(m.Left && m.getX() + 47 < 0) {
							m.setRight();
							m.setState(monster_walk_right_gif);
						}
						if(m.Right) {
							if(Math.abs((player.getX() + 95) - (m.getX() + 64)) <= 38 
									&& player.getY() + 166 <= m.getY() + 100 && player.getY() + 166 >= m.getY() + 60) {
								m.Attack_M = true;
								m.setState(monster_attack_right_gif);
								m.attackDelay++;
								if(m.attackDelay == 16) {
									if(m.getX() + 64 > player.getX() + 95) {
										player.hitR();
										player.hit();
									}
									else {
										player.hitL();
										player.hit();
									}
								}
								else if (m.attackDelay == 20)
									m.attackDelay = 0;
							}
							else {
								m.Attack_M = false;
								m.setState(monster_walk_right_gif);
								m.attackDelay=0;
							}
						} 
						if(m.Left) {
							if(Math.abs((player.getX() + 95) - (m.getX() + 64)) <= 38 
									&& player.getY() + 166 <= m.getY() + 100 && player.getY() + 166 >= m.getY() + 60 ) {
								m.Attack_M = true;
								m.setState(monster_attack_left_gif);
								m.attackDelay++;
								if(m.attackDelay == 16) {
									if(m.getX() + 64 > player.getX() + 95) {
										player.hitR();
										player.hit();
									}
									else {
										player.hitL();
										player.hit();
									}
								}
								else if (m.attackDelay == 20)
									m.attackDelay = 0;
							}
							else {
								m.Attack_M = false;
								m.setState(monster_walk_left_gif);
								m.attackDelay = 0;
							}
						}
						if(m.getHP() <= 0) {
							m.death();
							m.death_Delay++;
							if(m.death_Delay <=10) {
								stage_Score++;
								total_Score++;
							}
							if(m.death_Delay >= 20) {
								monster_Remove.add(m);
								monster_Count--;
							}
						}

					}
					if(player_Attack1) {
						hit_delay++;
						for(Monster m : monster) {
							if( hit_delay >= 32 && hit_delay <= 34  && Math.abs((player.getX() + 95) - (m.getX() + 64)) <= 86 
									&& player.getY() + 166 <= m.getY() + 95 && player.getY() + 166 >= m.getY() + 60) {
								if(m.getX() + 64  > player.getX() + 95 && player_R) {
									m.hit_L();
									m.hit();
								}
								if(m.getX() + 64  < player.getX() + 95 && player_L){
									m.hit_R();
									m.hit();
								}
							}
						}
						if(hit_delay > 54) hit_delay = 0;
					}
					else if (!player_Attack1 && !player_Attack3)
						hit_delay = 0;

					if(player_Attack3) {
						hit_delay++;
						for(Monster m : monster) {
							if( hit_delay >= 40 && hit_delay <= 44  && Math.abs((player.getX() + 95) - (m.getX() + 64)) <= 100
									&& player.getY() + 166 <= m.getY() + 95 && player.getY() + 166 >= m.getY() + 60) {
								if(m.getX() + 64  > player.getX() + 95 ) {
									m.hit_L();
									m.hit();
								}
								if(m.getX() + 64  < player.getX() + 95 ){
									m.hit_R();
									m.hit();
								}
							}
						}
						if(hit_delay > 84) hit_delay = 0;
					}else if(!player_Attack3 && !player_Attack1)
						hit_delay = 0;

					if(!attack_R.isEmpty()) {
						try {
							for(Attack aR : attack_R) {
								if(aR.getX() + 10 > 1400) {
									remove_R.add(aR);
								}
								for(Monster m : monster) {
									if(Math.abs((aR.getX() + 5) - (m.getX() + 64)) <= 25 && aR.getY() + 5 > m.getY() && aR.getY() + 5 < m.getY() + 95) {
										remove_R.add(aR);
										m.hit_L();
										m.hit();
									}
								}
								aR.moveR();
							}
						}catch (ConcurrentModificationException e) {
							System.out.println("예외처리");
						}
					}
					if(!attack_L.isEmpty()) {
						try {
							for(Attack aL : attack_L) {
								if(aL.getX() < 0) {
									remove_L.add(aL);
								}
								for(Monster m : monster) {
									if(Math.abs((aL.getX() + 5) - (m.getX() + 64)) <= 25 && aL.getY() + 5 > m.getY() && aL.getY() + 5 < m.getY() + 95) {
										remove_L.add(aL);
										m.hit_R();
										m.hit();
									}
								}
								aL.moveL();
							}
						}catch(ConcurrentModificationException e) {
							System.out.println("예외처리");
						}
					}

					for(Attack removeR : remove_R) {
						attack_R.remove(removeR);
					}
					for(Attack removeL : remove_L) {
						attack_L.remove(removeL);
					}
					for(Monster m : monster_Remove) {
						monster.remove(m);
						mainPanel.remove(m);
					}

				} catch(Exception e) {
					System.out.println("예외처리");
				}
			}
		}
	}

	// repaint 실행을 위한 메소드
	class Repaint implements Runnable {

		@Override
		public void run() {
			while(true) {
				try {
					repaint();
					Thread.sleep(10);
				} catch (Exception e) {
					System.out.println("예외처리");
				}
			}

		}
	}
	// 배경 이미지를 그려주고 JLabel의 보임 여부를 결정하는 JPanel, KeyListener를 통해 키 입력을 받음
	class MainPanel extends JPanel implements KeyListener{

		public void paintComponent(Graphics g) {
			int w = this.getWidth();
			int h = this.getHeight();

			setFocusable(true);
			requestFocus();

			if(enter_Count == 0) {
				g.drawImage(startPage,0,0,w,h,null);
			}
			if(enter_Count == 1) {
				g.drawImage(explainPage,0,0,w,h,null);
				start_Character.setVisible(false);
				explainMoveL.setVisible(true);
				explainMoveR.setVisible(true);
				explainAttack1.setVisible(true);
				explainAttack2.setVisible(true);
				explainAttack3.setVisible(true);
				explainDash.setVisible(true);
				explainMonsterW.setVisible(true);
				explainMonsterA.setVisible(true);
			}
			if(enter_Count >= 2) {
				explainMoveL.setVisible(false);
				explainMoveR.setVisible(false);
				explainAttack1.setVisible(false);
				explainAttack2.setVisible(false);
				explainAttack3.setVisible(false);
				explainDash.setVisible(false);
				explainMonsterW.setVisible(false);
				explainMonsterA.setVisible(false);
				g.drawImage(backGround, 0,0,w,h,null);

				g.setColor(Color.red);
				g.fillRect(86,83,player.HP*2, 20);

				if(dash_cooltime < 100) {
					dash_cooltime++;
				}

				if(!attack_R.isEmpty()) {
					try {
						for(Attack aR : attack_R) {
							aR.drawA(g);
						}
					} catch(ConcurrentModificationException e) {
						System.out.println("예외처리");
					}
				}
				if(!attack_L.isEmpty()) {
					try {
						for(Attack aL : attack_L) {
							aL.drawA(g);
						}
					} catch(ConcurrentModificationException e) {
						System.out.println("예외처리");
					}
				}
			}
			if(GameOver)
				g.drawImage(gameoverPage, 0, 0, w, h, null);
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();

			if(keycode == KeyEvent.VK_ENTER && enter_Count < 2) {
				enter_Count++;
				if(enter_Count >= 2) {
					player.setVisible(true);
					fire.setVisible(true);
					flag02.setVisible(true);
					flag03.setVisible(true);
					flag03_2.setVisible(true);
					nameLabel.setVisible(true);
					hpLabel.setVisible(true);
					stageLabel.setVisible(true);
					totalScore.setVisible(true);
					stageScore.setVisible(true);
					monsterLabel.setVisible(true);
					Process2.start();
					new Thread(new GameProcess()).start();
				}
			}

			if(keycode == KeyEvent.VK_RIGHT && enter_Count >= 2) {
				player_R = true;
				player_L = false;
				player_move_R = true;
			}
			if(keycode == KeyEvent.VK_LEFT && enter_Count >= 2) {
				player_L = true;
				player_R = false;
				player_move_L = true;
			}
			if(keycode == KeyEvent.VK_SHIFT && enter_Count >= 2) {
				dash = true;
			}
			if(keycode == KeyEvent.VK_A && enter_Count >= 2) {
				player_Attack1 = true;
			}
			if(keycode == KeyEvent.VK_S && enter_Count >= 2) {
				player_Attack2 = true;
			}
			if(keycode == KeyEvent.VK_D && enter_Count >= 2) {
				player_Attack3 = true;
			}

			if(keycode == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}

			// 점프 구현 메소드, 가속도의 성질을 가짐
			if(keycode == KeyEvent.VK_SPACE && jumping == false && enter_Count >= 2) {

				new Thread(new Runnable() {
					@Override
					public void run() {

						if(falling == false) {
							jumping = true;
							int set = 15;
							while(set >= 0) {
								set --;
								player.jump(set);
								try {
									Thread.sleep(10);
								} catch(InterruptedException e) {
									System.out.println("예외처리");
								}
							}
							falling = true;
							int under = player.getY() + 168;
							while ( under <= 765 ) {
								set++;
								if(under + set >= 765) {
									player.setY(596);
									break;
								}
								else if ( player.getX() + 95 >= 370 && player.getX() + 95 <= 1030 && under < 665 && under + set >= 665) {
									player.setY(496);
									break;
								}
								player.fall(set);
								under = player.getY() + 168;
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									System.out.println("예외처리");
								}
							}
						}
						jumping = false;
						falling = false;
					}
				}).start();
			}
		}

		public void keyReleased(KeyEvent e) {
			int keycode = e.getKeyCode();

			if(keycode == KeyEvent.VK_RIGHT) {
				player_move_R = false;
			}
			if(keycode == KeyEvent.VK_LEFT) {
				player_move_L = false;
			}
			if(keycode == KeyEvent.VK_SHIFT) {
				dash = false;
			}
			if(keycode == KeyEvent.VK_A) {
				player_Attack1 = false;
			}
			if(keycode == KeyEvent.VK_S) {
				player_Attack2 = false;
			}
			if(keycode == KeyEvent.VK_D) {
				player_Attack3 = false;
			}
		}
	}

	// Player객체 클래스 JLabel을 상속받아 x,y좌표와 HP를 가지며, 이미지는 gif를 구현함
	class Player extends JLabel {
		int pX;
		int pY;

		int HP = 100;

		Player(int x, int y) {
			pX = x;
			pY = y;
			super.setIcon(idle_left_gif);
		}
		public void moveR() {
			pX += 5;
			super.setBounds(pX, pY, 192, 192);
		}
		public void moveL() {
			pX -= 5;
			super.setBounds(pX, pY, 192, 192);
		}
		public void setState(ImageIcon state) {
			super.setIcon(state);
		}
		public void dashR() {
			pX += 7;
			super.setBounds(pX, pY, 192, 192);
		}
		public void dashL() {
			pX -= 7;
			super.setBounds(pX, pY, 192, 192);
		}
		public void jump(int y) {
			this.pY -= y;
		}
		public void fall(int y) {
			this.pY += y;
		}
		public int getX() {
			return pX;
		}
		public int getY() {
			return pY;
		}
		public void setY(int y) {
			pY = y;
		}
		public void setX(int x) {
			pX = x;
		}
		public void hitL() {
			new Thread(new Runnable() {
				public void run() {
					int set = 7;
					while(set >= 0) {
						set --;
						pX += set;
						if((pX += set) > 1268) {
							pX = 1268;
							break;
						}
						try {
							Thread.sleep(10);
						} catch(InterruptedException e) {
							System.out.println("예외처리");
						}
					}
				}
			}).start();
		}
		public void hitR() {
			new Thread(new Runnable() {
				public void run() {
					int set = 7;
					while(set >= 0) {
						set --;
						pX -= set;
						if((pX -= set) < -60) {
							pX = -60;
							break;
						}
						try {
							Thread.sleep(10);
						} catch(InterruptedException e) {
							System.out.println("예외처리");
						}
					}
				}
			}).start();
		}
		public void hit() {
			HP--;
		}
	}
	// Monster 객체 클래스 Player와 마찬가지로 JLabel을 상속하고 x,y좌표 및 hp를 가짐, 동작에 따른 딜레이 변수도 존재함
	class Monster extends JLabel {
		int mX;
		int mY;

		int hp_M;

		int spawnLocate;

		boolean Right;
		boolean Left;

		boolean Attack_M;

		int hit_delay;

		int death_Delay;

		int attackDelay;

		Monster(int x, int y, int spawn, int HP) {
			mX = x;
			mY = y;

			hp_M = HP;

			spawnLocate = spawn;
			if(spawnLocate % 2 != 0) {
				Right = true;
				Left = false;
				super.setIcon(monster_walk_right_gif);
			}
			else {
				Right = false;
				Left = true;
				super.setIcon(monster_walk_left_gif);
			}
		}
		public void move() {
			if(Right) {
				mX += 2;
				super.setBounds(mX,mY,128,128);
			}
			else if(Left) {
				mX -= 2;
				super.setBounds(mX,mY,128,128);
			}
		}
		public void setState(ImageIcon state) {
			super.setIcon(state);
		}
		public void fall(int y) {
			this.mY += y;
		}
		public int getX() {
			return mX;
		}
		public int getY() {
			return mY;
		}
		public void setRight() {
			Right = true;
			Left = false;
		}
		public void setLeft() {
			Left = true;
			Right = false;
		}
		public void setY(int y) {
			mY = y;
		}
		public void hit() {
			hp_M --;
		}
		public int getHP() {
			return hp_M;
		}
		public void death() {
			if(Left)
				super.setIcon(monster_death_left_gif);
			else
				super.setIcon(monster_death_right_gif);

		}
		public void hit_L() {
			super.setIcon(monster_hit_left_gif);
			new Thread(new Runnable() {
				public void run() {
					int set = 15;
					while(set >= 0) {
						set --;
						mX += set;
						try {
							Thread.sleep(10);
						} catch(InterruptedException e) {
							System.out.println("예외처리");
						}
					}
				}
			}).start();
			if(Left)
				super.setIcon(monster_walk_left_gif);
			else
				super.setIcon(monster_walk_right_gif);
		}

		public void hit_R() {
			super.setIcon(monster_hit_right_gif);
			new Thread(new Runnable() {
				public void run() {
					int set = 15;
					while(set >= 0) {
						set --;
						mX -= set;
						try {
							Thread.sleep(10);
						} catch(InterruptedException e) {
							System.out.println("예외처리");
						}
					}
				}
			}).start();
			if(Left)
				super.setIcon(monster_walk_left_gif);
			else
				super.setIcon(monster_walk_right_gif);
		}

	}

	// Player의 원거리 공격 객체 클래스, x,y좌표를 가지며 지정된 위치에 구 모양의 공격을 그림
	class Attack {
		int x;
		int y;
		Attack(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public void moveR() {
			this.x += 7;
		}
		public void moveL() {
			this.x -= 7;
		}
		public int getX() {
			return this.x;
		}
		public int getY() {
			return this.y;
		}
		public void drawA(Graphics g) {
			g.setColor(Color.white);
			g.fillOval(x, y, 10, 10);
		}
	}
}


