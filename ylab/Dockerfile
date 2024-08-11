FROM maven:3.6.3-openjdk-17

RUN mkdir ylab

WORKDIR ylab

COPY . .

RUN mvn clean package 

CMD ["java", "-jar", "target/ylab-1.0-SNAPSHOT.jar"]