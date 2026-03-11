USE fake_person;

GRANT ALL PRIVILEGES ON fake_person.* TO 'user'@'%';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS postal_code (
    postal_code CHAR(4) NOT NULL,
    town_name VARCHAR(30) NOT NULL,
    PRIMARY KEY (postal_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS person_name (
    id BIGINT AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
