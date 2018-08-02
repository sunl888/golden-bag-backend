FROM node:8-alpine as builder
# 替换国内镜像
COPY ./deploy/source.list /etc/apk/repositories
RUN apk update && apk --no-cache add git
RUN cd / && git clone --depth=1 https://github.com/tw1997/golden-bag-react.git code
RUN cd /code && npm install --registry=https://registry.npm.taobao.org \
    && npm run build


FROM maven:3.5.4-jdk-8-alpine
COPY . /app
COPY --from=builder /code/dist /app/src/main/resources/static
CMD ["nginx", "-g", "daemon off;"]
