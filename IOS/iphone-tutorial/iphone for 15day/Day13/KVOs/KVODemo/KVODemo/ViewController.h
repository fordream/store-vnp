//
//  ViewController.h
//  KVODemo
//
//  Created by cuong minh on 4/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Person.h"
@interface ViewController : UIViewController  <UIPickerViewDataSource, UIPickerViewDelegate>
@property (weak, nonatomic) IBOutlet UIPickerView *colorPicker;
@property (strong, nonatomic) UIColor *color;
@property (strong, nonatomic) NSArray *colorArray;
@property (strong, nonatomic) IBOutlet UILabel *myLabel;
@property (strong, nonatomic) IBOutlet UIButton *funButton;
@property (strong, nonatomic) Person *person;

@end
