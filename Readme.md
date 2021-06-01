###Collection operations

Create / switch to a collection (must be invoked before any other command execution): 
> use collectionName

Create collection for a database: 
> db.createCollection("collectionName")

Delete collection from a database: 
> db.collectionName.drop()


###CRUD operations

***Create operations***

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


**Filters:**
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
> db.collectionName.count({"followings": "6090241bc6e6d715c9c70380"})

Find unique values by field:
> db.collectionName.distinct("dateOfBirth")

Find amount of unique values by field:
> db.collectionName.distinct("dateOfBirth").length

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


###Indexes

***Types***

1. Single Field (may contain single field with dot notation access or a JSON object).
2. Compound object (may contain several fields).
3. Multikey (may contain arrays and is created automatically if any indexed field is an array).
4. Geospatial (created for 2d planar and 2d sphere geometry data).
5. Text (used for text searches).

***Index properties***

1. Unique index.
2. Partial index (indexes documents by a condition e.g. equality, field exists, $gt, $gte, $lt, $lte expressions, type expr. etc.).
3. Sparse index (contains only documents with at least one indexed field regardless its value).
4. TTL index (time-to-live -> sets a retention time of storing data in the database and automatically removes documents from a collection after a certain amount of time).
5. Hidden index (cannot be used in queries -> we can evaluate the potential impact of dropping an index without actually dropping it).


###Aggregations

***Pipeline***

Pipeline contains stages - autonomous operations with documents (similar to stream API).
> db.collectionName.aggregate([{$match:{"username": "Sir"}},{$addFields: {"a": "b"}}])

where $match and $addFields are stages, each stage makes its own operation.

Here's an example of aggregation where we get enabled users from Kharkiv and append age field to the result: 
> db.users.aggregate(
> [
>   { $match: { "enabled": true, "city": "Kharkiv" } }, 
>   { $addFields: 
>       { "age": {
>           $floor: {
>               $divide : [
>                   { $subtract: [new Date(), "$dateOfBirth"] },
>                   365 * 24 * 60 * 60 * 1000
>                   ]
>               }
>           } 
>       }
>   }
> ]
)
 
###Query optimization

1. Use indexes for read operations.
2. Consider query selectivity - more selective queries match a smaller percentage of documents, e.g. $nin and $ne operators are not very selective as they often match a large portion of the index.
3. Use covered query (search only by indexed field/s and exclude non-indexed fields).
4. To determine if a query is covered we can use explain call:
   > db.users.explain()
5. Use limit() to reduce number of documents in response:
   > db.users.find({"city": "Kyiv"}).limit(25)
6. Use projection to exclude unnecessary fields or include only required fields within response:
   > db.users.find({"city": "Kyiv"}, {"_id": 0})
   >  db.users.find({"city": "Kyiv"}, {"username": 1, "firstName": 1, "dateOfBirth": 1})
7. Use increment operator $inc instead of fetching a document, changing and updating in a database. It also prevents race conditions.