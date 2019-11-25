/* This is a command line Java Program that allows user to specify a department, pay-type, education-level
 * Connects to given database and run the query with those options
 * Program should then display the results of the query
 * Written by Kan Fung
 */

import java.sql.*;

public class q2 
{
	//Variables for connecting to the SQL database
	private static String userName = "technical_test";
	private static String passWord = "HopefullyProspectiveDevsDontBreakMe";
	
	//Variables for taking arguments as Command Line JAVA program
	private static String departmentDescription;
	private static String payType;
	private static String educationLevel;
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException 
	{
		try
        {	
			//Initializing Connection to SQL server with all the credentials included in the parameter
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql-technical-test.cq5i4y35n9gg.eu-west-1.rds.amazonaws.com/foodmart", userName, passWord);
            
            //Create instances for reading the metadata of the database
            DatabaseMetaData dbMeta = con.getMetaData();
            //create statement for SQL to be executed 
            Statement stmt = con.createStatement();
            
            
            /*Statement for compound SQL search 
             * INNER join tables department, employee and position 
             * 
             */
            PreparedStatement ps = con.prepareStatement("SELECT employee_id, full_name, department_description, pay_type, education_level "
            + "FROM department d inner join employee e on d.department_id = e.department_id inner join position p on p.position_id = e.position_id WHERE d.department_id = ? AND p.pay_type = ? AND e.education_level = ?");
            
            //The WHERE clause takes in the argument input by user to determine search result
            ps.setString(1,departmentDescription=args[0]);
            ps.setString(2,payType=args[1]);
            ps.setString(3,educationLevel=args[2]);
            
           //Executing the query search and display all relevant query results
            ResultSet rs0 = ps.executeQuery();
            
            while(rs0.next())
            {
            	System.out.println("Employee ID: " + rs0.getString("employee_id"));
            	System.out.println("Full Name: " + rs0.getString("full_name"));
            	System.out.println("Department: " + rs0.getString("department_description"));
            	System.out.println("Pay Type: " + rs0.getString("pay_type"));
            	System.out.println("Education Level: " + rs0.getString("education_level")+ "\n");
            }
            
            
            /* This section of code is used to view the data Schema of the database
             * rs views the 3 table names 
             * while rs1, rs2 and rs3 views the column names and type
             * This section will give a better understanding of the database structure
             * It is not neccessary to use this section of code to complete the objective
             * I included this section to show my thought process
             * 
             */
            ResultSet rs = dbMeta.getTables(null, null, "%", null);
            
            while(rs.next())
            {
            	//Column 3 is the Table_Name attribute of the MetaData of the Database
            	System.out.println(rs.getString(3));
            }
            
            System.out.print("\n");
            //----------------------------------------------------------------------------
            ResultSet rs1 = dbMeta.getColumns(null, null, "department", null);
            
            while(rs1.next()) 
            {
            	System.out.print(rs1.getString("TYPE_NAME")+ " ");
                System.out.println(rs1.getString("COLUMN_NAME"));
            }
           
            System.out.print("\n");
            //----------------------------------------------------------------------------
            ResultSet rs2 = dbMeta.getColumns(null, null, "employee", null);
            
            while(rs2.next()) 
            {
            	System.out.print(rs2.getString("TYPE_NAME")+ " ");
                System.out.println(rs2.getString("COLUMN_NAME"));
            }
           
            System.out.print("\n");
            //----------------------------------------------------------------------------
            
            ResultSet rs3 = dbMeta.getColumns(null, null, "position", null);
            
            while(rs3.next()) 
            {
            	System.out.print(rs3.getString("TYPE_NAME")+ " ");
                System.out.println(rs3.getString("COLUMN_NAME"));
            }
             //* ENDS HERE
             
            
            
            
            //-----------------------------------------------------------------
            
            /* This section of code is use to view all in department_desciption column from table description
             * Education level from employee table 
             * And pay type from position table
             * This gives me a better understanding to what data can be input in the argument for my test cases
             * It is not needed to use this section of code to complete the objective
             * But i include here to shows my thought process
             * 
             */
            ResultSet rs4 = stmt.executeQuery("SELECT * FROM department");
            
            while(rs4.next()) 
            {
            	System.out.println(rs4.getString("department_description"));
                
            }
            
            //-----------------------------------------------------------------
            
            ResultSet rs5 = stmt.executeQuery("SELECT * FROM employee");
            
            while(rs5.next()) 
            {
            	System.out.println(rs5.getString("education_level"));
                
            }
            
            //-----------------------------------------------------------------
            
            ResultSet rs6 = stmt.executeQuery("SELECT * FROM position");
            
            while(rs6.next()) 
            {
            	System.out.println(rs6.getString("pay_type"));
                
            }
            
            //-----------------------------------------------------------------
             //* ENDS HERE 
             
            
        //Catch any class not found and sql exceptions as it is very common for mistakes
        }catch(ClassNotFoundException | SQLException e)
        {
        	//prints the error problem
            System.out.println("There is a problem reading the database: " + e);
            e.printStackTrace();
        }

	}

}
