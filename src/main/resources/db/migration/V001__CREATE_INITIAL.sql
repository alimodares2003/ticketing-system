CREATE TABLE users
(
    id        BIGINT       NOT NULL,
    email     VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    firstname VARCHAR(255),
    lastname  VARCHAR(255),
    role      VARCHAR(255),
    cdt       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    udt       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT USERS_PKEY PRIMARY KEY (id)
);

CREATE TABLE tickets
(
    id          BIGINT       NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    status      INTEGER,
    user_id     BIGINT       NOT NULL,
    cdt         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    udt         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT TICKETS_PKEY PRIMARY KEY (id)
);

CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

ALTER TABLE users
    ADD CONSTRAINT unique_user_email UNIQUE (email);

ALTER TABLE tickets
    ADD CONSTRAINT fk_ticket_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;