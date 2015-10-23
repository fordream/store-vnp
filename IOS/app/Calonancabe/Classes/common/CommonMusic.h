//
//  CommonMusic.h
//  Calonancabe
//
//  Created by Truong Vuong on 10/1/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//


#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>

@interface CommonMusic : NSObject <AVAudioPlayerDelegate> {

}
+(AVAudioPlayer *)	soundBackground;
+(AVAudioPlayer *)	soundBang;
+(AVAudioPlayer *)	soundnormal;
@end
