//
//  FriendViewController.m
//  DemoTableStoryBoard
//
//  Created by cuong minh on 7/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "FriendViewController.h"

@interface FriendViewController ()

@end

@implementation FriendViewController
@synthesize photo = _photo;
@synthesize friend = _friend;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	self.title = self.friend.fullName;
    self.photo.image = self.friend.photo;
}

- (void)viewDidUnload
{
    [self setPhoto:nil];
    [super viewDidUnload];
    _friend = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
