package wzq;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register extends JFrame {

	private JPanel contentPane;
	private Register THIS = this;
	private JTextField name;
	private JTextField password_1;
	private JTextField password_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 400, 310, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel(){		
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//  /img/HomeImg.jpg 是存放在你正在编写的项目的bin文件夹下的img文件夹下的一个图片
				ImageIcon icon=new ImageIcon(getClass().getResource("index.jpg"));

				Image img=icon.getImage();
				g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
			}
		};
		panel.setBounds(0, 0, 292, 323);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("账号");
		label.setBounds(48, 119, 72, 18);
		panel.add(label);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(126, 119, 86, 24);
		panel.add(name);
		
		JLabel label_1 = new JLabel("密码");
		label_1.setBounds(48, 172, 72, 18);
		panel.add(label_1);
		
		password_1 = new JTextField();
		password_1.setColumns(10);
		password_1.setBounds(126, 172, 86, 24);
		panel.add(password_1);
		
		JLabel label_2 = new JLabel("确认密码");
		label_2.setBounds(48, 230, 72, 18);
		panel.add(label_2);
		
		password_2 = new JTextField();
		password_2.setColumns(10);
		password_2.setBounds(126, 230, 86, 24);
		panel.add(password_2);
		
		JButton button = new JButton("YES");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * 这里可能需要改
				 * */
				String temname = name.getText();
				if(temname.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入账号");
				}
				String password1 = password_1.getText();
				String password2 = password_2.getText();
				if(password1.isEmpty() || password2.isEmpty() || !password1.equals(password2)) {
					JOptionPane.showMessageDialog(null, "您没有输入密码或两次输入的密码不同");
				}
				User user = new User(3, temname, password1);
				try {
					if(WzqJdbc.insert(user)) {
						THIS.dispose();
						Index index = new Index();
						index.setVisible(true);;
					}
					else {
						JOptionPane.showMessageDialog(null, "用户名已中注册");
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "请检测网络");
				}
			}
		});
		button.setBounds(111, 296, 67, 27);
		panel.add(button);
		
		JButton button_1 = new JButton("NO");
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				THIS.dispose();
				First first = new First();
				first.setVisible(true);
				
			}
		});
		button_1.setBounds(192, 296, 67, 27);
		panel.add(button_1);
	}
}
