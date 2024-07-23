CREATE TABLE `ecommerce`.`products` (
  `product_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(200) NULL,
  PRIMARY KEY (`product_id`));

CREATE TABLE `ecommerce`.`shelfs` (
  `shelf_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`shelf_id`));

CREATE TABLE `ecommerce`.`products_shelfs` (
    product_id INT NOT NULL,
    shelf_id INT NOT NULL,
    PRIMARY KEY (product_id, shelf_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (shelf_id) REFERENCES shelfs(shelf_id)
);

DROP TABLE `ecommerce`.`products`;