# Holidu Search Challenge

This challenge is based on a example project provided by play.
https://github.com/playframework/play-java-ebean-example

## Requirements
- Java
- SBT

## Usage
1. Change the directory to the project
2. Execute `sbt`
3. After the project is loaded execute `run`
4. Open `localhost:9000` in your browser

## Coding
Usually you don't have to stop and start the application while you are implementing the features.
Just safe the code and refresh the page in the browser.
But in case you experience some weird issues you should stop the application (CTRL+D) and execute `run` again.
NOTE: CTRL+C will also stop sbt and you need to execute `sbt` again and afterwards `run`.

## Testing
There are some 
``` 
sbt
test-only com.holidu.HomeControllerFunctionalTest
test-only com.holidu.HomeControllerIntegrationTest
test-only com.holidu.SampleModelTest
```

## IDE
Intellij

## Sample
There is a SampleModel to demonstrate the usage of Ebean.
There is a SampleController to demonstrate the usage of routes and Elasticsearch.

```
# Get sample by id from the database
curl -XGET 'localhost:9000/sample/1'
``

```
# Add a new sample to the database (if you use Postman you need to specify the Content-Type header to application/json)
curl -XPOST -H "Content-Type: application/json" 'localhost:9000/sample' -d '{
    "id": 4,
    "name": "second sample"
}'
``

```
# Index a sample from the database to Elasticsearch
curl -XGET 'localhost:9000/sample/index/1'
``` 

```
# Search in Elasticsearch for samples with contain "first" in the name
curl -XGET 'localhost:9000/sample/search?name=first'
``` 

```
# Search documents in Elasticsearch directly where every document matches
curl -XPOST 'http://54.194.224.195:9200/sample_index/_search?pretty' -d '{
    "query": {
        "match_all": {}
    }
}'
```

```
# Search documents in Elasticsearch directly where the name contains 'first'
curl -XPOST 'http://54.194.224.195:9200/sample_index/_search?pretty' -d '{
    "query" : {
        "match": {
            "name": "first"
        }
    }`
}'
```

## Tasks

### `Ideas
prepare apartment database + indexing methods, give the task to add new fields to elasticsearch mapping

give empty evolution 3.sql and ask to add a field

add a button with "like" or "favorite", candidate needs to add field to database and to elasticsearch + api call to increment the favorite counter
based on the number of favorites/likes their could be  scoring function

add some bad styled code, wrong naming and bugs and see if the candidate touches them or not (we could state that improvements to the existing codes are allowed/wanted)

add apartment dto, map from model to dto, index dto to elasticsearch, create search controller
