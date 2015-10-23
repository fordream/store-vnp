//
//  BookReader.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/14/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "BookReader.h"
#import "Utility.h"


@interface BookReader()

- (void) checkDecodeChapter;
- (void) checkDecodeChapterImpl;

@end

@implementation BookReader

@synthesize webView;

- (id)init
{
    self = [super initWithNibName:@"BookReader" bundle:Nil];
    if (self) {
        currentBook = Nil;
        currentUserBook = Nil;
        dataStore = Nil;
    }
    return self;
}

- (id)initWithBook:(Book*) book
{
    self = [super initWithNibName:@"BookReader" bundle:Nil];
    if (self) {
        currentBook = book;
        currentUserBook = Nil;
        dataStore = Nil;
    }
    return self;
}

-(void)dealloc 
{
    currentBook = Nil;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [self loadBook:currentBook];
    
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


- (void) loadBook:(Book*) book
{
    currentBook = book;
    dataStore = [CachedDataStore getInstance];
    currentUserBook = [dataStore getUserBookById:[currentBook getBookId]];
    
    if (currentUserBook == Nil) {
        //User Book not found
        DLog_Error(@"Error: book %d does not exist in userbook", [currentBook getBookId]);
        return;
    }
    
    //Check if book is downloaded
    NSString* cached = [currentUserBook getCachedPath];
    if ([Utility isEmptyOrNull:cached]) {
        //Request download now
        DLog_Low(@"Info: book %d not downloaded yet !", [currentBook getBookId]);
        
        URLCacheConnection* ucc = [URLCacheConnection getInstance];
        [ucc addListener:self];
        [ucc downloadBook:currentUserBook];
        return;
    }
    
    //Book is already downloaded, check decoder now !!!
    [self checkDecodeChapter];
}

#pragma mark - URLCacheConnectionDelegate protocols

- (void) onDownloadDidFail:(DBUserBook *)book
{
    DLog_Error(@"Error: book %d download error", [book getBookId]);
    
    //Show error
}

- (void) onDownloadDidFinish:(DBUserBook *)book
{
    DLog_Low(@"Info: book %d downloaded", [book getBookId]);
    
    //Decode and show
    [self checkDecodeChapter];
}

- (void) onDownloadManagerStarted
{
    DLog_Low(@"Info: Download Manager started");
}

- (void) onDownloadManagerFinished
{
    DLog_Low(@"Info: Download Manager stopped");
}

#pragma mark - URLCacheConnectionDelegate protocols

- (void) checkDecodeChapter
{
    [self performSelectorInBackground:@selector(checkDecodeChapterImpl) withObject:Nil];
}

- (void) checkDecodeChapterImpl
{
    @autoreleasepool {
        
        //Do check 
        
    }
}


@end
