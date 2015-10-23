//
//  MenuController.h
//  ElfArcher
//
//  Created by truong vuong on 9/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PlayController.h"

@interface MenuController : UIViewController {
	UIButton  *btnPlay;
}
@property (nonatomic, retain) IBOutlet UIButton *btnPlay;
-(IBAction)onClick:(id)sender;

@end
