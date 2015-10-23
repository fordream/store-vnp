//
//  kim_kardashianViewController.m
//  kim_kardashian
//
//  Created by Jimit on 06/02/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "kim_kardashianViewController.h"
#import "ChapterViewController.h"
#import "RootViewController.h"

@implementation kim_kardashianViewController


@synthesize chapterViewController;
@synthesize galleryViewController;

// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}


-(IBAction)opengallery{
	if(galleryViewController==nil){
		RootViewController *cvc = [[RootViewController alloc] initWithNibName:@"RootViewController" bundle:[NSBundle mainBundle]];
		self.galleryViewController = cvc;
		[cvc release];
	}
	[self.navigationController pushViewController:galleryViewController animated:YES];
}

/*
 // Implement loadView to create a view hierarchy programmatically, without using a nib.
 - (void)loadView {
 }
 */



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	self.title=@"Back";
}


- (void)viewWillAppear:(BOOL)animated{
	[[self navigationController] setNavigationBarHidden:YES animated:NO];
	//self.navigationController.navigationBar.hidden=TRUE;
}

-(IBAction)openChapters{
	if(chapterViewController==nil){
		ChapterViewController *cvc = [[ChapterViewController alloc] initWithNibName:@"Chapter" bundle:[NSBundle mainBundle]];
		self.chapterViewController = cvc;
		[cvc release];
	}
	[self.navigationController pushViewController:chapterViewController animated:YES];
}


// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


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
	[chapterViewController release];
    [super dealloc];
}

@end
