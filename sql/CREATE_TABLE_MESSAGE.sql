CREATE TABLE message (
    message_id     NUMBER NOT NULL,
    constraint     flight_id_pk PRIMARY KEY(message_id),
    text           BLOB,
    date_sent      TIMESTAMP,
    date_read      TIMESTAMP,
    user_from_id   NUMBER,
    CONSTRAINT fk_user_from_id FOREIGN KEY ( user_from_id )
        REFERENCES "user" ( user_id ),
    user_to_id     NUMBER,
    CONSTRAINT fk_user_to_id FOREIGN KEY ( user_to_id )
        REFERENCES "user" ( user_id )
);