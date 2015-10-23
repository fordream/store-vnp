//
//  ViewController.m
//  NguoiMauVietNam
//
//  Created by Lion User on 26/05/2012.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize myImageView;
@synthesize myScrollView;


- (NSArray *)imageData {
    
    // the filenames are stored in a plist in the app bundle, so create array by reading this plist
    NSString *path = [[NSBundle mainBundle] pathForResource:@"ImageData" ofType:@"plist"];
    NSData *plistData = [NSData dataWithContentsOfFile:path];
    NSString *error; NSPropertyListFormat format;
    NSArray *imageData = [NSPropertyListSerialization propertyListFromData:plistData
                                                          mutabilityOption:NSPropertyListImmutable
                                                                    format:&format
                                                          errorDescription:&error];
    if (!imageData) {
        NSLog(@"Failed to read image names. Error: %@", error);
           }
    
    return imageData;
}


- (UIScrollView *) newScrollImageViewWithImage:(UIImage *)paramImage
                                         frame:(CGRect)paramFrame{
    
    UIScrollView *subScrollView = [[UIScrollView alloc] initWithFrame:paramFrame];
    
    subScrollView.minimumZoomScale = 0.25f;
    subScrollView.maximumZoomScale = 4.0f;
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:self.view.bounds];
    imageView.contentMode = UIViewContentModeScaleAspectFit;
    imageView.image = paramImage;
    [subScrollView addSubview:imageView];
    subScrollView.delegate = self;
    return subScrollView;
    
}

- (UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView {
    
    UIImageView* zoomImage = [scrollView.subviews objectAtIndex:0];
    return zoomImage;
}



- (void)viewDidLoad
{
   [super viewDidLoad];   
   NSArray *imageArray = [[NSArray alloc]initWithArray:[self imageData]];
    
   self.view.backgroundColor = [UIColor whiteColor];
   CGRect scrollViewRect = self.view.bounds;
   self.myScrollView = [[UIScrollView alloc] initWithFrame:scrollViewRect];
   self.myScrollView.pagingEnabled = YES;
   self.myScrollView.contentSize = CGSizeMake(scrollViewRect.size.width * [imageArray count], scrollViewRect.size.height * 16.0f);
   [self.view addSubview:self.myScrollView];
   CGRect imageViewRect = self.view.bounds;
   int temp = imageViewRect.origin.y;
    
    for (NSArray *image in imageArray) {
        for (NSString *m in image) {
           NSString *k = [m stringByAppendingString:@".jpg" ];
            UIImage *i = [UIImage imageNamed:k];
            
            UIScrollView *nguoimau = [self newScrollImageViewWithImage:i
                                                                      frame:imageViewRect];
            [self.myScrollView addSubview:nguoimau];
            imageViewRect.origin.y += imageViewRect.size.height;
        }
        imageViewRect.origin.y = temp;
        imageViewRect.origin.x += imageViewRect.size.width;
        
       
    }
   
       
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

@end
