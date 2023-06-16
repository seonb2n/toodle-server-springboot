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
- 네이버 간편 회원가입 및 로그인 기능 추가
- Redis 를 세션 저장소로 사용자 세션 ID 관리
- 프로젝트 / 태스크 / 액션 단위로 작업 관리 기능 구현
- 프로젝트 추가 기능 구현
- 포스트잇 추가 기능 구현
- today 화면이 현재 시간에 맞춰서 보이도록 구현
- 사용자가 호출한 API, 요청, 응답에 대한 DB 로깅
- Redis 를 사용한 사용자 호출 API 제한 설정(1분당 동일 IP 로 10건)

> 테스트용 로그인 아이디
> ID : 626ksb@naver.com
> PW : 1q2w3e4r!
