FROM maven:3.5.4-jdk-8-alpine

#COPY ./source.list /etc/apk/repositories

#RUN apk update && apk --no-cache add shadow
# grant all privileges to www-data
#ARG USERNAME=sunlong
#ARG USERGROUP=sunlong
#RUN groupadd -r ${USERGROUP} && useradd -r -g ${USERGROUP} ${USERNAME}