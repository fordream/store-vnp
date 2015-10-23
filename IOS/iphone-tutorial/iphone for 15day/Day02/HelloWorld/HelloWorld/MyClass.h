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
+ (NSString*)companyName;  //Class method
-(void) logText;
-(NSString*) text;  //Getter function
-(void) setText:(NSString *)textValue;  //Setter function
@end
