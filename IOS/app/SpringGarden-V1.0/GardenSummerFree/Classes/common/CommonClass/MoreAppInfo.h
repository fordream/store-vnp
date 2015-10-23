//
//  MoreAppInfo.h
//  VietAirline
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface MoreAppInfo : NSObject {
	
	int idValue;
	NSString *appName;
	NSString *appLink;
	NSString *appIcon;
	NSString *appImage;
	NSString *appIntro;
	
}

@property int idValue;
@property(nonatomic, retain) NSString *appName;
@property(nonatomic, retain) NSString *appLink;
@property(nonatomic, retain) NSString *appIcon;
@property(nonatomic, retain) NSString *appImage;
@property(nonatomic, retain) NSString *appIntro;

@end
