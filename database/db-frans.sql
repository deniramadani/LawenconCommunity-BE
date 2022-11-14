CREATE TABLE tb_role(
	id varchar(36) not null,
	role_code varchar(5) not null,
	role_name varchar(20) not null,
	created_by varchar(36) not null, 
	created_at timestamp without time zone not null ,
	update_by varchar(36) not null,
	update_at timestamp without time zone,
	versions int not null default 0,
	is_actives boolean not null default true
);

ALTER TABLE tb_role ADD CONSTRAINT role_pk primary key (id);
ALTER TABLE tb_role ADD CONSTRAINT role_bk unique (role_code);

CREATE TABLE tb_company(
	id varchar(36) not null,
	company_name varchar(30) not null,
	created_by varchar(36) not null,
	created_at timestamp without time zone not null ,
	update_by  varchar(36) not null,
	update_at timestamp without time zone,
	versions int not null default 0,
	is_actives boolean not null default true
);

ALTER TABLE tb_company ADD CONSTRAINT company_pk primary key (id);

CREATE TABLE tb_industry(
	id  varchar(36) not null,
	industry_name varchar(30),
	created_by  varchar(36) not null,
	created_at timestamp without time zone not null ,
	update_by  varchar(36) not null,
	update_at timestamp without time zone,
	versions int not null default 0,
	is_actives boolean not null default true
);
ALTER TABLE tb_industry ADD CONSTRAINT industry_pk primary key (id);

CREATE TABLE tb_position(
	id  varchar(36) not null,
	position_name varchar(30),
	created_by  varchar(36) not null,
	created_at timestamp without time zone not null ,
	update_by  varchar(36) not null,
	update_at timestamp without time zone,
	versions int not null default 0,
	is_actives boolean not null default true
);
ALTER TABLE tb_position ADD CONSTRAINT position_pk primary key (id);

CREATE TABLE tb_file(
	id  varchar(36) not null,
	file_encode text,
	file_extensions varchar(5),
	created_by  varchar(36) not null,
	created_at timestamp without time zone not null ,
	update_by  varchar(36) not null,
	update_at timestamp without time zone,
	versions int not null default 0,
	is_actives boolean not null default true
);
ALTER TABLE tb_file ADD CONSTRAINT file_pk primary key (id);


CREATE TABLE tb_user_type(
	id  varchar(36) not null,
	user_type_code varchar(6),
	user_type_name varchar(30),
	created_by  varchar(36) not null,
	created_at timestamp without time zone not null ,
	update_by varchar(36) not null,
	update_at timestamp without time zone,
	versions int not null default 0,
	is_actives boolean not null default true
);
ALTER TABLE tb_user_type ADD CONSTRAINT user_type_pk primary key (id);
ALTER TABLE tb_user_type ADD CONSTRAINT  user_type_bk unique (user_type_code);



CREATE TABLE tb_users(
	id  varchar(36) not null,
	fullname varchar(35),
	email varchar(30),
	password text,
	role_id varchar(36) not null,
	company_id  varchar(36) not null,
	industry_id  varchar(36) not null,
	position_id varchar(36) not null,
	photo_id  varchar(36) not null,
	user_type_id  varchar(36) not null,
	phone_number varchar(15),
	address text,
	date_of_birth date,
	ballance double precision,
	created_by  varchar(36) not null,
	created_at timestamp without time zone not null ,
	update_by  varchar(36) not null,
	update_at timestamp without time zone,
	versions int not null default 0,
	is_actives boolean not null default true
);

ALTER TABLE tb_users ADD CONSTRAINT users_pk primary key (id);
ALTER TABLE tb_users ADD CONSTRAINT users_bk unique (email);
ALTER TABLE tb_users ADD CONSTRAINT company_fk FOREIGN KEY(company_id) REFERENCES tb_company(id);
ALTER TABLE tb_users ADD CONSTRAINT industry_fk FOREIGN KEY(industry_id) REFERENCES tb_industry(id);
ALTER TABLE tb_users ADD CONSTRAINT position_fk FOREIGN KEY(position_id) REFERENCES tb_position(id);
ALTER TABLE tb_users ADD CONSTRAINT photo_fk FOREIGN KEY(photo_id) REFERENCES tb_file(id);
ALTER TABLE tb_users ADD CONSTRAINT user_type_fk FOREIGN KEY(user_type_id) REFERENCES tb_user_type(id);
ALTER TABLE tb_users ADD CONSTRAINT role_fk FOREIGN KEY(role_id) REFERENCES tb_role(id);

CREATE TABLE tb_verification_code(
	id  varchar(36) not null,
	verification_code varchar(6),
	user_id  varchar(36) not null,
	created_by  varchar(36) not null,
	created_at timestamp without time zone not null ,
	update_by  varchar(36) not null,
	update_at timestamp without time zone,
	versions int not null default 0,
	is_actives boolean not null default true
);

ALTER TABLE tb_verification_code ADD CONSTRAINT verification_code_pk primary key (id);
ALTER TABLE tb_verification_code ADD CONSTRAINT user_fk FOREIGN KEY(user_id) REFERENCES tb_users(id);












