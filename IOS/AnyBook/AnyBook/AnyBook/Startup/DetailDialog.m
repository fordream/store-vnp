//
//  DetailDialog.m
//  AnyBook
//
//  Created by Vuong Truong on 4/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "DetailDialog.h"

@implementation DetailDialog

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    
    return self;
}

-(id) init :(Book*)bok{
    self = [super initWithFrame:CGRectMake(0, 0, 100, 100)];
    
    book = bok;
    
    if(book!= nil){
        //image = [UIImage alloc]ini
    }
    
    [self setTitle:@"\n\n\n\n\n\n\n\n"];
    
//    [self setBackgroundColor:[UIColor clearColor]];
    UIImageView *imageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, WIDTH_DIALOG , HEIGHT_DIALOG)];
    
    [imageView setImage:[UIImage imageNamed:@"detail_bg.png"]];
    
    [self addSubview:imageView];
    
    
    UITableView *uiTableView = [[UITableView alloc]initWithFrame:CGRectMake(0, 2,  WIDTH_DIALOG,HEIGHT_DIALOG)];
    [uiTableView setBackgroundColor:[UIColor clearColor]];
    
    [uiTableView setSeparatorColor:[UIColor clearColor]];
    
    [uiTableView setDelegate:self];
    [uiTableView setDataSource:self];
    
    [self addSubview:uiTableView];
    
    ButtonX *buttonX = [[ButtonX alloc]init];
    
    [self addSubview:buttonX];
    [buttonX addTarget:self action:@selector(onClick:) forControlEvents:UIControlEventTouchUpInside];
    return self;
}
-(IBAction)onClick:(id)sender{
    [self dismissWithClickedButtonIndex:0 animated:NO];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier] ;
    }

    return cell;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section{
    return @"";
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0,0,WIDTH_DIALOG - 6,HEIGHT_DIALOG/3)];
    
    UIImageView *imageView = [[UIImageView alloc]initWithFrame:CGRectMake(2,2,WIDTH_DIALOG - 6,HEIGHT_DIALOG / 3)];
    [imageView setImage:[UIImage imageNamed:@"detail_header_table.png"]];
    [headerView addSubview:imageView];
    
    
    
    UIImageView *imageViewBook = [[UIImageView alloc] initWithFrame:CGRectMake(10,10,HEIGHT_DIALOG/5,HEIGHT_DIALOG/3 - 16)];
    
    
    NSData *mydata = [[NSData alloc] initWithContentsOfURL:[NSURL URLWithString:[book getBookTHUMBNAIL]]];
    
    UIImage *myimage = [[UIImage alloc] initWithData:mydata];
    
    [imageViewBook setImage:myimage];
    
    [headerView addSubview:imageViewBook];
    
    
    
    ///add name
    [headerView addSubview:[self createName:[book getBookTITLE]]];
    
    //add tacgia
    [headerView addSubview:[self createAuThoer:@"Tác giả : " left:80]];
    [headerView addSubview:[self createAuThor:[book getBookAUTHOR] left:80]];
    
    //add nam san xuat
    [self createNamSanxuatHeader:@"Năm sản xuất: " left:80 parent:headerView];
    
    // add so trang
    [self createSotrang:@"Năm sản xuất: " left:80 parent:headerView];
    
    // add thong tin sach
    [self createThongTinSach:headerView];
    
    return headerView;
}

// Add thong tin sachs
-(void)createThongTinSach:(UIView*)parent{
    
    float left = 10;
    
    float width_text = 260;
    
    UILabel *txtNameBook = [[UILabel alloc]initWithFrame:CGRectMake(left,110,width_text,20)];
    [txtNameBook setBackgroundColor:[UIColor yellowColor]];
    txtNameBook.text = @"Thông tin sách : ";
    [parent addSubview:txtNameBook];
     [txtNameBook setBackgroundColor:[UIColor clearColor]];
    
    UILabel *txtNameBook1 = [[UILabel alloc]initWithFrame:CGRectMake(left ,135,width_text,100)];

    txtNameBook1.text = [book getBookDESCRIPTION];
    txtNameBook1.lineBreakMode = UILineBreakModeWordWrap;
    txtNameBook1.numberOfLines = 0;
     [txtNameBook1 sizeToFit];
    
//    [txtNameBook1 setTextColor:[UIColor grayColor]];
    [txtNameBook1 setFont:[UIFont fontWithName:@"American Typewriter" size:14]];
    [txtNameBook1 setBackgroundColor:[UIColor clearColor]];
    [parent addSubview:txtNameBook1];
}

-(void)createSotrang:(NSString*)text left:(float)left parent:(UIView*)parent{
    float width_text = 80;
    UILabel *txtNameBook = [[UILabel alloc]initWithFrame:CGRectMake(left,70,width_text,20)];
 [txtNameBook setBackgroundColor:[UIColor clearColor]];
    txtNameBook.text = @"Số trang : ";
    [parent addSubview:txtNameBook];
    
    
    UILabel *txtNameBook1 = [[UILabel alloc]initWithFrame:CGRectMake(left + width_text,70,width_text + 32,20)];
   
    txtNameBook1.text = [book getBookIdAsString];
    [txtNameBook1 setTextColor:[UIColor grayColor]];
    [txtNameBook1 setFont:[UIFont fontWithName:@"American Typewriter" size:14]];
     [txtNameBook1 setBackgroundColor:[UIColor clearColor]];
    [parent addSubview:txtNameBook1];
    
} 


-(void)createNamSanxuatHeader:(NSString*)text left:(float)left parent:(UIView*)parent{
    float width_text = 120;
    UILabel *txtNameBook = [[UILabel alloc]initWithFrame:CGRectMake(left,50,width_text,20)];

    txtNameBook.text = text;
    [txtNameBook setBackgroundColor:[UIColor clearColor]];
    [parent addSubview:txtNameBook];
    
    
    UILabel *txtNameBook1 = [[UILabel alloc]initWithFrame:CGRectMake(left + width_text,50,width_text - 50 + 2,20)];

    txtNameBook1.text = [book getBookPUBLISH_TIME];
    
    [txtNameBook1 setTextColor:[UIColor grayColor]];
    [txtNameBook1 setBackgroundColor:[UIColor clearColor]];
    [txtNameBook1 setFont:[UIFont fontWithName:@"American Typewriter" size:14]];
    [parent addSubview:txtNameBook1];
} 


-(UILabel*)createAuThoer:(NSString*)text left:(float)left{
    float width_text = 70;
    UILabel *txtNameBook = [[UILabel alloc]initWithFrame:CGRectMake(left,30,width_text,20)];

    txtNameBook.text = text;
    [txtNameBook setBackgroundColor:[UIColor clearColor]];
    return txtNameBook;
} 

-(UILabel*)createAuThor:(NSString*)text left:(float)left{
    float width_text = 122;
    UILabel *txtNameBook = [[UILabel alloc]initWithFrame:CGRectMake(left + 70,30,width_text,20)];

    txtNameBook.text = text;
    [txtNameBook setTextColor:[UIColor grayColor]];
    [txtNameBook setBackgroundColor:[UIColor clearColor]];
    [txtNameBook setFont:[UIFont fontWithName:@"American Typewriter" size:14]];
    return txtNameBook;
} 

-(UILabel*)createName:(NSString*)text {
    float width_text = WIDTH_DIALOG - (20 + HEIGHT_DIALOG/5 + 10);
    UILabel *txtNameBook = [[UILabel alloc]initWithFrame:CGRectMake(20 + HEIGHT_DIALOG/5,10,width_text,20)];
    [txtNameBook setBackgroundColor:[UIColor clearColor]];
    txtNameBook.text = text;
    return txtNameBook;
} 

-(float)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return HEIGHT_DIALOG;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    return 1;
}

@end
