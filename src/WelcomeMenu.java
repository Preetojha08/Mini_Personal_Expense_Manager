import java.sql.*;
import java.util.Scanner;

public class WelcomeMenu
{
    //different object of different Classes.
    UserDetails usersdetails = new UserDetails();
    UserLoan userLoan = new UserLoan();


    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/expensemanagerdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Papa@2062";

    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    boolean HomeMenuFlag = false;

    public WelcomeMenu()
    {
        //Empty constructor
    }
    //User Login Function Body
    public boolean userLogin()
    {
        boolean loginStatus = false;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username=? AND userpassword=?"))
        {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    System.out.println("Login successful!");
                    loginStatus = true;
                    getUserInformation(username, password);
                }
                else
                {
                    System.out.println("Invalid username or password.");
                }
            }
        }//Ends
        catch (SQLException e)
        {
            System.err.println("Error occurred while attempting to login: " + e.getMessage());
        }//Ends Catch
        return loginStatus;
    }//Ends Login Function

    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public boolean userRegistration()
    {
        boolean userResgistrationStatus = false;
        try
        {
            //make a connection with db
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            //Take User values for registration
            String Uname = usersdetails.getUserName();
            String UmobileNo = usersdetails.getUserMobileNo();
            String Uemail = usersdetails.getUserEmailID();
            String Upassword = usersdetails.getUserPassword();
            double Uincome = usersdetails.getUserIncome();
            String Uloan = usersdetails.getUserLoan();
            double UfixedExpense = usersdetails.getUserFixedExpense();
            double UvarExpense = usersdetails.getUserVariableExpense();

            if (Uloan.equalsIgnoreCase("y") || Uloan.equalsIgnoreCase("Yes"))
            {
                double loanAmount = userLoan.getLoanAmount();
                double loanPendingAmount = userLoan.getLoanPendingAmount();
                double loanPaidAmount = userLoan.getLoanPendingAmount();

                String loanInsertQuery = "INSERT INTO loan (username,mobile_number,loan_amount,pending_amount,paid_amount) "+ "VALUES(?, ?, ?, ?, ?)";
                ps = connection.prepareStatement(loanInsertQuery);
                ps.setString(1, Uname);
                ps.setString(2, UmobileNo);
                ps.setDouble(3, loanAmount);
                ps.setDouble(4, loanPendingAmount);
                ps.setDouble(5, loanPaidAmount);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0)
                {
                    System.out.println("Data added Successfully to Loan Table!");
                }

            }
            String prefixET = "_expensedatatable";
            String expensetable = Uname.toLowerCase()+prefixET;

            String insertQuery = "INSERT INTO User (username, userpassword, usermobile_no, usermail, userincome, userloan, userexpensetable,fixed_expense,var_expense) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            //Creating a Prepared statement object
            ps = connection.prepareStatement(insertQuery);
            ps.setString(1, Uname);
            ps.setString(2, Upassword);
            ps.setString(3, UmobileNo);
            ps.setString(4, Uemail);
            ps.setDouble(5, Uincome);
            ps.setString(6, Uloan);
            ps.setString(7, expensetable);
            ps.setDouble(8, UfixedExpense);
            ps.setDouble(9, UvarExpense);

            // Executing the insertion query
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("New User added Successfully!");
                userResgistrationStatus=true;
                usersdetails.createExpenseTable(Uname,Upassword,Uemail,UmobileNo);
            }
            else
            {
                System.out.println("Failed to add new User.");
                userResgistrationStatus=false;
            }
        }
        catch (SQLException e)
        {
            //e.printStackTrace();
            e.getMessage();
        }
        finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                }
                if (connection != null)
                {
                    connection.close();
                }
            }
            catch (SQLException e)
            {
                //e.printStackTrace();
                e.getMessage();
            }
        }
        return userResgistrationStatus;
    }

    public void getUserInformation(String username,String password)
    {
        try
        {
            connection = DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD);
            stmt = connection.createStatement();

            String sql = "SELECT * FROM user WHERE username='" + username + "' AND userpassword='" + password + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                UserInfo userInfo = new UserInfo();

                userInfo.setUserName(rs.getString("username"));
                userInfo.setUserPassword(rs.getString("userpassword"));
                userInfo.setUserPhone(rs.getString("usermobile_no"));
                userInfo.setUserEmail(rs.getString("usermail"));
                userInfo.setUserIncome(rs.getDouble("userincome"));
                userInfo.setUserLoan(rs.getString("userloan"));
                userInfo.setUserExpenseTable(rs.getString("userexpensetable"));
                userInfo.setUserID(rs.getString("uid"));
            }
            // Clean-up environment
            rs.close();
            stmt.close();
            connection.close();

        }
        catch (SQLException e)
        {
            // Handle errors for JDBC
            e.getMessage();
        }
        catch(Exception e)
        {
            // Handle errors for any other kind os error
            e.getMessage();
        }
        finally
        {
            // Finally is used to close all the object of the SQL class
            try
            {
                if(stmt != null)
                {
                    stmt.close();
                }
                if (connection != null)
                {
                    connection.close();
                }
            }
            catch(SQLException se2)
            {
                se2.getMessage();
            }
        }

    }
}
