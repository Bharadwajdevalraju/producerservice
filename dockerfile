FROM clojure
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY project.clj /usr/src/app/
RUN lein deps
COPY . /usr/src/app
RUN lein uberjar
RUN cd target/uberjar
CMD ["java", "-jar","producerservice-0.1.0-SNAPSHOT.jar"]
