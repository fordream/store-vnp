//
//  NSLogUtils.h
//  Untitled
//
//  Created by Truong Vuong on 10/12/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface NSLogUtils : NSObject {
	NSString *tag;
	
}

@property(nonatomic, readwrite,retain) NSString *tag;
-(void) log :(NSString*)message;
-(NSLogUtils*)init : (NSString*)_tag;
@end
