set -e

mvn clean package -Dmaven.test.skip=true

docker build -t accounts .

docker-compose up -d rabbitMq

sleep 40s

docker-compose up -d accounts

mvn test

docker-compose down