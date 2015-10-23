//
//  PlayScreen.m
//  Calonancabe
//
//  Created by Truong Vuong on 10/1/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PlayScreen.h"
#import <QuartzCore/CADisplayLink.h>

@implementation PlayScreen

@synthesize imgCharactor,imgMap;
@synthesize lbBom,lbTomahoc;
- (void)viewDidLoad {
    [super viewDidLoad];
	self.navigationController.navigationBarHidden = YES;
	normal = 10;
	d = 3;
	isBegin= true;
	minHeight = 130;
	postion = [[Phoint1 alloc]init];
	newPosition = [[Phoint1 alloc]init];
	
	imgCharactor.animationImages = [NSArray arrayWithObjects: [UIImage imageNamed:@"taungam1.png"]
									,[UIImage imageNamed:@"taungam2.png"]
									,	nil];
	imgBom = [[UIImageView alloc ]initWithImage:[UIImage imageNamed:@"bom.png"]];
	
	
	imgBom.frame = CGRectMake( 350, 200,10,10);
	imgTomahoc = [[UIImageView alloc ]initWithImage:[UIImage imageNamed:@"tomahoc.png"]];
	imgTomahoc.frame = CGRectMake( 50, 100,20,5);

	[self.view addSubview:imgBom];
	[self.view addSubview:imgTomahoc];
	
	[imgCharactor setAnimationRepeatCount:0];
	[imgCharactor startAnimating];
	// add dan
	int xbegin = -50;
	for(int i = 0; i < 10; i ++){
		imgNormal[i] = [[UIImageView alloc ]initWithImage:[UIImage imageNamed:@"dan.png"]];
		imgNormal[i].frame = CGRectMake( 500, -100,10,5);
		
		xbegin = xbegin + 30;
		[self.view addSubview:imgNormal[i]];
		
	}
	
	for(int i = 0; i < 100; i ++){
		imgDich[i] = [[UIImageView alloc ]initWithImage:[UIImage imageNamed:@"cavang.png"]];
		imgDich[i].frame = CGRectMake( 420, 300,30,20);
		
		
		//[self.view addSubview:imgNormal[i]];
	}
	[self.view addSubview:imgDich[0]];
	
	
	[postion setX: (imgCharactor.center.x )];
	[postion setY: imgCharactor.center.y];
	
	[newPosition setX:[postion getX]];
	[newPosition setY:[postion getY]];
	
	[self startGame];
	
}

-(void)startGame{
	if(theTimer1== nil){
		theTimer1 = [CADisplayLink displayLinkWithTarget:self selector:@selector(gameLogic)];
		theTimer1.frameInterval = 1;
		[theTimer1 addToRunLoop:[NSRunLoop currentRunLoop] forMode: NSDefaultRunLoopMode];
	}
	
	if(theTimer2== nil){
		theTimer2 = [CADisplayLink displayLinkWithTarget:self selector:@selector(gameLogic1)];
		theTimer2.frameInterval = 1;
		[theTimer2 addToRunLoop:[NSRunLoop currentRunLoop] forMode: NSDefaultRunLoopMode];
	}
	
	if(theTimer3== nil){
		theTimer3 = [CADisplayLink displayLinkWithTarget:self selector:@selector(gameLogic2)];
		theTimer3.frameInterval = 1;
		[theTimer3 addToRunLoop:[NSRunLoop currentRunLoop] forMode: NSDefaultRunLoopMode];
	}
}
-(void)gameLogic2{
	
	// dan bay
	//if(isBegin){
//		imgNormal[0].center = CGPointMake( imgCharactor.center.x + 10, imgCharactor.center.y + 5);
//		isBegin = false;
//	}
	NSString * theValue = [lbBom text];
	int number = [theValue intValue];
	
	normal = number / 10 + 1;
	for(int i = 0; i < normal; i ++){
		imgNormal[i].center = CGPointMake( imgNormal[i].center.x + 3, imgNormal[i].center.y);
		
		if(imgNormal[i].center.x > 480){

				BOOL isBum = false;
				for(int j = 0; j < normal; j ++){
					if(j != i){
						if(imgNormal[j].center.x - imgCharactor.center.x<= 40){
							isBum= true;
						}
					}
				}
				if(!isBum){
					imgNormal[i].center = CGPointMake( imgCharactor.center.x + 10, imgCharactor.center.y + 5);
					[[CommonMusic soundnormal]play];
				}
		
		}
	}
	
	
	
	for(int i = 0; i < normal; i ++){
		if(CGRectIntersectsRect(imgNormal[i].frame, imgDich[0]
								.frame)){
			imgNormal[i].center = CGPointMake( 490, imgNormal[i].center.y );
			[[CommonMusic soundBang]play];
		}
	}
}
-(void)gameLogic1{
	if( imgMap.center.x == -(1440 /2 - 480)){
		imgMap.center = CGPointMake( 200, imgMap.center.y);
	}else imgMap.center = CGPointMake( imgMap.center.x - 1, imgMap.center.y);
}

-(void)gameLogic{
	
	int dx = [newPosition getX] - [postion getX];
	int dy = [newPosition getY] - [postion getY];
	
	
	imgBom.center = CGPointMake( imgBom.center.x, imgBom.center.y - 1);
	imgTomahoc.center = CGPointMake( imgTomahoc.center.x, imgTomahoc.center.y - 1);
	
	if(CGRectIntersectsRect(imgTomahoc.frame, imgCharactor
							.frame)){
		imgTomahoc.center = CGPointMake( imgTomahoc.center.x, 350);
		
		NSString * theValue = [lbTomahoc text];
		int number = [theValue intValue];
		
		
		number ++ ;
		if(number >=99){
			number = 99;
		}
		
		[lbTomahoc setText:[[NSString alloc]initWithFormat:@"%d",number]]; 
	}
	
	if(CGRectIntersectsRect(imgBom.frame, imgCharactor.frame)){
		imgBom.center = CGPointMake( imgBom.center.x, 350);
		
		NSString * theValue = [lbBom text];
		int number = [theValue intValue];
		
		
		number ++ ;
		if(number >=99){
			number = 99;
		}
		[lbBom setText:[[NSString alloc]initWithFormat:@"%d",number]]; 
	}
	
	if(imgBom.center.y < minHeight){
		imgBom.center = CGPointMake( imgBom.center.x, 350);
	}
	
	if(imgTomahoc.center.y < minHeight){
		imgTomahoc.center = CGPointMake( imgTomahoc.center.x, 350);
	}

	int d2 = sqrt(dx * dx + dy * dy);
	if(d2!= 0 ){
		dx = dx * d/ d2;
		dy = dy * d/ d2;
		
		[postion setX:([postion getX] + dx)];
		[postion setY:([postion getY] + dy)];
		
		if([Common compare:[postion getX] D2:[newPosition getX]] && [Common compare:[postion getY] D2:[newPosition getY]]){
			[postion setX:([newPosition getX])];
			[postion setY:([newPosition getY])];
		}
		
		if([postion getY] <= minHeight){
			[postion setY:minHeight];
			[newPosition setY:minHeight];
		}
	}
	
	
	
	
	imgCharactor.center = CGPointMake([postion getX], [postion getY]);
}

-(void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	UITouch *touch = [touches anyObject];
	CGPoint positionTouch = [touch locationInView:self.view];

	[newPosition setX:positionTouch.x];
	[newPosition setY:positionTouch.y];
	
	
}

-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
	UITouch *touch = [touches anyObject];
	CGPoint positionTouch = [touch locationInView:self.view];
	
	[newPosition setX:positionTouch.x];
	[newPosition setY:positionTouch.y];
}

// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    // man hinh chi nam ngnag
	if (interfaceOrientation == UIInterfaceOrientationLandscapeLeft || interfaceOrientation == UIInterfaceOrientationLandscapeRight) {
        return YES;
    }
    else {
        return NO;
    }
}

// The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization.
    }
	
	
	
    return self;
}


/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

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


- (void)dealloc {
    [super dealloc];
}


@end
