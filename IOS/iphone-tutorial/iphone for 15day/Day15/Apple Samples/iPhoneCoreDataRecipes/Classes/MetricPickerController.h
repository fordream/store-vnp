@interface MetricPickerController : NSObject <UIPickerViewDataSource, UIPickerViewDelegate> {
    UIPickerView *pickerView;
    UILabel *label;
}

@property (nonatomic, retain) IBOutlet UIPickerView *pickerView;
@property (nonatomic, retain) IBOutlet UILabel *label;

- (UIView *)viewForComponent:(NSInteger)component;
- (void)updateLabel;

@end
