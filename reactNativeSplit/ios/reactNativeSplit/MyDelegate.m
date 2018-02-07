//
//  MyDelegate.m
//  test0101
//
//  Created by yangguang on 2018/2/6.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "MyDelegate.h"

@implementation MyDelegate

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge {
  NSURL*  sourceUrl = [[NSBundle mainBundle] URLForResource:@"common" withExtension:@"bundle"];
  NSLog(@"guangy source url %@", sourceUrl.absoluteString);
  return sourceUrl;
}

@end
