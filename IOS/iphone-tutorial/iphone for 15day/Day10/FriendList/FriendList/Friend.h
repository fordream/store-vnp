//
//  Friend.h
//  FriendList
//
//  Created by Techmaster on 7/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Friend : NSObject
@property (nonatomic, strong) NSString *fullName;
@property (nonatomic, strong) NSString *notes;
@property (nonatomic, strong) UIImage *photo;
-(id)init: (NSString *) FullName withNotes: (NSString *)Notes andPhoto: (NSString *)aPhoto;
@end
