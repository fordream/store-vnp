//
//  JsonLogin.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/7/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "JsonBase.h"
#import "UserAccount.h"

@interface JsonLogin : JsonBase {
    
}

@property (nonatomic, retain) UserAccount* account;

+ (JsonLogin*)loginWithUsername:(NSString*)username password:(NSString*)password;
+ (JsonLogin*)register:
    (NSString*)fullname 
    mobile:(NSString*)mobile
    email:(NSString*)email
    address:(NSString*)address
    password:(NSString*)password;
//+ (JsonLogin*)synchronizeData:(NSString*)mobile password:(NSString*)password;

@end
