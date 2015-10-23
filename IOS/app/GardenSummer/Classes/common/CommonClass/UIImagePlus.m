//
//  UIImagePlus.m
//  VCooking
//
//   Created by namnd on 7/27/11.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "UIImagePlus.h"


@implementation UIImage (UIImagePlus)


- (UIImage*)scaleToSize:(CGSize)size {
	
	UIGraphicsBeginImageContext(size);
	
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGContextTranslateCTM(context, 0.0, size.height);
	CGContextScaleCTM(context, 1.0, -1.0);
	
	CGContextDrawImage(context, CGRectMake(0.0f, 0.0f, size.width, size.height), self.CGImage);
	
	UIImage* scaledImage = UIGraphicsGetImageFromCurrentImageContext();
	
	UIGraphicsEndImageContext();
	
	return scaledImage;
}


@end
