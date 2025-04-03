-- 建立 Employee 表
CREATE TABLE Employee (
                          employee_id INTEGER PRIMARY KEY,
                          employee_name VARCHAR(255) NOT NULL,
                          department VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          user_type VARCHAR(20) CHECK (user_type IN ('Admin', 'Regular User')) NOT NULL
);

-- 建立 Product 表
CREATE TABLE Product (
                         product_id SERIAL PRIMARY KEY,
                         product_name VARCHAR(255) NOT NULL,
                         number_part_check_out INTEGER NOT NULL DEFAULT 0,
                         number_part_in_stock INTEGER NOT NULL DEFAULT 0,
                         total_cost FLOAT NOT NULL DEFAULT 0
);

-- 建立 Part 表
CREATE TABLE Part (
                      part_id INTEGER PRIMARY KEY,
                      status VARCHAR(20) CHECK (status IN ('avaliable', 'borrow-out', 'unavaliable')) NOT NULL,
                      product_name VARCHAR(255) NOT NULL,
                      product_id INTEGER NOT NULL,
                      cost FLOAT NOT NULL,
                      part_name VARCHAR(255) NOT NULL,
                      FOREIGN KEY (product_id) REFERENCES Product(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- 建立 Employee_activity 表
CREATE TABLE Employee_activity (
                                   activity_id SERIAL PRIMARY KEY,
                                   action VARCHAR(20) CHECK (action IN ('Stock-in', 'Stock-out', 'Borrow', 'Return', 'Dispose')) NOT NULL,
    product_id INTEGER NOT NULL,
    employee_id INTEGER NOT NULL,
    operate_time TIMESTAMP NOT NULL,
    part_id INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES Product(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (part_id) REFERENCES Part(part_id) ON UPDATE NO ACTION ON DELETE NO ACTION
);
