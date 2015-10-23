//
//  DataSource.m
//  BackgroundChooser
//
//  Created by cuong minh on 3/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "DataSource.h"
#import "Photo.h"

@implementation DataSource
@synthesize photos;
@synthesize searchPhotos;

- (id) init {
    if (self = [super init]) {    
        self.photos = [NSMutableArray arrayWithObjects:
                       [[Photo alloc] initWithImage:@"samurai.png"
                                     andDescription:@"Kiếm thủ cô đơn tầm sư học đạo"],
                       
                       [[Photo alloc] initWithImage:@"hello.jpg"
                                     andDescription:@"Hello World"],
                       
                       [[Photo alloc] initWithImage:@"garden.jpg"
                                     andDescription:@"Khu vườn cổ tích"],
                       
                       [[Photo alloc] initWithImage:@"nongnoc.jpg"
                                     andDescription:@"Siêu nòng nọc"],
                       
                       [[Photo alloc] initWithImage:@"penguins.jpg"
                                     andDescription:@"Những người bạn của Linux Torvals"],
                       
                       [[Photo alloc] initWithImage:@"bottlespaceship.jpg"
                                     andDescription:@"Du thuyen trong trai thủy tinh"],
                       
                       [[Photo alloc] initWithImage:@"ikea.jpg"
                                     andDescription:@"Vô đề"],
                       
                       [[Photo alloc] initWithImage:@"lickme.jpg"
                                     andDescription:@"Cùng nhấm nháp"],
                       
                       [[Photo alloc] initWithImage:@"limegreen.jpg"
                                     andDescription:@"Cuộc sống tươi đẹp"],
                       
                       [[Photo alloc] initWithImage:@"lostgeneration.jpg"
                                     andDescription:@"Thế hệ mất mát"],
                       
                       [[Photo alloc] initWithImage:@"abc.png"
                                     andDescription:@"Alexander Rohde"],
                       
                       [[Photo alloc] initWithImage:@"rock.png"
                                     andDescription:@"Metalica"],
                       
                       [[Photo alloc] initWithImage:@"girl.png"
                                     andDescription:@"Người đàn bà xa lạ"],
                       
                       [[Photo alloc] initWithImage:@"library.jpg"
                                     andDescription:@"Amazon 200 năm trước đây"],
                       
                       [[Photo alloc] initWithImage:@"kiwi.jpg"
                                     andDescription:@"Kiwi"],
                       
                       [[Photo alloc] initWithImage:@"fight.jpg"
                                     andDescription:@"Chiến đấu"],

                       
                       nil];
        self.searchPhotos = [NSMutableArray arrayWithCapacity:[self.photos count]];
       }
    return self;
}
- (NSUInteger) count {
    return [self.photos count];
}
- (NSUInteger) searchCount {
    return [self.searchPhotos count];
}

- (UIImage *) imageAtIndex : (NSUInteger) index {
    return [[self.photos objectAtIndex:index] image];
}

- (NSString *) descriptionAtIndex : (NSUInteger) index {
    return [[self.photos objectAtIndex:index] description];
}

- (Photo *) photoAtIndex : (NSUInteger) index 
{
    return (Photo* )[self.photos objectAtIndex: index];
}

- (Photo *) searchPhotoAtIndex : (NSUInteger) index
{
    return (Photo* )[self.searchPhotos objectAtIndex: index];

}
- (void)filterContentForSearchText:(NSString *)searchText 
{
    
	[self.searchPhotos removeAllObjects]; // First clear the filtered array.
	
	for (Photo *photo in self.photos)
	{
        NSComparisonResult result = [photo.description compare:searchText options:(NSCaseInsensitiveSearch|NSDiacriticInsensitiveSearch) range:NSMakeRange(0, [searchText length])];
        if (result == NSOrderedSame)
		{
            [self.searchPhotos addObject:photo];
            NSLog(@"%@", photo.description);
        }
		
	}

}

- (void) removePhotoAtIndex: (NSUInteger) index
{
    [self.photos removeObjectAtIndex:index];
}

- (void)dealloc {
    [self setPhotos:nil];
    [self setSearchPhotos:nil];
}
@end
