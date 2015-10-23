//
//  MenuViewController.h
//  DiamondGame
//
//  Created by hnsunflower1807 on 6/20/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface MenuViewController : UIViewController {
	
	int modeGameChoosed;
	
	IBOutlet UIButton *styleOld;
	
	UIButton *playBtnClassic;
	UIButton *playBtnTimeBeaten;
	UIButton *helpBtn;
	UIButton *bestScoreBtn;
	
	
}

@property(nonatomic, readwrite) int modeGameChoosed;

@property(nonatomic, retain) IBOutlet UIButton *playBtnClassic;
@property(nonatomic, retain) IBOutlet UIButton *playBtnTimeBeaten;

@property(nonatomic, retain) IBOutlet UIButton *helpBtn;
@property(nonatomic, retain) IBOutlet UIButton *bestScoreBtn;

//==========================
-(IBAction) ClickPlayBtn:(id)sender;
-(IBAction) clickHelpBtn:(id)sender;
-(IBAction) clickBestScoreBtn:(id)sender;

@end
