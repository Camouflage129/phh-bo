FROM camouflage129/base:0.1
RUN apk update
RUN apk add busybox-extras
COPY target/bo-0.1.jar /home/phh-bo.jar
CMD nohup java -jar /home/phh-bo.jar 1> /dev/null 2>&1
EXPOSE 9000