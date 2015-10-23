@interface ImageToDataTransformer : NSValueTransformer {
}
@end


@interface Recipe : NSManagedObject {
}

@property (nonatomic, retain) NSString *instructions;
@property (nonatomic, retain) NSString *name;
@property (nonatomic, retain) NSString *overview;
@property (nonatomic, retain) NSString *prepTime;
@property (nonatomic, retain) NSSet *ingredients;
@property (nonatomic, retain) UIImage *thumbnailImage;

@property (nonatomic, retain) NSManagedObject *image;
@property (nonatomic, retain) NSManagedObject *type;

@end


@interface Recipe (CoreDataGeneratedAccessors)
- (void)addIngredientsObject:(NSManagedObject *)value;
- (void)removeIngredientsObject:(NSManagedObject *)value;
- (void)addIngredients:(NSSet *)value;
- (void)removeIngredients:(NSSet *)value;
@end
