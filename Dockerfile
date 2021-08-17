FROM openjdk:8
VOLUME /temp
EXPOSE 8084
ADD ./target/ms-fixedtermaccount-bank-0.0.1-SNAPSHOT.jar fixedtermaccount-service.jar
ENTRYPOINT ["java","-jar","/fixedtermaccount-service.jar"]