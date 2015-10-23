//
//  SettingsViewController.m
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/16/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "SettingsViewController.h"
#import "Setting.h"
#import "Custom1GroupCell.h"
#import "Custom2GroupCell.h"
#import "Custom3GroupCell.h"
#import "WifiViewController.h"

#define Sections 3

@interface SettingsViewController ()

@end

@implementation SettingsViewController
@synthesize settingData = _settingData;
@synthesize applicationData = _applicationData;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    NSString *settingDataPath = [[NSBundle mainBundle] pathForResource:@"settingData" ofType:@"plist"];
    NSString *appsDataPath = [[NSBundle mainBundle] pathForResource:@"appsData" ofType:@"plist"];
    
    NSArray *rawSettingData = [NSArray arrayWithContentsOfFile:settingDataPath];
    NSArray *rawAppsData = [NSArray arrayWithContentsOfFile:appsDataPath];
    
    _settingData = [[NSMutableArray alloc] initWithCapacity:[rawSettingData count]];
    _applicationData = [[NSMutableArray alloc] initWithCapacity:[rawAppsData count]];
    
    for (NSDictionary *object in rawSettingData) {
        Setting *tmp = [[Setting alloc] init:[object valueForKey:@"optionName"] mode:[[object valueForKey:@"mode"] integerValue] andPhoto:[object valueForKey:@"photo"]];
        [_settingData addObject:tmp];
    }
    
    for (NSDictionary *object in rawAppsData) {
        Setting *tmp = [[Setting alloc] init:[object valueForKey:@"optionName"] mode:[[object valueForKey:@"mode"] integerValue] andPhoto:[object valueForKey:@"photo"]];
        [_applicationData addObject:tmp];
    }
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    _settingData = nil;
    _applicationData = nil;
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return Sections;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    switch (section) {
        case 0:
        {
            NSInteger result = [_settingData count];
            // Return the number of rows in the section.
            return result;
            break;
        }
        case 1:
        {
            NSInteger result = [_applicationData count];
            // Return the number of rows in the section.
            return result;
            break;
        }
        default:
        {
            NSInteger result = 1;
            // Return the number of rows in the section.
            return result;
            break;
        }
    }
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *Custom1CellIdentifier = @"Custom1GroupCell";
    static NSString *Custom2CellIdentifier = @"Custom2GroupCell";
    static NSString *Custom3CellIdentifier = @"Custom3GroupCell";
    
    // Configure the cell...
                    
    switch (indexPath.section) {
        case 0:
        {
            Setting *objSetting = [_settingData objectAtIndex:indexPath.row];
            switch (objSetting.mode) {
                case 2:
                {
                    Custom2GroupCell *cell = [tableView dequeueReusableCellWithIdentifier:Custom2CellIdentifier];
                    cell.myImageView.image = objSetting.photo;
                    cell.myLabel.text = objSetting.optionName;
                    cell.mySwitch.on = FALSE;
                    return cell;
                    break;
                }
                case 3:
                {
                    Custom3GroupCell *cell = [tableView dequeueReusableCellWithIdentifier:Custom3CellIdentifier];
                    cell.myImageView.image = objSetting.photo;
                    cell.myLabelBold.text = objSetting.optionName;
                    cell.myLabelShow.text = @"Off";
                    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                    return  cell;
                    break;
                }
                default:
                {
                    Custom1GroupCell *cell = [tableView dequeueReusableCellWithIdentifier:Custom1CellIdentifier];
                    cell.myImageView.image = objSetting.photo;
                    cell.myLabel.text = objSetting.optionName;
                    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                    return cell;
                    break;
                }
            }
            break;
        }
        case 1:
        {
            Setting *objApps = [_applicationData objectAtIndex:indexPath.row];
            Custom1GroupCell *cell = [tableView dequeueReusableCellWithIdentifier:Custom1CellIdentifier];
            cell.myImageView.image = objApps.photo;
            cell.myLabel.text = objApps.optionName;
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            return cell;
            break;
        }
        default:
        {
            Custom1GroupCell *cell = [tableView dequeueReusableCellWithIdentifier:Custom1CellIdentifier];
            cell.myImageView.image = [UIImage imageNamed:@"cuong.jpg"];
            cell.myLabel.text = @"Olala";
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            return cell;
            break;
        }
    }
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
    WifiViewController *wifiViewController = [[WifiViewController alloc] initWithStyle:UITableViewStylePlain];
    [self.navigationController pushViewController:wifiViewController animated:YES];
}

@end
