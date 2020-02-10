文本和图形的双向变换,语言服务器和后端合并（未实现）   

# 部署  
## 语言服务器  
docker run -itd --name syn -p 8030:8030 node /bin/bash  
rz 上传pf-language-server文件夹  
docker cp pf-language-server syn:/pf  
docker exec -it syn /bin/bash  
cd pf  
yarn start  
## 后端  
run as -> maven clean  
run as -> maven install  
nohup java -jar syn.jar &  
## 前端  
cd /var/lib/docker/volumes/lamp_www/_data/example.com/public_html/

# bug
🔲requirement简称  
🔲project数据大  
🔲备用project  
🔲引用和约束的现象发起者和接受者问题（修改算法，引用找前面，约束是domain）  
🔲requirement 加简称（默认名称去空格）  
🔲owl导入  
🔲语言服务器加入后端  

# 变更记录
## 20200210
初始化，后端没有语言服务器