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

# Função para carregar dados do CSV para a tabela temporária
def load_data_to_temporary_table(conn, data):
    if conn is not None:
        cursor = conn.cursor()
        try:
            # Limpar a tabela temporária antes de carregar os novos dados
            cursor.execute("TRUNCATE TABLE carga_temporaria")
            
            # Inserir dados na tabela temporária
            for index, row in data.iterrows():
                cursor.execute("""
                    INSERT INTO carga_temporaria (
                        order_id, order_item_id, purchase_date, payments_date, buyer_email, buyer_name,
                        cpf, buyer_phone_number, sku, product_name, quantity_purchased, currency,
                        item_price, ship_service_level, recipient_name, ship_address_1, ship_address_2,
                        ship_address_3, ship_city, ship_state, ship_postal_code, ship_country, ioss_number
                    ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
                """, (
                    row['order-id'], row['order-item-id'], row['purchase-date'], row['payments-date'], row['buyer-email'],
                    row['buyer-name'], row['cpf'], row['buyer-phone-number'], row['sku'], row['product-name'],
                    row['quantity-purchased'], row['currency'], row['item-price'], row['ship-service-level'],
                    row['recipient-name'], row['ship-address-1'], row['ship-address-2'], row['ship-address-3'],
                    row['ship-city'], row['ship-state'], row['ship-postal-code'], row['ship-country'], row['ioss-number']
                ))
            
            conn.commit()
            print("Dados carregados na tabela temporária com sucesso.")
        except mysql.connector.Error as e:
            conn.rollback()
            print("Erro ao carregar dados na tabela temporária:", e)
        finally:
            cursor.close()
    else:
        print("Não foi possível conectar ao banco de dados.")


# Função para processar e inserir dados nas tabelas finais
def process_and_insert_data(conn):
    if conn is not None:
        cursor = conn.cursor()
        try:
            # Inserir produtos
            cursor.execute("""
                INSERT INTO Produtos (sku, product_name, quantity, currency, item_price)
                SELECT DISTINCT sku, product_name, quantity_purchased, currency, item_price FROM carga_temporaria
                WHERE sku NOT IN (SELECT sku FROM Produtos)
            """)
            
            # Inserir clientes
            cursor.execute("""
                INSERT INTO Clientes (cpf, buyer_email, buyer_name, buyer_phone_number)
                SELECT DISTINCT cpf, buyer_email, buyer_name, buyer_phone_number FROM carga_temporaria
                WHERE cpf NOT IN (SELECT cpf FROM Clientes)
            """)

            # Inserir pedidos
            cursor.execute("""
                INSERT INTO Pedidos (order_id, purchase_date, payments_date, buyer_email, buyer_name, cpf,
                                    buyer_phone_number, ship_service_level, recipient_name, ship_address_1,
                                    ship_address_2, ship_address_3, ship_city, ship_state, ship_postal_code,
                                    ship_country, ioss_number)
                SELECT DISTINCT order_id, purchase_date, payments_date, buyer_email, buyer_name, cpf,
                                buyer_phone_number, ship_service_level, recipient_name, ship_address_1,
                                ship_address_2, ship_address_3, ship_city, ship_state, ship_postal_code,
                                ship_country, ioss_number FROM carga_temporaria
            """)
            
            # Inserir itens de pedido
            cursor.execute("""
                INSERT INTO ItensPedido (order_id, sku, quantity_purchased)
                SELECT DISTINCT order_id, sku, quantity_purchased FROM carga_temporaria
            """)
            
            # Inserir movimentação de estoque
            cursor.execute("""
                INSERT INTO MovimentacaoEstoque (sku, quantidade)
                SELECT DISTINCT sku, quantity_purchased FROM carga_temporaria
            """)
            
            # Commit das transações
            conn.commit()
            print("Dados inseridos nas tabelas finais com sucesso.")
        except mysql.connector.Error as e:
            conn.rollback()
            print("Erro ao inserir dados nas tabelas finais:", e)
        finally:
            cursor.close()
    else:
        print("Não foi possível conectar ao banco de dados.")

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

# Exemplo de uso
if __name__ == "__main__":
    # Conectar ao banco de dados
    connection = connect_to_database()
    if connection:
        # Carregar dados do CSV
        csv_file = "carga.csv"
        data = load_data_from_csv(csv_file)
        if data is not None:
            # Carregar dados para a tabela temporária
            load_data_to_temporary_table(connection, data)
            
            # Processar e inserir dados nas tabelas finais
            process_and_insert_data(connection)
            
            # Fechar conexão
            connection.close()
