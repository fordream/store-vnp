//
//  DatabaseExampleViewController.h
//  DatabaseExample
//
//  Created by Truong Vuong on 10/13/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DBManager.h"
#import "QuartController.h"
#import "GetResponseXMLFromRequestString.h"
#import "MBProgressHUD.h"
#import "TBXML.h"

@interface DatabaseExampleViewController : UIViewController <GetResponseXMLFromRequestStringDelegate>{
	MBProgressHUD *loadingIndicator;
    //AppDelegate *appDelegate;
}
-(IBAction)onClick:(id)sender;

@end

