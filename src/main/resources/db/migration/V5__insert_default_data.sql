INSERT INTO users (username, email, password_hash) VALUES 
('admin', 'admin@board.com', 'admin123');

INSERT INTO boards (name, description) VALUES 
('Board Exemplo', 'Board de demonstração');

INSERT INTO board_columns (board_id, name, `order`, kind) VALUES 
(1, 'A Fazer', 0, 'INITIAL'),
(1, 'Em Progresso', 1, 'PENDING'),
(1, 'Concluído', 2, 'FINAL'),
(1, 'Cancelado', 3, 'CANCEL');