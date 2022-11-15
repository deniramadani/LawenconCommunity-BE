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