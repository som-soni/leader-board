# Leader Board POC
A proof-of-concept service that allows posting scores for users and also get **Top 'N'** users ranked based on the scores. The **Top 'N'** API has two variants one based on **'Mongo'** and the other one is based on **'Redis' Sorted Set**.

## Service Overview
The service exposes following endpoints.
* ### POST scores 
    Can be used to post a user's score.
* ### GET v1/top-n
    Returns **Top 'N'** users ranked based on the scores (descending). This endpoint returns the data from **Mongo** directly.
* ### GET v2/top-n
    This endpoint also **Top 'N'** users ranked based on the scores (descending) but uses **Redis Sorted Set**.

## Running the service
* Docker compose file is configured to starts Mongo, Redis in addition to the service.  Use following command to start the service on port 8080.

    ```
    docker-componse up --build app
    ```
* To post score for a user you can run the following CURL request. You'll have to replace <<user_id>> and <<score>>.

    ```
    curl --location --request POST 'http://localhost:8080/leader-boards/scores' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "userId" : "<<user_id>>",
        "score" : <<score>>   
    }'
    ```
* Execute following CURL request to get **Top 'N'**.  v1 API returns the data from Mongo while v2 API will return the data from Redis.

    ```
    curl --location --request GET 'http://localhost:8080/leader-boards/v1/top-n?limit=10'
    ```
    
    Sample Response
    ```
    [
        {
            "userId": "user1",
            "score": 10
        },
        {
            "userId": "user2",
            "score": 5
        }
    ]
    ```
