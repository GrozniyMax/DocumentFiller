cd /creation

echo 'db.createUser({ user:"'${MONGO_USERNAME}'", pwd:"'${MONGO_PASSWORD}'", roles:[{role:"readWrite",db:"'${MONGO_DB_NAME}'"}]});' > script.js
echo 'db.createCollection("'${MONGO_COLLECTION_NAME}'");' >> script.js

mongosh -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase=admin --file=./script.js 2>log.txt

#GridFS insert  files *.docx
cd /creation/templates
for file in `find . -type f -name "*.docx"`
do
  /bin/mongofiles -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase=admin -r -d $MONGO_DB_NAME put $(basename $file)
done
cd ..

#Put properties in db
touch tmp_script.js
for file in `find ./templates -type f -name "*.json"`
do
  content=$(cat $file)
  echo "db.${MONGO_DB_NAME}.insertOne(${content});">>tmp_script.js
done
mongosh -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase=admin --file=tmp_script.js

