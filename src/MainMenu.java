import java.sql.*;

import java.util.Scanner;

public class MainMenu
{
    UserInfo userInfo = new UserInfo();
    UserDetails userDetails = new UserDetails();
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/expensemanagerdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Papa@2062";
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    Scanner scannermm = new Scanner(System.in);
    public MainMenu()
    {
        //Empty Constructor
    }

    public void UpdateUserInfo()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSelect What you Want to Edit");
        System.out.println("1. Name");
        System.out.println("2. Mobile No");
        System.out.println("3. Email");
        System.out.println("4. Password");
        System.out.println("5. Income");
        System.out.println("6. Loan");
        System.out.println("7. Fixed Expense");
        System.out.println("8. Variable Expense");
        System.out.println("9. Exit");

        boolean UpdateDetailsflag = true;

        while(UpdateDetailsflag)
        {
            System.out.print("Enter You Choice: ");
            try
            {
                int choice = Integer.parseInt(scanner.nextLine());

                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                stmt = connection.createStatement();

                String userID = userInfo.getUserID();
                String updateQuery = null;

                switch (choice)
                {
                    case 1:
                        String name = userDetails.getUserName();
                        updateQuery = "UPDATE user SET username = '"+name+"' WHERE uid = "+userID+"";
                        // Implement logic for Name
                        break;
                    case 2:
                        String mobile = userDetails.getUserMobileNo();
                        updateQuery = "UPDATE user SET usermobile_no = '"+mobile+"' WHERE uid = "+userID+"";
                        // Implement logic for Mobile No
                        break;
                    case 3:
                        String email = userDetails.getUserEmailID();
                        updateQuery = "UPDATE user SET usermail = '"+email+"' WHERE uid = "+userID+"";
                        // Implement logic for Email
                        break;
                    case 4:
                        String password = userDetails.getUserPassword();
                        updateQuery = "UPDATE user SET userpassword = '"+password+"' WHERE uid = "+userID+"";
                        // Implement logic for Password
                        break;
                    case 5:
                        double income = userDetails.getUserIncome();
                        updateQuery = "UPDATE user SET userincome = "+income+" WHERE uid = "+userID+"";
                        // Implement logic for Income
                        break;
                    case 6:
                        String loan = userDetails.getUserLoan();
                        updateQuery = "UPDATE user SET userloan = '"+loan+"' WHERE uid = "+userID+"";
                        // Implement logic for Loan
                        break;
                    case 7:
                        double fixedExpense = userDetails.getUserFixedExpense();
                        updateQuery = "UPDATE user SET fixed_expense = "+fixedExpense+" WHERE uid = "+userID+"";
                        // Implement logic for Fixed Expense
                        break;
                    case 8:
                        double variableExpense = userDetails.getUserVariableExpense();
                        updateQuery = "UPDATE user SET var_expense = "+variableExpense+" WHERE uid = "+userID+"";
                        // Implement logic for Variable Expense
                        break;
                    case 9:
                        System.out.println("Back To Main Menu");
                        UpdateDetailsflag = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }

                int rowsAffected = stmt.executeUpdate(updateQuery);

                if (rowsAffected > 0)
                {
                    System.out.println("Information Updated Successfully");
                }
                else
                {
                    System.out.println("Failed to Register new User.");
                }

            }
            catch (SQLException e)
            {
                String s = e.getMessage();
                System.out.println("Error: "+s);
            }
            catch (Exception e)
            {
                String s = e.getMessage();
                System.out.println("Error: "+s);
            }
        }
    }

    public void ViewEexpenseData()
    {
        try
        {
            connection = DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD);
            String tableName = userInfo.getUserExpenseTable();
            String selectQuery = "Select * from "+tableName;

            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectQuery);


            System.out.printf("\n%-10s%-10s%-20s%-15s%-20s%n", "Expense ID", "Amount", "Remaining Income", "Category", "Expense Description");
            while (rs.next())
            {
                int expenseId = rs.getInt("expense_id");
                double amount = rs.getDouble("amount");
                double remainingIncome = rs.getDouble("remaining_income");
                String category = rs.getString("category");
                String expenseDesc = rs.getString("expense_desc");
                System.out.printf("%-10d%-10.1f%-20.1f%-15s%-20s%n", expenseId, amount, remainingIncome, category, expenseDesc);
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error: "+e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println("Error: "+e.getMessage());
            e.printStackTrace();
        }

    }

    public void BudgetPlanning()
    {

    }

}
