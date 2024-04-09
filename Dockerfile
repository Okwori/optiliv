# syntax = docker/dockerfile:1.2
FROM clojure:openjdk-17 AS build

WORKDIR /
COPY . /

#RUN clojure -Sforce -T:build all

FROM azul/zulu-openjdk-alpine:17

#COPY --from=build /target/optiliv-standalone.jar /optiliv/optiliv-standalone.jar
COPY /optiliv-standalone.jar /optiliv/optiliv-standalone.jar

EXPOSE $PORT

ENTRYPOINT exec java $JAVA_OPTS -jar /optiliv/optiliv-standalone.jar
