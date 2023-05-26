# toodle-server-springboot

tdd(Test Driven Development) 의 실습과 사용법을 학습하기 위해서 진행하고 있는 프로젝트입니다.

> 현재 github actions 와 docker 로 aws 상에 배포된 상태입니다.

Spring Data JPA 를 사용하며, DB 는 postgreSQL 을 사용하고 있습니다.
Spring Security 설정과 JWT 토큰을 통해서 현재, 사용자의 인증 여부를 체크하고 있습니다.
Junit5 를 사용한 단위 테스트를 꼼꼼히 작성했습니다. 


http://54.180.54.238:8300/login

- 회원 가입 기능 구현
- 포스트잇 관리 기능 구현
- 이메일을 통해 계정 비밀번호 찾기 기능 구현
- JWT 토큰을 통한 사용자 인증 기능 구현
- 네이버 간편 회원가입 및 로그인 기능(배포 전)
- Redis 를 세션 저장소로 사용자 세션 ID 관리(배포 전)
