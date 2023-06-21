#build 가 완료됐다는 전제하에

#docker daemon 실행이 선행되어야 함

#이전에 존재하던 이미지 삭제
echo ">>> REMOVE PAST IMAGE"
docker rmi $(docker images -q)

#backend build
echo ">>> BACKEND BUILD"
#docker buildx build --platform linux/arm64 --tag 626ksb/toodle_server .
docker build --tag 626ksb/toodle_server .
docker push 626ksb/toodle_server

#frontend build
echo ">>> FRONTEND BUILD"
cd C:/Users/seonbin/WebstormProjects/toodle_react
npm run build
#docker buildx build --platform linux/arm64 --tag 626ksb/toodle_frontend .
docker build --tag 626ksb/toodle_frontend .
docker push 626ksb/toodle_frontend