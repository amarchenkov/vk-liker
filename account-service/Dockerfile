FROM java:8-jre
WORKDIR /opt/account-service
EXPOSE 8091
ADD target/account-service.jar /opt/account-service/account-service.jar
ENTRYPOINT ["java", "-jar", "account-service.jar", "-Xms128m", "-Xmx256m"]