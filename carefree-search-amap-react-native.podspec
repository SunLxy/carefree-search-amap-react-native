# carefree-search-amap-react-native.podspec

require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "carefree-search-amap-react-native"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  carefree-search-amap-react-native
                   DESC
  s.homepage     = "https://github.com/SunLxy/carefree-search-amap-react-native"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "SunLxy" => "1011771396@qq.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/SunLxy/carefree-search-amap-react-native.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,cc,cpp,m,mm,swift}"
  s.requires_arc = true

  s.dependency "React-Core"
  s.dependency "AMapSearch", "9.2.0"
  # ...
  # s.dependency "..."
end

