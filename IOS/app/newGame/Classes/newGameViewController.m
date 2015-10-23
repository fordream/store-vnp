//
//  newGameViewController.m
//  newGame
//
//  Created by mac on 8/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "newGameViewController.h"
#import "ImageView.h"

@implementation newGameViewController

@synthesize scoreLable, livesLable, mesageLable ;     // Lable
@synthesize ball , paddle, paddle1, paddle2, you_win, you_win1;  // UIImageView

- (void)dealloc {
	[livesLable release];
	[mesageLable release];
	[paddle release];
	[paddle release];
	[ball release];
	[scoreLable release];
    [super dealloc];
}

-(void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
	if (isPlaying) {
		UITouch *touch = [[event allTouches] anyObject];
		touchOffset = paddle.center.x - [touch locationInView:touch.view].x;
		
		
	}else {
		[self startGame];
		
	}

}
//==
	
//==
-(void) touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event
{
	if (isPlaying) {
		
		UITouch *touch = [[event allTouches] anyObject];
		float didtanceMoved = ([touch locationInView:touch.view].x + touchOffset) - paddle.center.x;

		float newX = paddle.center.x + didtanceMoved;
		
		if (newX>30 && newX <290) {
			paddle.center = CGPointMake(newX, paddle.center.y);
		}
		if (newX< 30) {
			paddle.center = CGPointMake(30, paddle.center.y);
		}
		if (newX > 290) {
			paddle.center = CGPointMake(290, paddle.center.y);
		}
	}
}


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	//[self showImgNextLevel];
	[self loadGameState];
	you_win.hidden=YES;
	[self initializeBricks];
	
	//[self startGame];
}
//============================================================================================
-(void) initializeBricks
{
	
//	UIImage *myImageObj;
//	NSString *imagePath = [[NSBundle mainBundle] pathForResource:@"bricktype1" ofType:@"png"];
//	myImageObj = [[UIImage alloc] initWithContentsOfFile:imagePath];
//	UIImageView *myImageView = [[UIImageView alloc] initWithImage:myImageObj];
//	[self.view addSubview:myImageView];
	//========================================================================================
		brickTypes[0] = @"1.png";
		brickTypes[1] = @"2.png";
		brickTypes[2] = @"3.png";
		brickTypes[3] = @"4.png";
		
		int count = 0;
		for (int y = 0; y < BRICKS_HEIGH; y++)
		{
			for (int x = 0; x < BRICKS_WIDTH; x++)
			{
				UIImage *image = [ImageView loadImage:brickTypes[count++ % 4]];
				bricks[x][y] = [[[UIImageView alloc] initWithImage:image] autorelease];
				CGRect newFrame = bricks[x][y].frame;
				newFrame.origin = CGPointMake(x * 64, y * 40);
				bricks[x][y].frame = newFrame;
				[self.view addSubview:bricks[x][y]];
			}
		}
}
//============================================================================================

-(void) startGame
{
	count1 = 0;
	you_win.hidden= YES;
	if (!lives) {
		
		lives = 3;
		score = 0;
		
		for (int y = 0; y < BRICKS_HEIGH; y++)
		{
			for (int x = 0; x < BRICKS_WIDTH; x++)
			{
				bricks[x][y].alpha = 1.0;
			}
		}
	}

	scoreLable.text =[[NSString alloc] initWithFormat:@"%d", score];
	livesLable.text =[[NSString alloc] initWithFormat:@"%d", lives];

	ball.center = CGPointMake(153, 307);
	ballMovement = CGPointMake(7,7);
	
	if (arc4random()% 100 < 50) {
		ballMovement.x = - ballMovement.x;
		mesageLable.hidden = YES;
		you_win.hidden = YES;
		isPlaying = YES;
		[self initializeTimer];
	}
}
//============================================================================================
-(void) pauseGame
{
	[theTimer invalidate];
	theTimer = nil;
}


//============================================================================================
-(void) initializeTimer
{	//ball1 = nil;
	if (theTimer == nil) {
//	float theInterval = 1.0/40.0;								// toc do chay bong
//	[NSTimer scheduledTimerWithTimeInterval:theInterval 
//									 target:self 
//								   selector:@selector(gameLogic) 
//								   userInfo:nil 
//									repeats:YES];
		theTimer = [CADisplayLink displayLinkWithTarget:self
											   selector:@selector(gameLogic)];
		theTimer.frameInterval = 2;
		[theTimer addToRunLoop:[NSRunLoop currentRunLoop] forMode: NSDefaultRunLoopMode];
		
		
		theTimer1 = [CADisplayLink displayLinkWithTarget:self
											   selector:@selector(gameLogic1)];
		theTimer1.frameInterval = 2;
		[theTimer1 addToRunLoop:[NSRunLoop currentRunLoop] forMode: NSDefaultRunLoopMode];
		
		
	}
}
//============================================================================================

//-(void) showImgNextLevel {
//	//CGRect rect1 = CGRectMake(paddle1.frame.origin.x + 200, paddle1.frame.origin.y , paddle1.frame.size.width, paddle1.frame.size.height);
//	CGRect rect2 = CGRectMake(paddle2.frame.origin.x - 200, paddle2.frame.origin.y , paddle2.frame.size.width, paddle2.frame.size.height);					
//	[UIView beginAnimations:nil context:paddle2];
//	//[UIView beginAnimations:nil context:paddle1];		
//	[UIView setAnimationDuration:2];		
//	[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];			
//	paddle2.frame = rect2;	
//	//paddle1.center = rect1;		
//
//	[UIView setAnimationDelegate:self];	
//	[UIView setAnimationDidStopSelector:@selector(showImgNextLevel1)];
//	[UIView commitAnimations];	
//
//}
//-(void) showImgNextLevel1
//{
//	//CGRect rect1 = CGRectMake(paddle1.frame.origin.x - 200 , paddle1.frame.origin.y , paddle1.frame.size.width, paddle1.frame.size.height);		
////	CGRect rect2 = CGRectMake(paddle2.frame.origin.x + 200, paddle2.frame.origin.y , paddle2.frame.size.width, paddle2.frame.size.height);		
//    //CGPoint rect1 = CGPointMake(paddle1.center.x - 100, paddle1.center.y);
//	CGPoint rect2 = CGPointMake(paddle1.center.x + 100, paddle2.center.y);
//	
//	[UIView beginAnimations:nil context:paddle2];
//	//[UIView beginAnimations:nil context:paddle1];		
//	[UIView setAnimationDuration:2];		
//	[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];			
//	paddle2.center = rect2;	
//	//paddle1.center = rect1;		
//	
//	[UIView setAnimationDelegate:self];	
//	[UIView setAnimationDidStopSelector:@selector(showImgNextLevel)];
//	[UIView commitAnimations];
//}
//-(void)move
//{
//	CGPoint create_move = CGPointMake(you_win.center.x , you_win.center.y +200);
//	[UIView beginAnimations:nil context:you_win];
//	[UIView setAnimationDuration:0.1];		
//	[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];			
//	you_win.center = create_move;	
//	[UIView setAnimationDelegate:self];	
//	[UIView commitAnimations];
//	if (you_win.center.y >= 444) {
//		you_win.hidden = YES;
//	}
//}

-(void)gameLogic1
{	
	for(int i = 0 ; i < 20; i ++)
	
	if(ball1[i] != nil){
		ballMovement1 = CGPointMake(0,0);
		ballMovement1.y = - 1;
		//mesageLable.hidden = YES;
		ball1[i].center = CGPointMake(ball1[i].center.x , ball1[i].center.y - ballMovement1.y);
		if(CGRectIntersectsRect(ball1[i].frame, paddle.frame)){
			score += 10000;
			scoreLable.text = [[NSString alloc] initWithFormat:@"%d", score];
			[ball1[i] removeFromSuperview];
			ball1[i] = nil;
		}
		//if(CGPoin)
	}
}

-(void)gameLogic
{
	
//	UITouch *touch_panddle1;
//	float of_panddle1 = paddle1.center.x - [touch_panddle1 locationInView:touch_panddle1.view].x;
//	
//	float didtance_panddle1 = ([touch_panddle1 locationInView:touch_panddle1.view].x + of_panddle1) - paddle.center.x;
//	float newX = paddle.center.x + didtance_panddle1;
	ball.center = CGPointMake(ball.center.x + ballMovement.x, ball.center.y + ballMovement.y);
	
	BOOL paddlecolision = ball.center.x >= paddle.center.x - 32 && ball.center.x < paddle.center.x + 32 &&
	ball.center.y >= paddle.center.y - 16 && ball.center.y < paddle.center.y + 16;
//	
//	BOOL paddlecolision1 = ball.center.x >= paddle1.center.x - 32 && ball.center.x < paddle1.center.x + 32 &&
//	ball.center.y >= paddle1.center.y - 16 && ball.center.y < paddle1.center.y + 16;
//	
//	BOOL paddlecolision2 = ball.center.x >= paddle2.center.x - 40 && ball.center.x < paddle2.center.x + 40 &&
//	ball.center.y >= paddle2.center.y - 16 && ball.center.y < paddle2.center.y + 16;
//

    if(paddlecolision) {
        ballMovement.y = -ballMovement.y;

		
        if (ball.center.y >= paddle.center.y - 16 && ballMovement.y < 0) {
            ball.center = CGPointMake(ball.center.x, paddle.center.y - 16);
        } else if (ball.center.y <= paddle.center.y + 16 && ballMovement.y > 0) {
            ball.center = CGPointMake(ball.center.x, paddle.center.y + 16);
        } else if (ball.center.x >= paddle.center.x - 32 && ballMovement.x < 0) {
            ball.center = CGPointMake(paddle.center.x - 32, ball.center.y);
        } else if (ball.center.x <= paddle.center.x + 32 && ballMovement.x > 0) {
            ball.center = CGPointMake(paddle.center.x + 32, ball.center.y);
        }
	}
	
	if (ball.center.x > 310 || ball.center.x < 20) {
		ballMovement.x = - ballMovement.x;
	}
	if (ball.center.y < 32) {
		ballMovement.y = - ballMovement.y;
	}
	if (ball.center.y > 444) {
		
		[self pauseGame];
		isPlaying = NO;
		lives--;
		
		if (!lives) {
			[self saveGameState];
			mesageLable.text = [[NSString alloc] initWithFormat:@"Game Over"];
		}else {
			mesageLable.text = [[NSString alloc] initWithFormat:@"Ball out for bounds"];
		}
		mesageLable.hidden = NO;
		you_win.hidden = YES;
	}
	
	BOOL solid_bricks = NO;
	
	for (int y = 0; y < BRICKS_HEIGH; y++) {
		for (int x = 0 ; x < BRICKS_WIDTH; x++) {
			if (1.0 == bricks[x][y].alpha) {

				solid_bricks = YES;
				if (CGRectIntersectsRect(ball.frame, bricks[x][y].frame )) {
					
					[self processPolision:bricks[x][y]];
						UIImage *img = [UIImage imageNamed:@"1.png"];
						ball1[count1] = [[UIImageView alloc]initWithImage:img];
						CGRect newFrame = ball.frame;
						ball1[count1].frame = newFrame;
						[self.view addSubview:ball1[count1]];
					count1++;
						
					//}
				}
			}
			else {
				if (bricks[x][y].alpha > 0) {
					bricks[x][y].alpha -= 0.1;
				}
			}

		}
		
	}
	[NSThread detachNewThreadSelector:@selector(aMethod:) toTarget:self withObject:nil];
	if (!solid_bricks) {
		[theTimer invalidate];
		isPlaying = NO;
		lives = 0;
		[self saveGameState];
		mesageLable.text = @"you win";
		mesageLable.hidden = NO;
	}
 
	
}

-(void)aMethod :(id)param{
	if(you_win1 != nil){
		NSLog(@"%d",you_win1.frame.origin.y);
	}
	
}
-(void)processPolision:(UIImageView *)brick  
{
	ballMovement.x = - ballMovement.x;
	ballMovement.y = - ballMovement.y;
	score = score + 10;
	
	you_win.hidden = NO;
	

	scoreLable.text = [[NSString alloc] initWithFormat:@"%d", score];
	
	if (ballMovement.x > 0 && brick.frame.origin.x - ball.center.x <=1) {
		ballMovement.x = - ballMovement.x;
	}
	else if (ballMovement.x<0 && ball.center.x - (brick.frame.origin.x + brick.frame.size.width) <= 4){
		ballMovement.x = - ballMovement.x;
	}
	else if (ballMovement.y>0 && brick.frame.origin.y - ball.center.y <= 4){
		ballMovement.y = - ballMovement.y;
	}
	else if (ballMovement.y<0 && ball.center.y - (brick.frame.origin.y + brick.frame.size.height) <=4){
		ballMovement.y = - ballMovement.y;
	}
	
	brick.alpha = brick.alpha - 0.1;
}

NSString *kliveskey = @"lives";
NSString *kscorekey = @"scores";


-(void)saveGameState
{
	[[NSUserDefaults standardUserDefaults] setInteger:lives forKey:kliveskey];
	[[NSUserDefaults standardUserDefaults] setInteger:score forKey:kscorekey];
}

-(void)loadGameState
{
	lives = [[NSUserDefaults standardUserDefaults] integerForKey:kliveskey];
	livesLable.text = [[NSString alloc] initWithFormat:@"%d", lives];
	
	score = [[NSUserDefaults standardUserDefaults] integerForKey:kscorekey];
	scoreLable.text = [[NSString alloc] initWithFormat:@"%d", score];
}





@end
