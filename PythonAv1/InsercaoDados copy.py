import pandas as pd
import mysql.connector

# Função para conectar ao banco de dados
def connect_to_database():
    try:
        conn = mysql.connector.connect(
            host="localhost",
            user="root",
            database="pythonVersion"
        )
        return conn
    except mysql.connector.Error as e:
        print("Erro ao conectar ao banco de dados:", e)
        return None

# Função para carregar dados do CSV para o banco de dados
def load_data_from_csv(csv_file):
    try:
        df = pd.read_csv(csv_file, sep=';')  # Especificando o separador como ';'
        print("Arquivo CSV carregado com sucesso:")
        print(df.head())  # Imprime as primeiras linhas do DataFrame para verificar os dados
        return df
    except FileNotFoundError:
        print("Arquivo CSV não encontrado.")
        return None

# Função para processar e inserir dados no banco de dados
def process_and_insert_data(conn, data):
    if conn is not None:
        cursor = conn.cursor()
        try:
            # Inserir produtos
            for index, row in data.iterrows():
                # Inserir produtos
                cursor.execute("""
                    INSERT INTO Produtos (sku, product_name, quantity, currency, item_price)
                    VALUES (%s, %s, %s, %s, %s)
                    ON DUPLICATE KEY UPDATE 
                    product_name=VALUES(product_name),
                    quantity=VALUES(quantity),
                    currency=VALUES(currency),
                    item_price=VALUES(item_price)
                """, (
                    row['sku'], row['product-name'], row['quantity-purchased'], row['currency'], row['item-price']
                ))
            
                # Inserir clientes
                cursor.execute("""
                    INSERT INTO Clientes (cpf, buyer_email, buyer_name, buyer_phone_number)
                    VALUES (%s, %s, %s, %s)
                    ON DUPLICATE KEY UPDATE 
                    buyer_email=VALUES(buyer_email),
                    buyer_name=VALUES(buyer_name),
                    buyer_phone_number=VALUES(buyer_phone_number)
                """, (
                    row['cpf'], row['buyer-email'], row['buyer-name'], row['buyer-phone-number']
                ))
                
                # Inserir pedidos
                cursor.execute("""
                    INSERT INTO Pedidos (order_id, purchase_date, payments_date, buyer_email, buyer_name, cpf,
                                        buyer_phone_number, ship_service_level, recipient_name, ship_address_1,
                                        ship_address_2, ship_address_3, ship_city, ship_state, ship_postal_code,
                                        ship_country, ioss_number)
                    VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
                    ON DUPLICATE KEY UPDATE 
                    purchase_date=VALUES(purchase_date),
                    payments_date=VALUES(payments_date),
                    buyer_name=VALUES(buyer_name),
                    cpf=VALUES(cpf),
                    buyer_phone_number=VALUES(buyer_phone_number),
                    ship_service_level=VALUES(ship_service_level),
                    recipient_name=VALUES(recipient_name),
                    ship_address_1=VALUES(ship_address_1),
                    ship_address_2=VALUES(ship_address_2),
                    ship_address_3=VALUES(ship_address_3),
                    ship_city=VALUES(ship_city),
                    ship_state=VALUES(ship_state),
                    ship_postal_code=VALUES(ship_postal_code),
                    ship_country=VALUES(ship_country),
                    ioss_number=VALUES(ioss_number)
                """, (
                    row['order-id'], row['purchase-date'], row['payments-date'], row['buyer-email'], 
                    row['buyer-name'], row['cpf'], row['buyer-phone-number'], row['ship-service-level'], 
                    row['recipient-name'], row['ship-address-1'], row['ship-address-2'], row['ship-address-3'], 
                    row['ship-city'], row['ship-state'], row['ship-postal-code'], row['ship-country'], row['ioss-number']
                ))
                
                # Inserir itens de pedido
                cursor.execute("""
                    INSERT INTO ItensPedido (order_id, sku, quantity_purchased)
                    VALUES (%s, %s, %s)
                """, (
                    row['order-id'], row['sku'], row['quantity-purchased']
                ))
                
                # Inserir movimentação de estoque
                cursor.execute("""
                    INSERT INTO MovimentacaoEstoque (sku, quantidade)
                    VALUES (%s, %s)
                """, (
                    row['sku'], row['quantity-purchased']
                ))

            # Commit das transações
            conn.commit()
            print("Dados inseridos com sucesso.")
        except mysql.connector.Error as e:
            conn.rollback()
            print("Erro ao inserir dados:", e)
        finally:
            cursor.close()
            conn.close()
    else:
        print("Não foi possível conectar ao banco de dados.")

# Exemplo de uso
if __name__ == "__main__":
    # Conectar ao banco de dados
    connection = connect_to_database()
    if connection:
        # Carregar dados do CSV
        csv_file = "carga.csv"  # Agora o nome do arquivo é carga.csv
        data = load_data_from_csv(csv_file)
        if data is not None:
            # Processar e inserir dados no banco de dados
            process_and_insert_data(connection, data)
