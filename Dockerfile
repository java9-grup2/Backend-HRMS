
FROM amazoncorretto:19
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#==========Config Server Git==============
#docker build --build-arg JAR_FILE=config-git-server/build/libs/config-git-server-v.0.0.1.jar -t aktasberk/hrmsconfigservergit:v.0.2 .

#==========Auth Service==============
#docker build --build-arg JAR_FILE=auth-service/build/libs/auth-service-v.0.0.1.jar -t aktasberk/hrmsauth:v.0.6 .

#==========User Service==============
#docker build --build-arg JAR_FILE=user-service/build/libs/user-service-v.0.0.1.jar -t aktasberk/hrmsuser:v.0.6 .

#==========Company Service==============
#docker build --build-arg JAR_FILE=company-service/build/libs/company-service-v.0.0.1.jar -t aktasberk/hrmscompany:v.0.5 .

#==========Api Gateway Service==============
#docker build --build-arg JAR_FILE=api-gateway-service/build/libs/api-gateway-service-v.0.0.1.jar -t aktasberk/hrmsapigateway:v.0.5 .

#==========Mail Service==============
#docker build --build-arg JAR_FILE=mail-service/build/libs/mail-service-v.0.0.1.jar -t aktasberk/hrmsmail:v.0.8 .

#==========Comment Service==============
#docker build --build-arg JAR_FILE=comment-service/build/libs/comment-service-v.0.0.1.jar -t aktasberk/hrmscomment:v.0.5 .

#==========Advance Payment Service==============
#docker build --build-arg JAR_FILE=advance-payment-service/build/libs/advance-payment-service-v.0.0.1.jar -t aktasberk/hrmsadvance:v.0.4 .

#==========Expense Service==============
#docker build --build-arg JAR_FILE=expense-service/build/libs/expense-service-v.0.0.1.jar -t aktasberk/hrmsexpense:v.0.4 .

#==========Financial Performance Service==============
#docker build --build-arg JAR_FILE=financial-performance-service/build/libs/financial-performance-service-v.0.0.1.jar -t aktasberk/hrmsfinancial:v.0.4 .

#==========Permission Service==============
#docker build --build-arg JAR_FILE=permission-service/build/libs/permission-service-v.0.0.1.jar -t aktasberk/hrmspermission:v.0.4 .

#==========Permission Service==============
#docker build --build-arg JAR_FILE=shifts-and-breaks-service/build/libs/shifts-and-breaks-service-v.0.0.1.jar -t aktasberk/hrmsshiftsandbreaks:v.0.4 .

#==========Upcoming Payment Service==============
#docker build --build-arg JAR_FILE=upcoming-payment-service/build/libs/upcoming-payment-service-v.0.0.1.jar -t aktasberk/hrmsupcomingpayment:v.0.4 .

