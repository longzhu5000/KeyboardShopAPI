package css475;

// driver for DatabaseAPI class
public class DatabaseDriver {
	public static void main(String[] args) {
		String add = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String pass = "";
		DatabaseAPI test = new DatabaseAPI();
		test.getConnection(add, user, pass);
		
		test.createDB();
		test.loadExampleData();
		
		System.out.println("===================================================================");
		System.out.println(" 				QUERIES						 ");
		System.out.println("===================================================================");
		
		System.out.println("==Individual Customer:==");
		System.out.println();
		test.display(test.viewCustomer("CU000005"));
		System.out.println();
		// id, custnumber, firstname, lastname, email, phonenumber, streetaddress, city, state, zip
		// 5, CU000005, Faith, Collins, null, 1052345, 707 Fun Street, Tacoma, WA, 51234

		System.out.println("==All Customers:==");
		System.out.println();
		test.display(test.viewCustomers());
		System.out.println();
		// id, custnumber, firstname, lastname, email, phonenumber, streetaddress, city, state, zip
		// 1, CU000001, Rick, Sanchez, ricksanchez@gmail.com, 1012345, 101 Generic Street, Kirkland, WA, 10001
		// 2, CU000002, Blake, Sanders, blakesanders@gmail.com, 1022345, 102 Generic Ave, Bellevue, WA, 20012
		// 3, CU000003, Patricia, Jones, patjones@gmail.com, 1032345, 567 8th Ave SE, Seattle, WA, 32345
		// 4, CU000004, Kyle, Howell, null, 1042345, 311 3rd ST, Redmond, WA, 40123
		// 5, CU000005, Faith, Collins, null, 1052345, 707 Fun Street, Tacoma, WA, 51234
		
		System.out.println("==All Inventory:==");
		System.out.println();
		test.display(test.checkEntireInventory());
		System.out.println();
		
		// sku, price, name, description, count
		// PIN1899L, 1899, pinkl, Lifestyle model in pink, 15
		// GRE1899L, 1899, greenl, Lifestyle model in green, 15
		// ORA1899L, 1899, orangel, Lifestyle model in orange, 15
		// WHI2399W, 2399, whitew, Workplace model in white, 10
		// GRA2399W, 2399, grayw, Workplace model in gray, 10
		// TAN2399W, 2399, tanw, Workplace model in tan, 10
		// BLA5999G, 5999, blackg, Gaming model in black, 5
		// SKY5999G, 5999, skyg, Gaming model in sky blue, 5
		// NEO5999G, 5999, neong, Gaming model in multicolored neon, 5
		
		System.out.println("==Keyboard with matching SKU:==");
		System.out.println();
		test.display(test.checkInventory("GRA2399W"));
		System.out.println();
		// count
		// GRA2399W, 2399, grayw, Workplace model in gray, 10
		
		System.out.println("==All Orders:==");
		System.out.println();
		test.display(test.viewOrders());
		System.out.println();
		
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 1, 1, GRE1899L, 1
		// 2, OK000002, 1, 1, 2022-03-10 08:05:00, null, null, 2, 2, ORA1899L, 1
		// 3, OK000003, 2, 2, 2022-03-10 08:30:00, null, 1, 3, 3, WHI2399W, 3
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 4, 4, BLA5999G, 1
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 5, 4, NEO5999G, 2
		
		System.out.println("==Order by Customer==");
		test.display(test.viewOrdersByCustomer("CU000002"));
		System.out.println();
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, custnumber, firstname, lastname, email, phonenumber, streetaddress, city, state, zip
		// 3, OK000003, 2, 2, 2022-03-10 08:30:00, null, 1, 2, CU000002, Blake, Sanders, blakesanders@gmail.com, 1022345, 102 Generic Ave, Bellevue, WA, 20012
		
		System.out.println("==Order by Date==");
		test.display(test.viewOrdersByStartDate("2022-03-10 10:00:00"));
		System.out.println();
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2
		
		System.out.println("==Order by Status==");
		test.display(test.viewOrdersByStatus("OPEN"));
		System.out.println();
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, name
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 1, OPEN
		// 2, OK000002, 1, 1, 2022-03-10 08:05:00, null, null, 1, OPEN
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 1, OPEN
		
		System.out.println("==Order by AssignedTo (employee)==");
		test.display(test.viewOrdersByAssignedTo("EM000002"));
		System.out.println();
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, empnumber, firstname, lastname, email, phonenumber
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 2, EM000002, Joe, Jackson, joejackson@gmail.com, 1225566

		
		System.out.println("==Order with matching Order Key==");
		System.out.println();
		test.display(test.viewOrder("OK000001"));
		System.out.println();
		
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 4, 4, BLA5999G, 1
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 5, 4, NEO5999G, 2
		
		System.out.println("==Status of Order with matching Order Key==");
		System.out.println();
		test.display(test.checkStatus("OK000004"));
		System.out.println();
		// name
		// OPEN
		
		System.out.println("==All the employees==");
		System.out.println();
		test.display(test.viewEmployees());
		System.out.println();
		// id, empnumber, firstname, lastname, email, phonenumber
		// 1, EM000001, John, Smith, johnsmith@gmail.com, 1223344
		// 2, EM000002, Joe, Jackson, joejackson@gmail.com, 1225566
		// 3, EM000003, Alice, Jane, alicejane@gmail.com, 5667788
		// 4, EM000004, Mark, Thomas, markthomas@gmail.com, 9090909
		
		System.out.println("===================================================================");
		System.out.println(" 				UPDATES 					 ");
		System.out.println("===================================================================");
		
		System.out.println("==Status Update==");
		System.out.println();
		test.display(test.viewOrder("OK000003"));
		test.updateStatus("OK000003", "CANCELLED");
		test.display(test.viewOrder("OK000003"));
		System.out.println();
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 3, OK000003, 2, 2, 2022-03-10 08:30:00, null, 1, 3, 3, WHI2399W, 3
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 3, OK000003, 2, 5, 2022-03-10 08:30:00, null, 1, 3, 3, WHI2399W, 3
		
		System.out.println("==Update Inventory(keyboard) count==");
		System.out.println();
		test.display(test.checkInventory("GRE1899L"));
		test.adjInvCount("GRE1899L", 20);
		test.display(test.checkInventory("GRE1899L"));
		// count
		// 15
		// 35
		// count
		// 35
		
		System.out.println("==Update Inventory(keyboard) count negative test==");
		System.out.println();
		test.display(test.checkInventory("GRE1899L"));
		test.adjInvCount("GRE1899L", -40);
		test.display(test.checkInventory("GRE1899L"));
		// count
		// 35
		// 0
		// count
		// 0
		
		System.out.println("=Assign new employee to Order==");
		System.out.println();
		test.display(test.viewOrder("OK000003"));
		test.assignEmployee("OK000003", "EM000004");
		test.display(test.viewOrder("OK000003"));
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 3, OK000003, 2, 5, 2022-03-10 08:30:00, null, 1, 3, 3, WHI2399W, 3
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 3, OK000003, 2, 5, 2022-03-10 08:30:00, null, 4, 3, 3, WHI2399W, 3
		
		
		System.out.println("===================================================================");
		System.out.println(" 				INSERTS						 ");
		System.out.println("===================================================================");
		
		System.out.println("==Inserts a new customer into the customer table==");
		System.out.println();
		test.display(test.viewCustomers());
		test.addCustomer("CU123456", "John", "Chenault", "johncehnault@uw.edu", 4551239, "John C street NE 169th PL", "Bothell", "WA", 12345);
		System.out.println();
		test.display(test.viewCustomers());
		System.out.println();
		// id, custnumber, firstname, lastname, email, phonenumber, streetaddress, city, state, zip
		// 1, CU000001, Rick, Sanchez, ricksanchez@gmail.com, 1012345, 101 Generic Street, Kirkland, WA, 10001
		// 2, CU000002, Blake, Sanders, blakesanders@gmail.com, 1022345, 102 Generic Ave, Bellevue, WA, 20012
		// 3, CU000003, Patricia, Jones, patjones@gmail.com, 1032345, 567 8th Ave SE, Seattle, WA, 32345
		// 4, CU000004, Kyle, Howell, null, 1042345, 311 3rd ST, Redmond, WA, 40123
		// 5, CU000005, Faith, Collins, null, 1052345, 707 Fun Street, Tacoma, WA, 51234

		// id, custnumber, firstname, lastname, email, phonenumber, streetaddress, city, state, zip
		// 1, CU000001, Rick, Sanchez, ricksanchez@gmail.com, 1012345, 101 Generic Street, Kirkland, WA, 10001
		// 2, CU000002, Blake, Sanders, blakesanders@gmail.com, 1022345, 102 Generic Ave, Bellevue, WA, 20012
		// 3, CU000003, Patricia, Jones, patjones@gmail.com, 1032345, 567 8th Ave SE, Seattle, WA, 32345
		// 4, CU000004, Kyle, Howell, null, 1042345, 311 3rd ST, Redmond, WA, 40123
		// 5, CU000005, Faith, Collins, null, 1052345, 707 Fun Street, Tacoma, WA, 51234
		// 6, CN123456, John, Chenault, johncehnault@uw.edu, 4551239, John C street NE 169th PL, Bothell, WA, 12345
		
		System.out.println("==addOrderItem adds an a keyboard and its quantity to an order (fails if not enough stock, returns false==");
		System.out.println("==NOTE: an order can order more than one type of keyboard==");
		System.out.println();
		test.display(test.viewOrder("OK000001"));
		System.out.println();
				
		boolean res = test.addOrderItem("OK000001", "NEO5999G", 3);
		System.out.println(res);
		test.display(test.viewOrder("OK000001"));
				
		res = test.addOrderItem("OK000001", "NEO5999G", 6);
		System.out.println(res);
		test.display(test.viewOrder("OK000001"));
		System.out.println();
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 1, 1, GRE1899L, 1

		// true
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 1, 1, GRE1899L, 1
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 6, 1, NEO5999G, 3
		// false
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 1, 1, GRE1899L, 1
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 6, 1, NEO5999G, 3
		
		System.out.println("==Add an order to the table Orders==");
		System.out.println();
		test.display(test.viewOrders());
		test.addOrder("OK123456", "CU123456", "2022-03-16 11:00:00");
		test.display(test.viewOrders());
		System.out.println();
		
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 1, 1, GRE1899L, 1
		// 2, OK000002, 1, 1, 2022-03-10 08:05:00, null, null, 2, 2, ORA1899L, 1
		// 3, OK000003, 2, 5, 2022-03-10 08:30:00, null, 4, 3, 3, WHI2399W, 3
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 4, 4, BLA5999G, 1
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 5, 4, NEO5999G, 2
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 6, 1, NEO5999G, 3
		// id, orderkey, customerid, statusid, startdate, closeddate, assignedto, id, orderid, productsku, quantity
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 1, 1, GRE1899L, 1
		// 2, OK000002, 1, 1, 2022-03-10 08:05:00, null, null, 2, 2, ORA1899L, 1
		// 3, OK000003, 2, 5, 2022-03-10 08:30:00, null, 4, 3, 3, WHI2399W, 3
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 4, 4, BLA5999G, 1
		// 4, OK000004, 3, 1, 2022-03-10 10:00:00, null, 2, 5, 4, NEO5999G, 2
		// 1, OK000001, 1, 1, 2022-03-10 08:00:00, null, null, 6, 1, NEO5999G, 3
		// 5, OK123456, 6, 1, 2022-03-16 11:00:00, null, null, null, null, null, null
		
		System.out.println("==Add an employee==");
		System.out.println();
		test.display(test.viewEmployees());
		test.addEmployee("EM123456", "Jack", "Smith", "jacksmith@gmail.com", 1234567);
		test.display(test.viewEmployees());
		// id, empnumber, firstname, lastname, email, phonenumber
		// 1, EM000001, John, Smith, johnsmith@gmail.com, 1223344
		// 2, EM000002, Joe, Jackson, joejackson@gmail.com, 1225566
		// 3, EM000003, Alice, Jane, alicejane@gmail.com, 5667788
		// 4, EM000004, Mark, Thomas, markthomas@gmail.com, 9090909
		// id, empnumber, firstname, lastname, email, phonenumber
		// 1, EM000001, John, Smith, johnsmith@gmail.com, 1223344
		// 2, EM000002, Joe, Jackson, joejackson@gmail.com, 1225566
		// 3, EM000003, Alice, Jane, alicejane@gmail.com, 5667788
		// 4, EM000004, Mark, Thomas, markthomas@gmail.com, 9090909
		// 5, EM123456, Jack, Smith, jacksmith@gmail.com, 1234567
		
		
		
		// remove when done testing
		//test.rollback();
		test.dropDB();
		
		test.closeConnections();
	}

}
