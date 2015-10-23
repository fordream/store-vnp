//
//  Coffee.h
//  SQL
//
//  Created by iPhone SDK Articles on 10/26/08.
//  Copyright 2008 www.iPhoneSDKArticles.com.
//

#import <UIKit/UIKit.h>
#import <sqlite3.h>

@interface Coffee : NSObject {

	NSInteger coffeeID;
	NSString *coffeeName;
	NSDecimalNumber *price;
	UIImage *coffeeImage; 
	
	//Intrnal variables to keep track of the state of the object.
	BOOL isDirty;
	BOOL isDetailViewHydrated;
}

@property (nonatomic, readonly) NSInteger coffeeID;
@property (nonatomic, copy) NSString *coffeeName;
@property (nonatomic, copy) NSDecimalNumber *price;
@property (nonatomic, retain) UIImage *coffeeImage;

@property (nonatomic, readwrite) BOOL isDirty;
@property (nonatomic, readwrite) BOOL isDetailViewHydrated;

//Static methods.
+ (void) getInitialDataToDisplay:(NSString *)dbPath;
+ (void) finalizeStatements;

//Instance methods.
- (id) initWithPrimaryKey:(NSInteger)pk;
- (void) deleteCoffee;
- (void) addCoffee;
- (void) hydrateDetailViewData;
- (void) saveAllData;

@end
