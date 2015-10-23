//
//  AlertBook.h
//  AnyBook
//
//  Created by Vuong Truong on 4/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Book.h"
#import "DetailDialog.h"

@interface AlertBook : UIAlertView{
    UIButton *btnX;
    
    UIButton *btnRead;
    UIButton *btnBinhLuan;
    UIButton *btnDetail;
    UIButton *btnTang;
    UIViewController *mController;
    
    Book *book;
}
-(id) init :(Book*)bok;
-(UIButton *)createButton:(float)y name:(NSString *)title;
-(IBAction)onClick:(id)sender;
-(void)setController:(UIViewController*)controller;
@end
