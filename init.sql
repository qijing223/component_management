-- Define ENUM types used in the schema
CREATE TYPE user_type_enum AS ENUM ('Admin', 'Regular User');  -- User roles
CREATE TYPE action_enum AS ENUM ('Stock-in', 'Stock-out', 'Borrow', 'Return', 'Dispose');  -- Activity types
CREATE TYPE part_status_enum AS ENUM ('borrow-out', 'unavailable', 'available');  -- Status of parts

-- Employee table stores user information
CREATE TABLE Employee (
    employee_id INTEGER PRIMARY KEY,  -- Unique employee ID
    employee_name VARCHAR(255) NOT NULL,  -- Employee's name
    department VARCHAR(255) NOT NULL,  -- Department the employee belongs to
    password VARCHAR(255) NOT NULL,  -- User password
    user_type user_type_enum NOT NULL  -- Role of the user (Admin or Regular User)
);

-- Employee_activity table logs actions performed by employees
CREATE TABLE Employee_activity (
    activity_id SERIAL PRIMARY KEY,  -- Unique ID for each activity
    action action_enum NOT NULL,  -- Type of action performed
    product_id INTEGER NOT NULL,  -- ID of the product involved
    employee_id INTEGER NOT NULL,  -- ID of the employee who performed the action
    operate_time TIMESTAMP NOT NULL,  -- Timestamp when the action occurred
    part_id INTEGER NOT NULL  -- ID of the part involved in the action
);

-- Part table stores information about individual parts
CREATE TABLE Part (
    part_number INTEGER,  -- Number used to group or reference parts
    part_id INTEGER PRIMARY KEY,  -- Unique ID for the part
    part_name VARCHAR(255) NOT NULL,  -- Name of the part
    borrowed_employee_id INTEGER,  -- ID of the employee who borrowed the part, if any
    status part_status_enum NOT NULL,  -- Current status of the part
    cost FLOAT NOT NULL  -- Cost of the part
);

-- Product table contains information about products that consist of parts
CREATE TABLE Product (
    product_id SERIAL PRIMARY KEY,  -- Unique ID for each product
    product_name VARCHAR(255) NOT NULL,  -- Product name
    number_part_in_stock INTEGER NOT NULL,  -- Number of parts currently in stock
    total_cost FLOAT NOT NULL,  -- Total cost of all parts in the product
    number_part_check_out INTEGER NOT NULL,  -- Number of parts currently checked out
    lead_time TIMESTAMP NOT NULL,  -- Lead time for the product
    part_list JSON  -- JSON list detailing which parts are included in the product
);

-- Returned_part table records parts that have been returned
CREATE TABLE Returned_part (
    part_id SERIAL PRIMARY KEY,  -- Unique ID for each returned part
    part_name VARCHAR(255) NOT NULL,  -- Name of the returned part
    return_time TIMESTAMP NOT NULL,  -- When the part was returned
    borrow_employee_id INTEGER NOT NULL  -- ID of the employee who returned the part
);
