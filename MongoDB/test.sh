
#GridFS insert  files *.docx
cd /creation/templates
for file in `find . -type f -name "*.docx"`
do
  /bin/mongofiles -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase=admin  -d $MONGO_DB_NAME put $(basename $file)
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

#Remove all created files that are useless now
rm -rf /creation
