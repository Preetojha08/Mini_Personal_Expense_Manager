import java.sql.*;
import java.util.Scanner;

public class UserDetails
{
    public UserDetails()
    {
        //Empty constructor
    }
    Scanner scanner = new Scanner(System.in);
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/expensemanagerdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Papa@2062";

    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;

    public String getUserLoan()
    {
        String userLoan = "";
        boolean userLoanFlag = true;
        while(userLoanFlag)
        {
            System.out.println("Do You have any kind of Loan(Yes/NO):");
            userLoan = scanner.nextLine();

            //check the loan user inpu must be yes or no nothing else.
            if(userLoan.equalsIgnoreCase("Yes") || userLoan.equalsIgnoreCase("NO") || userLoan.equalsIgnoreCase("Y") || userLoan.equalsIgnoreCase("Yes"))
            {
                userLoanFlag = false;
            }
            else
            {
                //invalid response
                System.out.print("\nInvaild Entry for Loan (Just type Yes/No)\n");
                userLoanFlag = true;
            }
        }
        return userLoan;
    }

    public String getUserName()
    {
        //Get User Name from user Until its enter correct or proper Name
        String userName = "";
        boolean userNameflag=true;

        while (userNameflag)
        {
            System.out.println("Enter the User Name: ");
            userName = scanner.nextLine();

            //checks the value of userName has to be not null and not empty and does not contain numbers in it.
            if (userName == null || userName.length() == 0 || userName.length() == 1 || !userName.matches("^[^0-9]+$"))
            {
                //invalid response
                System.out.print("\nInvaild User Name type it Again \n");
                userNameflag = true;
            }
            //valid response
            else
            {
                userNameflag = false;
                //for vaild response and close the loop
            }
        }
        //scanner.close();
        return userName;
    }

    public String getUserPassword()
    {
        String userPassword = "";
        String passwordOne = "";
        String passwordTwo = "";

        boolean userPasswordFlag=true;

        //Get the both Password 1 and Password 2 from the user
        //define the password rules and take password from the user.
        System.out.println("Password Should Contain:-\n1.Must have 8 charter minimum \n2.One Upper Case Alphabet \n3.One Lower Case Alphabet \n4.At least one digit and one special character");

        while (userPasswordFlag)
        {
            System.out.println("Enter the User Password: ");
            passwordOne = scanner.nextLine();

            if (passwordOne.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))
            {
                //bool var for second password just to run the loop until it matches with previous password.
                boolean checkSceondPassword = true;

                //loop will check the second password is same as first password.
                while (checkSceondPassword)
                {
                    System.out.println("\nConfirm the User Password(Type again the Same Password as ABOVE): ");
                    passwordTwo = scanner.nextLine();

                    //if the password is same than "BINGO"
                    if (passwordOne.equals(passwordTwo))
                    {
                        userPasswordFlag = false;
                        checkSceondPassword = false;
                        userPassword=passwordOne;
                        //for vaild response and close the loop.
                    }
                    //for invalid response of second password.
                    else
                    {
                        System.out.println("Passwords do not match. type it again");
                        checkSceondPassword = true;
                    }
                }
            }
            //it checks password has all the stuffs which are required or not.
            else
            {
                System.out.println("\nINVAILD: Make sure you password may contain these things:-\n1.Must have 8 charter minimum \n2.One Upper Case Alphabet \n3.One Lower Case Alphabet \n4.At least one digit and one special character\n");
                passwordOne = "";
                userPasswordFlag=true;
            }
        }
        //scannerPW.close();
        return userPassword;
    }

    public String getUserEmailID()
    {
        String userEmailID = "";
        boolean userMailAddFlag=true;
        //Get USer Mail Id from user until its in specified format.
        while (userMailAddFlag)
        {
            System.out.println("Enter the User Mail ID: ");
            userEmailID = scanner.nextLine();

            //Checks the value of mail address is in the mail format or not. (.+@.+\\\\..+)
            if (userEmailID.matches("^[a-zA-Z0-9_!#$%&*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))
            {
                userMailAddFlag = false;
                //for vaild response and close the loop

            }
            //valid response
            else
            {
                //invalid response
                userMailAddFlag = true;
                System.out.println("\nInvalid email format. Please enter a valid email address.\n");

            }
        }
        //scannerEID.close();
        return userEmailID;
    }

    public String getUserMobileNo()
    {
        String userMobileNo = "";
        boolean userMobileNoflag=true;
        //Get User Mobile Number from user Until its enter correct or prooper number.
        while (userMobileNoflag)
        {
            System.out.println("Enter the User Mobile Number(Ten Numbers): ");
            userMobileNo = scanner.nextLine();

            //Checks the value of userMobileNoflag has to be not null and not empty and it has to be ten digits.
            if (!userMobileNo.matches("\\d{10}"))
            {
                //invalid response
                System.out.println("\nInvalid mobile number format. Please enter a 10-digit number.\n");
                userMobileNoflag = true;
            }
            //valid respons
            else
            {
                userMobileNoflag = false;
                //for vaild response and close the loop
            }
        }
        //scannerMNO.close();
        return userMobileNo;
    }

    public Double getUserIncome()
    {
        double userIncome = 0.0;
        boolean userIncomeFlag=true;
        //Get the user income and it should be verfied
        while (userIncomeFlag)
        {
            //just to vaild the input from the user
            try
            {
                System.out.println("Enter the User Anual Income: ");
                userIncome = scanner.nextDouble();

                //Income should be greater than zero
                if (userIncome == 0 || userIncome < 0 )
                {
                    //invalid response
                    System.out.println("\nInvalid Income(Zero or Less than Zero Not Possible ). Please enter a valid email address.\n");
                    userIncomeFlag = true;
                }
                else
                {
                    userIncomeFlag = false;
                    //for vaild response and close the loop
                }
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("\nInvalid Income Format. Please enter a valid email address.\n");
                userIncomeFlag = true;
            }
        }
        //scannerINC.close();
        return userIncome;
    }

    public Double getUserFixedExpense()
    {
        double userFixedExpense = 0.0;
        boolean userFixedExpenseFlag=true;
        //Get the user income and it should be verfied
        while (userFixedExpenseFlag)
        {
            //just to vaild the input from the user
            try
            {
                System.out.println("Enter the approximately fixed expenses amount: ");
                userFixedExpense = scanner.nextDouble();

                //Income should be greater than zero
                if (userFixedExpense == 0 || userFixedExpense < 0 )
                {
                    //invalid response
                    System.out.println("\nInvalid Amount(Zero or Less than Zero Not Possible ). Please enter a valid Expense amount.\n");
                    userFixedExpenseFlag = true;
                }
                else
                {
                    userFixedExpenseFlag = false;
                    //for vaild response and close the loop
                }
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("\nInvalid Amount Format. Please enter a valid email address.\n");
                userFixedExpenseFlag  = true;
            }
        }
        //scannerINC.close();
        return userFixedExpense;
    }

    public Double getUserVariableExpense()
    {
        double userVariableExpense = 0.0;
        boolean userVariableExpenseFlag=true;
        //Get the user FE and it should be Verified
        while (userVariableExpenseFlag)
        {
            //just to valid the input from the user
            try
            {
                System.out.println("Enter the approximately fixed expenses amount: ");
                userVariableExpense = scanner.nextDouble();

                //Income should be greater than zero
                if (userVariableExpense == 0 || userVariableExpense < 0 )
                {
                    //invalid response
                    System.out.println("\nInvalid Amount(Zero or Less than Zero Not Possible ). Please enter a valid Expense amount.\n");
                    userVariableExpenseFlag = true;
                }
                else
                {
                    userVariableExpenseFlag = false;
                    //for valid response and close the loop
                }
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("\nInvalid Amount Format. Please enter a valid expense amount.\n");
                userVariableExpenseFlag  = true;
            }
        }
        //scannerINC.close();
        return userVariableExpense;
    }

    public void createExpenseTable(String userName,String password,String email,String phoneNO)
    {
        try
        {
            //create connection
            connection = DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD);
            stmt = connection.createStatement();

            //create sql query
            String sql;
            sql= "SELECT * FROM user WHERE username='" + userName + "' AND usermail='" + email + "' AND usermobile_no='" + phoneNO + "' AND userpassword='"+ password + "'";

            //execute the query
            rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                String name = rs.getString("username");
                String emailID = rs.getString("usermail");
                String mobileNO = rs.getString("usermobile_no");
                String password1 = rs.getString("userpassword");
                Double income = rs.getDouble("userincome");
                String userID = rs.getString("uid");
                String userloan = rs.getString("userloan");
                double userfixedexpense = rs.getDouble("fixed_expense");
                double uservarexpense = rs.getDouble("var_expense");

                String poststringCreateTable = "_expensedatatable";
                String finalExpenseTableName = userName.replaceAll("\\s", "")+poststringCreateTable;
                String createSQLQ = "Create Table "+ finalExpenseTableName +" (expense_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,amount DOUBLE,remaining_income DOUBLE,category VARCHAR(100),expense_desc VARCHAR(200))";

                stmt.executeUpdate(createSQLQ);
                System.out.println("Table created");
            }
        }
        catch (SQLException e)
        {
            String msg = e.getMessage();
        }
        catch (Exception e)
        {
            String msg = e.getMessage();
        }
        finally
        {
            // Finally is used to close all the object of the SQL class
            try
            {
                //Clean UP Enviroment
                if(stmt != null)
                {
                    stmt.close();
                }
                if (connection != null)
                {
                    connection.close();
                }
                if (rs != null)
                {
                    rs.close();
                }
            }
            catch(SQLException se2)
            {
                String msg = se2.getMessage();
                //se2.printStackTrace();
            }
        }

    }

}
