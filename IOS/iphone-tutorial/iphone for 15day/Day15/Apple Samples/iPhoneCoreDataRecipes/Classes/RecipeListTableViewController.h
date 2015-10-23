#import "RecipeAddViewController.h"

@class Recipe;
@class RecipeTableViewCell;

@interface RecipeListTableViewController : UITableViewController <RecipeAddDelegate, NSFetchedResultsControllerDelegate> {
    @private
        NSFetchedResultsController *fetchedResultsController;
        NSManagedObjectContext *managedObjectContext;
}

@property (nonatomic, retain) NSFetchedResultsController *fetchedResultsController;
@property (nonatomic, retain) NSManagedObjectContext *managedObjectContext;

- (void)showRecipe:(Recipe *)recipe animated:(BOOL)animated;
- (void)configureCell:(RecipeTableViewCell *)cell atIndexPath:(NSIndexPath *)indexPath;

@end
