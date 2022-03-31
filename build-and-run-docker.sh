mvn clean package -f ./number-validator -DskipTests
docker build -t jumia/task.v1 .
docker run -p 8080:8080 jumia/task.v1