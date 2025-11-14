docker-compose down
mvn clean package
docker build -t chat-app .
docker-compose up -d