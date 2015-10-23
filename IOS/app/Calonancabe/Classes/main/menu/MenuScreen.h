//
//  MenuScreen.h
//  Calonancabe
//
//  Created by Truong Vuong on 10/1/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PlayScreen.h"

@interface MenuScreen : UIViewController {
	IBOutlet UIButton * btnPlay;
	IBOutlet UIButton * btnOptions;
	IBOutlet UIButton * btnRanking;
	IBOutlet UIButton * btnHowToPlay;
	IBOutlet UIButton * btnCredit;
}

@property (nonatomic, retain) IBOutlet UIButton *btnPlay;
@property (nonatomic, retain) IBOutlet UIButton *btnOptions;
@property (nonatomic, retain) IBOutlet UIButton *btnRanking;
@property (nonatomic, retain) IBOutlet UIButton *btnHowToPlay;
@property (nonatomic, retain) IBOutlet UIButton *btnCredit;

-(IBAction) onClick:(id)sender;
@end
