//
//  BoardInfo.h
//  PikachuGameForIpad
//
//  Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface BoardInfo : NSObject {
	int tag;
	int enabled;
}

@property(nonatomic, readwrite) int tag;
@property(nonatomic, readwrite) int enabled;

- (id) initWithTag : (NSInteger) theTag 
	andEnableState : (NSInteger) theState;

@end
