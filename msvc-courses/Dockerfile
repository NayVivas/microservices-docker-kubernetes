FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/msvc-courses

COPY ./pom.xml /app
COPY ./msvc-courses/.mvn ./.mvn
COPY ./msvc-courses/mvnw ./
COPY ./msvc-courses/pom.xml ./

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/ #solo descarga o empaqueta dependencias se salta test y main y rm -r ./target/ elimina la carpeta target
#RUN ./mvnw dependency:go-offline se puede utilizar tambien por la linea de arriba pero es mejor la primera instruccion
COPY ./msvc-courses/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/msvc-courses/target/msvc-courses-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

#ENTRYPOINT ["java", "-jar", "msvc-courses-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "msvc-courses-0.0.1-SNAPSHOT.jar"]
#permite cambiar la entrada, para entrar en modo interactivo



