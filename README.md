# TV-API : TV Scheduler

Simple TV scheduler, a backend Rest API, using Java, Spring, Spring boot and m2 in memory DB.

### Run the project
```
git clone https://github.com/shnizman/TV-Scheduler.git
cd TV-Scheduler/TVScheduler
```

- Clean and build the project, run the command:
```
mvn install
```
This will  generate a jar file with all the dependencies which we will run once
it has been created.
- Run the `jar` file by using the following command 
```
java -jar target/TVScheduler.jar
```
- Alternatively, you can run the `main` method in `TVScheduler.java` in your chosen IDE, e.g. `IntelliJ`


## Usage


### Add new TV show to scheduler
#### Request

id - The requested TV Show Id from [TVMaze API](https://www.tvmaze.com/api#shows)

```
POST http://localhost:8080/api/tvSchedule/tvShow?id={id}
Accept: application/json
Content-Type: application/json
```



#### Response

```json
200 OK
{
    "id": 1,
    "name": "Under the Dome",
    "image": "http://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg",
    "cast": [
        {
            "id": 7,
            "name": "Mackenzie Lintz",
            "image": "http://static.tvmaze.com/uploads/images/original_untouched/3/7816.jpg"
        },
        {
            "id": 9,
            "name": "Dean Norris",
            "image": "http://static.tvmaze.com/uploads/images/original_untouched/163/408986.jpg"
        }
    ]
}
```
```json
404 Not Found
{
    "timestamp": "2020-09-05T17:18:26.631+0000",
    "status": 404,
    "error": "Not Found",
    "message": "404 Not Found",
    "path": "/api/tvSchedule/tvShow"
}
```
```json
400 Bad Request
{
    "timestamp": "2020-09-05T17:19:22.723+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "Required int parameter 'id' is not present",
    "path": "/api/tvSchedule/tvShow"
}
```
```json
500 Internal Server Error
{
    "timestamp": "2020-09-05T17:19:22.723+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Error While Trying To Connect To Db",
    "path": "/api/tvSchedule/tvShow"
}
```

### Delete TV show from scheduler
#### Request

id - The requested TV Show Id from [TVMaze API](https://www.tvmaze.com/api#shows)

```
DELETE http://localhost:8080/api/tvSchedule/tvShow?id={id}
Accept: application/json
Content-Type: application/json
```

#### Response

```json
200 OK
```
```json
404 NOT FOUND
{
    "timestamp": "2020-09-05T18:28:03.706+0000",
    "status": 404,
    "error": "Not Found",
    "message": "Tv Show Not found",
    "path": "/api/tvSchedule/tvShow"
}
```
```json
400 Bad Request
{
    "timestamp": "2020-09-05T17:19:22.723+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "Required int parameter 'id' is not present",
    "path": "/api/tvSchedule/tvShow"
}
```
```json
500 Internal Server Error
{
    "timestamp": "2020-09-05T17:19:22.723+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Error While Trying To Connect To Db",
    "path": "/api/tvSchedule/tvShow"
}
```

### Get All scheduled TV shows
#### Request

```
GET http://localhost:8080/api/tvSchedule/tvShows
Accept: application/json
Content-Type: application/json
```

#### Response

```json
200 OK
[
    {
        "id": 1,
        "name": "Under the Dome",
        "image": "http://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg",
        "cast": [
            {
                "id": 7,
                "name": "Mackenzie Lintz",
                "image": "http://static.tvmaze.com/uploads/images/original_untouched/3/7816.jpg"
            }
        ]
    }
]
```
```json
500 Internal Server Error
{
    "timestamp": "2020-09-05T17:19:22.723+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Error While Trying To Connect To Db",
    "path": "/api/tvSchedule/tvShow"
}
```
### Get All unwatched scheduled TV shows
#### Request

```
GET http://localhost:8080/api/tvSchedule/tvShows/unwatched
Accept: application/json
Content-Type: application/json
```

#### Response

```json
200 OK
[
    {
        "id": 1,
        "name": "Under the Dome",
        "image": "http://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg",
        "cast": [
            {
                "id": 7,
                "name": "Mackenzie Lintz",
                "image": "http://static.tvmaze.com/uploads/images/original_untouched/3/7816.jpg"
            }
        ],
        "firstUnwatchedEpisode": {
            "id": 1,
            "name": "Pilot",
            "season": 1,
            "number": 1,
            "airDate": "2013-06-24T00:00:00.000+0000"
        }
    }
]
```
```json
500 Internal Server Error
{
    "timestamp": "2020-09-05T17:19:22.723+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Error While Trying To Connect To Db",
    "path": "/api/tvSchedule/tvShow"
}
```
### Mark an episode as watched
#### Request

```
PUT http://localhost:8080/api/tvSchedule/episode?id={id}
Accept: application/json
Content-Type: application/json
```

#### Response

```json
200 OK
```
```json
404 NOT FOUND
{
    "timestamp": "2020-09-05T18:28:03.706+0000",
    "status": 404,
    "error": "Not Found",
    "message": "Tv Show Not found",
    "path": "/api/tvSchedule/tvShow"
}
```
```json
500 Internal Server Error
{
    "timestamp": "2020-09-05T17:19:22.723+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Error While Trying To Connect To Db",
    "path": "/api/tvSchedule/tvShow"
}
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.




