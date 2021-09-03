FROM nginx:1.17.1-alpine as build-step
WORKDIR /app
COPY package.json ./
# install curl
RUN apk add --update curl
# install nodejs
RUN apk add --update nodejs npm
RUN npm install
COPY . .
EXPOSE 4200
CMD npm run start