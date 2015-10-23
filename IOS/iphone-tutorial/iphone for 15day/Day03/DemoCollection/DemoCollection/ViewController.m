//
//  ViewController.m
//  DemoCollection
//
//  Created by cuong minh on 3/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
@interface FunObject : NSObject
@property (nonatomic, strong) NSString *funName;
@end

@implementation FunObject
@synthesize funName;
//Returns a string that represents the contents of the receiving class.
+ (NSString *)description
{
    return @"This is FunObject. Inside it, there is funName";
}
//Returns a string that describes the contents of the receiver
- (NSString *)description 
{
    NSString *descriptionString = [NSString stringWithFormat: @"Fun Object : %@", funName];
    return descriptionString;                                  
}

@end



@implementation ViewController
NSInteger alphabeticSort(id string1, id string2, void *reverse);

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
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}
#pragma mark - Handle Button Events
//1. 
- (IBAction)demoNSArray:(id)sender {
    NSNumber *piNumber = [[NSNumber alloc] initWithDouble: 3.14158];
    FunObject *funObject = [[FunObject alloc] init];
    funObject.funName = @"Pinokyo";
    NSArray *array1 = [[NSArray alloc] initWithObjects:@"Hello", 
                       piNumber, 
                       funObject, 
                       [NSMutableString stringWithString: @"Changable"], 
                       nil];
    
    for (id object in array1) {
        NSLog(@"Class description: %@ - Object description: %@\n", [object class], object );
    }
    
    NSArray *array2 = [[NSArray alloc] initWithArray:array1 copyItems:NO];
    if ([[array2 lastObject] isKindOfClass:[NSMutableString class]]){
        NSMutableString *aMutableString = [array2 lastObject];
        [aMutableString insertString:@"We are " atIndex:0];
        NSLog(@"Last object of array1 %@", [array1 lastObject] );
        NSLog(@"Last object of array2 %@", [array2 lastObject] );    
    }
    
    
}
- (IBAction)demoNSMutableArray:(id)sender {
    // Load the data.
    NSString *dataPath = [[NSBundle mainBundle] pathForResource:@"data" ofType:@"plist"];

    NSArray *array1 = [[NSArray alloc] initWithContentsOfFile: dataPath];
    NSLog(@"%@", array1);
    
    
    NSMutableArray *array2 = [[NSMutableArray alloc] initWithArray:array1 copyItems:YES];
    [array2 addObject:@"George W.Bush"];
    [array2 addObject:@"Washington"];
    NSLog(@"%@", array2);
    
    [array2 removeObjectsInRange:(NSMakeRange(1, 2))];
    NSLog(@"%@", array2);
    
}
- (IBAction)sortArray:(id)sender {
    //First create the array of dictionaries
    NSString *LAST = @"lastName";
    NSString *FIRST = @"firstName";
    
    NSMutableArray *array = [NSMutableArray array];
    NSArray *sortedArray;
    
    NSDictionary *dict;
    dict = [NSDictionary dictionaryWithObjectsAndKeys:
            @"Jo", FIRST, @"Smith", LAST, nil];
    [array addObject:dict];
    
    dict = [NSDictionary dictionaryWithObjectsAndKeys:
            @"Joe", FIRST, @"Smith", LAST, nil];
    [array addObject:dict];
    
    dict = [NSDictionary dictionaryWithObjectsAndKeys:
            @"Joe", FIRST, @"Smythe", LAST, nil];
    [array addObject:dict];
    
    dict = [NSDictionary dictionaryWithObjectsAndKeys:
            @"Joanne", FIRST, @"Smith", LAST, nil];
    [array addObject:dict];
    
    dict = [NSDictionary dictionaryWithObjectsAndKeys:
            @"Robert", FIRST, @"Jones", LAST, nil];
    [array addObject:dict];
    
    //Next we sort the contents of the array by last name then first name
    
    // The results are likely to be shown to a user
    // Note the use of the localizedCaseInsensitiveCompare: selector
    NSSortDescriptor *lastDescriptor =
    [[NSSortDescriptor alloc] initWithKey:LAST
                                 ascending:YES
                                  selector:@selector(localizedCaseInsensitiveCompare:)] ;
    NSSortDescriptor *firstDescriptor =
    [[NSSortDescriptor alloc] initWithKey:FIRST
                                 ascending:YES
                                  selector:@selector(localizedCaseInsensitiveCompare:)] ;
    
    
    NSArray *descriptors = [NSArray arrayWithObjects:lastDescriptor, firstDescriptor, nil];
    NSLog(@"Before sorting\n%@", array);
    sortedArray = [array sortedArrayUsingDescriptors:descriptors];
    NSLog(@"After sorting\n%@", sortedArray);
}

NSInteger alphabeticSort(id string1, id string2, void *reverse)
{
    if (*(BOOL *)reverse == YES) {
        return [string2 localizedCaseInsensitiveCompare:string1];
    }
    return [string1 localizedCaseInsensitiveCompare:string2];
}

- (IBAction)sortBySelector:(id)sender {
    NSMutableArray *anArray =
    [NSMutableArray arrayWithObjects:@"aa", @"ab", @"ac", @"ad", @"ae", @"af", @"ag",
     @"ah", @"ai", @"aj", @"ak", @"al", @"am", @"an", @"ao", @"ap", @"aq", @"ar", @"as", @"at",
     @"au", @"av", @"aw", @"ax", @"ay", @"az", @"ba", @"bb", @"bc", @"bd", @"bf", @"bg", @"bh",
     @"bi", @"bj", @"bk", @"bl", @"bm", @"bn", @"bo", @"bp", @"bq", @"br", @"bs", @"bt", @"bu",
     @"bv", @"bw", @"bx", @"by", @"bz", @"ca", @"cb", @"cc", @"cd", @"ce", @"cf", @"cg", @"ch",
     @"ci", @"cj", @"ck", @"cl", @"cm", @"cn", @"co", @"cp", @"cq", @"cr", @"cs", @"ct", @"cu",
     @"cv", @"cw", @"cx", @"cy", @"cz", nil];
    // note: anArray is sorted
    NSData *sortedArrayHint = [anArray sortedArrayHint];
    
    [anArray insertObject:@"be" atIndex:5];
    
    NSArray *sortedArray;
    
    // sort using a selector
    sortedArray =
    [anArray sortedArrayUsingSelector:@selector(localizedCaseInsensitiveCompare:)];
    
    // sort using a function
    BOOL reverseSort = NO;
    sortedArray =
    [anArray sortedArrayUsingFunction:alphabeticSort context:&reverseSort];
    
    // sort with a hint
    sortedArray =
    [anArray sortedArrayUsingFunction:alphabeticSort
                              context:&reverseSort
                                 hint:sortedArrayHint];
}
- (IBAction)filterArray:(id)sender {
    NSMutableArray *array =
    [NSMutableArray arrayWithObjects:@"Bill", @"Ben", @"Chris", @"Melissa", nil];
    
    NSPredicate *bPredicate =
    [NSPredicate predicateWithFormat:@"SELF beginswith[c] 'b'"];
    NSArray *beginWithB =
    [array filteredArrayUsingPredicate:bPredicate];
    // beginWithB contains { @"Bill", @"Ben" }.
    NSLog(@"Begin with B:\n%@", beginWithB);
    
    
    NSPredicate *sPredicate =
    [NSPredicate predicateWithFormat:@"SELF contains[c] 's'"];
    [array filterUsingPredicate:sPredicate];
    // array now contains { @"Chris", @"Melissa" }
     NSLog(@"Begin contains s:\n%@", array);
}

- (IBAction)demoNSSet:(id)sender {
    NSString *aString = @"XYZ";
    NSString *bString = @"ABC";
    NSSet *aSet = [[NSSet alloc] initWithObjects: @"ABC", @"DEF", aString, aString, aString, bString, nil];
    NSLog(@"%@", aSet);
    
    if ([aSet containsObject: bString]) {
        NSLog(@"Contain");
    } else {
        NSLog(@"Not contain");
    }
    //Anh dang go gi, cac em co thay tren man hinh khong?
    NSArray *array = [[NSArray alloc] initWithObjects:@"Bill", @"Clinton", @"hifi", @"Obama", nil];
}
- (IBAction)demoNSMutableSet:(id)sender {
    
    NSSet *aSet = [[NSSet alloc] initWithObjects: @"Tran", @"Duong", @"Le", @"Tom", nil];
    NSMutableSet *bSet = [[NSMutableSet alloc] initWithObjects: @"Tom", @"Jhon", @"Phillips",  @"Le", nil];
    //[bSet unionSet:aSet];
    [bSet intersectSet:aSet];
    NSLog(@"%@", bSet);
}
- (IBAction)demoNSCountedSet:(id)sender {
    NSString *aString = @"XYZ";
    NSString *bString = @"ABC";
    NSCountedSet *aSet = [[NSCountedSet alloc] initWithObjects: @"ABC", @"DEF", aString, aString, aString, bString, nil];
    for (id object in aSet) {
        NSLog(@"%@ - %d", object, [aSet countForObject:object]);
    }

}
- (IBAction)demoUsingBlocks:(id)sender {
    NSArray *anArray=[NSArray arrayWithObjects:@"Cường", @"Dũng", @"Trang",@"Phương", @"Đông", nil];
    NSString *string=@"Phương";
    
    [anArray enumerateObjectsUsingBlock:^(id obj, NSUInteger index, BOOL *stop){
        if([obj localizedCaseInsensitiveCompare:string] == NSOrderedSame){
            NSLog(@"Object Found: %@ at index: %i",obj, index);
            *stop=YES;
        }
    } ];
}

- (IBAction)demoNSDictionary:(id)sender {
    static NSString *keyLast = @"LastName";
    static NSString *keyFirst = @"FirstName";
    static NSString *keyDOB = @"DateOfBirth";
    
    NSDateFormatter *df = [[NSDateFormatter alloc] init];
    [df setDateFormat:@"yyyy-MM-dd hh:mm:ss a"];
    NSDate *myDate = [df dateFromString: @"1975-11-27 10:00:00 am"];
  
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] initWithObjectsAndKeys:@"Cuong", keyFirst, @"Trinh", keyLast,
                                 myDate, keyDOB, nil];
    
    NSLog(@"%@", [dict objectForKey:keyLast]);
    NSLog(@"%@", [dict objectForKey:keyDOB]); //Đổi về giờ GMT
}
- (IBAction)demoNSMutableDictionary:(id)sender {
}
- (IBAction)loadPropertyList:(id)sender {
}

@end
