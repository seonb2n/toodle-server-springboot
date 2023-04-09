# toodle-server-springboot

tdd(Test Driven Development) 의 실습과 사용법을 학습하기 위해서 진행하고 있는 프로젝트입니다.

> 현재, 별도 서버에 배포되지는 않았습니다.
> 차후에, github actions 와 travis ci 를 사용해서 aws 상에 배포할 계획입니다.
> 현재는 docker image 로 만들어서 배포할 수 있는 Dockerfile 과 docker-compose 만 추가해둔 상태입니다.

Spring Data JPA 를 사용하며, DB 는 postgreSQL 을 사용하고 있습니다.
Spring Security 설정과 JWT 토큰을 통해서 현재, 사용자의 인증 여부를 체크하고 있습니다.
Junit5 를 사용한 단위 테스트를 꼼꼼히 작성했습니다. 
