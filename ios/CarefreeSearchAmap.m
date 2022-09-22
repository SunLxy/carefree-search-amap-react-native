// CarefreeSearchAmap.m

#import "CarefreeSearchAmap.h"


@implementation CarefreeSearchAmap{
  BOOL _isStarted;
  AMapSearchAPI *search
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
        search = [[AMapSearchAPI alloc] init];
        search.delegate = self;
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
    [self.search AMapGeocodeSearch:geo];
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
            @"longitude" : locationItem.location.longitude,
            @"latitude" : locationItem.location.latitude,
        }];
    }
}


RCT_EXPORT_METHOD(getAddress: (AMapGeoPoint *)point)
{
    
    AMapReGeocodeSearchRequest *regeo = [[AMapReGeocodeSearchRequest alloc] init];
    regeo.location = [AMapGeoPoint locationWithLatitude:point.latitude
                                              longitude:point.longitude];
    [self.search AMapReGoecodeSearch:regeo];
}

/* 逆地理编码回调. */
- (void)onReGeocodeSearchDone:(AMapReGeocodeSearchRequest *)request response:(AMapReGeocodeSearchResponse *)response {
    if (response.regeocode != nil) {
        AMapReGeocode locationItem = response.regeocode;
        AMapAddressComponent addressInfo = locationItem.addressComponent;
        AMapStreetNumber streetNumber = addressInfo.streetNumber;
        [self sendEventWithName: @"GetAddress" body:@{
            @"adCode" : @(addressInfo.adCode),
            @"building" : @(addressInfo.building),
            @"city" : @(addressInfo.city),
            @"cityCode" : @(addressInfo.citycode),
            @"country" : @(addressInfo.country),
            @"district" : @(addressInfo.district),
            @"address" : @(locationItem.formattedAddress),
            @"neighborhood" : @(addressInfo.neighborhood),
            @"province": @(addressInfo.province),
            @"streetNumber" : streetNumber ? streetNumber.number:@"",
            @"street" : streetNumber ? streetNumber.street:@"",
            @"towncode" : addressInfo.towncode,
            @"township" : addressInfo.township,
        }];
    }else{
        [self sendEventWithName: @"GetAddress" body:@{
            @"errCode" : @(-1),
            @"errInfo" : @"没有搜索到相关数据",
        }];
    }
}


@end
