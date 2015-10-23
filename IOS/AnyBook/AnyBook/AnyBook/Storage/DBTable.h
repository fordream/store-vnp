//
//  DBTable.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/10/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>
#import "DBFriendlyObject.h"
#import "JsonBase.h"

#define SQL_BOOL_TRUE 1
#define SQL_BOOL_FALSE 0

#define TYPE_TEXT       @"text"
#define TYPE_INTEGER    @"integer"
#define TYPE_FLOAT      @"float"
#define TYPE_BOOLEAN    @"boolean"

@interface DBTable : NSObject {

@private
    NSString* cacheAllFieldNames;
}

@property (nonatomic,retain) NSMutableArray*    fieldNames;
@property (nonatomic,retain) NSMutableArray*    fieldTypes;
@property (nonatomic,retain) NSMutableArray*    fieldJsonTypes;
@property (nonatomic,readwrite) int             primaryFieldIndex;
@property (nonatomic,retain) NSString*          tableName;

#pragma mark - Table declaration

- (id) initWithTable:(NSString*) name;
- (DBTable*) addField:(NSString*) name :(NSString*) type;
- (DBTable*) addField:(NSString*) name :(NSString*) type :(NSString*) jsontype;
- (void) initObjectData:(NSMutableArray*) data;
- (int) searchForFieldIndex:(NSString*) name;

#pragma mark - Parsing data from json

- (BOOL) parseFromJson:(NSDictionary*) json wJsonBase:(JsonBase*)jsonbase toObject:(DBFriendlyObject*) object;

#pragma mark - Parsing data from database

- (BOOL) parse:(sqlite3_stmt*) stm wObject:(DBFriendlyObject*) object;
- (NSString*) getString:(sqlite3_stmt*) compiled wIndex:(int) index;
- (int) getNumber:(sqlite3_stmt*) compiled wIndex:(int) index;
- (BOOL) getBoolean:(sqlite3_stmt*) compiled wIndex:(int) index;

#pragma mark - Generating sql statement

- (NSString*) getSqlCreateTable;
- (NSString*) getSqlSelectRowWithCondition:(NSString*) condition;
- (NSString*) getSqlSelectAllRow;
- (NSString*) getSqlUpdateRow:(DBFriendlyObject*) object wCondition:(NSString*) condition;
- (NSString*) getSqlInsertRow:(DBFriendlyObject*) object;
//- (NSString*) getSqlUpdateAllColumnExceptPrimaryWithCondition:(NSString*) condition;

@end
