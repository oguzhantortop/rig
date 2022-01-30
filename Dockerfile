############################################################
# Dockerfile to build containers that run sluservice
# based on OpenJDK 
############################################################

# Set the base image to openjdk 
FROM adoptopenjdk/openjdk11:jdk-11.0.6_10-alpine-slim

# File Author / Maintainer
MAINTAINER Oguzhan T "oguzhantt@yahoo.com"

EXPOSE 8080
WORKDIR /rig/
COPY target/*.jar rig.jar

ENTRYPOINT ["java","-jar","./rig.jar"]
