-- Tabela de foods (cardápio)
CREATE TABLE IF NOT EXISTS foods (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    image VARCHAR(500),
    price INTEGER NOT NULL
);

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);

-- Tabela de itens do carrinho
CREATE TABLE IF NOT EXISTS cart_items (
    id BIGSERIAL PRIMARY KEY,
    food_id BIGINT REFERENCES foods(id),
    quantity INTEGER NOT NULL DEFAULT 1
);
