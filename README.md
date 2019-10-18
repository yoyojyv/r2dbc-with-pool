# Spring R2DBC MySQL Sample Application

## 실제로 띄워보려면...
- datasource 설정은 상황에 맞게 변경하여 사용할 것 

## docker mysql 
- first time 
```
docker run --name=mysql_test01 -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=test -e MYSQL_PASSWORD=test -e MYSQL_DATABASE=test mysql/mysql-server:5.7.24
```
- else 
```
docker start mysql_test01
```

## reference
https://docs.spring.io/spring-data/r2dbc/docs/1.0.x/reference/html/#reference
