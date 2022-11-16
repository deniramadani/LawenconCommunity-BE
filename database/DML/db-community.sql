INSERT INTO tb_role (id, role_code, role_name, created_by, created_at) VALUES
('94ae6c35-879a-43a1-8c56-be61a87d8487', 'ROLSY','System', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
('066cd79f-735f-48d9-9bb1-95ab6b3e8edf', 'ROLSA','Super Admin', '4ba262b9-258b-4ae3-b879-ee286c1db783', NOW()),
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