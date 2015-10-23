- (void)viewDidLoad
{
    [super viewDidLoad];

    UIBarButtonItem *barButtontextURL = [[UIBarButtonItem alloc] initWithCustomView:self.textURL]; 
    UIBarButtonItem *barButtontextGoogle = [[UIBarButtonItem alloc] initWithCustomView:self.textGoogle]; 
    self.navigationItem.leftBarButtonItems = [[NSArray alloc ]initWithObjects: 
                                               barButtontextURL, barButtontextGoogle, nil];
    
    originalFrameOftextURL = self.textURL.frame;
    originalFrameOftextGoogle = self.textGoogle.frame;
    istextGoogleExpanded = NO;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    
    [textField resignFirstResponder];
    
    if ([textField isEqual: textURL]) {
        [self handletextURLReturn];
    } else if ([textField isEqual: textGoogle]) {
        [self handletextGoogleReturn];
    }
    //
    return NO;
}

- (void) handletextURLReturn 
{
    NSMutableString *uRLString = [[NSMutableString alloc] initWithString: self.textURL.text];
    NSRange range = [uRLString rangeOfString:@"http://" options:NSCaseInsensitiveSearch];
    if (range.length == 0) {
        [uRLString insertString:@"http://" atIndex:0];
    }
    [self browseTo:uRLString];
}

- (void) browseTo: (NSString *) uRLString
{
    NSURL *homeURL = [NSURL URLWithString:uRLString];    
	NSURLRequest *request = [[NSURLRequest alloc] initWithURL:homeURL];
    //Start loading the webview's request
	[webBrowser loadRequest:request];
    
}
- (void) handletextGoogleReturn
{
    NSMutableString *queryString = [[NSMutableString alloc] initWithString:self.textGoogle.text];
    [queryString replaceOccurrencesOfString:@" "
                                 withString:@"+"
                                    options:NSCaseInsensitiveSearch
                                      range: NSMakeRange(0, queryString.length)];                                
                                     
    NSMutableString *uRLString = [[NSMutableString alloc] initWithString: 
                                  @"http://www.google.com/search?client=safari&rls=en&ie=UTF-8&oe=UTF-8&q="];
    
    [uRLString appendString:queryString];
    [self toogletextGoogle];
    self.textURL.text = uRLString;
    [self browseTo:uRLString];    
}