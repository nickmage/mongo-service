****Collection operations****

Create / switch to a collection (must be invoked before any other command execution): 
> use collectionName

Create collection for a database: 
> db.createCollection("collectionName")

Delete collection from a database: 
> db.collectionName.drop()


****CRUD operations****

**Create operations**

Create a document:
> db.collectionName.insertOne({
"username": "TestUser",
"firstName": "testing",
"lastName": "Test",
"city": "Testville",
"dateOfBirth": new Date("1990-10-10T22:00:00.000Z"),
"enabled": true,
"followings": ["6090241bc6e6d715c9c70358", "6090241bc6e6d715c9c70389", "60902374c6e6d715c9c70283", "6090241bc6e6d715c9c70339", "6090241bc6e6d715c9c703ae", "6090241bc6e6d715c9c703a9"]
})

Create several documents:
> db.collectionName.insertMany([{
"username": "TestUser",
"firstName": "testing",
"lastName": "Test",
"city": "Testville",
"dateOfBirth": new Date("1990-10-10T22:00:00.000Z"),
"enabled": true,
"followings": ["6090241bc6e6d715c9c70358", "6090241bc6e6d715c9c70389", "60902374c6e6d715c9c70283", "6090241bc6e6d715c9c70339", "6090241bc6e6d715c9c703ae", "6090241bc6e6d715c9c703a9"]
}, 
{...} 
...])


***Read operations***
Find all documents:
> db.collectionName.find()

Find documents with a limit of 5:
> db.collectionName.find().limit(5)


*Filters:*
Find documents by username:
> db.collectionName.find({"username": "TestUser"})

Find documents by username AND first name:
> db.collectionName.find({"username": "TestUser", "firstName": "testing"})

Find documents by username OR first name:
> db.collectionName.find({"$or":[{"username": "TestUser"}, {"firstName": "Someone"}]})

Find documents by username in list ($nin -> not in):
> db.collectionName.find({"username": {"$in": ["TestUser", "Someone"]}})

Find documents where field exists (*"$exists": false* - where not):
> db.collectionName.find({"_class": {"$exists": true}})

Find documents with 2 followings:
> db.collectionName.find({"followings": {"$size": 2}})

Find documents by followings[2]:
> db.collectionName.find({"followings.2": "6090241bc6e6d715c9c7032d"}) 

Find count of followers by user id:
> db.collectionName.find({"followings": "6090241bc6e6d715c9c70380"}).count() 

Find documents that contain an array field with at least one element that matches all the specified query criteria:
> db.collectionName.find({"followings": {"$elemMatch": {"$eq": "6090241bc6e6d715c9c7037f"}}})

Find documents where followings size <= 3:
> db.collectionName.find({$expr:{$lte:[{$size:"$followings"}, 3]}})

**Logical operators**
$lt (<), $lte (<=), $gt (>), $gte (>=), $eq (==), $ne(!=)

Find a document by counter where counter == 5:
> db.collectionName.find({"counter": {$eq: 5}})


**Exclude fields**
Find a document excluding fields:
> db.collectionName.find({}, {"_id": 0, "followings": 0})

Find a document by username excluding id field:
> db.collectionName.find({"username": "TestUser"}, {"_id": 0})


**Sorting**

Find a document and sort by fields (1 ASC, -1 DESC), fields will be sorted by DOB and by a username 
if it found more than one record with the same DOB:
> db.collectionName.find({}, {"_id": 0, "followings": 0}).sort({"dateOfBirth": -1, "username": 1})


***Update operations***

Update single document by criteria (if multiple documents match filters only the first will be updated):
> db.collectionName.updateOne({"username": "TestUser"}, {"$set": {"firstName": "Testing"}})

Update multiple documents by criteria:
> db.collectionName.updateMany({"city": "Testville"}, {"$set": {"city": "Testburg"}})

Replace single document by criteria:
> db.collectionName.replaceOne({"username": "TestUser"}, {
"username": "TestUser",
"firstName": "testing",
"lastName": "Test",
"city": "Testville",
"dateOfBirth": new Date("1990-10-10T22:00:00.000Z"),
"enabled": true,
"followings": []
})

***Delete operations***

Delete single document by criteria
> db.collectionName.deleteOne({
dateOfBirth: {
$gte: ISODate("1990-10-10T22:00:00.000+00:00"),
$lt: ISODate("2005-10-10T22:00:00.000+00:00")
}
})

Delete multiple documents by criteria:
> db.collectionName.deleteMany({
dateOfBirth: {
$gte: ISODate("1990-10-10T22:00:00.000+00:00"),
$lt: ISODate("2005-10-10T22:00:00.000+00:00")
}
})

***Bulk operations***

Bulk write:
> db.collectionName.bulkWrite([
{"inertOne": { 
"document": {"username": "TestUser",
"firstName": "testing",
"lastName": "Test",
"city": "Testville",
"dateOfBirth": new Date("1990-10-10T22:00:00.000Z"),
"enabled": true,
"followings": []}
}},
{"deleteOne":{
"filter": {"lastName": "Test"}
}},
{"updateOne":{
"filter": {"city": "Testville"},
"update": {"$set": {"city": "Testburg"}}
}},
{"updateOne":{
"filter": {"city": "Testville"},
"update": {"$set": {"city": "Testburg"}}
}},
{"replaceOne":{
"filter": {"lastName": "Test"},
"update": {"$set": {"city": "Testburg"}}
}}
])

> db.collectionName.find({}, {"followings": 0}).sort({"followings": -1})