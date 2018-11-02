package wzq;

import java.util.Scanner;

public class Win {
	static int arr[][] = new int[16][16];

	public static boolean judge(int x, int y, String qishou) {
		// 白1， 黑2
		if (qishou.equals("黑")) {
			arr[x][y] = 2;
			System.out.println(x + " " + y + " " + qishou + "  " + arr[x][y]);

		} else {
			arr[x][y] = 1;
		}

		/*
		 * 从4个方向判断是否输赢,横向，竖向， 正对角线，负对角线
		 */

		// 横向
		int sum = 0;
		int max = 0;
		int temx = x - 5;
		int temy = y;
		if (temx < 0) {
			temx = 0;
		}
		for (int i = 0; i < 11; i++) {
			// 防止超出棋盘
			if (temx + i >= 16) {
				break;
			}

			// 连续黑则sum++， 否则赋值0
			if (qishou.equals("黑")) {
				if (arr[temx + i][temy] == 2) {
					sum += 1;
					if (sum == 5)
						return true;
				} else {
					sum = 0;
				}

			} else {
				// 连续白则sum++，否则就赋值0
				if (qishou.equals("白")) {
					if (arr[temx + i][temy] == 1) {
						sum += 1;
						// 如果能赢，就返回true
						if (sum == 5) {
							return true;
						}
					} else {
						sum = 0;
					}
				}
			}
		}

		// 竖向
		sum = 0;
		max = 0;
		temx = x;
		temy = y - 5;
		if (temy < 0) {
			temy = 0;
		}
		for (int i = 0; i < 11; i++) {
			// 防止超出棋盘
			if (temy + i >= 16) {
				break;
			}

			if (qishou.equals("黑")) {
				if (arr[temx][temy + i] == 2) {
					sum += 1;
					if (sum == 5)
						return true;
				} else {
					sum = 0;
				}

			} else {
				if (qishou.equals("白")) {
					if (arr[temx][temy + i] == 1) {
						sum += 1;
						if (sum == 5) {
							return true;
						}
					} else {
						sum = 0;
					}
				}
			}
		}

		// 正对角线
		sum = 0;
		max = 0;
		for (int i = 0; i <= 5; i++) {
			temx = x - i;
			temy = y - i;
			if (temx == 0 || temy == 0)
				break;
		}

		for (int i = 0; i < 11; i++) {
			// 防止超出棋盘
			if (temy + i >= 16) {
				break;
			}
			if (temx + i >= 16) {
				break;
			}

			if (qishou.equals("黑")) {
				if (arr[temx + i][temy + i] == 2) {
					sum += 1;
					if (sum == 5)
						return true;
				} else {
					sum = 0;
				}

			} else {
				if (qishou.equals("白")) {
					if (arr[temx + i][temy + i] == 1) {
						sum += 1;
						if (sum == 5) {
							return true;
						}
					} else {
						sum = 0;
					}
				}
			}
		}

		// 负对角线
		sum = 0;
		if (qishou.equals("黑")) {
			int a = 0, b = 0;

			for (int i = 0; i <= 5; i++) {
				a = x + i;
				b = y - i;
				if (a == 15 || b == 0)
					break;
			}

			for (int i = 0; i < 11; i++) {

				if (a - i < 0)
					break;
				if (b + i >= 16)
					break;
				System.out.println((a - i) + "   " + (b + i)  );
				System.out.println("arr: " + arr[a - i][b + i]);
				if (arr[a - i][b + i] == 2) {
					// System.out.println("黑");
					sum++;
					System.out.println(sum + "----------" + arr[a][b] + " -----------" + a + "---------" + b);
					if (sum == 5)
						return true;
				} else {
					sum = 0;
				}

			}

		} else {

			int a = 0, b = 0;

			for (int i = 0; i <= 5; i++) {
				a = x + i;
				b = y - i;
				if (a == 15 || b == 0)
					break;
			}

			for (int i = 0; i < 11; i++) {
				if (a - i < 0)
					break;
				if (b + i > 16)
					break;

				if (arr[a - i][b + i] == 1) {
					sum++;
					if (sum == 5)
						return true;
				} else
					sum = 0;

			}
		}

		// 返回没赢
		return false;

	}

	public static void showQipan() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				System.out.print(arr[i][j] + " ");

			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int a, b, j = 0;
		boolean ju = false;
		String string ;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		for (int i = 0; i < 15; i++) {
			
			a = scanner.nextInt();
			b = scanner.nextInt();
			string = scanner.next();
			ju = judge(a, b, string);

			showQipan();
			if (ju) {
				System.out.println(string + " 赢");
				break;
			}
		}
	}
}
