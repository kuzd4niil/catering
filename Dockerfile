FROM openjdk:17-jdk-alpine
RUN apk add --no-cache bash git openssh tree
WORKDIR /app
RUN git clone https://github.com/kuzd4niil/demo_tomcat.git
WORKDIR demo_tomcat
RUN ./gradlew assemble
WORKDIR ..
RUN pwd skipcache
RUN ls
RUN tree demo_tomcat skipcache
RUN cp demo_tomcat/build/libs/demo-0.0.1-SNAPSHOT.jar /app/app.jar
RUN rm -rf demo_tomcat
ENTRYPOINT ["java", "-jar", "app.jar"]