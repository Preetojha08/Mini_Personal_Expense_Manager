import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Expense
{
    UserInfo userInfo = new UserInfo();
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/expensemanagerdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Papa@2062";

    public Expense()
    {
        //Empty Constructor
    }

    public String getExpenseDecs()
    {
        Scanner scanner = new Scanner(System.in);
        String expenseCategory ="";
        boolean ExpensesCategoriesFlag = true;

        while(ExpensesCategoriesFlag)
        {
            System.out.print("\nEnter the Expense Category Name for the expense amount: ");
            expenseCategory = scanner.nextLine();
            if (expenseCategory == null || expenseCategory.length() == 0 || expenseCategory.length() == 1 || !expenseCategory.matches("^[^0-9]+$"))
            {
                //invalid response
                System.out.print("Invaild Expense Category \n");
                ExpensesCategoriesFlag = true;
            }
            else
            {
                //Vaild Response
                ExpensesCategoriesFlag = false;
            }
        }
        scanner.close();
        return expenseCategory;
    }

    public Double getAmountExpense()
    {
        Scanner scanner = new Scanner(System.in);
        boolean userExpenseAmountFlag = true;
        double expenseAmount=0;

        while (userExpenseAmountFlag)
        {
            try
            {
                System.out.print("\nEnter the Expense Amount: $");
                expenseAmount = scanner.nextDouble();

                if (expenseAmount < 0)
                {
                    System.out.println("Invalid Response for expense amount");
                    userExpenseAmountFlag = true;
                }
                else
                {
                    userExpenseAmountFlag = false;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid Response for expense amount");
                userExpenseAmountFlag = true;
            }
        }
        scanner.close();
        return expenseAmount;
    }

    public String getExpenseCategory()
    {
        String category = "";
        Scanner scanner = new Scanner(System.in);

        boolean expenseCatFlag=true;

        while (expenseCatFlag)
        {
            System.out.print("\nEnter the Expense Category: ");
            category = scanner.nextLine();

            if (category.equalsIgnoreCase("Fixed") || category.equalsIgnoreCase("Variable"))
            {
                expenseCatFlag =false;
            }
        }
        scanner.close();
        return category;
    }

    public boolean addExpenseData(Double amount,Double Rincome,String expenseCat,String expenseDecs)
    {
        boolean addExpenseDataFlag = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String tableName = userInfo.getUserExpenseTable();

        try
        {
            conn = DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD);

            String insertSQL = "INSERT INTO ? (amount, remaining_income, category, expense_dec) "+ "Values(?, ?, ?, ?)";

            ps = conn.prepareStatement(insertSQL);
            ps.setString(1,tableName);
            ps.setDouble(2,amount);
            ps.setDouble(3,Rincome);
            ps.setString(4,expenseCat);
            ps.setString(5,expenseDecs);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("New Expense added Successfully!");
            }
            else
            {
                System.out.println("Failed to add new Expense.");

            }

        }
        catch (SQLException e)
        {
            String s= e.getMessage();
            System.out.println(s);
        }
        finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                //e.printStackTrace();
                e.getMessage();
            }
        }
        return addExpenseDataFlag;
    }
}
