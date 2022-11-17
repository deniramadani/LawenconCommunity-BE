INSERT INTO tb_role (id, role_code, role_name, created_by, created_at) VALUES
('94ae6c35-879a-43a1-8c56-be61a87d8487', 'ROLSY','System', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('066cd79f-735f-48d9-9bb1-95ab6b3e8edf', 'ROLSA','Super Admin', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('4f5d2d78-5bfd-4e8a-9483-6c5c8f9ed91d', 'ROLAM','Admin', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('7adb1711-698a-46dd-9fe1-924bdb036416', 'ROLMM','Member', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW());

INSERT INTO tb_user_type (id, user_type_code, user_type_name, created_by, created_at) VALUES
('da6ba5a8-93a9-40de-be55-b7b5a9787b4b', 'UTCPM', 'Premium', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('affffccd-470d-41da-8f39-4e56788572e1', 'UTCBS', 'Basic', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW());

INSERT INTO tb_user (id, fullname, email, password, role_id, user_type_id, created_by, created_at, is_active) VALUES
('4ba262b9-258b-4ae3-b879-ee286c1db783', 'System',
'system@email.com', '$2a$10$mQ77inhXemDEE0zlr9kwc.94nqJERo1uHIsaPjesr0upzK1Hm6cWa',
'94ae6c35-879a-43a1-8c56-be61a87d8487', 'da6ba5a8-93a9-40de-be55-b7b5a9787b4b',
'4ba262b9-258b-4ae3-b879-ee286c1db783', NOW(), TRUE),
('e9984097-daa4-44f4-bacf-4f32f8a2947c', 'Admin',
'admin@email.com', '$2a$10$mQ77inhXemDEE0zlr9kwc.94nqJERo1uHIsaPjesr0upzK1Hm6cWa',
'066cd79f-735f-48d9-9bb1-95ab6b3e8edf', 'da6ba5a8-93a9-40de-be55-b7b5a9787b4b',
'4ba262b9-258b-4ae3-b879-ee286c1db783', NOW(), TRUE);

INSERT INTO tb_industry (id, industry_name, created_by, created_at) VALUES
('aaf039c7-4646-41cd-a015-6d77c0b9741d', 'Transport', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('92d28d19-92fb-4bcc-8eaa-8ff8fe93cc21', 'Aerospace', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()), 
('784f0bf0-c103-47e2-840d-7375a44e06d5', 'Agriculture', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('e3cdd133-ead2-414c-8c9a-249ab00e31b6', 'Computer', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('025ee880-3578-4a5e-91a6-f16c76f8529c', 'Telecommunication', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('673a0460-fe5f-4d25-ac42-9631045d158f', 'Education', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('d8574be7-bdbb-4820-83d9-fbcdce6965eb', 'Construction', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('61309d7a-ab8b-4e94-82de-aa4f6f211722', 'Electronics', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('cc10aaca-eb90-456b-8a0b-4ceabaf8dc5d', 'Manufacturing', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('007eb996-7877-4252-9ba7-b668e62e27db', 'Energy', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('66c06a17-889c-4055-b395-b6a950db522b', 'Pharmaceutical', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('41701fae-578c-4238-aa4f-bc3b2c5baedf', 'Healthcare', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('8a64b99a-1b9a-4900-98fe-c06943f00aa6', 'Food', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('79257500-3502-45a9-83cd-fc73efa6958e', 'Entertainment', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('8b31d55d-8d20-4bf0-93a1-9dd081d6b3c3', 'Music', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('a0582db5-dbce-4759-a519-1e64dd2be987', 'Mining', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('643aa8ed-ae6b-41f9-b259-cbcc1a35470b', 'Hospitality', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('a93e3d92-1de8-4017-a22b-2bdd58e87ec7', 'News Media', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('7385cb8f-fda0-42c8-b402-d7e85ee0a32f', 'World Wide Web', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW());

INSERT INTO tb_position (id, position_name, created_by, created_at) VALUES
('0cce6239-f359-45e4-8077-a93bd33a3352', 'Chief Executive Officer (CEO)', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('f229a905-a559-4e86-aefd-0e731b9610df', 'Chief Operations Officer (COO)', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('ac1551ce-e618-48b4-bba4-f02f33b044ef', 'Chief Financial Officer (CFO)', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('58048786-94b8-46e1-8f64-ca33a2f482e5', 'Receptionist', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('da7c1119-6b05-4ade-a2fd-bacfa70f04fc', 'Administrative Officer', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('dd8aa6fb-820a-41b0-9dee-0696d56617ff', 'Secretary', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('a4065788-55c2-4489-b962-e7de949123ac', 'Human Resources Manager', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('d7ef7fd5-c808-49ff-9aba-142edb4740bc', 'Benefits Manager', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('8f2b10aa-1193-45e8-a257-a2b1ce569b6f', 'Payroll Assistant', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('b3aa6674-b672-459d-80b8-d42e1e924906', 'Marketing Manager', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('440bb6cf-e8a7-475e-8724-721b1ffcbb66', 'Social Media Specialist', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('c94481db-98fd-40fb-8020-0fea3021e7bb', 'Copywriter', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('21f944ab-452e-462b-b4e4-97b08abb45de', 'Sales Representative', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('7cf3d4be-2107-4171-92e0-08480d29ad7e', 'Account Executive', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('d0118997-3b9d-430f-a7af-97a6564c1ab2', 'Regional Sales Manager', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('02393b34-2875-4fa9-b0a2-4abfe26350be', 'Finance Manager', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('8f25e53a-733c-4cdd-8f25-597f57e2746a', 'Internal Auditor', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('f47c140f-d237-4738-8d5c-1044bf7b6624', 'Accounts Payable Accountant', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW());

INSERT INTO tb_product_type (id, product_type_code, product_type_name, created_by, created_at) VALUES
('23ac5402-594f-4983-9abd-da4486a47c7b', 'PRODE', 'Event', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('ce785648-af41-4a94-b24c-27beb9c00c37', 'PRODC', 'Course', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW());


















