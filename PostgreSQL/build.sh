sudo docker build . -t postgres:local
sudo docker run -p 5432:5432 --rm -d --name postgress postgres:local