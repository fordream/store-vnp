@protocol RecipeAddDelegate;
@class Recipe;

@interface RecipeAddViewController : UIViewController <UITextFieldDelegate> {
    @private
        Recipe *recipe;
        UITextField *nameTextField;
        id <RecipeAddDelegate> delegate;
}

@property(nonatomic, retain) Recipe *recipe;
@property(nonatomic, retain) IBOutlet UITextField *nameTextField;
@property(nonatomic, assign) id <RecipeAddDelegate> delegate;

- (void)save;
- (void)cancel;

@end


@protocol RecipeAddDelegate <NSObject>
// recipe == nil on cancel
- (void)recipeAddViewController:(RecipeAddViewController *)recipeAddViewController didAddRecipe:(Recipe *)recipe;

@end
