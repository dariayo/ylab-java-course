FROM tomcat:10.1.1
LABEL maintainer="satheeskumar.pmp@gmail.com"

ADD target/ylab-1.0-SNAPSHOT.jar /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]