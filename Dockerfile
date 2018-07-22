FROM maven:3.5.4-jdk-8-alpine

#COPY ./source.list /etc/apk/repositories

#RUN apk update && apk --no-cache add shadow
# grant all privileges to www-data
#ARG USERNAME=sunlong
#ARG USERGROUP=sunlong
#RUN groupadd -r ${USERGROUP} && useradd -r -g ${USERGROUP} ${USERNAME}

# grant all privileges to current user
#ARG USERID=1000
#RUN apk --no-cache add shadow && useradd -r -d /var sunlong && usermod -u 1000 sunlong
#RUN su && chown -R 1000.1000 /var

#ENV USERNAME=sunlong
#RUN apk --no-cache add shadow && useradd -u 1000 -s /bin/bash "${USERNAME}"
#USER "${USERNAME}"
