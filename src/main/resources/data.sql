INSERT INTO tb_roles (role_id, name) VALUES (1, 'ADMIN')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_roles (role_id, name) VALUES (2, 'GERENTE')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_roles (role_id, name) VALUES (3, 'VENDEDOR')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_roles (role_id, name) VALUES (4, 'CLIENTE')
ON CONFLICT (role_id) DO NOTHING;