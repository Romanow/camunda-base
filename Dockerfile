FROM amazoncorretto:17 AS builder

WORKDIR application
COPY . .

RUN ./gradlew clean assemble && \
    mv build/libs/camunda-base.jar application.jar && \
    java -Djarmode=layertools -jar application.jar extract

FROM amazoncorretto:17

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0"

WORKDIR application
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
