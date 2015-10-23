//
//  TodoCell.h
//  todo
//
//  Created by Brandon Trebitowski on 9/1/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Todo.h"

@interface TodoCell : UITableViewCell {
	Todo        *todo;
    UILabel     *todoTextLabel;
    UILabel     *todoPriorityLabel;
    UIImageView *todoPriorityImageView;
}

@property (nonatomic, retain) UILabel     *todoTextLabel;
@property (nonatomic, retain) UILabel     *todoPriorityLabel;
@property (nonatomic, retain) UIImageView *todoPriorityImageView;

- (UIImage *)imageForPriority:(NSInteger)priority;

- (Todo *)todo;
- (void)setTodo:(Todo *)newTodo;

@end
