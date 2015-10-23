//
//  ViewController.m
//  demoWebView
//
//  Created by Techmaster on 7/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//
#define DATO_SCHEME @"dato"
#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize myWebView;
@synthesize txtAddress;

#pragma mark - View Life Cycle
- (void)viewDidLoad
{
    [super viewDidLoad];
    [self goHome:nil];
    
    //Add custom menu item
    UIMenuController *menuController = [UIMenuController sharedMenuController];
    UIMenuItem *getHighlightString = [[UIMenuItem alloc] initWithTitle: @"Custom Menu" action: @selector(customMenu)];
    [menuController setMenuItems: [NSArray arrayWithObjects:getHighlightString, nil]];
	// Do any additional setup after loading the view, typically from a nib.
}
-(void)viewWillAppear:(BOOL)animated{
    
}
-(void)viewWillDisappear:(BOOL)animated{
    
}

- (void)viewDidUnload
{
    [self setMyWebView:nil];
    [self setTxtAddress:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

#pragma mark - Rotation Handling
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}
#pragma mark - Utilites
-(void)loadURLWithPath:(NSString *)inputString{
    NSURL *url = [NSURL URLWithString:inputString];
    //Check http scheme
    if ([url.absoluteString rangeOfString:@"http://"].location == NSNotFound) {
        url = [NSURL URLWithString:[NSString stringWithFormat:@"http://%@",url.absoluteString]];
    }
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    [self.myWebView loadRequest:request];
}
-(void)loadHTMLStringWithPath:(NSString *)htmlFilePath{
    //Get htmlString from file
    NSString *htmlString = [NSString stringWithContentsOfFile:htmlFilePath encoding:NSUTF8StringEncoding error:nil];
    //Create base URL for html string
    NSURL *baseURL = [NSURL fileURLWithPath:htmlFilePath];
    [self.myWebView loadHTMLString:htmlString baseURL:baseURL];
}
-(void)runJavascript:(NSString *)scriptText{
    NSString *result = [self.myWebView stringByEvaluatingJavaScriptFromString:scriptText];
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Javascript Result" message:result delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles: nil];
    [alert show];
}

-(void)openCustomSchemeWithURL:(NSURL *)url{
    if ([url.scheme isEqualToString:DATO_SCHEME]) {
        //Open objective c function from url
        NSString *functionName = url.host;
        NSLog(@"host %@", url.host);
        [self performSelector:NSSelectorFromString(functionName)];
    }	
}

-(void)showImageView{
    UIImageView *imgView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"logo.png"]];
    imgView.backgroundColor = [UIColor whiteColor];
    imgView.center = self.view.center;
    imgView.alpha = 0.0;
    [self.view addSubview:imgView];
    [UIView animateWithDuration:1.0 animations:^{
        imgView.alpha = 1.0;
    }];
    [NSTimer scheduledTimerWithTimeInterval:2.0 target:self selector:@selector(hideImageView) userInfo:nil repeats:NO];
}
-(void)hideImageView{
    UIImageView *imgView = self.view.subviews.lastObject;
    [UIView animateWithDuration:1.0 animations:^{
        imgView.alpha = 0.0;
    } completion:^(BOOL finished) {        
        [imgView removeFromSuperview];
    }];
}
-(void)customMenu{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Custom Menu" message:@"Run your custom code" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles: nil];
    [alert show];
}
#pragma mark - Interface Action Handling
- (IBAction)goHome:(id)sender {
    ((UITextField*)[self.txtAddress customView]).text = @"";
    if ([[self.txtAddress customView] isFirstResponder]) {
        [[self.txtAddress customView] resignFirstResponder];
    }
    
    NSString *homeFilePath = [[NSBundle mainBundle] pathForResource:@"home" ofType:@"html" ];
    [self loadHTMLStringWithPath:homeFilePath ];
}
- (IBAction)doLongpress:(id)sender {
    CGPoint touchPoint = [sender locationInView:self.myWebView];
    
    //Read javascript from JSTools file
    NSString *jsToolsFilePath = [[NSBundle mainBundle] pathForResource:@"JSTools" ofType:@"js"];
    NSString *jsToolscript = [NSString stringWithContentsOfFile:jsToolsFilePath encoding:NSASCIIStringEncoding error:nil];
    
    //Run webview javascript
    [self.myWebView stringByEvaluatingJavaScriptFromString:jsToolscript];
    NSString *tagNames = [self.myWebView stringByEvaluatingJavaScriptFromString:[NSString stringWithFormat:@"appGetElementsTagNameAtPoint(%f,%f)",touchPoint.x, touchPoint.y]];
    NSLog(@"%@",tagNames);
}

#pragma mark - Textfield Delegate
-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}
-(void)textFieldDidEndEditing:(UITextField *)textField{
    if (textField.text.length > 0) {
        NSRange rangOfJS = [textField.text rangeOfString:@"js:"];
        if (rangOfJS.location != NSNotFound) {
            [self runJavascript:[textField.text substringFromIndex:(rangOfJS.location+rangOfJS.length)]];
        }else {            
            [self loadURLWithPath:textField.text];
        }
    }
}
#pragma mark - Gesture Regconizer Delegate
-(BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer{
    return YES;
}
#pragma mark - Webview Delegate
-(BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType{
    NSURL *url = request.URL;
    if((![url.scheme isEqualToString:@"http"]) && (![url.scheme isEqualToString:@"file"])){
        [self openCustomSchemeWithURL:url];
        return NO;
    }
    return YES;
}
-(void)webViewDidFinishLoad:(UIWebView *)webView{
    NSLog(@"finish loading");
}
@end
