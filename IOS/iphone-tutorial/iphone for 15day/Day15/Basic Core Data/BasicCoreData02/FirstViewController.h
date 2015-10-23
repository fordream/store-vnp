//
//  FirstViewController.h
//  BasicCoreData
//
//  Created by cuong minh on 3/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Person.h"


@interface FirstViewController : UIViewController
@property (nonatomic, strong) NSManagedObjectContext *managedObjectContext;
@end
