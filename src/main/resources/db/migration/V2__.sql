-- Alter tables to add new unique constraints
ALTER TABLE category
    ADD CONSTRAINT uc_category_title UNIQUE (title);

ALTER TABLE product
    ADD CONSTRAINT uc_product_title UNIQUE (title);

-- Modify the foreign key to include ON DELETE SET NULL
ALTER TABLE product
    DROP FOREIGN KEY FK_PRODUCT_ON_CATEGORY;

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id)
        ON DELETE SET NULL;