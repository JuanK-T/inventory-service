INSERT INTO inventories (product_id, quantity) VALUES
                                                   ('prod001', 50),   -- Smartphone Galaxy XZ
                                                   ('prod002', 25),   -- Laptop UltraBook Pro 15
                                                   ('prod003', 100),  -- Auriculares NoiseFree 300
                                                   ('prod004', 30),   -- Smart TV 55" 4K UHD
                                                   ('prod005', 75),   -- Zapatillas Running Ultra
                                                   ('prod006', 15),   -- Cámara Mirrorless Alpha
                                                   ('prod007', 40),   -- Tablet Pro 12.9"
                                                   ('prod008', 60),   -- Reloj Inteligente FitPro
                                                   ('prod009', 20),   -- Consola de Videojuegos NextGen
                                                   ('prod010', 80)    -- Mochila Antirrobo Urbana
    ON CONFLICT (product_id) DO UPDATE SET
    quantity = EXCLUDED.quantity,
                                    updated_at = CURRENT_TIMESTAMP;