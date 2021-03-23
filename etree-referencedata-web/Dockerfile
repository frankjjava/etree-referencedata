FROM openjdk:8
ADD target/opendata.jar opendata.jar
VOLUME /tmp
EXPOSE 2243
ENV JAVA_OPTS=""
CMD java $JAVA_OPTS -jar opendata.jar

# to build: sudo docker build  -t 344662149554.dkr.ecr.ap-south-1.amazonaws.com/opendata:1.0 .
# to run: 
#    - first run the vdc-args.sh file from verteil-parent/one-off_scripts folder
#    - sudo docker run --rm -e JAVA_OPTS="$_JAVA_OPTIONS" -v /vdc:/vdc -p 2243:2243 344662149554.dkr.ecr.ap-south-1.amazonaws.com/opendata:1.0
# to push to ECR
#    - Run 'aws configure' just once - contact Frank for Access-key and Secret-key.
#    - first authentiate with AWS - '(aws ecr get-login --no-include-email --region ap-south-1)' if not yet authenticated.
#    - sudo docker push 344662149554.dkr.ecr.ap-south-1.amazonaws.com/opendata:1.0
