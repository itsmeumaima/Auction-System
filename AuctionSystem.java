// import java.util.ArrayList;
// import java.util.List;

// public class AuctionSystem implements InvariantCheck {
//     private Double reservePrice;
//     private Double currentBid;
//     private String bidItem;
//     private String startDate;
//     private String endDate;
//     private String auctionDate;
//     private Double bidAmount;
//     private List<String> stack;

//     private void invTest() {
//         if (!inv()) {
//             throw new RuntimeException("Invariant violation!");
//         }
//     }

//     // Constructor
//     public AuctionSystem() {
//         this.reservePrice = null;
//         this.currentBid = null;
//         this.bidItem = null;
//         this.startDate = null;
//         this.endDate = null;
//         this.auctionDate = null;
//         this.bidAmount = null;
//         this.stack = new ArrayList<>();
//         invTest();
//     }

//     // Function to check if price is in range
//     private boolean inPriceRange(Double price) {
//         return price >= MIN_PRICE;
//     }

//     // Operation to start the auction
//     public void startAuction(Double initPrice, String bItem, String sDate, String eDate) {
//         // Preconditions
//         if ((reservePrice != null) && startDate != null && (endDate != null) && bidItem != null) {
//             throw new IllegalStateException("Auction cannot start");
//         }      

//         // Postconditions
//         reservePrice = initPrice;
//         startDate = sDate;
//         endDate = eDate;
//         currentBid = reservePrice;
//         bidItem = bItem;
//         invTest();
//     }

//     // Operation to place a bid
//     public void placeBid(Double amount, String date, String name) { 
//         if (!(((amount > reservePrice) && (amount > currentBid)) &&  (startDate.compareTo(date) <= 0 && endDate.compareTo(date) >= 0))) {
//             throw new IllegalArgumentException("Invalid bid");
//         }

//         // Postconditions
//         bidAmount = amount;
//         auctionDate = date;
//         stack.add(name);
//         currentBid = bidAmount;
//         invTest();
//     }

//     // Operation to close the auction
//     public void closeAuction(String date) {
//         // Preconditions
//         if (!(currentBid > reservePrice) && stack.isEmpty() || !date.equals(endDate)) {
//             throw new IllegalStateException("Invalid auction closure");
//         }
        
//         // Postconditions
//         if (!stack.isEmpty()) {
//             String auctionWinner = stack.get(stack.size() - 1); // Get the last bidder
//             Double winningBid = currentBid;

//             System.out.println("Auction closed successfully");
//             System.out.println("Winner: " + auctionWinner);
//             System.out.println("Winning Bid: " + winningBid);
//             invTest();

//         } else {
//             System.out.println("Auction closed with no winner"); // Handle the case with no bidders
//         }
//     }

//     // Constants
//     private static final Double MIN_PRICE = 1000.0;

//     public boolean inv() {
//         return  ( bidAmount == null || bidAmount != null) &&
//                 ( reservePrice == null || (inPriceRange(reservePrice)) ) &&
//                 (bidItem == null || bidItem != null) &&
//                 (currentBid == null || currentBid == reservePrice || currentBid == bidAmount) &&
//                 (startDate == null || startDate != null) &&
//                 (endDate == null || endDate != null && (endDate.compareTo(startDate) >=0)) &&
//                 (auctionDate == null || auctionDate != null) &&
//                 (stack.isEmpty() || !stack.isEmpty());
//     }

//     public static void main(String[] args) {
//         AuctionSystem auctionSystem = new AuctionSystem();

//         // Test StartAuction
//         auctionSystem.startAuction(1000.0, "Item1", "2024-01-10", "2024-01-20");
//         System.out.println("Auction started successfully");

//         // Test PlaceBid
//         auctionSystem.placeBid(1200.0, "2024-01-15", "Bidder1");
//         System.out.println("Bid placed successfully");

//         auctionSystem.placeBid(1500.0, "2024-01-17", "Bidder2");
//         System.out.println("Bid placed successfully");

//         // Test CloseAuction
//         auctionSystem.closeAuction("2024-01-20");
//         System.out.println("Auction closed successfully");
//     }
// }

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuctionSystem implements InvariantCheck {

    private Double reservePrice;
    private Double currentBid;
    private String bidItem;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate auctionDate;

    private List<Bid> bids;

    private static class Bid {
        double amount;
        String bidder;
        LocalDate date;

        Bid(double amount, String bidder, LocalDate date) {
            this.amount = amount;
            this.bidder = bidder;
            this.date = date;
        }
    }

    // Constructor
    public AuctionSystem() {
        this.reservePrice = null;
        this.currentBid = null;
        this.bidItem = null;
        this.startDate = null;
        this.endDate = null;
        this.auctionDate = null;
        this.bids = new ArrayList<>();
        invTest();
    }

    // invariant check
    private void invTest() {
        if (!inv()) {
            throw new RuntimeException("Invariant violation!");
        }
    }

    private static final double MIN_PRICE = 1000.0;

    private boolean inPriceRange(double price) {
        return price >= MIN_PRICE;
    }

    // ================= START AUCTION =================
    public void startAuction(Double initPrice, String item, String sDate, String eDate) {

        LocalDate start = LocalDate.parse(sDate);
        LocalDate end = LocalDate.parse(eDate);

        if (reservePrice != null) {
            throw new IllegalStateException("Auction already started");
        }

        if (!inPriceRange(initPrice)) {
            throw new IllegalArgumentException("Price below minimum");
        }

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date before start date");
        }

        reservePrice = initPrice;
        currentBid = initPrice;
        bidItem = item;
        startDate = start;
        endDate = end;

        invTest();
    }

    // ================= PLACE BID =================
    public void placeBid(Double amount, String date, String name) {

        if (startDate == null || endDate == null) {
            throw new IllegalStateException("Auction not started");
        }

        LocalDate bidDate = LocalDate.parse(date);

        if (bidDate.isBefore(startDate) || bidDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid bid date");
        }

        if (amount <= currentBid) {
            throw new IllegalArgumentException("Bid too low");
        }

        currentBid = amount;
        auctionDate = bidDate;
        bids.add(new Bid(amount, name, bidDate));

        invTest();
    }

    // ================= CLOSE AUCTION =================
    public void closeAuction(String date) {

        LocalDate closeDate = LocalDate.parse(date);

        if (!closeDate.equals(endDate)) {
            throw new IllegalStateException("Must close on end date");
        }

        if (bids.isEmpty()) {
            System.out.println("Auction closed with no bids");
            return;
        }

        Bid winner = bids.get(bids.size() - 1);

        System.out.println("Auction Closed!");
        System.out.println("Winner: " + winner.bidder);
        System.out.println("Winning Bid: " + winner.amount);

        invTest();
    }

    // ================= INVARIANT =================
    @Override
    public boolean inv() {

        return (reservePrice == null || reservePrice >= MIN_PRICE)
            && (currentBid == null || currentBid >= reservePrice)
            && (startDate == null || endDate == null || !endDate.isBefore(startDate))
            && (bids != null);
    }
}