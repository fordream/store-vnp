    //
//  PlayViewController.m
//  DiamondGame
//
//  Created by hnsunflower1807 on 6/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PlayViewController.h"
#import "SegmentViewController.h"

#import "Point1.h"


@implementation PlayViewController

@synthesize modeGame, exchallenge;

@synthesize homeBtn, playBtn, reloadBtn, styleLb, style, levelLb, level, scoreLb, score, modeLb, mode;
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

@synthesize indexTemp;

 
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
	self.navigationController.navigationBarHidden = YES;
	
	[self DrawBoard:0 withMode:3];
	[self createBoard];
	[self reloadBoard];
}


-(void) DrawBoard:(int)aStyle withMode:(int)aMode{
	start = -1;
	int width = 243;
	int height = 25;
	for(int x = 0; x < SIZE8; x ++)
	{
		for(int y = 0; y < SIZE8 ; y ++)
		{
			board[x][y] = [[Point1 alloc] init];
			board[x][y].type = TYPE_0_NORMAL;
			board[x][y].magic = TYPE_NORMAL;
			
			boardButton[x][y] = [UIButton buttonWithType:UIButtonTypeCustom];
			boardButton[x][y].frame = CGRectMake(width, height, 89, 84);
			
			// add press lissener.
			[(boardButton[x][y]) addTarget:self action:@selector(ButtonPressed:) forControlEvents:UIControlEventTouchUpInside];
			// add drag listener
			[(boardButton[x][y]) addTarget:self action:@selector(wasDragged:withEvent:) forControlEvents:UIControlEventTouchDragInside];
			
			[super.view addSubview:(boardButton[x][y])];
			width = width + 89;
			
			UIImage *img = [UIImage imageNamed:@"b0.png"];
			
			[(boardButton[x][y]) setImage:img forState:UIControlStateNormal];
		}
		
		height = height + 84;
		width = 243;
	}
}

-(void)reloadBoard{
	for(int i = 0; i < SIZE8; i ++)
	{
		for(int j = 0; j < SIZE8; j ++)
		{
			UIButton *btn = boardButton[i][j];
			[self startRotate:btn Number:4 X:i Y:j];
		}
	}
}

-(void) startRotate:(UIButton*)btn Number:(int) number X:(int)x Y:(int)y{
	int magic = board[x][y].magic;
	int type = board[x][y].type;
	NSString *end1 = @"0";
	NSString *end2 = @"0";
	NSString *end3 = @"0";
	NSString *end4 = @"0";
	NSString *name = @"";
	NSString *name1 = @"";
	NSString *name2 = @"";
	NSString *name3 = @"";
	NSString *name4 = @"";
	
	NSString *first = @"";
	
	if(type == TYPE_1_NORMAL)		name = [NSString stringWithFormat:@"b"];
	else if(type == TYPE_2_NORMAL)	name = [NSString stringWithFormat:@"g"];
	else if(type == TYPE_3_NORMAL)	name = [NSString stringWithFormat:@"o"];
	else if(type == TYPE_4_NORMAL)	name = [NSString stringWithFormat:@"p"];
	else if(type == TYPE_5_NORMAL)	name = [NSString stringWithFormat:@"r"];
	else if(type == TYPE_6_NORMAL)	name = [NSString stringWithFormat:@"w"];
	else if(type == TYPE_7_NORMAL)	name = [NSString stringWithFormat:@"y"];
	
	if(magic == TYPE_FIRE_4)
	{
		if(buttonStart == btn)		first = [NSString stringWithFormat:@"rotateFire"];
		else						first = [NSString stringWithFormat:@"fire"];
	}else if(magic == TYPE_FIRE_5){
		if(buttonStart == btn)		first = [NSString stringWithFormat:@"orb"];
		else						first = [NSString stringWithFormat:@"orb"];
	}else if(magic == TYPE_NORMAL){
		if(buttonStart == btn)		first = [NSString stringWithFormat:@""];
		else						first = [NSString stringWithFormat:@""];
	}
	
	if(buttonStart == btn){
		end1 = @"1";
		end2 = @"2";
		end3 = @"3";
		end4 = @"4";
	}
	
	name1 = [NSString stringWithFormat:@"%@%@%@.png",first,name,end1];
	name2 = [NSString stringWithFormat:@"%@%@%@.png",first,name,end2];
	name3 = [NSString stringWithFormat:@"%@%@%@.png",first,name,end3];
	name4 = [NSString stringWithFormat:@"%@%@%@.png",first,name,end4];
	
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

-(void) createBoard{
	while(true){
		
		for(int i = 0; i < SIZE8; i ++)
		{
			for(int j = 0; j < SIZE8; j ++)
			{
				int k = arc4random() % 7 + 1;
				
				board[i][j].type = k;
				board[i][j].magic = TYPE_NORMAL;
			}
		}
		
		NSMutableArray * eat = [self haveEat :board];
		NSMutableArray * canGrow = [self canGrow];
		if(eat.count == 0 && canGrow.count > 0)	break;
		
	}
}
//------------------------------------------------
-(NSMutableArray*) haveEat :(Point1 *[][8])board1{
	
	NSMutableArray *arrayList = [[NSMutableArray alloc]initWithCapacity:0];
	
	for(int i = 0; i < SIZE8 ; i ++)
	{
		for(int j = 0; j <SIZE8; j ++)
		{
			NSMutableArray *array = [self getArray:i Y:j BOARD:board1];
			for(int k = 0; k < array.count; k ++){
				Point1 *temp = [array objectAtIndex:k];
				if(![self isExists:arrayList POINT:temp]){
					[arrayList addObject:temp];
				}
			}
		}
	}
	
	return arrayList;
}

-(BOOL)isExists : (NSMutableArray *)array POINT:(Point1*) point{
	for(int i = 0; i < array.count; i ++){
		Point1 * temp = [array objectAtIndex:i];
		if(temp.x == point.x && temp.y == point.y) return YES;
	}
	
	return NO;
}


-(NSMutableArray *) getArray :(int)x Y:(int)y BOARD:(Point1*[][8])board1 {
	NSMutableArray * array = [[NSMutableArray alloc] initWithCapacity:0];
	
	//if(x == SIZE8 - 1 || x == SIZE8 - 1)
	Point1 *present = board1[x][y];
	present.x = x;
	present.y = y;
	
	int count = 1;
	for(int i = y + 1; i <= SIZE8; i ++){
		if(i < SIZE8 && board1[x][i].type == present.type){
			count ++;
		}else{
			if(count >= 3){
				for(int j = y ; j < y + count ; j ++){
					Point1 * temp = [[Point1 alloc] init];
					temp.x = x;
					temp.y = j;
					temp.type = board1[x][j].type;
					temp.magic =board1[x][j].magic; 
					[array addObject:temp];
				}
			}
			break;
		}
	}
	
	count = 1;
	for(int i = x + 1; i <= SIZE8; i ++){
		if(i < SIZE8 && board1[i][y].type == present.type){
			count ++;
		}else{
			if(count >= 3){
				for(int j = x ; j < x + count ; j ++){
					Point1 * temp = [[Point1 alloc] init];
					temp.x = j;
					temp.y = y;
					temp.type = board1[j][y].type;
					temp.magic =board1[j][y].magic;
					[array addObject:temp];
				}
			}
			break;
		}
	}
	
	return array;
}  
	
-(void)	ButtonPressed:(id)sender{
	//[self a : board];
	
	return;
	//int  x = [self getX:(UIButton *)sender];
//	int  y = [self getY:(UIButton *)sender];
//	Point1 * p =[self createPoint:x Y:y TP:TYPE_0_NORMAL MG:TYPE_NORMAL];
//	
//	NSMutableArray * array =[self canGrow1 : p];
//	
//	if(array.count == 0){
//		NSLog(@" %d ",array.count);
//		return;
//	}else if(array.count > 0){
//		NSLog(@" %d ",array.count);
//		return;
//	}
//	if([self getArray:x Y:y].count == 0){
//		NSLog(@" %d ",[self getArray:x Y:y].count);
//		return;
//	}else if([self getArray:x Y:y].count > 0){
//		NSLog(@" %d ",[self getArray:x Y:y].count);
//		return;
//	}
//	
//	if(buttonStart == nil){
//		buttonStart = (UIButton *)sender;
//		[self reloadBoard];
//	}else if(((UIButton *)sender) == buttonStart){
//		buttonStart = nil;
//		[self reloadBoard];
//	}else if([self isBeside: buttonStart BTN2: ((UIButton *)sender)])
//	{
//		//NSLog(@"o ben canh roi");
//		
//		int x1 = [self getX:buttonStart];
//		int y1 = [self getY:buttonStart];
//		int x2 = [self getX:(UIButton *)sender];
//		int y2 = [self getY:(UIButton *)sender];
//		buttonEnd = (UIButton *)sender;
//		
//		Point1 *temp = [[Point1 alloc]init];
//		temp = board[x1][y1];
//		board[x1][y1] = board[x2][y2];
//		board[x2][y2] = temp;
//		
//		if(![self haveEat]){
//			//[self unEnable];
//			NSLog(@"Vao day roi");
//			Point1 *temp = [[Point1 alloc]init];
//			temp = board[x1][y1];
//			board[x1][y1] = board[x2][y2];
//			board[x2][y2] = temp;
//			
//			tempBtn1 = buttonStart;
//			tempBtn2 = buttonEnd;
//			buttonStart = nil;
//			
//			[self reloadBoard];
//			
//			[self ChangeStateOf2Btn:tempBtn1 andButton:tempBtn2];
//			
//			[self performSelector:@selector(Run2) withObject:nil afterDelay:TIME3];
//			
//			[self performSelector:@selector(enable) withObject:nil afterDelay:TIME7];
//			[self performSelector:@selector(reInitial) withObject:nil afterDelay:TIME10];
//
//		}else{
//		
//		}
//		//[self ChangeStateOf2Btn:buttonEnd andButton:buttonStart];
//		// chuyen frame: 1-2, 2-1
//		
//		
//	}else{
//		buttonStart = (UIButton *)sender;
//		[self reloadBoard];
//	}
}
-(void) reInitial{
	buttonStart = nil;
	buttonEnd = nil;
}

-(void)enable{
	for(int i = 0; i < SIZE8; i ++)
		for(int j = 0; j < SIZE8; j ++)
			boardButton[i][i].userInteractionEnabled = YES;
}

-(void)unEnable{
	for(int i = 0; i < SIZE8; i ++)
		for(int j = 0; j < SIZE8; j ++)
			boardButton[i][i].userInteractionEnabled = NO;
}

-(void)Run2{
	if(![self haveEat]){
		[self ChangeStateOf2Btn: tempBtn2 andButton:tempBtn1];
	}
}

-(void) ChangeStateOf2Btn:(UIButton *)btn1 andButton:(UIButton *)btn2 {
	tempBtn1 = btn1;
	tempBtn2 = btn2;
	rect1 = btn1.frame;
	rect2 = btn2.frame;
	[self performSelector:@selector(FirstRun) withObject:nil afterDelay:TIME0];
	[self performSelector:@selector(SecondRun) withObject:nil afterDelay:TIME0];
}

-(void) FirstRun {
	[UIView beginAnimations:nil context:tempBtn1];
	[UIView setAnimationDuration:0.5];
	[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
	
	tempBtn1.frame = rect2;
	
	[UIView setAnimationDelegate:self];
	[UIView commitAnimations];
}

//------------------------------------------------
-(void) SecondRun {
	[UIView beginAnimations:nil context:tempBtn2];
	[UIView setAnimationDuration:0.5];
	[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
	
	tempBtn2.frame = rect1;
	
	[UIView setAnimationDelegate:self];
	[UIView commitAnimations];
}

-(BOOL) isBeside :(UIButton * )btn1 BTN2 :(UIButton *)btn2
{
	int x1 = [self getX:btn1];
	int y1 = [self getY:btn1];
	int x2 = [self getX:btn2];
	int y2 = [self getY:btn2];
	
	return x1 == x2 && (y1 - y2 == 1 || y1 - y2 == -1) 
		|| y2 == y1 &(x1 - x2 == 1 ||x1 - x2 == -1);
}

-(int )getX :(UIButton *)btn
{
	for(int i = 0; i < SIZE8 ; i ++)
		for(int j = 0; j < SIZE8 ; j ++)
			if(btn == boardButton[i][j])
				return i;
	
	return - 1;
}

-(int )getY :(UIButton *)btn
{
	for(int i = 0; i < SIZE8 ; i ++)
		for(int j = 0; j < SIZE8 ; j ++)
			if(btn == boardButton[i][j])
				return j;
	
	return - 1;
}
-(void) wasDragged :(UIButton *)button withEvent:(UIEvent *)event {}

-(NSMutableArray*) canGrow{
	Point1 *tempBoard[SIZE8][SIZE8];			
	for(int i = 0; i < SIZE8; i ++)
	{
		for(int j = 0; j < SIZE8; j ++){
			Point1 *p = board[i][j];
			tempBoard[i][j] = [self createPoint:p.x Y:p.y TP:p.type MG:p.magic];
		}
	}
	
	NSMutableArray * array = [[NSMutableArray alloc]initWithCapacity:0];
	
	for(int x = 0; x < SIZE8; x ++){
		for(int y = 0; y <SIZE8; y ++){
			if(x >= 1){
				Point1 *temp = tempBoard[x][y];
				
				tempBoard[x][y] = tempBoard[x - 1][y];
				tempBoard[x-1][y] = temp;
				
				array = [self haveEat:tempBoard];
				
				if(array.count > 0) return array;
				
				temp = tempBoard[x][y];
				tempBoard[x][y] = tempBoard[x - 1][y];
				tempBoard[x-1][y] = temp;
				
				array = [self haveEat:tempBoard];
			}
			
			if(x < SIZE8 - 1){
				Point1 *temp = tempBoard[x][y];
				
				tempBoard[x][y] = tempBoard[x + 1][y];
				tempBoard[x+1][y] = temp;
				
				array = [self haveEat:tempBoard];
				
				if(array.count > 0) return array;
				
				temp = tempBoard[x][y];
				tempBoard[x][y] = tempBoard[x + 1][y];
				tempBoard[x+1][y] = temp;
				
				array = [self haveEat:tempBoard];
			}
			if(y  >=1 ){
				Point1 *temp = tempBoard[x][y];
				
				tempBoard[x][y] = tempBoard[x][y - 1];
				tempBoard[x][y - 1] = temp;
				
				array = [self haveEat:tempBoard];
				
				if(array.count > 0) return array;
				
				temp = tempBoard[x][y];
				tempBoard[x][y] = tempBoard[x][y - 1];
				tempBoard[x][y - 1] = temp;
				
				array = [self haveEat:tempBoard];
			}
			
			if(y  < SIZE8 ){
				Point1 *temp = tempBoard[x][y];
				
				tempBoard[x][y] = tempBoard[x][y + 1];
				tempBoard[x][y + 1] = temp;
				
				array = [self haveEat:tempBoard];
				
				if(array.count > 0) return array;
				
				temp = tempBoard[x][y];
				tempBoard[x][y] = tempBoard[x][y + 1];
				tempBoard[x][y + 1] = temp;
				
				array = [self haveEat:tempBoard];
			}
			
		}
	}
	
	return array;
}

-(void) a :(Point1* [][2])a1{
	for(int i = 0; i < 2 ; i ++){
		for(int j = 0; j < 2; j ++){
			NSLog(@"%d",(a1[i][j].type));
		}
	}
}

-(NSMutableArray*)getBoardArray{
	NSMutableArray * boardArray = [[NSMutableArray alloc]initWithCapacity:0];
	
	for(int i = 0 ; i < SIZE8 ; i ++){
		for(int j = 0 ; j < SIZE8 ; j ++){
			Point1 * temp = board[i][j];
			[boardArray addObject:[self createPoint:temp.x Y:temp.y TP:temp.type MG:temp.magic]];
			
		}
	}
	
	return boardArray;
}

-(Point1 *)createPoint :(int)x Y:(int)y TP:(int)type MG:(int)magic{
	Point1 * point = [[Point1 alloc]init];
	point.x = x;
	point.y = y;
	point.type = type;
	point.magic = magic;
	return point;
}


-(BOOL) inBoard :(Point1 *)p{
	return 0 <= p.x && p.x < SIZE8 && 0 <= p.y && p.y < SIZE8;
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
