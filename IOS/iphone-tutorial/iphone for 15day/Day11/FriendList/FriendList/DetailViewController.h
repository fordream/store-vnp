//
//  DetailViewController.h
//  FriendList
//
//  Created by Techmaster on 7/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Friend.h"
@interface DetailViewController : UIViewController
@property (weak, nonatomic) IBOutlet UIImageView *photo;
@property (nonatomic, strong) Friend *friend;

@end
