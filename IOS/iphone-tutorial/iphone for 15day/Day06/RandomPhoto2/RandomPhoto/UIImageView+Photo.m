//
//  UIImageView+Photo.m
//  RandomPhoto
//
//  Created by cuong minh on 6/21/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "UIImageView+Photo.h"
#import <QuartzCore/QuartzCore.h>

@implementation UIImageView (Photo)
- (void) makeItCool
{
    [self.layer setMasksToBounds:NO];
    
    // put a white border around the image view to make the image stand out from the background
    [self.layer setBorderWidth:5.0f];
    [self.layer setBorderColor:[[UIColor whiteColor] CGColor]];
    
    // add a shadow behind the image view to make it stand out even more
    [self.layer setShadowRadius:5.0f];
    [self.layer setShadowOpacity:.85f];
    [self.layer setShadowOffset:CGSizeMake(1.0f, 2.0f)];
    [self.layer setShadowColor:[[UIColor blackColor] CGColor]];
    [self.layer setShouldRasterize:YES];
    [self.layer setMasksToBounds:NO];
    

    CGAffineTransform transform = CGAffineTransformMakeRotation(((float)rand()/RAND_MAX - 0.5)*0.4);
    self.transform = transform;    
}

//Bo xung nhan dang da cham vao tung anh.
- (void) addGestureRecognizers
{
    UIRotationGestureRecognizer *rotateGesture = [[UIRotationGestureRecognizer alloc] initWithTarget:self action:@selector(rotateMe:)];
    
    UIPinchGestureRecognizer *pinchGesture = [[UIPinchGestureRecognizer alloc] initWithTarget:self action:@selector(pichMe:)];
    self.userInteractionEnabled = YES;
    self.multipleTouchEnabled = YES;
    [self addGestureRecognizer:rotateGesture];
    [self addGestureRecognizer:pinchGesture];
    
}
- (void) rotateMe: (UIRotationGestureRecognizer *) sender
{
    CGAffineTransform transform = CGAffineTransformMakeRotation(sender.rotation);
    self.transform = transform;
}

- (void) pinchMe: (UIPinchGestureRecognizer *) sender
{
    CGAffineTransform transform = CGAffineTransformMakeScale(sender.scale, sender.scale);
    self.transform = transform;
}
@end
