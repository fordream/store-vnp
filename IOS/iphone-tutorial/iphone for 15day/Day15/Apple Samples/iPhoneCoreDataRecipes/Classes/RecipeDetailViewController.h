@class Recipe;

@interface RecipeDetailViewController : UITableViewController <UINavigationControllerDelegate, UIImagePickerControllerDelegate, UITextFieldDelegate> {
    @private
        Recipe *recipe;
        NSMutableArray *ingredients;
        
        UIView *tableHeaderView;    
        UIButton *photoButton;
        UITextField *nameTextField;
        UITextField *overviewTextField;
        UITextField *prepTimeTextField;
}
            
@property (nonatomic, retain) Recipe *recipe;
@property (nonatomic, retain) NSMutableArray *ingredients;

@property (nonatomic, retain) IBOutlet UIView *tableHeaderView;
@property (nonatomic, retain) IBOutlet UIButton *photoButton;
@property (nonatomic, retain) IBOutlet UITextField *nameTextField;
@property (nonatomic, retain) IBOutlet UITextField *overviewTextField;
@property (nonatomic, retain) IBOutlet UITextField *prepTimeTextField;

- (IBAction)photoTapped;

@end
