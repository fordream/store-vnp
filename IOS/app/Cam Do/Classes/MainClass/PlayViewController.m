

#import "PlayViewController.h"
#import "SegmentViewController.h"
#import "WinGameController.h"
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
	life = 30;
	self.navigationController.navigationBarHidden = YES;
	
	[self DrawBoard:0 withMode:3];
	//[self createBoard];
	//[self reloadBoard];
}




-(void) DrawBoard:(int)aStyle withMode:(int)aMode{	
	
	for(int i = 0; i < 19; i ++){
		board1[i] = [[Point1 alloc]init];
		board1[i].type = i;
		boardButton1[i] = [UIButton buttonWithType:UIButtonTypeCustom];
		// add press lissener.
		[(boardButton1[i]) addTarget:self action:@selector(ButtonPressed:) forControlEvents:UIControlEventTouchUpInside];
	}
	
	board1[0].x = RIGHT + WIDTH; 
	board1[0].y = TOP;
	board1[1].x = RIGHT + WIDTH * 2; 
	board1[1].y = TOP;
	board1[2].x = RIGHT + WIDTH * 3; 
	board1[2].y = TOP;
	
	board1[3].x = RIGHT + WIDTH/2; 
	board1[3].y = TOP + HEIGHT;
	board1[4].x = RIGHT + WIDTH/2 + WIDTH; 
	board1[4].y = TOP + HEIGHT;
	board1[5].x = RIGHT + WIDTH/2 + WIDTH * 2; 
	board1[5].y = TOP + HEIGHT;
	board1[6].x = RIGHT + WIDTH/2 + WIDTH * 3; 
	board1[6].y = TOP + HEIGHT;
	
	
	board1[7].x = RIGHT; 
	board1[7].y = TOP + HEIGHT * 2 ;
	board1[8].x = RIGHT + WIDTH; 
	board1[8].y = TOP + HEIGHT * 2 ;
	board1[9].x = RIGHT + WIDTH * 2; 
	board1[9].y = TOP + HEIGHT * 2 ;
	board1[10].x = RIGHT + WIDTH * 3; 
	board1[10].y = TOP + HEIGHT * 2 ;
	board1[11].x = RIGHT + WIDTH * 4; 
	board1[11].y = TOP + HEIGHT * 2 ;
	
	board1[12].x = RIGHT + WIDTH / 2; 
	board1[12].y = TOP + HEIGHT * 3 ;
	board1[13].x = RIGHT + WIDTH / 2 + WIDTH; 
	board1[13].y = TOP + HEIGHT * 3 ;
	board1[14].x = RIGHT + WIDTH / 2 + WIDTH * 2; 
	board1[14].y = TOP + HEIGHT * 3 ;
	board1[15].x = RIGHT + WIDTH / 2 + WIDTH * 3; 
	board1[15].y = TOP + HEIGHT * 3 ;
	
	board1[16].x = RIGHT + WIDTH ; 
	board1[16].y = TOP + HEIGHT * 4 ;
	board1[17].x = RIGHT + WIDTH + WIDTH; 
	board1[17].y = TOP + HEIGHT * 4 ;
	board1[18].x = RIGHT + WIDTH + WIDTH * 2; 
	board1[18].y = TOP + HEIGHT * 4 ;
	
	for(int i = 0; i < 19; i ++){
		UIImage *img;
		if(i < 4)
			img = [UIImage imageNamed:@"10.png"];
		else if(i < 9)
			img = [UIImage imageNamed:@"20.png"];
		else if(i < 13)
			img = [UIImage imageNamed:@"30.png"];
		else
			img = [UIImage imageNamed:@"50.png"];
		boardButton1[i].frame = CGRectMake(board1[i].x, board1[i].y, WIDTH, HEIGHT);
		[(boardButton1[i]) setImage:img forState:UIControlStateNormal];
		[super.view addSubview:(boardButton1[i])];
	}
}

	
-(void)	ButtonPressed:(id)sender{
	BOOL canRontate = NO;
	int position = -1 ;
	for(int i =0; i < 19; i ++){
		if(sender == boardButton1[i]){
			canRontate = YES;
			position = i;
			break;
		}
	}
	
	//NSLog(@"position %d type %d",position, board1[position].type);
		
	if(canRontate){
		[self rontate : position Direction:NO];
	}
}

-(void)rontate:(int)position Direction:(BOOL)direction{
	NSMutableArray * list =[self getList:position];
	
	//NSLog(@"Position %d",position );
	if(list.count == 0){
		return;
	}
	int _list[6];
	
	for(int i = 0; i < 6; i ++){
		Point1 * p = [list objectAtIndex:i];
		_list[i] = p.x;
	}
	
	[self rontate1:_list Direction:direction];
	
}

-(void) rontate1:(int[])list Direction:(BOOL)direction{
	NSLog(@"FIRST 1 = %d 5 = %d" ,board1[list[0]].type, board1[list[5]].type);
	if(!direction){
		int  temp = board1[list[0]].type;
		board1[list[0]].type = board1[list[1]].type;
		board1[list[1]].type = board1[list[2]].type;
		board1[list[2]].type = board1[list[3]].type;
		board1[list[3]].type = board1[list[4]].type;
		board1[list[4]].type = board1[list[5]].type;
		board1[list[5]].type = temp;
		
	}else{
		int temp =board1[list[5]].type;
		board1[list[5]].type = board1[list[4]].type;
		board1[list[4]].type = board1[list[3]].type;
		board1[list[3]].type = board1[list[2]].type;
		board1[list[2]].type = board1[list[1]].type;
		board1[list[1]].type = board1[list[0]].type;
		board1[list[0]].type =temp;
		
	}
	NSLog(@"END 1 = %d 5 = %d" ,board1[list[0]].type, board1[list[5]].type);
	if(direction){
		UIButton *tempBtm = boardButton1[list[5]];
		boardButton1[list[5]] = boardButton1[list[4]];
		boardButton1[list[4]] = boardButton1[list[3]];
		boardButton1[list[3]] = boardButton1[list[2]];
		boardButton1[list[2]] = boardButton1[list[1]];
		boardButton1[list[1]] = boardButton1[list[0]];
		boardButton1[list[0]] = tempBtm;
	}else{
		UIButton *tempBtm = boardButton1[list[0]];
		boardButton1[list[0]] = boardButton1[list[1]];
		boardButton1[list[1]] = boardButton1[list[2]];
		boardButton1[list[2]] = boardButton1[list[3]];
		boardButton1[list[3]] = boardButton1[list[4]];
		boardButton1[list[4]] = boardButton1[list[5]];
		boardButton1[list[5]] = tempBtm;
	}
	for(int i = 0; i < 6; i ++){
		CGRect rect2 = CGRectMake(board1[list[i]].x ,board1[list[i]].y,WIDTH,HEIGHT);
		[UIView beginAnimations:nil context:boardButton1[list[i]]];		
		[UIView setAnimationDuration:0.1];		
		[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];	
		boardButton1[list[i]].frame = rect2;
		[UIView setAnimationDelegate:self];	
		[UIView commitAnimations];
	}
	
	

}

-(NSMutableArray *)getList:(int)position{
	NSMutableArray* list = [[NSMutableArray alloc]initWithCapacity:0];
	Point1 *p[6];
	p[0] = [[Point1 alloc]init];
	p[1] = [[Point1 alloc]init];
	p[2] = [[Point1 alloc]init];
	p[3] = [[Point1 alloc]init];
	p[4] = [[Point1 alloc]init];
	p[5] = [[Point1 alloc]init];
	if(position == 4){
		p[0].x = 0;
		p[1].x = 1;
		p[2].x = 5;
		p[3].x = 9;
		p[4].x = 8;
		p[5].x = 3;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 5){
		p[0].x = 1;
		p[1].x = 2;
		p[2].x = 6;
		p[3].x = 10;
		p[4].x = 9;
		p[5].x = 4;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 8){
		
		p[0].x = 3;
		p[1].x = 4;
		p[2].x = 9;
		p[3].x = 13;
		p[4].x = 12;
		p[5].x = 7;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 9){
		p[0].x = 4;
		p[1].x = 5;
		p[2].x = 10;
		p[3].x = 14;
		p[4].x = 13;
		p[5].x = 8;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 10){
		p[0].x = 5;
		p[1].x = 6;
		p[2].x = 11;
		p[3].x = 15;
		p[4].x = 14;
		p[5].x = 9;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 13){
		
		p[0].x = 8;
		p[1].x = 9;
		p[2].x = 14;
		p[3].x = 17;
		p[4].x = 16;
		p[5].x = 12;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 14){
		p[0].x = 9;
		p[1].x = 10;
		p[2].x = 15;
		p[3].x = 18;
		p[4].x = 17;
		p[5].x = 13;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}
	
	
	return list;
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
