FROM java:8-jre
WORKDIR /opt/ui
ADD target/ui.jar /opt/ui/ui.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ui.jar", "-Xms128m", "-Xmx256m"]