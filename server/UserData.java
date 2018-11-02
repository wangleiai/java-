package newSocket;

import java.net.Socket;

//import cn.xt.format.MessageFormat;
import newSocket.MessageFormat;
import newSocket.SuperServlet;

/**
 * @author Administrator
 *
 */
public class UserData {
	
	public boolean isMatch() {
		return isMatch;
	}
	public void setMatch(boolean isMatch) {
		this.isMatch = isMatch;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnemy() {
		return enemy;
	}
	public void setEnemy(String enemy) {
		this.enemy = enemy;
	}
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	public boolean isOver() {
		return isOver;
	}
	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isConnect() {
		return isConnect;
	}
	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}
	public MessageFormat getMsgFormat() {
		return msgFormat;
	}
	public void setMsgFormat(MessageFormat msgFormat) {
		this.msgFormat = msgFormat;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEnemyId() {
		return enemyId;
	}
	public void setEnemyId(String enemyId) {
		this.enemyId = enemyId;
	}
	
	public boolean isMatch = false;
	public String name = ""; 

	public String enemy = ""; // 对家
	public String enemyId = ""; //对家的id

	private String userId; // 登陆的用户(唯一性)
	public boolean isFirst = false; // 是否为先手 false是不是先手
    public boolean isOver = true; // 对局是否结束 false是没结束
    public String status; // 状态(0：登陆; 1：准备; 2：对局开始; )
    public boolean isConnect = false;
	private Socket socket; // 客户端套接字对象
	private Thread thread; // 处理消息的线程
	public MessageFormat msgFormat; // 处理消息的对象


	
}
