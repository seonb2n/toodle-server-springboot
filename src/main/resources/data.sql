-- 테스트 계정
insert into user_account (user_id, user_email, user_nickname, user_password,  created_at, created_by, modified_at, modified_by) values
(1, 'sbkim@naver.com', 'sbKim', '{noop}1q2w3e4r!',  now(), 'TEST', now(), 'TEST');

-- sbkim이 가진 포스트잇
insert into tb_postit (id, created_at, created_by, modified_at, modified_by, postit_content, postit_end_time, postit_is_done, user_id)
values (1, now(), 'sbkim', now(), 'sbkim', '운동하기!', NOW() + INTERVAL '1' DAY, false, 1);

insert into tb_postit (id, created_at, created_by, modified_at, modified_by, postit_content, postit_end_time, postit_is_done, user_id)
values (2, now(), 'sbkim', now(), 'sbkim', '저녁 사기!', NOW() + INTERVAL '2' DAY, false, 1);