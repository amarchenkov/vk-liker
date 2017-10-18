FROM java:8-jre
WORKDIR /opt/content-service
EXPOSE 8092
ADD target/content-service.jar /opt/content-service/content-service.jar
ENTRYPOINT ["java", "-jar", "content-service.jar", "-Xms128m", "-Xmx256m"]