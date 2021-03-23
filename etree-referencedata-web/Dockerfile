FROM openjdk:8
ADD target/referencedata.jar referencedata.jar
VOLUME /tmp
EXPOSE 2243
ENV JAVA_OPTS=""
CMD java $JAVA_OPTS -jar referencedata.jar

# to build: sudo docker build  -t frankjjava/referencedata:1.0 .
# to run: 
#    - sudo docker run --rm -p 2427:2427 frankjjava/referencedata:1.0
# to push to docker-hub
#    - first authentiate - docker login --username=yourhubusername --email=youremail@company.com
#    - sudo docker push frankjjava/referencedata:1.0
