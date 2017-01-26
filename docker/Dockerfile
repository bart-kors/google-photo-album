FROM tomcat:8.5.11-jre8
RUN rm $CATALINA_HOME/conf/context.xml
ADD context.xml $CATALINA_HOME/conf
ADD mysql-connector-java-5.1.40-bin.jar $CATALINA_HOME/lib
ADD google-photo-album.war $CATALINA_HOME/webapps