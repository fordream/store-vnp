//
//  ViewController.m
//  GestureBasic
//
//  Created by cuong minh on 5/8/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize redSquare;
#pragma mark - Handle View
- (void)viewDidLoad
{
    [super viewDidLoad];
	UITapGestureRecognizer *tapRecognizer = [[UITapGestureRecognizer alloc] 
                                             initWithTarget:self
                                             action:@selector(tapHandler:)];
    tapRecognizer.numberOfTapsRequired = 1;
    tapRecognizer.numberOfTouchesRequired = 1; //Change to 2 and see what happens
    [redSquare addGestureRecognizer:tapRecognizer];
    
    //Move object around in main view
    UIPanGestureRecognizer *panRecognizer = [[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(panHandler:)];
    panRecognizer.delegate = self;
    [redSquare addGestureRecognizer:panRecognizer];
    
    //Scale object
    UIPinchGestureRecognizer *pinchRecognizer = [[UIPinchGestureRecognizer alloc] initWithTarget:self action:@selector(pinchHandler:)];
    pinchRecognizer.delegate = self;
    [redSquare addGestureRecognizer:pinchRecognizer];
    
    //Rotate object
    UIRotationGestureRecognizer *rotationRecognizer = [[UIRotationGestureRecognizer alloc] initWithTarget:self action:@selector(rotationHandler:)];
    rotationRecognizer.delegate = self;
    [redSquare addGestureRecognizer:rotationRecognizer];
    
}

- (void)viewDidUnload
{
    [self setRedSquare:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

#pragma mark - Gesture Recognizer Handlers
                                                       
- (void) tapHandler: (UITapGestureRecognizer *)gestureRecognizer {
    NSLog(@"Tap me!");
}

- (void) panHandler: (UIPanGestureRecognizer *)panRecognizer {
    if ([panRecognizer state] == UIGestureRecognizerStateBegan || [panRecognizer state] == UIGestureRecognizerStateChanged) {
        CGPoint translation = [panRecognizer translationInView:self.view];
        
        [redSquare setCenter:CGPointMake([redSquare center].x + translation.x, [redSquare center].y + translation.y)];
        [panRecognizer setTranslation:CGPointZero inView:self.view];        
    }

}

- (void) pinchHandler: (UIPinchGestureRecognizer *)pinchRecognizer
{
    CGAffineTransform transform = CGAffineTransformMakeScale(pinchRecognizer.scale, pinchRecognizer.scale);
    redSquare.transform = transform;
}
- (void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    NSLog(@"Number of touches: %d", [touches count]);
    for (UITouch* aTouch in touches) {
        CGPoint point = [aTouch locationInView:self.view];
        NSLog(@"x=%3.0f y=%3.0f", point.x, point.y);
    }
    
}

- (void) rotationHandler: (UIRotationGestureRecognizer *)rotateRecognizer
{
    CGAffineTransform transform = CGAffineTransformMakeRotation(rotateRecognizer.rotation);
    redSquare.transform = transform;
}

#pragma mark - UIGestureRecognizerDelegate
- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer
{
       if (([gestureRecognizer isMemberOfClass:[UIPanGestureRecognizer class]] && 
            [otherGestureRecognizer isMemberOfClass:[UIPinchGestureRecognizer class]]) ||
            ([gestureRecognizer isMemberOfClass:[UIPinchGestureRecognizer class]] && 
             [otherGestureRecognizer isMemberOfClass:[UIPanGestureRecognizer class]]
             )){
                return YES;
           
            } else {
                return NO;
            }
       
    
}



@end
