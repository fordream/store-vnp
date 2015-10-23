//
//  SearchHeaderLibrary.h
//  AnyBook
//
//  Created by Vuong Truong on 4/12/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SearchHeaderLibrary : UITableViewCell{
    IBOutlet UILabel *txtSearchHeader;
}

-(void) setTextSearch:(NSString *)textSearch1;

@end
