DROP TABLE IF EXISTS subscription;
DROP TABLE IF EXISTS subscription_product;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255)
);

CREATE TABLE subscription_product (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255),
                                      description VARCHAR(255),
                                      price INTEGER
);

CREATE TABLE subscription (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              product_id BIGINT NOT NULL,
                              start_date DATE,
                              end_date DATE,
                              status VARCHAR(20),
                              auto_renew BOOLEAN,
                              FOREIGN KEY (user_id) REFERENCES users(id),
                              FOREIGN KEY (product_id) REFERENCES subscription_product(id)
);
