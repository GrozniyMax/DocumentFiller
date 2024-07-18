FROM mongo:latest
LABEL authors="maxim Taranenko"

#Set enviroment for mongoDB auth
ENV MONGO_INITDB_ROOT_USERNAME=admin_user
ENV MONGO_INITDB_ROOT_PASSWORD=admin_password
ENV MONGO_USERNAME=maxim
ENV MONGO_PASSWORD=test
ENV MONGO_DB_NAME=test
ENV MONGO_COLLECTION_NAME=attribures

#Create directories and set needed rights
RUN mkdir ./creation;

#Copy and set rules for creation script.sh
COPY ./script.sh ./creation
COPY ./test.sh ./creation

RUN chmod -R a+rwx /creation
RUN chmod a+x ./creation/script.sh;
RUN cp ./creation/script.sh ./docker-entrypoint-initdb.d

#Copy templates
COPY templates ./creation/templates

EXPOSE 27017
