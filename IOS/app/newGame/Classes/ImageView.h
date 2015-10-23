//
//  ImageView.h
//  newGame
//
//  Created by mac on 9/4/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface ImageView : NSObject {

}
+ (UIImage*)loadImage:(NSString*)imageName;
+ (void)releaseCache;

@end
