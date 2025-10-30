-- Subscriber (구독자)
INSERT INTO subscriber (email, status)
VALUES ('als000825@naver.com', 'SUBSCRIBE');


-- Theme (주제들 먼저)
INSERT INTO theme (NAME) VALUES ('경제');
INSERT INTO theme (NAME) VALUES ('IT');
INSERT INTO theme (NAME) VALUES ('스포츠');

-- SubscribeTheme (중간 테이블, FK 로 subscriber_id + theme_id 참조)
INSERT INTO subscribe_theme (SUBSCRIBER_ID, THEME_ID) VALUES (1, 1);
INSERT INTO subscribe_theme (SUBSCRIBER_ID, THEME_ID) VALUES (1, 2);
