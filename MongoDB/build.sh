sudo docker build . -t my-mongo:test
sudo docker run -p 27017:27017 --rm -d --name test my-mongo:test
#sudo docker exec -it test /bin/bash