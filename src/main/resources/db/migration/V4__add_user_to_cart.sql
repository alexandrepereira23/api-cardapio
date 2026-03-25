-- Adicionar coluna user_id na tabela cart_items
ALTER TABLE cart_items ADD COLUMN user_id BIGINT REFERENCES users(id);
