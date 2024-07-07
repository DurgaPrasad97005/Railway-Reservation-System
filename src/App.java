import myPackage.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        Train train = new Train();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (true) {
            System.out.println("1.Book Ticket \n2.Cancel Ticket \n3.Print Chat \n4.Exit");
            System.out.println("Enter your choice:");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    int ticketNum = train.bookTicket();
                    if (ticketNum != -1) {
                        System.out.println("Your ticket booked successfully. The ticket details are as follows: ");
                        train.printTicketDetails(ticketNum);
                    } else {
                        System.out.println("Sorry. The ticket is not booked.");
                        System.out.println("___________________________________________");
                    }
                    break;

                case 2:
                    System.out.println("Enter your ticket number: ");
                    int num = sc.nextInt();
                    boolean status = train.cancelTicket(num);
                    if (status) {
                        System.out.println("Your ticket cancelled successfully.");
                        System.out.println("___________________________________________");
                    } else {
                        System.out.println("The ticket is not even booked.");
                        System.out.println("___________________________________________");
                    }
                    break;

                case 3:
                    train.printChat();
                    break;

                case 4:
                    exit = true;
                    break;

                default:
                    System.out.println("You entered wrong choice.");
                    break;
            }

            if (exit)
                break;
        }

        sc.close();
    }
}
