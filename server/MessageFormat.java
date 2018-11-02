package newSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.IllformedLocaleException;
import java.util.Random;

import javax.swing.*;
import javax.xml.stream.XMLOutputFactory;

import jdk.nashorn.internal.scripts.JO;
import org.apache.commons.lang.math.RandomUtils;

import newSocket.UserData;
import newSocket.SuperServlet;

import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.omg.PortableInterceptor.USER_EXCEPTION;

public class MessageFormat implements Runnable {


	public volatile boolean clientStart = true;
	private DataInputStream dis;
	private Socket socket;
	private DataOutputStream dos;

	public MessageFormat(Socket s) {
		try {
			this.socket = s;
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		while (clientStart) {
			try {
				// 客户端传来的信息, 阻塞方法
				String requestMsg = dis.readUTF(); 
				System.out.println("service do  in：" + requestMsg);
				/*
				 * 写处理消息，包括匹配队友，发送消息，棋盘信息
				 * 
				 * */
				addInfo(requestMsg);
				
				
			} catch (Exception e) {
				/*
				 * 处理捕捉到的意外
				 * */
				 // 如果无法接收客户端的信息, 就把这个用户踢出去，并且发送给对方
//				System.out.println("无法收到客户端信息");
	            for(Entry<String, UserData> set : SuperServlet.getClient().entrySet()){
	                String userId = set.getKey(); 
	                UserData ud = set.getValue();
	                // 匹配退出的客户端进程
	                if(this == ud.getMsgFormat()){
	                    // 给对手发送退出信息
	                    JSONObject responseMsg = new JSONObject();
	                    responseMsg.put(Const.ID, Const.ID_STATUS_FAIL);
	                    UserData clientData = SuperServlet.getClient().get(ud.getEnemy());
	                    if(clientData != null && !clientData.isOver()){
	                        clientData.getMsgFormat().send(responseMsg);
	                    }
//	                    MessageFormatHelper.ppList.remove(ud.getUserId());
//	                    System.out.println("退出的客户端为："+ ud.getUserName() + " --> " + ud.getUserId());
	                    SuperServlet.getClient().remove(userId); // 服务器移除这个用户
//                        for(int j=0; j<SuperServlet.ppList.size(); j++){
//                            if(SuperServlet.ppList.get(j).getMsgFormat() == this){
//                                SuperServlet.ppList.remove(this);  // ppList移除这个用户
//                                break;
//                            }
//                        }

	                    System.out.println("在线数量：" + SuperServlet.getClient().size());
                        System.out.println("等待匹配用户的数量" + SuperServlet.ppList.size());
                        // ud.getThread().interrupt(); // 等待线程关闭
	                    close(); // 关闭相应的流
	                    clientStart = false; // 预先停止 while循环
	                    ud.getThread().stop(); // 等待线程关闭
	                }
	            }
			}

		}
		
	}

	public void addInfo(String msg) {
		System.out.println("addInfo:   " + msg);

		JSONObject json = JSONObject.fromObject(msg.replaceAll("\"", "\\\""));
		String id = json.getString(Const.ID);
//		JOptionPane.showMessageDialog(null, "服务端： id: " +id);

		switch (id) {

			// 初始化  不给客户端发送消息
		case Const.ID_STATUS_INIT:
			String myId = json.getString("my");
			String myName = json.getString("name");
			UserData myData = SuperServlet.getClient().get(myId);
			myData.name = myName;
			myData.setStatus("0");
			myData.setMatch(false);
			System.out.println("初始化客户端");
//			JOptionPane.showMessageDialog(null, "初始化客户端");
			break;
		// 匹配玩家 这里也不给客户端发送消息，只是给改变了状态，加入匹配数组队列里面，匹配消息从匹配处理那边发送
		case Const.ID_STATUS_PP:
			/*
			 * 传值的时候要把自己的id发送过来
			 * 此处要同时改变两个人的状态，并且随机分配先后手
			 * */
//			System.out.println("id: " + id);
			String myId1 = json.getString("my");// == userId
			String myname = json.get("myname").toString();

//			System.out.println("myId1: " + myId1);
			UserData myData1 = SuperServlet.getClient().get(myId1);
			myData1.setEnemy(myname);
			myData1.setStatus("1"); // 1 代表可以匹配队友
            SuperServlet.ppList.add(myData1);
			System.out.println("匹配队友改变状态完成");
//			JOptionPane.showMessageDialog(null, "更改状态" + " " + SuperServlet.ppList.size());

			break;

////			System.out.println("myData1: " + myData1);
//			boolean isMatch =false;
////			System.out.println("准备进入循环");
//            for(Entry<String, UserData> set : SuperServlet.getClient().entrySet()){
////            	System.out.println("for loop ");
//                String userId = set.getKey();
//                UserData ud = set.getValue();
//                // 如果不是自己并且对方也没有匹配， 并且对方也可以匹配
//                System.out.println("ud: status: " + ud.getStatus());
//                if(!myId1.equals(userId) && !ud.isMatch() ) {
//                    System.out.println("匹配  匹配   匹配");
//                    synchronized(ud ) {
//                		isMatch = true;
//                		myData1.setEnemy(ud.getName());
//                		myData1.setEnemyId(ud.getUserId());
//                		myData1.isMatch = true;
//
//
//                		ud.setEnemy(myData1.getName());
//                		ud.setEnemyId(myData1.getUserId());
//                		ud.isMatch = true;
//
//                		//随机分配先后手
//                		Random random = new Random();
//                		int i = random.nextInt(2);
//                		if(i==1) {
//                			myData1.setFirst( true);
//                			ud.setFirst(false);
//
//                		}
//                		else {
//                			myData1.setFirst(false);
//                			ud.setFirst(true);
//                		}
//
//                		// 让他们的状态变为准备状态(0：登陆; 1：准备; 2：对局开始; )
//                		ud.setStatus("2");
//                		myData1.setStatus("2");
//
//                		// 给mydata发送匹配成功
//                		JSONObject jsonObject = new JSONObject();
//                		jsonObject.put("id", "匹配成功");
//                		jsonObject.put("isFirst", myData1.isFirst);
//                		jsonObject.put("enemy", myData1.getEnemy());  // 这个是发送给对方名字
//                		myData1.msgFormat.send(jsonObject);
//
//                		// 给对手发送匹配成功
//                		JSONObject jsonObject2 = new JSONObject();
//                		jsonObject2.put("id", "匹配成功");
//                		jsonObject2.put("isFirst", ud.isFirst);
//                		jsonObject2.put("enemy", ud.getEnemy());
//                		ud.msgFormat.send(jsonObject2);
//
//                		System.out.println("jsonObject: " + jsonObject);
//
//                		System.out.println("jsonObject2: " + jsonObject2);
//                		isMatch = true;
//                		break;
//                	}
//                }
//            }
//            System.out.println("匹配失败！！！！！！！");
//            // 匹配失败发送消息回去
//            if(isMatch == false) {
//            	JSONObject jsonObject = new JSONObject();
//            	jsonObject.put("id", "匹配失败");
//        		myData1.msgFormat.send(jsonObject);
//
//            	break;
//            }
//            else if(isMatch == true){
//    			break;
//            }

		// 落子
		case Const.ID_STATUS_PUT:
            // 落子
//            JOptionPane.showMessageDialog(null, "落子");
            String myId2 = json.get("my").toString();
            String point = json.get("point").toString();
//            JOptionPane.showMessageDialog(null,"服务端 " + json);

            UserData userData = SuperServlet.getClient().get(myId2);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "获取落子位置"); //
            jsonObject.put("point", point);
//            JOptionPane.showMessageDialog(null, "服务端 put： " + point);
            userData.getMsgFormat().send(userData.getEnemyId(), jsonObject); // 发送给对方
			System.out.println("落子");
			break;
		// 获取棋子
		case Const.ID_STATUS_GET:

			break;
		// 对局结束
		case Const.ID_STATUS_OVER:

			break;
		// 悔棋请求
		case Const.ID_STATUS_BACK:

			String myId3 = json.get("my").toString();
			UserData uD = SuperServlet.getClient().get(myId3);
			JSONObject json0 = new JSONObject();
			json0.put("id","请求悔棋");
//            JOptionPane.showMessageDialog(null, "服务端： 给对方发送悔棋请求  "+json0+"");
			UserData emd = SuperServlet.getClient().get(uD.getEnemyId());
			emd.getMsgFormat().send(json0);

			break;
		// 悔棋请求的结果
		case Const.ID_STATUS_BACK_RESULT:
            String myId4 = json.get("my").toString();
            UserData uD2 = SuperServlet.getClient().get(myId4);
            String result = json.get("result").toString();
            JSONObject jj = new JSONObject();
            jj.put("result", result);
            jj.put("id", "请求悔棋结果");
//            JOptionPane.showMessageDialog(null, "给请求方发送请求  " + jj);
            uD2.getMsgFormat().send(uD2.getEnemyId(), jj);

			break;
		// 认输
		case Const.ID_STATUS_FAIL:
//			JOptionPane.showMessageDialog(null, json);
			String my = json.get("my").toString();
//			JOptionPane.showMessageDialog(null, my );

			UserData userData1 = SuperServlet.getClient().get(my);
//			JOptionPane.showMessageDialog(null, "123" + userData1.getUserId() );

			JSONObject jsonObject1 = new JSONObject();
			jsonObject1.put("id", "请求认输");
//			JOptionPane.showMessageDialog(null, "请求认输123 " );

			UserData emyData = SuperServlet.getClient().get(userData1.getEnemyId());
//			JOptionPane.showMessageDialog(null, "请求认输 123321 " + userData1.getEnemyId());

			emyData.getMsgFormat().send(jsonObject1);
//			JOptionPane.showMessageDialog(null, "请求认输 123321 " + userData1.getEnemyId());
			System.out.println("请求认输");
			break;
		// 聊天消息
		case Const.ID_STATUS_MSG:
//		    JOptionPane.showMessageDialog(null, "" + json);
            String myy = json.get("my").toString();
//            JOptionPane.showMessageDialog(null,"服务端："+myy);
            String message = json.get("message").toString();
//            JOptionPane.showMessageDialog(null,"服务端："+message);

            UserData userData2 = SuperServlet.getClient().get(myy);

            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("id", "聊天消息");
            jsonObject2.put("message", message);
//            JOptionPane.showMessageDialog(null, jsonObject2 + "");
            userData2.getMsgFormat().send(userData2.getEnemyId(), jsonObject2);

			break;
		// 游戏超时
		case Const.ID_STATUS_OVERTIME:

			break;
		// 重新开始
		case Const.RESTART:
//			JOptionPane.showMessageDialog(null, "重新开始： " + json);
			String mmyy = json.get("my").toString();
//			String restart = json.get("restart").toString();

			UserData userData3 = SuperServlet.getClient().get(mmyy);
			JSONObject jsonObject3 = new JSONObject();
			jsonObject3.put("id", "重新开始");
//			JOptionPane.showMessageDialog(null, jsonObject3 + "");
			userData3.getMsgFormat().send(userData3.getEnemyId(), jsonObject3);

			break;
		case Const.RESTART_RESULT:
//			JOptionPane.showMessageDialog(null, "重新开始结果： "+ json);
			String m = json.get("my").toString();
			String result1 = json.get("result").toString();

			UserData userData4 = SuperServlet.getClient().get(m);
			JSONObject jsonObject4 = new JSONObject();
			jsonObject4.put("result", result1);
			jsonObject4.put("id", "重新开始结果");
//			JOptionPane.showMessageDialog(null, jsonObject4 + "");
			userData4.getMsgFormat().send(userData4.getEnemyId(), jsonObject4);
			break;
		default:

			System.out.println("server: 未匹配到分支; id:" + id);
			JOptionPane.showMessageDialog(null, "server: 未匹配到分支; id:" + id);
			break;
		}

	}

	/**
	 * 发送消息给客户端
	 */
	public void send(final JSONObject msg) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (msg != null && dos != null) {
					// 服务器传出信息
					System.out.println("service do out: " + msg);
					try {
						dos.writeUTF(msg.toString());
						dos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}).start();
	}

	/**
	 * 给指定的客户端发送信息
	 * 
	 * @param userId
	 *            客户端
	 * @param msg
	 *            信息
	 */
	public void send(String userId, JSONObject msg) {
		UserData ud = SuperServlet.getClient().get(userId);
		if (ud != null) {
			ud.getMsgFormat().send(msg);
		} else {
			System.out.println("服务器无法获取发送对象, userId:" + userId);
		}
	}

	// 关闭请流(服务没有被关闭不要调用此方法)
	public void close() {
		try {
			if (dis != null) {
				dis.close();
				dis = null;
			}
			if (dos != null) {
				dos.close();
				dos = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
