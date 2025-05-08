-- SQL Query Examples for Interviews
-- This file contains examples of various SQL queries focusing on:
-- - GROUP BY, HAVING
-- - ORDER BY, SORT
-- - Aggregate functions (AVG, SUM, COUNT)
-- - JOINs
-- - Multiple table operations
-- - Subqueries and combinations of these concepts

-- Assume we have the following tables:
-- 1. Employees (emp_id, first_name, last_name, hire_date, salary, dept_id, manager_id)
-- 2. Departments (dept_id, dept_name, location)
-- 3. Products (product_id, product_name, category, price, stock_quantity)
-- 4. Orders (order_id, customer_id, order_date, total_amount)
-- 5. OrderDetails (order_detail_id, order_id, product_id, quantity, unit_price)
-- 6. Customers (customer_id, first_name, last_name, email, phone, address, city, country)

-- Query 1: Basic SELECT with WHERE clause
-- Retrieve all employees with a salary greater than 50000
SELECT emp_id, first_name, last_name, salary
FROM Employees
WHERE salary > 50000;

-- Query 2: ORDER BY
-- Retrieve all employees ordered by salary in descending order
SELECT emp_id, first_name, last_name, salary
FROM Employees
ORDER BY salary DESC;

-- Query 3: Basic GROUP BY with COUNT
-- Count the number of employees in each department
SELECT dept_id, COUNT(*) as employee_count
FROM Employees
GROUP BY dept_id;

-- Query 4: GROUP BY with AVG and ORDER BY
-- Calculate the average salary by department, sorted by average salary in descending order
SELECT dept_id, AVG(salary) as avg_salary
FROM Employees
GROUP BY dept_id
ORDER BY avg_salary DESC;

-- Query 5: GROUP BY with HAVING clause
-- Find departments with more than 5 employees
SELECT dept_id, COUNT(*) as employee_count
FROM Employees
GROUP BY dept_id
HAVING COUNT(*) > 5;

-- Query 6: INNER JOIN
-- Retrieve employee names along with their department names
SELECT e.emp_id, e.first_name, e.last_name, d.dept_name
FROM Employees e
INNER JOIN Departments d ON e.dept_id = d.dept_id;

-- Query 7: LEFT JOIN
-- Retrieve all departments and their employees (including departments with no employees)
SELECT d.dept_id, d.dept_name, e.emp_id, e.first_name, e.last_name
FROM Departments d
LEFT JOIN Employees e ON d.dept_id = e.dept_id;

-- Query 8: Multiple JOINs
-- Retrieve employees, their departments, and their managers
SELECT e.emp_id, e.first_name, e.last_name, d.dept_name, 
       m.first_name as manager_first_name, m.last_name as manager_last_name
FROM Employees e
JOIN Departments d ON e.dept_id = d.dept_id
LEFT JOIN Employees m ON e.manager_id = m.emp_id;

-- Query 9: Subquery in WHERE clause
-- Find employees with salary higher than the average salary
SELECT emp_id, first_name, last_name, salary
FROM Employees
WHERE salary > (SELECT AVG(salary) FROM Employees);

-- Query 10: Subquery with IN
-- Find all employees who work in departments located in 'New York'
SELECT emp_id, first_name, last_name
FROM Employees
WHERE dept_id IN (SELECT dept_id FROM Departments WHERE location = 'New York');

-- Query 11: GROUP BY with multiple columns
-- Count employees by department and hire year
SELECT dept_id, YEAR(hire_date) as hire_year, COUNT(*) as employee_count
FROM Employees
GROUP BY dept_id, YEAR(hire_date)
ORDER BY dept_id, hire_year;

-- Query 12: GROUP BY with multiple aggregate functions
-- Calculate min, max, and avg salary by department
SELECT dept_id, 
       MIN(salary) as min_salary, 
       MAX(salary) as max_salary, 
       AVG(salary) as avg_salary
FROM Employees
GROUP BY dept_id;

-- Query 13: HAVING with multiple conditions
-- Find departments where the average salary is between 40000 and 60000
-- and there are at least 3 employees
SELECT dept_id, AVG(salary) as avg_salary, COUNT(*) as employee_count
FROM Employees
GROUP BY dept_id
HAVING AVG(salary) BETWEEN 40000 AND 60000 AND COUNT(*) >= 3;

-- Query 14: INNER JOIN with GROUP BY and aggregate functions
-- Calculate the total salary budget for each department including department name
SELECT d.dept_id, d.dept_name, SUM(e.salary) as total_salary_budget
FROM Departments d
INNER JOIN Employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name
ORDER BY total_salary_budget DESC;

-- Query 15: Self JOIN
-- Find employees who have the same salary
SELECT e1.emp_id, e1.first_name, e1.last_name, e1.salary
FROM Employees e1
JOIN Employees e2 ON e1.salary = e2.salary AND e1.emp_id <> e2.emp_id
ORDER BY e1.salary;

-- Query 16: Multiple JOINs with GROUP BY and aggregate function
-- Calculate the total number of employees and average salary by department location
SELECT d.location, COUNT(e.emp_id) as employee_count, AVG(e.salary) as avg_salary
FROM Departments d
LEFT JOIN Employees e ON d.dept_id = e.dept_id
GROUP BY d.location
ORDER BY employee_count DESC;

-- Query 17: Subquery in the FROM clause
-- Find the departments with the highest average salary
SELECT dept_subquery.dept_id, dept_subquery.avg_salary
FROM (
    SELECT dept_id, AVG(salary) as avg_salary
    FROM Employees
    GROUP BY dept_id
) dept_subquery
ORDER BY dept_subquery.avg_salary DESC
LIMIT 1;

-- Query 18: Calculate the percentage of total
-- Calculate what percentage each department's salary budget is of the total salary budget
SELECT d.dept_id, d.dept_name, 
       SUM(e.salary) as dept_salary_budget,
       (SUM(e.salary) * 100.0 / (SELECT SUM(salary) FROM Employees)) as percentage_of_total
FROM Departments d
JOIN Employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name
ORDER BY percentage_of_total DESC;

-- Query 19: HAVING with subquery
-- Find departments where the average salary is higher than the company's average salary
SELECT dept_id, AVG(salary) as dept_avg_salary
FROM Employees
GROUP BY dept_id
HAVING AVG(salary) > (SELECT AVG(salary) FROM Employees);

-- Query 20: Window functions with OVER and PARTITION BY
-- Calculate the running total of salary by department
SELECT emp_id, first_name, last_name, dept_id, salary,
       SUM(salary) OVER (PARTITION BY dept_id ORDER BY emp_id) as running_total
FROM Employees;

-- Query 21: Basic query on product sales
-- Retrieve the top 5 best-selling products
SELECT p.product_id, p.product_name, SUM(od.quantity) as total_sold
FROM Products p
JOIN OrderDetails od ON p.product_id = od.product_id
GROUP BY p.product_id, p.product_name
ORDER BY total_sold DESC
LIMIT 5;

-- Query 22: Calculate revenue by product category
-- Find the total revenue generated by each product category
SELECT p.category, SUM(od.quantity * od.unit_price) as total_revenue
FROM Products p
JOIN OrderDetails od ON p.product_id = od.product_id
GROUP BY p.category
ORDER BY total_revenue DESC;

-- Query 23: Time-based analysis
-- Calculate sales by month and year
SELECT YEAR(o.order_date) as year, MONTH(o.order_date) as month, 
       SUM(o.total_amount) as monthly_sales
FROM Orders o
GROUP BY YEAR(o.order_date), MONTH(o.order_date)
ORDER BY year, month;

-- Query 24: Customer analysis
-- Find customers who have made more than 3 orders
SELECT c.customer_id, c.first_name, c.last_name, COUNT(o.order_id) as order_count
FROM Customers c
JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.first_name, c.last_name
HAVING COUNT(o.order_id) > 3
ORDER BY order_count DESC;

-- Query 25: Complex JOIN with filtering
-- Find all products that have been ordered by customers from 'USA'
SELECT DISTINCT p.product_id, p.product_name
FROM Products p
JOIN OrderDetails od ON p.product_id = od.product_id
JOIN Orders o ON od.order_id = o.order_id
JOIN Customers c ON o.customer_id = c.customer_id
WHERE c.country = 'USA';

-- Query 26: Inventory value calculation
-- Calculate the total value of inventory by product category
SELECT p.category, SUM(p.price * p.stock_quantity) as inventory_value
FROM Products p
GROUP BY p.category
ORDER BY inventory_value DESC;

-- Query 27: Customer spending analysis
-- Analyze average order value by customer
SELECT c.customer_id, c.first_name, c.last_name, 
       COUNT(o.order_id) as order_count,
       AVG(o.total_amount) as avg_order_value
FROM Customers c
JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.first_name, c.last_name
ORDER BY avg_order_value DESC;

-- Query 28: Year-over-year analysis
-- Compare sales from this year vs last year by month
SELECT 
    MONTH(current_year.order_date) as month,
    SUM(current_year.total_amount) as current_year_sales,
    SUM(previous_year.total_amount) as previous_year_sales,
    (SUM(current_year.total_amount) - SUM(previous_year.total_amount)) as sales_growth
FROM 
    Orders current_year
JOIN 
    Orders previous_year ON MONTH(current_year.order_date) = MONTH(previous_year.order_date)
WHERE 
    YEAR(current_year.order_date) = YEAR(CURRENT_DATE) AND
    YEAR(previous_year.order_date) = YEAR(CURRENT_DATE) - 1
GROUP BY 
    MONTH(current_year.order_date)
ORDER BY 
    month;

-- Query 29: Employee hierarchy
-- Display the employee hierarchy (manager to employee relationship)
WITH RECURSIVE EmployeeHierarchy AS (
    -- Base case: employees with no manager (top level)
    SELECT emp_id, first_name, last_name, manager_id, 1 as level
    FROM Employees
    WHERE manager_id IS NULL
    
    UNION ALL
    
    -- Recursive case: employees with managers
    SELECT e.emp_id, e.first_name, e.last_name, e.manager_id, eh.level + 1
    FROM Employees e
    JOIN EmployeeHierarchy eh ON e.manager_id = eh.emp_id
)
SELECT emp_id, first_name, last_name, level
FROM EmployeeHierarchy
ORDER BY level, emp_id;

-- Query 30: Advanced analysis with window functions
-- Calculate the rank of each product by sales within its category
SELECT p.product_id, p.product_name, p.category,
       SUM(od.quantity) as total_quantity,
       RANK() OVER (PARTITION BY p.category ORDER BY SUM(od.quantity) DESC) as rank_in_category
FROM Products p
JOIN OrderDetails od ON p.product_id = od.product_id
GROUP BY p.product_id, p.product_name, p.category;