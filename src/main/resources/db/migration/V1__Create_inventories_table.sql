CREATE TABLE inventories (
                             product_id VARCHAR(50) PRIMARY KEY,
                             quantity INTEGER NOT NULL CHECK (quantity >= 0),
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_inventories_product_id ON inventories(product_id);
CREATE INDEX idx_inventories_quantity ON inventories(quantity);