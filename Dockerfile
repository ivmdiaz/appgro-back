FROM openjdk:11.0.1-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar", "&"]

#Variables de base de datos:
ENV SPRING_DATASOURCE_URL=
ENV SPRING_DATASOURCE_USERNAME=
ENV SPRING_DATASOURCE_PASSWORD=

#Credenciales MercadoPago
ENV PUBLIC_KEY=
ENV ACCESS_TOKEN= 

#Variables para SMTP
ENV APPGRO_SMTP_EMAIL=
ENV APPGRO_SMTP_PASSWORD=
ENV APPGRO_SMTP_HOST=
ENV APPGRO_SMTP_PORT=



