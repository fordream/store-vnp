//
//  ViewController.m
//  WebViewDemo
//
//  Created by cuong minh on 4/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize webView;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [self setWebView:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}
- (IBAction)loadURL:(id)sender {
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL: [NSURL URLWithString:@"http://www.apple.com"]];
    [self.webView loadRequest:request];
}
- (IBAction)loadHTML:(id)sender {
    
    NSString *pathname = [[NSBundle mainBundle]  pathForResource:@"intro" ofType:@"html" inDirectory:@"/"];
	NSString *htmlString = [NSString stringWithContentsOfFile:pathname encoding:NSUTF8StringEncoding error:nil];
    
    NSString *path = [[NSBundle mainBundle] bundlePath];
    NSURL *baseURL = [NSURL fileURLWithPath:path];
    
    [self.webView loadHTMLString:htmlString baseURL:baseURL];
}
- (IBAction)loadPDF:(id)sender {
    NSString *pdfFile = [[NSBundle mainBundle]  pathForResource:@"April" ofType:@"pdf" inDirectory:@"/"];    
    NSData *pDFData = [NSData dataWithContentsOfFile:pdfFile];
    [self.webView loadData:pDFData MIMEType:@"application/pdf" textEncodingName:nil baseURL:nil];

}


@end
