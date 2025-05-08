package com.interview.problems.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Examples of using JDBC to execute SQL queries
 * Note: This is for reference only and would require appropriate database driver and connection
 */
public class JdbcExamples {
    
    // Connection information (replace with your actual database info)
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employees_db";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    
    /**
     * Creates a database connection
     * @return a database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
    
    /**
     * Close database resources safely
     * @param connection the database connection to close
     * @param statement the SQL statement to close
     * @param resultSet the result set to close
     */
    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Example 1: Execute a simple SELECT query
     * @param departmentId the department ID to filter by
     * @return a list of employee names in the department
     */
    public static List<String> getEmployeesByDepartment(int departmentId) {
        List<String> employees = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            // SQL query with parameter
            String sql = "SELECT first_name, last_name FROM employees WHERE dept_id = ?";
            
            statement = connection.prepareStatement(sql);
            statement.setInt(1, departmentId);
            
            resultSet = statement.executeQuery();
            
            // Process the result set
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                employees.add(firstName + " " + lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
        
        return employees;
    }
    
    /**
     * Example 2: Execute an INSERT statement
     * @param firstName the employee's first name
     * @param lastName the employee's last name
     * @param salary the employee's salary
     * @param departmentId the employee's department ID
     * @return true if the insert was successful, false otherwise
     */
    public static boolean addEmployee(String firstName, String lastName, double salary, int departmentId) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = getConnection();
            
            // SQL insert statement with parameters
            String sql = "INSERT INTO employees (first_name, last_name, salary, dept_id) VALUES (?, ?, ?, ?)";
            
            statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setDouble(3, salary);
            statement.setInt(4, departmentId);
            
            // Execute the insert statement
            int rowsAffected = statement.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    /**
     * Example 3: Execute an UPDATE statement
     * @param employeeId the ID of the employee to update
     * @param newSalary the new salary for the employee
     * @return true if the update was successful, false otherwise
     */
    public static boolean updateEmployeeSalary(int employeeId, double newSalary) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = getConnection();
            
            // SQL update statement with parameters
            String sql = "UPDATE employees SET salary = ? WHERE emp_id = ?";
            
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, newSalary);
            statement.setInt(2, employeeId);
            
            // Execute the update statement
            int rowsAffected = statement.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    /**
     * Example 4: Execute a DELETE statement
     * @param employeeId the ID of the employee to delete
     * @return true if the delete was successful, false otherwise
     */
    public static boolean deleteEmployee(int employeeId) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = getConnection();
            
            // SQL delete statement with parameter
            String sql = "DELETE FROM employees WHERE emp_id = ?";
            
            statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);
            
            // Execute the delete statement
            int rowsAffected = statement.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    /**
     * Example 5: Execute a query with JOIN
     * @return a list of employees with their department names
     */
    public static List<Map<String, Object>> getEmployeesWithDepartments() {
        List<Map<String, Object>> result = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            // SQL query with JOIN
            String sql = "SELECT e.emp_id, e.first_name, e.last_name, d.dept_name " +
                         "FROM employees e " +
                         "INNER JOIN departments d ON e.dept_id = d.dept_id";
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            // Process the result set
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("employeeId", resultSet.getInt("emp_id"));
                row.put("firstName", resultSet.getString("first_name"));
                row.put("lastName", resultSet.getString("last_name"));
                row.put("department", resultSet.getString("dept_name"));
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
        
        return result;
    }
    
    /**
     * Example 6: Execute a GROUP BY query
     * @return a map of department IDs to employee counts
     */
    public static Map<Integer, Integer> getEmployeeCountByDepartment() {
        Map<Integer, Integer> departmentCounts = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            // SQL query with GROUP BY
            String sql = "SELECT dept_id, COUNT(*) as employee_count " +
                         "FROM employees " +
                         "GROUP BY dept_id";
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            // Process the result set
            while (resultSet.next()) {
                int departmentId = resultSet.getInt("dept_id");
                int count = resultSet.getInt("employee_count");
                departmentCounts.put(departmentId, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
        
        return departmentCounts;
    }
    
    /**
     * Example 7: Execute a query with GROUP BY and HAVING
     * @param minEmployees the minimum number of employees a department must have
     * @return a list of department IDs with at least the specified number of employees
     */
    public static List<Integer> getDepartmentsWithMinEmployees(int minEmployees) {
        List<Integer> departments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            // SQL query with GROUP BY and HAVING
            String sql = "SELECT dept_id " +
                         "FROM employees " +
                         "GROUP BY dept_id " +
                         "HAVING COUNT(*) >= ?";
            
            statement = connection.prepareStatement(sql);
            statement.setInt(1, minEmployees);
            
            resultSet = statement.executeQuery();
            
            // Process the result set
            while (resultSet.next()) {
                departments.add(resultSet.getInt("dept_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
        
        return departments;
    }
    
    /**
     * Example 8: Execute a query with ORDER BY
     * @param ascending true for ascending order, false for descending
     * @return a list of employees ordered by salary
     */
    public static List<Map<String, Object>> getEmployeesOrderedBySalary(boolean ascending) {
        List<Map<String, Object>> employees = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            // SQL query with ORDER BY
            String sql = "SELECT emp_id, first_name, last_name, salary " +
                         "FROM employees " +
                         "ORDER BY salary " + (ascending ? "ASC" : "DESC");
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            // Process the result set
            while (resultSet.next()) {
                Map<String, Object> employee = new HashMap<>();
                employee.put("employeeId", resultSet.getInt("emp_id"));
                employee.put("firstName", resultSet.getString("first_name"));
                employee.put("lastName", resultSet.getString("last_name"));
                employee.put("salary", resultSet.getDouble("salary"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
        
        return employees;
    }
    
    /**
     * Example 9: Execute a query with aggregate functions
     * @return a map with aggregate values (min, max, avg, sum, count)
     */
    public static Map<String, Object> getSalaryStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            // SQL query with aggregate functions
            String sql = "SELECT " +
                         "MIN(salary) as min_salary, " +
                         "MAX(salary) as max_salary, " +
                         "AVG(salary) as avg_salary, " +
                         "SUM(salary) as total_salary, " +
                         "COUNT(*) as employee_count " +
                         "FROM employees";
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            // Process the result set
            if (resultSet.next()) {
                statistics.put("minSalary", resultSet.getDouble("min_salary"));
                statistics.put("maxSalary", resultSet.getDouble("max_salary"));
                statistics.put("avgSalary", resultSet.getDouble("avg_salary"));
                statistics.put("totalSalary", resultSet.getDouble("total_salary"));
                statistics.put("employeeCount", resultSet.getInt("employee_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
        
        return statistics;
    }
    
    /**
     * Example 10: Execute a query with subquery
     * @return a list of employees who earn more than the average salary
     */
    public static List<Map<String, Object>> getEmployeesAboveAverageSalary() {
        List<Map<String, Object>> employees = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            // SQL query with subquery
            String sql = "SELECT emp_id, first_name, last_name, salary " +
                         "FROM employees " +
                         "WHERE salary > (SELECT AVG(salary) FROM employees)";
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            // Process the result set
            while (resultSet.next()) {
                Map<String, Object> employee = new HashMap<>();
                employee.put("employeeId", resultSet.getInt("emp_id"));
                employee.put("firstName", resultSet.getString("first_name"));
                employee.put("lastName", resultSet.getString("last_name"));
                employee.put("salary", resultSet.getDouble("salary"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
        
        return employees;
    }
    
    /**
     * Example 11: Execute a query with multiple JOINs
     * @return a list of employees with their department and manager information
     */
    public static List<Map<String, Object>> getEmployeesWithDepartmentsAndManagers() {
        List<Map<String, Object>> result = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            // SQL query with multiple JOINs
            String sql = "SELECT e.emp_id, e.first_name, e.last_name, " +
                         "d.dept_name, " +
                         "m.first_name as manager_first_name, m.last_name as manager_last_name " +
                         "FROM employees e " +
                         "INNER JOIN departments d ON e.dept_id = d.dept_id " +
                         "LEFT JOIN employees m ON e.manager_id = m.emp_id";
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            // Process the result set
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("employeeId", resultSet.getInt("emp_id"));
                row.put("firstName", resultSet.getString("first_name"));
                row.put("lastName", resultSet.getString("last_name"));
                row.put("department", resultSet.getString("dept_name"));
                row.put("managerFirstName", resultSet.getString("manager_first_name"));
                row.put("managerLastName", resultSet.getString("manager_last_name"));
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
        
        return result;
    }
    
    /**
     * Example 12: Execute a transaction with multiple statements
     * @param employeeId the ID of the employee to transfer
     * @param oldDepartmentId the ID of the current department
     * @param newDepartmentId the ID of the new department
     * @return true if the transaction was successful, false otherwise
     */
    public static boolean transferEmployee(int employeeId, int oldDepartmentId, int newDepartmentId) {
        Connection connection = null;
        PreparedStatement updateEmployeeStmt = null;
        PreparedStatement updateOldDeptStmt = null;
        PreparedStatement updateNewDeptStmt = null;
        
        try {
            connection = getConnection();
            
            // Disable auto-commit to start a transaction
            connection.setAutoCommit(false);
            
            // Update employee's department
            String updateEmployeeSql = "UPDATE employees SET dept_id = ? WHERE emp_id = ?";
            updateEmployeeStmt = connection.prepareStatement(updateEmployeeSql);
            updateEmployeeStmt.setInt(1, newDepartmentId);
            updateEmployeeStmt.setInt(2, employeeId);
            updateEmployeeStmt.executeUpdate();
            
            // Update old department's employee count
            String updateOldDeptSql = "UPDATE department_stats SET employee_count = employee_count - 1 " +
                                     "WHERE dept_id = ?";
            updateOldDeptStmt = connection.prepareStatement(updateOldDeptSql);
            updateOldDeptStmt.setInt(1, oldDepartmentId);
            updateOldDeptStmt.executeUpdate();
            
            // Update new department's employee count
            String updateNewDeptSql = "UPDATE department_stats SET employee_count = employee_count + 1 " +
                                     "WHERE dept_id = ?";
            updateNewDeptStmt = connection.prepareStatement(updateNewDeptSql);
            updateNewDeptStmt.setInt(1, newDepartmentId);
            updateNewDeptStmt.executeUpdate();
            
            // Commit the transaction
            connection.commit();
            
            return true;
        } catch (SQLException e) {
            // Rollback the transaction in case of error
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            // Restore auto-commit mode
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException autoCommitEx) {
                    autoCommitEx.printStackTrace();
                }
            }
            
            // Close resources
            closeResources(null, updateEmployeeStmt, null);
            closeResources(null, updateOldDeptStmt, null);
            closeResources(connection, updateNewDeptStmt, null);
        }
    }
    
    /**
     * Example 13: Execute a batch update
     * @param employees a list of employee information to insert
     * @return an array of update counts
     */
    public static int[] addEmployeesBatch(List<Map<String, Object>> employees) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = getConnection();
            
            // SQL insert statement
            String sql = "INSERT INTO employees (first_name, last_name, salary, dept_id) VALUES (?, ?, ?, ?)";
            
            statement = connection.prepareStatement(sql);
            
            // Add batch for each employee
            for (Map<String, Object> employee : employees) {
                statement.setString(1, (String) employee.get("firstName"));
                statement.setString(2, (String) employee.get("lastName"));
                statement.setDouble(3, (Double) employee.get("salary"));
                statement.setInt(4, (Integer) employee.get("departmentId"));
                
                statement.addBatch();
            }
            
            // Execute the batch
            return statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            return new int[0];
        } finally {
            closeResources(connection, statement, null);
        }
    }
    
    /**
     * Example 14: Execute a prepared statement with IN clause
     * @param departmentIds a list of department IDs to filter by
     * @return a list of employees in the specified departments
     */
    public static List<Map<String, Object>> getEmployeesInDepartments(List<Integer> departmentIds) {
        List<Map<String, Object>> employees = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            
            // Build the IN clause with placeholders
            StringBuilder inClause = new StringBuilder();
            for (int i = 0; i < departmentIds.size(); i++) {
                if (i > 0) {
                    inClause.append(", ");
                }
                inClause.append("?");
            }
            
            // SQL query with IN clause
            String sql = "SELECT emp_id, first_name, last_name, dept_id " +
                         "FROM employees " +
                         "WHERE dept_id IN (" + inClause.toString() + ")";
            
            statement = connection.prepareStatement(sql);
            
            // Set parameters for IN clause
            for (int i = 0; i < departmentIds.size(); i++) {
                statement.setInt(i + 1, departmentIds.get(i));
            }
            
            resultSet = statement.executeQuery();
            
            // Process the result set
            while (resultSet.next()) {
                Map<String, Object> employee = new HashMap<>();
                employee.put("employeeId", resultSet.getInt("emp_id"));
                employee.put("firstName", resultSet.getString("first_name"));
                employee.put("lastName", resultSet.getString("last_name"));
                employee.put("departmentId", resultSet.getInt("dept_id"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, statement, resultSet);
        }
        
        return employees;
    }
    
    public static void main(String[] args) {
        System.out.println("===== JDBC Examples =====");
        System.out.println("Note: These examples require a proper database connection and schema.");
        System.out.println("They are provided for reference only.");
    }
}