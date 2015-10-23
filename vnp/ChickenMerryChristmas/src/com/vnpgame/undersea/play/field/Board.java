package com.vnpgame.undersea.play.field;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Point;

import com.vnpgame.chickenmerrychristmas.R;

public class Board {
	private int type = 0;
	private int data[][] = new int[FiledBoard.SIZE][FiledRow.SIZE];

	public Board(int type) {
		this.type = type;
		create();
	}

	public void create() {
		for (int i = 0; i < FiledBoard.SIZE; i++) {
			for (int j = 0; j < FiledRow.SIZE; j++) {
				data[i][j] = -1;
			}
		}
	}

	public void set(int i, int j, int value) {
		data[i][j] = value;
	}

	public int get(int i, int j) {
		if (data[i][j] == 0) {
			return R.drawable.play1;
		}
		if (data[i][j] == 1) {
			return R.drawable.play2;
		}
		if (data[i][j] == 2) {
			return R.drawable.play3;
		}
		if (data[i][j] == 3) {
			return R.drawable.play4;
		}
		if (data[i][j] == 4) {
			return R.drawable.play5;
		}
		if (data[i][j] == 5) {
			return R.drawable.play6;
		}
		if (data[i][j] == 6) {
			return R.drawable.play7;
		}
		if (data[i][j] == 7) {
			return R.drawable.play8;
		}
		return R.drawable.tranfer;
	}

	public void createNewBoard(int level) {
		if (level <= 5) {
			type = 4;
		} else if (level <= 10) {
			type = 5;
		} else if (level <= 15) {
			type = 6;
		} else if (level <= 20) {
			type = 7;
		} else if (level <= 25) {
			type = 8;
		} else {
			type = 8;
		}
		
		create();
		Random random = new Random();
		for (int i = 0; i < FiledBoard.SIZE; i++) {
			for (int j = 0; j < FiledRow.SIZE; j++) {
				int value = random.nextInt(type);
				data[i][j] = value;
			}
		}

		if (!check()) {
			createNewBoard(level);
		}
	}

	public boolean check() {
		for (int i = 0; i < FiledBoard.SIZE; i++) {
			for (int j = 0; j < FiledRow.SIZE; j++) {
				try {
					if (data[i][j] == data[i + 1][j] && data[i][j] >= 0) {
						return true;
					}
				} catch (Exception e) {
				}

				try {
					if (data[i][j] == data[i][j + 1] && data[i][j] >= 0) {
						return true;
					}
				} catch (Exception e) {
				}
			}
		}
		return false;
	}

	public List<Point> check(int row, int column) {
		List<Point> integers = new ArrayList<Point>();
		if (data[row][column] >= 0) {
			integers.add(new Point(row, column));
			boolean check[][] = new boolean[FiledBoard.SIZE][FiledRow.SIZE];

			for (int i = 0; i < FiledBoard.SIZE; i++) {
				for (int j = 0; j < FiledRow.SIZE; j++) {
					check[i][j] = false;
				}
			}

			check[row][column] = true;
			List<Point> temp = new ArrayList<Point>();
			temp.add(new Point(row, column));

			while (temp.size() > 0) {
				Point point = temp.get(temp.size() - 1);
				temp.remove(temp.size() - 1);

				check[point.x][point.y] = true;

				if (isSame(point, new Point(point.x, point.y - 1))) {
					if (!check[point.x][point.y - 1]) {
						temp.add(new Point(point.x, point.y - 1));
						integers.add(new Point(point.x, point.y - 1));
					}
				}

				if (isSame(point, new Point(point.x, point.y + 1))) {
					if (!check[point.x][point.y + 1]) {
						temp.add(new Point(point.x, point.y + 1));
						integers.add(new Point(point.x, point.y + 1));
					}
				}

				if (isSame(point, new Point(point.x - 1, point.y))) {
					if (!check[point.x - 1][point.y]) {
						temp.add(new Point(point.x - 1, point.y));
						integers.add(new Point(point.x - 1, point.y));
					}
				}

				if (isSame(point, new Point(point.x + 1, point.y))) {
					if (!check[point.x + 1][point.y]) {
						temp.add(new Point(point.x + 1, point.y));
						integers.add(new Point(point.x + 1, point.y));
					}
				}
			}
		}
		return integers;
	}

	private boolean isSame(Point point, Point point2) {
		try {
			return data[point.x][point.y] == data[point2.x][point2.y];
		} catch (Exception e) {
			return false;
		}

	}

	public void remove(List<Point> list) {
		for (int i = 0; i < list.size(); i++) {
			data[list.get(i).x][list.get(i).y] = -1;
		}
	}

	public boolean update() {
		boolean check = false;
		// duyet theo cot
		for (int i = 0; i < FiledRow.SIZE; i++) {
			int index = canDown(i);
			if (index > 0) {
				check = true;
				for (int k = index - 1; k >= 0; k--) {
					data[k + 1][i] = data[k][i];
					if (k == 0) {
						data[k][i] = -1;
					} else {
						// data[k][i] = data[k + 1][i];
					}
				}
			}
		}

		if (!check) {
			int index = canLeft();
			if (index >= 0) {
				check = true;
				for (int i = index + 1; i < FiledRow.SIZE; i++) {
					for (int j = 0; j < FiledBoard.SIZE; j++) {
						data[j][i - 1] = data[j][i];
						if (i == FiledRow.SIZE - 1) {
							data[j][i] = -1;
						}
					}
				}
			}
		}
		return check;
	}

	private int canLeft() {
		int check = -1;
		for (int i = 0; i < FiledRow.SIZE; i++) {
			if (data[FiledBoard.SIZE - 1][i] < 0 && check == -1) {
				check = i;
			} else if (check >= 0 && data[FiledBoard.SIZE - 1][i] >= 0) {
				return check;
			}
		}
		return -1;
	}

	private int canDown(int i) {
		int count = -1;
		for (int j = 0; j < FiledBoard.SIZE; j++) {
			if (count == -1 && data[j][i] >= 0) {
				count = 0;
			}

			if (count == 0 && data[j][i] < 0) {
				return j;
			}
		}

		// for(int j = 0; j < FiledBoard.SIZE; j ++){
		// if(data[j][i] >=0){
		// return true;
		// }
		// }
		return -1;
	}

	public int count() {
		int count = 0;
		for (int i = 0; i < FiledBoard.SIZE; i++) {
			for (int j = 0; j < FiledRow.SIZE; j++) {
				if (data[i][j] >= 0) {
					count++;
				}
			}
		}
		return count;
	}
}
