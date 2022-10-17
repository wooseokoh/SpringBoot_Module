sudo docker stop compsite;
sudo docker stop product;
sudo docker stop recommend;
sudo docker stop review;
sudo docker stop mongoDB;
sudo docker stop mysqlDB;

sudo docker rmi compsite;
sudo docker rmi product;
sudo docker rmi recommend;
sudo docker rmi review;
sudo docker network rm apps_net;
