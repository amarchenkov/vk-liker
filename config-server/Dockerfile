FROM java:8-jre
WORKDIR /opt/configserver
ADD target/config-server.jar /opt/configserver/config-server.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "config-server.jar"]