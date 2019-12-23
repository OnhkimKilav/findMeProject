CREATE TABLE USERS (
    users_id               NUMBER NOT NULL,
    constraint            users_id_pk PRIMARY KEY(users_id),
    first_name            NVARCHAR2(25),
    last_name             NVARCHAR2(25),
    phone                 NVARCHAR2(25),
    country               NVARCHAR2(25),
    city                  NVARCHAR2(25),
    age                   NUMBER,
    date_registered       TIMESTAMP,
    date_last_active      TIMESTAMP,
    relationship_status   NVARCHAR2(50),
    religion              NVARCHAR2(25),
    school                NVARCHAR2(50),
    university            NVARCHAR2(50),
    messages_sent         NVARCHAR2(255),
    messages_received     NVARCHAR2(255)
);