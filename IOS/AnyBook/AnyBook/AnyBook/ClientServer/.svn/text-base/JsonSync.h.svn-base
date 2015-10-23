//
//  JsonSync.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/12/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "JsonBase.h"

@interface JsonSyncUserBookInfo : NSObject {
}

@property (nonatomic,readwrite) int book_id;
@property (nonatomic,retain) NSString* book_isbn;

+ (JsonSyncUserBookInfo*) parseJson:(NSDictionary*) json wJsonBase:(JsonBase*) jsonBase;

@end

@interface JsonSync : JsonBase {
}

@property (nonatomic,retain)    NSMutableArray* list_book_info;
@property (nonatomic,readwrite) int             list_count;

+ (JsonSync*) getUserBookShelfInfo:(NSString*) user_msisdn;

@end
