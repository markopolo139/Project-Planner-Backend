FROM ubuntu

RUN apt-get update && apt-get upgrade -y

RUN apt-get install openjdk-17-jdk -y

COPY build/libs/* project-overview-backend-1.0.jar

CMD ["java", "-jar", "project-overview-backend-1.0.jar"]