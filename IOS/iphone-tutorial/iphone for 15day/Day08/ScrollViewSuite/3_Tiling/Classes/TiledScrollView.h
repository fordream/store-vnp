
@class TapDetectingView;

@protocol TiledScrollViewDataSource;


@interface TiledScrollView : UIScrollView <UIScrollViewDelegate> {
    id <TiledScrollViewDataSource>  dataSource;
    CGSize                          tileSize;
    TapDetectingView                *tileContainerView;
    NSMutableSet                    *reusableTiles;    

    int                              resolution;
    int                              maximumResolution;
    int                              minimumResolution;
    
    // we use the following ivars to keep track of which rows and columns are visible
    int firstVisibleRow, firstVisibleColumn, lastVisibleRow, lastVisibleColumn;
}

@property (nonatomic, assign) id <TiledScrollViewDataSource> dataSource;
@property (nonatomic, assign) CGSize tileSize;
@property (nonatomic, readonly) TapDetectingView *tileContainerView;
@property (nonatomic, assign) int minimumResolution;
@property (nonatomic, assign) int maximumResolution;

- (UIView *)dequeueReusableTile;  // Used by the delegate to acquire an already allocated tile, in lieu of allocating a new one.
- (void)reloadData;
- (void)reloadDataWithNewContentSize:(CGSize)size;

@end


@protocol TiledScrollViewDataSource <NSObject>

- (UIView *)tiledScrollView:(TiledScrollView *)scrollView tileForRow:(int)row column:(int)column resolution:(int)resolution;

@end


