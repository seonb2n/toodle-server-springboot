#ec2 ubuntu 에서 docker image 를 실행시키는 sh 파일

echo ">>> PULL DOCKER IMAGE"
docker pull 626ksb/toodle_server
docker pull 626ksb/toodle_frontend

echo ">>> DOCKER-COMPOSE UP"
docker-compose-up -d

echo ">>> CHECK IMAGES"
docker ps
