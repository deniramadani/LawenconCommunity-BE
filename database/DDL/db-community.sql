CREATE TABLE tb_role(
    id varchar(36) not null,
    role_code varchar(5) not null,
    role_name varchar(20) not null,
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    updated_by varchar(36),
    updated_at timestamp without time zone,
    versions int not null default 0,
    is_active boolean not null default true
);

ALTER TABLE
    tb_role
ADD
    CONSTRAINT role_pk primary key (id);

ALTER TABLE
    tb_role
ADD
    CONSTRAINT role_bk unique (role_code);

CREATE TABLE tb_industry(
    id varchar(36) not null,
    industry_name varchar(30) NOT NULL,
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    updated_by varchar(36),
    updated_at timestamp without time zone,
    versions int not null default 0,
    is_active boolean not null default true
);

ALTER TABLE
    tb_industry
ADD
    CONSTRAINT industry_pk primary key (id);

CREATE TABLE tb_position(
    id varchar(36) not null,
    position_name varchar(30),
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    updated_by varchar(36),
    updated_at timestamp without time zone,
    versions int not null default 0,
    is_active boolean not null default true
);

ALTER TABLE
    tb_position
ADD
    CONSTRAINT position_pk primary key (id);

CREATE TABLE tb_file(
    id varchar(36) not null,
    file_encode text,
    file_extensions varchar(5),
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    updated_by varchar(36),
    updated_at timestamp without time zone,
    versions int not null default 0,
    is_active boolean not null default true
);

ALTER TABLE
    tb_file
ADD
    CONSTRAINT file_pk primary key (id);

CREATE TABLE tb_social_media(
    id varchar(36) not null,
    social_media_name varchar(30) not null,
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    updated_by varchar(36),
    updated_at timestamp without time zone,
    versions int not null default 0,
    is_active boolean not null default true
);

ALTER TABLE
    tb_social_media
ADD
    CONSTRAINT socmed_pk primary key (id);

CREATE TABLE tb_user_type(
    id varchar(36) not null,
    user_type_code varchar(6) not null,
    user_type_name varchar(30) not null,
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    updated_by varchar(36),
    updated_at timestamp without time zone,
    versions int not null default 0,
    is_active boolean not null default true
);

ALTER TABLE
    tb_user_type
ADD
    CONSTRAINT user_type_pk primary key (id);

ALTER TABLE
    tb_user_type
ADD
    CONSTRAINT user_type_bk unique (user_type_code);

CREATE TABLE tb_user(
    id varchar(36) not null,
    fullname varchar(100) not null,
    email varchar(100) not null,
    password text not null,
    role_id varchar(36) not null,
    company varchar(100),
    industry_id varchar(36),
    position_id varchar(36),
    photo_id varchar(36),
    user_type_id varchar(36) not null,
    phone_number varchar(15),
    address text,
    date_of_birth date,
    ballance double precision not null default 0,
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    updated_by varchar(36),
    updated_at timestamp without time zone,
    versions int not null default 0,
    is_active boolean not null default false
);

ALTER TABLE
    tb_user
ADD
    CONSTRAINT user_pk primary key (id);

ALTER TABLE
    tb_user
ADD
    CONSTRAINT user_bk unique (email);

ALTER TABLE
    tb_user
ADD
    CONSTRAINT industry_fk FOREIGN KEY(industry_id) REFERENCES tb_industry(id);

ALTER TABLE
    tb_user
ADD
    CONSTRAINT position_fk FOREIGN KEY(position_id) REFERENCES tb_position(id);

ALTER TABLE
    tb_user
ADD
    CONSTRAINT photo_fk FOREIGN KEY(photo_id) REFERENCES tb_file(id);

ALTER TABLE
    tb_user
ADD
    CONSTRAINT user_type_fk FOREIGN KEY(user_type_id) REFERENCES tb_user_type(id);

ALTER TABLE
    tb_user
ADD
    CONSTRAINT role_fk FOREIGN KEY(role_id) REFERENCES tb_role(id);

CREATE TABLE tb_user_socmed(
    id varchar(36) NOT NULL,
    user_id varchar(36) NOT NULL,
    socmed_id varchar(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_user_socmed
ADD
    CONSTRAINT tb_user_socmed_pk PRIMARY KEY(id);

ALTER TABLE
    tb_user_socmed
ADD
    CONSTRAINT tb_user_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);

ALTER TABLE
    tb_user_socmed
ADD
    CONSTRAINT tb_socmed_fk FOREIGN KEY(socmed_id) REFERENCES tb_social_media(id);

CREATE TABLE tb_post_type(
    id varchar(36) NOT NULL,
    post_type_code varchar(5) NOT NULL,
    post_type_name varchar(100) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_type
ADD
    CONSTRAINT tb_post_type_pk PRIMARY KEY(id);

ALTER TABLE
    tb_post_type
ADD
    CONSTRAINT tb_post_type_bk UNIQUE(post_type_code);

CREATE TABLE tb_post (
    id varchar(36) NOT NULL,
    title varchar(100) NOT NULL,
    body text NOT NULL,
    user_id varchar(36) NOT NULL,
    type_post_id varchar(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post
ADD
    CONSTRAINT tb_post_pk PRIMARY KEY(id);

ALTER TABLE
    tb_post
ADD
    CONSTRAINT user_tb_post_type_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);

ALTER TABLE
    tb_post
ADD
    CONSTRAINT post_type_tb_post_type_fk FOREIGN KEY(type_post_id) REFERENCES tb_post_type(id);

CREATE TABLE tb_post_attachment(
    id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    file_id varchar(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_attachment
ADD
    CONSTRAINT tb_post_attachment_pk PRIMARY KEY(id);

ALTER TABLE
    tb_post_attachment
ADD
    CONSTRAINT tb_post_attachment_user_fk FOREIGN KEY(post_id) REFERENCES tb_post(id);

ALTER TABLE
    tb_post_attachment
ADD
    CONSTRAINT tb_post_attachment_file_fk FOREIGN KEY(file_id) REFERENCES tb_file(id);

CREATE TABLE tb_post_like(
    id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    user_id varchar(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_like
ADD
    CONSTRAINT tb_post_like_pk PRIMARY KEY(id);

ALTER TABLE
    tb_post_like
ADD
    CONSTRAINT tb_post_like_fk FOREIGN KEY(post_id) REFERENCES tb_post(id);

ALTER TABLE
    tb_post_like
ADD
    CONSTRAINT tb_post_like_user_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);

CREATE TABLE tb_post_bookmark(
    id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    user_id varchar(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_bookmark
ADD
    CONSTRAINT tb_post_bookmark_pk PRIMARY KEY(id);

ALTER TABLE
    tb_post_bookmark
ADD
    CONSTRAINT tb_post_bookmark_fk FOREIGN KEY(post_id) REFERENCES tb_post(id);

ALTER TABLE
    tb_post_bookmark
ADD
    CONSTRAINT tb_post_bookmark_user_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);

CREATE TABLE tb_post_polling(
    id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    question varchar(150) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_polling
ADD
    CONSTRAINT tb_post_polling_pk PRIMARY KEY(id);

ALTER TABLE
    tb_post_polling
ADD
    CONSTRAINT tb_post_polling_fk FOREIGN KEY(post_id) REFERENCES tb_post(id);

CREATE TABLE tb_post_polling_option(
    id varchar(36) NOT NULL,
    post_polling_id varchar(36) NOT NULL,
    content varchar(150) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_polling_option
ADD
    CONSTRAINT tb_post_polling_option_pk PRIMARY KEY(id);

ALTER TABLE
    tb_post_polling_option
ADD
    CONSTRAINT tb_post_polling_fk FOREIGN KEY(post_polling_id) REFERENCES tb_post_polling(id);

CREATE TABLE tb_user_polling_response(
    id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    polling_option_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_user_polling_response
ADD
    CONSTRAINT user_polling_response_pk PRIMARY KEY(id);

ALTER TABLE
    tb_user_polling_response
ADD
    CONSTRAINT user_polling_response_ck UNIQUE(user_id, polling_option_id);

ALTER TABLE
    tb_user_polling_response
ADD
    CONSTRAINT product_user_polling_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);

ALTER TABLE
    tb_user_polling_response
ADD
    CONSTRAINT product_post_polling_fk FOREIGN KEY(polling_option_id) REFERENCES tb_post_polling_option(id);

CREATE TABLE tb_comment(
    id VARCHAR(36) NOT NULL,
    content TEXT NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    post_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_comment
ADD
    CONSTRAINT comment_pk PRIMARY KEY(id);

ALTER TABLE
    tb_comment
ADD
    CONSTRAINT tb_user_post_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);

ALTER TABLE
    tb_comment
ADD
    CONSTRAINT tb_comment_post_fk FOREIGN KEY(post_id) REFERENCES tb_post(id);

CREATE TABLE tb_article(
    id VARCHAR(36) NOT NULL,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    photo_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_article
ADD
    CONSTRAINT article_pk PRIMARY KEY(id);

ALTER TABLE
    tb_article
ADD
    CONSTRAINT article_photo_fk FOREIGN KEY(photo_id) REFERENCES tb_file(id);

CREATE TABLE tb_product_type(
    id VARCHAR(36) NOT NULL,
    product_type_code VARCHAR(6) NOT NULL,
    product_type_name TEXT NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_product_type
ADD
    CONSTRAINT product_type_pk PRIMARY KEY(id);

ALTER TABLE
    tb_product_type
ADD
    CONSTRAINT product_type_bk UNIQUE(product_type_code);

ALTER TABLE
    tb_product_type
ADD
    CONSTRAINT product_type_ck UNIQUE(id, product_type_code);

CREATE TABLE tb_product(
    id VARCHAR(36) NOT NULL,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    provider TEXT NOT NULL,
    location TEXT NOT NULL,
    price FLOAT NOT NULL,
    owner_id VARCHAR(36) NOT NULL,
    type_product_id VARCHAR(36) NOT NULL,
    photo_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_product
ADD
    CONSTRAINT product_pk PRIMARY KEY(id);

ALTER TABLE
    tb_product
ADD
    CONSTRAINT owner_product_fk FOREIGN KEY(owner_id) REFERENCES tb_user(id);

ALTER TABLE
    tb_product
ADD
    CONSTRAINT product_type_product_fk FOREIGN KEY(type_product_id) REFERENCES tb_product_type(id);

ALTER TABLE
    tb_product
ADD
    CONSTRAINT file_product_fk FOREIGN KEY(photo_id) REFERENCES tb_file(id);

CREATE TABLE tb_schedule(
    id VARCHAR(36) NOT NULL,
    date_time_start TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    date_time_end TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_schedule
ADD
    CONSTRAINT schedule_pk PRIMARY KEY(id);

ALTER TABLE
    tb_schedule
ADD
    CONSTRAINT product_schedule_fk FOREIGN KEY(product_id) REFERENCES tb_product(id);

CREATE TABLE tb_payment(
    id VARCHAR(36) NOT NULL,
    transaction_code TEXT NOT NULL,
    approval BOOLEAN NOT NULL DEFAULT FALSE,
    user_id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    transfer_photo_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    versions INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_payment
ADD
    CONSTRAINT payment_pk PRIMARY KEY(id);

ALTER TABLE
    tb_payment
ADD
    CONSTRAINT payment_bk UNIQUE(transaction_code);

ALTER TABLE
    tb_payment
ADD
    CONSTRAINT user_payment_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);

ALTER TABLE
    tb_payment
ADD
    CONSTRAINT product_payment_fk FOREIGN KEY(product_id) REFERENCES tb_product(id);
    
ALTER TABLE
    tb_payment
ADD
    CONSTRAINT file_payment_fk FOREIGN KEY(transfer_photo_id) REFERENCES tb_file(id);