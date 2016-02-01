# web-park
This web application helps some park to look after the plants in the park. Also helps owners to give orders to their employees.

##Software requirements:
* jre 1.7 or above - [download](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Tomcat Application Server - [download](https://tomcat.apache.org/download-80.cgi)
* MySQL 5.4 or above - [download](https://dev.mysql.com/downloads/workbench/)
* MySQL Java Connector - [download](http://dev.mysql.com/downloads/connector/j/)
* Log4j library - [download](https://logging.apache.org/log4j/1.2/download.html)
* JSTL library - [download](https://jstl.java.net/download.html)

##Install:

1) Install all requirement software

2) clone this repo

```
git clone https://github.com/JackKarichkovskiy/web-park.git
```

3) Copy MySQL Connector, Log4j and JSTL libraries to the Tomcat environment - `<TOMCAT_HOME>/lib`

4) Build and deploy the project to the Tomcat Application Server

5) Well Done! You can access the application by http://localhost:8084/WebPark

###Usage:
This application allows for Owners to manage forester tasks(create, confirm, update) and for Foresters to accept tasks from Owners and do a report for them.
