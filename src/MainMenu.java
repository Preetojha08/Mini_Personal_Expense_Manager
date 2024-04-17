import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

import java.util.Scanner;

public class MainMenu
{
    UserInfo userInfo = new UserInfo();
    UserDetails userDetails = new UserDetails();
    UserLoan userLoan = new UserLoan();
    Expense expense = new Expense();
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

    public void displayMainMenu()
    {

        boolean MainMenuExit = true; //for main menu input validation
        while (MainMenuExit)
        {
            System.out.println("\nMain Menu:");
            System.out.println("1. Update User Details"); //User Details will be updated here(like name , mobile no,email) And it will also update income and fixed and variable expense will be updated here .
            System.out.println("2. Add Expense"); //Add expense what and how much you send on something
            System.out.println("3. View Transaction History"); //view the entire transaction history of expense with its details with income information
            System.out.println("4. Saving"); //View the budget planning information or steps and tips to save money
            System.out.println("5. Budget Planning Tips"); //just back to pervious menu
            System.out.println("6. Logout"); //just back to pervious menu

            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter your choice: ");
                String line= reader.readLine();

                int MainMenuChoice = Integer.parseInt(line);

                switch (MainMenuChoice)
                {
                    case 1:
                        //Update User Information
                        UpdateUserInfo();
                        break;
                    case 2:
                        //Add Expense Logic
                        double expAmount=expense.getAmountExpense();
                        String expCategory =expense.getExpenseCategory();
                        String expDescription =expense.getExpenseDecs();
                        double curentIncome =userInfo.getUserIncome();
                        double RIncome = curentIncome - expAmount;
                        boolean addexpenseFlag = expense.addExpenseData(expAmount,RIncome,expCategory,expDescription);

                        break;
                    case 3:
                        //View the Expense Information of user
                        ViewEexpenseData();
                        break;
                    case 4:
                        //View Anuual Saving of the user
                        DisplaySaving();
                        break;
                    case 5:
                        //View Budget Planning tips;
                        BudgetPlanningTips();
                        break;
                    case 6:
                        //Logout (Back to Main Function);
                        System.out.println("Logout Successfully Completed");
                        MainMenuExit=false;
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }//End of Switch

            }//End of try

            catch (NumberFormatException e)
            {
                String s =e.getMessage();
                System.out.println("Invalid choice "+s);
            }//End of catch

            catch (Exception e)
            {
                String s =e.getMessage();
                System.out.println("Invalid choice "+s);
                break;
            }//End of catch
        }//End of While Loop
    }//End of function

    public void UpdateUserInfo()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSelect What you Want to Edit");
        System.out.println("1. Name: "+userInfo.getUserName());
        System.out.println("2. Mobile No: "+userInfo.getUserPhone());
        System.out.println("3. Email: "+userInfo.getUserEmail());
        System.out.println("4. Password: "+userInfo.getUserPassword());
        System.out.println("5. Income: "+userInfo.getUserIncome());
        System.out.println("6. Loan: "+userInfo.getUserLoan());
        System.out.println("7. Fixed Expense: "+userInfo.getUserFixedExpense());
        System.out.println("8. Variable Expense: "+userInfo.getUserVarExpense());
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

            boolean validateInput = true;

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

            while (validateInput)
            {
                System.out.print("\nDo you want the expense data in Excel with Graph (Yes/NO): ");
                String choiceExcelSheet = scannermm.nextLine();

                if (choiceExcelSheet.equalsIgnoreCase("yes") || choiceExcelSheet.equalsIgnoreCase("no"))
                {
                    validateInput = false;
                    if (choiceExcelSheet.equalsIgnoreCase("yes"))
                    {
                        expense.viewExpenseDataExcel();
                    }
                }
                else
                {
                    System.out.print("\nInvalid choice. Please enter a valid option.");
                }
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

    public void BudgetPlanningTips()
    {
        System.out.println("\nOf Course! Here are Some Budget Planning Tips:");
        System.out.println();
        System.out.println("1. Track Your Expenses: Monitor your spending habits to understand where your money is going.");
        System.out.println("2. Set Financial Goals: Define clear objectives for your finances, such as saving for a vacation or paying off debt.");
        System.out.println("3. Create a Budget: Develop a budget that allocates your income towards essential expenses, savings, and discretionary spending.");
        System.out.println("4. Prioritize Spending: Distinguish between needs and wants, prioritizing essential expenses while limiting non-essential spending.");
        System.out.println("5. Regularly Review: Review your budget regularly to ensure you're on track with your financial goals and make adjustments as needed.");
        System.out.println("6. Build an Emergency Fund: Set aside funds for unexpected expenses to avoid financial strain during emergencies.");
        System.out.println("7. Automate Savings: Automate transfers to your savings account to consistently build your savings over time.");
        System.out.println("8. Reduce Debt: Focus on paying off high-interest debt to free up more money for savings and investments.");

    }

    public void DisplaySaving()
    {
        String username = userInfo.getUserName();
        double income = userInfo.getUserIncome();
        double pendingAmount = userLoan.getPenddingAmount();
        double installment = userLoan.getInstallment();

        double sumOfFixedExp = expense.getTotalAmountFixedCAT();
        double sumOfVarExp = expense.getTotalAmountVariableCAT();

        System.out.println("Savings of " + username);
        System.out.println("- For this month you have saved is "+(income-installment));
        System.out.println("- As your Fixed expense is "+sumOfFixedExp);
        System.out.println("- As your Variable expense is "+sumOfVarExp);
        System.out.println("- And your pending Amount is "+pendingAmount);


    }
}
