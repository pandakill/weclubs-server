# weclubs-server

1. 使用AOE拦截HTTP请求，并将请求信息（IP地址、请求类型、请求参数、日期、）打印在日志系统当中；
2. 设计请求参数`sign`签名机制，防止请求参数被篡改或者流量攻击等：将所有请求参数hash升序排列之后，在手尾添加密钥，并将其`MD5`加密，得到签名值；
3. 设计`token`，并设计单点登录机制：同类型设备只允许一个`token`存在（即登录），否则会在请求当中返回`token`失效的`tips`；
4. 利用`MD`文档，进行友好性的文档输出；
