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
	
	h1 = 10;
	w1 = 20;
	pNext = [[Point1 alloc]init];
	pNext.x = -40;
	pNext.y = -40;
	if (self) {
        // Custom initialization.
    }
	
	[self autoRun];
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
	maxRound = 0;
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
	//NSString *s = [[NSBundle mainBundle] pathForResource:@"rontate" ofType:@"mp3" ];
	
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
	
	Seven *seven = [Common1 random];
	//int type[7];
	//for(int i =0; i < 7 ; i ++) type[i] = -1;
	BOOL board10[19];
	BOOL board2[19];

	for(int i = 0; i < 19; i ++){
		board10[i] = NO;
		board2[i]= NO; 
	}
	int _count = 0;
	for(int i = 0; i < 19 ; i ++){
		if(!board10[i]){
			int type = board1[i].type;
			
			for(int j = 0; j < 19; j ++){
				if(!board10[j] && board1[j].type == type){
					board1[j].type = [seven get:_count];
					board10[j] = TRUE;
				}
				
				if(!board2[j] && boardEnd[j].type == type){
					boardEnd[j].type = [seven get:_count];
					board2[j] = TRUE;
				}
			}
			
			_count ++;
		}
	}
	
	for(int i = 0; i < 19; i ++){
		NSString *name = [[NSString alloc]initWithFormat:@"%d.png",board1[i].type];
		UIImage *img= [UIImage imageNamed:name];
		[(boardButton1[i]) setImage:img forState:UIControlStateNormal];
		
		NSString *name1 = [[NSString alloc]initWithFormat:@"%d.png",boardEnd[i].type];
		UIImage *img1= [UIImage imageNamed:name1];
		[(imgEnd[i]) setImage:img1 forState:UIControlStateNormal];
		imgEnd[i].userInteractionEnabled = NO;
	}
	
	//self drawRect:nil];
}

-(void) viewLevelAndTurns{
	
	score = numberOfTurns * 20;
	if(numberOfTurns <= 0)
		score = 0;
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
		boardFirst[i] = [[Point1 alloc]init];
		boardButton1[i] = [UIButton buttonWithType:UIButtonTypeCustom];
		//[boardButton1[i] setEnabled:NO];
		boardButton1[i].userInteractionEnabled = NO;
		
		// add press lissener.
		//[(boardButton1[i]) addTarget:self action:@selector(ButtonPressed:) forControlEvents:UIControlEventTouchUpInside];
		board1[i] = [[Point1 alloc]init];
		board1[i].type = 0;
		board1[i].x = LEFT + WIDTH*[Common1 getCount1:i]/2 + WIDTH * [Common1 getCount:i];
		board1[i].y = TOP + HEIGHT * [Common1 getCount2:i];
		
		boardEnd[i] = [[Point1 alloc]init];
		boardEnd[i].x = LEFT_END +  WIDTH_END*[Common1 getCount1:i]/2 + WIDTH_END * [Common1 getCount:i];
		boardEnd[i].y = TOP_END+HEIGHT_END * [Common1 getCount2:i] ;
	
		NSString *name1= [[NSString alloc]initWithFormat:@"0.png"];
		UIImage *img= [UIImage imageNamed:name1];
		boardButton1[i].frame = CGRectMake(board1[i].x, board1[i].y, WIDTH, HEIGHT);
		[(boardButton1[i]) setImage:img forState:UIControlStateNormal];
		[super.view addSubview:(boardButton1[i])];
		
		img= [UIImage imageNamed:name1];
		[(imgEnd[i]) setImage:img forState:UIControlStateNormal];
		imgEnd[i].frame = CGRectMake(boardEnd[i].x, boardEnd[i].y, WIDTH_END, HEIGHT_END);
		[super.view addSubview:(imgEnd[i])];
	}

}


-(void)lost{

	LevelController *_view1 = [[LevelController alloc] initWithNibName:@"level" bundle:nil];
	[self.navigationController pushViewController:_view1 animated:YES];
}

-(void)win{

	[[Common sharedInstance].dbManager updateStore :level status:2 score:score] ;
	LevelController *_view1 = [[LevelController alloc] initWithNibName:@"level" bundle:nil];
	[self.navigationController pushViewController:_view1 animated:YES];
}


-(void)rontate0:(int)position Direction:(BOOL)direction{
	NSMutableArray * list =[Common1 getList:position];//[self getList:position];
	
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
	//NSLog(@"END 1 = %d 5 = %d" ,board1[list[0]].type, board1[list[5]].type);
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
		
		[theTimer1 release];
		[theTimer2 release];
		[theTimer3 release];
		
		MenuController *_view00 = [[MenuController alloc] initWithNibName:@"menu" bundle:nil];
		[self.navigationController pushViewController:_view00 animated:YES];
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

-(UIImageView *)  addViewImage :(int)x y:(int)y status:(int) status{
	NSString *name = [[NSString alloc]initWithFormat:@"star1.png"];
	UIImage *img= [UIImage imageNamed:name];
	
	
	UIImageView *imgView = [[UIImageView alloc]initWithImage:img];
	
	
	if(status == 0)
		imgView.animationImages = [NSArray arrayWithObjects:
									 [UIImage imageNamed:@"star1.png"]
									 ,[UIImage imageNamed:@"star2.png"]
									 ,	nil];
	else if(status == 1){
		imgView.animationImages = [NSArray arrayWithObjects:
								   [UIImage imageNamed:@"nextlevel.png"]
								   ,[UIImage imageNamed:@"nextlevel.png"]
								   ,	nil];
		imgView.animationDuration = 0.1;
	}
	
	else if(status == 2){
		imgView.animationImages = [NSArray arrayWithObjects:
								   [UIImage imageNamed:@"levelfailed.png"]
								   ,[UIImage imageNamed:@"levelfailed.png"]
								   ,	nil];
		imgView.animationDuration = 0.1;
	}
	
	else if(status == 3){
		imgView.animationImages = [NSArray arrayWithObjects:
								   [UIImage imageNamed:@"bee1.png"]
								   ,[UIImage imageNamed:@"bee2.png"]
								   ,	nil];
		imgView.animationDuration = 0.1;
	}else if(status == 4){
		imgView.animationImages = [NSArray arrayWithObjects:
								   [UIImage imageNamed:@"butterfly1.png"]
								   ,[UIImage imageNamed:@"butterfly2.png"]
								   ,[UIImage imageNamed:@"butterfly3.png"]
								   ,	nil];
		imgView.animationDuration = 0.2;
	}

	[imgView setAnimationRepeatCount:0];
	
	[imgView startAnimating];	
	
	CGRect newFrame = CGRectMake(x, y, 10, 10);;
	
	if(status == 1){
		newFrame = CGRectMake(x, y, 50, 20);
	}

	imgView.frame = newFrame;
	
	//[arrayView addObject:imgView];
	
	[super.view addSubview:imgView];
	
	return imgView;
}


-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	
	if (canRun == NO){
		return;
	}

	UITouch *touch = [touches anyObject];
	CGPoint position = [touch locationInView:self.view];

	int x = position.x;
	int y = position.y;
	
	[arrayView addObject:[self addViewImage:x y:y status:0]];
	
	
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
	
	[arrayView addObject:[self addViewImage:x y:y status:0]];
	
	for(int i = 0 ; i < arrayView.count; i ++){
		UIImageView * view = [arrayView objectAtIndex:i];
		[view removeFromSuperview]; 
	}
	
	[arrayView release];
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
	
	
	
	[array release];
	array = [[NSMutableArray alloc]initWithCapacity:0];
	
	if(index1 == -1 || index2 == -1){
		return;
	}
	
	//int _index = -1;
	
	BOOL isRontate = NO;
	if(index3 == -1){
		Point1 * temp = [Common1 getIndex:index1 i2:index2];
		
		//NSLog(@"x = %d y2 = %d",temp.x,temp.y);
		if(temp.x != -1){
			
			if([Setting1 canSoundEffect]){
				[[SoundCommon soundRontate]play];
			}
			
			isRontate = TRUE;
			[self rontate0 : temp.x Direction:(temp.y == 1)];
		}
	}else{
		Point1 * p2 = [Common1 getIndex:index1 i2:index2 i3:index3];
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
		if([Common1 isWin:board1 end:boardEnd]){
		//QA2
		//if([self isWin]){
			[theTimer1 release];
			[theTimer2 release];
			[theTimer3 release];
			
			canRun = NO;
			
			if([Setting1 canSoundEffect]){
				[[SoundCommon soundLevelup]play];
			}
			
			if(level == maxLevel){
				//win
				
				UIAlertView *alv = [[UIAlertView alloc] initWithTitle:@"Confirmation"
										message:@"The lite version is limited. Do you want to get more level with the full version?"
										delegate:self cancelButtonTitle:@"Yes"
												   otherButtonTitles:@"No", nil];
				[alv show];
				[alv release];
				
				
				
			//	[[Common sharedInstance].dbManager updateStore :level status:3 score:score];
//				LevelController *_view1 = [[LevelController alloc] initWithNibName:@"level" bundle:nil];
//				[self.navigationController pushViewController:_view1 animated:YES];
			
			}else{
				
				uiImageVIew = [self addViewImage:(250 - w1/2) y:(160 - h1/2) status:1];
				
				theTimer1 = [CADisplayLink displayLinkWithTarget:self
														selector:@selector(gameLogic1)];
				theTimer1.frameInterval = 2;
				[theTimer1 addToRunLoop:[NSRunLoop currentRunLoop] forMode: NSDefaultRunLoopMode];
				
				
				[self performSelector:@selector(win) withObject:nil afterDelay:2];
			}
		}else{
			if(![Setting1 isTranning]){
				if(numberOfTurns  <= 0){
					canRun = NO;
					if([Setting1 canSoundEffect]){
						[[SoundCommon soundGameOver]play];
					}
					
					uiImageVIew = [self addViewImage:(250 - w1/2) y:(160 - h1/2) status:2];
					
					theTimer1 = [CADisplayLink displayLinkWithTarget:self
															selector:@selector(gameLogic1)];
					theTimer1.frameInterval = 2;
					[theTimer1 addToRunLoop:[NSRunLoop currentRunLoop] forMode: NSDefaultRunLoopMode];
					
					
					
					
					[theTimer1 release];
					[theTimer2 release];
					[theTimer3 release];
					[self performSelector:@selector(lost) withObject:nil afterDelay:2];
				}
			}
		}
	}else{
		if(index1 != -1){
			if([Setting1 canSoundEffect]){
				[[SoundCommon soundMenu]play];

			}
		}
	}
	
	
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
	if (buttonIndex == 0) {
		[[UIApplication sharedApplication]
		 openURL:[NSURL
				  URLWithString:@"http://itunes.apple.com/us/app/spring-garden/id464751242?ls=1&mt=8"]];
	}
	else {
		[[Common sharedInstance].dbManager updateStore :level status:3 score:score];
		LevelController *_view1 = [[LevelController alloc] initWithNibName:@"level" bundle:nil];
		[self.navigationController pushViewController:_view1 animated:YES];
	}


}

-(void)gameLogic1{
	h1 = h1 + 2;
	w1 = w1 + 4;
	
	
	CGRect newFrame = CGRectMake(250 - w1 / 2, 160 - h1/2, w1, h1);
	uiImageVIew.frame = newFrame;
}

-(void)autoRun {
	if(theTimer2 == nil){
		theTimer2 = [CADisplayLink displayLinkWithTarget:self
											selector:@selector(gameLogic2)];
		theTimer2.frameInterval = 4;
		[theTimer2 addToRunLoop:[NSRunLoop currentRunLoop] forMode: NSDefaultRunLoopMode];
	}
	
	if(theTimer3 == nil){
		theTimer3 = [CADisplayLink displayLinkWithTarget:self
												selector:@selector(gameLogic3)];
		theTimer3.frameInterval = 15;
		[theTimer3 addToRunLoop:[NSRunLoop currentRunLoop] forMode: NSDefaultRunLoopMode];
	}
}
-(void) gameLogic3{
	int random = arc4random() % 100;
	if(random == 0){
		[[SoundCommon soundBird:NO]play];
	}else if(random == 60){
		[[SoundCommon soundBird:TRUE]play];
	}else if(random == 30){
		[[SoundCommon soundBee]play];
	}
}

-(void)gameLogic2{
	float w = 40, h = 40;
	float dx = -1,dy = 0;
	
	
	if(uiImageVIew1 != nil){
		dx = pNext.x - uiImageVIew1.center.x;
		dy = pNext.y - uiImageVIew1.center.y;
		if(uiImageVIew1.center.x + w /2 <= 0 
		   ||uiImageVIew1.center.x - w /2 >= 480
		   ||uiImageVIew1.center.y + h /2 <= 0 
		   || uiImageVIew1.center.y - h /2 >= 320){
			if(arc4random() % 100 < 5){
				[uiImageVIew1 removeFromSuperview];
				[uiImageVIew1 release];
				uiImageVIew1 = nil;
				return;
			}
		}
		
		if(uiImageVIew1.center.x - 5 < pNext.x && pNext.x < uiImageVIew1.center.x + 5 
		   && pNext.y < uiImageVIew1.center.y + 5 && pNext.y > uiImageVIew1.center.y - 5){
			pNext.x = arc4random() % 630 - 30;
			pNext.y = arc4random() % 430 - 30;
			
			
		}
		

		
		
		float temp = sqrt(dx * dx + dy * dy);
		dx = dx * 5/  temp;
		
		dy = dy * 5/ temp;
		
		CGAffineTransform transform ;
		transform = CGAffineTransformMakeRotation([Common1 calculatorRontate:dx dy:dy]);
		[uiImageVIew1 setTransform:transform];
		uiImageVIew1.center = CGPointMake(uiImageVIew1.center.x + dx, uiImageVIew1.center.y + dy);
	}
	
	
	if(uiImageVIew1 == nil){
		int ran = arc4random()%200;
		
		if(ran == 3 || ran == 4){
			uiImageVIew1 = [self addViewImage:self.view.center.x y:self.view.center.y status:ran];
			if(0 <= pNext.x && pNext.y <= 480 
			   &&0 <= pNext.y && pNext.y <= 320){
				int ran = arc4random() %4;
				if(ran == 1){
					pNext.x = -20;
				}else if(ran == 2){
					pNext.x = 480 + 20;
				}else if(ran == 3){
					pNext.y = -20;
				} else{
					pNext.y = 340;
				}
			}
			CGRect newFrame = CGRectMake(pNext.x, pNext.y , w, h);
			uiImageVIew1.frame = newFrame;
			[self.view addSubview:uiImageVIew1];
		}
	}
	

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


@end
