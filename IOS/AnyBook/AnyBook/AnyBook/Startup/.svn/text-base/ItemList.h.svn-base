//
//  ItemList.h
//  AnyBook
//
//  Created by Vuong Truong on 4/13/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Book.h"
#import "AlertBook.h"


@interface ItemList : UITableViewCell{
    IBOutlet UIImageView *imageView1;
    IBOutlet UILabel *labelHeader;
    IBOutlet UILabel *labelAuthor;
    
    IBOutlet UIButton *btn;
    UIViewController *mController;
    //Menulongpress *menulongPress;
    int count;
    Book *mBook;
}

-(void) setImageView:(UIImage *)image;
-(void) setlabelhederText:(NSString *)text;
-(void) setlabelAutor:(NSString *)text;
-(void) setBook:(Book *)book;
-(IBAction)onClick:(id)sender;
-(void)setController:(UIViewController*)controller;
@end
