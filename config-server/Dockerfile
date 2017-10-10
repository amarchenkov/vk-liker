FROM java:8-jre
WORKDIR /opt/config-server
ADD target/config-server.jar /opt/config-server/config-server.jar
EXPOSE 8888
ENV GIT_LOGIN ""
ENV GIT_PASSWORD ""
ENV GIT_URI ""
ENV SPRING_PROFILES_ACTIVE "production"
ENTRYPOINT ["java", "-jar", "config-server.jar"]