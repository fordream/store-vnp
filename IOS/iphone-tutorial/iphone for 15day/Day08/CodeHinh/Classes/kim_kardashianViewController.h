//
//  kim_kardashianViewController.h
//  kim_kardashian
//
//  Created by Jimit on 06/02/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class	ChapterViewController;
@class  RootViewController;

@interface kim_kardashianViewController : UIViewController {
	ChapterViewController *chapterViewController;
	RootViewController *galleryViewController; 

}

-(IBAction)openChapters;
-(IBAction)opengallery;

@property (nonatomic,retain) ChapterViewController *chapterViewController;
@property (nonatomic,retain) RootViewController *galleryViewController;

@end

