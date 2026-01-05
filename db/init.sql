CREATE DATABASE IF NOT EXISTS springbook;
USE springbook;

CREATE TABLE users
(
    id        VARCHAR(10) PRIMARY KEY,
    name      VARCHAR(20) NOT NULL,
    password  VARCHAR(10) NOT NULL,
    email     VARCHAR(20) NOT NULL,
    level     TINYINT     NOT NULL,
    login     INT         NOT NULL,
    recommend INT         NOT NULL
);

-- testdb 데이터베이스 생성 및 테이블 복사
CREATE DATABASE IF NOT EXISTS testdb;
USE testdb;

CREATE TABLE users
(
    id        VARCHAR(10) PRIMARY KEY,
    name      VARCHAR(20) NOT NULL,
    password  VARCHAR(10) NOT NULL,
    email     VARCHAR(20) NOT NULL,
    level     TINYINT     NOT NULL,
    login     INT         NOT NULL,
    recommend INT         NOT NULL
);
