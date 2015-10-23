//
//  Stuff.h
//  MasterDetailCoreData
//
//  Created by cuong minh on 5/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Category;

@interface Stuff : NSManagedObject

@property (nonatomic, retain) NSString * name;
@property (nonatomic) NSDate *purchaseDate;
@property (nonatomic) NSDate *guaranteeDate;
@property (nonatomic) double price;
@property (nonatomic, retain) id photo;
@property (nonatomic, retain) Category *toCategory;

@end
