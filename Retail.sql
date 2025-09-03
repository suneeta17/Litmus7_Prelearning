#Creating database
DROP DATABASE retail;
CREATE DATABASE retail;

USE retail;

#Table Categories
CREATE TABLE Categories(
	category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(20) NOT NULL);
    
#Table Suppliers
CREATE TABLE Suppliers(
	supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    supplier_name VARCHAR(20) NOT NULL,
    contact_email VARCHAR(30));

#Table Products
CREATE TABLE Products(
	product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(25) NOT NULL,
    category_id INT,
    supplier_id INT,
    price DECIMAL(10,2),
    FOREIGN KEY(category_id) REFERENCES Categories(category_id),
    FOREIGN KEY(supplier_id) REFERENCES Suppliers(supplier_id));
    
#Table Inventory
CREATE TABLE Inventory(
	inventory_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    quantity INT,
    last_updated DATE,
    FOREIGN KEY(product_id) REFERENCES Products(product_id));
    
#Table Orders
CREATE TABLE Orders(
	order_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    order_date DATE,
    quantity_ordered INT,
    total_price DECIMAL(10,2),
    FOREIGN KEY(product_id) REFERENCES Products(product_id));
  
  
-- INSERT STATEMENTS --
-- Insert Categories
INSERT INTO Categories (category_name) VALUES
('Electronics'),
('Home Appliances'),
('Books'),
('Clothing');

-- Insert Suppliers
INSERT INTO Suppliers (supplier_name, contact_email) VALUES
('TechWorld Inc', 'tech@tw.com'),
('HomeNeeds Ltd', 'contact@hn.com'),
('BookBarn', 'info@bb.com'),
('FashionHub', 'support@fh.com'),
('Independent Supplier', 'independent@is.com');

-- Insert Products
INSERT INTO Products (product_name, category_id, supplier_id, price) VALUES
('Smartphone', 1, 1, 29999.99),
('Laptop', 1, 1, 55999.50),
('Microwave', 2, 2, 799.00),
('Refrigerator', 2, 2, 8499.99),
('Novel', 3, 3, 849.99),
('Textbook', 3, 3, 1200.00),
('T-Shirt', 4, 4, 499.50),
('Jeans', 4, 4, 550.00),
('Headphones', 1, NULL, 698.00),
('Blender', 2, NULL, 699.00);

-- Insert Inventory
INSERT INTO Inventory (product_id, quantity, last_updated) VALUES
(1, 50, '2025-08-20'),
(2, 0, '2025-08-20'),
(3, 20, '2025-08-20'),
(4, 5, '2025-08-20'),
(5, 0, '2025-08-20'),
(6, 15, '2025-08-20'),
(7, 2, '2025-08-20'),
(8, 25, '2025-08-20'),
(9, 30, '2025-08-20'),
(10, 0, '2025-08-20');

-- Insert Orders
INSERT INTO Orders (product_id, order_date, quantity_ordered, total_price) VALUES
(1, '2025-08-21', 2, 59999.98),
(2, '2025-08-22', 1, 55999.50),
(3, '2025-08-22', 3, 2397.00),
(5, '2025-08-23', 1, 849.99),
(7, '2025-08-23', 10, 4995.00),
(8, '2025-08-24', 20, 11000.00),
(9, '2025-08-24', 2, 1396.00),
(10, '2025-08-25', 1, 699.00);

SELECT * FROM Categories;

SELECT * FROM Suppliers;

SELECT * FROM Products;

SELECT * FROM Inventory;

SELECT * FROM Orders;

-- TASKS --

-- Task 1: Inner Join
SELECT P.product_id , P.product_name , C.category_name 
FROM Products P 
INNER JOIN Categories C
ON P.category_id = C.category_id;

-- Task 2: Left Join
SELECT P.product_id , P.product_name , S.supplier_name
FROM Products p
LEFT JOIN Suppliers S
ON P.supplier_id = S.supplier_id;

-- Task 3: Right Join
SELECT S.supplier_id, P.product_id , P.product_name
FROM Products P
RIGHT JOIN  Suppliers S
on S.supplier_id = P.supplier_id;

-- Task 4: Full Outer Join
SELECT P.product_id , P.product_name , S.supplier_id , S.supplier_name
FROM Products P
LEFT JOIN Suppliers S
on P.supplier_id = S.supplier_id

UNION

SELECT P.product_id , P.product_name , S.supplier_id , S.supplier_name
FROM Products P
RIGHT JOIN Suppliers S
on P.supplier_id = S.supplier_id; 

-- Task 5: Join with Inventory
SELECT P.product_name , S.supplier_name , I.quantity
FROM Products P
INNER JOIN Suppliers S
ON P.supplier_id = S.supplier_id
INNER JOIN Inventory I
ON P.product_id = I.product_id
WHERE quantity > 0;

-- Task 6: Join with Orders 
SELECT P.product_name ,SUM(O.quantity_ordered) AS "total quantity" , SUM(O.total_price) AS "revenue" 
FROM Products P 
INNER JOIN Orders O 
ON P.product_id = O.product_id 
GROUP BY P.product_name
ORDER BY SUM(O.total_price) DESC;

-- Task 7: Multi-Table Join
SELECT O.order_id,O.order_date,P.product_name,C.category_name,S.supplier_name,O.quantity_ordered,O.total_price
FROM Orders O
LEFT JOIN Products P 
ON O.product_id = P.product_id
LEFT JOIN Categories C
ON P.category_id = C.category_id
LEFT JOIN Suppliers S
ON P.supplier_id = S.supplier_id;

-- Task 8: Conditional Join
SELECT P.product_name,S.supplier_name,I.quantity 
FROM Products P
INNER JOIN Inventory I 
ON P.product_id = I.product_id
LEFT JOIN Suppliers S 
ON P.supplier_id = S.supplier_id
WHERE I.quantity < 10;

-- Task 9: 

-- Find suppliers who supply products in multiple categories
SELECT S.supplier_id , S.supplier_name
FROM suppliers S
JOIN Products P
ON P.supplier_id = S.supplier_id
GROUP BY S.supplier_id
HAVING COUNT(DISTINCT P.category_id) > 1;


-- Find products that have never been ordered
SELECT product_id , product_name
FROM Products 
WHERE product_id NOT IN (
SELECT product_id from Orders);

-- Find the catgory with highest total sales
SELECT C.category_id , C.category_name , SUM(O.total_price) AS "total_sales"
FROM Categories C
JOIN Products P 
ON C.category_id = P.category_id
JOIN Orders O
ON P.product_id = O.product_id
GROUP BY C.category_id 
ORDER BY total_sales DESC
LIMIT 1;














    

