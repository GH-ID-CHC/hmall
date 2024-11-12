# 前端

在web中还提供了一个hmall-nginx的目录：

其中就是一个nginx程序以及我们的前端代码，直接在windows下将其复制到一个非中文、不包含特殊字符的目录下。然后进入hmall-nginx后，利用cmd启动即可：

## 启动nginx

start nginx.exe
## 停止
nginx.exe -s stop
## 重新加载配置
nginx.exe -s reload
## 重启
nginx.exe -s restart

特别注意：
nginx.exe 不要双击启动，而是打开cmd窗口，通过命令行启动。停止的时候也一样要是用命令停止。如果启动失败不要重复启动，而是查看logs目录中的error.log日志，查看是否是端口冲突。如果是端口冲突则自行修改端口解决。

启动成功后，访问http://localhost:18080，应该能看到我们的门户页面：