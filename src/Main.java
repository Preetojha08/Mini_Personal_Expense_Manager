import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Main
{
    public static void main(String[] args)
    {
        Scanner scannerwm = new Scanner(System.in);

        WelcomeMenu welcome = new WelcomeMenu();
        MainMenu mainmenu = new MainMenu();
        Expense expense = new Expense();
        UserInfo userinfo = new UserInfo();

        boolean WelcomeMenuExit = true;//used to check the exit status of welcome menu.
        boolean RegAfterLogin ;//used to check registration is completed than processed to login.
        boolean MainMenuCallFlag = false;//check we can call the main menu or not.

        //-------------------------------------------Welcome Menu------------------------------------------------//

        System.out.println("\nWelcome to the Expense Manger System");
        while (WelcomeMenuExit)
        {
            System.out.println("\nWelcome Menu:");
            System.out.println("1. User Login");
            System.out.println("2. New User Registration");
            System.out.println("3. Exit");

            try
            {
                System.out.print("Enter your choice: ");
                int WelcomeMenuChoice = Integer.parseInt(scannerwm.nextLine());

                switch (WelcomeMenuChoice)
                {
                    case 1:
                        //user Login function call;
                        System.out.println("\nUser Login Process");
                        MainMenuCallFlag = welcome.userLogin();
                        if(MainMenuCallFlag)
                        {
                            WelcomeMenuExit = false;
                        }//End of IF
                        break;

                    case 2:
                        //user Registration call;
                        System.out.println("\nNew User Registration Process");
                        RegAfterLogin = welcome.userRegistration();
                        if (RegAfterLogin)
                        {
                            MainMenuCallFlag = welcome.userLogin();
                            if(MainMenuCallFlag)
                            {
                                WelcomeMenuExit = false;
                            }//End of IF
                        }//End of IF
                        break;

                    case 3:
                        WelcomeMenuExit = false;
                        MainMenuCallFlag = false;
                        System.out.println("Exiting program. Goodbye!");
                        System.out.println("Thanks For Using ME !");
                        break;

                    default:
                        System.out.println("Invalid choice: Try again ");

                }//End of Switch

            }//End of try

            catch (NumberFormatException e)
            {
                String s =e.getMessage();
                System.out.println("Invalid Format "+s);
            }//End of catch

            catch (Exception e)
            {
                String s =e.getMessage();
                System.out.println("Invalid choice "+s);
                break;
            }//End of catch

        }//End of While Loop

        //-------------------------------------------Main Menu------------------------------------------------//

        boolean MainMenuExit = true; //for main menu input validation

        while (MainMenuExit)
        {
            System.out.println("\nMain Menu:");
            System.out.println("1. Update User Details"); //User Details will be updated here(like name , mobile no,email) And it will also update income and fixed and variable expense will be updated here .
            System.out.println("2. Add Expense"); //Add expense what and how much you send on something
            System.out.println("3. View Transaction History"); //view the entire transaction history of expense with its details with income information
            //System.out.println("4. Saving"); //View the budget planning information or steps and tips to save money
            System.out.println("4. Budget Planning Tips"); //just back to pervious menu
            System.out.println("5. Logout"); //just back to pervious menu
            System.out.println("6. Exit from the Program"); //shut down the program.

            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter your choice: ");
                String line= reader.readLine();

                //int MainMenuChoice = Integer.parseInt(scannerwm.nextLine());
                int MainMenuChoice = Integer.parseInt(line);

                switch (MainMenuChoice)
                {
                    case 1:
                        mainmenu.UpdateUserInfo();
                        break;
                    case 2:
                        //updateIncome();
                        double expAmount=expense.getAmountExpense();
                        String expCategory =expense.getExpenseCategory();
                        String expDescription =expense.getExpenseDecs();
                        double curentIncome =userinfo.getUserIncome();
                        double RIncome = curentIncome - expAmount;
                        boolean addexpenseFlag = expense.addExpenseData(expAmount,RIncome,expCategory,expDescription);
                        System.out.println(addexpenseFlag);
                        if (addexpenseFlag)
                        {
                            expense.UpdateIncome(RIncome);
                            System.out.println("Helloworld preet");
                            userinfo.setUserIncome(RIncome);
                        }
                        break;
                    case 3:
                        //addExpense();
                        mainmenu.ViewEexpenseData();
                        break;
                    case 4:
                        //budgetPlanning();
                        System.out.println("View Saving of the USer here");
                        break;
                    case 5:
                        //budgetPlanning();
                        mainmenu.BudgetPlanning();
                        break;
                    case 6:
                        //logout();
                        System.out.println("User Logout Function comes here");
                        break;
                    case 7:
                        System.out.println("Exiting program. Goodbye!");
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

    }
}