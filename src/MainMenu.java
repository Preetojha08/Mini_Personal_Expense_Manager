import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainMenu
{
    UserInfo userInfo = new UserInfo();
    UserDetails userDetails = new UserDetails();
    Scanner scannermm = new Scanner(System.in);
    public MainMenu()
    {
        //Empty Constructor
    }

    public void UpdateUserInfo()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select What you Want to Edit");
        System.out.println("1. Name");
        System.out.println("2. Mobile No");
        System.out.println("3. Email");
        System.out.println("4. Password");
        System.out.println("5. Income");
        System.out.println("6. Loan");
        System.out.println("8. Fixed Expense");
        System.out.println("9. Variable Expense");
        System.out.println("10. Exit");

        boolean UpdateDetailsflag = true;

        while(UpdateDetailsflag)
        {
            System.out.print("Enter You Choice: ");

            try
            {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        String name = userDetails.getUserName();
                        // Implement logic for Name
                        break;
                    case 2:
                        String mobile = userDetails.getUserMobileNo();
                        // Implement logic for Mobile No
                        break;
                    case 3:
                        System.out.println("You chose Email");
                        String email = userDetails.getUserEmailID();
                        // Implement logic for Email
                        break;
                    case 4:
                        System.out.println("You chose Password");
                        // Implement logic for Password
                        break;
                    case 5:
                        System.out.println("You chose Income");
                        // Implement logic for Income
                        break;
                    case 6:
                        System.out.println("You chose Loan");
                        // Implement logic for Loan
                        break;
                    case 7:
                        System.out.println("You chose Fixed Expense");
                        // Implement logic for Fixed Expense
                        break;
                    case 8:
                        System.out.println("You chose Variable Expense");
                        // Implement logic for Variable Expense
                        break;
                    case 9:
                        System.out.println("Exiting program. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }

            }
            catch (Exception e)
            {
                String s = e.getMessage();
            }

        }

    }

    public void AddExpense()
    {

    }

    public void UpdateIncome(double income)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        String name = userInfo.getUserName();
        String id = userInfo.getUserID();

        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_manager", "root", "root");
            stmt = conn.prepareStatement("UPDATE user SET userincome =? WHERE username =? AND uid =? ");
            stmt.setDouble(1, income);
            stmt.setString(2, name);
            stmt.setString(3,id);

            stmt.executeUpdate();

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0)
            {
                System.out.println("Income updated successfully for " +name);
            }
            else
            {
                System.out.println("No user found with " + name);
            }

        }
        catch (SQLException e)
        {
            String s= e.getMessage();
            System.out.println(s);
        }
        catch (Exception e)
        {
            String s= e.getMessage();
            System.out.println(s);
        }
        finally
        {
            try
            {
                if (conn == null)
                {
                    conn.close();
                }

                if (stmt == null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                e.getMessage();
            }

        }

    }

    public void BudgetPlanning()
    {

    }

}
