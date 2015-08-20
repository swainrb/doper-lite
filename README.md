Libraries used:
- Java Jersey
- Guava
- Jackson

Base URL
localhost:8080

Endpoints

GET /jobs
- Get all registered jobs

POST /jobs
- Register a job
Body:
{
"total": 10,
"progress": 3
}

GET /jobs/ids/{id}
- Get job by id

PUT /jobs/ids/{id}?updatetype=increment:absolute
- Update the progress by an increment or to an absolute value
Body:
{
"progress": 3
}