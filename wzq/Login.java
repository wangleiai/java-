package wzq;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.log.Log;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField passwordField;
	
	public static User user = new User();
	
	static String username = "0";
	
	Login THIS = this;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 400, 310, 357);
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
		panel.setBounds(0, 0, 292, 310);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("账号");
		lblNewLabel.setBounds(66, 107, 48, 18);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("密码");
		lblNewLabel_1.setBounds(66, 154, 48, 18);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("YES");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = nameField.getText();
				String password = passwordField.getText();
				if(name.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入用户名");
				}
				if(password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入密码");
				}
				
				// 这里jdbc编程验证用户

				User user = new User(0, name, password);

				if(WzqJdbc.select(user)) {
					username = name;
					Index index = new Index();
					index.setVisible(true);
					THIS.dispose();
				}else {
					JOptionPane.showMessageDialog(THIS, "用户名或密码错误");
				}
			}
		});
		btnNewButton.setBounds(119, 270, 76, 27);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("NO");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 这里可能加回退到初始界面
				THIS.dispose();
				First first = new First();
				first.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(202, 270, 76, 27);
		panel.add(btnNewButton_1);
		
		nameField = new JTextField();
		nameField.setBounds(123, 104, 86, 24);
		panel.add(nameField);
		nameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setBounds(123, 151, 86, 24);
		panel.add(passwordField);
		passwordField.setColumns(10);
	}
}
