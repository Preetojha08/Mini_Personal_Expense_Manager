import com.mysql.cj.log.Log;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.sql.*;
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

    public void viewExpenseDataExcel()
    {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM preet_expensedatatable")) {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Expense Data");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Expense ID");
            headerRow.createCell(1).setCellValue("Amount");
            headerRow.createCell(2).setCellValue("Remaining Income");
            headerRow.createCell(3).setCellValue("Category");
            headerRow.createCell(4).setCellValue("Expense Description");

            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(resultSet.getInt("expense_id"));
                row.createCell(1).setCellValue(resultSet.getDouble("amount"));
                row.createCell(2).setCellValue(resultSet.getDouble("remaining_income"));
                row.createCell(3).setCellValue(resultSet.getString("category"));
                row.createCell(4).setCellValue(resultSet.getString("expense_desc"));
            }

            // Write data to Excel file
            try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\devel\\Downloads\\ExpenseData.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Expense data has been written to Excel file.");
                createGraph();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createGraph()
    {
        try (FileInputStream fis = new FileInputStream("C:\\Users\\devel\\Downloads\\ExpenseData.xlsx");
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 5, 1, 15, 20);

            XSSFChart chart = (XSSFChart) drawing.createChart(anchor);
            XDDFChartLegend legend = chart.getOrAddLegend();
            legend.setPosition(LegendPosition.RIGHT);

            XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
            leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);

            XDDFDataSource<String> xs = XDDFDataSourcesFactory.fromStringCellRange(sheet,
                    new CellRangeAddress(1, sheet.getLastRowNum(), 0, 0));
            XDDFNumericalDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                    new CellRangeAddress(1, sheet.getLastRowNum(), 1, 1));

            XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
            XDDFLineChartData.Series series = (XDDFLineChartData.Series) data.addSeries(xs, ys);
            series.setTitle("Expense Data", null);

            chart.plot(data);

            try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\devel\\Downloads\\ExpenseData.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Expense graph has been created successfully.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
