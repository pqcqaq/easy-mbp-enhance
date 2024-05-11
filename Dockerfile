FROM eclipse-temurin:17-jdk-jammy
COPY ./easy-mbp-enhance-1.0.0.jar /tmp/app.jar
EXPOSE 8090
# 设置时区
RUN echo 'Asia/Shanghai' > /etc/timezone
RUN mkdir /tmp/imgs
ENTRYPOINT java -jar -Xmx512M -Xms64M -Xss128M /tmp/app.jar --server.port=8090 --spring.profiles.active=prod --storage.local.path="/tmp/imgs"
