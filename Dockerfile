# İlk olarak kullanılan JDK seçilir
FROM amazoncorretto:19

# JAR dosyasının docker'a kopyalanması
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Jarı çalıştırma komutu
ENTRYPOINT ["java", "-jar", "/app.jar"]

# docker build --build-arg JAR_FILE=config-git-server/build/libs/config-git-server-v.0.0.1.jar -t leventtarik/hrmsconfigservergit:v.0.1 .
# docker build --build-arg JAR_FILE=api-gateway-service/build/libs/api-gateway-service-v.0.0.1.jar -t leventtarik/hrmsgatewayservice:v.0.1 .
#
# docker build --build-arg JAR_FILE=auth-service/build/libs/auth-service-v.0.0.1.jar -t leventtarik/hrmsauthservice:v.0.1 .