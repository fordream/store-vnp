PrintPhoto

=========================================================================
DESCRIPTION:

PrintPhoto demonstrates how to print photos. The application allows a user to view and print any photo from the user's photo library. It initially presents a photo that is built into the application's bundle but by touching the photo picker icon you can choose any photo in the library.

PrintPhoto demonstrates two different strategies for printing photos. The simplest way to print a photo is to set the printingItem property of the shared UIPrintInteractionController instance to the NSURL, NSData, UIImage, or ALAsset object referencing or containing the photo. If you have multiple photos to print, you can create an array containing the types of objects cited above and assign that array to the printingItems property. 

PrintPhoto also demonstrates how to print by using a custom UIPrintPageRenderer object to render the content for printing. This object must be assigned to the printPageRenderer property of the shared UIPrintInteractionController instance. For this example, the subclass of UIPrintPageRenderer overrides the numberOfPages method as well as a "draw" method that draws the image upon request.

PrintPhoto also shows how to:

	* Pick a photo from the user's photo library for display and print.
	* Utilize the ALAssetsLibrary to obtain a screen size image for display.
	* Obtain and configure the shared UIPrintInteractionController instance.
	* Use the printingItem property of the UIPrintInteractionController object for direct data submission.
	* Alternatively, use a UIPrintPageRenderer object to draw printable content instead of submitting it directly.

To have the UIPrintPageRenderer object draw the printable content, comment out the following line in PrintPhotoViewController.m or change the value to "0":

#define DIRECT_SUBMISSION 1

=========================================================================
RELATED INFORMATION:

Tip:  You can easily add photos to the photo library in the simulator by dragging an image onto the simulator which
      will load the image in Safari. If you then touch and hold the image, you will get a popover giving you a choice
      of copying the image to the pasteboard or saving it to the photo library.
      
      Another approach is to email some photos to yourself and then save those photos to the photo library in the simulator by using the Mail application.


=========================================================================
BUILD REQUIREMENTS:

XCode 3.2.5, Mac OS X 10.6.4 or later


=========================================================================
RUNTIME REQUIREMENTS:

iOS 4.2 or later


=========================================================================
PACKAGING LIST:

PrintPhotoViewController.h
PrintPhotoViewController.m

The PrintPhotoViewController class defines the controller object for the application. The controller object uses a UIImageView to present its content and an UIImagePickerController to allow a user to pick a photo from their photo library. The code also demonstrates how to use the ALAssetLibrary to obtain a screen size image for use for display. The PrintPhotoViewController class uses the shared UIPrintInteractionController object to print a photo either by assigning objects containing or referencing image data to the printingItem property or by using PrintPhotoPageRenderer, a subclass of the UIPrintPageRenderer class.

PrintPhotoPageRenderer.h
PrintPhotoPageRenderer.m
A subclass of UIPrintPageRenderer that prints a single photo. UIKit calls methods of the UIPrintPageRender class to compute the number of pages to print and to render those pages to the printing graphics context.

FirstImage.jpg
An image to present as the first image for a user to see (and print) prior to picking a photo from the photo library.


=========================================================================
CHANGES FROM PREVIOUS VERSIONS:

9/22/2010 - First version.

=========================================================================
Copyright (C) 2010 Apple Inc.  All rights reserved.