//
//  ViewController.h
//  DemoMemoryManagement
//
//  Created by cuong minh on 2/29/12.
//  Copyright (c) 2012 TechMaster.vn. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
- (IBAction)MemoryLeakDemo:(id)sender;
- (IBAction)RetainDemo:(id)sender;
- (IBAction)PointToOtherOject:(id)sender;

#pragma mark - Property Declaration
@property (nonatomic, assign) int primitiveNumber;

/*
 @property (nonatomic, assign, readwrite) int primitiveNumber; is valid
 @property (nonatomic, assign, readonly) int primitiveNumber; is not valid why?
 */

@property (nonatomic, strong) UIImage *myImage;
//@property (nonatomic, strong) UIImage *myImage;
/* Try this line @property (nonatomic, assign) UIImage* myImage; and rerun to see what happens. Assign is used for primitive, struct data type not for object */

@property (nonatomic, copy) NSMutableString *myString;  //Change from copy to strong or retain to see different output
@property (nonatomic, copy) NSString *myConstantString;

@end
