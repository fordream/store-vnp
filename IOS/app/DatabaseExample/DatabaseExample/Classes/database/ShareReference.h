//
//  ShareReference.h
//  DatabaseExample
//
//  Created by Truong Vuong on 10/14/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface ShareReference : NSObject {
	NSUserDefaults *defaults;
}

@property(nonatomic, readwrite,retain) NSUserDefaults *defaults;
+ (ShareReference *)sharedInstance;

-(void)setObject :(NSString*)data forKey:(NSString*)key;
-(NSString *)getString:(NSString*)key;
@end
