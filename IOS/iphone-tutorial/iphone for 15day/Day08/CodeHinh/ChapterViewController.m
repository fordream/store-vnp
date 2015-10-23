#import "ChapterViewController.h"
#import "RootViewController.h"

@implementation ChapterViewController

@synthesize detailviewcontroller;
@synthesize chapters;
@synthesize listContent;
@synthesize outlineData;
@synthesize myTableView;
@synthesize galleryViewController;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
		// Custom initialization

		kItemTitleKey = @"itemTitle";		// dictionary key for obtaining the item's title to display in each cell
		kChildrenKey = @"itemChildren";	// dictionary key for obtaining the item's children

		NSString *path = [[NSBundle mainBundle] pathForResource:@"Outline" ofType:@"plist"];
		outlineData = [[NSDictionary dictionaryWithContentsOfFile:path] retain];
		
		NSArray *topLevel1Content = [outlineData objectForKey:kChildrenKey];
		
		// give the top view controller its content
		self.listContent = topLevel1Content;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
	[myTableView setBackgroundColor:[UIColor clearColor]];
	self.title=@"Kim Kardashian";
	
	[self.navigationController.navigationBar setTintColor:[UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:1.0]];

	UIBarButtonItem *addButton = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"gallery-icon.png"]
																  style:UIBarButtonItemStyleBordered target:self action:@selector(opengallery:)];
	self.navigationItem.rightBarButtonItem = addButton;
	[addButton release];
}


-(IBAction)opengallery:(id)sender{
	
	
	if(self.galleryViewController == nil) {
		//	DetailsViewController *dvc = [[DetailsViewController alloc] initWithNibName:@"DetailsViewController" bundle:[NSBundle mainBundle]];
		RootViewController *dvc	=	[[RootViewController alloc] init];
		self.galleryViewController	=	dvc;
		[dvc release];
	}
	
	
	[self.navigationController pushViewController:galleryViewController animated:YES];
}

- (void)viewWillAppear:(BOOL)animated
{
	[[self navigationController] setNavigationBarHidden:NO animated:NO];
	[myTableView reloadData];	// populate our table's data
	
	NSIndexPath *tableSelection = [myTableView indexPathForSelectedRow];
	[myTableView deselectRowAtIndexPath:tableSelection animated:NO];
	
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
	return [listContent count];
}



- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	static NSString *MyIdentifier = @"MyIdentifier";
	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:MyIdentifier];
	UIImageView *bgView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 286, 40)];
	UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 17, 17)];
	
	UIImage *bgImage, *imgImage;	
	
	if (cell == nil)
	{
		cell = [[[UITableViewCell alloc] initWithFrame:CGRectZero reuseIdentifier:MyIdentifier] autorelease];
		cell.textLabel.textColor= [UIColor colorWithRed:1.0 green:1.0 blue:1.0 alpha:1.0];
		
	}
	
	bgImage = [UIImage imageNamed:@"listing-bg.png"];
	imgImage = [UIImage imageNamed:@"bullet.png"];
	
	[bgView setImage:bgImage];
	[imgView setImage:imgImage];
	
	[cell setBackgroundView:bgView];

	NSDictionary *timeZoneDictionary = [listContent objectAtIndex:indexPath.row];
	cell.imageView.image	=	imgImage;
	cell.textLabel.text = [timeZoneDictionary objectForKey:kItemTitleKey];
	return cell;
	[bgImage release];
	[bgView release];
	[imgImage release];
	[imgView release];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	
	if(indexPath.row==7) {
		[[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://www.kimkardashian.celebuzz.com"]];

	} else if(indexPath.row==8) {
		if(self.galleryViewController == nil) {
			//	DetailsViewController *dvc = [[DetailsViewController alloc] initWithNibName:@"DetailsViewController" bundle:[NSBundle mainBundle]];
			RootViewController *dvc	=	[[RootViewController alloc] init];
			self.galleryViewController	=	dvc;
			[dvc release];
		}
		
		
		[self.navigationController pushViewController:galleryViewController animated:YES];
	} else {
		
		if(self.detailviewcontroller == nil) {
			DetailViewController *dvc = [[DetailViewController alloc] initWithNibName:@"DetailView" bundle:[NSBundle mainBundle]];
			self.detailviewcontroller = dvc;
			[dvc release];
		}

		NSDictionary *timeZoneDictionary = [listContent objectAtIndex:indexPath.row];
	
		// move to the 2nd level
		[[self navigationController] pushViewController:detailviewcontroller animated:YES];
		self.detailviewcontroller.title = [timeZoneDictionary objectForKey:kItemTitleKey];
		NSString *temp;
		temp = [timeZoneDictionary objectForKey:@"itemDesc"];
		temp = [temp stringByReplacingOccurrencesOfString:@"[NL]" withString:@"\n"];
		[self.detailviewcontroller.desview setText:temp];
	}
}


- (void)dealloc
{
	[listContent release];
	[myTableView release];
	[detailviewcontroller release];
	[super dealloc];
}

@end