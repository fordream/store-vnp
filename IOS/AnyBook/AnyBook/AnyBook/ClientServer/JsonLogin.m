//
//  JsonLogin.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/7/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "JsonLogin.h"
#import "ClientServer.h"

@implementation JsonLogin
@synthesize account;

- (id)init {
    self = [super init];
    if (self) {
        account = [[UserAccount alloc] init];
    }
    return self;
}

+ (JsonLogin*)loginWithUsername:(NSString*)username password:(NSString*)password
{
    NSString* url = [ClientServer getLoginUrl:username wPassword:password];
    JsonLogin* jl = [[JsonLogin alloc] init];
    NSDictionary* json = [jl requestSynchronousWithURLString:url withShowAlert:NO];
    if ([jl isSuccess]) {
        NSDictionary* data = [json objectForKey:@"data"];
        jl.account.user_full_name = [jl getString:data skey:@"full_name" require:NO];
        jl.account.user_mobile = username; // [jl getString:data skey:@"mobile" require:NO];
        jl.account.user_email = [jl getString:data skey:@"email" require:NO];
        jl.account.user_address = [jl getString:data skey:@"address" require:NO];
    }
    return jl;
}


+ (JsonLogin*)register:
    (NSString*)fullname 
    mobile:(NSString*)mobile
    email:(NSString*)email
    address:(NSString*)address
    password:(NSString*)password
{
    
    NSString* url = [ClientServer getRegisterUrl:fullname 
                                          mobile:mobile
                                           email:email
                                         address:address
                                        password:password];
    
    JsonLogin* jl = [[JsonLogin alloc] init];
    
    NSDictionary* json = [jl requestSynchronousWithURLString:url withShowAlert:NO];
    
    if ([jl isSuccess]) {
        NSDictionary* data = [json objectForKey:@"data"];
        jl.account.user_full_name = [jl getString:data skey:@"full_name" require:NO];
        jl.account.user_mobile = [jl getString:data skey:@"mobile" require:NO];
        jl.account.user_email = [jl getString:data skey:@"email" require:NO];
        jl.account.user_address = [jl getString:data skey:@"address" require:NO];
    }
    return jl;
}


@end
