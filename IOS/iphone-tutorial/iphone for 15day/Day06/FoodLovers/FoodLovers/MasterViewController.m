//
//  MasterViewController.m
//  FoodLovers
//
//  Created by Techmaster on 6/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "MasterViewController.h"

@interface MasterViewController ()

@end
#define Cake 1
#define Cocktail 2
#define Soup 3
#define Sushi 4
#define QuickOrder 5

@implementation MasterViewController
@synthesize detailViewController;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"Food Lovers";
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    self.detailViewController = nil;    
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
- (IBAction)handleButtonTap:(id)sender {
    UIButton *button = (UIButton *) sender;
    //Kiểm tra xem thuộc tính detailViewController được khởi tạo chưa
    //nếu chưa thì khởi tạo
    if (!self.detailViewController)
    {
        self.detailViewController = [[DetailViewController alloc] initWithNibName:@"DetailViewController" bundle:nil];        
    }

    switch (button.tag) {
        case Cake:
            self.detailViewController.title = @"Bánh ngọt";
            break;
        case Cocktail:
            self.detailViewController.title = @"Cốc tai";
            break;
        case Soup:
            self.detailViewController.title = @"Súp";
            break;
        case Sushi:
            self.detailViewController.title = @"Sushi";
            break;
        case QuickOrder:
            self.detailViewController.title = @"Đặt hàng";
            break;
        default:
            break;
    }
    
    UIBarButtonItem *newBackButton = [[UIBarButtonItem alloc] initWithTitle: @"Quay về" style: UIBarButtonItemStyleBordered target: nil action: nil];
   /*UIBarButtonItem *newBackButton = [UIBarButtonItem alloc] initWithImage:<#(UIImage *)#> style:<#(UIBarButtonItemStyle)#> target:<#(id)#> action:<#(SEL)#> */
    self.navigationItem.backBarButtonItem = newBackButton;
        
    [self.navigationController pushViewController:self.detailViewController animated:YES];    
}

@end
