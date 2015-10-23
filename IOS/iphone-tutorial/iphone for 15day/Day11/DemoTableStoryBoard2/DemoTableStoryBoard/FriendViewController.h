//
//  FriendViewController.h
//  DemoTableStoryBoard
//
//  Created by cuong minh on 7/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Friend.h"
@interface FriendViewController : UIViewController
@property (nonatomic, strong) Friend* friend;
@property (strong, nonatomic) IBOutlet UIImageView *photo;
@end
