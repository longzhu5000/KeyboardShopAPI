package css475;

import java.sql.*;

public class DatabaseAPI {
	private Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	private String user = "";
	private String pass = "";
	//private String address1 = "";
	//private String address2 = "";
	DatabaseAPI(){
		
	}

	// utility functions
	public void getConnection(String address, String user, String password) {
		try {
			// Class.forName("org.postgresql.Driver");
			this.user = user;
			this.pass = password;
			//this.address1 = address;
			conn = DriverManager.getConnection(address, user, password);
			//conn.setAutoCommit(false);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Connection Failed!");
		}
	}
	
	public void closeConnections() {
		try {
			conn.close();
			rs.close();
			ps.close();
		} catch(Exception e) {
			
		}
	}
	
	/*
	public void rollback() {
		try {
			conn.rollback();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	*/

	public void createDB() {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE DATABASE kbshop;");
			conn = null;
			getConnection("jdbc:postgresql://localhost:5432/kbshop", user, pass);
			Statement newstmt = conn.createStatement();
			newstmt.executeUpdate("CREATE TABLE Customer("
					+ "ID  		 SERIAL not null,"
					+ "CustNumber     VARCHAR(8) not null unique,"
					+ "FirstName   	 VARCHAR(15) not null,"
					+ "LastName    	 VARCHAR(15) not null,"
					+ "Email      	 VARCHAR(30),"
					+ "PhoneNumber   	 INTEGER not null,"
					+ "StreetAddress	 VARCHAR(30) not null,"
					+ "City	 	 VARCHAR(50) not null,"
					+ "State		 CHAR(2) not null,"
					+ "Zip		 INTEGER not null,"
					+ "Primary Key  	 (ID));");
			
			newstmt.executeUpdate("CREATE TABLE Status ("
				    + "ID             INTEGER not null,"
				    + "Name             VARCHAR(20) not null,"
				    + "Primary Key         (ID));");
			
			newstmt.executeUpdate("CREATE TABLE Employee ("
				    + "ID               SERIAL not null,"
				    + "EmpNumber         VARCHAR(8) not null unique,"
				    + "FirstName            VARCHAR(15) not null,"
				    + "LastName             VARCHAR(15) not null,"
				    + "Email               VARCHAR(30) not null,"
				    + "PhoneNumber        INTEGER not null,"
				    + "Primary Key       (ID));");
			
			newstmt.executeUpdate("CREATE TABLE Orders ("
				    + "ID             SERIAL not null,"
				    + "OrderKey         VARCHAR(8) not null unique,"
				    + "CustomerID         INTEGER not null,"
				    + "StatusID         INTEGER not null,"
				    + "StartDate         TIMESTAMP not null,"
				    + "ClosedDate         TIMESTAMP ,"
				    + "AssignedTo         INTEGER ,"
				    + "Primary Key         (ID),"
				    + "Foreign Key         (CustomerID) references Customer(ID)"
				    + "             Deferrable Initially Deferred,"
				    + "Foreign Key         (StatusID) references Status(ID)"
				    + "             Deferrable Initially Deferred,"
				    + "Foreign Key         (AssignedTo) references Employee(ID)"
				    + "             Deferrable Initially Deferred);");
			
			newstmt.executeUpdate("CREATE TABLE Keyboard ("
				    + "SKU             CHAR(8) not null,"
				    + "Price         INTEGER not null,"
				    + "Name             VARCHAR(30) not null,"
				    + "Description         VARCHAR(300) not null,"
				    + "Count         INTEGER not null,"
				    + "Primary Key         (SKU));");
			
			newstmt.executeUpdate("CREATE TABLE OrderItem ("
				    + "ID             SERIAL not null," 
				    + "OrderID         INTEGER not null,"
				    + "ProductSKU         CHAR(8) not null,"
				    + "Quantity         Integer,"
				    + "Primary Key         (ID),"
				    + "Foreign Key         (OrderID) references Orders(ID)"
				    + "             Deferrable Initially Deferred,"
				    + "Foreign Key         (ProductSKU) references Keyboard(SKU)"
				    + "             Deferrable Initially Deferred);");
			
			stmt.close();
			newstmt.close();
		} catch(Exception e) {
			System.out.println(e);
		}

	}
	
	public void loadExampleData() {
		try {
			Statement stmt = conn.createStatement();
			
			stmt.executeUpdate("INSERT INTO Status (ID, name)"
					+ "VALUES (1, 'OPEN'),"
					+ "    (2, 'INPROGRESS'),"
					+ "    (3, 'INTRANSIT'),"
					+ "    (4, 'COMPLETE'),"
					+ "    (5, 'CANCELLED');");

					stmt.executeUpdate("INSERT INTO Keyboard (SKU, price, name, description, count)"
					+ "VALUES ('PIN1899L', '1899', 'pinkl', 'Lifestyle model in pink', '15'),"
					+ "    ('GRE1899L', '1899', 'greenl', 'Lifestyle model in green', '15'),"
					+ "    ('ORA1899L', '1899', 'orangel', 'Lifestyle model in orange', '15'),"
					+ "    ('WHI2399W', '2399', 'whitew', 'Workplace model in white', '10'),"
					+ "    ('GRA2399W', '2399', 'grayw', 'Workplace model in gray', '10'),"
					+ "    ('TAN2399W', '2399', 'tanw', 'Workplace model in tan', '10'),"
					+ "    ('BLA5999G', '5999', 'blackg', 'Gaming model in black', '5'),"
					+ "    ('SKY5999G', '5999', 'skyg', 'Gaming model in sky blue', '5'),"
					+ "    ('NEO5999G', '5999', 'neong', 'Gaming model in multicolored neon', '5');");

					stmt.executeUpdate("INSERT INTO Employee (empNumber, firstName, lastName, email, phoneNumber)"
					+ "VALUES ('EM000001', 'John', 'Smith', 'johnsmith@gmail.com', '1223344'),"
					+ "    ('EM000002', 'Joe', 'Jackson', 'joejackson@gmail.com', '1225566'),"
					+ "    ('EM000003', 'Alice', 'Jane', 'alicejane@gmail.com', '5667788'),"
					+ "    ('EM000004', 'Mark', 'Thomas', 'markthomas@gmail.com', '9090909');");

					stmt.executeUpdate("INSERT INTO Customer (custNumber, firstName, lastName, email, phoneNumber, streetAddress, city, state, zip)"
					+ "VALUES ('CU000001', 'Rick', 'Sanchez', 'ricksanchez@gmail.com', '1012345', '101 Generic Street', 'Kirkland', 'WA', '10001'),"
					+ "    ('CU000002', 'Blake', 'Sanders', 'blakesanders@gmail.com', '1022345', '102 Generic Ave', 'Bellevue', 'WA', '20012'),"
					+ "    ('CU000003', 'Patricia', 'Jones', 'patjones@gmail.com', '1032345', '567 8th Ave SE', 'Seattle', 'WA', '32345');");

					stmt.executeUpdate("INSERT INTO Customer (custNumber, firstName, lastName, phoneNumber, streetAddress, city, state, zip)"
					+ "VALUES ('CU000004', 'Kyle', 'Howell', '1042345', '311 3rd ST', 'Redmond', 'WA', '40123'),"
					+ "    ('CU000005', 'Faith', 'Collins', '1052345', '707 Fun Street', 'Tacoma', 'WA', '51234');");

					stmt.executeUpdate("INSERT INTO Orders (OrderKey, customerID, statusID, startDate)"
					+ "VALUES ('OK000001',"
					+ "    (SELECT id FROM Customer WHERE custNumber = 'CU000001'),"
					+ "    (SELECT id FROM Status WHERE name = 'OPEN'),"
					+ "    '2022-03-10 8:00 AM');");

					stmt.executeUpdate("INSERT INTO Orders (OrderKey, customerID, statusID, startDate)"
					+ "VALUES ('OK000002',"
					+ "    (SELECT id FROM Customer WHERE custNumber = 'CU000001'),"
					+ "    (SELECT id FROM Status WHERE name = 'OPEN'),"
					+ "    '2022-03-10 8:05 AM');");

					stmt.executeUpdate("INSERT INTO Orders (OrderKey, customerID, statusID, startDate, assignedTo)"
					+ "VALUES ('OK000003',"
					+ "    (SELECT id FROM Customer WHERE custNumber = 'CU000002'),"
					+ "    (SELECT id FROM Status WHERE name = 'INPROGRESS'),"
					+ "    '2022-03-10 8:30 AM',"
					+ "    (SELECT id FROM Employee WHERE empNumber = 'EM000001'));");

					stmt.executeUpdate("INSERT INTO Orders (OrderKey, customerID, statusID, startDate, assignedTo)"
					+ "VALUES ('OK000004',"
					+ "    (SELECT id FROM Customer WHERE custNumber = 'CU000003'),"
					+ "    (SELECT id FROM Status WHERE name = 'OPEN'),"
					+ "    '2022-03-10 10:00 AM',"
					+ "    (SELECT id FROM Employee WHERE empNumber = 'EM000002'));");

					stmt.executeUpdate("INSERT INTO OrderItem (orderID, productSKU, quantity)"
							+ "VALUES ((SELECT id FROM Orders WHERE orderKey = 'OK000001'), 'GRE1899L', 1),"
							+ "    ((SELECT id FROM Orders WHERE orderKey = 'OK000002'), 'ORA1899L', 1),"
							+ "    ((SELECT id FROM Orders WHERE orderKey = 'OK000003'), 'WHI2399W', 3),"
							+ "    ((SELECT id FROM Orders WHERE orderKey = 'OK000004'), 'BLA5999G', 1),"
							+ "    ((SELECT id FROM Orders WHERE orderKey = 'OK000004'), 'NEO5999G', 2);");
			
			stmt.close();
			
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public void dropDB() {
		try {
			conn.close();
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "CSS475");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP DATABASE kbshop;");
			System.out.println("Database Dropped");
			
			stmt.close();
		} catch(Exception e) {
			System.out.println(e);	
		}

	}
	
	// function to display data from resultset, separating values with :
	public void display(ResultSet rs) {
		try {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int columnCount = rsMetaData.getColumnCount();
			String output = "";
			for (int i = 1; i <= columnCount; i++) {
				if(i == columnCount) {
					output += rsMetaData.getColumnName(i);
				}
				else {
					output += rsMetaData.getColumnName(i) + ", ";
				}
			}
			output += "\n";
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++)
				{
					if(i == columnCount) {
						output += rs.getString(i);
					}
					else {
						output += rs.getString(i) + ", ";
					}
				}
				System.out.println(output);
				output = "";
			}
			}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	// core functions
	
	// queries
	public ResultSet viewCustomer(String custNum) {
		try {
			ps = conn.prepareStatement("SELECT * FROM Customer WHERE custnumber = ?");
			ps.setString(1, custNum);
			
			rs = ps.executeQuery();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return rs;
	}
	
	public ResultSet viewCustomers() {
		try {
			ps = conn.prepareStatement("SELECT * FROM Customer");
		
			rs = ps.executeQuery();
		} catch(Exception e) {
			System.out.println(e);
		}
		return rs;
	
	}
	
	public ResultSet checkInventory(String sku)
	{
		try {
			ps = conn.prepareStatement("SELECT count FROM Keyboard WHERE sku = ?");
			ps.setString(1, sku);
			
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public ResultSet checkEntireInventory() {
		try {
			ps = conn.prepareStatement("SELECT * FROM Keyboard");
		
			rs = ps.executeQuery();
		} catch(Exception e) {
			System.out.println(e);
		}
		return rs;
	
	}
	
	public ResultSet viewOrders() {
        try {
            ps = conn.prepareStatement("SELECT * FROM Orders LEFT JOIN OrderItem ON (Orders.id = OrderItem.orderID)");
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
	
	public ResultSet viewOrdersByCustomer(String custNum) {
        try {
            ps = conn.prepareStatement("SELECT * FROM Orders LEFT JOIN Customer ON (Customer.id = Orders.Customerid) "
            		+ "WHERE CustNumber = ?");
            ps.setString(1, custNum);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
	
	public ResultSet viewOrdersByAssignedTo(String assignedTo) {
        try {
            ps = conn.prepareStatement("SELECT * FROM Orders LEFT JOIN Employee ON (Employee.id = Orders.AssignedTo) "
            		+ "WHERE Employee.EmpNumber = ?");
            ps.setString(1, assignedTo);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
	
	public ResultSet viewOrdersByStatus(String status) {
        try {
            ps = conn.prepareStatement("SELECT * FROM Orders LEFT JOIN Status ON (Status.id = Orders.Statusid) "
            		+ "WHERE Status.name = ?");
            ps.setString(1, status);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
	
	public ResultSet viewOrdersByStartDate(String date) {
        try {
            ps = conn.prepareStatement("SELECT * FROM Orders WHERE StartDate = ?::timestamp");
            ps.setString(1, date);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
	
	// ex
	public ResultSet viewOrder(String orderKey) {
        try {
            ps = conn.prepareStatement("SELECT * FROM Orders LEFT JOIN OrderItem ON (Orders.id = OrderItem.orderID) WHERE orderKey = ?;");
            ps.setString(1, orderKey);
            
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return rs;
    }
	
	public ResultSet checkStatus(String orderKey) {
		try {
			ps = conn.prepareStatement("SELECT name FROM Orders JOIN Status ON (Status.id = Orders.statusid) WHERE orderkey = ?;");
			
			ps.setString(1, orderKey);
			
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public ResultSet viewEmployees() {
		try {
			ps = conn.prepareStatement("SELECT * FROM Employee");
		
			rs = ps.executeQuery();
		} catch(Exception e) {
			System.out.println(e);
		}
		return rs;
	
	}
	
	// updates
	
	public void updateStatus(String orderKey, String statusName) {
		try {
			ps = conn.prepareStatement("UPDATE Orders SET StatusID = (SELECT id FROM Status WHERE name = ?) WHERE orderkey = ?;");
			
			ps.setString(1, statusName);
			ps.setString(2, orderKey);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	// ex
	public void adjInvCount(String productSKU, Integer count) {
        try {
        	ResultSet newrs = checkInventory(productSKU);
        	int newCount = 0;
        	int i = 1;
        	while (newrs.next()) {
        		newCount = newrs.getInt(i) + count;
        		i++;
        	}
            if (newCount < 0) newCount = 0;
            System.out.println(newCount);
            ps = conn.prepareStatement("UPDATE Keyboard SET Count = ? WHERE SKU = ?");
            
            ps.setInt(1, newCount);
            ps.setString(2, productSKU);
            
            ps.executeUpdate();
            newrs.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	public void assignEmployee(String orderKey, String empNumber) {
		try {
			ps = conn.prepareStatement("UPDATE Orders SET AssignedTo = (SELECT id FROM Employee WHERE EmpNumber = ?) WHERE OrderKey = ?;");
			
			ps.setString(1, empNumber);
			ps.setString(2, orderKey);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// inserts
	
	// ex
	public void addCustomer(String cn, String fn, String ln, String email, int phoneNumber, String streetadd, String city, String state, int zip) {
		try {
			ps = conn.prepareStatement("INSERT INTO Customer"
					+ "(Custnumber, FirstName, LastName, Email, PhoneNumber, StreetAddress, City, State, Zip)"
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			
			ps.setString(1, cn);
			ps.setString(2, fn);
			ps.setString(3, ln);
			ps.setString(4, email);
			ps.setInt(5, phoneNumber);
			ps.setString(6, streetadd);
			ps.setString(7, city);
			ps.setString(8, state);
			ps.setInt(9, zip);
			
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void addOrder(String orderKey, String custNumber, String startDate) {
		try {
			ps = conn.prepareStatement("INSERT INTO Orders (OrderKey, CustomerID, StatusID, StartDate) values (?, (SELECT id FROM Customer WHERE CustNumber = ?), ?, ?::timestamp);");
			
			ps.setString(1, orderKey);
			ps.setString(2, custNumber);
			ps.setInt(3, 1);
			ps.setString(4, startDate);
			
			ps.executeUpdate();
			} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ex
	public boolean addOrderItem(String orderKey, String productSKU, Integer quantity) {
        try {
            ResultSet newrs = checkInventory(productSKU);
            int count = 0;
            int i = 1;
            while (newrs.next()) {
                count = newrs.getInt(i);
                i++;
            }
            if (quantity <= count) {
                ps = conn.prepareStatement("INSERT INTO OrderItem"
                        + "(OrderID, ProductSKU, Quantity)"
                        + "values ((SELECT id FROM Orders WHERE orderKey = ?),"
                        + "?, ?);");
                
                ps.setString(1, orderKey);
                ps.setString(2, productSKU);
                ps.setInt(3, quantity);
                
                ps.executeUpdate();
                newrs.close();
                return true;
            }      
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
	
	public void addEmployee(String empNumber, String firstName, String lastName, String email, Integer phoneNumber) {
        try {
            ps = conn.prepareStatement("INSERT INTO Employee"
                    + "(empNumber, firstName, lastName, email, phoneNumber)"
                    + "values (?, ?, ?, ?, ?);");
            
            ps.setString(1, empNumber);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setInt(5, phoneNumber);
            
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
	
	
	
	
}
