### netty自定义数据包协议
give me a coffee     give me a tea

give me a coffeegive me a tea	粘包现象

give me 
 a coffeegive me a tea	分包现象
 
粘包和分包现象出现的原因，是因为没有一个稳定的数据结构

解决方案：1、分隔符的方式   2、长度+数据

FrameDecoder这个decoder可以协助我们解决粘包和分包问题
 
ChannelBuffer  readerIndex() = 读指针>=0  writeIndex() = 写指针>=读指针

### 自定义序列化
com.yejinhui.common.serial

### 自定义请求和响应的编解码
com.yejinhui.common.codec


### socket字节流攻击
长度+数据，攻击把长度给你设置为Integer.MAX_VALUE，这样直接内存就撑不住了 = 字节流式的攻击

解决方案：如果长度超过了2048，就把buffer清空，但是又带来另外一个问题，我怎么知道后面来的数据哪里是开头 解决方案：包头+长度+数据，包头标识