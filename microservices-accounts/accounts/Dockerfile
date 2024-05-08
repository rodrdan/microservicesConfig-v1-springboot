FROM openjdk:17-jdk-slim

#Information on who maintains the image
LABEL rodrdan.cz="dan"

#Add the application's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#Execute this command when generating a container
ENTRYPOINT ["java", "-jar", "/accounts-0.0.1-SNAPSHOT.jar"]