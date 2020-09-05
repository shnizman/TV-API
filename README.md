# TV-API : TV Scheduler

Simple TV scheduler, a backend Rest API, using Java, Spring, Spring boot and m2 in memory DB

## Installation

Use the package manager [pip](https://pip.pypa.io/en/stable/) to install foobar.

```bash
pip install foobar
```

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

```
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
```
404 NOT FOUND
{
    "timestamp": "2020-09-05T17:18:26.631+0000",
    "status": 404,
    "error": "Not Found",
    "message": "404 Not Found",
    "path": "/api/tvSchedule/tvShow"
}
```
```
400 Bad Request
{
    "timestamp": "2020-09-05T17:19:22.723+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "Failed to convert value of type 'java.lang.String' to required type 'int'",
    "path": "/api/tvSchedule/tvShow"
}
```
```
500 Internal Server Error
{
    "timestamp": "2020-09-05T17:19:22.723+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Error While Trying To Connect To Db",
    "path": "/api/tvSchedule/tvShow"
}
```
```
1.	Adding Tv show to my schedule, POST request:
     a.	http://localhost:8080/api/tvSchedule/tvShow?id=X
     b.	Assumption: will return an object in the body response
2.	Removing Tv show from my schedule, DELETE request:
     a.	http://localhost:8080/api/tvSchedule/tvShow?id=X
     b.	Assumption: will not return an object in the body response
3.	Getting Tv shows in my schedule, GET request:
     a.	http://localhost:8080/api/tvSchedule/tvShows
4.	Getting Tv shows in my schedule with unwatched episode, GET request:
     a.	http://localhost:8080/api/tvSchedule/tvShows/unwatched
5.	Marking an episode as watched, PUT request:
     a.	http://localhost:8080/api/tvSchedule/episode?id=X
     b.	Assumption: will not return an object in the body response

```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


---

