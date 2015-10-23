#import "EditingTableViewCell.h"

@implementation EditingTableViewCell

@synthesize label, textField;

- (void)dealloc {
	[label release];
	[textField release];
	[super dealloc];
}

@end
