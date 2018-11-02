package newSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import newSocket.UserData;

import net.sf.json.JSONObject;

import javax.swing.*;

public class SuperServlet extends Thread {

    private static final int port = 8888;
	private ServerSocket ss;
	private Socket s;
	private boolean start = false;
	private static volatile Map<String, UserData> client = new HashMap<>();
	public static ArrayList<UserData> ppList = new ArrayList<UserData>();

	public static void main(String[] args) {
		SuperServlet servlet = new SuperServlet();
		Thread thread = new Thread(){
            @Override
            public void run() {
                while(true){
                    System.out.println("true true " + SuperServlet.ppList.size());
//					System.out.println();
					SuperServlet.ppList.size();
                    if(ppList.size()>=2){
//                        JOptionPane.showMessageDialog(null, ppList.size() + "");
                        System.out.println("队友已匹配");
                        UserData data1 = ppList.get(0);
                        UserData data2 = ppList.get(1);

                        // 得到id1和id2
                        String id1 = data1.getUserId();
                        String id2 = data2.getUserId();

                        // 给data1和data2分配战斗对象
                        data1.isMatch = true;
                        data1.setEnemyId(id2);
                        data1.setEnemy(data2.getName());
                        data2.isMatch = true;
                        data2.setEnemyId(id1);
                        data2.setEnemy(data1.getName());

                        // 给data1 和data2 改变状态 2是已经开始对战了
                        data1.setStatus("2");
                        data2.setStatus("2");

                        //随机分配先后手
                        Random random = new Random();
                        int i = random.nextInt(2);
                        if(i==1) {
                            data1.setFirst( true);
                            data2.setFirst(false);

                        }
                        else {
                            data1.setFirst(false);
                            data2.setFirst(true);
                        }

                        // 给对手发送消息
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", "匹配成功");
                        jsonObject.put("isFirst", data1.isFirst);
//                        jsonObject.put("e")
                        jsonObject.put("enemy", data1.getEnemy()); // 发送对方名字
                        data1.getMsgFormat().send(jsonObject);

                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("id", "匹配成功");
                        jsonObject1.put("isFirst", data2.isFirst);
                        jsonObject1.put("enemy", data2.getEnemy());
                        data2.getMsgFormat().send(jsonObject1);

                        // 把匹配了的给踢出匹配队列
                        ppList.remove(0);
                        ppList.remove(0);
                    }
                }

            }
        };
		thread.start();
		servlet.startService();
//        thread.interrupt();
	}

	public SuperServlet() {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 统一获取接口
	 * 获取在服务器上客户端集
	 */
	public synchronized static Map<String, UserData> getClient(){
		return client;
	}

	
	// 启动的方法
	public void startService() {
		// LogUtils.init();
		this.start = true; // 打开接收的标记
		this.start(); // 启动线程
		System.out.println("服务已启动！监听端口：" + port);
	}
	
	
	@Override
	public void run() {

		while (true) {
			try {
				s = ss.accept();
				synchronized (s) {
					// 随机分配ID
					String userId = Const.getId();
					
				    MessageFormat cs = new MessageFormat(s); // 建立传输入数据的线程(服务类)
	                // 为每个连接的客户端定义一个线程为其服务
	                Thread th = new Thread(cs); 
	                // 定义用户信息类
	                UserData ud = new UserData();
	                ud.setName(userId); // 这里地方可能需要改
	                ud.setUserId(userId);
	                ud.setMsgFormat(cs);
	                ud.setSocket(s);
	                ud.setThread(th);

	                // myId == userId
	                client.put(userId, ud); // 将这个用户保存到服务器中
	                th.start(); // 启动这个线程
	                // 将识别ID发给客户端保存
	                JSONObject responseMsg = new JSONObject();
	                // Const.ID和Const.MSG是key， 后面是value
	                responseMsg.put(Const.ID, Const.ID_STATUS_HANDSNAKE);
	                responseMsg.put(Const.MSG, userId);
	                cs.send(responseMsg); // 发送消息给客户端
	                System.out.println("一个客户连接... 在线数量：" + client.size());

					
				}

			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	}

}
