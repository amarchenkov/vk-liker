FROM java:8-jre
WORKDIR /opt/group-service
EXPOSE 8094
ADD target/group-service.jar /opt/group-service/group-service.jar
ENTRYPOINT ["java", "-jar", "group-service.jar", "-Xms128m", "-Xmx256m"]