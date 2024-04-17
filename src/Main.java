
import java.util.Scanner;


public class Main
{
    public static void main(String[] args)
    {
        Scanner scannerwm = new Scanner(System.in);

        WelcomeMenu welcome = new WelcomeMenu();
        MainMenu mainmenu = new MainMenu();

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
                            mainmenu.displayMainMenu();
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
                                mainmenu.displayMainMenu();
                            }//End of IF
                        }//End of IF
                        break;

                    case 3:
                        WelcomeMenuExit = false;
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

    }
}