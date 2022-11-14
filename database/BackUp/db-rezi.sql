CREATE TABLE tb_user_polling_response(
    id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    polling_option_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
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

ALTER TABLE
    tb_user_polling_response
ADD
    CONSTRAINT product_user_polling_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);

ALTER TABLE
    tb_user_polling_response
ADD
    CONSTRAINT product_post_polling_fk FOREIGN KEY(user_id) REFERENCES tb_post_polling(id);

CREATE TABLE tb_comment(
    id VARCHAR(36) NOT NULL,
    content TEXT NOT NULL,
    user_id VARCHAR(36) NOT NULL,
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
    ver INT NOT NULL DEFAULT 0,
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
    owner_id VARCHAR(36) NOT NULL,
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

CREATE TABLE tb_user_polling_response(
    id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    polling_option_id VARCHAR(36) NOT NULL,
    created_by VARCHAR(36) NOT NULL,
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

ALTER TABLE
    tb_user_polling_response
ADD
    CONSTRAINT product_user_polling_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);

ALTER TABLE
    tb_user_polling_response
ADD
    CONSTRAINT product_post_polling_fk FOREIGN KEY(user_id) REFERENCES tb_post_polling(id);

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
    product_type_code VARCHAR(6) NOT NULL,
    product_type_name TEXT NOT NULL,
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
    owner_id VARCHAR(36) NOT NULL,
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

ALTER TABLE
    tb_payment
ADD
    CONSTRAINT photo_payment_fk FOREIGN KEY(transfer_photo_id) REFERENCES tb_file(id);

ALTER TABLE
    tb_payment
ADD
    CONSTRAINT user_payment_fk FOREIGN KEY(user_id) REFERENCES tb_user(id);