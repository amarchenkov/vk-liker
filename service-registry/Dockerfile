FROM java:8-jre
WORKDIR /opt/service-registry
ADD target/service-registry.jar /opt/service-registry/config-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "service-registry.jar"]