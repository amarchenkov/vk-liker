FROM java:8-jre
EXPOSE 8095
WORKDIR /opt/api-gateway
ADD target/api-gateway.jar /opt/api-gateway/api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar", "-Xms128m", "-Xmx256m"]