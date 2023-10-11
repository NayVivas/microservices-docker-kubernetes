ARG MSVC_NAME=msvc-users
FROM openjdk:17-jdk-alpine as builder
ARG MSVC_NAME
#ARG argumentos en tiempos de construccion de la imagen, solo en dockerfile - no en codigo del proyecto
WORKDIR /app/$MSVC_NAME
COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw ./
COPY ./$MSVC_NAME/pom.xml ./
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/ #solo descarga o empaqueta dependencias se salta test y main y rm -r ./target/ elimina la carpeta target
#RUN ./mvnw dependency:go-offline se puede utilizar tambien por la linea de arriba pero es mejor la primera instruccion
COPY ./$MSVC_NAME/src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine
ARG MSVC_NAME
WORKDIR /app
RUN mkdir ./logs
ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/$MSVC_NAME-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=8001
ENV PORT $PORT_APP
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "msvc-users-0.0.1-SNAPSHOT.jar"]
#CMD ["java", "-jar", "msvc-users-0.0.1-SNAPSHOT.jar"] permite cambiar la entrada, para entrar en modo interactivo



