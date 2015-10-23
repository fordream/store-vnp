//
//  CustomCellDelegate.h
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#ifndef NewDemoTableView_CustomCellDelegate_h
#define NewDemoTableView_CustomCellDelegate_h

#import <Foundation/Foundation.h>

@protocol CustomCellDelegate <NSObject>

@required
-(void) onLongPress;

@end

#endif
