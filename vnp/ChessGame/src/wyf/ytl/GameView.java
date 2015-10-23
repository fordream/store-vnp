package wyf.ytl;

import wyf.ytl.model.Item;
import wyf.ytl.model.PlayItemExit;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ����������������Ҫ���࣬������Ϸ�Ľ���
 * �ý���̳���SurfaceView��ʵ����SurfaceHolder.Callback�ӿ� ���а���һ��ˢ֡���߳���
 * 
 */
@SuppressLint("WrongCall")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	/*
	 * Sounds, exit
	 */
	private Item sound, exit, soundStatus, win, ok, lost;
	private TutorialThread thread;
	private TimeThread timeThread;
	private ChessActivity activity;
	private Item qiPan;

	private Item vs;
	private Item right;
	private Item left;
	private Item current;
	private Bitmap time;// ð��
	private Bitmap redtime;// ��ɫð��
	private Bitmap background;// ����ͼƬ

	private Bitmap[] heiZi = new Bitmap[7];
	private Bitmap[] hongZi = new Bitmap[7];
	private Bitmap[] number = new Bitmap[10];
	private Bitmap[] redNumber = new Bitmap[10];
	private Item qizibackground;

	private MediaPlayer go;// ��������
	private Paint paint;// ����
	boolean caiPan = true;// �Ƿ�Ϊ�������
	private boolean focus = false;// ��ǰ�Ƿ���ѡ�е�����
	private int selectqizi = 0; // ��Ȼѡ�е�����

	private int startI, startJ;// ��¼��ǰ���ӵĿ�ʼλ��
	private int endI, endJ;// ��¼��ǰ���ӵ�Ŀ��λ��

	private GuiZe guiZe;// ������

	private int status = 0;// ��Ϸ״̬��0��Ϸ�У�1ʤ��, 2ʧ��
	public int heiTime = 0;// �ڷ��ܹ�˼��ʱ��
	public int hongTime = 0;// �췽�ܹ�˼��ʱ��

	int[][] qizi = new int[][] {// ����
	{ 2, 3, 6, 5, 1, 5, 6, 3, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 4, 0, 0, 0, 0, 0, 4, 0 }, { 7, 0, 7, 0, 7, 0, 7, 0, 7 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },

			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 14, 0, 14, 0, 14, 0, 14, 0, 14 },
			{ 0, 11, 0, 0, 0, 0, 0, 11, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 9, 10, 13, 12, 8, 12, 13, 10, 9 }, };

	public GameView(Context context, ChessActivity activity) {// ������
		super(context);
		this.activity = activity;// �õ�Activity������
		getHolder().addCallback(this);
		go = MediaPlayer.create(this.getContext(), R.raw.go);// �������������
		this.thread = new TutorialThread(getHolder(), this);// ��ʼ��ˢ֡�߳�
		this.timeThread = new TimeThread(this);// ��ʼ��˼��ʱ����߳�
		init();// ��ʼ��������Դ
		guiZe = new GuiZe();// ��ʼ��������
	}

	public void init() {// ��ʼ������
		Resources resources = getResources();
		qiPan = new Item(R.drawable.qipan, resources, new Point(10, 10));

		ChessUtils.updateSize(qiPan);
		sound = new Item(R.drawable.sound2, resources, new Point(10,
				ChessUtils.SIZE_2 * 15));

		soundStatus = new Item(R.drawable.sound3, resources, new Point(
				10 + ChessUtils.SIZE_1, ChessUtils.SIZE_2 * 15));
		exit = new PlayItemExit(resources);

		win = new Item(R.drawable.win, resources, new Point());
		ok = new Item(R.drawable.ok, resources, new Point());
		lost = new Item(R.drawable.lost, resources, new Point());

		paint = new Paint();// ��ʼ������

		qizibackground = new Item(R.drawable.qizi, resources, new Point());
		vs = new Item(R.drawable.vs, resources, new Point(10,
				ChessUtils.SIZE_2 * 11));
		right = new Item(R.drawable.right, resources, new Point(155, 420));
		left = new Item(R.drawable.left, resources, new Point(120, 420));
		current = new Item(R.drawable.current, resources, new Point(138, 445));
		time = BitmapFactory.decodeResource(resources, R.drawable.time);// ��ɫð��
		redtime = BitmapFactory.decodeResource(resources, R.drawable.redtime);// ��ɫð��

		heiZi[0] = BitmapFactory.decodeResource(resources, R.drawable.heishuai);// ��˧
		heiZi[1] = BitmapFactory.decodeResource(resources, R.drawable.heiju);// �ڳ�
		heiZi[2] = BitmapFactory.decodeResource(resources, R.drawable.heima);// ����
		heiZi[3] = BitmapFactory.decodeResource(resources, R.drawable.heipao);// ����
		heiZi[4] = BitmapFactory.decodeResource(resources, R.drawable.heishi);// ��ʿ
		heiZi[5] = BitmapFactory.decodeResource(resources, R.drawable.heixiang);// ����
		heiZi[6] = BitmapFactory.decodeResource(resources, R.drawable.heibing);// �ڱ�

		hongZi[0] = BitmapFactory.decodeResource(resources,
				R.drawable.hongjiang);// �콫
		hongZi[1] = BitmapFactory.decodeResource(resources, R.drawable.hongju);// �쳵
		hongZi[2] = BitmapFactory.decodeResource(resources, R.drawable.hongma);// ����
		hongZi[3] = BitmapFactory.decodeResource(resources, R.drawable.hongpao);// ��h
		hongZi[4] = BitmapFactory.decodeResource(resources, R.drawable.hongshi);// ����
		hongZi[5] = BitmapFactory.decodeResource(resources,
				R.drawable.hongxiang);// ����
		hongZi[6] = BitmapFactory.decodeResource(resources, R.drawable.hongzu);// ����

		number[0] = BitmapFactory.decodeResource(resources, R.drawable.number0);// ��ɫ����0
		number[1] = BitmapFactory.decodeResource(resources, R.drawable.number1);// ��ɫ����1
		number[2] = BitmapFactory.decodeResource(resources, R.drawable.number2);// ��ɫ����2
		number[3] = BitmapFactory.decodeResource(resources, R.drawable.number3);// ��ɫ����3
		number[4] = BitmapFactory.decodeResource(resources, R.drawable.number4);// ��ɫ����4
		number[5] = BitmapFactory.decodeResource(resources, R.drawable.number5);// ��ɫ����5
		number[6] = BitmapFactory.decodeResource(resources, R.drawable.number6);// ��ɫ����6
		number[7] = BitmapFactory.decodeResource(resources, R.drawable.number7);// ��ɫ����7
		number[8] = BitmapFactory.decodeResource(resources, R.drawable.number8);// ��ɫ����8
		number[9] = BitmapFactory.decodeResource(resources, R.drawable.number9);// ��ɫ����9

		redNumber[0] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber0);// ��ɫ����0
		redNumber[1] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber1);// ��ɫ����1
		redNumber[2] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber2);// ��ɫ����2
		redNumber[3] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber3);// ��ɫ����3
		redNumber[4] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber4);// ��ɫ����4
		redNumber[5] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber5);// ��ɫ����5
		redNumber[6] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber6);// ��ɫ����6
		redNumber[7] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber7);// ��ɫ����7
		redNumber[8] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber8);// ��ɫ����8
		redNumber[9] = BitmapFactory.decodeResource(resources,
				R.drawable.rednumber9);// ��ɫ����9

		background = BitmapFactory.decodeResource(resources,
				R.drawable.bacnground);

	}

	/**
	 * �÷������Լ�����Ĳ�����д�� �÷������5ģ�ֻ�����ݻ�����Ļ
	 */

	public void onDraw(Canvas canvas) {// �Լ�д�Ļ��Ʒ���
		if (canvas != null) {
			canvas.drawColor(Color.WHITE);

			canvas.drawBitmap(background, 0, 0, null);// �屳��

			qiPan.onDraw(canvas, paint);

			for (int i = 0; i < qizi.length; i++) {
				for (int j = 0; j < qizi[i].length; j++) {// ��������
					if (qizi[i][j] != 0) {
						qizibackground
								.setPosition(new Point(9 + j
										* ChessUtils.SIZE_1, 10 + i
										* ChessUtils.SIZE_2));
						qizibackground.onDraw(canvas, paint);
						if (qizi[i][j] == 1) {// Ϊ��˧ʱ
							canvas.drawBitmap(heiZi[0], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 2) {// Ϊ�ڳ�ʱ
							canvas.drawBitmap(heiZi[1], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 3) {// Ϊ����ʱ
							canvas.drawBitmap(heiZi[2], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 4) {// Ϊ����ʱ
							canvas.drawBitmap(heiZi[3], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 5) {// Ϊ��ʿʱ
							canvas.drawBitmap(heiZi[4], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 6) {// Ϊ����ʱ
							canvas.drawBitmap(heiZi[5], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 7) {// Ϊ�ڱ�ʱ
							canvas.drawBitmap(heiZi[6], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 8) {// Ϊ�콫ʱ
							canvas.drawBitmap(hongZi[0], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 9) {// Ϊ�쳵ʱ
							canvas.drawBitmap(hongZi[1], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 10) {// Ϊ����ʱ
							canvas.drawBitmap(hongZi[2], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 11) {// Ϊ��hʱ
							canvas.drawBitmap(hongZi[3], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == ChessUtils.SIZE_12) {// Ϊ����ʱ
							canvas.drawBitmap(hongZi[4], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 13) {// Ϊ����ʱ
							canvas.drawBitmap(hongZi[5], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						} else if (qizi[i][j] == 14) {// Ϊ����ʱ
							canvas.drawBitmap(hongZi[6], ChessUtils.SIZE_12 + j
									* ChessUtils.SIZE_1, ChessUtils.SIZE_13 + i
									* ChessUtils.SIZE_2, paint);
						}
					}
				}
			}

			vs.onDraw(canvas, paint);
			canvas.drawBitmap(time, 81, ChessUtils.SIZE_2 * 12, paint);
			int temp = this.heiTime / 60;// ����ʱ��

			String timeStr = temp + "";// ת�����ַ�
			if (timeStr.length() < 2) {// ������}λʱǰ����0
				timeStr = "0" + timeStr;
			}

			for (int i = 0; i < 2; i++) {// ѭ������ʱ��
				int tempScore = timeStr.charAt(i) - '0';
				canvas.drawBitmap(number[tempScore], 65 + i * 7, 412, paint);
			}
			// ������
			temp = this.heiTime % 60;
			timeStr = temp + "";// ת�����ַ�
			if (timeStr.length() < 2) {
				timeStr = "0" + timeStr;// ������С��2ʱ��ǰ�����һ��0
			}
			for (int i = 0; i < 2; i++) {// ѭ��
				int tempScore = timeStr.charAt(i) - '0';
				canvas.drawBitmap(number[tempScore], 85 + i * 7, 412, paint);// ����
			}
			// ��ʼ���ƺ췽ʱ��
			canvas.drawBitmap(this.redtime, 262, 410, paint);// �췽��ð��
			int temp2 = this.hongTime / 60;// ����ʱ��
			String timeStr2 = temp2 + "";// ת�����ַ�
			if (timeStr2.length() < 2) {// ������}λʱǰ����0
				timeStr2 = "0" + timeStr2;
			}
			for (int i = 0; i < 2; i++) {// ѭ������ʱ��
				int tempScore = timeStr2.charAt(i) - '0';
				canvas.drawBitmap(redNumber[tempScore], 247 + i * 7, 411, paint);// ����
			}
			// ������
			temp2 = this.hongTime % 60;// ���ǰ������
			timeStr2 = temp2 + "";// ת�����ַ�
			if (timeStr2.length() < 2) {// ����}λʱǰ����0��
				timeStr2 = "0" + timeStr2;
			}
			for (int i = 0; i < 2; i++) {// ѭ������
				int tempScore = timeStr2.charAt(i) - '0';
				canvas.drawBitmap(redNumber[tempScore], 267 + i * 7, 411, paint);// ����ʱ������
			}

			right.onDraw(canvas, paint, caiPan == true);
			left.onDraw(canvas, paint, caiPan != true);
			current.onDraw(canvas, paint);
			sound.onDraw(canvas, paint);
			soundStatus.onDraw(canvas, paint, activity.isSound);
			exit.onDraw(canvas, paint);
			win.onDraw(canvas, paint, status == 1);
			ok.onDraw(canvas, paint, status == 2 || status == 1);
			lost.onDraw(canvas, paint, status == 2);
		}
	}

	/**
	 * �÷�������Ϸ��Ҫ�߼��ӿ� ����������� ��ݵ���λ�ú͵�ǰ����Ϸ״̬�����Ӧ�Ĵ���
	 * ����Ҫ�л�Viewʱ��ͨ���Activity����Handler��Ϣ4���� ע�����ֻȡ��Ļ�����µ��¼�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {// ��д����Ļ����
		if (event.getAction() == MotionEvent.ACTION_DOWN) {// ֻȡ��갴�µ��¼�

			if (exit.isClickMe(event)) {
				activity.myHandler.sendEmptyMessage(1);// ������Ϣ���л���MenuView
			} else if (ok.isClickMe(event) && (status == 1 || status == 2)) {
				activity.myHandler.sendEmptyMessage(1);
			} else if (status == 0) {// ��Ϸ��ʱ
				if (event.getX() > 10
						&& event.getX() < 10 + ChessUtils.SIZE_1 * 8
						&& event.getY() > 10
						&& event.getY() < 10 + ChessUtils.SIZE_2 * 8) {// ����λ����������ʱ
					if (caiPan == true) {// ����Ǹ��������
						int i = -1, j = -1;
						int[] pos = getPos(event);// �����껻������ڵ��к���
						i = pos[0];
						j = pos[1];
						if (focus == false) {// ֮ǰû��ѡ�е�����
							if (qizi[i][j] != 0) {// ����λ��������
								if (qizi[i][j] > 7) {// �������Լ������ӡ�������ĺ�ɫ����
									selectqizi = qizi[i][j];// ����������Ϊѡ�е�����
									focus = true;// ��ǵ�ǰ��ѡ�е�����
									startI = i;
									startJ = j;
								}
							}
						} else {// ֮ǰѡ�й�����
							if (qizi[i][j] != 0) {// ����λ��������
								if (qizi[i][j] > 7) {// ������Լ�������.
									selectqizi = qizi[i][j];// ����������Ϊѡ�е�����
									startI = i;
									startJ = j;
								} else {// ����ǶԷ�������
									endI = i;
									endJ = j;// ����õ�
									boolean canMove = guiZe.canMove(qizi,
											startI, startJ, endI, endJ);
									if (canMove) {// �������ƶ���ȥ
										caiPan = false;// �����������
										if (qizi[endI][endJ] == 1
												|| qizi[endI][endJ] == 8) {// ����ǡ�˧���򡰽���
											this.success();// ʤ����
										} else {
											if (activity.isSound) {
												go.start();// ������������
											}
											qizi[endI][endJ] = qizi[startI][startJ];// �ƶ�����
											qizi[startI][startJ] = 0;// ��ԭ4�����
											startI = -1;
											startJ = -1;
											endI = -1;
											endJ = -1;// ��ԭ�����
											focus = false;// ��ǵ�ǰû��ѡ������

											ChessMove cm = guiZe
													.searchAGoodMove(qizi);// ��ݵ�ǰ���Ʋ�ѯһ����õ��߷�
											if (activity.isSound) {
												go.start();// ������������
											}
											qizi[cm.toX][cm.toY] = qizi[cm.fromX][cm.fromY];// �ƶ�����
											qizi[cm.fromX][cm.fromY] = 0;
											caiPan = true;// �ָ������Ӧ
										}
									}
								}
							}// end����λ��������
							else {// ������λ��û������
								endI = i;
								endJ = j;
								boolean canMove = guiZe.canMove(qizi, startI,
										startJ, endI, endJ);// �鿴�Ƿ����
								if (canMove) {// �������ƶ�
									caiPan = false;// �����������
									if (activity.isSound) {
										go.start();// ������������
									}
									qizi[endI][endJ] = qizi[startI][startJ];// �ƶ�����
									qizi[startI][startJ] = 0;// ��ԭ4���ÿ�
									startI = -1;
									startJ = -1;
									endI = -1;
									endJ = -1;// ��ԭ�����
									focus = false;// ��־λ��false

									ChessMove cm = guiZe.searchAGoodMove(qizi);// �õ�һ���߷�
									if (qizi[cm.toX][cm.toY] == 8) {// ���Գ�����Ľ�
										status = 2;// �л���Ϸ״̬Ϊʧ��
									}
									if (activity.isSound) {// ��Ҫ��������ʱ
										go.start();// ������������
									}
									qizi[cm.toX][cm.toY] = qizi[cm.fromX][cm.fromY];// �ƶ�����
									qizi[cm.fromX][cm.fromY] = 0;
									caiPan = true;// �ָ������Ӧ
								}
							}
						}// end ֮ǰѡ�й�����
					}
				}// end����λ����������ʱ
			}// end��Ϸ��ʱ
		}
		return super.onTouchEvent(event);
	}

	public int[] getPos(MotionEvent e) {// ����껻��������ά��
		int[] pos = new int[2];
		double x = e.getX();// �õ����λ�õ�x���
		double y = e.getY();// �õ����λ�õ�y���
		if (qiPan.isClickMe(e)) {
			// if (x > 10 && y > 10 && x < 10 + qiPan.getWidth()
			// && y < 10 + qiPan.getHeight()) {// ����������ʱ

			pos[0] = Math.round((float) ((y - 21) / (ChessUtils.SIZE_1 + 1)));// ȡ�����ڵ���
			pos[1] = Math.round((float) ((x - 21) / (ChessUtils.SIZE_2 + 1)));// ȡ�����ڵ���
		} else {// ����λ�ò�������ʱ
			pos[0] = -1;// ��λ����Ϊ������
			pos[1] = -1;
		}

		return pos;// ��������鷵��
	}

	public void success() {// ʤ����
		status = 1;// �л���ʤ��״̬
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {// ��д��
		this.thread.setFlag(true);
		this.thread.start();// ��ˢ֡�߳�
		timeThread.setFlag(true);
		timeThread.start();// ��˼��ʱ����߳�
	}

	public void surfaceDestroyed(SurfaceHolder holder) {// view���ͷ�ʱ���õ�
		boolean retry = true;
		thread.setFlag(false);// ֹͣˢ֡�߳�
		timeThread.setFlag(false);// ֹͣ˼��ʱ���߳�
		while (retry) {
			try {
				thread.join();
				timeThread.join();// �ȴ��߳̽���
				retry = false;// ����ѭ����־λΪfalse
			} catch (InterruptedException e) {// ���ϵ�ѭ����ֱ���ȴ���߳̽���
			}
		}
	}

	class TutorialThread extends Thread {// ˢ֡�߳�
		private int span = 300;// ˯�ߵĺ�����
		private SurfaceHolder surfaceHolder;// SurfaceHolder������
		private GameView gameView;// gameView������
		private boolean flag = false;// ѭ����־λ

		public TutorialThread(SurfaceHolder surfaceHolder, GameView gameView) {// ������
			this.surfaceHolder = surfaceHolder;// �õ�SurfaceHolder����
			this.gameView = gameView;// �õ�GameView������
		}

		public void setFlag(boolean flag) {// ����ѭ�����
			this.flag = flag;
		}

		public void run() {// ��д�ķ���
			Canvas c;// ����
			while (this.flag) {// ѭ������
				c = null;
				try {
					c = this.surfaceHolder.lockCanvas(null);
					synchronized (this.surfaceHolder) {
						gameView.onDraw(c);// ���û��Ʒ���
					}
				} finally {// ��finally��֤�������һ����ִ��
					if (c != null) {
						// ������Ļ��ʾ����
						this.surfaceHolder.unlockCanvasAndPost(c);
					}
				}
				try {
					Thread.sleep(span);// ˯��span����
				} catch (Exception e) {// �����쳣��Ϣ
					e.printStackTrace();// ��ӡ�쳣��ջ��Ϣ
				}
			}
		}
	}
}