//
//  ViewController.m
//  DemoMemoryManagement
//
//  Created by cuong minh on 2/29/12.
//  Copyright (c) 2012 TechMaster.vn. All rights reserved.
//

#import "ViewController.h"
#import "Parent.h"
#import "Child.h"
#import "GoodParent.h"
#import "GoodChild.h"
#import "UIDevice+Orientation.h"  //Tên của baseclass+categoryname
#import "UIDevice+Fly.h"
#import "Boss.h"
#import "Employee.h"
#import "Friend.h"

@implementation ViewController

@synthesize primitiveNumber;
@synthesize myImage;
@synthesize myString;
@synthesize myConstantString;


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}

#pragma mark - Demo Memory Management
- (NSArray *) returnAnArray {
    NSArray *array = [[NSArray alloc] initWithObjects: @"Honda", @"Yamaha", 
                      @"Suzuki", @"Kawasaki", @"GM", @"Peugeot", @"Microsoft", 
                      @"Oracle", @"FaceBook", @"Apple", @"Twitter", nil];
    
    return [array autorelease]; //this code should be fine with autorelease         
    //return array;
}




/*
 Demo correct way to change a pointer from pointing to this object to other object
 */


#pragma mark - Button event handling
- (IBAction)MemoryLeakDemo:(id)sender {
    NSArray *array = [[NSArray alloc] initWithObjects: @"Honda", @"Yamaha", 
                      @"Suzuki", @"Kawasaki", @"GM", @"Peugeot", nil];
    NSLog(@"Array count: %d", [array count]);
    // Try to comment out following code
    [array release];
}

/*
 Run without the line  [anotherArrayPointer retain];
 Run again with the line [anotherArrayPointer retain];
 */

- (IBAction)RetainDemo:(id)sender {
    NSArray *resultArray = [self returnAnArray];
    NSLog(@"Ref Counter of resultArray %d", [resultArray retainCount]);
    
    NSArray *anotherArrayPointer = resultArray;
    [anotherArrayPointer retain];
    
    NSLog(@"Ref Counter of resultArray %d", [resultArray retainCount]);
    for (id anObject in resultArray) {
        NSLog(@"%@", anObject);
    }
    [resultArray release];
    
    NSLog(@"%@", anotherArrayPointer);
    
    NSLog(@"Ref Counter of anotherArrayPointer %d", [anotherArrayPointer retainCount]);
    
    // Add either of below lines and run Analyze to see warning
    // [anotherArrayPointer autorelease];
    // [anotherArrayPointer release];
    
    NSArray *moreArrayPointer = anotherArrayPointer;
    [moreArrayPointer retain];
    NSLog(@"Ref Counter of resultArray %d", [resultArray retainCount]);
    [moreArrayPointer release];

}

- (IBAction)PointToOtherOject:(id)sender {
    NSArray *resultArray =[[NSArray alloc] initWithObjects:@"Tom", @"Jim", @"Lee", nil] ;
    //NSArray *resultArray =[[[NSArray alloc] initWithObjects:@"Tom", @"Jim", @"Lee", nil] autorelease];
    /*
     if resultArray is assigned by autorelease object, don't release pointer points to that memory.
     if resultArray is assigned by not autorelease object, then must release this object by the end of function
     */
    
    NSLog(@"Ref Counter of resultArray %d", [resultArray retainCount]);
    
    NSArray *anotherArrayPointer = resultArray;
    [anotherArrayPointer retain];
    
    ///[resultArray release]; //before assign a pointer to new object in memory, developer must explicitly release this pointer from current object
    resultArray = [[NSArray alloc] initWithObjects:@"Dog", @"Bird", @"Horse", nil];
    NSLog(@"resultArray %@", resultArray);
    
    //[resultArray release];
    NSLog(@"anotherArrayPointer %@", anotherArrayPointer);
    [anotherArrayPointer release];  //Use when it points to non autorelease object otherwise Analyze will complain
    
}
- (IBAction)DisplayProperties:(id)sender {
    NSLog(@"myImage %@", self.myImage);
    NSLog(@"primitiveNumber %d", self.primitiveNumber);
    NSLog(@"myImage retain count %d",[self.myImage retainCount]);
}

- (IBAction)demoPropertywithCopyAttribute:(id)sender {
    
    NSMutableString *aString = [[NSMutableString alloc] initWithString:@"Gone with the wind"];
    self.myString = aString;
    NSLog(@"aString %p", aString);
    NSLog(@"myString %p", self.myString);
    
    
    [aString appendString: @" is a good film"];
    NSLog(@"aString %@", aString);
    NSLog(@"myString %@", self.myString);
    
    
    //copy attribute does not affect for immutable object. See the address cString and myConstantString points to are exactly same.
    NSString *cString = @"Life of hacker";
    self.myConstantString = cString;
    NSLog(@"cString %p", cString);
    NSLog(@"myConstantString %p", self.myConstantString);
    
    [aString release];
  
}
- (IBAction)cyclicReference:(id)sender {
    Parent *parent = [[Parent alloc] init];
    Child *child = [[Child alloc] init];
    parent.child = child;
    
    child.parent = parent;
    NSLog(@"Child retain count %d", [child retainCount]);
    NSLog(@"Parent retain count %d", [parent retainCount]);
    [parent release];
    [child release];
}
- (IBAction)antiCyclicReference:(id)sender {
    GoodParent *goodParent = [[GoodParent alloc] init];
    GoodChild *goodChild = [[GoodChild alloc] init];
    goodParent.goodChild = goodChild;
    goodChild.goodParent = goodParent;
    
    NSLog(@"Good child retain count %d", [goodChild retainCount]);
    NSLog(@"Good parent retain count %d", [goodParent retainCount]);
    
    [goodParent giveChildAToy];
   
    /*
     assign: retain count of image before and after are same
     strong: retain count of image after increased by 1
     */
    UIImage* image = [UIImage imageNamed:@"SteveJobs.png"];
    NSLog(@"Before Image retain count %d", [image retainCount]);
    goodChild.photo = image;
    NSLog(@"After Image retain count %d", [image retainCount]);
   
    [goodParent release];
    [goodChild release];
}


- (IBAction)demoCategory:(id)sender {
    
    UIDevice *device = [UIDevice currentDevice];
    NSLog(@"device is landspace mode %@", device.isLandscape ? @"Yes": @"No");
    [device fly];
    [device doMagicThing];
}
- (IBAction)demoProtocol:(id)sender {
        
    Boss *boss = [[Boss alloc] init];
    Employee *employee = [[Employee alloc] init];
    boss.delegate = employee;
    [boss onBusinessTrip];
   
    Friend *friend = [[Friend alloc] init];
    [employee release];    
    
    boss.delegate = friend;
    [boss onBusinessTrip];
    
   
    [friend release];
    [boss release];
}
- (IBAction)demoBlock:(id)sender {
    
    NSArray *words = [@"Gone with the wind is a great novel" componentsSeparatedByString:@" "];
    [words enumerateObjectsUsingBlock:
     ^(id obj, NSUInteger idx, BOOL *stop) {
         NSString *word = (NSString *) obj;
         NSLog(@"The length of '%@' is %d", word, word.length);
         
         if ([word compare: @"wind"] == NSOrderedSame){
             *stop = YES;
             NSLog(@"Found the 'wind' in this sentence at %d", idx);
         }
     }];
}

@end
