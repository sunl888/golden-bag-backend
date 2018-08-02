FROM node:8-alpine as builder
# 替换国内镜像
COPY ./deploy/source.list /etc/apk/repositories
RUN apk update && apk --no-cache add git
RUN cd / && git clone https://github.com/tw1997/golden-bag-react.git code
RUN cd /code && npm install --registry=https://registry.npm.taobao.org \
    && npm run build


FROM nginx:1.13-alpine
COPY --from=builder /code/dist /var/www
CMD ["nginx", "-g", "daemon off;"]
