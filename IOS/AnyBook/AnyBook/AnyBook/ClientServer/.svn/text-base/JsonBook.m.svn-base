//
//  JsonBook.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/12/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "JsonBook.h"
#import "ClientServer.h"

@implementation JsonBook

@synthesize book, downloadUrl, rightObject, serverKey;

- (BOOL) parseBook:(NSDictionary*) json wBookId:(int) book_id
{
    Book* _book = [[Book alloc] init];
    [_book setBookId:book_id];
    
    BOOL res = [[Book declareDBTable] parseFromJson:json wJsonBase:self toObject:_book];
    [self setBook:_book];
    return res;
}

+ (JsonBook*) getBook:(NSString*) user_msisdn book_id:(int) book_id device_profile:(NSString*) device_profile device_imei:(NSString*) device_imei
{
    NSString* url = [ClientServer getBookUrl:user_msisdn :book_id :device_profile :device_imei];
    JsonBook* js = [[JsonBook alloc] init];
    NSDictionary* json = [js requestSynchronousWithURLString:url withShowAlert:NO];
    if ([js isSuccess]) {
        NSDictionary* data = [js requireObject:json skey:@"data"];
        [js parseBook:data wBookId:book_id];
    }
    return js;
}

+ (JsonBook*) getDownloadLink:(NSString*) user_msisdn device_profile:(NSString*) device_profile device_imei:(NSString*) device_imei book_id:(int) book_id
{
    NSString* url = [ClientServer getDownloadUrl :user_msisdn :device_profile :device_imei :book_id];
    JsonBook* js = [[JsonBook alloc] init];
    NSDictionary* json = [js requestSynchronousWithURLString:url withShowAlert:NO];
    if ([js isSuccess]) {
        js.downloadUrl = [js requireString:json skey:@"filepath"];
        js.rightObject = [js requireString:json skey:@"rightobject"];
        js.serverKey = [js requireString:json skey:@"server_key"];
    }
    return js;
}

@end
