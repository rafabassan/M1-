INSERT INTO product (id, name, category, price, active) VALUES
('p1', 'Notebook Lenovo', 'Eletrônicos', 3500.00, TRUE),
('p2', 'Smartphone Samsung', 'Eletrônicos', 2200.00, TRUE),
('p3', 'Cadeira Escritório', 'Móveis', 850.00, TRUE),
('p4', 'Monitor LG', 'Eletrônicos', 1450.00, TRUE),
('p5', 'Mesa Escritório', 'Móveis', 1200.00, TRUE),
('p6', 'Teclado Mecânico', 'Eletrônicos', 450.00, TRUE),
('p7', 'Impressora Epson', 'Periféricos', 980.00, TRUE),
('p8', 'Headset Gamer', 'Eletrônicos', 620.00, TRUE)
ON CONFLICT (id) DO NOTHING;
