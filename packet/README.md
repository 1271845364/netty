### 粘包和分包问题
解决方案：定义一个稳定的数据结构 dataLength + data

1. 为什么FrameDecoder decode()的return就是往下传递的对象：最终还是调用了ctx.sendUpstream()
2. buffer中的数据没读取完怎么办：cumulation缓存了
3. 为什么FrameDecoder decode()的return null，就可以缓存buffer：cumulation缓存数据

FrameDecoder里面的cumulation其实就是一个缓存的buffer对象
