FROM java:8

FROM tomcat:8.5.49
COPY jstl-1.2.jar /usr/local/tomcat/lib
COPY postgresql-42.2.8.jar /usr/local/tomcat/lib
COPY testcasesversion3_war.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]