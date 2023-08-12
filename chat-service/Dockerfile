FROM ghcr.io/graalvm/graalvm-ce:latest as graalvm
WORKDIR /app
COPY . /app
ENV PORT=8085
RUN gu install native-image
RUN apt-get update && apt-get install -y maven
RUN mvn package
RUN native-image --no-server -jar target/chat-service-0.0.1-SNAPSHOT.jar
FROM debian:bullseye-slim
COPY --from=graalvm /app/chat-service-0.0.1-SNAPSHOT /app/chat-service-0.0.1-SNAPSHOT
EXPOSE $PORT
CMD ["/app/chat-service-0.0.1-SNAPSHOT"]