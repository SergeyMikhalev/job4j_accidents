FROM openjdk
WORKDIR job4j_accidents
ADD target/job4j_accidents-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT java -jar app.jar
