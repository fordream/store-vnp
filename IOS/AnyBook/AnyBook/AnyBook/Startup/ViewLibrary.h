//
//  ViewLibrary.h
//  AnyBook
//
//  Created by Vuong Truong on 4/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UiTableViewCellLibraryGrid.h"
@interface ViewLibrary : UIViewController<
        UIPickerViewDataSource, UIPickerViewDelegate,
        UITableViewDelegate, UITableViewDataSource> {
    int *typeList;
    IBOutlet UIButton *btnOrder;
    // btnOrder
    IBOutlet UIButton *btnListType;
    IBOutlet UIButton *btnAsyn;
    IBOutlet UIButton *btnOption;
    
    IBOutlet UITableView *tvData;
    
	IBOutlet UIPickerView *pickerView;
	NSMutableArray *arrayColors;
            
    NSMutableArray *arrayTable;
            
    IBOutlet UiTableViewCellLibraryGrid *tblCell;
    
}

@property (retain) UiTableViewCellLibraryGrid *tblCell;
- (IBAction) onClick:(id)sender;

-(void)changleType;
@end
