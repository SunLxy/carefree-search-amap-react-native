// CarefreeSearchAmap.h

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <AMapFoundationKit/AMapFoundationKit.h>
#import <AMapSearchKit/AMapSearchKit.h>

@interface CarefreeSearchAmap : RCTEventEmitter <RCTBridgeModule, AMapSearchDelegate>

@end
