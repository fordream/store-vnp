//
//  DetailDialog.h
//  AnyBook
//
//  Created by Vuong Truong on 4/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Book.h"
#import "ButtonX.h"

#define WIDTH_DIALOG 282
#define HEIGHT_ITEM 50
#define HEIGHT_HEADER 200
#define HEIGHT_DIALOG 300

@interface DetailDialog : UIAlertView<UITableViewDelegate, UITableViewDataSource>{
    Book* book;
    UIImage *image;
}
-(id) init :(Book*)bok;
-(IBAction)onClick:(id)sender;
-(UILabel*)createName:(NSString*)text;
-(UILabel*)createAuThoer:(NSString*)text left:(float)left;
-(UILabel*)createAuThor:(NSString*)text left:(float)left;

-(void)createNamSanxuatHeader:(NSString*)text left:(float)left parent:(UIView*)parent;
-(void)createSotrang:(NSString*)text left:(float)left parent:(UIView*)parent;
-(void)createThongTinSach:(UIView*)parent;
@end
