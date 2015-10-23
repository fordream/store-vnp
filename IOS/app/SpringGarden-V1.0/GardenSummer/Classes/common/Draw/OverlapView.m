//
//  OverlapView.m
//  PikachuGameForIpad
//
//  Created by namnd on 5/30/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "OverlapView.h"


@implementation OverlapView


- (id)initWithFrame:(CGRect)frame {
	if (self = [super initWithFrame:frame]) {
		// Initialization code
	}
	return self;
}


- (void)drawRect:(CGRect)rect {
	
	CGContextRef	context = UIGraphicsGetCurrentContext();
	
	CGContextSetLineWidth(context, 5.0);
	CGContextMoveToPoint(context, 50, 100);
	CGContextAddLineToPoint(context, 200, 100);
	CGContextStrokePath(context);
	
	
	CGContextAddEllipseInRect(context, CGRectMake(70.0, 170.0, 50.0, 50.0));
	CGContextStrokePath(context);
	
	
	CGContextAddEllipseInRect(context, CGRectMake(150.0, 170.0, 50.0, 50.0));
	CGContextFillPath(context);
	
	
	CGContextSetRGBStrokeColor(context, 0.0, 1.0, 0.0, 1.0);
	CGContextSetRGBFillColor(context, 0.0, 0.0, 1.0, 1.0);
	CGContextAddRect(context, CGRectMake(30.0, 30.0, 60.0, 60.0));
	CGContextFillPath(context);
	
	
	CGContextAddArc(context, 260, 90, 40, 0.0*M_PI/180, 270*M_PI/180, 1);
	CGContextAddLineToPoint(context, 280, 350);
	CGContextStrokePath(context);
	
	CGContextMoveToPoint(context, 130, 300);
	CGContextAddLineToPoint(context, 80, 400);
	CGContextAddLineToPoint(context, 190, 400);
	CGContextAddLineToPoint(context, 130, 300);
	CGContextStrokePath(context);	
	
}


- (void) drawLineToPoint:(CGPoint)startPoint toPoint:(CGPoint)nextPoint {
	
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGContextSetLineWidth(context, 3.0);
	CGContextSetRGBStrokeColor(context, 1, 0, 1, 1.0); // red line 
	CGContextMoveToPoint(context, startPoint.x, startPoint.y);
	CGContextAddLineToPoint(context, nextPoint.x, nextPoint.y);
	CGContextStrokePath(context);
	
}

- (void)dealloc {
    [super dealloc];
}


@end
