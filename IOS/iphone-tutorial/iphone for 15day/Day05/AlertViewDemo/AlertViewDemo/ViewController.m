//
//  ViewController.m
//  AlertViewDemo
//
//  Created by cuong minh on 3/5/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@implementation ViewController

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

#pragma mark - AlertView Basic
- (IBAction)simpleAlert:(id)sender {
    UIAlertView *alertView = [[UIAlertView alloc]
                            initWithTitle:@"Title"
                            message:@"Message"
                            delegate:nil
                            cancelButtonTitle:@"Cancel"
                            otherButtonTitles:@"Ok", nil];
  [alertView show];
}

- (NSString *) yesButtonTitle{
    return @"Yes";
}

- (NSString *) noButtonTitle{
    return @"No";
}

- (void) alertView:(UIAlertView *)alertView 
   clickedButtonAtIndex:(NSInteger)buttonIndex{
    
    NSString *buttonTitle = [alertView buttonTitleAtIndex:buttonIndex];
    
    if ([buttonTitle isEqualToString:[self yesButtonTitle]]){
        NSLog(@"User pressed the Yes button.");
    }
    else if ([buttonTitle isEqualToString:[self noButtonTitle]]){
        NSLog(@"User pressed the No button.");
    }
    else if ([buttonTitle isEqualToString:@"Login"]){
        UITextField *username = [alertView textFieldAtIndex:0];
        UITextField *password = [alertView textFieldAtIndex:1];
        
        NSLog(@"Username: %@\nPassword: %@", username.text, password.text);
    }
    else if ([buttonTitle isEqualToString:@"Copy File"]){
        NSLog(@"User pressed the Copy File button.");
    }
    
}
- (IBAction)askForConfirmation:(id)sender {
  NSString *message = @"Are you sure you want to open this link in Safari?";
  UIAlertView *alertView = [[UIAlertView alloc]
                            initWithTitle:@"Open Link"
                            message:message
                            delegate:self  //Try to change to delegate:nil to see what happen
                            cancelButtonTitle:[self noButtonTitle]
                            otherButtonTitles:[self yesButtonTitle], nil];
  [alertView show];
}
- (IBAction)plainTextInput:(id)sender {
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Credit Card Number" 
                                                        message:@"Please enter your credit card number:"
                                                       delegate:self
                                              cancelButtonTitle:@"Cancel"
                                              otherButtonTitles:@"Ok", nil];
                            
    [alertView setAlertViewStyle:UIAlertViewStylePlainTextInput];
                        
    /* Display a numerical keypad for this text field */
    UITextField *textField = [alertView textFieldAtIndex:0];
    
    textField.keyboardType = UIKeyboardTypeNumberPad; // Please check .h file to see other type
    
    
    /* Can we add more UITextField ? Try to commend and see what happen
    UITextField *textField2 = [alertView textFieldAtIndex:1];
    textField2.keyboardType = UIKeyboardTypeNumberPad; */
    [alertView show];

}
- (IBAction)secureTextInput:(id)sender {
    UIAlertView *alertView = [[UIAlertView alloc] 
                              initWithTitle:@"Password"
                              message:@"Please enter your password:"
                              delegate:self
                              cancelButtonTitle:@"Cancel"
                              otherButtonTitles:@"Ok", nil];
    
    [alertView setAlertViewStyle:UIAlertViewStyleSecureTextInput];
    [alertView show];

}
- (IBAction)loginPassword:(id)sender {
    UIAlertView *alertView = [[UIAlertView alloc] 
                              initWithTitle:@"Password"
                              message:@"Please enter your credentials:"
                              delegate:self
                              cancelButtonTitle:@"Cancel"
                              otherButtonTitles:@"Login", nil];
    
    [alertView setAlertViewStyle:UIAlertViewStyleLoginAndPasswordInput];
    [alertView show];
}

#pragma mark - Customized AlerView
- (IBAction)withAdditionButtons:(id)sender {
    UIAlertView *alert = [[UIAlertView alloc]
                          initWithTitle:@"UIAlertView"
                          message:@"What do you want with this file?"
                          delegate:self
                          cancelButtonTitle:@"Cancel"
                          otherButtonTitles:@"Copy File", @"Move File", nil];
	[alert show];
    
    /* See the method, put break point to see how click event of button "Copy File" is handled.
     (void) alertView:(UIAlertView *)alertView 
     clickedButtonAtIndex:(NSInteger)buttonIndex{
    */
}
- (IBAction)showAlertWithImage:(id)sender {
    UIAlertView *alertView = [[UIAlertView alloc] 
                              initWithTitle:@"Password"
                              message:@"Please enter your credentials:"
                              delegate:self
                              cancelButtonTitle:@"Cancel"
                              otherButtonTitles:@"Login", nil];
    
    [alertView setAlertViewStyle:UIAlertViewStyleLoginAndPasswordInput];
    UIImageView *iconView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"key.png"]];
    [alertView addSubview: iconView];
    //iconView.center = CGPointMake(10, 10);
    
    //Explain the below line of code. Comment it to see diffence
    iconView.frame = CGRectMake(10, 10, iconView.frame.size.width, iconView.frame.size.height);
    [alertView show];
}

- (IBAction)addBackgroundToAlerView:(id)sender {
    UIAlertView *alertView = [[UIAlertView alloc]
                              initWithTitle:@"Thông báo"
                              message:@"Sáng tạo không có giới hạn"
                              delegate:nil
                              cancelButtonTitle:@"Không đồng ý"
                              otherButtonTitles:@"Đồng ý", nil];
    UIImageView *backgroundImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"woodbackground"]];
    backgroundImageView.contentMode = UIViewContentModeScaleAspectFit;
    [alertView insertSubview:backgroundImageView     
                     atIndex:0];
    [alertView show];

}

- (IBAction)customizeButton:(id)sender {
    UIAlertView *alert = [[UIAlertView alloc]
                          initWithTitle:@"UIAlertView"
                          message:@"Em có yêu anh không?"
                          delegate:self
                          cancelButtonTitle:@"Cancel"
                          otherButtonTitles:@"Yeu", @"Khong Yeu", nil];
    
    for (id object in alert.subviews) {
        if ([object isKindOfClass:[UIButton class]]) {
            UIButton *button = (UIButton *) object;
            NSString *title = [button titleForState: UIControlStateNormal];
            if ([title isEqualToString: @"Yeu"]){
                [button setImage: [UIImage imageNamed:@"heart.png"] forState:UIControlStateNormal];
            } else if ([title isEqualToString: @"Khong Yeu"]){
                [button setImage: [UIImage imageNamed:@"heartbreak.png"] forState:UIControlStateNormal];
            }
        }
    }
    
	[alert show];

    
}

#pragma mark - ActionSheet

- (IBAction)simpleActionSheet:(id)sender {
    UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:@"Can you choose country before go next?"
                                                             delegate:self cancelButtonTitle:nil destructiveButtonTitle:@"OK" otherButtonTitles:nil];
	actionSheet.actionSheetStyle = UIActionSheetStyleDefault;
    UIImageView *iconView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"globe.png"]];
    [actionSheet addSubview: iconView];
    iconView.frame = CGRectMake(0, 8, iconView.frame.size.width, iconView.frame.size.height);
	[actionSheet showInView:self.view];	// show from our table view (pops up in the middle of the table)
}
- (IBAction)actionSheetOtherButtons:(id)sender {
    // open a dialog with two custom buttons
	UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:@"Please choose your action"
                                                             delegate:self cancelButtonTitle:nil destructiveButtonTitle:nil
                                                    otherButtonTitles:@"Back up file", @"Delete file", nil];
	actionSheet.actionSheetStyle = UIActionSheetStyleDefault;
	actionSheet.destructiveButtonIndex = 1;	// make the second button red (destructive)
	[actionSheet showInView:self.view]; // show from our table view (pops up in the middle of the table)

}

@end
