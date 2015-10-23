//
//  PeopleManager.h
//  DemoTableSearch
//
//  Created by cuong minh on 4/19/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PeopleManager : NSObject
@property (nonatomic, strong) NSMutableArray * peopleArray;
@property (nonatomic, strong) NSMutableArray * resultPeopleArray;

-(id) init: (NSString *) dataFile;
-(void) filterContentForSearchText: (NSString *) searchString;
@end
