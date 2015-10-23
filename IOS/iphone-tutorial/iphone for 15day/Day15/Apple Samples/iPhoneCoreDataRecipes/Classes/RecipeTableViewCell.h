#import "Recipe.h"

@interface RecipeTableViewCell : UITableViewCell {
    Recipe *recipe;
    
    UIImageView *imageView;
    UILabel *nameLabel;
    UILabel *overviewLabel;
    UILabel *prepTimeLabel;
}

@property (nonatomic, retain) Recipe *recipe;

@property (nonatomic, retain) UIImageView *imageView;
@property (nonatomic, retain) UILabel *nameLabel;
@property (nonatomic, retain) UILabel *overviewLabel;
@property (nonatomic, retain) UILabel *prepTimeLabel;

@end
