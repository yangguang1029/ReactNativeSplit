//
//  MainControllerViewController.m
//  test0101
//
//  Created by yangguang on 2018/2/6.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "MainViewController.h"
#import <React/RCTRootView.h>
#

@interface MainViewController ()

@end

@implementation MainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
  
  [self.view setBackgroundColor:UIColor.whiteColor];
  
  UIButton* b1 = [UIButton new];
  [b1 setTitle:@"toA" forState:UIControlStateNormal];
  [b1 addTarget:self action:@selector(clicka:) forControlEvents:UIControlEventTouchUpInside];
  [b1 setBackgroundColor:UIColor.grayColor];
  [b1 setFrame:CGRectMake(100, 100, 100, 40)];
  [self.view addSubview:b1];
  
  UIButton* b2 = [UIButton new];
  [b2 setTitle:@"toB" forState:UIControlStateNormal];
  [b2 setBackgroundColor:UIColor.grayColor];
  [b2 addTarget:self action:@selector(clickb:) forControlEvents:UIControlEventTouchUpInside];
  [b2 setFrame:CGRectMake(100, 200, 100, 40)];
  [self.view addSubview:b2];
  
}



- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

-(void) setBridge:(RCTBridge *)bridge{
  _bridge = bridge;
}

-(void)clicka:(UIButton *)button{
  NSLog(@"click a......");
  
  NSURL*  sourceUrl = [[NSBundle mainBundle] URLForResource:@"suba" withExtension:@"bundle"];
  [_bridge loadModule:@"suba" url:sourceUrl onComplete:^(NSError *error) {
    if (error) {
      NSLog(@"DPNmanager::suba::加载失败error::%@",error);
    } else {
      NSLog(@"DPNmanager:suba::加载成功");
      [self openView:@"suba"];
    }
  }];
}


-(void)clickb:(UIButton *)button{
  NSLog(@"click b......");
  NSURL*  sourceUrl = [[NSBundle mainBundle] URLForResource:@"subb" withExtension:@"bundle"];
  [_bridge loadModule:@"subb" url:sourceUrl onComplete:^(NSError *error) {
    if (error) {
      NSLog(@"DPNmanager::subb::加载失败error::%@",error);
    } else {
      NSLog(@"DPNmanager:subb::加载成功");
      [self openView:@"subb"];
    }
  }];
}

-(void) openView:(NSString*)moduleName{
  RCTRootView* view = [[RCTRootView alloc] initWithBridge:_bridge moduleName:moduleName initialProperties:nil];
  UIViewController* controller = [UIViewController new];
  [controller setView:view];
  [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:controller animated:NO completion:nil];
  
}

@end
