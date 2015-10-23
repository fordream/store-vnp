//
//  PlayController.h
//  ElfArcher
//
//  Created by truong vuong on 9/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RoundView.h"


@interface PlayController : UIViewController {
	UIImageView *_view1;
	UIImageView *_view2;
	int width;
	int height;
	
	int top;
	int left;
	int dx,dy;
	
	int width2;
	int height2;
	
}

@end
