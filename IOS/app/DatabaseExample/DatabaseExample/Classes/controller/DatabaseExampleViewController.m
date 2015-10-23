//
//  DatabaseExampleViewController.m
//  DatabaseExample
//
//  Created by Truong Vuong on 10/13/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import "DatabaseExampleViewController.h"

@implementation DatabaseExampleViewController



/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	loadingIndicator = [[MBProgressHUD alloc] initWithView:self.view];
    [loadingIndicator setLabelText:@"Logging in..."];
    loadingIndicator.dimBackground = YES;
    [self.view addSubview:loadingIndicator];
	
	
	DBManager *db  = [[DBManager alloc]init];
	[db openDB];
	int max = [db getMaxlevel];
	
	NSLog(@" max level = %i   ",max);
}

-(IBAction)onClick:(id)sender{
	GetResponseXMLFromRequestString *request = [[GetResponseXMLFromRequestString alloc] init];
	request.subDelegate = self;
	request.soapMessage = [NSString stringWithFormat:@"\n"
						   "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:lox=\"http://LoxleyService/\">\n"
						   "<soapenv:Header/>\n"
						   "<soapenv:Body>\n"
						   "<lox:LoginWebUser>\n"
						   "<lox:UserName>%@</lox:UserName>\n"
						   "<lox:Password>%@</lox:Password>\n"
						   "</lox:LoginWebUser>\n"
						   "</soapenv:Body>\n"
						   "</soapenv:Envelope>\n"
						   , @"vuongvantruong@gmail.com", @"123456"
						   ];
	request.soapActionName = @"http://LoxleyService/LoginWebUser";
	[request getData];
	
	
	[loadingIndicator show:YES];

	
	
	//QuartController *testController = [[QuartController alloc]initWithNibName:@"QuartController" bundle:nil];
	//[self presentModalViewController:testController animated:YES];
}


#pragma mark - GetResponseXMLFromRequestStringDelegate
- (void)connection:(GetResponseXMLFromRequestString *)conn failedLoadResponseDataFromServer:(NSError *)err {
    [conn release];
}

- (void)connection:(GetResponseXMLFromRequestString *)conn finishLoadResponseDataFromServer:(NSData *)data {
    [loadingIndicator hide:YES];
    TBXML *tbxml = [TBXML tbxmlWithXMLData:data];
    TBXMLElement *root = tbxml.rootXMLElement;
    TBXMLElement *bodyElement = [TBXML childElementNamed:@"soap:Body" parentElement:root];
    if (bodyElement) {
        TBXMLElement *responseElement = [TBXML childElementNamed:@"LoginWebUserResponse" parentElement:bodyElement];
        TBXMLElement *resultElement = [TBXML childElementNamed:@"LoginWebUserResult" parentElement:responseElement];
        NSString *userInfoString = [TBXML textForElement:resultElement];
        NSLog(@"aaa %@",userInfoString);
		
		if (![userInfoString isEqualToString:@""] && [userInfoString rangeOfString:@","].length != 0) {
            NSArray *userInfo = [userInfoString componentsSeparatedByString:@","];
            //[GlobalFunction sharedGlobalData].userID = [userInfo objectAtIndex:0];
//            [GlobalFunction sharedGlobalData].userName = [userInfo objectAtIndex:1];
//            
            // remove text at textfield if checkbox is uncheck
           // if (!rememberCheckbox.selected) {
//                usernameTF.text = @"";
//                passwordTF.text = @"";
//            }
            
            // then, come into app
            
            //HomeViewController *homeController = [[HomeViewController alloc] initWithNibName:@"HomeViewController" bundle:nil];
//            [self.navigationController pushViewController:homeController animated:YES];
//            [homeController release];
        }
        else {
           // ALERT_ERROR_WITH_TEXT(@"Can not log in to system.");
        }
    }
//    
    [conn release];
}



/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
	 [loadingIndicator release];
    [super dealloc];
}

@end
