# Custom Docker image for Reasonly's Java code execution sandbox.
# Pre-installs Jackson so the generated Runner.java can parse any JSON data type.
FROM eclipse-temurin:24-jdk

# Download Jackson JARs into /libs/ so they are available on the classpath
RUN mkdir /libs
RUN wget -q -O /libs/jackson-core-2.17.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.17.2/jackson-core-2.17.2.jar
RUN wget -q -O /libs/jackson-databind-2.17.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.17.2/jackson-databind-2.17.2.jar
RUN wget -q -O /libs/jackson-annotations-2.17.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.17.2/jackson-annotations-2.17.2.jar

# Set /libs on the default classpath so javac and java can find Jackson automatically
ENV CLASSPATH="/libs/*:/app"

WORKDIR /app
