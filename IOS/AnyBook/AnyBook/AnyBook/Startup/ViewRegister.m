//
//  ViewRegister.m
//  AnyBook
//
//  Created by Vuong Truong on 4/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewRegister.h"
#import "Utility.h"

@interface ViewRegister() {
    int keyBoardHeight;
}

- (void)registerForKeyboardNotifications;

@end

@implementation ViewRegister

- (id)init
{
    self = [super initWithNibName:@"ViewRegister" bundle:nil];
    
    if (self) {
        keyBoardHeight = 0;
    }
    
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    [tfAddress addTarget:self action:@selector(onRegister:) forControlEvents:UIControlEventEditingDidEndOnExit];
    
    //  next
    [tfFullName addTarget:self action:@selector(nextPressed:) forControlEvents:UIControlEventEditingDidEndOnExit];
    [tfPassword   addTarget:self action:@selector(nextPressed:) forControlEvents:UIControlEventEditingDidEndOnExit];
    [tfMobile addTarget:self action:@selector(nextPressed:) forControlEvents:UIControlEventEditingDidEndOnExit];
    [tfEmail addTarget:self action:@selector(nextPressed:) forControlEvents:UIControlEventEditingDidEndOnExit];
    [tfRePassword addTarget:self action:@selector(nextPressed:) forControlEvents:UIControlEventEditingDidEndOnExit];
    
    tfFullName.delegate = self;
    tfPassword.delegate = self;
    tfMobile.delegate = self;
    tfEmail.delegate = self;
    tfRePassword.delegate = self;
    tfAddress.delegate = self;
    
    UIView *fullNamePaddingView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, 20)];
    tfFullName.leftView = fullNamePaddingView;
    tfFullName.leftViewMode = UITextFieldViewModeAlways;

    UIView *passwordPaddingView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, 20)];
    tfPassword.leftView = passwordPaddingView;
    tfPassword.leftViewMode = UITextFieldViewModeAlways;

    UIView *mobilePaddingView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, 20)];
    tfMobile.leftView = mobilePaddingView;
    tfMobile.leftViewMode = UITextFieldViewModeAlways;

    UIView *emailPaddingView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, 20)];
    tfEmail.leftView = emailPaddingView;
    tfEmail.leftViewMode = UITextFieldViewModeAlways;

    UIView *rePasswordPaddingView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, 20)];
    tfRePassword.leftView = rePasswordPaddingView;
    tfRePassword.leftViewMode = UITextFieldViewModeAlways;

    UIView *addressPaddingView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, 20)];
    tfAddress.leftView = addressPaddingView;
    tfAddress.leftViewMode = UITextFieldViewModeAlways;
    
    [scrollView setScrollEnabled:YES];
    [scrollView setUserInteractionEnabled:YES];
    [self registerForKeyboardNotifications];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

/**************************************************************************************
 *
 *     Custom Functions
 * 
 **************************************************************************************/


- (BOOL)checkForms
{
    if (tfFullName.text.length == 0) {
        [Utility showAlertOKWithTitle:STRING_NULL_USERNAME message:@""];
        return NO;
    }
    
    if (tfAddress.text.length == 0) {
        [Utility showAlertOKWithTitle:STRING_NULL_ADRESS message:@""];
        return NO;
    }
    
    if (tfEmail.text.length == 0) {
        [Utility showAlertOKWithTitle:STRING_NULL_EMAIL message:@""];
        return NO;
    }
    
    if (tfMobile.text.length == 0) {
        [Utility showAlertOKWithTitle:STRING_NULL_MOBILE message:@""];
        return NO;
    }
    
    if (tfPassword.text.length == 0) {
        [Utility showAlertOKWithTitle:STRING_NULL_PASSWORD message:@""];
        return NO;
    }
    
    return YES;
}


- (IBAction)onRegister:(id)sender{
    [self hiddenKeyboard];
    
    if ((sender == btnBoqua) || (sender == btnHome)) {
        [self dismissModalViewControllerAnimated:YES];
        return;
    }
    
    if([self checkForms]){
        JsonLogin* jl = [JsonLogin register:tfFullName.text 
                         mobile:tfMobile.text
                         email:tfEmail.text
                         address:tfAddress.text
                         password:tfPassword.text];
        if (![jl isSuccess]) {
            [Utility showAlertOKWithTitle:STRING_ERROR message:jl.sMessage];
        } else {
            [Utility showAlertOKWithTitle:STRING_OK message:STRING_REGISTER_SUCCESS];
            [self dismissModalViewControllerAnimated:YES];
        }
    }
}

-(IBAction)backgroundTouched:(id)sender
{
    [self hiddenKeyboard];
}

- (void) hiddenKeyboard{
     [tfFullName resignFirstResponder];
     [tfAddress resignFirstResponder];
     [tfEmail resignFirstResponder];
     [tfMobile resignFirstResponder];
     [tfPassword resignFirstResponder];
     [tfRePassword resignFirstResponder];
    
}

-(IBAction)nextPressed:(id) sender{
    if(sender == tfAddress){
        [tfPassword becomeFirstResponder];
    }else if(sender == tfEmail){
        [tfAddress becomeFirstResponder];
    }else if(sender == tfFullName){
        [tfEmail becomeFirstResponder];
    }else if(sender == tfMobile){
        [tfPassword becomeFirstResponder];
    }else if(sender == tfPassword){
        [tfRePassword becomeFirstResponder];
    }else if(sender == tfRePassword){
        [tfFullName becomeFirstResponder];
    }
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [self hiddenKeyboard];
    [super touchesBegan:touches withEvent:event];
}


// Call this method somewhere in your view controller setup code.
- (void)registerForKeyboardNotifications
{
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWasShown:)
                                                 name:UIKeyboardDidShowNotification object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillBeHidden:)
                                                 name:UIKeyboardWillHideNotification object:nil];
}

- (void) ajustScrollBar
{
    CGRect viewFrame = scrollView.frame;
    viewFrame.size.height -= keyBoardHeight;
    
    int offsety = activeField.frame.origin.y - (viewFrame.size.height - activeField.frame.size.height) / 2;
    if (offsety < 0) {
        offsety = 0;
    }
    
    CGPoint offset = [scrollView contentOffset];
    offset.y = offsety;
    [scrollView setContentOffset:offset];
}

// Called when the UIKeyboardDidShowNotification is sent.
- (void)keyboardWasShown:(NSNotification*)aNotification
{
    NSDictionary* info = [aNotification userInfo];
    CGSize kbSize = [[info objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size;
    keyBoardHeight = kbSize.height;
    
    [self ajustScrollBar];
}

// Called when the UIKeyboardWillHideNotification is sent
- (void)keyboardWillBeHidden:(NSNotification*)aNotification
{
    keyBoardHeight = 0;
    [scrollView setContentOffset:CGPointMake(0.0, 0.0)];
}

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    activeField = textField;
    [self ajustScrollBar];
}

- (void)textFieldDidEndEditing:(UITextField *)textField
{
    activeField = nil;
}

@end
