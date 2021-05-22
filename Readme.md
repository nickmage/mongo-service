Create / switch to a database (must be invoked before any other command execution): 
> use databaseName

Create collection for a database: 
> db.createCollection("databaseName")

Delete collection from a database: 
> db.databaseName.drop()



***CRUD operations***

Create a document:
> db.databaseName.insertOne({
"username": "TestUser",
"firstName": "testing",
"lastName": "Test",
"city": "Testville",
"dateOfBirth": new Date("1990-10-10T22:00:00.000Z"),
"enabled": true,
"followings": ["6090241bc6e6d715c9c70358", "6090241bc6e6d715c9c70389", "60902374c6e6d715c9c70283", "6090241bc6e6d715c9c70339", "6090241bc6e6d715c9c703ae", "6090241bc6e6d715c9c703a9"],
})

Create several documents:
> db.databaseName.insertMany([{
"username": "TestUser",
"firstName": "testing",
"lastName": "Test",
"city": "Testville",
"dateOfBirth": new Date("1990-10-10T22:00:00.000Z"),
"enabled": true,
"followings": ["6090241bc6e6d715c9c70358", "6090241bc6e6d715c9c70389", "60902374c6e6d715c9c70283", "6090241bc6e6d715c9c70339", "6090241bc6e6d715c9c703ae", "6090241bc6e6d715c9c703a9"],
}, 
{...} 
...])




Find all documents:
> db.databaseName.find()

Find documents with a limit of 5:
> db.databaseName.find().limit(5)


**Filters:**
Find documents by username:
> db.databaseName.find({"username": "TestUser"})

Find documents by username AND first name:
> db.databaseName.find({"username": "TestUser", "firstName": "testing"})

Find documents by username OR first name:
> db.databaseName.find({"$or":[{"username": "TestUser"}, {"firstName": "Someone"}]})

Find documents by username in list ($nin -> not in):
> db.databaseName.find({"username": {"$in": ["TestUser", "Someone"]}})

Find documents where field exists (*"$exists": false* - where not):
> db.databaseName.find({"_class": {"$exists": true}})

Find documents with 2 followings:
> db.databaseName.find({"followings": {"$size": 2}})

Find documents by followings[2]:
> db.databaseName.find({"followings.2": "6090241bc6e6d715c9c7032d"}) 

Find count of followers by user id:
> db.databaseName.find({"followings": "6090241bc6e6d715c9c70380"}).count() 

**Logical operators**
$lt (<), $lte (<=), $gt (>), $gte (>=), $eq (==), $ne(!=)

Find a document by counter where counter == 5:
> db.databaseName.find({"counter": {$eq: 5}})


**Exclude fields**
Find a document excluding fields:
> db.databaseName.find({}, {"_id": 0, "followings": 0})

Find a document by username excluding id field:
> db.databaseName.find({"username": "TestUser"}, {"_id": 0})


**Sorting**

Find a document and sort by fields (1 ASC, -1 DESC), fields will be sorted by DOB and by a username 
if it found more than one record with the same DOB:
> db.databaseName.find({}, {"_id": 0, "followings": 0}).sort({"dateOfBirth": -1, "username": 1})


> db.users.find({$expr:{$lte:[{$size:"$followings"}, 3]}})
> db.databaseName.find({}, {"followings": 0}).sort({"followings": -1})