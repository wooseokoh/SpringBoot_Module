sudo docker run -d --rm --name mysqlDB \
-e MYSQL_DATABASE=review-db \
-e MYSQL_USER=user01 \
-e MYSQL_PASSWORD=user01 \
-e MYSQL_ROOT_PASSWORD=password \
- 3306:3306 \
mysql:5.7

sudo docker run -d --rm --name mongoDB \
- 27017:27017 \
mongo:3.6.9 \
mongod --smallfiles