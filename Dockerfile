FROM maven:3.8.4-jdk-11 as builder

COPY . /home/app/
WORKDIR /home/app/
RUN mvn install

FROM openjdk:11

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

COPY --from=builder /home/app/target/leader-board-1.0-SNAPSHOT.jar /app.jar
