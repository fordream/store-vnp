//
//  DataSource.h
//  BackgroundChooser
//
//  Created by cuong minh on 3/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class Photo;

@interface DataSource : NSObject

@property (nonatomic, strong) NSMutableArray *photos;
@property (nonatomic, strong) NSMutableArray *searchPhotos;

- (UIImage *) imageAtIndex : (NSUInteger) index;
- (NSString *) descriptionAtIndex : (NSUInteger) index;
- (Photo *) photoAtIndex : (NSUInteger) index;
- (Photo *) searchPhotoAtIndex : (NSUInteger) index;
- (NSUInteger) count;
- (NSUInteger) searchCount;
- (void)filterContentForSearchText:(NSString *)searchText;
- (void) removePhotoAtIndex: (NSUInteger) index;
@end
