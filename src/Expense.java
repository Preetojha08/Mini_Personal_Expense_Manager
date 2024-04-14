import com.mysql.cj.log.Log;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Expense
{
    UserInfo userInfo = new UserInfo();
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/expensemanagerdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Papa@2062";
    private static Scanner scanner = new Scanner(System.in);
    Log log;
    double UpdatedIncome = 0;
    Connection conn = null;
    PreparedStatement ps = null;

    public Expense()
    {
        //Empty Constructor
    }

    public String getExpenseDecs()
    {
        String expenseCategory ="";
        boolean ExpensesCategoriesFlag = true;

        while(ExpensesCategoriesFlag)
        {
            System.out.print("Enter the Expense description Name for the expense amount: ");
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
        return expenseCategory;
    }

    public Double getAmountExpense()
    {

        boolean userExpenseAmountFlag = true;
        double expenseAmount=0;

        Scanner scanner = new Scanner(System.in);

        UserInfo userInfo = new UserInfo();

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
        return expenseAmount;
    }

    public String getExpenseCategory()
    {
        String category = "";
        boolean expenseCatFlag = true;

        while (expenseCatFlag)
        {
            try
            {
                System.out.print("Enter the Expense Category (Fixed/Variable): ");
                category = scanner.nextLine();

                if (category.equalsIgnoreCase("Fixed"))
                {
                    category = "Fixed Exp";
                    expenseCatFlag = false;
                }
                else if (category.equalsIgnoreCase("Variable"))
                {
                    category = "Variable Exp";
                    expenseCatFlag = false;
                }
                else
                {
                    System.out.println("Invalid category. Please enter 'Fixed' or 'Variable'.");
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return category;
    }

    public void UpdateIncome(double income)
    {

        String name = userInfo.getUserName();
        String id = userInfo.getUserID();

        try
        {
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            ps = conn.prepareStatement("UPDATE user SET userincome =? WHERE username =? AND uid =? ");
            ps.setDouble(1, income);
            ps.setString(2, name);
            ps.setString(3,id);

            ps.executeUpdate();

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0)
            {
                log.logDebug("Income updated successfully for " +name);
            }
            else
            {
                System.out.println("No user found with " + name);
            }

        }
        catch (SQLException e)
        {
            String s= e.getMessage();
            e.printStackTrace();
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

                if (ps == null)
                {
                    ps.close();
                }
            }
            catch (SQLException e)
            {
                e.getMessage();
            }
        }
    }

    public boolean addExpenseData(Double amount,Double Rincome,String expenseCat,String expenseDecs)
    {
        boolean addExpenseDataFlag = false;
        String tableName = userInfo.getUserExpenseTable();

        conn=null;
        ps=null;
        try
        {
            conn = DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD);

            String insertSQL = "INSERT INTO "+tableName+" (amount, remaining_income, category, expense_desc) "+ "VALUES(?, ?, ?, ?)";
            //String insertSQL = "INSERT INTO leet_expensetb (amount, remaining_income, category, expense_dec) "+ "VALUES(?, ?, ?, ?)";

            ps = conn.prepareStatement(insertSQL);
            ps.setDouble(1,amount);
            ps.setDouble(2,Rincome);
            ps.setString(3,expenseCat);
            ps.setString(4,expenseDecs);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("Expense Added Successfully!");
                UpdateIncome(Rincome);
                userInfo.setUserIncome(Rincome);
            }
            else
            {
                System.out.println("Failed to add new Expense.");

            }

        }
        catch (SQLException e)
        {
            String s= e.getMessage();
            e.printStackTrace();
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
                e.printStackTrace();
                e.getMessage();
            }
        }
        return addExpenseDataFlag;
    }
}
