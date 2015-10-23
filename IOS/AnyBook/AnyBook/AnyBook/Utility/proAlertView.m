//
//  proAlertView.m
//  Key Chain
//
//  Created by Jonah on 11-05-10.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "proAlertView.h"

@interface proAlertView (Private)

- (void) drawRoundedRect:(CGRect) rect inContext:(CGContextRef) 
context withRadius:(CGFloat) radius;

@end

static UIColor *fillColor = nil;
static UIColor *borderColor = nil;


@implementation proAlertView

@synthesize alertType, alpha;

- (void) setBackgroundColor:(UIColor *) background 
			withStrokeColor:(UIColor *) stroke alpha:(float)al
{

	fillColor = background;
	borderColor = stroke;
    self.alpha = al;
}

- (id)initWithFrame:(CGRect)frame
{
    if((self = [super initWithFrame:frame]))
	{
        if(fillColor == nil)
		{
		
		}
    }
	
    return self;
}

- (void)layoutSubviews
{
	for (UIView *sub in [self subviews])
	{
		if([sub class] == [UIImageView class] && sub.tag == 0)
		{
			// The alert background UIImageView tag is 0, 
			// if you are adding your own UIImageView's 
			// make sure your tags != 0 or this fix 
			// will remove your UIImageView's as well!
			[sub removeFromSuperview];
//			break;
		}
        if ([sub class] != [UILabel class])
        {
            [sub removeFromSuperview];
        }
	}
    if (alertType == TYPE_OK){
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        CGRect frame = self.frame;
        [button setFrame:CGRectMake(86, frame.size.height - 70, 116, 32)];
        [button.titleLabel setFont:[UIFont boldSystemFontOfSize:16]];
        [button setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
        [button setTitle:STRING_OK forState:UIControlStateNormal];
        [button setBackgroundImage:[UIImage imageNamed:@"rect_btn_default.png"] forState:UIControlStateNormal];
        [button setBackgroundImage:[UIImage imageNamed:@"rect_btn_highlighted.png"] forState:UIControlStateHighlighted];
        [button addTarget:self action:@selector(dismissAlert) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:button];
    }else if (alertType == TYPE_CONFIRM)
    {
        UIButton *button_yes = [UIButton buttonWithType:UIButtonTypeCustom];
        CGRect frame = self.frame;
        [button_yes setFrame:CGRectMake(25, frame.size.height - 70, 116, 32)];
        [button_yes.titleLabel setFont:[UIFont boldSystemFontOfSize:16]];
        [button_yes setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
        [button_yes setTitle:STRING_YES forState:UIControlStateNormal];
        [button_yes setBackgroundImage:[UIImage imageNamed:@"rect_btn_default.png"] forState:UIControlStateNormal];
        [button_yes setBackgroundImage:[UIImage imageNamed:@"rect_btn_highlighted.png"] forState:UIControlStateHighlighted];
        [button_yes addTarget:self action:@selector(dismissAlertByClickOk) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:button_yes];
        
        UIButton *button_no = [UIButton buttonWithType:UIButtonTypeCustom];
        [button_no setFrame:CGRectMake(145, frame.size.height - 70, 116, 32)];
        [button_no.titleLabel setFont:[UIFont boldSystemFontOfSize:16]];
        [button_no setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
        [button_no setTitle:STRING_NO forState:UIControlStateNormal];
        [button_no setBackgroundImage:[UIImage imageNamed:@"rect_btn_default.png"] forState:UIControlStateNormal];
        [button_no setBackgroundImage:[UIImage imageNamed:@"rect_btn_highlighted.png"] forState:UIControlStateHighlighted];
        [button_no addTarget:self action:@selector(dismissAlertByClickCancel) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:button_no];
    }else if (alertType == TYPE_DISCONNECT)
    {
        UIImage *img = [UIImage imageNamed:@"10-other-disconnect4444_03.png"];
        UIImageView *image = [[UIImageView alloc] initWithFrame:CGRectMake(110, 100, img.size.width, img.size.height)];
        [image setImage:img];
        [self addSubview:image];
        
        UILabel *lb = [[UILabel alloc] initWithFrame:CGRectMake(105, 190, 200, 21)];
        [lb setFont:[UIFont systemFontOfSize:14]];
        [lb setBackgroundColor:[UIColor clearColor]];
        [lb setTextColor:[UIColor lightGrayColor]];
        [lb setText:STRING_DISCONNECT];
        [self addSubview:lb];
        
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        CGRect frame = self.frame;
        [button setFrame:CGRectMake(100, frame.size.height - 150, 116, 32)];
        [button.titleLabel setFont:[UIFont boldSystemFontOfSize:16]];
        [button setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
        [button setTitle:STRING_RETRY forState:UIControlStateNormal];
        [button setBackgroundImage:[UIImage imageNamed:@"rect_btn_default.png"] forState:UIControlStateNormal];
        [button setBackgroundImage:[UIImage imageNamed:@"rect_btn_highlighted.png"] forState:UIControlStateHighlighted];
        [button addTarget:self action:@selector(dismissAlert) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:button];
    }
}

- (void)drawRect:(CGRect)rect
{	
	CGContextRef context = UIGraphicsGetCurrentContext();
	
	CGContextClearRect(context, rect);
	CGContextSetAllowsAntialiasing(context, true);
	CGContextSetLineWidth(context, 0.0);
	CGContextSetAlpha(context, 0.8); 
	CGContextSetLineWidth(context, 2.0);
	CGContextSetStrokeColorWithColor(context, [borderColor CGColor]);
	CGContextSetFillColorWithColor(context, [fillColor CGColor]);
	
	// Draw background
	CGFloat backOffset = 2;
	CGRect backRect = CGRectMake(rect.origin.x + backOffset, 
								 rect.origin.y + backOffset, 
								 rect.size.width - backOffset*2, 
								 rect.size.height - backOffset*2);
	
	[self drawRoundedRect:backRect inContext:context withRadius:8];
	CGContextDrawPath(context, kCGPathFillStroke);
	
	// Clip Context
	CGRect clipRect = CGRectMake(backRect.origin.x + backOffset-1, 
								 backRect.origin.y + backOffset-1, 
								 backRect.size.width - (backOffset-1)*2, 
								 backRect.size.height - (backOffset-1)*2);
	
	[self drawRoundedRect:clipRect inContext:context withRadius:8];
	CGContextClip (context);
	/*
	//Draw highlight
	CGGradientRef glossGradient;
	CGColorSpaceRef rgbColorspace;
	size_t num_locations = 2;
	CGFloat locations[2] = { 0.0, 1.0 };
	CGFloat components[8] = { 1.0, 1.0, 1.0, 0.35, 1.0, 1.0, 1.0, 0.06 };
	rgbColorspace = CGColorSpaceCreateDeviceRGB();
	glossGradient = CGGradientCreateWithColorComponents(rgbColorspace, 
														components, locations, num_locations);
	
	CGRect ovalRect = CGRectMake(-130, -115, (rect.size.width*2), 
								 rect.size.width/2);
	
	CGPoint start = CGPointMake(rect.origin.x, rect.origin.y);
	CGPoint end = CGPointMake(rect.origin.x, rect.size.height/5);
	
	CGContextSetAlpha(context, 1.0); 
	CGContextAddEllipseInRect(context, ovalRect);
	CGContextClip (context);
	
	CGContextDrawLinearGradient(context, glossGradient, start, end, 0);
	
	CGGradientRelease(glossGradient);
	CGColorSpaceRelease(rgbColorspace); */
}

- (void) drawRoundedRect:(CGRect) rrect inContext:(CGContextRef) context 
			  withRadius:(CGFloat) radius
{
	CGContextBeginPath (context);
	
	CGFloat minx = CGRectGetMinX(rrect), midx = CGRectGetMidX(rrect), 
	maxx = CGRectGetMaxX(rrect);
	
	CGFloat miny = CGRectGetMinY(rrect), midy = CGRectGetMidY(rrect), 
	maxy = CGRectGetMaxY(rrect);
	
	CGContextMoveToPoint(context, minx, midy);
	CGContextAddArcToPoint(context, minx, miny, midx, miny, radius);
	CGContextAddArcToPoint(context, maxx, miny, maxx, midy, radius);
	CGContextAddArcToPoint(context, maxx, maxy, midx, maxy, radius);
	CGContextAddArcToPoint(context, minx, maxy, minx, midy, radius);
	CGContextClosePath(context);
}

- (void)disableDismissForIndex:(int)index_{
	
	canIndex = index_;
	disableDismiss = TRUE;
}

- (void)dismissAlert{
	
	[self dismissWithClickedButtonIndex:[self cancelButtonIndex] animated:YES];
}

- (void)vibrateAlert:(float)seconds{
	canVirate = TRUE;

	[self moveLeft];
	
	[self performSelector:@selector (stopVibration) withObject:nil afterDelay:seconds];
}

-(void)dismissWithClickedButtonIndex:(NSInteger)buttonIndex animated:(BOOL)animated {
    
	if (disableDismiss == TRUE && canIndex == buttonIndex){
		
	}else {

	
	[super dismissWithClickedButtonIndex:buttonIndex animated:animated];
		
	}
}


- (void)hideAfter:(float)seconds{
	
	[self performSelector:@selector (dismissAlert) withObject:nil afterDelay:seconds];
	
}

- (void)moveRight{
	
	if (canVirate){
		
		[UIView beginAnimations:nil context:NULL];
		[UIView setAnimationDuration:0.05];
		
		self.transform = CGAffineTransformMakeTranslation(-10.0, 0.0);
		
		[UIView commitAnimations];
		
		[self performSelector:@selector (moveLeft) withObject:nil afterDelay:0.05];
		
	}
	
}
- (void)moveLeft{
	
	if (canVirate){
	
	[UIView beginAnimations:nil context:NULL];
	[UIView setAnimationDuration:0.05];
	
	self.transform = CGAffineTransformMakeTranslation(10.0, 0.0);
	
	[UIView commitAnimations];
	
  [self performSelector:@selector (moveRight) withObject:nil afterDelay:0.05];
		
	}
}

- (void)stopVibration{
	
	canVirate = FALSE;
	
	self.transform = CGAffineTransformMakeTranslation(0.0, 0.0);
}

- (void)dismissAlertByClickCancel
{
//    [self.delegate alertView:self willDismissWithButtonIndex:self.cancelButtonIndex];
//    [self dismissWithClickedButtonIndex:[self cancelButtonIndex] animated:YES];
    [self.delegate alertView:self clickedButtonAtIndex:self.cancelButtonIndex];
}
- (void)dismissAlertByClickOk
{
//    [self.delegate alertView:self willDismissWithButtonIndex:self.firstOtherButtonIndex];
//    [self dismissWithClickedButtonIndex:[self firstOtherButtonIndex] animated:YES];
    [self.delegate alertView:self clickedButtonAtIndex:self.firstOtherButtonIndex];
}

@end
