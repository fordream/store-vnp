@class Recipe;

@interface Ingredient : NSManagedObject {

}

@property (nonatomic, retain) NSString *name;
@property (nonatomic, retain) NSString *amount;
@property (nonatomic, retain) Recipe *recipe;
@property (nonatomic, retain) NSNumber *displayOrder;


@end



