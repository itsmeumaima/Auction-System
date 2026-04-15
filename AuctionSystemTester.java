// import java.util.Scanner;

// public class AuctionSystemTester {
//     public static void main(String[] args) {
//         char choice;
//         AuctionSystem auctionSystem = new AuctionSystem();
//         try {
//             do {
//                 System.out.println("\n\n");
//                 System.out.println("1. Start Auction");
//                 System.out.println("2. Place Bid");
//                 System.out.println("3. Close Auction");
//                 System.out.println("4. Quit");
//                 System.out.println("Enter choice 1-4");
//                 choice = new Scanner(System.in).next().charAt(0);
//                 System.out.println(); // blank line

//                 try {
//                     switch (choice) {
//                         case '1':
//                             option1(auctionSystem);
//                             break;
//                         case '2':
//                             option2(auctionSystem);
//                             break;
//                         case '3':
//                             option3(auctionSystem);
//                             break;
//                         default:
//                             break;
//                     }
//                 } catch (RuntimeException e) {
//                     e.printStackTrace();
//                 }
//             } while (choice != '4');
//         } catch (RuntimeException e) {
//             System.out.println("Initial object breaks invariant");
//             System.out.println("\nPress Enter to quit");
//             new Scanner(System.in).nextLine();
//         }
//     }

//     private static void option1(AuctionSystem auctionSystem) {
//         System.out.println("Enter initial price: ");
//         double initPrice = new Scanner(System.in).nextDouble();
//         System.out.println("Enter auction item: ");
//         String item = new Scanner(System.in).next();
//         System.out.println("Enter start date: ");
//         String startDate = new Scanner(System.in).next();
//         System.out.println("Enter end date: ");
//         String endDate = new Scanner(System.in).next();

//         auctionSystem.startAuction(initPrice, item, startDate, endDate);
//         System.out.println("Auction started successfully");
//     }

//     private static void option2(AuctionSystem auctionSystem) {
//         System.out.println("Enter bid amount: ");
//         double amount = new Scanner(System.in).nextDouble();
//         System.out.println("Enter bid date: ");
//         String date = new Scanner(System.in).next();
//         System.out.println("Enter bidder name: ");
//         String name = new Scanner(System.in).next();

//         auctionSystem.placeBid(amount, date, name);
//         System.out.println("Bid placed successfully");
//     }

//     private static void option3(AuctionSystem auctionSystem) {
//         System.out.println("Enter closing date: ");
//         String closingDate = new Scanner(System.in).next();

//         auctionSystem.closeAuction(closingDate);

//         System.out.println("Auction closed successfully");
//     }
// }

import java.util.Scanner;

public class AuctionSystemTester {

    public static void main(String[] args) {

        AuctionSystem auctionSystem = new AuctionSystem();
        Scanner sc = new Scanner(System.in);

        char choice;

        do {
            System.out.println("\n1. Start Auction");
            System.out.println("2. Place Bid");
            System.out.println("3. Close Auction");
            System.out.println("4. Quit");
            System.out.print("Enter choice: ");

            choice = sc.next().charAt(0);

            try {
                switch (choice) {
                    case '1' -> option1(auctionSystem, sc);
                    case '2' -> option2(auctionSystem, sc);
                    case '3' -> option3(auctionSystem, sc);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != '4');

        sc.close();
    }

    private static void option1(AuctionSystem auctionSystem, Scanner sc) {
        System.out.print("Enter initial price: ");
        double price = sc.nextDouble();

        System.out.print("Enter item: ");
        String item = sc.next();

        System.out.print("Enter start date (yyyy-mm-dd): ");
        String start = sc.next();

        System.out.print("Enter end date (yyyy-mm-dd): ");
        String end = sc.next();

        auctionSystem.startAuction(price, item, start, end);
        System.out.println("Auction started");
    }

    private static void option2(AuctionSystem auctionSystem, Scanner sc) {
        System.out.print("Enter bid amount: ");
        double amount = sc.nextDouble();

        System.out.print("Enter bid date (yyyy-mm-dd): ");
        String date = sc.next();

        System.out.print("Enter bidder name: ");
        String name = sc.next();

        auctionSystem.placeBid(amount, date, name);
        System.out.println("Bid placed");
    }

    private static void option3(AuctionSystem auctionSystem, Scanner sc) {
        System.out.print("Enter closing date: ");
        String date = sc.next();

        auctionSystem.closeAuction(date);
    }
}