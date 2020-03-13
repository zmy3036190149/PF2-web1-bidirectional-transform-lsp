文本和图形的双向变换  
采用LSP的DidOpen,didSave实现了文本端的注册及通知功能，该功能与语法检测是分开的
    仿照LSP的DidOpen,didSave实现了图形端的注册及通知功能
后端加入了语言服务器，为每个客户端都开了一个线程，且只提供语法检测功能,这种方法在本地可以连接，但上传到服务器后就不能连接了，因此又将语言服务器从后端拿出来。  
在图形端和文本端均保留两种AST,修改时同时修改两种AST,同步（合并）时文本AST与文本AST合并，图形AST与图形AST合并  
# 部署  
## 语言服务器  
rz 上传pf-language-server文件夹  
cd pf-language-server 
yarn install
nohup yarn start  &

## 后端  
修改路径
    AddressService.java; Forward.java  
run as -> maven clean  
run as -> maven install  
nohup java -jar PF2-new-0.0.1-SNAPSHOT.jar --server.port=8099 &

## 前端  
修改路径
    localhost -> 47.52.116.116
1.windows中 ng build --prod
3.将dist/PF1-web/压缩
4.cd /var/lib/docker/volumes/lamp_www/_data/example.com/public_html
5.rm -rf PF1-web 
6.rz上传  PF1-web.zip
7.unzip PF1-web.zip
8.rm PF1-web.zip


# bug
[X]requirement简称  
[X]requirement 加简称（默认名称去空格）  
[X]语言服务器加入后端  
[X]文本端引号问题
[]project数据大  
[X]备用project  
[]引用和约束的现象发起者和接受者问题（修改算法，引用找前面，约束是domain）  
[]owl导入  

# 变更记录
## 20200210
初始化，后端没有语言服务器  
## 20200216
简化实体属性名及方法名  
## 20200307
实现了图形的同步和文本的同步  
    采用LSP的DidOpen,didSave实现了文本端的注册及通知功能，该功能与语法检测是分开的
    仿照LSP的DidOpen,didSave实现了图形端的注册及通知功能
后端加入了语言服务器，为每个客户端都开了一个线程，且只提供语法检测功能,这种方法在本地可以连接，但上传到服务器后就不能连接了
修改了若干bug
## 30300313
还有若干bug，见图形文本同步bug.docx(一个页面测试)和图形文本同时同步.docx(两个页面测试)

# 文件说明
- pf-language-server  
    语言服务器，提供语法检测功能
- xtext-pf-language-server  
    定义pf语法，执行 ./gradlew shadowJar 生成pf-language-server.jar  
- doc
    文档，图

- 其他参考文档
https://www.yuque.com/zhaomengyao2019/xiangmu/kk27hq
doc/change&merge.uml