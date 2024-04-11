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
                        String email = userDetails.getUserEmailID();
                        // Implement logic for Email
                        break;
                    case 4:
                        String password = userDetails.getUserPassword();
                        // Implement logic for Password
                        break;
                    case 5:
                        double income = userDetails.getUserIncome();
                        // Implement logic for Income
                        break;
                    case 6:
                        String loan = userDetails.getUserLoan();
                        // Implement logic for Loan
                        break;
                    case 7:
                        double fixedExpense = userDetails.getUserFixedExpense();
                        // Implement logic for Fixed Expense
                        break;
                    case 8:
                        double variableExpense = userDetails.getUserVariableExpense();
                        // Implement logic for Variable Expense
                        break;
                    case 9:
                        System.out.println("Back To Main Menu");
                        UpdateDetailsflag = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
            catch (Exception e)
            {
                String s = e.getMessage();
                System.out.println("Error: "+s);
            }
        }
    }


    public void BudgetPlanning()
    {

    }

}
