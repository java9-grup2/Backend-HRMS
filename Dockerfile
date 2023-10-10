
FROM amazoncorretto:19
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#==========Config Server Git==============
#docker build --build-arg JAR_FILE=config-git-server/build/libs/config-git-server-v.0.0.1.jar -t aktasberk/hrmsconfigservergit:v.0.2 .

#==========Auth Service==============
#docker build --build-arg JAR_FILE=auth-service/build/libs/auth-service-v.0.0.1.jar -t aktasberk/hrmsauth:v.0.3 .

#==========User Service==============
#docker build --build-arg JAR_FILE=user-service/build/libs/user-service-v.0.0.1.jar -t aktasberk/hrmsuser:v.0.3 .

#==========Company Service==============
#docker build --build-arg JAR_FILE=company-service/build/libs/company-service-v.0.0.1.jar -t aktasberk/hrmscompany:v.0.3 .

#==========Api Gateway Service==============
#docker build --build-arg JAR_FILE=api-gateway-service/build/libs/api-gateway-service-v.0.0.1.jar -t aktasberk/hrmsapigateway:v.0.3 .

