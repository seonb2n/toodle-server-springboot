-- 테스트 계정
insert into user_account (user_id, user_email, user_nickname, user_password, user_is_tmp_pwd) values
('2e7bafdc-18b7-423a-9a04-1edbce00c560', '626ksb@naver.com', 'sbKim', '{noop}1q2w3e4r!', false);

-- sbkim이 가진 포스트잇 카테고리
insert into tb_postit_category (postit_category_id, postit_category_client_id, created_at, created_by, modified_at, modified_by, postit_category_title, deleted, user_id)
values ('02e4dd01-a94a-4bd1-9069-7b3fdfee04fe', 'ac448216-995f-4e0f-b5d7-cb9a2967d3ba', now(), 'sbkim', now(), 'sbkim', '운동', false, '2e7bafdc-18b7-423a-9a04-1edbce00c560');

insert into tb_postit_category (postit_category_id, postit_category_client_id, created_at, created_by, modified_at, modified_by, postit_category_title, deleted, user_id)
values ('bd8e27f9-d5d5-4d7f-87c0-ec474828145f', '5d7a8bda-c988-44a5-b965-90a768e6f040', now(), 'sbkim', now(), 'sbkim', '생필품', false, '2e7bafdc-18b7-423a-9a04-1edbce00c560');


-- sbkim이 가진 포스트잇
insert into tb_postit (postit_id, postit_client_id, created_at, created_by, modified_at, modified_by, postit_content, postit_is_done, user_id, postit_category_id)
values ('862f028c-8094-4f8b-a275-cd8c4e3a75e9', '1e20bfaa-98c6-487f-b761-4aa6d522b02a', now(), 'sbkim', now(), 'sbkim', '하체 운동하기!', false, '2e7bafdc-18b7-423a-9a04-1edbce00c560', '02e4dd01-a94a-4bd1-9069-7b3fdfee04fe');

insert into tb_postit (postit_id, postit_client_id, created_at, created_by, modified_at, modified_by, postit_content, postit_is_done, user_id, postit_category_id)
values ('44240e8a-eaab-4a8f-831a-87a7e8fdc0b6', 'b6b703a3-1646-4d9a-9c40-3ee6f2bf8806', now(), 'sbkim', now(), 'sbkim', '저녁 식재료 사기!', false, '2e7bafdc-18b7-423a-9a04-1edbce00c560', 'bd8e27f9-d5d5-4d7f-87c0-ec474828145f');

-- sbkim 이 가진 프로젝트
insert into tb_project (project_id, created_at, created_by, modified_at, modified_by, project_name, project_color, user_id)
values ('1017449f-daf1-4c03-9bd9-1d157c90b61f', now(), 'sbkim', now(), 'sbkim', '프로젝트 1', '#DAD9FF', '2e7bafdc-18b7-423a-9a04-1edbce00c560');

-- sbkim 이 가진 테스크
insert into tb_task (task_id, created_at, created_by, modified_at, modified_by, content, task_importance, project_id, user_id, task_start_at, task_end_at)
values ('d80b26ce-cfe8-460d-a0dd-0f7b45c2f5a4', now(), 'sbkim', now(), 'sbkim', '프로젝트 테스크 1',  'MIDDLE', '1017449f-daf1-4c03-9bd9-1d157c90b61f', '2e7bafdc-18b7-423a-9a04-1edbce00c560', now(), now());

-- sbkim이 가진 액션
insert into tb_action (action_id, created_at, created_by, modified_at, modified_by, action_content, action_due_date, actions_is_done, task_task_id, user_id)
values ('9418d173-3024-4fed-9e90-df823131cc35', now(), 'sbkim', now(), 'sbkim', '프로젝트 액션 1', NOW() + INTERVAL '3' HOUR, false, 'd80b26ce-cfe8-460d-a0dd-0f7b45c2f5a4', '2e7bafdc-18b7-423a-9a04-1edbce00c560');
