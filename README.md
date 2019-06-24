# API接口安全方案
本平台下使用的接口类型主要包括：

- Web Service接口
- Hessian接口
- Dubbo服务接口
- Redis缓存接口
- Rest风格接口

其中Web Service接口和Restful接口一般用于和第三方单位或者移动客户端通信；Hessian接口、Dubbo服务接口、Redis缓存接口一般用于内部数据通信。基于使用场景我们制定如下的安全方案，保证数据通信的安全性。

