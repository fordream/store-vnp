
#import "Creating_Scrollable_Content_with_UIScrollViewViewController.h"

@implementation Creating_Scrollable_Content_with_UIScrollViewViewController

@synthesize myImageView;
@synthesize myScrollView;

- (void)didReceiveMemoryWarning
{
  [super didReceiveMemoryWarning];
  // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

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


///* 4 */
- (void)viewDidLoad{
  [super viewDidLoad];
  
  self.view.backgroundColor = [UIColor whiteColor];
  
  UIImage *iPhone = [UIImage imageNamed:@"iPhone.png"];       
  UIImage *iPad = [UIImage imageNamed:@"iPad.png"];
  UIImage *macBookAir = [UIImage imageNamed:@"MacBookAir.png"];
  
  CGRect scrollViewRect = self.view.bounds;
  
  self.myScrollView = [[UIScrollView alloc] initWithFrame:scrollViewRect];
  self.myScrollView.pagingEnabled = YES;
  self.myScrollView.contentSize = CGSizeMake(scrollViewRect.size.width * 3.0f,
                                             scrollViewRect.size.height);
  [self.view addSubview:self.myScrollView];

  CGRect imageViewRect = self.view.bounds;
    UIScrollView *iPhoneImageView = [self newScrollImageViewWithImage:iPhone
                                                       frame:imageViewRect];
  [self.myScrollView addSubview:iPhoneImageView];
  
  /* Go to next page by moving the x position of the next image view */
  imageViewRect.origin.x += imageViewRect.size.width;
  UIScrollView *iPadImageView = [self newScrollImageViewWithImage:iPad
                                                     frame:imageViewRect];
  [self.myScrollView addSubview:iPadImageView];
  
  /* Go to next page by moving the x position of the next image view */
  imageViewRect.origin.x += imageViewRect.size.width;
  UIScrollView *macBookAirImageView = 
    [self newScrollImageViewWithImage:macBookAir
                          frame:imageViewRect];
  [self.myScrollView addSubview:macBookAirImageView];
  
}

- (void)viewDidUnload
{
  [super viewDidUnload];
  // Release any retained subviews of the main view.
  // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
  [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
  [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
  [super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
  [super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
  // Return YES for supported orientations
  if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
  } else {
    return YES;
  }
}

@end
