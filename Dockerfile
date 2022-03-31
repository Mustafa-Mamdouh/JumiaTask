FROM openjdk:8
ADD ./number-validator/target/number-validator-0.0.1-SNAPSHOT.jar jumia.jar
ENTRYPOINT ["java", "-jar","jumia.jar"]
