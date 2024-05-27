DROP TABLE users IF EXISTS;

CREATE TABLE users
(
    id BIGINT,
    name VARCHAR(50) NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    password VARCHAR(150) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE login_history
(
    id bigint AUTO_INCREMENT NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    login_result BOOLEAN NOT NULL,
    failure_reason VARCHAR(50),
    ip_address VARCHAR(50) NOT NULL,
    user_agent VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
