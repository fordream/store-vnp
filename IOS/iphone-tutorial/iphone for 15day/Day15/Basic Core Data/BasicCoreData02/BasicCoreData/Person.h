//
//  Person.h
//  BasicCoreData
//
//  Created by cuong minh on 3/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface Person : NSManagedObject

@property (nonatomic, retain) NSDate * dateOfBirth;
@property (nonatomic, retain) NSString * firstName;
@property (nonatomic, retain) NSString * fullName;
@property (nonatomic, retain) NSString * lastName;

@end
