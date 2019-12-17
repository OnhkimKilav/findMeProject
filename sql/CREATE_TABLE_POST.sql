CREATE TABLE post (
    post_id          NUMBER NOT NULL,
    constraint       post_id_pk PRIMARY KEY(post_id),
    post             BLOB,
    date_posted      TIMESTAMP,
    user_posted_id   NUMBER,
    CONSTRAINT fk_user_posted_id FOREIGN KEY ( user_posted_id )
        REFERENCES "user" ( user_id )
);