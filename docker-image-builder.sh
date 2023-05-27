#build 가 완료됐다는 전제하에

#이전에 존재하던 이미지 삭제
docker rmi $(docker images -q)

#backend build
docker buildx build --platform linux/arm64 --tag 626ksb/toodle_server .
docker push 626ksb/toodle_server

#frontend build
cd C:\Users\seonbin\WebstormProjects\toodle_react
npm run build
docker buildx build --platform linux/arm64 --tag 626ksb/toodle_frontend .
docker push 626ksb/toodle_frontend