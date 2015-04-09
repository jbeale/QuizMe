# QuizWhiz Server


This directory contains all server-related components for QuizWhiz, as well as web components (in Spring MVC).

### Components
- /src : The directory for all things having to do with Java (that will go in the war file)
- /socket : The Node.js project for the Socket.IO server


### Prerequisites

Building QuizWhiz Server requires the following:
- Java JRE/JDK 7 or 8
- Apache Maven
- Node.js
- Compass (SCSS Compiler)
- Tomcat 7 or 8
- MySQL Server
- Patience and a sound mind

I developed using IntelliJ Idea 13 Ultimate. Eclipse would work too but why would you want to use that?

### Building Core Services (REST API)

1. IN THIS DIRECTORY, run the Maven build:
```sh
mvn clean install
```
2. After the build completes and is successful, deploy the war file by copying it to tomcat's webapps directory.
```sh
cp target/quizme.war /path/to/tomcat7/webapps/ROOT.war
```
3. It won't work right now because you still haven't imported the MySQL Database. See below.

### Building and Running Socket Server

**LE NOTE: The socket server runs on port 3001. Either open that port, or setup an Apache proxy to it.**

```sh
cd socket
npm install
./start.sh
```

To run without binding up the shell
```sh
./start.sh > socket.log &
```

### Configuring Core Services
Core services uses a configuration file on start to determine some of its settings that change per environment
(for example, MySQL credentials). This file must live at /opt/quizme/config.properties (on Windows this is C:/opt/quizme/config.properties)

Here are sample contents:
```properties
dataSource.url=jdbc:mysql://localhost:3306/quizme
dataSource.username=root
dataSource.password=password
interactionServer.baseUrl=http://localhost:3001
```

Set dataSource fields to match connection credentials for your local mysql server. Set interactionServer.baseUrl to be
the EXTERNAL URL for accessing the quizzing socket server.

### Importing MySQL Data

The most recent MySQL Schema dump lives in the **db** directory. It should be imported preferably using MySQL Workbench
but theoretically it's just SQL and it should execute the same regardless of the tool used, so long as the server is MySQL 5.

