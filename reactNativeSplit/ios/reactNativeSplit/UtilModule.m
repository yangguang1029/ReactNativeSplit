//
//  UtilModule.m
//  test0101
//
//  Created by yangguang on 2018/2/7.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "UtilModule.h"

@implementation UtilModule

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(back)
{
  dispatch_async(dispatch_get_main_queue(), ^{
    [[UIApplication sharedApplication].keyWindow.rootViewController dismissViewControllerAnimated:NO completion:nil];
  });
  
}

@end
