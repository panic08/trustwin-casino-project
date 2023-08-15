FROM ghcr.io/graalvm/graalvm-ce:latest as graalvm
WORKDIR /app
COPY . /app
ENV PORT=9001
RUN gu install native-image
RUN curl -fsSL -o /tmp/apache-maven.tar.gz https://archive.apache.org/dist/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz \
  && tar -xzf /tmp/apache-maven.tar.gz -C /tmp \
  && mv /tmp/apache-maven-3.8.4 /usr/share/maven \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

RUN mvn -Pnative -DskipTests package

FROM ubuntu:latest
RUN apt-get update && apt-get install -y libgomp1
COPY --from=graalvm /app/target/game-overgo-service /app/game-overgo-service
EXPOSE $PORT
CMD ["/app/game-overgo-service"]
