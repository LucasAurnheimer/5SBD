USE SBD;

CREATE TABLE Pedidos (
    order_id INT PRIMARY KEY IDENTITY,
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
    order_item_id INT PRIMARY KEY IDENTITY,
    order_id INT,
    sku VARCHAR(50),
    quantity_purchased INT,
    FOREIGN KEY (order_id) REFERENCES Pedidos(order_id),
    FOREIGN KEY (sku) REFERENCES Produtos(sku)
);

-- Tabela de Movimentação de Estoque
CREATE TABLE MovimentacaoEstoque (
    movimento_id INT PRIMARY KEY IDENTITY,
    sku VARCHAR(50),
    quantidade INT,
    FOREIGN KEY (sku) REFERENCES Produtos(sku)
);

-- Tabela de Compras
CREATE TABLE Compras (
    compra_id INT PRIMARY KEY IDENTITY,
    sku VARCHAR(50),
    quantidade INT,
    FOREIGN KEY (sku) REFERENCES Produtos(sku)
);

-- Tabela temporária para carga de dados
CREATE TABLE carga_temporaria (
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
