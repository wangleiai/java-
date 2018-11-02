package wzq;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Graphics;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class ReLookQiPan extends JFrame {

	private JPanel contentPane;
	int position=0;
	// 拿到棋盘的画笔
	Graphics qPanGraphics;

	// 拿到棋盘的this
	JPanel THIS;
	
	// 下棋路径
	public static String trace = "dedfeeeffeffhegfge";
	// 先手
	boolean isFirst = true;
	// 棋手
	String qishou = "黑";
	
    Map<Integer, String> numToString = new HashMap<Integer, String>();
    Map<String,Integer> stringToNum = new HashMap<String,Integer>();
    
    ArrayList<String> arrayList = new ArrayList<>();
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReLookQiPan frame = new ReLookQiPan();
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
	public ReLookQiPan() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("复盘");
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
	    
	    
		changeTiList();
		position = arrayList.size()-1;
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				qPanGraphics = g;
			
				THIS = this;

				Color color2 = new Color(217, 184, 159);
				g.setColor(color2);
				g.fillRect(0, 0, this.getHeight(), this.getWidth());

				g.setColor(new Color(0, 0, 0));
				int i =10;
				for(i = 10; i<this.getHeight(); i+=30) {
					// 画横线
					g.drawLine(10, i, 460, i);
					// 画竖线
					g.drawLine(i, 10, i, 460);
				}

			}
		
			
			public void repaint() {
			}
		};
		panel.setLayout(null);
		panel.setBounds(0, 0, 466, 466);
		contentPane.add(panel);
		
		JButton btnNewButton = new JButton("上一步");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(position == arrayList.size()-1){
					JOptionPane.showMessageDialog(null, "上一步不可执行");
				}
				else {
					Graphics g = panel.getGraphics();
					position +=1;
					Color color2 = new Color(217, 184, 159);
					g.setColor(color2);
					g.fillRect(0, 0, panel.getHeight(), panel.getWidth());

					g.setColor(new Color(0, 0, 0));
					int i =10;
					for(i = 10; i<panel.getHeight(); i+=30) {
						// 画横线
						g.drawLine(10, i, 460, i);
						// 画竖线
						g.drawLine(i, 10, i, 460);
					}
					int k=arrayList.size()-1;
					qishou = "黑";
					while (true) {
						if(k==position)
							break;
						else {
							
							String string = arrayList.get(k);
							String[]  psStrings = string.split(" ");
//							position = position -1;
							
							int x = Integer.parseInt(psStrings[0]);
							int y = Integer.parseInt(psStrings[1]);
							if(qishou.equals("黑")) {
								g.setColor(Color.BLACK);
								qishou = "白";
							}
							else {
								g.setColor(Color.white);
								qishou = "黑";
							}
							g.fillOval(x*30+10-15, y*30+10-15, 30, 30);
							k--;
						}
					}
					
				}
				
			}
		});
		btnNewButton.setBounds(32, 495, 113, 27);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("下一步");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(position!=-1) {
					String string = arrayList.get(position);
					String[]  psStrings = string.split(" ");
					position = position -1;
					
					int x = Integer.parseInt(psStrings[0]);
					int y = Integer.parseInt(psStrings[1]);
					System.out.println(x +  " " + y);
					Graphics graphics = panel.getGraphics();
					if(qishou.equals("黑")) {
						graphics.setColor(Color.BLACK);
						qishou = "白";
					}
					else {
						graphics.setColor(Color.white);
						qishou = "黑";
					}
					graphics.fillOval(x*30+10-15, y*30+10-15, 30, 30);
//					System.out.println("aaaaaaaaaa");
				}
				else {
					JOptionPane.showMessageDialog(null, "复盘已经完成");
				}
				
			}
		});
		btnNewButton_1.setBounds(334, 495, 113, 27);
		contentPane.add(btnNewButton_1);
	}
	
	
	
	public void changeTiList() {
		String sub = new String();
	
		while (trace.length()!=0) {
			sub = trace.substring(trace.length()-2,trace.length());

			String aString = ""+sub.charAt(0);
			String bString = "" + sub.charAt(1);
			int x = stringToNum.get(aString);
			int y = stringToNum.get(bString);
			arrayList.add(x + " " + y);
			System.out.println(x + " " + y);
			trace = trace.substring(0,trace.length()-2);
			
//			int 
		}
		System.out.println("list: " + arrayList.size());
	}
	
}
