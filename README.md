# toodle-server-springboot

tdd(Test Driven Development) 의 실습과 사용법을 학습하기 위해서 진행하고 있는 프로젝트입니다.

> 현재 github actions 와 docker 로 aws 상에 배포된 상태입니다.

Spring Data JPA 를 사용하며, DB 는 postgreSQL 을 사용하고 있습니다.
Spring Security 설정과 JWT 토큰을 통해서 현재, 사용자의 인증 여부를 체크하고 있습니다.
Junit5 를 사용한 단위 테스트를 꼼꼼히 작성했습니다. 

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
- ec2 volume 7GB -> 30GB 로 업데이트
- Test Coverage 90% 이상 유지
- JPA 사용
- EhCache 로 자주 들어오는 요청에 대해서는 캐싱 처리
- DB 인덱스 추가


> 테스트용 로그인 아이디
> ID : 626ksb@naver.com
> PW : 1q2w3e4r!

![image](https://github.com/seonb2n/toodle-server-springboot/assets/53749409/07bcb3d7-4018-4b06-8909-53ac9237e695)
![image](https://github.com/seonb2n/toodle-server-springboot/assets/53749409/9f41b3fd-c663-4d0b-962f-38233a0aaf1f)
![image](https://github.com/seonb2n/toodle-server-springboot/assets/53749409/d87ee6c5-731e-4c81-9143-1e4f9c968215)
![image](https://github.com/seonb2n/toodle-server-springboot/assets/53749409/231375bc-84d8-41c0-ab83-f041fbe3b5f7)
![image](https://github.com/seonb2n/toodle-server-springboot/assets/53749409/5a5e4a5d-dab1-46e3-bffb-1919d7051987)
![image](https://github.com/seonb2n/toodle-server-springboot/assets/53749409/4e5492c4-b3b4-44a1-bcd6-c69c69dd4874)
![image](https://github.com/seonb2n/toodle-server-springboot/assets/53749409/1ff7cf54-00e4-45dc-8d92-d0fc4ec3fa98)
