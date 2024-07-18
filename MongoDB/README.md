# My docker image for MongoDB using
## How to use it
1) Run command below for starting docker container  
    sudo docker run -d  --name my-mongo my-mongo  
    (if doesn't work run without --name part)
2) Run next comand for starting shell  
    sudo docker exec -it CONTAINER_ID(NAME) /bin/bash
3) Run command below for starting MongoDB shell  
    mongosh
## How to auth 
1) Open mongo shell
2) use admin
3) db.auth("admin_user","admin_password") or use set username/password
## For insert on gridfs user
/bin/mongofiles -u admin_user -p admin_password --authenticationDatabase=admin -d DATABASE_NAME put FILE 

sudo docker exec -it test /bin/bash