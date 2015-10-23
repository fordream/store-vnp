#import "PlayViewController.h"
#import "SegmentViewController.h"
#import "WinGameController.h"
#import "Point1.h"


@implementation PlayViewController

@synthesize modeGame, exchallenge;

@synthesize homeBtn, playBtn, reloadBtn, levelLb, level, scoreLb, score, modeLb, mode;
@synthesize ArrImages, ArrMove;
@synthesize vector;
@synthesize index1, index2, tagOfStart,tagOfEnd;
@synthesize btnSuggest;
@synthesize numberBtnNeedFill;
@synthesize btnHintClick;
@synthesize stateSound;
@synthesize soundBtn;
@synthesize totalScore;
@synthesize progressBar, timePlaying, totalTimePlay;
@synthesize tempBtnTest1, tempBtnTest2;
@synthesize lifeLb, life;
@synthesize indexTemp,tVLife;

 
@synthesize ArrFare, ArrFare1, ArrFare2;


- (id)initWithNibName:(NSString *)nibNameOrNil  bundle:(NSBundle *)nibBundleOrNil  withGameMode:(int)gameMode	
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
   
	if (self) modeGame = gameMode;
	
    return self;
}



//==========================================================================================================================================
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    
	[super viewDidLoad];
	//--- namadd.
	score = 0;
	NSString *scoreStr = [[NSString alloc] initWithFormat:@"Scores: %d",score];
	[scoreLb setText:scoreStr];
	
	 life = 30;
	NSString *lifeStr = [[NSString alloc] initWithFormat:@"Life: %d",life];
	[lifeLb setText:lifeStr];
	
	level = 1;
	NSString *levelStr = [[NSString alloc] initWithFormat:@"Level: %d",level];
	[levelLb setText:levelStr];
	
	self.navigationController.navigationBarHidden = YES;
	
	[self DrawBoard:0 withMode:3];
	[self createBoard];
	[self reloadBoard];
}

-(void) createBoard{
	int number = 0;
	if(level < 4)
		number = 3;
	else if(level < 6) number = 4;
	else if(level < 8) number = 5;

	while(true){
		
		for(int i = 0; i < SIZE_X; i ++)
		{
			for(int j = 0; j < SIZE_Y; j ++)
			{
				int k = arc4random() % number + 1;
				
				board[i][j].type = k;
				board[i][j].magic = TYPE_NORMAL;
			}
		}
		
		if([self canGrow]){
			break;
		}		
	}
}
-(Point1 *)createPoint :(int)x Y:(int)y TP:(int)type MG:(int)magic{
	Point1 * p = [[Point1 alloc]init];
	p.x = x;
	p.y = y;
	p.type = type;
	p.magic = magic;
	
	return p;
}
-(BOOL) canGrow{
	for(int x = 0; x < SIZE_X - 1; x ++){
		for(int y = 0; y <SIZE_Y - 1; y ++){
			if(board[x][y].type == board[x + 1][y].type && board[x][y].type != TYPE_0_NORMAL){
				return YES;
			}
			
			if(board[x][y].type == board[x][y + 1].type && board[x][y].type != TYPE_0_NORMAL){
				return YES;
			}
		}
	}
	 
	return NO;
}
-(void) DrawBoard:(int)aStyle withMode:(int)aMode{
	start = -1;
	int width = 56;
	int height = 32 + HEIGHT * SIZE_X;
	for(int x = 0; x < SIZE_X; x ++)
	{
		for(int y = 0; y < SIZE_Y ; y ++)
		{
			board[x][y] = [[Point1 alloc] init];
			board[x][y].type = TYPE_0_NORMAL;
			board[x][y].magic = TYPE_NORMAL;
			
			boardButton[x][y] = [UIButton buttonWithType:UIButtonTypeCustom];
			boardButton[x][y].frame = CGRectMake(width, height, 56, 76);
			
			// add press lissener.
			[(boardButton[x][y]) addTarget:self action:@selector(ButtonPressed:) forControlEvents:UIControlEventTouchUpInside];
			// add drag listener
			[(boardButton[x][y]) addTarget:self action:@selector(wasDragged:withEvent:) forControlEvents:UIControlEventTouchDragInside];
			
			[super.view addSubview:(boardButton[x][y])];
			width = width + WIDTH;
			
			UIImage *img = [UIImage imageNamed:@"0.png"];
			
			[(boardButton[x][y]) setImage:img forState:UIControlStateNormal];
		}
		
		height = height - HEIGHT;
		width = 56;
	}
}

-(void)reloadBoard{
	for(int i = 0; i < SIZE_X; i ++)
	{
		for(int j = 0; j < SIZE_Y; j ++)
		{
			UIButton *btn = boardButton[i][j];
			[self startRotate:btn Number:4 X:i Y:j];
		}
	}
}

-(void) startRotate:(UIButton*)btn Number:(int) number X:(int)x Y:(int)y{
	NSString *name = @"";
	NSString *name1 = @"";
	NSString *name2 = @"";
	NSString *name3 = @"";
	NSString *name4 = @"";
	
	//int magic = board[x][y].magic;
	int type = board[x][y].type;
	
	if(type == TYPE_0_NORMAL)		name = [NSString stringWithFormat:@"0"];
	else if(type == TYPE_1_NORMAL)	name = [NSString stringWithFormat:@"1"];
	else if(type == TYPE_2_NORMAL)	name = [NSString stringWithFormat:@"2"];
	else if(type == TYPE_3_NORMAL)	name = [NSString stringWithFormat:@"3"];
	else if(type == TYPE_4_NORMAL)	name = [NSString stringWithFormat:@"4"];
	else if(type == TYPE_5_NORMAL)	name = [NSString stringWithFormat:@"5"];

	name1 = [NSString stringWithFormat:@"%@.png",name];
	name2 = [NSString stringWithFormat:@"%@.png",name];
	name3 = [NSString stringWithFormat:@"%@.png",name];
	name4 = [NSString stringWithFormat:@"%@.png",name];
	
	btn.imageView.animationImages = [NSArray arrayWithObjects:
					[UIImage imageNamed:name1]
				,	[UIImage imageNamed:name2]
				,	[UIImage imageNamed:name3]
				,	[UIImage imageNamed:name4]
				,	nil];

	
	
	[btn.imageView setAnimationRepeatCount:0];
	btn.imageView.animationDuration = TIME4;
	[btn.imageView startAnimating];
	
}

	
-(void)	ButtonPressed:(id)sender{
	Point1 *point = [self getPoint:(UIButton *)sender];
	
	if(point.x == -1 || point.x == -1) return;
	
	//NSLog(@"type = %d ;",board[point.x][point.y].type);;
	if(board[point.x][point.y].type == TYPE_0_NORMAL) return;
	
	
	
	NSMutableArray * array = [self getPointSame : point];
	
	if(array.count > 1){
		for(int i = 0; i < array.count; i ++){
			Point1 * temp = [array objectAtIndex:i];
			board[temp.x][temp.y].type = TYPE_0_NORMAL;
			
			boardButton[temp.x][temp.y].imageView.animationImages = [NSArray arrayWithObjects:
											 [UIImage imageNamed:@"m1.png"]
											 ,	[UIImage imageNamed:@"m2.png"]
											 ,	[UIImage imageNamed:@"m3.png"]
											 ,	[UIImage imageNamed:@"m0.png"]
											 ,	nil];
			[boardButton[temp.x][temp.y].imageView setAnimationRepeatCount:0];
			boardButton[temp.x][temp.y].imageView.animationDuration = TIME4;
			[boardButton[temp.x][temp.y].imageView startAnimating];
			
			
			
		}
		
		score = score + SCORE_NORMAL * array.count * array.count * array.count ;
		NSString *scoreStr = [[NSString alloc] initWithFormat:@"Scores: %d",score];
		[scoreLb setText:scoreStr];
		
		//[self reloadBoard];
		[self performSelector:@selector(reloadBoard) withObject:nil afterDelay:TIME3];
		//----------------------------------------------------
		[self performSelector:@selector(down1) withObject:nil afterDelay:TIME5];
		[self performSelector:@selector(left) withObject:nil afterDelay:TIME7];
	}
	
	[self performSelector:@selector(canGrow11) withObject:nil afterDelay:TIME10];
	return;
}

-(void) canGrow11{
	if(![self canGrow]){
		int notNormal = [self notNormal];
		life = life - notNormal;
		
		NSString *lifeStr = [[NSString alloc] initWithFormat:@"Life: %d",life];
		[lifeLb setText:lifeStr];
		if(life >= 0){
			level = level + 1;
			NSString *levelStr = [[NSString alloc] initWithFormat:@"Level: %d",level];
			[levelLb setText:levelStr];
			[self createBoard];
			[self reloadBoard];
		}else{
			UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Confirmation" 
                                                            message:@"Do you want to continuously play previous level?" 
                                                           delegate:self 
                                                  cancelButtonTitle: @"No" 
                                                  otherButtonTitles:@"Yes", nil];
            [alert show];
            [alert release];
		}
	}
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
	
	if (buttonIndex == 0) {
		
		
	}
	//---- If buttonIndex = 1 <=> Cancel
	if (buttonIndex == 1) {
		
		score = 0;
		NSString *scoreStr = [[NSString alloc] initWithFormat:@"Scores: %d",score];
		[scoreLb setText:scoreStr];
		
		life = 30;
		NSString *lifeStr = [[NSString alloc] initWithFormat:@"Life: %d",life];
		[lifeLb setText:lifeStr];
		
		level = 1;
		NSString *levelStr = [[NSString alloc] initWithFormat:@"Level: %d",level];
		[levelLb setText:levelStr];
		
		[self createBoard];
		[self reloadBoard];
		
		
	}
	
}

-(int) notNormal{
	int count = 0;
	for(int i = 0; i < SIZE_X; i ++){
		for(int j = 0; j < SIZE_X; j ++){
			if(board[i][j].type != TYPE_0_NORMAL ){
				count ++;
			}
		}
	}
	
	return count;
}
-(void)down1{
	for(int y = 0; y < SIZE_Y; y ++){
		[self down:y];
	}
	
	[self reloadBoard];
}

-(void)left{
	for(int y = 0; y < SIZE_Y; y ++){
		if(board[SIZE_X - 1][y].type == TYPE_0_NORMAL){
			for(int y1 = y;y1 < SIZE_Y - 1; y1 ++){
				for(int x = 0; x < SIZE_X ; x ++){
					board[x][y1].type  = board[x][y1 + 1].type;
					board[x][y1 + 1].type = TYPE_0_NORMAL;
				}
			}
			
			
			if(board[SIZE_X - 1][y].type == TYPE_0_NORMAL){
				for(int y1 = y + 1;y1 < SIZE_Y - 1; y1 ++){
					if(board[SIZE_X - 1][y1].type != TYPE_0_NORMAL){
						y --;
						break;
					}
				}
			}
			
			//y --;
		}
		//[self down:y];
	}
	[self reloadBoard];

}
-(void) down :(int) column {
	
	for(int i = SIZE_X - 1; 0 <= i; i --){
		if(board[i][column].type == TYPE_0_NORMAL){
			int k = -1;
			for(int j = i - 1;0<= j; j -- ){
				if(board[j][column].type != TYPE_0_NORMAL){
					k = j;
					break;
				}
			}
			
			if(k == -1) return;
			board[i][column].type = board[k][column].type;
			board[k][column].type = TYPE_0_NORMAL;
		}
	}
	
}

-(BOOL) canDown :(int)column Count:(int) count{
	for(int i = 0; i < SIZE_X ; i ++){
		if(board[i][column].type == TYPE_0_NORMAL){
			count --;
			//if(count == 0)	return NO;
		}else{
			if(count == 0)	return NO;
			else			return YES;

		}
	
	}
	
	return NO;

} 
-(NSMutableArray *)getPointSame :(Point1 *)p{
	NSLog(@"Ngoai %d %d",p.x, p.y);
	NSMutableArray * array = [[NSMutableArray alloc] initWithCapacity:0];
	//int x = p.x;
	//int y = p.y;
	NSLog(@"x = %d   ; y = %d" , p.x, p.y);
	[array addObject:p];
	
	BOOL bBoard[SIZE_X][SIZE_Y];
	//NSLog(@"khong chet1");
	for(int i = 0;   i< SIZE_X ; i ++)
	{
		for(int j = 0 ;  j< SIZE_Y ; j ++){
			bBoard[i][j] = NO;
			
		}
	}
	
	bBoard[p.x][p.y] = YES;
	
	
	while(true){
		
		int count = array.count;
		
		for(int i = 0; i < count; i ++){
			
			Point1 * point = [array objectAtIndex:i];
			int x = point.x;
			int y = point.y;
			
			if([self isSame:x Y1:y X2: (x - 1) Y2:y] && !bBoard[x - 1][y]){
				Point1 * temp = [self createPoint:(x - 1) Y:y TP:0 MG:0];
				[array addObject:temp];
				bBoard[x - 1][y] = YES;
			}
				
			if([self isSame:x Y1:y X2: (x + 1) Y2:y] && !bBoard[x + 1][y]){
				Point1 * temp = [self createPoint:(x + 1) Y:y TP:0 MG:0];
				[array addObject:temp];
				bBoard[x + 1][y] = YES;
			}
				
			if([self isSame:x Y1:y X2: (x) Y2:(y+1)] && !bBoard[x][y + 1]){
				Point1 * temp = [self createPoint:(x) Y:(y+1) TP:0 MG:0];
				[array addObject:temp];
				bBoard[x][y + 1] = YES;
			}
			if([self isSame:x Y1:y X2: (x) Y2:(y-1)] && !bBoard[x][y - 1]){
				Point1 * temp = [self createPoint:(x) Y:(y-1) TP:0 MG:0];
				[array addObject:temp];
				bBoard[x][y - 1] = YES;
			}
		}
		
		if(count == array.count) return array;
	}
	
	
	return array;
}

-(BOOL) isSame :(int)x1 Y1:(int )y1 X2:(int) x2 Y2:(int) y2{
	if(		0 <= x1 && x1 < SIZE_X 
		&&	0 <= x2 && x2 < SIZE_X 
		&&	0 <= y1 && y1 < SIZE_Y 
		&&	0 <= y2 && y2 < SIZE_Y){
		if(board[x1][y1].type == board[x2][y2].type 
		   && board[x1][y1].type != TYPE_0_NORMAL ){
			return YES;
		}
	}
	
	return NO;
}

-(void) wasDragged :(UIButton *)button withEvent:(UIEvent *)event {}

//add truong
-(Point1 *)getPoint :(UIButton *) btn{

	for(int i = 0; i < SIZE_X; i ++)
	{
		for(int j = 0; j < SIZE_Y; j ++)
		{
			if(boardButton[i][j] == btn){
				Point1 *p = board[i][j];

				return [self createPoint:i Y:j TP:p.type MG:p.magic];
			}
		}
	}
	
	return [self createPoint: -1 Y:-1 TP:TYPE_0_NORMAL MG:TYPE_NORMAL];
}
//================================================================================================
- (IBAction) ClickHomeBtn:(id)sender{ [self.navigationController popViewControllerAnimated:YES];}
- (void)viewDidUnload {  [super viewDidUnload];}
- (void)dealloc {
	[ArrImages release];
	
	[ArrFare release];
	//	[ArrMove release];
	
	[homeBtn release];
	[playBtn release];
	[styleLb	release];
	[levelLb	release];
	[scoreLb	release];
	[modeLb	release];	
    [super dealloc];
}

@end
