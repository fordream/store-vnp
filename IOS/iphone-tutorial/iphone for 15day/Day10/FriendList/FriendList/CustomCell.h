//
//  CustomCell.h
//  FriendList
//
//  Created by Khoa Nguyen on 7/18/12.
//
//

#import <UIKit/UIKit.h>

@interface CustomCell : UITableViewCell
@property(nonatomic, strong)  IBOutlet UIImageView *photo;
@property(nonatomic, strong) IBOutlet UILable *title;
@property(nonatomic, strong) IBOutlet UILable *description;
@end
