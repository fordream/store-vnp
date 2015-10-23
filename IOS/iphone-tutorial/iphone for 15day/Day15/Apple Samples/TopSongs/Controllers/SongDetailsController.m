#import "SongDetailsController.h"
#import "Song.h"
#import "Category.h"

@implementation SongDetailsController

@synthesize song;

- (NSDateFormatter *)dateFormatter {
    if (dateFormatter == nil) {
        dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateStyle:NSDateFormatterMediumStyle];
        [dateFormatter setTimeStyle:NSDateFormatterNoStyle];
    }
    return dateFormatter;
}

// When the view appears, update the title and table contents.
- (void)viewWillAppear:(BOOL)animated {
    self.title = song.title;
    [self.tableView reloadData];
}

- (NSInteger)tableView:(UITableView *)table numberOfRowsInSection:(NSInteger)section {
    return 4;
}

- (UITableViewCell *)tableView:(UITableView *)table cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *kCellIdentifier = @"SongDetailCell";
    UITableViewCell *cell = (UITableViewCell *)[self.tableView dequeueReusableCellWithIdentifier:kCellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue2 reuseIdentifier:kCellIdentifier] autorelease];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    switch (indexPath.row) {
        case 0: {
            cell.textLabel.text = NSLocalizedString(@"album", @"album label");
            cell.detailTextLabel.text = song.album;
        } break;
        case 1: {
            cell.textLabel.text = NSLocalizedString(@"artist", @"artist label");
            cell.detailTextLabel.text = song.artist;
        } break;
        case 2: {
            cell.textLabel.text = NSLocalizedString(@"category", @"category label");
            cell.detailTextLabel.text = song.category.name;
        } break;
        case 3: {
            cell.textLabel.text = NSLocalizedString(@"released", @"released label");
            cell.detailTextLabel.text = [self.dateFormatter stringFromDate:song.releaseDate];
        } break;
    }
    return cell;
}

- (NSString *)tableView:(UITableView *)table titleForHeaderInSection:(NSInteger)section {
    return NSLocalizedString(@"Song details", @"Song details label");
}

- (void)dealloc {
    [dateFormatter release];
    [song release];
    [super dealloc];
}

@end
