#Reference image
FROM eclipse-temurin:21-jdk

MAINTAINER amina.mimouni@totalenergies.com
ENV JAVA_OPTS="--enable-preview"

ARG JAR_FILE=target/Tondeuse-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} Tondeuse-1.0-SNAPSHOT.jar

EXPOSE 8009

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /Tondeuse-1.0-SNAPSHOT.jar" ]