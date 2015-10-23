//
//  proAlertView.h
//  Key Chain
//
//  Created by Jonah on 11-05-10.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


#define TYPE_OK         0
#define TYPE_CONFIRM    1
#define TYPE_DISCONNECT 2

@interface proAlertView : UIAlertView {
	
	int canIndex;
	BOOL disableDismiss;
    BOOL canVirate;
    int alertType;
    float alpha;
    
}

@property (readwrite) int alertType;
@property (readwrite) float alpha;

-(void) setBackgroundColor:(UIColor *) background 
			withStrokeColor:(UIColor *) stroke alpha:(float)al;

- (void)disableDismissForIndex:(int)index_;
- (void)dismissAlert;
- (void)vibrateAlert:(float)seconds;

- (void)moveRight;
- (void)moveLeft;

- (void)hideAfter:(float)seconds;

- (void)stopVibration;
- (void)dismissAlertByClickCancel;
- (void)dismissAlertByClickOk;

@end
