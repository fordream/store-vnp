//
//  ViewController.m
//  MultipleAnimation
//
//  Created by cuong minh on 6/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

#define ball_size 32
@implementation ViewController
@synthesize ball=_ball;
@synthesize animating;
- (void)viewDidLoad
{
    [super viewDidLoad];
	self.view.userInteractionEnabled = YES;
    UITapGestureRecognizer *tapRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(animateBall)];
    [self.view addGestureRecognizer: tapRecognizer];
    
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    self.ball = nil;
}

- (void)viewDidAppear:(BOOL)animated
{
    
    if (!self.ball)
    {
        self.ball = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"ball.png"]];
        CGFloat width = [UIScreen mainScreen].bounds.size.width;        
        self.ball.center = CGPointMake(width/2, ball_size);
        [self.view addSubview:self.ball];
    }
    self.animating = NO;
    
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

- (void) animateBall
{
    if (animating) {
        return;
    } else {
        self.animating = YES;
    }
    CGFloat width = [UIScreen mainScreen].bounds.size.width;
    CGFloat height = [UIScreen mainScreen].bounds.size.height;
    self.ball.center = CGPointMake(width/2, ball_size);
    
    
    [UIView animateWithDuration:1.0f animations:^{
        self.ball.center = CGPointMake(ball_size, height/2);
    } completion:^(BOOL finished) {
        [UIView animateWithDuration:1.0f animations:^{
            self.ball.center = CGPointMake(width/2, height-ball_size);
        } completion:^(BOOL finished) {
            [UIView animateWithDuration:1.0f animations:^{
                self.ball.center = CGPointMake(width-ball_size, height/2);
            } completion:^(BOOL finished) {
                [UIView animateWithDuration:1.0f animations:^{
                    self.ball.center = CGPointMake(width/2, ball_size);
                } completion:^(BOOL finished) {
                    NSLog(@"Done");
                    self.animating = NO;
                }];
            }];
        }];
        
    }];
}

@end
