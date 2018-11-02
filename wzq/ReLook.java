package wzq;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class ReLook extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;
	JPanel panel_1;
	JPanel panel_3;
	JPanel panel_2 ;
	public static ArrayList<String> tArrayList = new ArrayList<>();
	public static ArrayList<String> nArrayList = new ArrayList<>();
	public static ArrayList<Integer> wArrayList = new ArrayList<>();
	public static ArrayList<String> eArrayList = new ArrayList<>();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReLook frame = new ReLook();
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
	public ReLook() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 432, 328);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("胜负");
		lblNewLabel_1.setBounds(245, 81, 72, 18);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("对手");
		lblNewLabel_2.setBounds(131, 81, 72, 18);
		panel.add(lblNewLabel_2);
		
		table = new JTable(0,2);
		table.setBounds(68, 138, 174, -24);
		
		panel.add(table);
		
		panel_1 = new JPanel();
		panel_1.setBounds(96, 115, 72, 160);
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel_2 = new JPanel();
		panel_2.setBounds(210, 115, 72, 163);
		panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label = new JLabel("查看");
		label.setBounds(360, 81, 72, 18);
		panel.add(label);
		
		panel_3 = new JPanel();
		panel_3.setBounds(325, 115, 72, 160);
		panel.add(panel_3);
		getFight();
		
	}
	
	private void getFight() {
		RecordJdbc recordJdbc = new RecordJdbc();
		recordJdbc.select(Login.user.getName());
		System.out.println(eArrayList.size());
		
		for(int i=0; i<eArrayList.size(); i++) {
//			panel_1.add(new JLabel(tArrayList.get(i)));
			panel_1.add(new JButton(eArrayList.get(i)));
			panel_2.add(new JButton(wArrayList.get(i)+""));
			JButton button = new JButton("查看");
//			button.getActionCommand();
			button.setActionCommand(""+i);
			button.addActionListener(this);
			panel_3.add(button);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			String command = e.getActionCommand();
//			System.out.println(command);
			this.dispose();
			ReLookQiPan.trace = tArrayList.get(Integer.parseInt(command));
			ReLookQiPan reLookQiPan = new ReLookQiPan();
			
			reLookQiPan.setVisible(true);
			
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
	}
	
	
}
