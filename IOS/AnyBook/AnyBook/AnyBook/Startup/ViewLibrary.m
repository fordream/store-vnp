//
//  ViewLibrary.m
//  AnyBook
//
//  Created by Vuong Truong on 4/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewLibrary.h"
#import "Book.h"

@implementation ViewLibrary

@synthesize tblCell;
- (id)init
{
    self = [super initWithNibName:@"ViewLibrary" bundle:Nil];
    
    if (self) {
        // Custom initialization
    }
    
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.

    self.title = @"Thư Viện";
    pickerView.hidden = YES;
    
    [super viewDidLoad];

	arrayColors = [[NSMutableArray alloc] init];
	[arrayColors addObject:@"Sách theo tiêu đè"];
	[arrayColors addObject:@"Sách theo tác giả"];
	[arrayColors addObject:@"Sách mới nhất"];
	[arrayColors addObject:@"Sách hay đọc"];
    
    // add data example
    arrayTable  = [[NSMutableArray alloc] init];
    for(int i = 0; i < 10; i ++){
        Book *bok = nil;
        bok = [[Book alloc]init];
        [arrayTable addObject:bok];
    }
 
    
    typeList = 0;
    
}

- (void)viewDidUnload
{
    [super viewDidUnload];

    // release
    arrayColors = nil;
}



- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

/**************************************************************************************
 *
 *     Custom Functions
 * 
 **************************************************************************************/
- (IBAction) onClick:(id)sender{
    if(sender == btnOrder){
        if(pickerView.hidden == NO){
            pickerView.hidden = YES; 
        }else{
            pickerView.hidden = NO;
        }
    }else if(sender == btnListType){
        // changle and store listType
        [self changleType];
        
    }else if(sender == btnAsyn){
    
    }else if(sender == btnOption){
        
    }
}

-(void)changleType{
    if(typeList == 0){
        typeList = 1;
        [btnListType setTitle:@"Dạng danh sách" forState:UIControlStateNormal];
    }else{
        typeList = 0;
        [btnListType setTitle:@"Dạng list" forState:UIControlStateNormal];
    }
    [tvData reloadData];
    // Update list danh sach
}

// for picker

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)thePickerView {
	
	return 1;
}

- (NSInteger)pickerView:(UIPickerView *)thePickerView numberOfRowsInComponent:(NSInteger)component {
	
	return [arrayColors count];
}

- (NSString *)pickerView:(UIPickerView *)thePickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
	
	return [arrayColors objectAtIndex:row];
}

- (void)pickerView:(UIPickerView *)thePickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
	pickerView.hidden = YES;
    [arrayColors objectAtIndex:row];
    [btnOrder setTitle:[arrayColors objectAtIndex:row] forState:UIControlStateNormal];
    
    // Update list danh sach
}


//===========table View
// Customize the number of rows in the table view.
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if(typeList == 0){
        return [arrayTable count];
    }else{
        if([arrayTable count]%4 == 0){
            return [arrayTable count]/4;
        }else{
            return [arrayTable count]/4 + 1;
        }
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if(typeList == 0){
        return 57.0f;
    }else{
        return 144.0f;
    }
}


// select
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    //	NextViewController *nextController = [[NextViewController alloc] initWithNibName:@"NextView" bundle:nil];
    //	[self.navigationController pushViewController:nextController animated:YES];
    //	[nextController changeProductText:[arryData objectAtIndex:indexPath.row]];
    
    NSLog(@"item");
}

// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if(typeList == 0){
        static NSString *CellIdentifier = @"Cell";
        
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
        if (cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier] ;
        }
        
        //UIImage *indicatorImage = [UIImage imageNamed:@"OK.png"];
        
        //cell.accessoryView =[[UIImageView alloc] initWithImage:indicatorImage];
        
        // Configure the cell.
        UIImage *cellImage = [UIImage imageNamed:@"OK.png"];
        
        
        
//        Book * bok = [arrayTable objectAtIndex: [indexPath row]];
//        
//                
//        NSString *subtitle = [NSString stringWithString: [bok title]];
//        subtitle = [subtitle stringByAppendingFormat:[bok book_id]];
//        
        
        //cell.textLabel.text = [bok book_id];
        //cell.detailTextLabel.text = subtitle;
        cell.imageView.image = cellImage;
        return cell;
    }else{
    	static NSString *MyIdentifier = @"MyIdentifier";
        MyIdentifier = @"UiTableViewCellLibraryGrid";
        
        UiTableViewCellLibraryGrid *cell =  [tableView dequeueReusableCellWithIdentifier:MyIdentifier];        
        if(cell == nil) {
            [[NSBundle mainBundle] loadNibNamed:@"UiTableViewCellLibraryGrid" owner:self options:nil];
            cell = tblCell;
            self.tblCell = nil;
        }
        
        if(cell != nil){
           // Book
            //[cell setBook:<#(Book *)#> book2:<#(Book *)#> book3:<#(Book *)#> book4:<#(Book *)#>];
        }
        
        return cell;
    }

}
/*
//1 anh, 1 title, 1 header
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier] ;
    }
    
    //UIImage *indicatorImage = [UIImage imageNamed:@"OK.png"];
    
    //cell.accessoryView =[[UIImageView alloc] initWithImage:indicatorImage];
    
 
    
    NSString *colorString = [arrayTable objectAtIndex: [indexPath row]];
    
    cell.textLabel.text = colorString;
    
 
    // Configure the cell.
    UIImage *cellImage = [UIImage imageNamed:@"OK.png"];
    cell.imageView.image = cellImage;
    NSString *subtitle = [NSString stringWithString: @"All about the color "];
    subtitle = [subtitle stringByAppendingFormat:colorString];
    
    cell.detailTextLabel.text = subtitle;
    
    return cell;
}
 */
// Customize the appearance of table view cells.
/*
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    
    cell.textLabel.text = [arrayColors objectAtIndex: [indexPath row]];
    
    return cell;
}
*/

/*
 // 1 anh , 1text
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    }
    // Configure the cell.
    UIImage *cellImage = [UIImage imageNamed:@"OK.png"];
    cell.imageView.image = cellImage;
    cell.textLabel.text = [arrayColors objectAtIndex: [indexPath row]];
    return cell;
}
 */







@end
