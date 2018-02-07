//
//  MainControllerViewController.h
//  test0101
//
//  Created by yangguang on 2018/2/6.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <React/RCTBridge.h>

@interface MainViewController : UIViewController

@property (nonatomic, assign) RCTBridge* bridge;

-(void)setBridge:(RCTBridge*) bridge;

- (void)clicka:(UIButton *)button;
- (void)clickb:(UIButton *)button;

@end
