-- 테스트 계정
insert into user_account (user_id, user_nickname, user_email, user_password,  created_at, created_by, modified_at, modified_by) values
(1, 'sbkim@naver.com', 'sbKim', '{noop}1q2w3e4r!',  now(), 'TEST', now(), 'TEST');