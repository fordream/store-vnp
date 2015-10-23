    //
//  DrawView.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/6/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "DrawView.h"


@implementation DrawView
/*
-(void)drawPic:(UIImage *)thisPic {
	
	myPic = thisPic;
	
	[myPicretain];
	
	[selfsetNeedsDisplay];
	
}

- (void)drawRect:(CGRect)rect {
	
	float newHeight;
	
	float newWidth;
	
	if (!myDrawing) {
		
		myDrawing = [[NSMutableArrayalloc] initWithCapacity:0];
		
	}
	
	CGContextRef ctx = UIGraphicsGetCurrentContext();
	
	if (myPic != NULL) {
		
		float ratio = myPic.size.height/460;
		
		if (myPic.size.width/320 > ratio) {
			
			ratio = myPic.size.width/320;
			
		}
		
		newHeight = myPic.size.height/ratio;
		
		newWidth = myPic.size.width/ratio;
		
		[myPicdrawInRect:CGRectMake(0,0,newWidth,newHeight)];
		
	}
	
	if ([myDrawingcount] > 0) {
		
		CGContextSetLineWidth(ctx, 5);
		
		for (int i = 0 ; i < [myDrawingcount] ; i++) {
			
			NSArray *thisArray = [myDrawingobjectAtIndex:i];
			
			if ([thisArray count] > 2) {
				
				float thisX = [[thisArray objectAtIndex:0] floatValue];
				
				float thisY = [[thisArray objectAtIndex:1] floatValue];
				
				CGContextBeginPath(ctx);
				
				CGContextMoveToPoint(ctx, thisX, thisY);
				
				for (int j = 2; j < [thisArray count] ; j+=2) {
					
					thisX = [[thisArray objectAtIndex:j] floatValue];
					
					thisY = [[thisArray objectAtIndex:j+1] floatValue];
					
					CGContextAddLineToPoint(ctx, thisX,thisY);
					
				}
				
				CGContextStrokePath(ctx);
				
			}
			
		}
		
	}
	
}

- (void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
	
	[myDrawingaddObject:[[NSMutableArrayalloc] initWithCapacity:4]];
	
	CGPoint curPoint = [[touches anyObject] locationInView:self];
	
	[[myDrawinglastObject] addObject:[NSNumbernumberWithFloat:curPoint.x]];
	
	[[myDrawinglastObject] addObject:[NSNumbernumberWithFloat:curPoint.y]];
	
}

- (void) touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
	
	CGPoint curPoint = [[touches anyObject] locationInView:self];
	
	[[myDrawinglastObject] addObject:[NSNumbernumberWithFloat:curPoint.x]];
	
	[[myDrawinglastObject] addObject:[NSNumbernumberWithFloat:curPoint.y]];
	
	[selfsetNeedsDisplay];
	
}

- (void) touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
	
	CGPoint curPoint = [[touches anyObject] locationInView:self];
	
	[[myDrawinglastObject] addObject:[NSNumbernumberWithFloat:curPoint.x]];
	
	[[myDrawinglastObject] addObject:[NSNumbernumberWithFloat:curPoint.y]];
	
	[selfsetNeedsDisplay];
	
}

-(void)cancelDrawing {
	
	[myDrawingremoveAllObjects];
	
	[selfsetNeedsDisplay];
	
}

- (void)dealloc {
	
	[super dealloc];
	
	[myPicrelease];
	
	[myDrawingrelease];
	
}

*/



@end
