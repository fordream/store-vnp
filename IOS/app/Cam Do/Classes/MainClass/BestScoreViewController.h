//
//  BestScoreViewController.h
//  DiamondGame
//
//  Created by hnsunflower1807 on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface BestScoreViewController : UIViewController {
	
	UIButton *homeBtn;
}

@property(nonatomic, retain) IBOutlet UIButton *homeBtn;

-(IBAction) clickHomeBtn:(id)sender;

@end
