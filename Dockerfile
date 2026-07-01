# Image to run the suite inside CI / a container, pointed at a Selenium Grid.
FROM maven:3.9-eclipse-temurin-17

WORKDIR /automation
COPY pom.xml .
# Pre-fetch dependencies for faster, cacheable rebuilds
RUN mvn -B dependency:go-offline

COPY . .

# remote=true makes DriverFactory talk to the Grid defined by GRIDURL
ENV REMOTE=true
ENV HEADLESS=true

ENTRYPOINT ["mvn", "clean", "test"]
CMD ["-P", "parallel", "-Dremote=true", "-Dgridurl=http://selenium-hub:4444/wd/hub"]
