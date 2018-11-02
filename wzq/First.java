package wzq;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class First extends JFrame {

	private JPanel contentPane;
	First firstTHIS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					First frame = new First();
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
	public First() {
		firstTHIS = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100+500, 100+300, 308, 372);
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
		};;
		panel.setBounds(0, 0, 290, 325);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("登陆");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstTHIS.dispose();
				Login login = new Login();
				login.setVisible(true);
			}
		});
		btnNewButton.setBounds(76, 130, 148, 27);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("注册");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstTHIS.dispose();
				Register register = new Register();
				register.setVisible(true);
				
			}
		});
		btnNewButton_1.setBounds(76, 175, 148, 27);
		panel.add(btnNewButton_1);
	}
}
