//
//  JsonSync.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/12/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "JsonSync.h"
#import "ClientServer.h"

@implementation JsonSyncUserBookInfo

@synthesize book_id, book_isbn;

+ (JsonSyncUserBookInfo*) parseJson:(NSDictionary*) json wJsonBase:(JsonBase*) jsonBase
{
    JsonSyncUserBookInfo* jsubi = [[JsonSyncUserBookInfo alloc] init];
    
    [jsubi setBook_id:[jsonBase requireNumber:json skey:@"id"]];
    [jsubi setBook_isbn:[jsonBase requireString:json skey:@"isbn"]]; //isBn not isDn
    
    return jsubi;
}

@end

@implementation JsonSync

@synthesize list_book_info, list_count;

+ (JsonSync*) getUserBookShelfInfo:(NSString*) user_msisdn
{
    NSString* url = [ClientServer getUserBookShelfInfoUrl:user_msisdn];
    JsonSync* js = [[JsonSync alloc] init];
    NSDictionary* json = [js requestSynchronousWithURLString:url withShowAlert:NO];
    if ([js isSuccess]) {
        NSDictionary* data = [js requireObject:json skey:@"data"];
        
        //Count
        [js setList_count:[js requireNumber:data skey:@"count"]];
        
        //Parse book info list
        NSArray* json_list = [js requireArray:data skey:@"books"];
        NSMutableArray* obj_list = [[NSMutableArray alloc] init];
        for (int i = 0; i < [json_list count]; i++) {
            NSDictionary* json_book = (NSDictionary*) [json_list objectAtIndex:i];
            [obj_list addObject:[JsonSyncUserBookInfo parseJson:json_book wJsonBase:js]];
        }
        [js setList_book_info:obj_list];
    }
    return js;
}

@end
