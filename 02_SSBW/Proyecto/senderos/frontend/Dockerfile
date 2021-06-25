FROM node:14.9
 
WORKDIR /usr/src/app
 
COPY package*.json ./
 
RUN npm install
RUN npm install -g local-cors-proxy
 
COPY . .
 
EXPOSE 3000
 
CMD [ "npm", "start" ]