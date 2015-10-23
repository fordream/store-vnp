//
//  Category.h
//  MasterDetailCoreData
//
//  Created by cuong minh on 5/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>


@interface Category : NSManagedObject

@property (nonatomic, retain) NSString * name;
@property (nonatomic, retain) NSSet *toStuff;
@end

@interface Category (CoreDataGeneratedAccessors)

- (void)addToStuffObject:(NSManagedObject *)value;
- (void)removeToStuffObject:(NSManagedObject *)value;
- (void)addToStuff:(NSSet *)values;
- (void)removeToStuff:(NSSet *)values;

@end
