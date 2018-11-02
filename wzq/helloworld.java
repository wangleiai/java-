package wzq;

import java.io.IOException;
import java.util.Scanner;


public class helloworld {
	public static String a;
	public static int[][] oldscore = new int[16][16];
	public static int num = 0, islose = 0, iskome = 0, myscore = 0, hescore = 0, wtf = 0;

	public static int totalscore = 0;
	public static int[] score = new int[] { 850000, 600000, 30000, 3000, 2000, 800, 55, 35, 7, 0 };
	public static String[] str1 = new String[] { "CMMMM", "MCMMM", "MMCMM", "MMMCM", "MMMMC", "MMMCM", "M.MCM.M" };
	public static String[] str2 = new String[] { "OOOOC", "COOOO", "OOOCO", "OOCOO", "OCOOO" };
	public static String[] str3 = new String[] { ".CMMM.", ".MCMM.", ".MMCM.", ".MMMC.", "MCM.M", "M.MCM", "C.MMM",
			"MMM.C" };
	public static String[] str4 = new String[] { "COOO.", ".OOOC", ".OOCO.", ".OCOO." };
	public static String[] str5 = new String[] { "OCMMM.", "OMCMM.", "OMMCM.", "OMMMC.", ".CMMMO", ".MCMMO", ".MMCMO",
			".MMMCO", "O.MMC", "O.MCM" };
	public static String[] str6 = new String[] { ".MMC.", ".MCM.", ".CMM." };
	public static String[] str7 = new String[] { ".OOC", "COO.", ".COO", ".OCO" };
	public static String[] str8 = new String[] { ".MMCO", ".MCMO", ".CMMO", "OMMC.", "OMCM.", "OCMM.", "MOOC", "COOM" };
	public static String[] str9 = new String[] { ".MC.", ".CM.","OC." };
	public static String[] str10 = new String[] { ".OOOOC", "COOOO." };

	public static String xy = "abcdefghijklmnop";
	public static String[][] WG = new String[16][16];// 网格棋盘

	public static void main(String arg[]) throws IOException, InterruptedException {
		initWG();

		String b;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			islose = 0;
			iskome = 0;
			myscore = 0;
			hescore = 0;
			wtf = 0;
			num = 0;

			a = scanner.next();
//			System.out.println(eigthbegin(a));
		}

	}
	
	public static StringBuilder getPoint(String string) {
		initWG();
		islose = 0;
		iskome = 0;
		myscore = 0;
		hescore = 0;
		wtf = 0;
		num = 0;
		
		try {
			return eigthbegin(string);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return null;
//			e.printStackTrace();
		}
		
	}

	private static void initWG() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				WG[i][j] = ".";
				oldscore[i][j]=0;
			}
		}
	}
	
	public static StringBuilder MaxMinstate() throws InterruptedException {
		myscore = 0;
		hescore = 0;
		wtf = 0;
		StringBuilder board10 = new StringBuilder(a);
		String s = eigthbegin(board10.toString()).toString();
		myscore = wtf;
		if (iskome == 1) {
			iskome = 0;
			return eigthbegin(a);
		} else {
//			System.out.print("预判前棋盘" + board10);
			board10.append(s);
//			System.out.println("    预判后的棋盘" + board10);
			eigthbegin(board10.toString());
			hescore = wtf;
			if (myscore > hescore) {
				return eigthbegin(a);
			} else {
				board10.append(eigthbegin(board10.toString()));
				return eigthbegin(board10.toString());
			}
		}
	}

	public static StringBuilder eigthbegin(String m) throws InterruptedException {
		initWG();
		initWG2(m);
		if ((m.length() / 2) % 2 == 0)
			changeWG_7(1);
		else
			changeWG_7(2);
		int k = 0;
		int max = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (WG[i][j] == ".") {
					WG[i][j] = "C";

					StringBuilder s = new StringBuilder("");
					s.append(xy.charAt(i)).append(xy.charAt(j));
					// System.out.println( find7(s.toString(),1));
					findscore(find7(s.toString(), 1).toString());
					findscore(find7(s.toString(), 2).toString());
					findscore(find7(s.toString(), 3).toString());
					findscore(find7(s.toString(), 4).toString());
					oldscore[i][j] += totalscore;
					if (max < totalscore) {
						max = totalscore;
					}
					WG[i][j] = ".";
					totalscore = 0;
				}
			}
		}
		for(int i=0;i<16;i++) {
			for(int j=0;j<16;j++) {
//				System.out.print(WG[i][j]+" ");
			}
//			System.out.println();
		}
		for(int i=0;i<16;i++) {
			for(int j=0;j<16;j++) {
//				System.out.print(oldscore[i][j]+" ");
			}
//			System.out.println();
		}
		int pp = 0;
		wtf = max;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (oldscore[i][j] == max) {
					StringBuilder s = new StringBuilder("");
					s.append(xy.charAt(i));
					s.append(xy.charAt(j));
					// System.out.println(s);
					return s;

				}
			}
		}
		StringBuilder s = new StringBuilder();
		return s;
	}

	public static void initWG2(String board) {
		int j = 0;
		for (int i = 0; i < board.length(); i += 2) {
			if (j == 0) {
				changeWG(findnum(board.charAt(i + 1)), findnum(board.charAt(i)), 1);
				j = 1;
			} else if (j == 1) {
				changeWG(findnum(board.charAt(i + 1)), findnum(board.charAt(i)), 2);
				j = 0;
			}
		}
	}

	public static StringBuilder find7(String s, int type) {
		StringBuilder ans = new StringBuilder("");
		if (type == 1) {
			int x = 0, y = 0;
			for (int i = 1; i <= 4; i++) {
				if (findnum(s.charAt(1)) - i >= 0) {
					x = i;
				} else
					break;
			}
			for (int i = findnum(s.charAt(1)) - x; i <= findnum(s.charAt(1)); i++) {
				ans.append(WG[findnum(s.charAt(0))][i]);
			}
			for (int i = 1; i <= 4; i++) {
				if (findnum(s.charAt(1)) + i <= 14) {
					y = i;
				} else
					break;
			}
			for (int i = findnum(s.charAt(1)) + 1; i <= findnum(s.charAt(1)) + y; i++) {
				ans.append(WG[findnum(s.charAt(0))][i]);
			}
		} else if (type == 2) {
			int x = 0, y = 0, temp = 0;
			for (int i = 1; i <= 4; i++) {
				if (findnum(s.charAt(1)) - i >= 0 && findnum(s.charAt(0)) - i >= 0) {
					x = i;
				}
			}
			for (int i = x; i >= 0; i--) {
				ans.append(WG[findnum(s.charAt(0)) - i][findnum(s.charAt(1)) - i]);
			}
			for (int i = 1; i <= 4; i++) {
				if (findnum(s.charAt(1)) + i <= 14 && findnum(s.charAt(0)) + i <= 14) {
					y = i;
				}
			}
			for (int i = 1; i <= y; i++) {
				ans.append(WG[findnum(s.charAt(0)) + i][findnum(s.charAt(1)) + i]);
			}
		} else if (type == 3) {
			int x = 0, y = 0;
			for (int i = 1; i <= 4; i++) {
				if (findnum(s.charAt(0)) - i >= 0) {
					x = i;
				} else
					break;
			}
			for (int i = findnum(s.charAt(0)) - x; i <= findnum(s.charAt(0)); i++) {
				ans.append(WG[i][findnum(s.charAt(1))]);
			}
			for (int i = 1; i <= 4; i++) {
				if (findnum(s.charAt(0)) + i <= 14) {
					y = i;
				} else
					break;
			}
			for (int i = findnum(s.charAt(0)) + 1; i <= findnum(s.charAt(0)) + y; i++) {
				ans.append(WG[i][findnum(s.charAt(1))]);
			}
		} else if (type == 4) {
			int x = 0, y = 0;
			for (int i = 1; i <= 4; i++) {
				if (findnum(s.charAt(1)) + i >= 0 && findnum(s.charAt(0)) - i >= 0) {
					x = i;
				}
			}
			for (int i = x; i >= 0; i--) {
				if (findnum(s.charAt(0)) - i >= 0 && findnum(s.charAt(1)) + i <= 14)
					ans.append(WG[findnum(s.charAt(0)) - i][findnum(s.charAt(1)) + i]);
			}
			for (int i = 1; i <= 4; i++) {
				if (findnum(s.charAt(1)) - i <= 14 && findnum(s.charAt(0)) + i <= 14) {
					y = i;
				}
			}
			for (int i = 1; i <= y; i++) {
				if (findnum(s.charAt(0)) + i <= 14 && findnum(s.charAt(1)) - i >= 0)
					ans.append(WG[findnum(s.charAt(0)) + i][findnum(s.charAt(1)) - i]);
			}
		}
//		 System.out.println(ans);
		return ans;
	}

	public static int findnum(char a) {
		for (int i = 0; i < 16; i++) {
			if (xy.charAt(i) == a) {
				// System.out.println(i);
				return i;
			}
		}
		return -10;
	}

	public static StringBuilder changeWG(int x, int y, int type) {
		StringBuilder s = new StringBuilder("");
		if (type == 1) {
			if (y >= 0 && y <= 14 && x >= 0 && x <= 14)
				WG[y][x] = "x";
		} else if (type == 2) {
			if (y >= 0 && y <= 14 && x >= 0 && x <= 14)
				WG[y][x] = "o";
		}
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				s.append(WG[j][i]);
			}
		}
		// System.out.println(s);
		return s;
	}

	public static void changeWG_7(int type) {
		if (type == 1) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					if (WG[i][j] == "x") {
						WG[i][j] = "M";
					}
					if (WG[i][j] == "o") {
						WG[i][j] = "O";
					}
				}
			}
		} else if (type == 2) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					if (WG[i][j] == "o") {
						WG[i][j] = "M";
					}
					if (WG[i][j] == "x") {
						WG[i][j] = "O";
					}
				}
			}
		}
	}

	public static int findscore(String s) throws InterruptedException {

		// System.out.println(s);
		for (int i = 0; i < str1.length; i++) {
			if (s.contains(str1[i]) == true) {
				// System.out.println(s+"1存在"+str1[i]);
				totalscore += score[0];
			}
		}

		for (int i = 0; i < str2.length; i++) {
			if (s.contains(str2[i]) == true) {
				iskome = 1;
				// System.out.println(s+"2存在"+str2[i]);
				totalscore += score[1];
			}
		}

		for (int i = 0; i < str3.length; i++) {
			if (s.contains(str3[i]) == true) {
				// System.out.println(s+"3存在"+str3[i]);
				totalscore += score[2];
			}
		}

		for (int i = 0; i < str4.length; i++) {
			if (s.contains(str4[i]) == true) {
				// System.out.println(s+"4存在"+str4[i]);
				totalscore += score[3];
			}
		}

		for (int i = 0; i < str5.length; i++) {
			if (s.contains(str5[i]) == true) {
				// System.out.println(s+"5存在"+str5[i]);
				totalscore += score[4];
			}
		}

		for (int i = 0; i < str6.length; i++) {
			if (s.contains(str6[i]) == true) {
				// System.out.println(s+"6存在"+str6[i]);
				totalscore += score[5];
			}
		}

		for (int i = 0; i < str7.length; i++) {
			if (s.contains(str7[i]) == true) {
				// System.out.println(s+"7存在"+str7[i]);
				totalscore += score[6];
			}
		}

		for (int i = 0; i < str8.length; i++) {
			if (s.contains(str8[i]) == true) {
				// System.out.println(s+"8存在"+str8[i]);
				totalscore += score[7];
			}
		}

		for (int i = 0; i < str9.length; i++) {
			if (s.contains(str9[i]) == true) {
				// System.out.println(s+"9存在"+str9[i]);
				totalscore += score[8];
			}
		}

		for (int i = 0; i < str10.length; i++) {
			//
			if (s.contains(str10[i]) == true) {
				islose = 1;
				System.out.println(s + "    " + str10[i]);
				// System.out.println("玩赖了啊！！！");
			}
		}

		return totalscore;
	}
}
