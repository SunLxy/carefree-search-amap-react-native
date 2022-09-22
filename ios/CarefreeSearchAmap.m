// CarefreeSearchAmap.m

#import "CarefreeSearchAmap.h"


@implementation CarefreeSearchAmap{
  BOOL _isStarted;
  AMapSearchAPI *_search;
}

RCT_EXPORT_MODULE()

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
        [AMapServices sharedServices].apiKey = apiKey;
        _search = [[AMapSearchAPI alloc] init];
        _search.delegate = self;
        _isStarted = YES;
        resolve(@(_isStarted));
    } @catch (NSException *error){
        // Print exception information
        NSLog( @"NSException caught" );
        NSLog( @"Name: %@", error.name);
        NSLog( @"Reason: %@", error.reason );
        reject([NSString stringWithFormat:@"%ld",(long)error.name], error.reason, error);
    }
    
}

- (NSArray<NSString *> *)supportedEvents {
  return @[ @"GetLatLong",@"GetAddress" ];
}


RCT_EXPORT_METHOD(getLatLong:(NSString *) address)
{
    AMapGeocodeSearchRequest *geo = [[AMapGeocodeSearchRequest alloc] init];
    geo.address = address;
    [_search AMapGeocodeSearch:geo];
}

- (void)onGeocodeSearchDone:(AMapGeocodeSearchRequest *)request response:(AMapGeocodeSearchResponse *)response{
    if (response.count == 0) {
        [self sendEventWithName: @"GetLatLong" body:@{
            @"errCode" : @(-1),
            @"errInfo" : @"没有搜索到相关数据",
        }];

    }else{
        AMapGeocode *locationItem = (AMapGeocode *)response.geocodes[0];
        [self sendEventWithName: @"GetLatLong" body:@{
            @"longitude" : @(locationItem.location.longitude),
            @"latitude" : @(locationItem.location.latitude),
        }];
    }
}


RCT_EXPORT_METHOD(getAddress: (AMapGeoPoint *)point)
{
    
    AMapReGeocodeSearchRequest *regeo = [[AMapReGeocodeSearchRequest alloc] init];
    regeo.location = [AMapGeoPoint locationWithLatitude:point.latitude
                                              longitude:point.longitude];
    [_search AMapReGoecodeSearch:regeo];
}

/* 逆地理编码回调. */
- (void)onReGeocodeSearchDone:(AMapReGeocodeSearchRequest *)request response:(AMapReGeocodeSearchResponse *)response {
    if (response.regeocode != nil) {
        AMapReGeocode *locationItem = response.regeocode;
        [self sendEventWithName: @"GetAddress" body:@{
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


@end
