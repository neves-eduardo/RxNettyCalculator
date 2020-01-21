FROM java:8
WORKDIR /
ADD build/libs/tema-08.jar tema-08.jar
CMD java -jar tema-08.jar
EXPOSE 8081


