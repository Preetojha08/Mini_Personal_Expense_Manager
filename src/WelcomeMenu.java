
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
            String username = scanner.nextLine().trim();

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
        boolean userRegistrationStatus = false;
        try {
            // Make a connection with the database
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Take User values for registration
            String Uname = usersdetails.getUserName();
            String UmobileNo = usersdetails.getUserMobileNo();
            String Uemail = usersdetails.getUserEmailID();
            double Uincome = usersdetails.getUserIncome();
            double UfixedExpense = usersdetails.getUserFixedExpense();
            double UvarExpense = usersdetails.getUserVariableExpense();
            String Uloan = usersdetails.getUserLoan();
            String Upassword = usersdetails.getUserPassword();
            String UID = "";

            if (Uloan.equalsIgnoreCase("y") || Uloan.equalsIgnoreCase("Yes")) {
                System.out.print("\nLoan Information\n");
                double loanAmount = userLoan.getLoanAmount();
                double loanPendingAmount = userLoan.getLoanPendingAmount();
                double loanPaidAmount = loanAmount - loanPendingAmount;

                // Insert loan information
                String loanInsertQuery = "INSERT INTO loan (username, mobile_number, loan_amount, pending_amount, paid_amount, monthly_installment) VALUES (?, ?, ?, ?, ?, ?)";
                ps = connection.prepareStatement(loanInsertQuery);
                ps.setString(1, Uname);
                ps.setString(2, UmobileNo);
                ps.setDouble(3, loanAmount);
                ps.setDouble(4, loanPendingAmount);
                ps.setDouble(5, loanPaidAmount);
                ps.setDouble(6, userLoan.getLoanMonthlyInstallment());
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Loan Information Added");
                }
            }

            // Insert user details
            String insertQuery = "INSERT INTO User (username, userpassword, usermobile_no, usermail, userincome, userloan, fixed_expense, var_expense) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(insertQuery);
            ps.setString(1, Uname);
            ps.setString(2, Upassword);
            ps.setString(3, UmobileNo);
            ps.setString(4, Uemail);
            ps.setDouble(5, Uincome);
            ps.setString(6, Uloan);
            ps.setDouble(7, UfixedExpense);
            ps.setDouble(8, UvarExpense);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve user_id
                String selectUserIdQuery = "SELECT uid FROM user WHERE username = ?";
                PreparedStatement selectUserIdStatement = connection.prepareStatement(selectUserIdQuery);
                selectUserIdStatement.setString(1, Uname);
                ResultSet userIdResultSet = selectUserIdStatement.executeQuery();
                if (userIdResultSet.next()) {
                    UID = userIdResultSet.getString("uid");
                }
                userIdResultSet.close();
                selectUserIdStatement.close();

                // Concatenate username and user_id to form the userexpensetable name
                String Expenstablename = Uname.trim() + UID.trim() + "_expensetable";

                // Insert userexpensetable value along with user_id
                String insertExpenseTableQuery = "UPDATE user SET userexpensetable = ? WHERE uid = ?";
                PreparedStatement insertExpenseTableStatement = connection.prepareStatement(insertExpenseTableQuery);
                insertExpenseTableStatement.setString(1, Expenstablename);
                insertExpenseTableStatement.setString(2, UID);
                insertExpenseTableStatement.executeUpdate();

                System.out.println("Thank You " + Uname + ", for Registration. Now Login with that INFO");
                userRegistrationStatus = true;
                usersdetails.createExpenseTable(Uname, Upassword, Uemail, UmobileNo);
            } else {
                System.out.println("Failed to Register new User.");
                userRegistrationStatus = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }
        return userRegistrationStatus;
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
                userInfo.setUserFixedExpense(rs.getDouble("fixed_expense"));
                userInfo.setUserVarExpense(rs.getDouble("var_expense"));
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
