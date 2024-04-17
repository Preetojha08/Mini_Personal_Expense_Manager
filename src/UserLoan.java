import java.sql.*;
import java.util.Scanner;

public class UserLoan
{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/expensemanagerdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Papa@2062";

    UserInfo userInfo = new UserInfo();
    Connection conn = null;
    Statement statement = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Scanner scanner = new Scanner(System.in);
    double MainAmountLoan = 0.0;

    public UserLoan()
    {
        //Empty Constructor
    }

    //Get Loan Amount
    public Double getLoanAmount()
    {
        double amountLoan = 0.0;
        boolean amountLoanFlag = true;
        try
        {
            while(amountLoanFlag)
            {
                System.out.print("Enter the LOAN amount you have taken: ");
                amountLoan = Double.parseDouble(scanner.next());
                //vaild response
                if(amountLoan > 0.0)
                {
                    amountLoanFlag = false;
                    this.MainAmountLoan = amountLoan;
                }
                //invaild response
                else
                {
                    System.out.print("\nInvaild Entry please enter a positive number");
                }
            }
        }
        catch (NumberFormatException e)
        {
            System.out.print(""+e.getMessage());
        }
        return amountLoan;
    }

    public Double getLoanPendingAmount()
    {
        double loanPending = 0.0;
        boolean loanPendingFlag = true;
       try
       {
           while(loanPendingFlag)
           {
               System.out.print("Enter the LOAN pending amount: ");
               loanPending = Double.parseDouble(scanner.next());

               //vaild response
               if(loanPending < MainAmountLoan && loanPending > 0)
               {
                   loanPendingFlag = false;
               }
               //invaild response
               else
               {
                   System.out.print("\nInvaild Entry please enter a positive number");
               }
           }
       }
       catch (NumberFormatException e)
       {
           System.out.print(""+e.getMessage());
       }
        return loanPending;
    }

    public Double getLoanMonthlyInstallment()
    {
        double loanMonthlyInstallment = 0.0;
        boolean loanMonthlyInstallmentFlag = true;
        try
        {
            while(loanMonthlyInstallmentFlag)
            {
                System.out.print("Enter the LOAN monthly installment: ");
                loanMonthlyInstallment = Double.parseDouble(scanner.next());

                //valid response
                if(loanMonthlyInstallment < MainAmountLoan && loanMonthlyInstallment > 0)
                {
                    loanMonthlyInstallmentFlag = false;
                }
                //invalid response
                else
                {
                    System.out.print("\nInvalid Entry please enter a positive number");
                }
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println(""+e.getMessage());
        }
        return loanMonthlyInstallment;
    }

    public double getPenddingAmount()
    {
        double loanPendingAmount = 0;
        try
        {
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            String username = userInfo.getUserName();
            String selectQuery = "SELECT pending_amount FROM loan WHERE username = ?";

            ps = conn.prepareStatement(selectQuery);
            ps.setString(1, username); // Set the parameter with the username

            rs = ps.executeQuery(); // Execute the prepared statement

            if (rs.next())
            {
                loanPendingAmount = rs.getDouble("pending_amount"); // Retrieve the correct column name
            }
            else
            {
                System.out.println("No Loan Information Found");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return loanPendingAmount;
    }

    public double getInstallment()
    {
        double loanInstallment = 0;
        try
        {
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            String username = userInfo.getUserName();
            String selectQuery = "SELECT monthly_installment FROM loan WHERE username = ?";

            ps = conn.prepareStatement(selectQuery);
            ps.setString(1, username); // Set the parameter with the username

            rs = ps.executeQuery(); // Execute the prepared statement

            if (rs.next())
            {
                loanInstallment = rs.getDouble("monthly_installment"); // Retrieve the correct column name
            }
            else
            {
                System.out.println("No Loan Information Found");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return loanInstallment;
    }

}
