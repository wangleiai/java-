package wzq;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.naming.directory.InvalidAttributeIdentifierException;
import javax.sound.midi.Soundbank;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.css.ElementCSSInlineStyle;

import com.mysql.jdbc.exceptions.jdbc4.MySQLInvalidAuthorizationSpecException;
import com.mysql.jdbc.log.Log;

import net.sf.json.JSONObject;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.DataBufferInt;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Thread.State;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class QpbaseP2P extends JFrame {

	public String enemyLuozi = "";
	JTextArea txtrn;
	// 表示匹配上队友才能落子
	public static boolean canDown = false;

	// socket网络请求
	Socket socket;
	DataInputStream dataInputStream;
	DataOutputStream dataOutputStream;

	// my information
	public static String myId = "";
	public static boolean isFirst = false;
	public static String enemy = "";
	public static boolean canLuozi = false;
	// public
	// public static boolean

	Client client;

	JLabel label_3;

	JLabel label_4;

	JLabel label;

	JLabel lblNewLabel_2;

	private JPanel contentPane;

	public JLabel lblNewLabel;

	// private int state = 4;

	// 表示是否可以下棋
	public static int qizi;

	//
	Thread thread;
	JLabel lblNewLabel_4;
	public static JPanel panel;

	public static int[][] arr = new int[16][16];
	// 棋盘 白1 黑2
	int pan[][] = new int[16][16];

	// 该谁下棋，黑表示黑手下棋， 白表示白手下棋
	public static String qishou = new String("黑");

	// 连个列表，用来存下棋记录
	public static ArrayList<String> recordXY = new ArrayList<String>();
	public static ArrayList<String> recordUser = new ArrayList<String>();
	public static ArrayList<String> recordClickXY = new ArrayList<String>();
	public ArrayList<String> recordReallyXY = new ArrayList<String>();

	// 要传向人机的方向
	public String record = new String("");

	// 文本域，这里显示下载记录
	JTextArea textArea;

	// 拿到棋盘的画笔
	Graphics qPanGraphics;

	// 拿到棋盘的this
	JPanel THIS;

	RecordJdbc recordJdbc = new RecordJdbc();

	public static JLabel lblUser = new JLabel("名称: ");
	// lblUser = new JLabel("名称: " + Login.user.getEnemy());

	Map<Integer, String> numToString = new HashMap<Integer, String>();
	Map<String, Integer> stringToNum = new HashMap<String, Integer>();

	int user1Win = 0;
	int user1Fail = 0;

	int user2Win = 0;
	int user2Fail = 0;

	// 定义用户1和 用户2
	static String user1 = new String("我方: ");
	static String user2 = new String("对方: ");

	//
	Timer timer;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QpbaseP2P frame = new QpbaseP2P();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public boolean getConnection() {
		try {
			socket = new Socket("localhost", 8888);
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Create the frame.
	 */
	public QpbaseP2P() {
		user1 += Login.user.getName();
		numToString.put(0, "a");
		numToString.put(1, "b");
		numToString.put(2, "c");
		numToString.put(3, "d");
		numToString.put(4, "e");
		numToString.put(5, "f");
		numToString.put(6, "g");
		numToString.put(7, "h");
		numToString.put(8, "i");
		numToString.put(9, "j");
		numToString.put(10, "k");
		numToString.put(11, "l");
		numToString.put(12, "m");
		numToString.put(13, "n");
		numToString.put(14, "o");
		numToString.put(15, "p");

		stringToNum.put("a", 0);
		stringToNum.put("b", 1);
		stringToNum.put("c", 2);
		stringToNum.put("d", 3);
		stringToNum.put("e", 4);
		stringToNum.put("f", 5);
		stringToNum.put("g", 6);
		stringToNum.put("h", 7);
		stringToNum.put("i", 8);
		stringToNum.put("j", 9);
		stringToNum.put("k", 10);
		stringToNum.put("l", 11);
		stringToNum.put("m", 12);
		stringToNum.put("n", 13);
		stringToNum.put("o", 14);
		stringToNum.put("p", 15);

		setResizable(false);

		setTitle("超凡五子棋");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				// 可能在这里加上重绘棋子就不会出现棋子丢失的情况了

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

					/*
					 * 如果可以落子，就给对方发消息
					 */
					if (canLuozi) {
						if ((pan[temXX][temYY] != 2) && (pan[temXX][temYY] != 1)) {
							canLuozi = false;
							// pan[temXX][temYY] = ;
							recordUser.add(qishou);
							recordXY.add(temXX + " " + temYY);
							recordClickXY.add(x + " " + y);
							textArea.append(qishou + ": " + "(" + temXX + "," + temYY + ")" + '\n');
							record += numToString.get(temXX);
							record += numToString.get(temYY);

							if (qishou.equals("黑")) {
								Graphics graphics = panel.getGraphics();
								graphics.setColor(new Color(0, 0, 0));
								graphics.fillOval(temXX * 30 + 10 - 15, temYY * 30 + 10 - 15, 30, 30);
								lblNewLabel.setText("白手下");
								pan[temXX][temYY] = 2;
								arr[temXX][temYY] = 2;
								// 发送完判断输赢，如果赢了就给对方发送消息
								boolean j = judge(temXX, temYY);
								if (j) {
									JOptionPane.showMessageDialog(null, "黑棋赢");
									canLuozi = false;
									int win;
									if (isFirst) {
										 user1Win += 1;
//										 user1Fail = 0;

//										 user2Win = 0;
										user2Fail += 1;
//										System.out.println("pp:" + enemy);
										recordJdbc.insert(record, Login.user.getName(), 1, enemy);
									} else {
//										 user1Win = 0;
										 user1Fail += 1;

										 user2Win += 1;
//										user2Fail = 0;
										System.out.println("pp:" + enemy);
										recordJdbc.insert(record, Login.user.getName(), 0, enemy);
									}

									System.out.println("棋子：" + record);
								}
								qishou = "白";
							} else {
								Graphics graphics = panel.getGraphics();
								graphics.setColor(new Color(255, 255, 255));
								graphics.fillOval(temXX * 30 + 10 - 15, temYY * 30 + 10 - 15, 30, 30);
								lblNewLabel.setText("黑手下");
								pan[temXX][temYY] = 1;
								arr[temXX][temYY] = 1;
								// 发送完判断输赢，如果赢了就给对方发送消息
								boolean j = judge(temXX, temYY);
								if (j) {
									JOptionPane.showMessageDialog(null, "白棋赢");
									canLuozi = false;
									System.out.println("棋子：" + record);
									if (!isFirst) {
										user1Win += 1;
										 user1Fail = 0;

										 user2Win = 0;
										user2Fail += 1;
										System.out.println("pp:" + enemy);
										recordJdbc.insert(record, Login.user.getName(), 1, enemy);
									} else {
										user1Win = 0;
										user1Fail += 1;

										user2Win += 1;
										user2Fail = 0;
										System.out.println("pp:" + enemy);
										recordJdbc.insert(record, Login.user.getName(), 0, enemy);
									}
								}
								qishou = "黑";
							}
							lblNewLabel_2.setText("胜："+ user1Win);
							label.setText("负：" + user1Fail);
							label_3.setText("胜："+user2Win);
							label_4.setText("负: " + user2Fail);
							String point = temXX + " " + temYY;
							// JOptionPane.showMessageDialog(null, "马上发送point： " + point);
							client.sendXY(myId, point);

						}
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
				// TODO Auto-generated method stub
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

		txtrn = new JTextArea();
		JScrollPane jScrollPane2 = new JScrollPane(txtrn);
		jScrollPane2.setBounds(0, 0, 153, 179);
		// textArea_2.setBounds(0, 0, 153, 213);
		jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel_5.add(jScrollPane2);

		textField = new JTextField();
		textField.setBounds(0, 192, 98, 24);
		panel_5.add(textField);
		textField.setColumns(10);

		JButton btnNewButton_1 = new JButton("ok");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String message = textField.getText();
				textField.setText("");
				if (message != null) {
					txtrn.append("我方：" + message + "\n");
					client.sendChatMessage(myId, message);
				}

			}
		});
		btnNewButton_1.setBounds(104, 192, 49, 27);
		panel_5.add(btnNewButton_1);

		JPanel panel_6 = new JPanel();
		panel_6.setBounds(0, 492, 815, 68);
		contentPane.add(panel_6);
		panel_6.setLayout(null);

		/*
		 * 悔棋操作
		 */

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
		JButton btnNewButton = new JButton("悔棋");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(523, 15, 92, 42);
		panel_6.add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (recordUser.isEmpty()) {
					JOptionPane.showMessageDialog(null, "盘上无落子，无法请求悔棋");
				} else {
					client.regret(myId);
				}

			}
		});

		btnNewButton.setFont(new Font("SAN_SERIF", Font.BOLD, 24));
		btnNewButton.setBackground(Color.CYAN);

		JPanel panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBackground(Color.GRAY);
		panel_9.setBounds(15, 8, 611, 55);
		panel_6.add(panel_9);

		lblNewLabel_4 = new JLabel("00：00");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		lblNewLabel_4.setBackground(Color.WHITE);
		lblNewLabel_4.setBounds(26, 7, 132, 42);
		panel_9.add(lblNewLabel_4);

		JButton button_1 = new JButton("认输");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("认输");

				// 计时器的线程关闭
				// state = 0;
				JOptionPane.showMessageDialog(null, "您已认输");
				client.giveIn(myId);
				close();
				recordUser.clear();
				recordXY.clear();

				// System.out.println("change to state ");
				// state = 0;
			}
		});

		button_1.setFont(new Font("Dialog", Font.BOLD, 24));
		button_1.setBackground(Color.CYAN);
		button_1.setBounds(400, 7, 101, 42);
		panel_9.add(button_1);

		JButton button_2 = new JButton("重开");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.restart(myId);
			}
		});
		button_2.setFont(new Font("Dialog", Font.BOLD, 24));
		button_2.setBackground(Color.CYAN);
		button_2.setBounds(295, 7, 101, 42);
		panel_9.add(button_2);

		JButton button_3 = new JButton("开始");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 先和服务器取得连接，然后在发送匹配玩家的消息
					boolean b = getConnection();
					//
					if (b) {
						String requsetMsg = dataInputStream.readUTF();
						JSONObject jsonObject = JSONObject.fromObject(requsetMsg);
						myId = jsonObject.get("msg").toString();
						client = new Client(socket);

						// 先初始化
						client.init(Login.username, myId);
						// 尝试匹配
						boolean pp = client.pp(myId);
						if (pp) {
							// 0是reset，1是start ,2是stop
							acceptMessage(); // 开启另一个线程
							// state = 1;
							begin();
						} else {
							/*
							 * 这里给服务端发送消息，改变自己的状态，使它下次可以继续匹配 *
							 */
							JOptionPane.showMessageDialog(null, "请重新匹配");
						}
					} else {
						JOptionPane.showMessageDialog(null, "请注意网络连接！1");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "请注意网络连接！2");
					e2.printStackTrace();
				}

			}
		});
		button_3.setFont(new Font("Dialog", Font.BOLD, 24));
		button_3.setBackground(Color.CYAN);
		button_3.setBounds(192, 7, 101, 42);
		panel_9.add(button_3);

		panel_7.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("胜：0");
		lblNewLabel_2.setBounds(14, 70, 39, 18);
		panel_7.add(lblNewLabel_2);

		label = new JLabel("负：0");
		label.setBounds(73, 70, 39, 18);
		panel_7.add(label);

		JLabel lblNewLabel_3 = new JLabel("名称: " + Login.user.getName());
		lblNewLabel_3.setBounds(14, 13, 98, 18);
		panel_7.add(lblNewLabel_3);

		JPanel panel_8 = new JPanel();
		panel_8.setBounds(0, 248, 140, 88);
		contentPane.add(panel_8);
		panel_8.setLayout(null);

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

	/*
	 * 判断输赢
	 */
	public static boolean judge(int x, int y) {
		// 白1， 黑2
		// 这个是不多余的？？？？？
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
				if (arr[a - i][b + i] == 2) {
					sum++;
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

	public void acceptMessage() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				String message = null;
				try {
					while ((message = dataInputStream.readUTF()) != null) {
						JSONObject jsonObject = JSONObject.fromObject(message);
						String id = jsonObject.get("id").toString();
						/*
						 * 这里将是一大堆的if else
						 */
						// System.out.println("id::aaaaaaaa " + id);
						if (id.equals("请求认输")) {
							// 0是reset，1是start ,2是stop
							// state = 0;// 改变状态
							System.out.println("对方认输");
							JOptionPane.showMessageDialog(null, "对方认输");
						} else if (id.equals("获取落子位置")) {
							canLuozi = true;
							String point = jsonObject.getString("point");
							/*
							 * 获取到落子后要直接下棋
							 */
							enemyLuozi = point;
							String string[] = point.split(" ");
							int tx = Integer.parseInt(string[0]);
							int ty = Integer.parseInt(string[1]);
							recordUser.add(qishou);
							recordXY.add(tx + " " + ty);
							textArea.append(qishou + ": " + "(" + tx + "," + ty + ")" + '\n');
							record += numToString.get(tx);
							record += numToString.get(ty);
							if (qishou.equals("黑")) {
								arr[tx][ty] = 2;
								pan[tx][ty] = 2;
								Graphics graphics = panel.getGraphics();
								graphics.setColor(new Color(0, 0, 0));
								graphics.fillOval(tx * 30 + 10 - 15, ty * 30 + 10 - 15, 30, 30);
								boolean b = judge(tx, ty);
								if (b) {
									JOptionPane.showMessageDialog(null, "黑手赢");
									canLuozi = false;
									if (isFirst) {
										user1Win += 1;
//										user1Fail += 0;

//										 user2Win += 0;
										user2Fail += 1;
										
										lblNewLabel_2.setText("胜："+ user1Win);
										label.setText("负：" + user1Fail);
										label_3.setText("胜："+user2Win);
										label_4.setText("负: " + user2Fail);
										
										System.out.println("pp:" + enemy);
										recordJdbc.insert(record, Login.user.getName(), 1, enemy);
									} else {
//										user1Win = 0;
										user1Fail += 1;

										user2Win += 1;
//										user2Fail = 0;
										lblNewLabel_2.setText("胜："+ user1Win);
										label.setText("负：" + user1Fail);
										label_3.setText("胜："+user2Win);
										label_4.setText("负: " + user2Fail);
										System.out.println("pp:" + enemy);
										recordJdbc.insert(record, Login.user.getName(), 0, enemy);
									}
									System.out.println("棋子：" + record);

								}
								lblNewLabel.setText("白手下");
								qishou = "白";
								// System.out.println("棋子：" + record);

							} else {
								arr[tx][ty] = 1;
								pan[tx][ty] = 1;
								Graphics graphics = panel.getGraphics();
								graphics.setColor(new Color(255, 255, 255));
								graphics.fillOval(tx * 30 + 10 - 15, ty * 30 + 10 - 15, 30, 30);
								boolean b = judge(tx, ty);
								if (b) {
									JOptionPane.showMessageDialog(null, "白手赢");
									if (!isFirst) {
										user1Win += 1;
//										user1Fail ;

//										user2Win += 1;
										user2Fail += 1;
										System.out.println("pp:" + enemy);
										lblNewLabel_2.setText("胜："+ user1Win);
										label.setText("负：" + user1Fail);
										label_3.setText("胜："+user2Win);
										label_4.setText("负: " + user2Fail);
										recordJdbc.insert(record, Login.user.getName(), 1, enemy);
									} else {
//										user1Win = 0;
										 user1Fail += 1;

										 user2Win += 1;
//										user2Fail = 0;
										 lblNewLabel_2.setText("胜："+ user1Win);
											label.setText("负：" + user1Fail);
											label_3.setText("胜："+user2Win);
											label_4.setText("负: " + user2Fail);
										System.out.println("pp:" + enemy);
										recordJdbc.insert(record, Login.user.getName(), 0, enemy);
									}
									canLuozi = false;
									System.out.println("棋子：" + record);

								}
								lblNewLabel.setText("黑手下");
								qishou = "黑";
							}
							// JOptionPane.showMessageDialog(null, "客户端落子位置： " + point);

						} else if (id.equals("请求悔棋结果")) {
							String result = jsonObject.getString("result");
							/*
							 * 拿到悔棋结果
							 */

							System.out.println("客户端：拿到悔棋结果 " + result);
							if (result.equals("yes")) {
								JOptionPane.showMessageDialog(null, "对方同意了您的悔棋请求");
								/*
								 * 这里写悔棋重绘
								 */
								record = record.substring(0, record.length() - 2);

								String string = recordXY.get(recordXY.size() - 1);
								String[] string2 = string.split(" ");
								int topx = Integer.parseInt(string2[0]);
								int topy = Integer.parseInt(string2[1]);
								pan[topx][topy] = 0;
								arr[topx][topy] = 0;

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
								int len = recordUser.size();
								for (int i1 = 0; i1 < len - 1; i1++) {
									qString = recordUser.get(i1);
									xyString = recordXY.get(i1).split(" ");
									int a, b;

									a = (int) Double.parseDouble(xyString[0]);
									b = (int) Double.parseDouble(xyString[1]);
									if (qString == "白") {

										// System.out.println("重画白");
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

								// recordClickXY.remove(recordClickXY.size() - 1);
								recordUser.remove(recordUser.size() - 1);
								recordXY.remove(recordXY.size() - 1);
								canLuozi = true;
							} else {
								JOptionPane.showMessageDialog(null, "对方拒绝了您的悔棋请求");
							}
							// JOptionPane.showMessageDialog(null, "result " + result);
							// JOptionPane.showMessageDialog(null, "请求悔棋结束");

						} else if (id.equals("重新开始")) {

							int res = JOptionPane.showConfirmDialog(null, "对方请求重新开始", "悔棋", JOptionPane.YES_NO_OPTION);
							if (res == JOptionPane.YES_OPTION) {
								client.restartResult(myId, "yes");
								// JOptionPane.showMessageDialog(null, "您同意了对方的重新开始的请求");
								/*
								 * 123321132321
								 **/
								for (int i = 0; i < 16; i++) {
									for (int j = 0; j < 16; j++) {
										arr[i][j] = 0;
										pan[i][j] = 0;
									}
									// pan = arr;
								}

								System.out.println("棋盘重置完成");
								// record = "";
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
//								if (isFirst)
//									qishou = new String("黑");
//								// lblNewLabel.setText("");
//								else {
//									qishou = new String("白");
//								}
								qishou = "黑";
								lblNewLabel.setText(qishou + "手下");
								canLuozi = true;
								// 连个列表，用来存下棋记录
								// recordClickXY.clear();
								recordUser.clear();
								recordXY.clear();
								System.out.println("change to state ");
								close();
								begin();
								if(isFirst) {
									canLuozi = true;
								}
								else {
									canLuozi = false;
								}
								textArea.append("重新开始");
							} else {
								client.restartResult(myId, "no");
							}

						} else if (id.equals("重新开始结果")) {
							String result = jsonObject.getString("result");
							if (result.equals("yes")) {
								textArea.append("重新开始");
								JOptionPane.showMessageDialog(null, "对方同意了您的重新开始的请求");
								/*
								 * 123321132321
								 **/
								for (int i = 0; i < 16; i++) {
									for (int j = 0; j < 16; j++) {
										arr[i][j] = 0;
										pan[i][j] = 0;
									}
									// pan = arr;
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
								if (isFirst)
									qishou = new String("黑");
								// lblNewLabel.setText("");
								else {
									qishou = new String("白");
								}
								lblNewLabel.setText(qishou + "手下");
								canLuozi = true;
								// 连个列表，用来存下棋记录
								// recordClickXY.clear();
								recordUser.clear();
								recordXY.clear();
								// System.out.println("change to state ");
								begin();

								// textArea.cl

							} else {
								JOptionPane.showMessageDialog(null, "对方拒绝了您重新开始的请求");
								JOptionPane.showMessageDialog(null, "您需要重新匹配玩家");
							}
						} else if (id.equals("请求悔棋")) {
							/*
							 * 客户端拿到对方要悔棋的请求,并且发送是否同意悔棋操作
							 */
							int res = JOptionPane.showConfirmDialog(null, "对方请求悔棋", "悔棋", JOptionPane.YES_NO_OPTION);
							if (res == JOptionPane.YES_OPTION) {
								record = record.substring(0, record.length() - 2);
								// System.out.println("yes");
								String string = recordXY.get(recordXY.size() - 1);
								String[] string2 = string.split(" ");
								int topx = Integer.parseInt(string2[0]);
								int topy = Integer.parseInt(string2[1]);
								pan[topx][topy] = 0;
								arr[topx][topy] = 0;

								if (qishou.equals("白"))
									textArea.append("黑" + " 悔棋！！" + "\n");
								else {
									textArea.append("白" + " 悔棋！！" + "\n");
								}
								canLuozi = false;
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
								int len = recordUser.size();
								for (int i1 = 0; i1 < len - 1; i1++) {
									qString = recordUser.get(i1);
									xyString = recordXY.get(i1).split(" ");
									int a, b;

									a = (int) Double.parseDouble(xyString[0]);
									b = (int) Double.parseDouble(xyString[1]);
									if (qString == "白") {

										// System.out.println("重画白");
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

								// recordClickXY.remove(recordClickXY.size() - 1);
								recordUser.remove(recordUser.size() - 1);
								recordXY.remove(recordXY.size() - 1);
								client.sendWH(myId, "yes");
							} else {
								// System.out.println("no");
								client.sendWH(myId, "no");
							}

						} else if (id.equals("聊天消息")) {
							String message1 = jsonObject.getString("message");
							txtrn.append("对方：" + message1 + "\n");
							// JOptionPane.showMessageDialog(null, "客户端：" + message1);
						} else {
							JOptionPane.showMessageDialog(null, "id 错误");
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					JOptionPane.showMessageDialog(null, "acceptMessage错误");
				}
			}
		};
		thread.start();
	}
}
