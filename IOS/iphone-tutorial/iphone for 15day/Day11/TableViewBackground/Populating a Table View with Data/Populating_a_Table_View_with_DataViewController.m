//
//  Populating_a_Table_View_with_DataViewController.m
//  Populating a Table View with Data
//
//  Created by Vandad Nahavandipoor on 11/07/2011.
//  Copyright 2011 Pixolity Ltd. All rights reserved.
//

#import "Populating_a_Table_View_with_DataViewController.h"

@implementation Populating_a_Table_View_with_DataViewController

@synthesize myTableView;
@synthesize floatingView;

- (void)didReceiveMemoryWarning{
  [super didReceiveMemoryWarning];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
  
  NSInteger result = 0;
  if ([tableView isEqual:self.myTableView]){
    result = 3;
  }
  return result;
  
}

- (NSInteger)tableView:(UITableView *)tableView 
 numberOfRowsInSection:(NSInteger)section{
  
  NSInteger result = 0;
  if ([tableView isEqual:self.myTableView]){
    switch (section){
      case 0:{
        result = 3;
        break;
      }
      case 1:{
        result = 5;
        break;
      }
      case 2:{
        result = 8;
        break;
      }
    }
  }
  return result;
  
}

- (UITableViewCell *)     tableView:(UITableView *)tableView 
              cellForRowAtIndexPath:(NSIndexPath *)indexPath{
  
  UITableViewCell *result = nil;
  
  if ([tableView isEqual:self.myTableView]){
    
    static NSString *TableViewCellIdentifier = @"MyCells";
    
    result = [tableView
              dequeueReusableCellWithIdentifier:TableViewCellIdentifier];
    
    if (result == nil){
      result = [[UITableViewCell alloc] 
                initWithStyle:UITableViewCellStyleDefault
                reuseIdentifier:TableViewCellIdentifier];
    }
    
    result.textLabel.text = [NSString stringWithFormat:@"Section %ld, Cell %ld",
                             (long)indexPath.section,
                             (long)indexPath.row];
    
  }
  
  return result;
  
}



- (void)viewDidLoad{
  [super viewDidLoad];
      
  self.myTableView =   [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
  
  self.myTableView.dataSource = self;
  
  /* Make sure our table view resizes correctly */
  self.myTableView.autoresizingMask = 
  UIViewAutoresizingFlexibleWidth |
  UIViewAutoresizingFlexibleHeight;

  UIImage *backgroundImage = [UIImage imageNamed:@"woodgrain.jpg"];
  self.myTableView.backgroundView = [[UIImageView alloc] initWithImage:backgroundImage];
    
  UIImage *floatingImage = [UIImage imageNamed:@"heart.png"];
  self.floatingView = [[UIImageView alloc] initWithImage:floatingImage];
  self.floatingView.frame = CGRectMake(120,0, 64, 64);
  
  //[self.myTableView addSubview: self.floatingView];  // Un REM this line to see different
    
  [self.view addSubview:self.myTableView];
  [self.view addSubview:self.floatingView];
  
}

- (void)viewDidUnload{
  [super viewDidUnload];
  self.myTableView = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation
        :(UIInterfaceOrientation)interfaceOrientation{
  return YES;
}

#pragma -
#pragma mark Handle scroll event

-(void) scrollViewDidScroll:(UIScrollView *)scrollView
{
  //  CGRect tableBounds = self.myTableView.bounds;
    
    
}
@end
