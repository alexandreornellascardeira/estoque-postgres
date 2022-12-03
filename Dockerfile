FROM openjdk:18
COPY ./target/estoque-*.jar /usr/local/bin/estoque-service.jar
RUN chmod +x /usr/local/bin/estoque-service.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/estoque-service.jar"]