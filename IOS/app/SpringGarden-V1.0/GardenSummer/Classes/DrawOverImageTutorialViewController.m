    //
//  DrawOverImageTutorialViewController.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/6/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "DrawOverImageTutorialViewController.h"


@implementation DrawOverImageTutorialViewController
/*

// The designated initializer. Override to perform setup that is required before the view is loaded.

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
	
	if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
		
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
	
}

- (IBAction)choosePic {
	
	UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
	
	imagePicker.delegate = self;
	
	imagePicker.allowsImageEditing = NO;
	
	[self presentModalViewController:imagePicker animated:YES];
	
}

- (IBAction)clear {
	
	[self.view cancelDrawing];
	
}

- (IBAction)saveDrawing {
	
	UIGraphicsBeginImageContext(self.view.bounds.size);
	
	[self.view.layer renderInContext:UIGraphicsGetCurrentContext()];
	
	UIImage *finishedPic = UIGraphicsGetImageFromCurrentImageContext();
	
	UIGraphicsEndImageContext();
	
	UIImageWriteToSavedPhotosAlbum(finishedPic, self, @selector(exitProg:didFinishSavingWithError:contextInfo:), nil);
	
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingImage:(UIImage *)image editingInfo:(NSDictionary *)editingInfo {
	
	[self dismissModalViewControllerAnimated:YES];
	
	[picker release];
	
	[self.view drawPic:image];
	
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
	
	[self dismissModalViewControllerAnimated:YES];
	
	[picker release];
	
}

-(void)exitProg:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo {
	
	UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Success" message:@"Your picture has been saved" delegate:self cancelButtonTitle:nil otherButtonTitles:@"Ok", nil];
	
	[alertView show];
	
	[alertView release];
	
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
	
	[super dealloc];
	
}

@end
