//
//  AlertBook.m
//  AnyBook
//
//  Created by Vuong Truong on 4/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "AlertBook.h"
#import "BookReader.h"
@implementation AlertBook

-(id) init :(Book*)bok{
    self = [super initWithFrame:CGRectMake(0, 0, 100, 100)];
    
    //add data 
    book = bok;
    
    
    [self setTitle:@"\n\n\n\n"];
    
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 100, 100)];
    
    float y = 20;
    float space = 35;
    
    
    UIImage *image = [UIImage imageNamed:@"x_normal.png"];
    UIImage *image_select = [UIImage imageNamed:@"x_select.png"];
    
    
    //X button
    btnX = [[UIButton alloc]initWithFrame:CGRectMake(0, 0, 20.0f, 20.0f)];
    [btnX setBackgroundImage:image forState:UIControlStateNormal];
    [btnX setBackgroundImage:image_select forState:UIControlStateReserved];
    [view addSubview:btnX];
    
     

    btnRead = [self createButton :(y) name:@"Đọc sách"];
    btnBinhLuan = [self createButton :(y + space) name:@"Bình luận"];
    btnDetail = [self createButton :(y + space * 2) name:@"Detail"];
    btnTang = [self createButton :(y + space * 3) name:@"Tặng"];
    
    [self addSubview:btnRead];
    [self addSubview:btnBinhLuan];
    [self addSubview:btnDetail];
    [self addSubview:btnTang];
    
    [self addSubview:view];

    [btnX addTarget:self action:@selector(onClick:) forControlEvents:UIControlEventTouchUpInside];
    
    [btnBinhLuan addTarget:self action:@selector(onClick:) forControlEvents:UIControlEventTouchUpInside];
    [btnDetail addTarget:self action:@selector(onClick:) forControlEvents:UIControlEventTouchUpInside];
    [btnRead addTarget:self action:@selector(onClick:) forControlEvents:UIControlEventTouchUpInside];
    [btnTang addTarget:self action:@selector(onClick:) forControlEvents:UIControlEventTouchUpInside];
    
    return self;
}

-(UIButton *)createButton:(float)y name:(NSString *)title{
    float width = 100;
    float height = 30;
    float left = 95;
 
    UIImage *image = [UIImage imageNamed:@"btn_btn.png"];
    UIImage *image_select = [UIImage imageNamed:@"btn_btn_select.png"];
    UIButton * btnBinhLuan1 = [[UIButton alloc]initWithFrame:CGRectMake(left, y , width, height)];
    [btnBinhLuan1 setTitle:title forState:UIControlStateNormal];
    [btnBinhLuan1 setBackgroundImage:image forState:UIControlStateNormal];
    [btnBinhLuan1 setBackgroundImage:image_select forState:UIControlStateReserved];
    
    return btnBinhLuan1;
}

-(IBAction)onClickDetail:(id)sender{
    NSLog(@"sssss");
    [[[DetailDialog alloc]init:book]show];
}
-(IBAction)onClick:(id)sender{
    [self dismissWithClickedButtonIndex:0 animated:NO];
    
    if(sender == btnX){
        
    }else if(btnBinhLuan == sender){
        
    }else if(btnDetail == sender){
        NSLog(@"sssss");
        [[[DetailDialog alloc]init:book]show];
    }else if(btnRead == sender){
        BookReader *vc = [[BookReader alloc]initWithBook:book];
        [mController presentModalViewController:vc animated:YES];
    }else if(sender == btnTang){
    }

}

-(void)setController:(UIViewController*)controller{
    mController = controller;
}

@end
