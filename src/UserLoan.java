import java.util.Scanner;

public class UserLoan
{
    Scanner scanner = new Scanner(System.in);
    double MainAmountLoan = 0.0;

    public UserLoan()
    {
        //Empty Constructor
    }

    //Get Loan Amount
    public Double getLoanAmount()
    {
        double amountLoan = 0.0;
        boolean amountLoanFlag = true;
        while(amountLoanFlag)
        {
            System.out.print("Enter the LOAN amount you have taken: ");
            amountLoan = scanner.nextDouble();
            //vaild response
            if(amountLoan > 0.0)
            {
                amountLoanFlag = false;
                this.MainAmountLoan = amountLoan;
            }
            //invaild response
            else
            {
                System.out.print("\nInvaild Entry please enter a positive number");
            }
        }
        return amountLoan;
    }

    public Double getLoanPendingAmount()
    {
        double loanPending = 0.0;
        boolean loanPendingFlag = true;
        while(loanPendingFlag)
        {
            System.out.print("Enter the LOAN pending amount: ");
            loanPending = scanner.nextDouble();

            //vaild response
            if(loanPending < MainAmountLoan && loanPending > 0)
            {
                loanPendingFlag = false;
            }
            //invaild response
            else
            {
                System.out.print("\nInvaild Entry please enter a positive number");
            }
        }
        return loanPending;
    }

    public Double getLoanPaidAmount()
    {
        double loanAmountPaid = 0.0;
        boolean loanAmountPaidFlag = true;
        while(loanAmountPaidFlag)
        {
            System.out.println("\nEnter the LOAN amount You paid to Bank: ");
            loanAmountPaid = scanner.nextDouble();

            //vaild response
            if(loanAmountPaid < MainAmountLoan && loanAmountPaid > 0)
            {
                loanAmountPaidFlag = false;
            }
            //invaild response
            else
            {
                System.out.print("\nInvaild Entry please enter a positive number");
            }
        }
        return loanAmountPaid;
    }

}
