//
//  MyClass.h
//  DemoGetSet
//
//  Created by cuong minh on 2/18/12.

#import <Foundation/Foundation.h>
//MyClass.h file
@interface MyClass: NSObject
{
    
    NSString *text;
@private 
    double secretNumber;
@public 
    double publicNumber;
@protected 
    double protectNumber;     
}
-(id) init;
-(void) logText;
-(NSString*) text;
-(void) setText:(NSString *)textValue;
@end
