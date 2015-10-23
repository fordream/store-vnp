@class Book, EditingViewController;

@interface DetailViewController : UITableViewController {
    Book *book;
	NSDateFormatter *dateFormatter;
	NSUndoManager *undoManager;
}

@property (nonatomic, retain) Book *book;
@property (nonatomic, retain) NSDateFormatter *dateFormatter;
@property (nonatomic, retain) NSUndoManager *undoManager;

- (void)setUpUndoManager;
- (void)cleanUpUndoManager;
- (void)updateRightBarButtonItemState;

@end

