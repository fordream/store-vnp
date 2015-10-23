//
//  ViewTabLibrary.h
//  AnyBook
//
//  Created by Vuong Truong on 4/11/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#define HEIGHT_GRID_LIBRARY 160
#define HEIGHT_LIST_LIBRARY 80


#import <UIKit/UIKit.h>
#import "UiTableViewCellLibraryGrid.h"
#import "CachedDataStore.h"
#import "JsonLogin.h"
#import "Utility.h"
#import "SearchHeaderLibrary.h"
#import "HeaderSearchOnline.h"
#import "NoReult.h"
#import "Headersearch.h"
#import "ItemList.h"
#import "OptionController.h"
#import "PersonalInformationController.h"



@interface ViewTabLibrary : UIViewController<
UIPickerViewDataSource, UIPickerViewDelegate,
UITableViewDelegate, UITableViewDataSource,UISearchBarDelegate,UITextFieldDelegate>{
    
    // Button Menu
    IBOutlet UIButton *btnHomeMenu;
    IBOutlet UIButton *btnLibraryMenu;
    IBOutlet UIButton *btnSearch;
    IBOutlet UIButton *btnMuaBan;
    
    
    
    IBOutlet UIView *viewHome;
    IBOutlet UIView *viewLibarary;
    IBOutlet UIView *viewSearch;
    IBOutlet UIView *viewSearchTouch;
    
    IBOutlet UIView *viewMuaBan;
    IBOutlet UIView *viewPicker;
    IBOutlet UIView *viewLoading;
    
    IBOutlet UIView *viewMenuLongPress;
    //long press
    IBOutlet UIButton *btnMenuLongPressRead;
    IBOutlet UIButton *btnMenuLongPressComment;
    IBOutlet UIButton *btnMenuLongPressDetail;
    IBOutlet UIButton *btnMenuLongPressPresent;
    
    // config
    int tabId;
    int typeTable;
    int typeOrder;

    // button menu
    IBOutlet UIButton *btnHomeList;
    IBOutlet UIButton *btnHomeGrid;
    IBOutlet UIButton *btnHomeMuban;
    IBOutlet UIButton *btnHomeBook;
    IBOutlet UIButton *btnHomeHuongDan;
    IBOutlet UIButton *btnHomeSetting;
    
    IBOutlet UIButton *btnHomeCaiDat;
    IBOutlet UIButton *btnHomeTaiKhoan;
    
    
    
    // view library
    IBOutlet UITableView * tableViewLibrary;
    NSMutableArray *arrayTable;
    NSArray *arrayBookOfline;
    
    
    IBOutlet UiTableViewCellLibraryGrid *tblCell;
    IBOutlet UIImageView *imageViewLibarary;
    
    IBOutlet UIButton *btnLibraryMenuTypeList;
    IBOutlet UIButton *btnLibraryMenuAsyn;
    IBOutlet UIButton *btnLibraryMenuSetting;
    
    IBOutlet UIButton *btnLibraryOption;
    IBOutlet UIButton *btnLibraryChonBoSuTap ;
    
    //Picker
    IBOutlet UIPickerView *pickerView;
	NSMutableArray *arrayColors;
    
    //search
    IBOutlet UISearchBar *searchBar;
    IBOutlet UITableView *tableViewSearch;
    NSMutableArray *arrayTableSearchOffline;
    NSMutableArray *arrayTableSearchOnline;
    
    
    IBOutlet SearchHeaderLibrary *tblSearchHeaderLibrary;
    
    IBOutlet HeaderSearchOnline *tblHeaderSearchOnline;
    
    IBOutlet NoReult *tblNoReult;
    
    IBOutlet ItemList *tblItemList; 
}

@property (retain) UiTableViewCellLibraryGrid *tblCell;
@property (retain) SearchHeaderLibrary *tblSearchHeaderLibrary;
@property (retain) HeaderSearchOnline *tblHeaderSearchOnline;
@property (retain) NoReult *tblNoReult;

@property (retain) ItemList *tblItemList;




-(IBAction)onMenuChangle:(id)sender;

-(IBAction)onHomeClick:(id)sender;

-(IBAction)onLibraryClick:(id)sender;

-(void)refreshLibrary;

-(void)refreshSearch;

-(void)synchronizeData;

-(IBAction)onMenuLongPressPresentClick:(id)sender;

-(UITableViewCell *)createCell:(Book *)book tableVIew : (UITableView *)tableVIew cellForRowAtIndexPath:(NSIndexPath *)indexPath;

-(void)showDialog;
-(void)read:(Book*)book;

-(void)gotoSearch;

@end
