public class UserInfo
{

    private static String userName;
    private static String userPassword;
    private static String userEmail;
    private static String userPhone;
    private static double userIncome;
    private static String userLoan;
    private static String userExpenseTable;
    private static String userID;
    private static double userFixedExpense;
    private static double userVarExpense;


    //------------------------------Getter Methods--------------------------------------------------------------------------------------
    public static String getUserID() {
        return userID;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static String getUserPhone() {
        return userPhone;
    }

    public static double getUserIncome() {
        return userIncome;
    }

    public static String getUserLoan() {
        return userLoan;
    }

    public static String getUserExpenseTable() {
        return userExpenseTable;
    }

    public static double getUserFixedExpense() {
        return userFixedExpense;
    }

    public static double getUserVarExpense() {
        return userVarExpense;
    }

    //------------------------------Setter Methods--------------------------------------------------------------------------------------

    public static void setUserID(String userID) {
        UserInfo.userID = userID;
    }

    public static void setUserName(String userName) {
        UserInfo.userName = userName;
    }

    public static void setUserPassword(String userPassword) {
        UserInfo.userPassword = userPassword;
    }

    public static void setUserEmail(String userEmail) {
        UserInfo.userEmail = userEmail;
    }

    public static void setUserPhone(String userPhone) {
        UserInfo.userPhone = userPhone;
    }

    public static void setUserIncome(double userIncome) {
        UserInfo.userIncome = userIncome;
    }

    public static void setUserLoan(String userLoan) {
        UserInfo.userLoan = userLoan;
    }

    public static void setUserExpenseTable(String userExpenseTable) {
        UserInfo.userExpenseTable = userExpenseTable;
    }

    public static void setUserFixedExpense(double userFixedExpense) {
        UserInfo.userFixedExpense = userFixedExpense;
    }

    public static void setUserVarExpense(double userVarExpense) {
        UserInfo.userVarExpense = userVarExpense;
    }

}
