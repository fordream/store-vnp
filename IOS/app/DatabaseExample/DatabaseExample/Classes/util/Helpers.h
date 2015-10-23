//
//  Helpers.h
//  DatabaseExample
//
//  Created by Truong Vuong on 10/14/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>

@interface Helpers : NSObject {

}

+(Helpers*) shareInstance;
+(AVAudioPlayer *)	soundBackground;
+(void)	setSoundBackground:(BOOL)sound1;
@end
