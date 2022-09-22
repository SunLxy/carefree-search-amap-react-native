// CarefreeSearchAmap.m

#import "CarefreeSearchAmap.h"


@implementation CarefreeSearchAmap


RCT_EXPORT_MODULE()

- (instancetype)init {
    self = [super init];
    if (self) {
        [AMapSearchAPI updatePrivacyShow:AMapPrivacyShowStatusDidShow privacyInfo:AMapPrivacyInfoStatusDidContain];
        [AMapSearchAPI updatePrivacyAgree:AMapPrivacyAgreeStatusDidAgree];
        // self.search = [[AMapSearchAPI alloc] init];
        // self.search.delegate = self;
    }
    return self;
}

RCT_EXPORT_METHOD(initSDK: (NSString *)apiKey 
                resolver: (RCTPromiseResolveBlock)resolve
                rejecter:(RCTPromiseRejectBlock)reject
) {
    [AMapServices sharedServices].apiKey = apiKey;
    if(self){
        self.search = [[AMapSearchAPI alloc] init];
        self.search.delegate = self;
        resolver(@(true));
    }else{
        reject(@("设置apiKey失败"));
    }
}

RCT_EXPORT_METHOD(getLatLong:
                  (NSString *) address
                resolver: (RCTPromiseResolveBlock)resolve
                rejecter:(RCTPromiseRejectBlock)reject)
{
    AMapGeocodeSearchRequest *geo = [[AMapGeocodeSearchRequest alloc] init];
    geo.address = @(address);
    [self onGeocodeSearchDone:(AMapGeocodeSearchRequest *)request response:(AMapGeocodeSearchResponse *)response{
        if (response.geocodes.count == 0){
            resolver(@{
                @"errCode" : @(-1),
                @"errInfo" : @"没有搜索到相关数据",
            });
        }else{
            AMapGeocode locationItem = [response.geocodes firstObject]
            resolver(@{
                @"longitude" : locationItem.location.longitude,
                @"latitude" : locationItem.location.latitude,
            });
        }
    }];
    [self.search AMapGeocodeSearch:geo];
}


RCT_EXPORT_METHOD(getAddress:
                (AMapGeoPoint) point
                resolver: (RCTPromiseResolveBlock)resolve
                rejecter:(RCTPromiseRejectBlock)reject)
{
    AMapReGeocodeSearchRequest *reGeo = [[AMapReGoecodeSearch alloc] init];
    AMapGeoPoint newPoint = AMapGeoPoint(point.latitude, point.longitude)  
    
    reGeo.location = @(newPoint);
    [self onReGeocodeSearchDone:(AMapReGeocodeSearchRequest *)request response:(AMapReGeocodeSearchResponse *)response{
        if (response.regeocode.addressComponent){
            AMapReGeocode locationItem = response.regeocode;
            AMapAddressComponent addressInfo = locationItem.addressComponent;
            AMapStreetNumber streetNumber = addressInfo.streetNumber;
            resolver(@{
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
            });
        }else{
            resolver(@{
                @"errCode" : @(-1),
                @"errInfo" : @"没有搜索到相关数据",
            });
        }
        
    }];
    [self.search AMapGeocodeSearch:reGeo];
}

@end
