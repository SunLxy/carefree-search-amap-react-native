// CarefreeSearchAmap.m

#import "CarefreeSearchAmap.h"


@implementation CarefreeSearchAmap{
  BOOL _isStarted;
  AMapSearchAPI *_search;
}

RCT_EXPORT_MODULE()

+ (NSString *)storeKey {
    return @"CarefreeSearchAmap";
}

- (instancetype)init {
    self = [super init];
    if (self) {
        [AMapSearchAPI updatePrivacyShow:AMapPrivacyShowStatusDidShow privacyInfo:AMapPrivacyInfoStatusDidContain];
        [AMapSearchAPI updatePrivacyAgree:AMapPrivacyAgreeStatusDidAgree];
    }
    return self;
}

RCT_EXPORT_METHOD(initSDK: (NSString *)apiKey resolver: (RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject
) {
    @try{
        NSLog( @"initSDK-->" );
        [AMapServices sharedServices].apiKey = apiKey;
        NSLog( @"initSDK-->1" );
        _search = [[AMapSearchAPI alloc] init];
        NSLog( @"initSDK-->2" );
        _search.delegate = self;
        NSLog( @"initSDK-->3" );
        _isStarted = YES;
        resolve(@(_isStarted));
    } @catch (NSException *error){
        NSLog( @"initSDK-->" );
        NSLog( @"Name: %@", error.name);
        NSLog( @"Reason: %@", error.reason);
        reject([NSString stringWithFormat:@"%ld",(long)error.name], error.reason, error);
    }
    
}

- (NSArray<NSString *> *)supportedEvents {
  return @[ @"GetLatLong",@"GetAddress",@"AddressOrLatLongError"];
}

RCT_EXPORT_METHOD(getLatLong:(NSString *) address)
{
     NSLog( @"getLatLong-->" );
    AMapGeocodeSearchRequest *geo = [[AMapGeocodeSearchRequest alloc] init];
    geo.address = address;
    [_search AMapGeocodeSearch:geo];
}

- (void)onGeocodeSearchDone:(AMapGeocodeSearchRequest *)request response:(AMapGeocodeSearchResponse *)response{
    NSLog( @"onGeocodeSearchDone-->" );
    if (response.count == 0) {
        [self sendEventWithName: @"GetLatLong" body:@{
            @"errCode" : @(-1),
            @"errInfo" : @"没有搜索到相关数据",
        }];

    }else{
        AMapGeocode *locationItem = (AMapGeocode *)response.geocodes[0];
        [self sendEventWithName: @"GetLatLong" body:@{
            @"errCode" : @(1000),
            @"longitude" : @(locationItem.location.longitude),
            @"latitude" : @(locationItem.location.latitude),
        }];
    }
}


RCT_EXPORT_METHOD(getAddress:(float)latitude typer:(float)longitude)
{
     NSLog( @"getAddress-->");
    AMapReGeocodeSearchRequest *regeo = [[AMapReGeocodeSearchRequest alloc] init];
    regeo.location = [AMapGeoPoint locationWithLatitude:latitude longitude:longitude];
    regeo.radius = 10;
    [_search AMapReGoecodeSearch:regeo];
}

/* 逆地理编码回调. */
- (void)onReGeocodeSearchDone:(AMapReGeocodeSearchRequest *)request response:(AMapReGeocodeSearchResponse *)response {
     NSLog( @"onReGeocodeSearchDone-->" );
    if (response.regeocode != nil) {
        AMapReGeocode *locationItem = response.regeocode;
        [self sendEventWithName: @"GetAddress" body:@{
            @"errCode" : @(1000),
            @"adCode" : locationItem.addressComponent.adcode,
            @"building" : locationItem.addressComponent.building,
            @"city" : locationItem.addressComponent.city,
            @"cityCode" : locationItem.addressComponent.citycode,
            @"country" : locationItem.addressComponent.country,
            @"district" : locationItem.addressComponent.district,
            @"address" : locationItem.formattedAddress,
            @"neighborhood" :locationItem.addressComponent.neighborhood,
            @"province": locationItem.addressComponent.province,
            @"streetNumber" : locationItem.addressComponent.streetNumber ,
            @"street" : locationItem.addressComponent.streetNumber ,
            @"towncode" : locationItem.addressComponent.towncode,
            @"township" : locationItem.addressComponent.township,
        }];
    }else{
        [self sendEventWithName: @"GetAddress" body:@{
            @"errCode" : @(-1),
            @"errInfo" : @"没有搜索到相关数据",
        }];
    }
}

- (void)AMapSearchRequest:(id)request didFailWithError:(NSError *)error
{
    NSLog(@"Error: %@", error);
    [self sendEventWithName: @"AddressOrLatLongError" body:@{
        @"errCode" : @(-3),
        @"errInfo" : error,
    }];
}

/*! 
 * [warn][tid:main][RCTModuleData.mm:68] Module Alipay requires main queue setup since it overrides `init` but doesn't implement `requiresMainQueueSetup`. 
 * In a future release React Native will default to initializing all native modules on a background thread unless explicitly opted-out of.
 */;
+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

@end
