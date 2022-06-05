FROM node:16.14.0-alpine

RUN apk update && apk upgrade
RUN apk add nodejs
RUN rm -rf /var/cache/apk/*

COPY . /src
RUN cd /src; npm install
EXPOSE 8080
CMD ["node", "/src/server.js"]