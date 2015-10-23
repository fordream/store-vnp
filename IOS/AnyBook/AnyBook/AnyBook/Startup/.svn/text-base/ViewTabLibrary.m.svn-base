//
//  ViewTabLibrary.m
//  AnyBook
//
//  Created by Vuong Truong on 4/11/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewTabLibrary.h"

@implementation ViewTabLibrary
@synthesize tblCell;
@synthesize tblSearchHeaderLibrary;
@synthesize tblHeaderSearchOnline;
@synthesize tblNoReult;
@synthesize tblItemList;
- (id)init
{
    self = [super initWithNibName:@"ViewTabLibrary" bundle:Nil];
    
    if (self) {
        // Custom initialization
    }
    
    //[Controller setStr:self];
    return self;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    viewHome.hidden = NO;
    viewLibarary.hidden = YES;
    viewMuaBan.hidden = YES;
    viewSearch.hidden = YES;
    viewSearchTouch.hidden = YES;
    viewPicker.hidden = YES;
    viewMenuLongPress.hidden = YES;
    viewLoading.hidden = YES;
    
    searchBar.hidden = YES;
    
    
    tabId = 0;
    typeTable = 0;
    typeOrder = 0;
    
    // add data example
    arrayTable  = [[NSMutableArray alloc] init];
    
    arrayColors = [[NSMutableArray alloc] init];
	[arrayColors addObject:@"Sách theo tiêu đề"];
	[arrayColors addObject:@"Sách theo tác giả"];
	[arrayColors addObject:@"Sách mới nhất"];
	[arrayColors addObject:@"Sách hay đọc"];
    
    
    //load data offline
    CachedDataStore* cds = [CachedDataStore getInstance];
    arrayBookOfline = [cds getCurrentUserBookShelf];
    
    if(arrayBookOfline == nil){
        arrayBookOfline = [[NSArray alloc]init];
    }
    
    // long press
    
    UILongPressGestureRecognizer *lpgr = [[UILongPressGestureRecognizer alloc] 
                                          initWithTarget:self action:@selector(handleLongPress:)];
    lpgr.minimumPressDuration = 2.0; //seconds
    //lpgr.delegate = self;
    [tableViewLibrary addGestureRecognizer:lpgr];
    
    arrayTableSearchOffline = [[NSMutableArray alloc] init];
    [tableViewSearch addGestureRecognizer:lpgr];
    
    arrayTableSearchOnline = [[NSMutableArray alloc ]init];
    
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

/////
///Custom
////

-(IBAction)onMenuChangle:(id)sender{
    
    if(sender == btnLibraryMenu){
        return;
    }
    viewHome.hidden = YES;
    viewLibarary.hidden = YES;
    viewMuaBan.hidden = YES;
    viewSearch.hidden = YES;
    
    if(sender == btnHomeMenu){
        [btnLibraryMenu setTitle:@"Trang chủ" forState:UIControlStateNormal];
        viewHome.hidden = NO;
        tabId = 0;
    }else if(sender == btnLibraryMenu){
        //[btnLibraryMenu setTitle:@"Thư viện" forState:UIControlStateNormal];
       // viewLibarary.hidden = NO;
        //tabId = 1;
        // reset list library
        //[self refreshLibrary];
    }else if(sender == btnSearch){
        [btnLibraryMenu setTitle:@"Tìm kiếm" forState:UIControlStateNormal];
        
        viewSearch.hidden = NO;
        searchBar.hidden = NO;
        viewSearchTouch.hidden = NO;
        
        [searchBar becomeFirstResponder];
        tabId = 3;
        
        [self refreshSearch];
        
    }else if(sender== btnMuaBan){
        [self showDialog];
        //[btnLibraryMenu setTitle:@"Mua bán" forState:UIControlStateNormal];
        
        //viewMuaBan.hidden = NO;
       /// tabId = 2;
        // reset list mua ban
    }
    
}

-(IBAction)onHomeClick:(id)sender{
    if(sender == btnHomeBook){
        // den sach moi doc
        [self showDialog];
    }else if(sender == btnHomeMuban){
         [self showDialog];

    }else if(sender == btnHomeList){
        [self showDialog];
        
    }else if(sender == btnHomeGrid){
       [btnLibraryMenu setTitle:@"Thư Viện" forState:UIControlStateNormal];
        
        viewHome.hidden = YES;
       viewLibarary.hidden = NO;
        tabId = 1;
        //typeTable = 2;
        
        // reset list library
        [self refreshLibrary];
    }else if(sender == btnHomeSetting){
        CachedDataStore* cds = [CachedDataStore getInstance];
        UserAccount *acount = [cds getCurrentUserAccount];
        [acount setUser_password:@""]; //Clear password
        [cds setCurrentUserAccount:acount];
        
        [self dismissModalViewControllerAnimated:false];

        // call setting
    }else if(sender == btnHomeHuongDan){
         [self showDialog];
        // call huong dan
    }else if(sender == btnHomeCaiDat){
//        [self showDialog];
        OptionController* vc = [[OptionController alloc] init];
        //[vc setController:self];
        
        self.modalPresentationStyle = UIModalPresentationPageSheet;
        [self presentModalViewController:vc animated:YES];
    }else if(sender == btnHomeTaiKhoan){
        
        //        [self showDialog];
        PersonalInformationController* vc = [[PersonalInformationController alloc] init];
        //[vc setController:self];
        
        self.modalPresentationStyle = UIModalPresentationPageSheet;
        [self presentModalViewController:vc animated:YES];
        //[self showDialog];
    }
}

// library click
-(IBAction)onLibraryClick:(id)sender{
    if(sender == btnLibraryMenuAsyn){
        // lien ket den mang
        //viewLoading.hidden = NO;
        [self synchronizeData];
        [self refreshLibrary];
       // viewLoading.hidden = YES;
        
    }else if(sender == btnLibraryMenuSetting){
        // call setting
    }else if(sender== btnLibraryMenuTypeList){
        if(typeTable == 0){
            typeTable = 1;
        }else{
            typeTable = 0;
        }
        
        [self refreshLibrary];
    }else if(sender == btnLibraryOption){
        viewPicker.hidden = NO;
        pickerView.hidden = NO;
    }else if(sender == btnLibraryChonBoSuTap){
        [self showDialog];
    }
}

-(void)synchronizeData{
    viewLoading.hidden = NO;
    
    [[CachedDataStore getInstance] doSynchonizeUserBookShelf];
    
    viewLoading.hidden = YES;
    
}

-(void)refreshLibrary{
    CachedDataStore* cds = [CachedDataStore getInstance];
    arrayBookOfline = [cds getCurrentUserBookShelf];
    
    arrayTable  = [[NSMutableArray alloc] init];
    
    [tableViewLibrary reloadData];
    
    
    if(typeTable == 0){
        //        UIImage *image = [[UIImage alloc] initWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:@"http://farm4.static.flickr.com/3092/2915896504_a88b69c9de.jpg"]]];
        
        UIImage *image = [UIImage imageNamed: @"bg_libary_tab_list.png"];
        [btnLibraryMenuTypeList setBackgroundImage:image forState:UIControlStateNormal];
        //[ imageViewLibarary setImage:image];
    }else{
        UIImage *image = [UIImage imageNamed: @"bg_library_tab_grid.png"];
        [btnLibraryMenuTypeList setBackgroundImage:image forState:UIControlStateNormal];
        //[imageViewLibarary setImage:image];
    }
}

-(void)refreshSearch{
    CachedDataStore* cds = [CachedDataStore getInstance];
    
    NSArray *data  = [cds getCurrentUserBookShelf];
    arrayTableSearchOffline = [[NSMutableArray alloc]init];
    NSString *txtSearch =  [searchBar text];
    
    for(int i = 0; i < [data count] ; i ++){
        Book *bok = [data objectAtIndex:i];
        NSString * bookId = [bok getBookTITLE];
        if([bookId rangeOfString:txtSearch].location != NSNotFound ){
            [arrayTableSearchOffline addObject:bok];
        }
    }
    
    [tableViewSearch reloadData]; 
}

//----------------Libarary
//===========table View
// Customize the number of rows in the table view.
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    if(tabId == 3){
        if(section ==0){
            return ([arrayTableSearchOffline count] ==0)? 1: [arrayTableSearchOffline count];
        }else{
            return ([arrayTableSearchOnline count] ==0)? 1: arrayTableSearchOnline.count;
        }
    }
    
    if(typeTable == 0){
        return [arrayBookOfline count];
    }else{
        return [arrayBookOfline count]/4 + (([arrayBookOfline count]%4 == 0)?0 : 1); 
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if(tabId == 3){
        if([indexPath section]==0 && [arrayTableSearchOffline count] == 0) return HEIGHT_LIST_LIBRARY;
        
        if([indexPath section]==1 && [arrayTableSearchOnline count] == 0) return HEIGHT_LIST_LIBRARY;
        
        return HEIGHT_LIST_LIBRARY;
    }
    
    return (typeTable == 0)? HEIGHT_LIST_LIBRARY : HEIGHT_GRID_LIBRARY;
}


// select
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
}

// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if(tabId == 3){
        
        if([indexPath section] == 0){
            
            if([arrayTableSearchOffline count] == 0){
                static NSString *MyIdentifier = @"MyIdentifier";
                MyIdentifier = @"NoReult";
                
                NoReult *cell =  [tableView dequeueReusableCellWithIdentifier:MyIdentifier];        
                if(cell == nil) {
                    [[NSBundle mainBundle] loadNibNamed:@"NoReult" owner:self options:nil];
                    cell = tblNoReult;
                    self.tblNoReult = nil;
                }
                
                return cell;
                
            }
            
            Book* book = [arrayTableSearchOffline objectAtIndex:([indexPath row])];
            return [self createCell:book tableVIew:tableView cellForRowAtIndexPath:indexPath];
        }else{
            if([arrayTableSearchOnline count] == 0){
                static NSString *MyIdentifier = @"MyIdentifier";
                MyIdentifier = @"NoReult";
                
                NoReult *cell =  [tableView dequeueReusableCellWithIdentifier:MyIdentifier];        
                if(cell == nil) {
                    [[NSBundle mainBundle] loadNibNamed:@"NoReult" owner:self options:nil];
                    cell = tblNoReult;
                    self.tblNoReult = nil;
                }
                
                return cell;
                
            }else{
            
                Book* book = [arrayTableSearchOnline objectAtIndex:([indexPath row])];
                return [self createCell:book tableVIew:tableView cellForRowAtIndexPath:indexPath];

            }
        }
        
    }
    
    if(typeTable == 0){
        
        Book* book = [arrayBookOfline objectAtIndex:([indexPath row])];
        return [self createCell:book tableVIew:tableView cellForRowAtIndexPath:indexPath];
        
    }else{
    	static NSString *MyIdentifier = @"MyIdentifier";
        MyIdentifier = @"UiTableViewCellLibraryGrid";
        
        UiTableViewCellLibraryGrid *cell =  [tableView dequeueReusableCellWithIdentifier:MyIdentifier];        
        if(cell == nil) {
            [[NSBundle mainBundle] loadNibNamed:@"UiTableViewCellLibraryGrid" owner:self options:nil];
            cell = tblCell;
            
            self.tblCell = nil;
        }
        
        [cell setController:self];
        
        if(cell != nil){
            Book *book1 = nil, *book2 = nil, *book3 = nil, *book4 = nil;
            
            book1 = [arrayBookOfline objectAtIndex:([indexPath row] * 4)];
            
            if(([indexPath row] * 4) + 1 < [arrayBookOfline count]){
                book2 = [arrayBookOfline objectAtIndex:([indexPath row] * 4 + 1)];
            }
            
            if(([indexPath row] * 4) + 2 < [arrayBookOfline count]){
                book3 = [arrayBookOfline objectAtIndex:([indexPath row] * 4 + 2)];
            }
            
            if(([indexPath row] * 4) + 3 < [arrayBookOfline count]){
                book4 = [arrayBookOfline objectAtIndex:([indexPath row] * 4 + 3)];
            }
            
            [cell setBook:book1 book2:book2 book3:book3 book4:book4];
        }
        
        return cell;
    }
    
}

-(UITableViewCell *)createCell:(Book *)book tableVIew : (UITableView *)tableVIew cellForRowAtIndexPath:(NSIndexPath *)indexPath{ 

    static NSString *MyIdentifier = @"MyIdentifier";
    MyIdentifier = @"ItemList";
    
    ItemList *cell =  [tableVIew dequeueReusableCellWithIdentifier:MyIdentifier];        
    if(cell == nil) {
        [[NSBundle mainBundle] loadNibNamed:@"ItemList" owner:self options:nil];
        cell = tblItemList;
        self.tblItemList = nil;
    }
    
    [cell setBook:book];
    [cell setController:self];
    
    return cell;   
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
	if(tabId == 3){
        NSArray *sectionArray =  [NSArray arrayWithObjects:@"Thư viện", @"Lưu trữ", nil] ;
        return [sectionArray count];
	}else{
        return 1;
    }
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
	if(tabId == 3){
        NSString *sectionHeader = nil;
        
        if(section == 0) {
            sectionHeader = @"Thư viện";
        }
        
        if(section == 1) {
            sectionHeader = @"Lưu trữ";
        }
        
        return sectionHeader;
    }else{
        return nil;
    }
}

// header for table View

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0,0,tableView.frame.size.width,20)] ;
    UILabel *headerLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, headerView.frame.size.width, 30)];
    if(section == 0){
        headerView = [[UIView alloc] initWithFrame:CGRectMake(0,0,tableView.frame.size.width,100.0f)] ;
        headerLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 70, headerView.frame.size.width, 30)];
        UIImageView *imageView =  [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, headerView.frame.size.width, 70)];
        UIImage *image = [UIImage imageNamed:@"search_result.png"];
        [imageView setImage:image];
        [headerView addSubview:imageView];
        
        UILabel *textSearch =  [[UILabel alloc] initWithFrame:CGRectMake(150, 25, 150, 20)];
        textSearch.backgroundColor = [UIColor clearColor];
        textSearch.text = searchBar.text;
        [headerView addSubview:textSearch];
    }
    
    headerLabel.textAlignment = UITextAlignmentLeft;
    
    if(section == 0){
        headerLabel.text = @"Thư viện";
    }else{
        headerLabel.text = @"Lưu trữ";
    }
    
    headerLabel.backgroundColor = [UIColor grayColor];
    
    [headerView addSubview:headerLabel];
    
    return headerView;
}

-(float)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return (tabId == 3 && section == 0) ? 100.0f :(tabId == 3 )? 30.0f: 0;
}


/// long press
-(void)handleLongPress:(UILongPressGestureRecognizer *)gestureRecognizer
{
    CGPoint p = [gestureRecognizer locationInView:tableViewLibrary];
    
    NSIndexPath *indexPath = [tableViewLibrary indexPathForRowAtPoint:p];
    
    if (indexPath != nil){
        viewMenuLongPress.hidden = NO;
    }
}

-(IBAction)onMenuLongPressPresentClick:(id)sender{
    viewMenuLongPress.hidden = YES;
    
    if(sender == btnMenuLongPressComment){
        // comment
    }else if(sender == btnMenuLongPressDetail){
        // detail
    }else if(sender == btnMenuLongPressPresent){
        // present for you
    }else if(sender == btnMenuLongPressRead){
        // read
    }
}


//----------------------- Picker view
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
    viewPicker.hidden = YES;    
    [arrayColors objectAtIndex:row];
    [btnLibraryOption setTitle:[arrayColors objectAtIndex:row] forState:UIControlStateNormal];
    
    typeOrder = row;
    
    [self refreshLibrary];
}

//for search bar
- (void)searchBarTextDidBeginEditing:(UISearchBar *)searchBar1

{
    
    
    
}
-(void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText{
    //search data offline
    
    [self refreshSearch];
    
}

-(void)searchBarSearchButtonClicked:(UISearchBar *)searchBar1{
    searchBar.hidden = YES;
    viewSearchTouch.hidden = YES;
    [searchBar resignFirstResponder];
    // search online
    
    
}


- (void)searchBarTextDidEndEditing:(UISearchBar *)searchBar1
{
   // searchBar.hidden = YES;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    searchBar.hidden = YES;
   viewSearchTouch.hidden = YES;
    [searchBar resignFirstResponder];
}

-(void)showDialog{
    [Utility showAlertOKWithTitle:@"Chức năng này chưa được hỗ trợ" message:nil];
}

-(void)read:(Book*)book{
}

-(void)gotoSearch{
    viewHome.hidden = YES;
    viewLibarary.hidden = YES;
    viewMuaBan.hidden = YES;
    viewSearch.hidden = YES;
    [btnLibraryMenu setTitle:@"Tìm kiếm" forState:UIControlStateNormal];
    
    viewSearch.hidden = NO;
    searchBar.hidden = NO;
    viewSearchTouch.hidden = NO;
}

@end
