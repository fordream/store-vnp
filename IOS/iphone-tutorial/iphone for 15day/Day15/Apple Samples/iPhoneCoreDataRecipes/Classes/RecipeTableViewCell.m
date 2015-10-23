#import "RecipeTableViewCell.h"

#pragma mark -
#pragma mark SubviewFrames category

@interface RecipeTableViewCell (SubviewFrames)
- (CGRect)_imageViewFrame;
- (CGRect)_nameLabelFrame;
- (CGRect)_descriptionLabelFrame;
- (CGRect)_prepTimeLabelFrame;
@end


#pragma mark -
#pragma mark RecipeTableViewCell implementation

@implementation RecipeTableViewCell

@synthesize recipe, imageView, nameLabel, overviewLabel, prepTimeLabel;


#pragma mark -
#pragma mark Initialization

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {

	if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        imageView = [[UIImageView alloc] initWithFrame:CGRectZero];
		imageView.contentMode = UIViewContentModeScaleAspectFit;
        [self.contentView addSubview:imageView];

        overviewLabel = [[UILabel alloc] initWithFrame:CGRectZero];
        [overviewLabel setFont:[UIFont systemFontOfSize:12.0]];
        [overviewLabel setTextColor:[UIColor darkGrayColor]];
        [overviewLabel setHighlightedTextColor:[UIColor whiteColor]];
        [self.contentView addSubview:overviewLabel];

        prepTimeLabel = [[UILabel alloc] initWithFrame:CGRectZero];
        prepTimeLabel.textAlignment = UITextAlignmentRight;
        [prepTimeLabel setFont:[UIFont systemFontOfSize:12.0]];
        [prepTimeLabel setTextColor:[UIColor blackColor]];
        [prepTimeLabel setHighlightedTextColor:[UIColor whiteColor]];
		prepTimeLabel.minimumFontSize = 7.0;
		prepTimeLabel.lineBreakMode = UILineBreakModeTailTruncation;
        [self.contentView addSubview:prepTimeLabel];

        nameLabel = [[UILabel alloc] initWithFrame:CGRectZero];
        [nameLabel setFont:[UIFont boldSystemFontOfSize:14.0]];
        [nameLabel setTextColor:[UIColor blackColor]];
        [nameLabel setHighlightedTextColor:[UIColor whiteColor]];
        [self.contentView addSubview:nameLabel];
    }

    return self;
}


#pragma mark -
#pragma mark Laying out subviews

/*
 To save space, the prep time label disappears during editing.
 */
- (void)layoutSubviews {
    [super layoutSubviews];
	
    [imageView setFrame:[self _imageViewFrame]];
    [nameLabel setFrame:[self _nameLabelFrame]];
    [overviewLabel setFrame:[self _descriptionLabelFrame]];
    [prepTimeLabel setFrame:[self _prepTimeLabelFrame]];
    if (self.editing) {
        prepTimeLabel.alpha = 0.0;
    } else {
        prepTimeLabel.alpha = 1.0;
    }
}


#define IMAGE_SIZE          42.0
#define EDITING_INSET       10.0
#define TEXT_LEFT_MARGIN    8.0
#define TEXT_RIGHT_MARGIN   5.0
#define PREP_TIME_WIDTH     80.0

/*
 Return the frame of the various subviews -- these are dependent on the editing state of the cell.
 */
- (CGRect)_imageViewFrame {
    if (self.editing) {
        return CGRectMake(EDITING_INSET, 0.0, IMAGE_SIZE, IMAGE_SIZE);
    }
	else {
        return CGRectMake(0.0, 0.0, IMAGE_SIZE, IMAGE_SIZE);
    }
}

- (CGRect)_nameLabelFrame {
    if (self.editing) {
        return CGRectMake(IMAGE_SIZE + EDITING_INSET + TEXT_LEFT_MARGIN, 4.0, self.contentView.bounds.size.width - IMAGE_SIZE - EDITING_INSET - TEXT_LEFT_MARGIN, 16.0);
    }
	else {
        return CGRectMake(IMAGE_SIZE + TEXT_LEFT_MARGIN, 4.0, self.contentView.bounds.size.width - IMAGE_SIZE - TEXT_RIGHT_MARGIN * 2 - PREP_TIME_WIDTH, 16.0);
    }
}

- (CGRect)_descriptionLabelFrame {
    if (self.editing) {
        return CGRectMake(IMAGE_SIZE + EDITING_INSET + TEXT_LEFT_MARGIN, 22.0, self.contentView.bounds.size.width - IMAGE_SIZE - EDITING_INSET - TEXT_LEFT_MARGIN, 16.0);
    }
	else {
        return CGRectMake(IMAGE_SIZE + TEXT_LEFT_MARGIN, 22.0, self.contentView.bounds.size.width - IMAGE_SIZE - TEXT_LEFT_MARGIN, 16.0);
    }
}

- (CGRect)_prepTimeLabelFrame {
    CGRect contentViewBounds = self.contentView.bounds;
    return CGRectMake(contentViewBounds.size.width - PREP_TIME_WIDTH - TEXT_RIGHT_MARGIN, 4.0, PREP_TIME_WIDTH, 16.0);
}


#pragma mark -
#pragma mark Recipe set accessor

- (void)setRecipe:(Recipe *)newRecipe {
    if (newRecipe != recipe) {
        [recipe release];
        recipe = [newRecipe retain];
	}
	imageView.image = recipe.thumbnailImage;
	nameLabel.text = recipe.name;
	overviewLabel.text = recipe.overview;
	prepTimeLabel.text = recipe.prepTime;
}


#pragma mark -
#pragma mark Memory management

- (void)dealloc {
    [recipe release];
    [imageView release];
    [nameLabel release];
    [overviewLabel release];
    [prepTimeLabel release];
    [super dealloc];
}

@end
