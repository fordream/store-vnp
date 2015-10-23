//
//  DBTable.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/10/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "DBTable.h"

#pragma mark - Private functions
@interface DBTable()

- (NSString*) private_getAllFieldNames;

@end


@implementation DBTable

@synthesize fieldNames, fieldTypes, fieldJsonTypes, primaryFieldIndex, tableName;

- (id) initWithTable:(NSString*) name
{
    self = [super init];
    if (self != nil) {
        [self setFieldNames:[[NSMutableArray alloc] init]];
        [self setFieldTypes:[[NSMutableArray alloc] init]];
        [self setFieldJsonTypes:[[NSMutableArray alloc] init]];
        [self setPrimaryFieldIndex:-1];
        cacheAllFieldNames = Nil;
        [self setTableName:name];
    }
    return self;
}

-(void)dealloc 
{
    [self setFieldNames:Nil];
    [self setFieldTypes:Nil];
    [self setFieldJsonTypes:Nil];
    [self setPrimaryFieldIndex:-1];
    cacheAllFieldNames = Nil;
}

#pragma mark - Table declaration

- (DBTable*) addField:(NSString*) name :(NSString*) type :(NSString*) jsontype
{
    [fieldNames addObject:name];
    [fieldTypes addObject:type];
    [fieldJsonTypes addObject:jsontype];
    return self;
}

- (DBTable*) addField:(NSString*) name :(NSString*) type
{
    return [self addField:name :type :type];
}

- (void) initObjectData:(NSMutableArray*) data
{
    [data removeAllObjects];
    for (int i = 0; i < [fieldNames count]; i++) {
        NSString* type = [fieldTypes objectAtIndex:i];
        if ([type isEqualToString:TYPE_TEXT]) {
            [data addObject:[NSString stringWithFormat:@""]];
        } else if ([type isEqualToString:TYPE_INTEGER]) {
            [data addObject:[NSNumber numberWithInt:0]];
        } else if ([type isEqualToString:TYPE_BOOLEAN]) {
            [data addObject:[NSNumber numberWithInt:SQL_BOOL_FALSE]];
        }
    }
}

- (int) searchForFieldIndex:(NSString*) name
{
    for (int i = 0; i < [fieldNames count]; i++) {
        if ([[fieldNames objectAtIndex:i] isEqualToString:name]) {
            return i;
        }
    }
    return -1;
}




#pragma mark - Parsing data from json

- (BOOL) parseFromJson:(NSDictionary*) json wJsonBase:(JsonBase*)jsonbase toObject:(DBFriendlyObject*) object
{
    for (int i = 0; i < [fieldTypes count]; i++) {
        NSString* name = [fieldNames objectAtIndex:i];
        NSString* type = [fieldTypes objectAtIndex:i];
        NSString* jsontype = [fieldJsonTypes objectAtIndex:i];
        
        //If json type is none - no json available
        if ([jsontype isEqualToString:@"none"]) {
            continue;
        }
        
        //Text type
        if ([type isEqualToString:TYPE_TEXT]) {
            
            if ([jsontype isEqualToString:TYPE_TEXT]) {
                [object setField:i wString:[jsonbase requireString:json skey:name]];
                
            } else if ([jsontype isEqualToString:TYPE_INTEGER]) {
                int number = [jsonbase requireNumber:json skey:name];
                [object setField:i wString:[NSString stringWithFormat:@"%d", number]];
                
            } else if ([jsontype isEqualToString:TYPE_BOOLEAN]) {
                BOOL value = [jsonbase requireBoolean:json skey:name];
                [object setField:i wString:[NSString stringWithFormat:@"%d", (value?SQL_BOOL_TRUE:SQL_BOOL_FALSE)]];
                
            } else if ([jsontype isEqualToString:TYPE_FLOAT]) {
                float number = [jsonbase requireFloat:json skey:name];
                [object setField:i wString:[NSString stringWithFormat:@"%f", number]];
                
            } else {
                DLog_Error(@"Fatal: class %@ declaration: field %@ type unconvertable (db: %@, json: %@)", 
                           tableName, name, type, jsontype);
            }
        
        //Integer type
        } else if ([type isEqualToString:TYPE_INTEGER]) {
            
            if ([jsontype isEqualToString:TYPE_TEXT]) {
                NSString* value = [jsonbase requireString:json skey:name];
                NSNumberFormatter * f = [[NSNumberFormatter alloc] init];
                [f setNumberStyle:NSNumberFormatterDecimalStyle];
                NSNumber * myNumber = [f numberFromString:value];
                if (value != Nil) {
                    [object setField:i wInt:[myNumber intValue]];
                } else {
                    [jsonbase setParseError]; //Error parsing json
                    [object setField:i wInt:0];
                }
                
            } else if ([jsontype isEqualToString:TYPE_INTEGER]) {
                int number = [jsonbase requireNumber:json skey:name];
                [object setField:i wInt:number];
                
            } else if ([jsontype isEqualToString:TYPE_BOOLEAN]) {
                BOOL value = [jsonbase requireBoolean:json skey:name];
                [object setField:i wInt:(value?SQL_BOOL_TRUE:SQL_BOOL_FALSE)];
                
            } else {
                //Can't convert float to int
                DLog_Error(@"Fatal: class %@ declaration: field %@ type unconvertable (db: %@, json: %@)", 
                           tableName, name, type, jsontype);
            }
            
        //Boolean type
        } else if ([type isEqualToString:TYPE_BOOLEAN]) {
            
            if ([jsontype isEqualToString:TYPE_INTEGER]) {
                int number = [jsonbase requireNumber:json skey:name];
                [object setField:i wBoolean:(number==SQL_BOOL_TRUE)];
                
            } else if ([jsontype isEqualToString:TYPE_BOOLEAN]) {
                BOOL value = [jsonbase requireBoolean:json skey:name];
                [object setField:i wBoolean:value];
                
            } else {
                DLog_Error(@"Fatal: class %@ declaration: field %@ type unconvertable (db: %@, json: %@)", 
                           tableName, name, type, jsontype);
            }
        }
        
        if (![jsonbase isSuccess]) {
            break;
        }
    }
    return [jsonbase isSuccess];
}





#pragma mark - Parsing data from database

- (BOOL) parse:(sqlite3_stmt*) stm wObject:(DBFriendlyObject*) object
{
    for (int i = 0; i < [fieldTypes count]; i++) {
        NSString* type = [fieldTypes objectAtIndex:i];
        if ([type isEqualToString:TYPE_TEXT]) {
            [object setField:i wString:[self getString:stm wIndex:i]];
        } else if ([type isEqualToString:TYPE_INTEGER]) {
            [object setField:i wInt:[self getNumber:stm wIndex:i]];
        } else if ([type isEqualToString:TYPE_BOOLEAN]) {
            [object setField:i wBoolean:[self getBoolean:stm wIndex:i]];
        }
    }
    return TRUE;
}

- (NSString*) getString:(sqlite3_stmt*) compiled wIndex:(int) index
{
    return [NSString stringWithUTF8String:(char *)sqlite3_column_text(compiled, index)];
}

- (int) getNumber:(sqlite3_stmt*) compiled wIndex:(int) index
{
    return sqlite3_column_int(compiled, index);
}

- (BOOL) getBoolean:(sqlite3_stmt*) compiled wIndex:(int) index
{
    return sqlite3_column_int(compiled, index) == SQL_BOOL_TRUE;
}





#pragma mark - Generating sql statement

- (NSString*) getSqlCreateTable
{
    NSMutableArray* sqlCreate = [[NSMutableArray alloc] init];
    for (int i = 0; i < [fieldNames count]; i++) {
        
        NSString* type = [fieldTypes objectAtIndex:i];
        if ([type isEqualToString:TYPE_TEXT]) {
        } else if ([type isEqualToString:TYPE_INTEGER]) {
        } else if ([type isEqualToString:TYPE_BOOLEAN]) {
            type = TYPE_INTEGER; //User integer for boolean
        }
        
        if (i == primaryFieldIndex) {
            [sqlCreate addObject:[NSString stringWithFormat:@"%@ %@ primary key",
                                  [fieldNames objectAtIndex:i],
                                  type]];
        } else {
            [sqlCreate addObject:[NSString stringWithFormat:@"%@ %@",
                                  [fieldNames objectAtIndex:i],
                                  type]];
        }
    }
    
    return [NSString stringWithFormat:
            @"CREATE TABLE %@(%@);",
            tableName,
            [sqlCreate componentsJoinedByString:@","]];
}

- (NSString*) getSqlSelectRowWithCondition:(NSString*) condition
{
    return [NSString stringWithFormat:
            @"SELECT %@ FROM %@ WHERE %@;",
            [self private_getAllFieldNames],
            tableName,
            condition];
}

- (NSString*) getSqlSelectAllRow
{
    return [NSString stringWithFormat:
            @"SELECT %@ FROM %@;",
            [self private_getAllFieldNames],
            tableName];    
}

- (NSString*) getSqlUpdateRow:(DBFriendlyObject*) object wCondition:(NSString*) condition
{
    NSMutableArray* list = [[NSMutableArray alloc] init];
    for (int i = 0; i < [fieldTypes count]; i++) {
        NSString* type = [fieldTypes objectAtIndex:i];
        if ([type isEqualToString:TYPE_TEXT]) {
            [list addObject:[NSString stringWithFormat:@"%@ = '%@'", 
                             [fieldNames objectAtIndex:i], 
                             [object getFieldAsString:i]]];
        } else if ([type isEqualToString:TYPE_INTEGER]) {
            [list addObject:[NSString stringWithFormat:@"%@ = %d", 
                             [fieldNames objectAtIndex:i], 
                             [object getFieldAsNumber:i]]];
        } else if ([type isEqualToString:TYPE_BOOLEAN]) {
            [list addObject:[NSString stringWithFormat:@"%@ = %d", 
                             [fieldNames objectAtIndex:i], 
                             [object getFieldAsNumber:i]]];
        }
    }
    
    return [NSString stringWithFormat:@"UPDATE %@ SET %@ WHERE %@", 
            tableName,
            [list componentsJoinedByString:@","], 
            condition];
}

- (NSString*) getSqlInsertRow:(DBFriendlyObject*) object
{
    NSMutableArray* list = [[NSMutableArray alloc] init];
    for (int i = 0; i < [fieldTypes count]; i++) {
        NSString* type = [fieldTypes objectAtIndex:i];
        if ([type isEqualToString:TYPE_TEXT]) {
            [list addObject:[NSString stringWithFormat:@"'%@'", 
                             [object getFieldAsString:i]]];
        } else if ([type isEqualToString:TYPE_INTEGER]) {
            [list addObject:[NSString stringWithFormat:@"%d", 
                             [object getFieldAsNumber:i]]];
        } else if ([type isEqualToString:TYPE_BOOLEAN]) {
            [list addObject:[NSString stringWithFormat:@"%d", 
                             [object getFieldAsNumber:i]]];
        }
    }
    
    return [NSString stringWithFormat:@"INSERT INTO %@(%@) VALUES(%@)",
            tableName,
            [fieldNames componentsJoinedByString:@","],
            [list componentsJoinedByString:@","]];
}



#pragma mark - Private functions

- (NSString*) private_getAllFieldNames
{
    if (cacheAllFieldNames == Nil) {
        cacheAllFieldNames = [fieldNames componentsJoinedByString:@","];
    }
    return cacheAllFieldNames;
}

@end
