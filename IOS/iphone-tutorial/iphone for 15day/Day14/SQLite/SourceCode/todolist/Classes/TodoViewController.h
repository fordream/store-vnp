//
//  TodoViewController.h
//  todo
//
//  Created by Brandon Trebitowski on 9/6/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Todo.h"

@interface TodoViewController : UIViewController {
	IBOutlet UITextField        *todoText;
	IBOutlet UISegmentedControl *todoPriority;
	IBOutlet UILabel            *todoStatus;
	IBOutlet UIButton			*todoButton;
	Todo						*todo;
}

@property(nonatomic,retain) IBOutlet UITextField        *todoText;
@property(nonatomic,retain) IBOutlet UISegmentedControl *todoPriority;
@property(nonatomic,retain) IBOutlet UILabel            *todoStatus;
@property(nonatomic,retain) IBOutlet UIButton           *todoButton;
@property(nonatomic,retain) Todo						*todo;

- (IBAction) updateStatus:(id) sender;
- (IBAction) updatePriority:(id) sender;
- (IBAction) updateText:(id) sender;

@end
