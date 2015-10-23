    //
//  PlayerController.m
//  GardenSummer
//
//  Created by Truong Vuong on 8/28/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "PlayerController.h"
#import "Point1.h"
#import "DBManager.h"
#import <AVFoundation/AVFoundation.h>
#import "LevelController.h"
#import "MenuController.h"
#import "OverlapView.h"

@implementation PlayerController
@synthesize btnLeft,btnRight,btnBack,btnSetting,btnReplay;
@synthesize tVLevel,tVNumberOfTurns,tVScore;
// The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
   level = 1;
	if (self) {
    }
    return self;
	
}

- (id)initWithNibName:(NSString *)nibNameOrNil level :(int) _level bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
	level = _level;
	if (self) {
        // Custom initialization.
    }
    return self;
}


/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/

- (void)viewDidLoad {
	[super viewDidLoad];
	self.navigationController.navigationBarHidden = YES;
	isClockwise = YES;
	
	maxLevel = [[Common sharedInstance].dbManager getMaxlevel];
	
	minRound = 0;
	minRound = 0;
	numberOfTurns = 0;
	score = 0;
	array = [[NSMutableArray alloc] initWithCapacity:0];
	arrayView = [[NSMutableArray alloc] initWithCapacity:0];
	[self DrawBoard:0];
	[self initBoard:0];
	[self viewLevelAndTurns];
}

-(void) sound {
	//sound.
	NSString *s = [[NSBundle mainBundle] pathForResource:@"rontate" ofType:@"mp3" ];
	
}

//mode == 0 create New Game
//mode == 1 chay lai 
-(void)initBoard:(int)mode{
	canRun = YES;
	if(mode == 0){
		NSMutableArray *_list =  [[Common sharedInstance].dbManager getListStart :level ];
		
		for(int i = 0; i < 19; i ++){
			Point1 * temp = [_list objectAtIndex:(i)];
			boardFirst[i].type = temp.type;
			board1[i].type = temp.type;
		}
		
		Point1 * temp1 = [_list objectAtIndex:(19)];
		maxRound  = temp1.type;
		temp1 = [_list objectAtIndex:(20)];
		minRound  = temp1.type;
		
		if(![Setting1 isTranning]){
			numberOfTurns = maxRound;
		}else{
			numberOfTurns = 0;
		}
		
		_list =  [[Common sharedInstance].dbManager getListEnd :level ];
		for(int i = 0; i < 19; i ++){
			Point1 * temp = [_list objectAtIndex:(i)];
			boardEnd[i].type = temp.type;
		}
	}else if(mode == 1){
		
	}
	
	for(int i = 0; i < 19; i ++){
		NSString *name = [[NSString alloc]initWithFormat:@"%d.png",board1[i].type];
		UIImage *img= [UIImage imageNamed:name];
		[(boardButton1[i]) setImage:img forState:UIControlStateNormal];
		
		NSString *name1 = [[NSString alloc]initWithFormat:@"%d.png",boardEnd[i].type];
		UIImage *img1= [UIImage imageNamed:name1];
		[(imgEnd[i]) setImage:img1 forState:UIControlStateNormal];
	}
	
	//self drawRect:nil];
}

-(BOOL)isWin{
	for(int i = 0; i < 19; i ++){

		if(board1[i].type != boardEnd[i].type){
			return NO;
		}
	}
	return YES;
}

-(void) viewLevelAndTurns{
	NSString * textLevel = [[NSString alloc ] initWithFormat:@"%d",level ];
	NSString * textTurns = [[NSString alloc ] initWithFormat:@"%d",numberOfTurns ];
	NSString * textScore = [[NSString alloc ] initWithFormat:@"%d",score ];
	[tVLevel setText:textLevel] ;
	[tVScore setText:textScore ] ;
	
	if(numberOfTurns > 0){
		[tVNumberOfTurns setText:textTurns] ;
	}else{
		[tVNumberOfTurns setText:@"Turns : 0" ] ;
	}
	
}

-(void) DrawBoard:(int)aStyle{
	for(int i = 0; i < 19; i ++){
		NSString *name = [[NSString alloc]initWithFormat:@"0.png"];
		
		boardImage[0] = [UIImage imageNamed:name];
		
		imgEnd[i] =[UIButton buttonWithType:UIButtonTypeCustom];
		
		boardEnd[i] = [[Point1 alloc]init];
		
		boardFirst[i] = [[Point1 alloc]init];
		board1[i] = [[Point1 alloc]init];
		board1[i].type = 0;
		
		boardButton1[i] = [UIButton buttonWithType:UIButtonTypeCustom];
		[boardButton1[i] setEnabled:NO];
		// add press lissener.
		[(boardButton1[i]) addTarget:self action:@selector(ButtonPressed:) forControlEvents:UIControlEventTouchUpInside];
	}
		
	for(int i = 0; i < 19; i ++){
		if(i < 3){
			boardEnd[i].x = LEFT_END + WIDTH_END * ( i );
			boardEnd[i].y = TOP_END ;
		}else if(i < 7){
			boardEnd[i].x = LEFT_END  + WIDTH_END/2 + WIDTH_END * ( i - 4);
			boardEnd[i].y = TOP_END + HEIGHT_END;
		}else if( i < 12){
			boardEnd[i].x = LEFT_END  + WIDTH_END * ( i - 8);
			boardEnd[i].y = TOP_END  + HEIGHT_END *2;
		}else if(i < 16){
			boardEnd[i].x = LEFT_END  + WIDTH_END/2 + WIDTH_END * ( i - 13);
			boardEnd[i].y = TOP_END  + HEIGHT_END * 3;
		}else{
			boardEnd[i].x = LEFT_END  + WIDTH_END + WIDTH_END * ( i - 17);
			boardEnd[i].y = TOP_END  + HEIGHT_END * 4;
		}
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
		NSString *name = [[NSString alloc]initWithFormat:@"0.png"];
		UIImage *img= [UIImage imageNamed:name];
		boardButton1[i].frame = CGRectMake(board1[i].x, board1[i].y, WIDTH, HEIGHT);
		[(boardButton1[i]) setImage:img forState:UIControlStateNormal];
		[super.view addSubview:(boardButton1[i])];
		
		img= [UIImage imageNamed:name];
		[(imgEnd[i]) setImage:img forState:UIControlStateNormal];
		imgEnd[i].frame = CGRectMake(boardEnd[i].x, boardEnd[i].y, WIDTH_END, HEIGHT_END);
		[super.view addSubview:(imgEnd[i])];
	
	}
	
	//[self drawRect1: nil];
}

-(void)	ButtonPressed:(id)sender{
	
	if(!canRun){
		return;
	}

	int position = -1 ;
	for(int i =0; i < 19; i ++){
		if(sender == boardButton1[i]){
			//canRontate = YES;
			position = i;
			break;
		}
	}
	
	if(position == 4 ||position == 5 ||position == 8 ||position == 9 ||position == 10 ||
	   position == 13 ||position == 14 ){
		
		if([Setting1 canSoundEffect]){
			[[SoundCommon soundRontate]play];
		}
		
		[self rontate : position Direction:NO];
		
		if(numberOfTurns > 0){
			numberOfTurns --;
		}
		
		[self viewLevelAndTurns];
		
		if([self isWin]){
			canRun = NO;
			
			if([Setting1 canSoundEffect]){
				[[SoundCommon soundLevelup]play];
			}
			
			if(level == maxLevel){
				//win
				[[Common sharedInstance].dbManager updateStore :level status:3];
				LevelController *_view1 = [[LevelController alloc] initWithNibName:@"level" bundle:nil];
				[self.navigationController pushViewController:_view1 animated:YES];
			}else{
				[self performSelector:@selector(win) withObject:nil afterDelay:0.5];
			}
		}else{
			if(![Setting1 isTranning]){
				if(numberOfTurns  <= 0){
					canRun = NO;
					if([Setting1 canSoundEffect]){
						[[SoundCommon soundGameOver]play];
					}
				
					[self performSelector:@selector(lost) withObject:nil afterDelay:1];
				}
			}
		}
	}else{
		if([Setting1 canSoundEffect]){
			[[SoundCommon soundMenu]play];
		}
	}
}

-(void)lost{
	LevelController *_view1 = [[LevelController alloc] initWithNibName:@"level" bundle:nil];
	[self.navigationController pushViewController:_view1 animated:YES];
}

-(void)win{
	[[Common sharedInstance].dbManager updateStore :level status:2];
	LevelController *_view1 = [[LevelController alloc] initWithNibName:@"level" bundle:nil];
	[self.navigationController pushViewController:_view1 animated:YES];
}

-(void)rontate:(int)position Direction:(BOOL)direction{
	NSMutableArray * list =[self getList:position];
	
	if(list.count == 0){
		return;
	}
	int _list[6];
	
	for(int i = 0; i < 6; i ++){
		Point1 * p = [list objectAtIndex:i];
		_list[i] = p.x;
	}
	
	[self rontate1:_list Direction:isClockwise];
	
}


-(void)rontate0:(int)position Direction:(BOOL)direction{
	NSMutableArray * list =[self getList:position];
	
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
	//NSLog(@"FIRST 1 = %d 5 = %d" ,board1[list[0]].type, board1[list[5]].type);
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


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    if (interfaceOrientation == UIInterfaceOrientationLandscapeLeft || interfaceOrientation == UIInterfaceOrientationLandscapeRight) {
        return YES;
    }
    else {
        return NO;
    }
}

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc. that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

-(void)	ButtonPressedManager:(id)sender{
	
	if(!canRun){
		return;
	}
	
	if([Setting1 canSoundEffect]){
		[[SoundCommon soundMenu] play];
	}
	
	if(sender == btnBack){
		MenuController *_view = [[MenuController alloc] initWithNibName:@"menu" bundle:nil];
		[self.navigationController pushViewController:_view animated:YES];
		//[self.navigationController popViewControllerAnimated:YES];
	}else if(sender ==btnRight){
		isClockwise = YES;
	}else if(sender == btnLeft){
		isClockwise = NO;
	}else if(sender == btnSetting){
		level ++;
		numberOfTurns = 20;
		[self viewLevelAndTurns];
		[self initBoard:0];
	}else if(sender == btnReplay){
		numberOfTurns = 20;
		[self initBoard:0];
		[self viewLevelAndTurns];
	}
}

-(void)addViewImage :(int)x y:(int)y{
	NSString *name = [[NSString alloc]initWithFormat:@"1.png"];
	UIImage *img= [UIImage imageNamed:name];
	UIImageView *imgView = [[UIImageView alloc]initWithImage:img];
	CGRect newFrame = CGRectMake(x, y, 10, 10);;
	imgView.frame = newFrame;
	
	[arrayView addObject:imgView];
	
	[super.view addSubview:imgView];
}
-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	
	if (canRun == NO){
		return;
	}
	

	
	
	
	UITouch *touch = [touches anyObject];
	CGPoint position = [touch locationInView:self.view];

	int x = position.x;
	int y = position.y;
	
	[self addViewImage:x y:y];
	
	
	Point1 *p = [[Point1 alloc]init];
	
	p.x = x; p.y = y;
	
	[array addObject:p];
}


-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
	if (canRun == NO){
		return;
	}
	
	UITouch *touch = [touches anyObject];
	CGPoint position = [touch locationInView:self.view];
	
	int x = position.x;
	int y = position.y;
	Point1 *p = [[Point1 alloc]init];
	
	p.x = x; p.y = y;
	
	[array addObject:p];
	[self addViewImage:x y:y];
	for(int i = 0 ; i < arrayView.count; i ++){
		UIImageView * view = [arrayView objectAtIndex:i];
		[view removeFromSuperview]; 
	}
	
	arrayView = [[NSMutableArray alloc] initWithCapacity:0];
	
	int index1 = -1,index2 = -1,index3 = -1;
	
	
	if(array.count > 1){
		for(int i = 0; i < array.count; i ++){
			Point1 *temp = [array objectAtIndex:i];
			int index = [self getIndex:temp];
			if( index != -1){
				if(index1 == -1){
					index1 = index;
				}else if(index2 == -1){
					if(index != index1){
						index2 = index; 
					}
				}else if(index3 == -1){
					if(index != index1 && index != index2){
						index3 = index;
					}

				}
			}
		}
		
		
	}
	
	//NSLog(@"index1 = %d index2 = %d index3 = %d",index1,index2,index3);
	
	[array release];
	array = [[NSMutableArray alloc]initWithCapacity:0];
	
	if(index1 == -1 || index2 == -1){
		return;
	}
	
	int _index = -1;
	
	BOOL isRontate = NO;
	if(index3 == -1){
		Point1 * temp = [self getIndex:index1 i2:index2];
		
		NSLog(@"x = %d y2 = %d",temp.x,temp.y);
		if(temp.x != -1){
			
			if([Setting1 canSoundEffect]){
				[[SoundCommon soundRontate]play];
			}
			
			isRontate = TRUE;
			[self rontate0 : temp.x Direction:(temp.y == 1)];
		}
	}else{
		Point1 * p2 = [self getIndex:index1 i2:index2 i3:index3];
		if(p2.x != -1){
			
			if([Setting1 canSoundEffect]){
				[[SoundCommon soundRontate]play];
			}
			
			isRontate = TRUE;
			[self rontate0 : p2.x Direction:(p2.y == 1)];
		}
	}
	
	if(isRontate){
		numberOfTurns --;
		[self viewLevelAndTurns];
		
		if([self isWin]){
			canRun = NO;
			
			if([Setting1 canSoundEffect]){
				[[SoundCommon soundLevelup]play];
			}
			
			if(level == maxLevel){
				//win
				[[Common sharedInstance].dbManager updateStore :level status:3];
				LevelController *_view1 = [[LevelController alloc] initWithNibName:@"level" bundle:nil];
				[self.navigationController pushViewController:_view1 animated:YES];
			}else{
				[self performSelector:@selector(win) withObject:nil afterDelay:0.5];
			}
		}else{
			if(![Setting1 isTranning]){
				if(numberOfTurns  <= 0){
					canRun = NO;
					if([Setting1 canSoundEffect]){
						[[SoundCommon soundGameOver]play];
					}
					
					[self performSelector:@selector(lost) withObject:nil afterDelay:1];
				}
			}
		}
	}
	
	
}
//QA
-(Point1*)getIndex:(int)i1 i2:(int)i2 i3:(int)i3{
	Point1 *p = [[Point1 alloc]init];
	p.x = -1;
	p.y = -1;
	
	p = [self checkPoint: 4 i1:i1 i2:i2 i3:i3];
	
	if(p.x == -1){
		p = [self checkPoint: 5 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [self checkPoint: 8 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [self checkPoint: 9 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [self checkPoint: 10 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [self checkPoint: 13 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [self checkPoint: 14 i1:i1 i2:i2 i3:i3];
	}
	
	//_list = [self getList:4];
	return p;
}

-(Point1*)checkPoint:(int)index i1:(int)i1 i2:(int)i2 i3:(int)i3{
	Point1 *p1 = [[Point1 alloc]init];
	p1.x = -1;
	p1.y = -1;
	NSMutableArray* list = [self getList:index];
	
	int _list[6];
	for(int i = 0; i < 6; i ++){
		Point1 * p = [list objectAtIndex:i];
		_list[i] = p.x;
	}
	int index1 = 0;
	int count = 0;
	for(int i = 0 ; i < 6; i ++){
		if(i1 ==_list[i]||i2 ==_list[i]||i3 ==_list[i]){
			count ++;
		}
		
		if(i1 == _list[i]){
			index1 = i;
		}
	}
	
	if(count == 3){
		p1.x = index;
		p1.y = 0;
		
		for(int i = index1 ; i < 6; i ++){
		
			if(i2 == _list[i]){
				p1.y = 1;
				break;
			}
			
		}
		
		if(index1 == 5){
			if(i2 == _list[0]){
				p1.y = 1;
			}
		}
		
		if(index1 == 0){
			if(i2 == _list[5]){
				p1.y = 0;
			}
		}
		
	}
	
	return p1;
	
}
-(Point1*)getIndex:(int)i1 i2:(int)i2{
	Point1 *p = [[Point1 alloc]init];
	p.x = -1;
	p.y = -1;
	
	if(i1 == 0 && i2 == 1){
		p.x = 4;
		p.y = 1;
	}else if(i1 == 1 && i2 == 0){
		p.x = 4;
		p.y = 0;
	}if(i1 == 1 && i2 == 5){
		p.x = 4;
		p.y = 1;
	}else if(i1 == 5 && i2 == 1){
		p.x = 4;
		p.y = 0;
	}if(i1 == 3 && i2 == 0){
		p.x = 4;
		p.y = 1;
	}else if(i1 == 0 && i2 == 3){
		p.x = 4;
		p.y = 0;
	}if(i1 == 8 && i2 == 3){
		p.x = 4;
		p.y = 1;
	}else if(i1 == 3 && i2 == 8){
		p.x = 4;
		p.y = 0;
	}
	
	if(i1 == 4 && i2 == 1){
		p.x = 5;
		p.y = 1;
	}else if(i1 == 1 && i2 == 4){
		p.x = 5;
		p.y = 0;
	}
	
	if(i1 == 1 && i2 == 2){
		p.x = 5;
		p.y = 1;
	}else if(i1 == 2 && i2 == 1){
		p.x = 5;
		p.y = 0;
	}
	
	if(i1 == 2 && i2 == 6){
		p.x = 5;
		p.y = 1;
	}else if(i1 == 6 && i2 == 2){
		p.x = 5;
		p.y = 0;
	}
	if(i1 == 6 && i2 == 10){
		p.x = 5;
		p.y = 1;
	}else if(i1 == 10 && i2 == 6){
		p.x = 5;
		p.y = 0;
	}
	
	if(i1 == 13 && i2 == 12){
		p.x = 8;
		p.y = 1;
	}else if(i1 == 12 && i2 == 13){
		p.x = 8;
		p.y = 0;
	}
	
	if(i1 == 12 && i2 == 7){
		p.x = 8;
		p.y = 1;
	}else if(i1 == 7 && i2 == 12){
		p.x = 8;
		p.y = 0;
	}
	
	if(i1 == 7 && i2 == 3){
		p.x = 8;
		p.y = 1;
	}else if(i1 == 3 && i2 == 7){
		p.x = 8;
		p.y = 0;
	}
	
	if(i1 == 3 && i2 == 4){
		p.x = 8;
		p.y = 1;
	}else if(i1 == 4 && i2 == 3){
		p.x = 8;
		p.y = 0;
	}

	//9
	if(i1 == 5 && i2 == 10){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 10 && i2 == 5){
		p.x = 9;
		p.y = 0;
	}

	if(i1 == 4 && i2 == 5){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 5 && i2 == 4){
		p.x = 9;
		p.y = 0;
	}
	
	if(i1 == 10 && i2 == 14){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 14 && i2 == 10){
		p.x = 9;
		p.y = 0;
	}
	
	if(i1 == 14 && i2 == 13){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 13 && i2 == 14){
		p.x = 9;
		p.y = 0;
	}
	
	if(i1 == 13 && i2 == 8){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 8 && i2 == 13){
		p.x = 9;
		p.y = 0;
	}
	
	if(i1 == 8 && i2 == 4){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 4 && i2 == 8){
		p.x = 9;
		p.y = 0;
	}
	
	//10
	if(i1 == 5 && i2 == 6){
		p.x = 10;
		p.y = 1;
	}else if(i1 == 6 && i2 == 5){
		p.x = 10;
		p.y = 0;
	}
	
	
	if(i1 == 6 && i2 == 11){
		p.x = 10;
		p.y = 1;
	}else if(i1 == 11 && i2 == 6){
		p.x = 10;
		p.y = 0;
	}
	
	
	if(i1 == 11 && i2 == 15){
		p.x = 10;
		p.y = 1;
	}else if(i1 == 15 && i2 == 11){
		p.x = 10;
		p.y = 0;
	}
	
	
	if(i1 == 15 && i2 == 14){
		p.x = 10;
		p.y = 1;
	}else if(i1 == 14 && i2 == 15){
		p.x = 10;
		p.y = 0;
	}
	
	//13
	if(i1 == 14 && i2 == 17){
		p.x = 13;
		p.y = 1;
	}else if(i1 == 17 && i2 == 14){
		p.x = 13;
		p.y = 0;
	}
	
	
	if(i1 == 17 && i2 == 16){
		p.x = 13;
		p.y = 1;
	}else if(i1 == 16 && i2 == 17){
		p.x = 13;
		p.y = 0;
	}
	
	
	if(i1 == 16 && i2 == 12){
		p.x = 13;
		p.y = 1;
	}else if(i1 == 12 && i2 == 16){
		p.x = 13;
		p.y = 0;
	}
	
	if(i1 == 12 && i2 == 8){
		p.x = 13;
		p.y = 1;
	}else if(i1 == 8 && i2 == 12){
		p.x = 13;
		p.y = 0;
	}
	
	//14
	
	if(i1 == 10 && i2 == 15){
		p.x = 14;
		p.y = 1;
	}else if(i1 == 15 && i2 == 10){
		p.x = 14;
		p.y = 0;
	}
	
	if(i1 == 15 && i2 == 18){
		p.x = 14;
		p.y = 1;
	}else if(i1 == 18 && i2 == 15){
		p.x = 14;
		p.y = 0;
	}
	
	if(i1 == 18 && i2 == 17){
		p.x = 14;
		p.y = 1;
	}else if(i1 == 17 && i2 == 18){
		p.x = 14;
		p.y = 0;
	}
	
	if(i1 == 17 && i2 == 13){
		p.x = 14;
		p.y = 1;
	}else if(i1 == 13 && i2 == 17){
		p.x = 14;
		p.y = 0;
	}
	return p;
}

-(int) getIndex:(Point1*)p{
	int x = p.x;
	int y = p.y;
	for(int i = 0; i < 19; i ++){
		if( board1[i].x <= x && x <= board1[i].x + WIDTH && board1[i].y <= y && y <= board1[i].y + HEIGHT ){
			return i;
		}
	}
	return - 1;
}
- (void)dealloc {
    [super dealloc];
}


- (void) drawLineToPoint:(CGPoint)startPoint toPoint:(CGPoint)nextPoint {
	
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGContextSetLineWidth(context, 3.0);
	CGContextSetRGBStrokeColor(context, 1, 0, 1, 1.0); // red line 
	CGContextMoveToPoint(context, startPoint.x, startPoint.y);
	CGContextAddLineToPoint(context, nextPoint.x, nextPoint.y);
	CGContextStrokePath(context);
	
}


@end
