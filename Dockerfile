FROM openjdk:11-jre
RUN mkdir -p /app/bin
COPY ./target/opentelemetry-app-a.jar /app/bin
RUN wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.3.1/opentelemetry-javaagent-all.jar
CMD java -Dotel.traces.exporter=jaeger \
         -Dotel.exporter.jaeger.endpoint=jaeger:14250 \
         -Dotel.service.name=OpenTelemetryAppA \
		 -Dapplication.home=/app/bin/ \
		 -Dapplication.name=OpenTelemetryAppA \
		 -javaagent:opentelemetry-javaagent-all.jar \
		 -jar \
		 /app/bin/opentelemetry-app-a.jar
