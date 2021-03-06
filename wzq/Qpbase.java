package wzq;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class Qpbase extends JFrame {
	JLabel label_3;
	JLabel lblNewLabel_4;
	JLabel label_4;
	Thread thread;
	JLabel label;

	JLabel lblNewLabel_2;

	private JPanel contentPane;

	public JLabel lblNewLabel;

	// 表示是否可以下棋
	public static int qizi;

	public  JPanel panel;

	public static int[][] arr = new int[16][16];

	// 该谁下棋，黑表示黑手下棋， 白表示白手下棋
	public static String qishou = new String("黑");

	// 连个列表，用来存下棋记录
	public static ArrayList<String> recordXY = new ArrayList<String>();
	public static ArrayList<String> recordUser = new ArrayList<String>();
	public static ArrayList<String> recordClickXY = new ArrayList<String>();

	// 文本域，这里显示下载记录
	JTextArea textArea;

	// 拿到棋盘的画笔
	Graphics qPanGraphics;

	// 拿到棋盘的this
	JPanel THIS;

	// 棋盘 白1 黑2
	int pan[][] = new int[16][16];

	// user1 的胜负情况
	int user1Win = 0;
	int user1Fail = 0;

	// user2的胜负情况
	int user2Win = 0;
	int user2Fail = 0;

	// timer
	private Timer timer;
	/*
	 * 
	 * 棋盘状态 0是reset，1是start ,2是stop
	 */
	public int state = 4;

	// 定义用户1和 用户2
	String user1 = new String("user1: ");
	String user2 = new String("user2: ");

	String timeString = "";
	TimerTask task2;
	long time = System.currentTimeMillis();
	// long time = System.currentTimeMillis();
	// TimerTask task = new TimerTask() {
	// @Override
	// public void run() {
	// String str = String.format("%1$tM:%1$tS:%1$1tL", System.currentTimeMillis() -
	// time);
	// System.out.println((str.substring(0, 7)));
	// }
	// };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Qpbase frame = new Qpbase();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Qpbase() {

		setResizable(false);

		setTitle("超凡五子棋");
//		setDefaultCloseOperation(JFrame.);
		setBounds(300 + 50, 300, 821, 607);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Color color = new Color(24, 103, 143);
		contentPane.setBackground(color);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				// 是存放在你正在编写的项目的bin文件夹下的img文件夹下的一个图片
				ImageIcon icon = new ImageIcon(getClass().getResource("boy.png"));

				Image img = icon.getImage();
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}

			@Override
			public void repaint() {
				// TODO Auto-generated method stub
				super.repaint();
			}
		};
		panel_1.setBounds(0, 13, 140, 139);
		contentPane.add(panel_1);

		JPanel panel_2 = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				ImageIcon icon = new ImageIcon(getClass().getResource("girl.png"));

				Image img = icon.getImage();
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		panel_2.setBounds(0, 340, 140, 139);
		contentPane.add(panel_2);

		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				qPanGraphics = g;

				THIS = this;

				Color color2 = new Color(217, 184, 159);
				g.setColor(color2);
				g.fillRect(0, 0, this.getHeight(), this.getWidth());

				g.setColor(new Color(0, 0, 0));
				int i = 10;
				for (i = 10; i < this.getHeight(); i += 30) {
					// 画横线
					g.drawLine(10, i, 460, i);
					// 画竖线
					g.drawLine(i, 10, i, 460);
				}

			}

			@Override
			public void repaint() {
				// TODO Auto-generated method stub
				super.repaint();
			}
		};

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				double x, y; // 鼠标点的位置
				x = arg0.getX();
				y = arg0.getY();

				// 确认鼠标点在了棋盘内
				if ((x >= 10 && x <= 460) && (y >= 10 && y <= 460)) {
					// System.out.println("鼠标点击： " + x + " " + y);

					// 得到x距离那个位置更近一些
					double temX = (x - 10) % 30;
					// 得到x大致在那个格子
					int temXX = (int) ((x - 10) / 30);
					if (temX >= 15) {
						temXX += 1;
					} else {
						temXX = temXX;
					}

					// 得到Y距离那个位置更近一些
					double temY = (y - 10) % 30;
					// 得到Y大致在那个格子
					int temYY = (int) ((y - 10) / 30);
					if (temY >= 15) {
						temYY += 1;
					} else {
						temYY = temYY;
					}

					// 只有在start开始状态下可以下棋
					if (state == 1) {
						System.out.println("start！！！！！！！！");
						if ((pan[temXX][temYY] != 2) && (pan[temXX][temYY] != 1)) {

							// 添加棋手到
							recordUser.add(qishou);
							// 添加棋盘x, y 记录
							recordXY.add(temXX + " " + temYY);
							// 添加下棋x, y 记录
							recordClickXY.add(x + " " + y);

							// 添加到下棋记录里面
							textArea.append(qishou + ": " + "(" + temXX + "," + temYY + ")" + '\n');

							boolean b = judge(temXX, temYY);
							if (b) {
								System.out.println(" WIn         ");
							}

							if (qishou == "白") {

								Graphics graphics = panel.getGraphics();
								graphics.setColor(new Color(255, 255, 255));
								graphics.fillOval(temXX * 30 + 10 - 15, temYY * 30 + 10 - 15, 30, 30);
								lblNewLabel.setText("黑手下");

								// 给棋盘赋值
								pan[temXX][temYY] = 1;

								// 一方赢了，把棋盘和队列等设置为空
								if (b) {

									user2Win += 1;
									label_3.setText("胜：" + user2Win);
									user1Fail += 1;
									label.setText("负：" + user1Fail);
									JOptionPane.showMessageDialog(null, qishou + "赢！！！", "",
											JOptionPane.WARNING_MESSAGE);

								}
								qishou = "黑";
							} else {

								Graphics graphics = panel.getGraphics();
								graphics.setColor(new Color(0, 0, 0));
								graphics.fillOval(temXX * 30 + 10 - 15, temYY * 30 + 10 - 15, 30, 30);
								lblNewLabel.setText("白手下");
								pan[temXX][temYY] = 2;
								/*
								 * 
								 * */
								if (b) {

									user1Win += 1;
									user2Fail += 1;
									lblNewLabel_2.setText("胜：" + user1Win);
									label_4.setText("负：" + user2Fail);
									JOptionPane.showMessageDialog(null, qishou + "赢！！！", "",
											JOptionPane.WARNING_MESSAGE);
								}
								qishou = "白";
							}

						}
						else {
							// 否则，棋手
							System.out.println("棋手： "+qishou);
						}

					}
					// 这里是reset是需要重置所有的东西
					// reset 会将本棋盘清空
					else if (state == 0) {
						System.out.println("*****************reset****************** ");

					}
					// 这里是暂停啥也不用管
					else if (state == 2) {
						// JOptionPane.
						JOptionPane.showMessageDialog(null, "  暂停禁止下棋！！！", "", JOptionPane.WARNING_MESSAGE);

					}
					// 这里是还未开始前, 啥也不用管
					else {

					}

				}

			}
		});
		panel.setBounds(154, 13, 466, 466);
		contentPane.add(panel);
		panel.setLayout(null);

		/*
		 * 这里要写下棋的痕迹
		 */
		JPanel panel_3 = new JPanel() {
			@Override
			public void setBackground(Color bg) {
				Color bColor = new Color(177, 177, 177);
				super.setBackground(bColor);
			}
		};
		panel_3.setBounds(634, 0, 181, 495);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(14, 13, 153, 232);
		panel_3.add(panel_4);
		panel_4.setLayout(null);

		textArea = new JTextArea();
		JScrollPane jScrollPane = new JScrollPane(textArea);
		jScrollPane.setBounds(0, 0, 153, 232);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		panel_4.add(jScrollPane);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(14, 269, 153, 213);
		panel_3.add(panel_5);
		panel_5.setLayout(null);

		JTextArea textArea_2 = new JTextArea();
		JScrollPane jScrollPane2 = new JScrollPane(textArea_2);
		jScrollPane2.setBounds(0, 0, 153, 179);
		// textArea_2.setBounds(0, 0, 153, 213);
		jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel_5.add(jScrollPane2);

		JPanel panel_6 = new JPanel();
		panel_6.setBounds(0, 492, 815, 68);
		contentPane.add(panel_6);
		panel_6.setLayout(null);

		lblNewLabel = new JLabel("黑手下");
		lblNewLabel.setBounds(640, 8, 145, 55);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 38));
		panel_6.add(lblNewLabel);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(0, 156, 140, 88);
		contentPane.add(panel_7);
		panel_7.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("棋手：黑手");
		lblNewLabel_1.setBounds(14, 38, 98, 29);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 25));

		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.GRAY);
		panel_9.setBounds(14, 8, 611, 55);
		panel_6.add(panel_9);
		panel_9.setLayout(null);

		// 悔棋
		JButton btnNewButton = new JButton("悔棋");
		btnNewButton.setBounds(505, 7, 101, 42);
		panel_9.add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (recordClickXY.isEmpty()) {
					JOptionPane.showMessageDialog(null, "棋盘上无落子", "", JOptionPane.WARNING_MESSAGE);
				}

				if (!recordClickXY.isEmpty()) {

					// 将棋盘对应位置改可放置 即改为0
					String string = recordXY.get(recordXY.size() - 1);
					String[] string2 = string.split(" ");
					int topx = Integer.parseInt(string2[0]);
					int topy = Integer.parseInt(string2[1]);
					pan[topx][topy] = 0;

					if (qishou.equals("白"))
						textArea.append("黑" + " 悔棋！！" + "\n");
					else {
						textArea.append("白" + " 悔棋！！" + "\n");
					}

					Graphics g = panel.getGraphics();
					Color color2 = new Color(217, 184, 159);
					g.setColor(color2);
					g.fillRect(0, 0, panel.getHeight(), panel.getWidth());

					g.setColor(new Color(0, 0, 0));
					int i = 10;
					// 画棋盘
					for (i = 10; i < panel.getHeight(); i += 30) {
						// 画横线
						g.drawLine(10, i, 460, i);
						// 画竖线
						g.drawLine(i, 10, i, 460);
					}

					System.out.println(" 悔棋重画");

					String qString;
					String[] xyString;

					// 悔棋后把之前图片画上去
					int len = recordClickXY.size();
					for (int i1 = 0; i1 < len - 1; i1++) {
						qString = recordUser.get(i1);
						xyString = recordXY.get(i1).split(" ");
						int a, b;

						a = (int) Double.parseDouble(xyString[0]);
						b = (int) Double.parseDouble(xyString[1]);
						if (qString == "白") {

							System.out.println("重画白");
							g.setColor(new Color(255, 255, 255));
							g.fillOval(a * 30 + 10 - 15, b * 30 + 10 - 15, 30, 30);
							// g.fillOval(300,300,10,10);
							lblNewLabel.setText("黑手下");
							qishou = "黑";

						} else {

							g.setColor(new Color(0, 0, 0));
							g.fillOval(a * 30 + 10 - 15, b * 30 + 10 - 15, 30, 30);
							lblNewLabel.setText("白手下");
							qishou = "白";

						}
					}

					recordClickXY.remove(recordClickXY.size() - 1);
					recordUser.remove(recordUser.size() - 1);
					recordXY.remove(recordXY.size() - 1);
				}

			}
		});

		btnNewButton.setFont(new Font("SAN_SERIF", Font.BOLD, 24));
		btnNewButton.setBackground(Color.CYAN);

		lblNewLabel_4 = new JLabel("00：00");
		lblNewLabel_4.setBackground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		lblNewLabel_4.setBounds(26, 7, 132, 42);
		panel_9.add(lblNewLabel_4);

		JButton button = new JButton("重置");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 改变状态 0是reset，1是start ,2是stop
				state = 0;
				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 16; j++)
						arr[i][j] = 0;
					    pan = arr;
				}
				
				System.out.println("棋盘重置完成");

				// 线程关闭
				close();

				Graphics g = panel.getGraphics();
				Color color2 = new Color(217, 184, 159);
				g.setColor(color2);
				g.fillRect(0, 0, panel.getHeight(), panel.getWidth());

				g.setColor(new Color(0, 0, 0));
				int i = 10;
				// 画棋盘
				for (i = 10; i < panel.getHeight(); i += 30) {
					// 画横线
					g.drawLine(10, i, 460, i);
					// 画竖线
					g.drawLine(i, 10, i, 460);
				}

				// 该谁下棋，黑表示黑手下棋， 白表示白手下棋
				qishou = new String("黑");

				// 连个列表，用来存下棋记录
				recordClickXY.clear();
				recordUser.clear();
				recordXY.clear();
				System.out.println("change to state ");

			}
		});
		button.setFont(new Font("Dialog", Font.BOLD, 24));
		button.setBackground(Color.CYAN);
		button.setBounds(400, 7, 101, 42);
		panel_9.add(button);

		JButton button_1 = new JButton("暂停");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 改变状态
				// 0是reset，1是start ,2是stop
				state = 2;
			}
		});
		button_1.setFont(new Font("Dialog", Font.BOLD, 24));
		button_1.setBackground(Color.CYAN);
		button_1.setBounds(295, 7, 101, 42);
		panel_9.add(button_1);

		JButton button_2 = new JButton("开始");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 0是reset，1是start ,2是stop
				state = 1;
				begin();

			}
		});
		button_2.setFont(new Font("Dialog", Font.BOLD, 24));
		button_2.setBackground(Color.CYAN);
		button_2.setBounds(192, 7, 101, 42);
		panel_9.add(button_2);

		panel_7.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("胜：0");
		lblNewLabel_2.setBounds(14, 70, 39, 18);
		panel_7.add(lblNewLabel_2);

		label = new JLabel("负：0");
		label.setBounds(73, 70, 39, 18);
		panel_7.add(label);

		JLabel lblNewLabel_3 = new JLabel("名称: user1");
		lblNewLabel_3.setBounds(14, 13, 98, 18);
		panel_7.add(lblNewLabel_3);

		JPanel panel_8 = new JPanel();
		panel_8.setBounds(0, 248, 140, 88);
		contentPane.add(panel_8);
		panel_8.setLayout(null);

		JLabel lblUser = new JLabel("名称: user2");
		lblUser.setBounds(14, 13, 98, 18);
		panel_8.add(lblUser);

		JLabel label_2 = new JLabel("棋手：白手");
		label_2.setBounds(14, 38, 98, 29);
		panel_8.add(label_2);

		label_3 = new JLabel("胜：0");
		label_3.setBounds(14, 70, 39, 18);
		panel_8.add(label_3);

		label_4 = new JLabel("负：0");
		label_4.setBounds(73, 70, 39, 18);
		panel_8.add(label_4);
	}

	public static boolean judge(int x, int y) {
		// 白1， 黑2
		if (qishou.equals("黑")) {
			arr[x][y] = 2;
			System.out.println(x + " " + y + " " + qishou + "  " + arr[x][y]);

		} else {
			arr[x][y] = 1;
		}

		/*
		 * 从4个方向判断是否输赢,横向，竖向， 正对角线，负对角线
		 */

		// 横向
		int sum = 0;
		int max = 0;
		int temx = x - 5;
		int temy = y;
		if (temx < 0) {
			temx = 0;
		}
		for (int i = 0; i < 11; i++) {
			// 防止超出棋盘
			if (temx + i >= 16) {
				break;
			}

			// 连续黑则sum++， 否则赋值0
			if (qishou.equals("黑")) {
				if (arr[temx + i][temy] == 2) {
					sum += 1;
					if (sum == 5)
						return true;
				} else {
					sum = 0;
				}

			} else {
				// 连续白则sum++，否则就赋值0
				if (qishou.equals("白")) {
					if (arr[temx + i][temy] == 1) {
						sum += 1;
						// 如果能赢，就返回true
						if (sum == 5) {
							return true;
						}
					} else {
						sum = 0;
					}
				}
			}
		}

		// 竖向
		sum = 0;
		max = 0;
		temx = x;
		temy = y - 5;
		if (temy < 0) {
			temy = 0;
		}
		for (int i = 0; i < 11; i++) {
			// 防止超出棋盘
			if (temy + i >= 16) {
				break;
			}

			if (qishou.equals("黑")) {
				if (arr[temx][temy + i] == 2) {
					sum += 1;
					if (sum == 5)
						return true;
				} else {
					sum = 0;
				}

			} else {
				if (qishou.equals("白")) {
					if (arr[temx][temy + i] == 1) {
						sum += 1;
						if (sum == 5) {
							return true;
						}
					} else {
						sum = 0;
					}
				}
			}
		}

		// 正对角线
		sum = 0;
		max = 0;
		for (int i = 0; i <= 5; i++) {
			temx = x - i;
			temy = y - i;
			if (temx == 0 || temy == 0)
				break;
		}

		for (int i = 0; i < 11; i++) {
			// 防止超出棋盘
			if (temy + i >= 16) {
				break;
			}
			if (temx + i >= 16) {
				break;
			}

			if (qishou.equals("黑")) {
				if (arr[temx + i][temy + i] == 2) {
					sum += 1;
					if (sum == 5)
						return true;
				} else {
					sum = 0;
				}

			} else {
				if (qishou.equals("白")) {
					if (arr[temx + i][temy + i] == 1) {
						sum += 1;
						if (sum == 5) {
							return true;
						}
					} else {
						sum = 0;
					}
				}
			}
		}

		// 负对角线
		sum = 0;
		if (qishou.equals("黑")) {
			int a = 0, b = 0;

			for (int i = 0; i <= 5; i++) {
				a = x + i;
				b = y - i;
				if (a == 15 || b == 0)
					break;
			}

			for (int i = 0; i < 11; i++) {

				if (a - i < 0)
					break;
				if (b + i >= 16)
					break;
				System.out.println((a - i) + "   " + (b + i) + "   arr " + arr[a - i][b + i]);
				if (arr[a - i][b + i] == 2) {
					// System.out.println("黑");
					sum++;
					System.out.println(sum + "----------" + arr[a][b] + " -----------" + a + "---------" + b);
					if (sum == 5)
						return true;
				} else {
					sum = 0;
				}

			}

		} else {

			int a = 0, b = 0;

			for (int i = 0; i <= 5; i++) {
				a = x + i;
				b = y - i;
				if (a == 15 || b == 0)
					break;
			}

			for (int i = 0; i < 11; i++) {
				if (a - i < 0)
					break;
				if (b + i >= 16)
					break;

				if (arr[a - i][b + i] == 1) {
					sum++;
					if (sum == 5)
						return true;
				} else
					sum = 0;

			}
		}

		// 返回没赢
		return false;

	}

	public void begin() {
		thread = new Thread() {
			@Override
			public void run() {
				long time = System.currentTimeMillis();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						String str = String.format("%1$tM:%1$tS", System.currentTimeMillis() - time);
						lblNewLabel_4.setText(str);

					}
				};
				timer = new Timer();
				timer.schedule(task, 1, 100);
			}
		};
		thread.start();
	}

	public void close() {
		timer.cancel();
		lblNewLabel_4.setText("00: 00");

	}

}
