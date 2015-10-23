//
//  ItemList.m
//  AnyBook
//
//  Created by Vuong Truong on 4/13/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ItemList.h"
#import "BookReader.h"
@implementation ItemList

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

-(void) setImageView:(UIImage *)image{

    [imageView1 setImage:image];
}
-(void) setlabelhederText:(NSString *)text{
    [labelHeader setText:text];
}
-(void) setlabelAutor:(NSString *)text{
    [labelAuthor setText:text];
}

-(void) setBook:(Book *)book{
    NSData *mydata = [[NSData alloc] initWithContentsOfURL:[NSURL URLWithString:[book getBookTHUMBNAIL]]];
    UIImage *myimage = [[UIImage alloc] initWithData:mydata];
    
    [imageView1 setImage:myimage];
    [labelHeader setText:[book getBookTITLE]];
    [labelAuthor setText:[book getBookAUTHOR]];
    
    mBook = book;
    
    UILongPressGestureRecognizer *lpgr = [[UILongPressGestureRecognizer alloc] 
                                          initWithTarget:self action:@selector(handleLongPress:)];
    [btn addGestureRecognizer:lpgr];
    count = 0;
}
-(IBAction)onClick:(id)sender{
    NSLog(@"onCLick");
    BookReader *vc = [[BookReader alloc]initWithBook:mBook];
    [mController presentModalViewController:vc animated:YES];

}
-(void)handleLongPress:(UILongPressGestureRecognizer *)gestureRecognizer
{
    count ++;
    if(count == 2){
        count = 0;
        return;
    }
    
    AlertBook *alert  = [[AlertBook alloc]init : mBook];
    [alert setController:mController];
    [alert show];
}

-(void)setController:(UIViewController*)controller{
    mController = controller;
}

@end
