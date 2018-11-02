package wzq;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream.PutField;
import java.net.Socket;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.JOptionPane;

import com.mysql.jdbc.log.Log;

import net.sf.json.JSONObject;
import net.sf.json.groovy.JsonGroovyBuilder;

public class Client {
	
	Socket socket;
	DataInputStream dataInputStream;
	DataOutputStream dataOutputStream;
	
	public Client(Socket socket) {
		this.socket = socket;
		try {
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 客户端初始化
	public boolean init(String name, String myId) {
		/*
		 * key: 
		 *     id:状态
		 *     my:userId
		 *     name: 用户名
		 * */
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", "初始化客户端");
		jsonObject.put("my", myId);
		jsonObject.put("name", name);
		System.out.println("客户端初始化");
		
		try {
			dataOutputStream.writeUTF(jsonObject.toString());
			dataOutputStream.flush();			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "请检查网络连接");
		}
		
		return false;
	}
	
	// 发送请求匹配玩家请求
	public boolean pp(String myId) {
		/*
		 * 发送： 
		 *    id:匹配玩家
		 *    my:myId
		 * 返回：
		 *    id:匹配成功
		 *    enemy:对方的名字
		 *    isFirst: true or false
		 * */
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", "匹配玩家");
		jsonObject.put("my", myId);
		jsonObject.put("myname", Login.user.getName());
		try {
			dataOutputStream.writeUTF(jsonObject.toString());
			dataOutputStream.flush();
			System.out.println("发送消息");
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "请重新连接");
		}
		
		System.out.println("匹配玩家");

		String requsetMsg;
		try {
			
			requsetMsg = null;
			/*             
			 *              这 里加一个匹配超时
			 * */
			while ((requsetMsg = dataInputStream.readUTF())!=null) {
				JSONObject jsonObject1 = JSONObject.fromObject(requsetMsg);
				if(jsonObject1.get("id").toString().equals("匹配成功")) {
					QpbaseP2P.enemy = jsonObject1.get("enemy").toString();
					QpbaseP2P.user2 += QpbaseP2P.enemy;
					System.out.println("myName: " + Login.user.getName());
					System.out.println("enemyName: " + QpbaseP2P.enemy);
					QpbaseP2P.lblUser.setText("名称: " + QpbaseP2P.enemy);
					Login.user.setEnemy(jsonObject1.get("enemy").toString());
					System.out.println("isFirst: " + jsonObject1.get("isFirst").toString().equals("true"));
					QpbaseP2P.isFirst = jsonObject1.get("isFirst").toString().equals("true");
					if(QpbaseP2P.isFirst == true) {
						QpbaseP2P.qishou = "黑";
						QpbaseP2P.canLuozi = true;
						JOptionPane.showMessageDialog(null, "您是先手，执黑棋");
					}
					else {
						QpbaseP2P.qishou = "黑";
						JOptionPane.showMessageDialog(null, "您是后手，执白棋");
					}
//					return true;
					System.out.println("匹配成功");
					break;
				}
				else {
					continue;
				}
			}
			
			
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	
	public void sendXY(String myId, String point) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", "传送落子位置");
		jsonObject.put("my", myId);
		jsonObject.put("point", point);
		
		try {
//			JOptionPane.showMessageDialog(null, "再一次马上发送point" + jsonObject);
			dataOutputStream.writeUTF(jsonObject.toString());
			dataOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "请检查网络");
		}
		
	}
	
	public boolean regret(String myId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", "请求悔棋");
		jsonObject.put("my", myId);
		
		try {
			dataOutputStream.writeUTF(jsonObject.toString());
			System.out.println("发送悔棋");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "不可悔棋");
		}
		
		
		return false;
	}	
	
	public void giveIn(String myId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("my", myId);
		jsonObject.put("id", "认输");
		try {
			dataOutputStream.writeUTF(jsonObject.toString());
			System.out.println("发送认输");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "认输失败");
		}
		
	}
	
	public void sendWH(String myId, String result) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("my", myId);
		jsonObject.put("id", "请求悔棋结果");
		jsonObject.put("result", result);
		try {
			dataOutputStream.writeUTF(jsonObject.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("客户端：悔棋请求结果： " + result);
//			JOptionPane.showMessageDialog(parentComponent, message);
		}
	}
	
	public void sendChatMessage(String myId, String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("my", myId);
		jsonObject.put("id", "聊天消息");
		jsonObject.put("message", message);
		try {
			dataOutputStream.writeUTF(jsonObject.toString());
			dataOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		JOptionPane.showMessageDialog(null, "客户端 发送聊天消息: " + jsonObject);
	}
	
	public void restart(String myId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("my", myId);
		jsonObject.put("id", "重新开始");
		try {
			System.out.println("重新开始: " + jsonObject);
			dataOutputStream.writeUTF(jsonObject.toString());
			dataOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "重新开始错误");
		}
	}
	
	public void restartResult(String myId,String result) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("my", myId);
		jsonObject.put("id", "重新开始结果");
		jsonObject.put("result", result);
		
		System.out.println("返回重新开始的结果");
		try {
			dataOutputStream.writeUTF(jsonObject.toString());
			dataOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "无效操作");
		}
		
	}
}
