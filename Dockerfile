FROM openjdk:11
EXPOSE 8080
ADD target/creditcardservice-0.0.1-SNAPSHOT.jar creditcardservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "-jar", "creditcardservice-0.0.1-SNAPSHOT.jar"]