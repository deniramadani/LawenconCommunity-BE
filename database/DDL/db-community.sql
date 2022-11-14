CREATE TABLE tb_role(
    id varchar(36) not null,
    role_code varchar(5) not null,
    role_name varchar(20) not null,
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    update_by varchar(36) not null,
    update_at timestamp without time zone,
    versions int not null default 0,
    is_actives boolean not null default true
);

ALTER TABLE
    tb_role
ADD
    CONSTRAINT role_pk primary key (id);

ALTER TABLE
    tb_role
ADD
    CONSTRAINT role_bk unique (role_code);

CREATE TABLE tb_company(
    id varchar(36) not null,
    company_name varchar(30) not null,
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    update_by varchar(36) not null,
    update_at timestamp without time zone,
    versions int not null default 0,
    is_actives boolean not null default true
);

ALTER TABLE
    tb_company
ADD
    CONSTRAINT company_pk primary key (id);

CREATE TABLE tb_industry(
    id varchar(36) not null,
    industry_name varchar(30),
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    update_by varchar(36) not null,
    update_at timestamp without time zone,
    versions int not null default 0,
    is_actives boolean not null default true
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
    update_by varchar(36) not null,
    update_at timestamp without time zone,
    versions int not null default 0,
    is_actives boolean not null default true
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
    update_by varchar(36) not null,
    update_at timestamp without time zone,
    versions int not null default 0,
    is_actives boolean not null default true
);

ALTER TABLE
    tb_file
ADD
    CONSTRAINT file_pk primary key (id);

CREATE TABLE tb_user_type(
    id varchar(36) not null,
    user_type_code varchar(6),
    user_type_name varchar(30),
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    update_by varchar(36) not null,
    update_at timestamp without time zone,
    versions int not null default 0,
    is_actives boolean not null default true
);

ALTER TABLE
    tb_user_type
ADD
    CONSTRAINT user_type_pk primary key (id);

ALTER TABLE
    tb_user_type
ADD
    CONSTRAINT user_type_bk unique (user_type_code);

CREATE TABLE tb_users(
    id varchar(36) not null,
    fullname varchar(35),
    email varchar(30),
    password text,
    role_id varchar(36) not null,
    company_id varchar(36) not null,
    industry_id varchar(36) not null,
    position_id varchar(36) not null,
    photo_id varchar(36) not null,
    user_type_id varchar(36) not null,
    phone_number varchar(15),
    address text,
    date_of_birth date,
    ballance double precision,
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    update_by varchar(36) not null,
    update_at timestamp without time zone,
    versions int not null default 0,
    is_actives boolean not null default true
);

ALTER TABLE
    tb_users
ADD
    CONSTRAINT users_pk primary key (id);

ALTER TABLE
    tb_users
ADD
    CONSTRAINT users_bk unique (email);

ALTER TABLE
    tb_users
ADD
    CONSTRAINT company_fk FOREIGN KEY(company_id) REFERENCES tb_company(id);

ALTER TABLE
    tb_users
ADD
    CONSTRAINT industry_fk FOREIGN KEY(industry_id) REFERENCES tb_industry(id);

ALTER TABLE
    tb_users
ADD
    CONSTRAINT position_fk FOREIGN KEY(position_id) REFERENCES tb_position(id);

ALTER TABLE
    tb_users
ADD
    CONSTRAINT photo_fk FOREIGN KEY(photo_id) REFERENCES tb_file(id);

ALTER TABLE
    tb_users
ADD
    CONSTRAINT user_type_fk FOREIGN KEY(user_type_id) REFERENCES tb_user_type(id);

ALTER TABLE
    tb_users
ADD
    CONSTRAINT role_fk FOREIGN KEY(role_id) REFERENCES tb_role(id);

CREATE TABLE tb_verification_code(
    id varchar(36) not null,
    verification_code varchar(6),
    user_id varchar(36) not null,
    created_by varchar(36) not null,
    created_at timestamp without time zone not null,
    update_by varchar(36) not null,
    update_at timestamp without time zone,
    versions int not null default 0,
    is_actives boolean not null default true
);

ALTER TABLE
    tb_verification_code
ADD
    CONSTRAINT verification_code_pk primary key (id);

ALTER TABLE
    tb_verification_code
ADD
    CONSTRAINT user_fk FOREIGN KEY(user_id) REFERENCES tb_users(id);

CREATE TABLE tb_post (
    id varchar(36) NOT NULL,
    title varchar(100) NOT NULL,
    body text NOT NULL,
    user_id varchar(36) NOT NULL,
    type_post_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT TIME ZONE,
    versions int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post
ADD
    CONSTRAINT tb_post_pk PRIMARY KEY(id);

CREATE TABLE tb_post_type(
    id varchar(36) NOT NULL,
    post_type_code varchar(5) NOT NULL,
    post_type_name varchar(100) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT TIME ZONE,
    versions int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_type
ADD
    CONSTRAINT tb_post_type_pk PRIMARY KEY(id);

ALTER TABLE
    tb_post_type
ADD
    CONSTRAINT tb_post_type_bk UNIQUE(post_type_code);

CREATE TABLE tb_post_attachment(
    id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    file_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT TIME ZONE,
    versions int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_attachment
ADD
    CONSTRAINT tb_post_attachment_pk PRIMARY KEY(id);

CREATE TABLE tb_post_attachment(
    id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    file_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT TIME ZONE,
    versions int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_attachment
ADD
    CONSTRAINT tb_post_attachment_pk PRIMARY KEY(id);

CREATE TABLE tb_post_attachment(
    id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    file_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT TIME ZONE,
    versions int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_attachment
ADD
    CONSTRAINT tb_post_attachment_pk PRIMARY KEY(id);

CREATE TABLE tb_post_like(
    id varchar(36) NOT NULL,
    post_id varchar(36) NOT NULL,
    user_id varchar(36) NOT NULL,
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT TIME ZONE,
    versions int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
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
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT TIME ZONE,
    versions int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
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
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT TIME ZONE,
    versions int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
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
    created_by varchar(36) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    updated_by varchar(36),
    updated_at timestamp WITHOUT TIME ZONE,
    versions int NOT NULL DEFAULT 0,
    is_active boolean NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_post_polling_option
ADD
    CONSTRAINT tb_post_polling_pk PRIMARY KEY(id);

ALTER TABLE
    tb_post_polling_option
ADD
    CONSTRAINT tb_post_polling_fk FOREIGN KEY(post_id) REFERENCES tb_post(id);

CREATE TABLE tb_user_polling_response(
    id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    polling_option_id VARCHAR(36) NOT NULL,
    NO created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    ver INT NOT NULL DEFAULT 0,
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

CREATE TABLE tb_comment(
    id VARCHAR(36) NOT NULL,
    content TEXT NOT NULL,
    post_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    ver INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_comment
ADD
    CONSTRAINT comment_pk PRIMARY KEY(id);

CREATE TABLE tb_article(
    id VARCHAR(36) NOT NULL,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    photo_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    ver INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_article
ADD
    CONSTRAINT article_pk PRIMARY KEY(id);

CREATE TABLE tb_product_type(
    id VARCHAR(36) NOT NULL,
    code TEXT NOT NULL,
    name TEXT NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    ver INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_product_type
ADD
    CONSTRAINT product_type_pk PRIMARY KEY(id);

ALTER TABLE
    tb_product_type
ADD
    CONSTRAINT product_type_bk UNIQUE(code);

ALTER TABLE
    tb_product_type
ADD
    CONSTRAINT product_type_ck UNIQUE(id, code);

CREATE TABLE tb_product(
    id VARCHAR(36) NOT NULL,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    provider TEXT NOT NULL,
    location TEXT NOT NULL,
    price FLOAT NOT NULL,
    type_product_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    ver INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE
    tb_product
ADD
    CONSTRAINT product_pk PRIMARY KEY(id);

ALTER TABLE
    tb_product
ADD
    CONSTRAINT product_type_product_fk FOREIGN KEY(type_product_id) REFERENCES tb_product_type(id);

CREATE TABLE tb_schedule(
    id VARCHAR(36) NOT NULL,
    date_start DATE NOT NULL,
    date_end DATE NOT NULL,
    time_start TIME WITHOUT TIME ZONE NOT NULL,
    time_end TIME WITHOUT TIME ZONE NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(36),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    ver INT NOT NULL DEFAULT 0,
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
    ver INT NOT NULL DEFAULT 0,
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
    CONSTRAINT product_payment_fk FOREIGN KEY(product_id) REFERENCES tb_product(id);