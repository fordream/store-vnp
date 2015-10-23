package org.com.vnp.line98.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.com.vnp.line98.activity.R;

public class Board {
	public static final int SIZE = 9;
	public static final int TYPE_COUNT = 5;
	public static final int RANDOM_SIZE = 3;
	private int board[][] = new int[SIZE][SIZE];
	private int randomitem[] = new int[RANDOM_SIZE];
	public static final int TYPE_SMALL_0 = 1;
	public static final int TYPE_SMALL_1 = 2;
	public static final int TYPE_SMALL_2 = 3;
	public static final int TYPE_SMALL_3 = 4;
	public static final int TYPE_SMALL_4 = 5;
	public static final int TYPE_BIG_0 = 10;
	public static final int TYPE_BIG_1 = 21;
	public static final int TYPE_BIG_2 = 31;
	public static final int TYPE_BIG_3 = 41;
	public static final int TYPE_BIG_4 = 51;

	public Board() {
		clearBoard();
	}

	public void clearBoard() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				board[i][j] = -1;
			}
		}

		for (int i = 0; i < RANDOM_SIZE; i++) {
			randomitem[i] = -1;
		}
	}

	public void newBoard() {
		clearBoard();
		Random random = new Random();
		int count = 0;

		while (count < 3) {
			if(countSpace() ==0){
				return;
			}
			Point point = new Point(random.nextInt(SIZE), random.nextInt(SIZE));
			if (board[point.x][point.y] == -1) {
				board[point.x][point.y] = random.nextInt(TYPE_COUNT) + 1;
				count++;
			}
		}

		growBoard();

		nextRandom();
	}

	public void nextRandom() {
		for (int i = 0; i < RANDOM_SIZE; i++) {
			randomitem[i] = new Random().nextInt(TYPE_COUNT) + 1;
		}
	}

	public int getRandomRes(int index) {
		return getResource(index);
	}

	public void growBoard() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				board[i][j] = getGrowPoint(board[i][j]);
			}
		}
	}

	private int getGrowPoint(int small) {
		if (small == TYPE_SMALL_0) {
			return TYPE_BIG_0;
		} else if (small == TYPE_SMALL_1) {
			return TYPE_BIG_1;
		} else if (small == TYPE_SMALL_2) {
			return TYPE_BIG_2;
		} else if (small == TYPE_SMALL_3) {
			return TYPE_BIG_3;
		} else if (small == TYPE_SMALL_4) {
			return TYPE_BIG_4;
		}

		return small;
	}

	public int get(int i, int j) {
		return board[i][j];
	}

	public int getResource(int i, int j) {
		int r = board[i][j];
		if (r == TYPE_BIG_0) {
			return R.drawable.p1;
		} else if (r == TYPE_BIG_1) {
			return R.drawable.p2;
		} else if (r == TYPE_BIG_2) {
			return R.drawable.p4;
		} else if (r == TYPE_BIG_3) {
			return R.drawable.p5;
		} else if (r == TYPE_BIG_4) {
			return R.drawable.p6;
		} else if (r == TYPE_SMALL_0) {
			return R.drawable.p1_mini;
		} else if (r == TYPE_SMALL_1) {
			return R.drawable.p2_mini;
		} else if (r == TYPE_SMALL_2) {
			return R.drawable.p4_mini;
		} else if (r == TYPE_SMALL_3) {
			return R.drawable.p5_mini;
		} else if (r == TYPE_SMALL_4) {
			return R.drawable.p6_mini;
		}
		return R.drawable.p0;
	}

	public int getResource(int j) {
		int r = randomitem[j];
		if (r == TYPE_BIG_0) {
			return R.drawable.p1;
		} else if (r == TYPE_BIG_1) {
			return R.drawable.p2;
		} else if (r == TYPE_BIG_2) {
			return R.drawable.p4;
		} else if (r == TYPE_BIG_3) {
			return R.drawable.p5;
		} else if (r == TYPE_BIG_4) {
			return R.drawable.p6;
		} else if (r == TYPE_SMALL_0) {
			return R.drawable.p1_mini;
		} else if (r == TYPE_SMALL_1) {
			return R.drawable.p2_mini;
		} else if (r == TYPE_SMALL_2) {
			return R.drawable.p4_mini;
		} else if (r == TYPE_SMALL_3) {
			return R.drawable.p5_mini;
		} else if (r == TYPE_SMALL_4) {
			return R.drawable.p6_mini;
		}
		return R.drawable.p0;
	}

	public boolean isCanClick(int x, int y) {
		int value = board[x][y];
		return value == TYPE_BIG_0 || value == TYPE_BIG_1
				|| value == TYPE_BIG_2 || value == TYPE_BIG_3
				|| value == TYPE_BIG_4;
	}

	public void update(Point point, Point point2) {
		board[point2.x][point2.y] = board[point.x][point.y];
		board[point.x][point.y] = -1;
	}

	public List<Point> findWay(Point point, Point point2) {
		List<Point> lPoints = new ArrayList<Point>();
		// lPoints.add(point);
		boolean check[][] = new boolean[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				check[i][j] = false;
			}
		}

		check[point.x][point.y] = true;

		List<Point> list = new ArrayList<Point>();
		point.level = 0;
		list.add(point);
		int index = 0;
		while (list.size() > 0) {
			int count = 0;
			int size = list.size();

			for (int i = index; i < size; i++) {
				Point point3 = list.get(i);
				check[point3.x][point3.y] = true;
				if (point3.x - 1 >= 0 && !check[point3.x - 1][point3.y]
						&& !isCanClick(point3.x - 1, point3.y)) {
					Point p = new Point(point3.x - 1, point3.y);
					p.level = point3.level + 1;
					check[p.x][p.y] = true;
					list.add(p);
					if (p.x == point2.x && p.y == point2.y) {
						count = 0;
						break;
					}
					count = 1;
				}

				if (point3.x + 1 < SIZE && !check[point3.x + 1][point3.y]
						&& !isCanClick(point3.x + 1, point3.y)) {
					Point p = new Point(point3.x + 1, point3.y);
					p.level = point3.level + 1;
					check[p.x][p.y] = true;
					list.add(p);
					if (p.x == point2.x && p.y == point2.y) {
						count = 0;
						break;
					}
					count = 1;
				}

				if (point3.y + 1 < SIZE && !check[point3.x][point3.y + 1]
						&& !isCanClick(point3.x, point3.y + 1)) {
					Point p = new Point(point3.x, point3.y + 1);
					p.level = point3.level + 1;
					check[p.x][p.y] = true;
					list.add(p);
					if (p.x == point2.x && p.y == point2.y) {
						count = 0;
						break;
					}
					count = 1;
				}
				if (point3.y - 1 >= 0 && !check[point3.x][point3.y + -1]
						&& !isCanClick(point3.x, point3.y - 1)) {
					Point p = new Point(point3.x, point3.y - 1);
					p.level = point3.level + 1;
					check[p.x][p.y] = true;
					list.add(p);
					if (p.x == point2.x && p.y == point2.y) {
						count = 0;
						break;
					}
					count = 1;
				}
			}

			if (count == 0) {
				break;
			}

			// index = size;
		}

		if (list.get(list.size() - 1).x == point2.x
				&& list.get(list.size() - 1).y == point2.y) {
			Point p = list.get(list.size() - 1);
			lPoints.add(p);
			while (p.level != 0) {
				p = lPoints.get(lPoints.size() - 1);
				for (int i = 0; i < list.size(); i++) {
					Point point3 = list.get(i);
					if (point3.level == p.level - 1) {
						if (p.x == point3.x
								&& (p.y == point3.y - 1 || p.y == point3.y + 1)) {
							lPoints.add(point3);
							break;
						}
						if (p.y == point3.y
								&& (p.x == point3.x - 1 || p.x == point3.x + 1)) {
							lPoints.add(point3);
							break;
						}
					}
				}
				p = lPoints.get(lPoints.size() - 1);
			}

		}
		return lPoints;
	}

	public void update(List<Point> lPoints) {
		for(int i = 0; i <lPoints.size(); i ++){
			Point point = lPoints.get(i);
			board[point.x][point.y] = - 1;
		}
	}

	public List<Point> getEat() {
		List<Point> list = new ArrayList<Point>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (isCanClick(i, j)) {
					//int type = board[i][j];
					Point[] p = new Point[5];
					p[0] = new Point(i, j);
					p[1] = new Point(i, j + 1);
					p[2] = new Point(i, j + 2);
					p[3] = new Point(i, j + 3);
					p[4] = new Point(i, j + 4);
					check(list, p);

					Point[] p1 = new Point[5];
					p1[0] = new Point(i, j);
					p1[1] = new Point(i, j - 1);
					p1[2] = new Point(i, j - 2);
					p1[3] = new Point(i, j - 3);
					p1[4] = new Point(i, j - 4);
					check(list, p1);

					Point[] p2 = new Point[5];
					p2[0] = new Point(i, j);
					p2[1] = new Point(i + 1, j);
					p2[2] = new Point(i + 2, j);
					p2[3] = new Point(i + 3, j);
					p2[4] = new Point(i + 4, j);
					check(list, p2);
					
					Point[] p3 = new Point[5];
					p3[0] = new Point(i, j);
					p3[1] = new Point(i + 1, j + 1);
					p3[2] = new Point(i + 2, j + 2);
					p3[3] = new Point(i + 3, j + 3);
					p3[4] = new Point(i + 4, j + 4);
					check(list, p3);
					
					Point[] p4 = new Point[5];
					p4[0] = new Point(i, j);
					p4[1] = new Point(i + 1, j - 1);
					p4[2] = new Point(i + 2, j - 2);
					p4[3] = new Point(i + 3, j - 3);
					p4[4] = new Point(i + 4, j - 4);
					check(list, p4);
				}
			}
		}
		return list;
	}

	private void check(List<Point> list, Point[] p) {
		try {
			if (board[p[0].x][p[0].y] == board[p[1].x][p[1].y]
					&& board[p[0].x][p[0].y] == board[p[2].x][p[2].y]
					&& board[p[0].x][p[0].y] == board[p[3].x][p[3].y]
					&& board[p[0].x][p[0].y] == board[p[4].x][p[4].y]) {
				for (int i = 0; i < 5; i++) {
					boolean check = false;
					for (int j = 0; j < list.size(); j++) {
						Point point = list.get(j);
						if (p[i].x == point.x && p[i].y == point.y) {
							check = true;
						}
					}
					if (!check) {
						list.add(p[i]);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void nextBoard() {
		int count = 0;
		Random random = new Random();
		while (count < 3) {
			Point point = new Point(random.nextInt(SIZE), random.nextInt(SIZE));
			if (board[point.x][point.y] == -1) {
				board[point.x][point.y] = randomitem[count];
				count++;
			}
		}
	}
	
	public int countSpace(){
		int count = 0;
		for(int i = 0; i < SIZE; i ++){
			for(int j = 0; j < SIZE ; j ++){
				int type = board[i][j];
				if(type == - 1){
					count ++;
				}
			}
		}
		return count;
	}
}
