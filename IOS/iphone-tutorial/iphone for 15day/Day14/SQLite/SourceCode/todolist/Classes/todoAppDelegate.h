//
//  todoAppDelegate.h
//  todo
//
//  Created by Brandon Trebitowski on 8/17/08.
//  Copyright __MyCompanyName__ 2008. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <sqlite3.h>
#import "Todo.h"

@interface todoAppDelegate : NSObject <UIApplicationDelegate> {
	
	IBOutlet UIWindow *window;
	IBOutlet UINavigationController *navigationController;

	sqlite3 *database;
	NSMutableArray *todos;
}

@property (nonatomic, retain) UIWindow *window;
@property (nonatomic, retain) UINavigationController *navigationController;
@property (nonatomic, retain) NSMutableArray *todos;

-(void)removeTodo:(Todo *)todo;
-(Todo *)addTodo;

@end

