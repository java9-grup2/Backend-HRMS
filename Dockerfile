

FROM amazoncorretto:19
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#==========Config Server Git==============
#docker build --build-arg JAR_FILE=config-git-server/build/libs/config-git-server-v.0.0.1.jar -t aktasberk/hrmsconfigservergit:v.0.1 .
#==========Auth Service==============
#docker build --build-arg JAR_FILE=auth-service/build/libs/auth-service-v.0.0.1.jar -t aktasberk/hrmsauth:v.0.1 .

