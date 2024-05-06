-- Tabela de Pedidos
CREATE TABLE Pedidos (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    purchase_date DATE,
    payments_date DATE,
    buyer_email VARCHAR(255),
    buyer_name VARCHAR(255),
    cpf VARCHAR(14),
    buyer_phone_number VARCHAR(20),
    ship_service_level VARCHAR(255),
    recipient_name VARCHAR(255),
    ship_address_1 VARCHAR(255),
    ship_address_2 VARCHAR(255),
    ship_address_3 VARCHAR(255),
    ship_city VARCHAR(255),
    ship_state VARCHAR(255),
    ship_postal_code VARCHAR(20),
    ship_country VARCHAR(255),
    ioss_number VARCHAR(255)
);

-- Tabela de Clientes
CREATE TABLE Clientes (
    cpf VARCHAR(14) PRIMARY KEY,
    buyer_email VARCHAR(255),
    buyer_name VARCHAR(255),
    buyer_phone_number VARCHAR(20)
);

-- Tabela de Produtos
CREATE TABLE Produtos (
    sku VARCHAR(50) PRIMARY KEY,
    product_name VARCHAR(255),
    quantity INT,
    currency VARCHAR(5),
    item_price DECIMAL(10, 2)
);

-- Tabela de Itens de Pedido
CREATE TABLE ItensPedido (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    sku VARCHAR(50),
    quantity_purchased INT,
    FOREIGN KEY (order_id) REFERENCES Pedidos(order_id),
    FOREIGN KEY (sku) REFERENCES Produtos(sku)
);

-- Tabela de Movimentação de Estoque
CREATE TABLE MovimentacaoEstoque (
    movimento_id INT PRIMARY KEY AUTO_INCREMENT,
    sku VARCHAR(50),
    quantidade INT,
    FOREIGN KEY (sku) REFERENCES Produtos(sku)
);

-- Tabela de Compras
CREATE TABLE Compras (
    compra_id INT PRIMARY KEY AUTO_INCREMENT,
    sku VARCHAR(50),
    quantidade INT,
    FOREIGN KEY (sku) REFERENCES Produtos(sku)
);

-- Tabela temporária para carga de dados
CREATE TABLE carga_temporaria (
    order_id INT,
    order_item_id INT,
    purchase_date DATE,
    payments_date DATE,
    buyer_email VARCHAR(255),
    buyer_name VARCHAR(255),
    cpf VARCHAR(14),
    buyer_phone_number VARCHAR(20),
    sku VARCHAR(50),
    product_name VARCHAR(255),
    quantity_purchased INT,
    currency VARCHAR(5),
    item_price DECIMAL(10, 2),
    ship_service_level VARCHAR(255),
    recipient_name VARCHAR(255),
    ship_address_1 VARCHAR(255),
    ship_address_2 VARCHAR(255),
    ship_address_3 VARCHAR(255),
    ship_city VARCHAR(255),
    ship_state VARCHAR(255),
    ship_postal_code VARCHAR(20),
    ship_country VARCHAR(255),
    ioss_number VARCHAR(255)
);

-------------------------------- COMANDOS DE INSERÇÃO DE DADOS NAS TABELAS ---------------------------------

--Limpar a tabela temporária antes de carregar os novos dados
 cursor.execute("TRUNCATE TABLE carga_temporaria")

 -- Inserir dados na tabela temporária
INSERT INTO carga_temporaria (
    order_id, order_item_id, purchase_date, payments_date, buyer_email, buyer_name,
    cpf, buyer_phone_number, sku, product_name, quantity_purchased, currency,
    item_price, ship_service_level, recipient_name, ship_address_1, ship_address_2,
    ship_address_3, ship_city, ship_state, ship_postal_code, ship_country, ioss_number
) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)

-- Inserir produtos na tabela Produtos
INSERT INTO Produtos (sku, product_name, quantity, currency, item_price)
SELECT DISTINCT sku, product_name, quantity_purchased, currency, item_price
FROM carga_temporaria
WHERE sku NOT IN (SELECT sku FROM Produtos)

-- Inserir clientes na tabela Clientes
INSERT INTO Clientes (cpf, buyer_email, buyer_name, buyer_phone_number)
SELECT DISTINCT cpf, buyer_email, buyer_name, buyer_phone_number
FROM carga_temporaria
WHERE cpf NOT IN (SELECT cpf FROM Clientes)

-- Inserir pedidos na tabela Pedidos
INSERT INTO Pedidos (order_id, purchase_date, payments_date, buyer_email, buyer_name, cpf,
                    buyer_phone_number, ship_service_level, recipient_name, ship_address_1,
                    ship_address_2, ship_address_3, ship_city, ship_state, ship_postal_code,
                    ship_country, ioss_number)
SELECT DISTINCT order_id, purchase_date, payments_date, buyer_email, buyer_name, cpf,
                buyer_phone_number, ship_service_level, recipient_name, ship_address_1,
                ship_address_2, ship_address_3, ship_city, ship_state, ship_postal_code,
                ship_country, ioss_number
FROM carga_temporaria

-- Inserir itens de pedido na tabela ItensPedido
INSERT INTO ItensPedido (order_id, sku, quantity_purchased)
SELECT DISTINCT order_id, sku, quantity_purchased
FROM carga_temporaria

-- Inserir movimentação de estoque na tabela MovimentacaoEstoque
INSERT INTO MovimentacaoEstoque (sku, quantidade)
SELECT DISTINCT sku, quantity_purchased
FROM carga_temporaria



