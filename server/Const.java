package newSocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Const {

	// 基础数据(key)
	public static final String ID = "id";
	public static final String MSG = "msg";

	// 基础数据(value)
	public static final String ID_STATUS_ERROR = "idError"; // 错误信息
	public static final String ID_STATUS_INIT = "初始化客户端"; // 向服务器请求初始化
	public static final String ID_STATUS_PP = "匹配玩家";
	public static final String PP_SUCCESS = "匹配成功";
	public static final String ID_STATUS_PUT = "传送落子位置";
	public static final String ID_STATUS_GET = "获取落子位置";
	public static final String ID_STATUS_OVER = "对局结束";
	public static final String ID_STATUS_MSG = "聊天消息";
	public static final String ID_STATUS_BACK = "请求悔棋";
	public static final String ID_STATUS_FAIL = "认输";
	public static final String ID_STATUS_HANDSNAKE = "初次握手";
	public static final String ID_STATUS_BACK_RESULT = "请求悔棋结果";
	public static final String ID_STATUS_OVERTIME = "游戏超时";
	public static final String SIZE = "棋盘长度";
	public static final String EXISTS = "该用户名已存在系统中";
	public static final String USER_NAME = "userName";
	public static final String INIT_SUCCESS = "初始化成功";
	public static final String X = "x";
	public static final String Y = "y";
	public static final String STATUS = "status"; // 当前棋子的状态
	public static final String COLOR = "落子颜色";
	public static final String SYSTEM_MSG = "系统消息";
	public static final  String RESTART = "重新开始";
	public static final String RESTART_RESULT = "重新开始结果";


	public static String getId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(new Date()) + "-" + UUID.randomUUID().toString();

	}
}
