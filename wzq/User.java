package wzq;

public class User {
	
	private int id = 0;
	private String name = "";
	private String password = "";
	private int win;
	private int fail;
	private String enemy = "";
	public String getEnemy() {
		return enemy;
	}
	public void setEnemy(String enemy) {
		this.enemy = enemy;
	}
	public User() {
		
	}
	public User(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.win = 0;
		this.fail = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}
	

}
