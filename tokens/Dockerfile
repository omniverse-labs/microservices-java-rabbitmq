FROM adoptopenjdk:11-jre-hotspot

COPY target/lib /usr/src/lib

COPY target/tokens-1.0.0-SNAPSHOT-runner.jar /usr/src

WORKDIR /usr/src

CMD java -Xmx64m \
    -jar tokens-1.0.0-SNAPSHOT-runner.jar