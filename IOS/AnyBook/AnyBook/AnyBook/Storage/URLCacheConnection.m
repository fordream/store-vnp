/*
 
 File: URLCacheConnection.m
 Abstract: The NSURL connection class for the URLCache sample.
 
 Version: 1.1
 
 Disclaimer: IMPORTANT:  This Apple software is supplied to you by Apple Inc.
 ("Apple") in consideration of your agreement to the following terms, and your
 use, installation, modification or redistribution of this Apple software
 constitutes acceptance of these terms.  If you do not agree with these terms,
 please do not use, install, modify or redistribute this Apple software.
 
 In consideration of your agreement to abide by the following terms, and subject
 to these terms, Apple grants you a personal, non-exclusive license, under
 Apple's copyrights in this original Apple software (the "Apple Software"), to
 use, reproduce, modify and redistribute the Apple Software, with or without
 modifications, in source and/or binary forms; provided that if you redistribute
 the Apple Software in its entirety and without modifications, you must retain
 this notice and the following text and disclaimers in all such redistributions
 of the Apple Software.
 Neither the name, trademarks, service marks or logos of Apple Inc. may be used
 to endorse or promote products derived from the Apple Software without specific
 prior written permission from Apple.  Except as expressly stated in this notice,
 no other rights or licenses, express or implied, are granted by Apple herein,
 including but not limited to any patent rights that may be infringed by your
 derivative works or by other works in which the Apple Software may be
 incorporated.
 
 The Apple Software is provided by Apple on an "AS IS" basis.  APPLE MAKES NO
 WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 PURPOSE, REGARDING THE APPLE SOFTWARE OR ITS USE AND OPERATION ALONE OR IN
 COMBINATION WITH YOUR PRODUCTS.
 
 IN NO EVENT SHALL APPLE BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION, MODIFICATION AND/OR
 DISTRIBUTION OF THE APPLE SOFTWARE, HOWEVER CAUSED AND WHETHER UNDER THEORY OF
 CONTRACT, TORT (INCLUDING NEGLIGENCE), STRICT LIABILITY OR OTHERWISE, EVEN IF
 APPLE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 Copyright (C) 2008-2010 Apple Inc. All Rights Reserved.
 
 */

#import "URLCacheConnection.h"
#import "Utility.h"
#import "AppDelegate.h"
#import "JsonBook.h"
#import "CachedDataStore.h"
#import "ZipArchive.h"

@interface URLCacheConnection()

- (void) initCache;
- (void) checkStartDownloader;
- (BOOL) removeFile:(NSString*) filePath;
- (BOOL) unzip:(NSString*) filePath toFolder:(NSString*) folderPath;
- (void) downloadThread;
- (void) downloadThreadImpl;
- (void) onDownloadDidFail:(DBUserBook *)book;
- (void) onDownloadDidFinish:(DBUserBook *)book;
- (void) onDownloadManagerStarted;
- (void) onDownloadManagerFinished;

@end

@implementation URLCacheConnection

static URLCacheConnection* g_UrlCacheConnection = nil;

+ (URLCacheConnection*) getInstance
{
	@synchronized([URLCacheConnection class])
	{
		if (!g_UrlCacheConnection)
			g_UrlCacheConnection = [[self alloc] init];
        
		return g_UrlCacheConnection;
	}
    
	return nil;
}

+(id)alloc
{
	@synchronized([URLCacheConnection class])
	{	
		NSAssert(g_UrlCacheConnection == nil, @"Attempted to allocate a second instance of a singleton.");
		g_UrlCacheConnection = [super alloc];
		return g_UrlCacheConnection;
	}
	return nil;
}

-(id)init {
	self = [super init];
	if (self != nil) {
        
        isDownloaderRunning = NO;
        
        //Init delegate
        list_delegate = [[NSMutableArray alloc] init];
        list_orders = [[NSMutableArray alloc] init];
        
        //Init cache folder
		[self initCache];
	}
	return self;
}

- (void)dealloc
{
    list_delegate = Nil;
    list_orders = Nil;
    sFolderPath = Nil;
}

- (void) addListener:(id<URLCacheConnectionDelegate>) delegate
{
    @synchronized (list_delegate) {
        [list_delegate addObject:delegate];
    }
}

- (void) downloadBook:(DBUserBook*) book
{
    @synchronized (list_orders) {
        [list_orders addObject:book];
        
        //Check start downloader now
        [self checkStartDownloader];
    }
}

- (void) downloadBookList:(NSArray*) book_list
{
    for (NSObject* obj in book_list) {
        if ([obj isKindOfClass:[DBUserBook class]]) {
            [self downloadBook:(DBUserBook*) obj];
        }
    }
}

- (void) initCache
{
	/* create path to cache directory inside the application's Documents directory */
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES);
    sFolderPath = [[paths objectAtIndex:0] stringByAppendingPathComponent:@"AnyBookCache"];
	
	/* check for existence of cache directory */
	if ([[NSFileManager defaultManager] fileExistsAtPath:sFolderPath]) {
		return;
	}
	
	/* create a new cache directory */
	if (![[NSFileManager defaultManager] createDirectoryAtPath:sFolderPath
								   withIntermediateDirectories:NO
													attributes:nil
														 error:nil]) {
		//URLCacheAlertWithError(error);
		return;
	}
}

- (void) checkStartDownloader
{
 
    @synchronized (list_orders) {
        if (isDownloaderRunning || ([list_orders count] == 0)) {
            return;
        }
    }
    
    [self performSelectorInBackground:@selector(downloadThread) withObject:Nil];
}

- (void) downloadThread
{
    @autoreleasepool {
        [self downloadThreadImpl];
    }
}

- (BOOL) removeFile:(NSString*) filePath
{
    NSFileManager *fileManager = [NSFileManager defaultManager];
    return [fileManager removeItemAtPath:filePath error:NULL];
}

- (BOOL) unzip:(NSString*) filePath toFolder:(NSString*) folderPath
{
    //Do unzip files now
    ZipArchive *zipArchive = [[ZipArchive alloc] init];
    [zipArchive UnzipOpenFile:filePath];
    BOOL result = [zipArchive UnzipFileTo:folderPath overWrite:YES];
    [zipArchive UnzipCloseFile];
    return result;
}

- (BOOL) downloadFile:(NSURL*) url toFilePath:(NSString*) filePath
{
    @try {
        NSHTTPURLResponse   * response;
        NSError             * error;
        NSMutableURLRequest * request;
        request = [[NSMutableURLRequest alloc] initWithURL:url	cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:1000];
        
        NSData* data = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];	
        
        NSInteger codeStatus = [response statusCode];
        DLog_Low(@"codeStatus = %d", codeStatus);
        if (data == Nil) {
            return NO;
        }
        
        NSOutputStream *stream = [[NSOutputStream alloc] initToFileAtPath:filePath append:NO];
        [stream open];
        
        NSUInteger left = [data length];
        DLog_Low(@"File size to download = %d", left);
        NSUInteger nwr = 0;
        @try {
            do {
                nwr = [stream write:[data bytes] maxLength:left];
                if (-1 == nwr) break;
                left -= nwr;
            } while (left > 0);
        }
        @catch (NSException *exception) {
            DLog_Error(@"Error while saving files ... left: %d", left);
            left = 1; //Mark this to return FALSE;
        }
        @finally {
        }
        [stream close];
        
        if (left > 0) {
            return NO;
        }
        return YES;
    }
    @catch (NSException *exception) {
        DLog_Error(@"Error while downloading files ...");
    }
    @finally {
    }
    return NO;
}

- (void) downloadThreadImpl
{
    @synchronized (list_orders) {
        if (isDownloaderRunning || ([list_orders count] == 0)) {
            return;
        }
        isDownloaderRunning = YES;
    }
    [self onDownloadManagerStarted];
    
    CachedDataStore* dataStore = [CachedDataStore getInstance];
    NSString* device_imei = [Utility getDeviceImei];
    NSString* device_profile = [Utility getDeviceProfile];
    UserAccount* user = [dataStore getCurrentUserAccount];
    NSString* msisdn = user.user_mobile;
    
    while (true) {
        DBUserBook* userbook = Nil;
        //Check continue condition
        @synchronized (list_orders) {
            if ([list_orders count] == 0) {
                isDownloaderRunning = NO;
                break;
            }
            
            //Peak
            userbook = (DBUserBook*) [list_orders objectAtIndex:0];
            [list_orders removeObjectAtIndex:0];
            
            //If userbook is already downloaded, remove from queue
            if (![Utility isEmptyOrNull:[userbook getCachedPath]]) {
                continue;
            }
        }
        int bookId = [userbook getBookId];
        
        //Check download link
        JsonBook* jsonBook = [JsonBook getDownloadLink:msisdn device_profile:device_profile device_imei:device_imei book_id:bookId];
        if ([jsonBook isBookInEncodeProgress]) {
            int wait_time = 0;
            @synchronized (list_orders) {
                int orders = [list_orders count];
                if (orders == 0) {
                    wait_time = 2000;
                } else if (orders == 1) {
                    wait_time = 1000;
                } else if (orders == 2) {
                    wait_time = 500;
                } else {
                    wait_time = 100;
                }

                //Push back
                [list_orders addObject:userbook];
            }
            
            if (wait_time > 0) {
                //Wait
                [NSThread sleepForTimeInterval:wait_time];
            }
            continue;            
            
        } else if (![jsonBook isSuccess]) {
            //Failed getting download link -> notify error
            [self onDownloadDidFail:userbook];
            //Do notify now!
            continue;
        }
        
        //Come here mean it's ok to download
        NSString *filePath = [sFolderPath stringByAppendingPathComponent:[NSString stringWithFormat:@"%d_%@.vef", bookId, msisdn]];
        NSString *folderPath = [sFolderPath stringByAppendingPathComponent:[NSString stringWithFormat:@"%d_%@", bookId, msisdn]];
        NSString* surl = [[jsonBook downloadUrl] stringByReplacingOccurrencesOfString:@" " withString:@"%20"];
        NSURL *furl = [NSURL URLWithString:surl];
        
        //Download file
        BOOL success = [self downloadFile:furl toFilePath:filePath];
        
        //Unzip if download success
        if (success) {
            success = [self unzip:filePath toFolder:folderPath];
        }
        
        //Remove file after unzip, if unzip error -> remove it
        [self removeFile:filePath];
        
        if (success) {
            DLog_Low(@"Unzipped to %@", folderPath);
            
            //Update user book
            [userbook setCachedPath:folderPath];
            [userbook setServerKey:[jsonBook serverKey]];
            [userbook setRightObject:[jsonBook rightObject]];
            [dataStore updateUserBook:userbook];
            
            //Notify download success
            [self onDownloadDidFinish:userbook];
            
        } else {
            //Failed getting download link -> notify error
            [self onDownloadDidFail:userbook];
        }
    }
    
    [self onDownloadManagerFinished];
}

- (void) onDownloadDidFail:(DBUserBook *)book
{
    for (id<URLCacheConnectionDelegate> delegate in list_delegate) {
        @try {
            [delegate onDownloadDidFail:book];
        }
        @catch (NSException *exception) {
        }
        @finally {
        }
    }
}

- (void) onDownloadDidFinish:(DBUserBook *)book
{
    for (id<URLCacheConnectionDelegate> delegate in list_delegate) {
        @try {
            [delegate onDownloadDidFinish:book];
        }
        @catch (NSException *exception) {
        }
        @finally {
        }
    }
}

- (void) onDownloadManagerStarted
{
    for (id<URLCacheConnectionDelegate> delegate in list_delegate) {
        @try {
            [delegate onDownloadManagerStarted];
        }
        @catch (NSException *exception) {
        }
        @finally {
        }
    }
}

- (void) onDownloadManagerFinished
{
    for (id<URLCacheConnectionDelegate> delegate in list_delegate) {
        @try {
            [delegate onDownloadManagerFinished];
        }
        @catch (NSException *exception) {
        }
        @finally {
        }
    }
}

@end