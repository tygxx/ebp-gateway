FROM registry.yy.com:5000/centos-jre:8
VOLUME /app/tmp
ADD config /app/config
ADD ebp-gateway-0.0.1.jar /app/ebp-gateway.jar
ADD lib /app/lib
RUN sh -c 'touch /app/ebp-gateway.jar'
ENV JAVA_OPTS="-XX:MaxPermSize=50MB"
WORKDIR /app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar ebp-gateway.jar"]