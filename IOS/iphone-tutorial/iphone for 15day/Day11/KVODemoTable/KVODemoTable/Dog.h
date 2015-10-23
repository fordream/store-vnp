//
//  Animal.h
//  KVODemoTable
//
//  Created by cuong minh on 4/4/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
typedef enum {
    Retriever,
    Working,
    Toy,
    Hunting,
    Herding,
    Sporting
} DogType;


@interface Dog : NSObject
@property (nonatomic, strong) NSString* name;
@property (nonatomic, assign) DogType type;

-(id) init :(NSString*) _name withType: (DogType) _type;
@end
