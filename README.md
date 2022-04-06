# Number Vieweing Service

###### Requirment statement:
**Exercise**
Create a single page application in Java (Frameworks allowed) that uses the provided database (SQLite 3) to list and categorize country phone numbers.
Phone numbers should be categorized by country, state (valid or not valid), country code and number.
The page should render a list of all phone numbers available in the DB. It should be possible to filter by country and state. Pagination is an extra.

**Solution**
I came out with two approches for number validating,
- First: Validating on memorey using java regex api.
- Second: Validating on database side.

**Uesd Technologies:**

- Java 8
- Sqlite
- Angular13
- Spring Boot 2.6.2

**Prerequisites:**
- Java 8+
- mvn
- Docker(Optional)

**How To Run:**
1. Start application using any shell: 
	- Run locally by run script : run-project.sh
	- Run containerized by running script:  build-and-run-docker.sh
2. After running one of the above commands  navigate to:
```
http://localhost:8080/
```

## Web Interface
![alt text](https://raw.githubusercontent.com/Mustafa-Mamdouh/JumiaTask/master/front-end.png)

## Test Coverage
![alt text](https://raw.githubusercontent.com/Mustafa-Mamdouh/JumiaTask/master/test-coverage.png)
