//
//  Setting.h
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/17/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Setting : NSObject

@property (nonatomic, strong) UIImage *photo;
@property (nonatomic, strong) NSString *optionName;
@property (nonatomic) NSInteger mode;

-(id) init:(NSString *)OptionName mode:(NSInteger)Mode andPhoto:(NSString *)aPhoto;

@end
