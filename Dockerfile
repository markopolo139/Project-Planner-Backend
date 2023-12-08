FROM ubuntu

RUN apt-get update && apt-get upgrade -y

RUN apt-get install openjdk-17-jdk -y

RUN mkdir -p /backend

WORKDIR /backend

COPY build/libs/project-overview-backend-1.0.jar /backend/project-overview-backend-1.0.jar

CMD ["java", "-jar", "/backend/project-overview-backend-1.0.jar"]