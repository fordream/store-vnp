//
//  UiTableViewCellLibraryGrid.m
//  AnyBook
//
//  Created by Vuong Truong on 4/11/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "UiTableViewCellLibraryGrid.h"
#import "BookReader.h"
@implementation UiTableViewCellLibraryGrid

- (id)initWithFrame:(CGRect)frame reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithFrame:frame reuseIdentifier:reuseIdentifier]) {

    }
    
    return self;
}

-(id)init{
    if(self = [super initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:@"UiTableViewCellLibraryGrid"]){
    }
    
    return self;
}


- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    
    [super setSelected:selected animated:animated];
    
    // Configure the view for the selected state
}



- (void)setLabelText:(NSString *)_text index:(int)index{
   // cellText.text = _text;
    if(index == 0){
        label10.text = _text;
    }else if(index == 1){
        label11.text = _text;
    }
}
- (void)setBook:(Book *)b1 book2:(Book *)b2 book3:(Book *)b3 book4:(Book *)b4{
    book1 = b1;
    book2 = b2;
    book3 = b3;
    book4 = b4;
    
    // set title

    label10.text = @"";
    label11.text = @"";
    
    label20.text = @"";
    label21.text = @"";
    
    label30.text = @"";
    label31.text = @"";
    
    label40.text = @"";
    label41.text = @"";


    
    
    if(book1 != nil){
           NSData *mydata = [[NSData alloc] initWithContentsOfURL:[NSURL URLWithString:[book1 getBookTHUMBNAIL]]];
        UIImage *myimage = [[UIImage alloc] initWithData:mydata];
        [image1 setImage:myimage];
        label10.text = [book1 getBookTITLE];
        label11.text = [book1 getBookAUTHOR];
        UILongPressGestureRecognizer *lpgr = [[UILongPressGestureRecognizer alloc] 
                                              initWithTarget:self action:@selector(handleLongPress1:)];
         [btnButton1 addGestureRecognizer:lpgr];
    }
    
    if(book2 != nil){
        
        NSData *mydata = [[NSData alloc] initWithContentsOfURL:[NSURL URLWithString:[book2 getBookTHUMBNAIL]]];
        UIImage *myimage = [[UIImage alloc] initWithData:mydata];
        [image2 setImage:myimage];
        
        
        label20.text = [book2 getBookTITLE];
        label21.text = [book2 getBookAUTHOR];
        UILongPressGestureRecognizer *lpgr = [[UILongPressGestureRecognizer alloc] 
                                              initWithTarget:self action:@selector(handleLongPress2:)];
         [btnButton2 addGestureRecognizer:lpgr];
    }
    
    if(book3!= nil){
        NSData *mydata = [[NSData alloc] initWithContentsOfURL:[NSURL URLWithString:[book3 getBookTHUMBNAIL]]];
        UIImage *myimage = [[UIImage alloc] initWithData:mydata];
        [image3 setImage:myimage];
        
        label30.text = [book3 getBookTITLE];
        label31.text = [book3 getBookAUTHOR];
        UILongPressGestureRecognizer *lpgr = [[UILongPressGestureRecognizer alloc] 
                                              initWithTarget:self action:@selector(handleLongPress3:)];
         [btnButton3 addGestureRecognizer:lpgr];
    }
    
    if(book4!= nil){
        NSData *mydata = [[NSData alloc] initWithContentsOfURL:[NSURL URLWithString:[book4 getBookTHUMBNAIL]]];
        UIImage *myimage = [[UIImage alloc] initWithData:mydata];
        [image4 setImage:myimage];
        
        label40.text = [book4 getBookTITLE];
        label41.text = [book4 getBookAUTHOR];
        UILongPressGestureRecognizer *lpgr = [[UILongPressGestureRecognizer alloc] 
                                              initWithTarget:self action:@selector(handleLongPress4:)];
         [btnButton4 addGestureRecognizer:lpgr];
    }
    
    count = 0;
 }

-(void)handleLongPress1:(UILongPressGestureRecognizer *)gestureRecognizer
{
    [self showDialog:book1];
}

-(void)handleLongPress2:(UILongPressGestureRecognizer *)gestureRecognizer
{
    [self showDialog:book2];
}

-(void)handleLongPress3:(UILongPressGestureRecognizer *)gestureRecognizer
{
   [self showDialog:book3];
}

-(void)handleLongPress4:(UILongPressGestureRecognizer *)gestureRecognizer
{
    [self showDialog:book4];
}

-(void) showDialog:(Book *)book{
    count ++;
    if(count == 2){
        count = 0;
        return;
    }
    NSLog(@"onLongClick");
    AlertBook *alert  = [[AlertBook alloc]init : book];
    [alert setController:mController];
    [alert show];
}

-(IBAction)onCLick:(id)sender{
    if(sender == btnButton1 && book1 != nil){
        BookReader *vc = [[BookReader alloc]initWithBook:book1];
        [mController presentModalViewController:vc animated:YES];
    }
    
    if(sender == btnButton2 && book2 != nil){
        BookReader *vc = [[BookReader alloc]initWithBook:book2];
        [mController presentModalViewController:vc animated:YES];
    }
    
    if(sender == btnButton3 && book3 != nil){
        BookReader *vc = [[BookReader alloc]initWithBook:book3];
        [mController presentModalViewController:vc animated:YES];    }
    
    if(sender == btnButton4 && book4 != nil){
        BookReader *vc = [[BookReader alloc]initWithBook:book4];
        [mController presentModalViewController:vc animated:YES];
    }
    
}


- (NSString*)reuseIdentifier
{
    return @"UiTableViewCellLibraryGrid";
}

-(void)setController:(UIViewController*)controller{
    mController = controller;
}



@end
