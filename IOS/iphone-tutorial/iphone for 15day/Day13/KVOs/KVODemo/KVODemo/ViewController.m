//
//  ViewController.m
//  KVODemo
//
//  Created by cuong minh on 4/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"



@implementation ViewController
@synthesize colorPicker;
@synthesize color;
@synthesize myLabel;
@synthesize funButton;
@synthesize colorArray;
@synthesize person;


- (void)viewDidLoad
{
    [super viewDidLoad];
	// 1. Create a data model inside ViewController
    colorArray = [[NSArray alloc] initWithObjects:
                  [UIColor blackColor],      // 0.0 white 
                  [UIColor darkGrayColor],   // 0.333 white 
                  [UIColor lightGrayColor],  // 0.667 white 
                  [UIColor whiteColor],      // 1.0 white 
                  [UIColor grayColor],       // 0.5 white 
                  [UIColor redColor],        // 1.0, 0.0, 0.0 RGB 
                  [UIColor greenColor],      // 0.0, 1.0, 0.0 RGB 
                  [UIColor blueColor],       // 0.0, 0.0, 1.0 RGB 
                  [UIColor cyanColor],       // 0.0, 1.0, 1.0 RGB 
                  [UIColor yellowColor],     // 1.0, 1.0, 0.0 RGB 
                  [UIColor magentaColor],    // 1.0, 0.0, 1.0 RGB 
                  [UIColor orangeColor],     // 1.0, 0.5, 0.0 RGB 
                  [UIColor purpleColor],     // 0.5, 0.0, 0.5 RGB 
                  [UIColor brownColor],      // 0.6, 0.4, 0.2 RGB 
                  nil];
    //2. Add Observer to monitor the change in color property
    [self addObserver:self
           forKeyPath:@"color"
              options:0
              context:nil];
    
    //3. Add Observer to Person
    self.person = [[Person alloc] init];
    self.myLabel.text = self.person.fullName;
    [self.person addObserver: self 
           forKeyPath:@"fullName" 
              options:NSKeyValueObservingOptionNew 
              context:nil];
    
    //http://cocoawithlove.com/2008/06/five-approaches-to-listening-observing.html
  

}

- (void)viewDidUnload
{
    [self setColorPicker:nil];
    [self setColorArray:nil];
    [self setFunButton:nil];
    [self setMyLabel:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

#pragma mark - UIPickerViewDataSource
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    NSInteger result = 0;
    if ([pickerView isEqual:self.colorPicker]){
        result = 1;
    }
    return result;
}
- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    NSInteger result = 0;
    if ([pickerView isEqual:self.colorPicker] && component==0){
        result = [colorArray count];
    }
    return result;
}

/*- (NSString *)pickerView:(UIPickerView *)pickerView 
             titleForRow:(NSInteger)row
            forComponent:(NSInteger)component{
    
    NSString *result = nil;
    if ([pickerView isEqual:self.colorPicker]){        
        result = [NSString stringWithFormat:@"Row %ld", (long)row + 1];        
    }
    return result;
    
}
*/

#pragma mark - UIPickerViewDelegate
- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    self.color = (UIColor *)[colorArray objectAtIndex:row];
}
- (UIView *)pickerView:(UIPickerView *)pickerView 
            viewForRow:(NSInteger)row 
          forComponent:(NSInteger)component 
           reusingView:(UIView *)view
{
    CGRect imageFrame = CGRectMake(0.0, 0.0, 30, 30);
    UIView *colorView = [[UIView alloc] initWithFrame:imageFrame] ;
    
    colorView.backgroundColor = (UIColor *)[colorArray objectAtIndex:row];
   
    return colorView;
}


- (void)observeValueForKeyPath:(NSString*)keyPath
                      ofObject:(id)object
                        change:(NSDictionary*)change
                       context:(void*)context
{
    if ([keyPath isEqualToString:@"color"]) {
        myLabel.textColor = self.color;
        funButton.backgroundColor = self.color;
    }    
    else if ([keyPath isEqualToString:@"fullName"]){
       // NSLog(@"%@", self.person.fullName);
        self.myLabel.text = self.person.fullName;
    } else {
        [super observeValueForKeyPath:keyPath
                             ofObject:object
                               change:change
                              context:context];
    }
}
#pragma mark - Handle Control Events
- (IBAction)demoRegisterDependentKeys:(id)sender {
    self.person.firstName = [Person randomName];
    self.person.lastName = [Person randomName];
}

@end
