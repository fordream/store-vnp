//
//  JsonBook.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/12/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "JsonBase.h"
#import "Book.h"

@interface JsonBook : JsonBase {
    
}

@property (nonatomic, retain) Book* book;
@property (nonatomic, retain) NSString* downloadUrl;
@property (nonatomic, retain) NSString* rightObject;
@property (nonatomic, retain) NSString* serverKey;

- (BOOL) parseBook:(NSDictionary*) json wBookId:(int) book_id;

+ (JsonBook*) getBook:(NSString*) user_msisdn book_id:(int) book_id device_profile:(NSString*) device_profile device_imei:(NSString*) device_imei;
+ (JsonBook*) getDownloadLink:(NSString*) user_msisdn device_profile:(NSString*) device_profile device_imei:(NSString*) device_imei book_id:(int) book_id;

@end
