FROM java:8-jre
WORKDIR /opt/user-service
ADD target/user-service.jar /opt/user-service/user-service.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "user-service.jar", "-Xms128m", "-Xmx256m"]