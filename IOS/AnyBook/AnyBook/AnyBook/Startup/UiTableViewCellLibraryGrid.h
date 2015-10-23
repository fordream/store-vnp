//
//  UiTableViewCellLibraryGrid.h
//  AnyBook
//
//  Created by Vuong Truong on 4/11/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Book.h"
#import "AlertBook.h"
@interface UiTableViewCellLibraryGrid : UITableViewCell{
    IBOutlet UIImageView *image1;
    IBOutlet UIImageView *image2;
    IBOutlet UIImageView *image3;
    IBOutlet UIImageView *image4;
    
    
    IBOutlet UILabel *label10;
    IBOutlet UILabel *label11;
    
    IBOutlet UILabel *label20;
    IBOutlet UILabel *label21;
    
    IBOutlet UILabel *label30;
    IBOutlet UILabel *label31;
    
    IBOutlet UILabel *label40;
    IBOutlet UILabel *label41;
    
    IBOutlet UIButton *btnButton1;
    IBOutlet UIButton *btnButton2;
    IBOutlet UIButton *btnButton3;
    IBOutlet UIButton *btnButton4;
    
    
    Book *book1;
    Book *book2;
    Book *book3;
    Book *book4;

    int count;
    
    UIViewController *mController;

    
}

- (void)setLabelText:(NSString *)_text index:(int)index;
- (void)setBook:(Book *)b1 book2:(Book *)b2 book3:(Book *)b3 book4:(Book *)b4;
-(IBAction)onCLick:(id)sender;
-(void) showDialog:(Book *)book;

-(void)setController:(UIViewController*)controller;

@end
