set -e

mvn clean package -Dmaven.test.skip=true

docker build -t tokens .

docker-compose up -d rabbitMq

sleep 40s

docker-compose up -d tokens

mvn test

docker-compose down