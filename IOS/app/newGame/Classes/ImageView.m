    //
//  ImageView.m
//  newGame
//
//  Created by mac on 9/4/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "ImageView.h"


@implementation ImageView


static NSMutableDictionary *dict;

+ (UIImage*)loadImage:(NSString*)imageName
{
	if (!dict) dict = [[NSMutableDictionary dictionary] retain];
	
	UIImage* image = [dict objectForKey:imageName];
	if (!image)
	{
		NSString* imagePath = [[NSBundle mainBundle] pathForResource:imageName ofType:nil];	
		image = [UIImage imageWithContentsOfFile:imagePath];
		if (image)
		{
			[dict setObject:image forKey:imageName];
		}
	}
	
	return image;
}

+ (void)releaseCache {
	if (dict) {
		[dict removeAllObjects];
	}
}

@end

