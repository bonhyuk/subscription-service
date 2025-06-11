DELETE FROM subscription_product;
-- 구독 상품 데이터 삽입
INSERT INTO subscription_product (id, name, description, price) VALUES (1, '베이직 구독', '기본 구독 서비스', 10000);
INSERT INTO subscription_product (id, name, description, price) VALUES (2, '프리미엄 구독', '고급 구독 서비스', 20000);
INSERT INTO subscription_product (id, name, description, price) VALUES (3, '다이아 구독', '초고급 구독 서비스', 30000);
INSERT INTO subscription_product (id, name, description, price) VALUES (4, '챌린저 구독', '특급 구독 서비스', 40000);
-- 사용자 삽입
-- INSERT INTO users (id, username, password, email) VALUES (1, 'testuser', '{noop}1234', 'test@example.com');

-- 구독 삽입
INSERT INTO subscription (id, user_id, product_id, start_date, end_date, status)
VALUES (1, 1, 1, CURRENT_DATE, DATEADD('DAY', 30, CURRENT_DATE), 'ACTIVE');