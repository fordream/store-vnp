

#import <UIKit/UIKit.h>
#import "Point1.h"

#define kSliderHeight			18.0

#define kLeftMargin				20.0
#define kTopMargin				20.0
#define kRightMargin			20.0
#define kTweenMargin			10.0

#define kTextFieldHeight		30.0


#define kSegmentedControlHeight 40.0
#define kLabelHeight			20.0

#define TOTAL_TIME_PLAY			400	// 400 seconds
//----------- With classic. -----------------------
#define SCORE_LEVEL2			2000
#define SCORE_LEVEL3			4000
#define SCORE_LEVEL4			6000
#define SCORE_LEVEL5			10.000
#define SCORE_LEVEL6			20.000
#define SCORE_LEVEL7			40.000
#define SCORE_LEVEL8			60.000
#define SCORE_LEVEL9			80.000
#define SCORE_LEVEL10			100.000
#define SCORE_LEVEL11			120.000
#define SCORE_LEVEL12			150.000

#define TYPE0					0			// an 0		NORmal
#define TYPE01					1			// norMAL quay
#define TYPE1					1			// an 1
#define TYPE2					2			// an 2
#define TYPE3					3			// an 3
#define TYPE4					4			// an 4
#define TYPE5					5			// an 5
#define TYPE6					6			// an 6
#define TYPE7					7			// an 7
#define TYPE31					31			// an 3
#define TYPE41					41			// an 4
#define TYPE51					51			// an 5
#define TYPE61					61			// an 6
#define SIZE					64
#define SIZE8					50
#define TIME0					0.0
#define TIME1					0.1
#define TIME2					0.2
#define TIME3					0.3
#define TIME4					0.4
#define TIME5					0.5
#define TIME6					0.6
#define TIME7					0.7
#define TIME8					0.8
#define TIME9					0.9
#define TIME10					1.0
#define TIME20					3.0
#define TYPE_0_NORMAL			0
#define TYPE_1_NORMAL			1
#define TYPE_2_NORMAL			2
#define TYPE_3_NORMAL			3
#define TYPE_4_NORMAL			4
#define TYPE_5_NORMAL			5
#define TYPE_6_NORMAL			6
#define TYPE_7_NORMAL			7
#define TYPE_NORMAL				0
#define TYPE_FIRE_4				1
#define TYPE_FIRE_5				2
#define SIZE_X					10
#define SIZE_Y					10
#define TOP						60
#define RIGHT					200
#define WIDTH					60
#define HEIGHT					60



@interface PlayViewController : UIViewController {
	//----- mode game.
	int modeGame;
	int exchallenge;
	
	int stateSound;
	UIButton *soundBtn;
	//--------------------------------------------------
	IBOutlet UIButton *homeBtn;
	IBOutlet UIButton *playBtn;
	IBOutlet UIButton *reloadBtn;
	//--------------------------------------------------
	IBOutlet UILabel *styleLb;
	int style;
	IBOutlet UIImageView *levelLb;
	int level;
	IBOutlet UILabel *scoreLb;
	int score;
	int totalScore;
	
	IBOutlet UILabel *modeLb;
	int mode;
	int start;
	int end;
	
	
	//--------------------------------------------------
	IBOutlet UIImageView *imageRondom;
	IBOutlet UIImageView *imageStar1;
	IBOutlet UIImageView *imageStar2;
	IBOutlet UIImageView *imageStar3;
	//--------------------------------------------------
	NSMutableArray *ArrImages;
	
	NSMutableArray *ArrFare;
	
	NSMutableArray *ArrFare1;
	NSMutableArray *ArrFare2;
	
	NSMutableArray *ArrMove;
	
	//--------------------------------------------------
	UIButton *buttonStart;
	UIButton *buttonEnd;
	UIButton *buttonDragStart;
	UIButton *buttonDragEnd;
	
	int vector;
	
	//--- For Press.
	UIButton *tempBtn1, *tempBtn2;
	CGRect rect1, rect2;
	int index1, index2;
	int tagOfStart, tagOfEnd;
	
	//--------
	BOOL isRun;
	BOOL isDrag;
	
	int numberBtnNeedFill;
	
	UIButton *btnSuggest;
	
	//---- 
	IBOutlet UIView *settingView;
	
	
	//=========== For Setting.


	
	//---- Time & Score.
	UIProgressView		*progressBar;
	int totalTimePlay;
	int timePlaying;
	
	NSTimer *playTimer;
	
	UIButton *btnHintClick;
	BOOL hintClick;
	
	
	//---- Test.
	UIButton *btnTest;
	int indexTemp; 
	UIButton *tempBtnTest1;
	UIButton *tempBtnTest2;
	
	//tvuong1 add
	Point1 * board1[19];
	UIButton * boardButton1[19];
	
	Point1 *board[SIZE_X][SIZE_Y];
	UIButton * boardButton[SIZE_X][SIZE_Y];
	UIImageView *boardImage[SIZE_X][SIZE_Y];
	int life;
	
	UITextView *tVLife;
	
}

@property (nonatomic, readwrite) IBOutlet UITextView *tVLife;
//--- Test.
@property (nonatomic, readwrite) int indexTemp;
@property (nonatomic, retain) UIButton *tempBtnTest1;
@property (nonatomic, retain) UIButton *tempBtnTest2;

//---------------- For Setting.----------

//-------- TimePlay & Score. ------------
@property (nonatomic, retain, readonly) UIProgressView *progressBar;
@property (nonatomic, readwrite) int timePlaying;
@property (nonatomic, readwrite) int totalTimePlay;
//----------------------------


@property(nonatomic, readwrite) int modeGame;
@property(nonatomic, readwrite) int exchallenge;
@property(nonatomic, readwrite) int stateSound;
@property(nonatomic, retain) IBOutlet UIButton *soundBtn;

@property(nonatomic, retain) IBOutlet UIButton *btnSuggest;
@property(nonatomic, retain) IBOutlet UIButton *btnHintClick;
@property(nonatomic, readwrite) int numberBtnNeedFill;


@property(nonatomic, readwrite) int index1;
@property(nonatomic, readwrite) int index2;

@property(nonatomic, readwrite) int tagOfStart;
@property(nonatomic, readwrite) int tagOfEnd;


@property(nonatomic, readwrite) int vector;

@property(nonatomic, retain) IBOutlet UIButton *homeBtn;
@property(nonatomic, retain) IBOutlet UIButton *playBtn;
@property(nonatomic, retain) IBOutlet UIButton *reloadBtn;

@property(nonatomic, retain) IBOutlet UILabel *styleLb;
@property(nonatomic, readwrite) int style;

@property(nonatomic, retain) IBOutlet UIImageView *levelLb;
@property(nonatomic, readwrite) int level;

@property(nonatomic, retain) IBOutlet UILabel *scoreLb;
@property(nonatomic, readwrite) int score;
@property(nonatomic, readwrite) int totalScore;

@property(nonatomic, retain) IBOutlet UILabel *modeLb;
@property(nonatomic, readwrite) int mode;


@property (nonatomic, retain) NSMutableArray *ArrImages;

@property (nonatomic, retain) NSMutableArray *ArrFare;
@property (nonatomic, retain) NSMutableArray *ArrFare1;
@property (nonatomic, retain) NSMutableArray *ArrFare2;
@property (nonatomic, retain) NSMutableArray *ArrMove;


- (id)initWithNibName:(NSString *)nibNameOrNil 
			   bundle:(NSBundle *)nibBundleOrNil 
		 withGameMode:(int)gameMode;
// truong add
-(void) DrawBoard:(int)aStyle withMode:(int)aMode;
-(void) createBoard;
-(void)reloadBoard;
-(void) startRotate:(UIButton*)btn Number:(int) number X:(int)x Y:(int)y;
-(void)	ButtonPressed:(id)sender;
-(NSMutableArray *)getPointSame :(Point1 *)p;
///
-(Point1 *)createPoint :(int)x Y:(int)y TP:(int)type MG:(int)magic;

////

-(BOOL) canGrow;
-(Point1 *)getPoint :(UIButton *) btn;
-(void) down :(int) column;
-(BOOL) isSame :(int)x1 Y1:(int )y1 X2:(int) x2 Y2:(int) y2;

@end
