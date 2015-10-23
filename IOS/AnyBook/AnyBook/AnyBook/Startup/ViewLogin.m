//
//  ViewLogin.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/7/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "ViewLogin.h"
#import "JsonLogin.h"
#import "Utility.h"
#import "CachedDataStore.h"
#import "ViewLibrary.h"

@implementation ViewLogin

- (id)init
{
    self = [super initWithNibName:@"ViewLogin" bundle:Nil];
    if (self) {
        // Custom initialization
        dataStore = Nil;
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

#define KEY_LOGIN_REMEMBER @"view_login_remember"
#define VAL_LOGIN_REMEMBER @"YES"
#define VAL_LOGIN_DONT_REMEMBER @"NO"

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self.navigationController setNavigationBarHidden:YES];
    
    dataStore = [CachedDataStore getInstance];
    
    BookMark *bookMark = [[BookMark alloc]init];
    [bookMark setBookMarkId:2];
    [bookMark setBookId:1];
    [bookMark setBookMarkPage:1];
    [bookMark setBookMarkPhone:@"1111"];
    //[dataStore addBookMark:bookMark];
    //

   // NSLog(@"%d", [dataStore getBookMarkShelf].count );
   
    
    
    UIView *userNamePaddingView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, 20)];
    txtUsername.leftView = userNamePaddingView;
    txtUsername.leftViewMode = UITextFieldViewModeAlways;
    
    UIView *passwordPaddingView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 10, 20)];
    txtPassword.leftView = passwordPaddingView;
    txtPassword.leftViewMode = UITextFieldViewModeAlways;
    
    NSString* rem = [dataStore getStringConfig:KEY_LOGIN_REMEMBER];
    if ([Utility isEmptyOrNull:rem]) {
        [btnRememberPassword setSelected:YES];
    } else {
        [btnRememberPassword setSelected:[rem isEqualToString:VAL_LOGIN_REMEMBER]];
    }
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
    
    dataStore = Nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    NSString* username = [dataStore getCurrentUserAccount].user_mobile;
    if (![Utility isEmptyOrNull:username]) {
        txtUsername.text = username;
    }
    txtPassword.text = @"";
}

- (void)viewDidAppear:(BOOL)animated
{
	[super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    // Dismiss the keyboard when the view outside the text field is touched.
    [txtUsername resignFirstResponder];
    [txtPassword resignFirstResponder];
    [super touchesBegan:touches withEvent:event];
}

/**************************************************************************************
 *
 *     Text field delegate
 * 
 **************************************************************************************/

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return true;
}

/**************************************************************************************
 *
 *     Custom Functions
 * 
 **************************************************************************************/

- (BOOL)checkForms
{
    if (txtUsername.text.length == 0) {
        [Utility showAlertOKWithTitle:STRING_NULL_USERNAME message:@""];
        return NO;
    }
    
    if (txtPassword.text.length == 0) {
        [Utility showAlertOKWithTitle:STRING_NULL_PASSWORD message:@""];
        return NO;
    }
    
    return YES;
}

- (IBAction)onRegister:(id)sender
{
    ViewRegister* vc = [[ViewRegister alloc] init];
    self.modalPresentationStyle = UIModalPresentationPageSheet;

    [self presentModalViewController:vc animated:YES];
}

- (IBAction)onLogin:(id)sender
{    
    if (![self checkForms]) {
        return;
    }
    
    JsonLogin* jl = [JsonLogin loginWithUsername:txtUsername.text password:txtPassword.text];
    if (![jl isSuccess]) {
        [Utility showAlertOKWithTitle:jl.sMessage message:nil];
    } else {
        // save info
        BOOL rem = [btnRememberPassword isSelected];
        if (rem) {
            jl.account.user_password = txtPassword.text;
        }
        [dataStore setStringConfig:(rem?VAL_LOGIN_REMEMBER:VAL_LOGIN_DONT_REMEMBER) forKey:KEY_LOGIN_REMEMBER];
        
        [dataStore setCurrentUserAccount:jl.account];
        
        // open Library view
        ViewTabLibrary* vc = [[ViewTabLibrary alloc] init];
        self.modalPresentationStyle = UIModalPresentationPageSheet;
        [self presentModalViewController:vc animated:YES];
    }
}

- (IBAction)onRememberPassword:(id)sender
{
    [btnRememberPassword setSelected:![btnRememberPassword isSelected]];
}

@end
