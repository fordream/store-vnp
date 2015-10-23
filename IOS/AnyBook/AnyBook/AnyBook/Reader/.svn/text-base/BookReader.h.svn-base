//
//  BookReader.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/14/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Book.h"
#import "DBUserBook.h"
#import "CachedDataStore.h"
#import "URLCacheConnection.h"

@interface BookReader : UIViewController<URLCacheConnectionDelegate> {
    Book* currentBook;
    DBUserBook* currentUserBook;
    CachedDataStore* dataStore;
}

@property (nonatomic,retain) IBOutlet UIWebView* webView;

- (id)init;
- (id)initWithBook:(Book*) book;
- (void) loadBook:(Book*) book;

#pragma mark - URLCacheConnectionDelegate protocols

- (void) onDownloadDidFail:(DBUserBook *)book;
- (void) onDownloadDidFinish:(DBUserBook *)book;
- (void) onDownloadManagerStarted;
- (void) onDownloadManagerFinished;

@end
