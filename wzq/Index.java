package wzq;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;

public class Index extends JFrame {
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
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
	public Index() {
		
		Index THIS = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 373, 525);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel(){		
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//  /img/HomeImg.jpg 是存放在你正在编写的项目的bin文件夹下的img文件夹下的一个图片
				ImageIcon icon=new ImageIcon(getClass().getResource("index.jpg"));

				Image img=icon.getImage();
				g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
			}
		};

		panel.setBounds(0, 0, 360, 480);

		JPanel jPanel = new JPanel() ;
		getContentPane().add(panel);
		panel.setLayout(null);
		

		JButton but1 = new JButton("离线人人对战");
		but1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				THIS.dispose();
				Qpbase qpbase = new Qpbase();
				qpbase.setVisible(true);
				
			}
		});
		but1.setBounds(106, 278, 130, 27);
		panel.add(but1);
		
		JButton but2 = new JButton("在线人人对战");
		but2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				QpbaseP2P qpbaseP2P = new QpbaseP2P();
				qpbaseP2P.setVisible(true);
			}
		});
		but2.setBounds(106, 226, 130, 27);
		panel.add(but2);
		
		JButton but3 = new JButton("人机对战");
		but3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				THIS.dispose();
				QpbaseP2M qpbaseP2M = new QpbaseP2M();
				qpbaseP2M.setVisible(true);
			}
		});
		but3.setBounds(106, 176, 130, 27);
		panel.add(but3);
		
		JButton button = new JButton("复盘");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReLook reLook = new ReLook();
				reLook.setVisible(true);
			}
		});
		button.setBounds(106, 332, 130, 27);
		panel.add(button);
	}
}
