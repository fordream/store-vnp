#import <Foundation/Foundation.h>

@interface Category : NSManagedObject

@property (nonatomic, retain) NSString *name;
@property (nonatomic, retain) NSSet *songs;

@end
