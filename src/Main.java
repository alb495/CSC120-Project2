import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main - Main program
 * Handles loading, saving, printing, and modifying a fleet of boats.
 * This class contains the application main() method.
 * @author Asher Berman
 * @version 2.2
 */
public class Main {

    //Constants

    private static final String CSV_FILE = "fleet.csv";
    private static final String DB_FILE = "fleet.db";
    private static final String MENU =
            "(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it : ";

    //Fields

    private final List<Boat> fleet = new ArrayList<>();
    private final Scanner in = new Scanner(System.in);

    //Main Loop

    public void run() {
        System.out.println("Welcome to the Fleet Management System");
        System.out.println("--------------------------------------");

        String cmd;

        do {
            System.out.print("\n" + MENU);
            cmd = in.nextLine().trim().toUpperCase();

            switch (cmd) {

                case "P":
                    printFleet();
                    break;

                case "A":
                    addBoatMenu();
                    break;

                case "R":
                    removeBoatMenu();
                    break;

                case "E":
                    expenseBoatMenu();
                    break;

                case "X":
                    System.out.println("\nExiting the Fleet Management System");
                    saveToDB();
                    break;

                default:
                    System.out.println("Invalid menu option, try again");
            }//End of switch statement

        } while (!cmd.equals("X"));
    }//End of run method

    //Load in file

    public void loadFromCSV() {
        File f = new File(CSV_FILE);

        if (!f.exists()) {
            System.out.println("CSV not found — loading sample fleet.");
            loadSampleFleet();
            return;
        }//End of if condition

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Boat b = Boat.fromCSV(line);
                if (b != null) fleet.add(b);
            }//End of while loop
            System.out.println("Loaded from CSV.");
        } catch (IOException e) {
            System.out.println("Error reading CSV — loading sample fleet.");
            loadSampleFleet();
        }//End of exception handling
    }//End of loadFromCSV method

    private void loadSampleFleet() {
        fleet.add(new Boat(Boat.BoatType.POWER,"Big Brother",2019,"Mako",20,12989.56));
        fleet.add(new Boat(Boat.BoatType.SAILING,"Moon Glow",1973,"Bristol",30,5500.00));
        fleet.add(new Boat(Boat.BoatType.SAILING,"Osita",1988,"Tartan",40,11500.07));
        fleet.add(new Boat(Boat.BoatType.POWER,"Rescue II",2016,"Zodiac",12,8900.00));
    }//End of loadSampleFleet method

    //Save file

    public void saveToDB() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DB_FILE))) {
            out.writeObject(fleet);
        } catch (Exception e) {
            System.out.println("Error saving database.");
        }//End of exception handling
    }//End of saveToDB method

    //Menu

    private void printFleet() {
        System.out.println("\nFleet report:");
        double totalPaid = 0;
        double totalSpent = 0;

        for (Boat b : fleet) {
            System.out.println(b);
            totalPaid += b.getPurchasePrice();
            totalSpent += b.getTotalExpenses();
        }//End of for loop

        System.out.printf(
                "    %-7s %-21s %-4s %-12s %-3s : Paid $ %10.2f : Spent $ %10.2f\n",
                "Total", "", "", "", "",
                totalPaid, totalSpent
        );
    }//End of printFleet method

    private void addBoatMenu() {
        System.out.print("Please enter the new boat CSV data          : ");
        String csv = in.nextLine();

        Boat b = Boat.fromCSV(csv);
        if (b == null) {
            System.out.println("Invalid CSV format.");
            return;
        }//End of if statement
        fleet.add(b);
    }//End of addBoatmenu

    private void removeBoatMenu() {
        System.out.print("Which boat do you want to remove?           : ");
        String name = in.nextLine();

        Boat b = findBoat(name);
        if (b == null) {
            System.out.println("Cannot find boat " + name);
            return;
        }

        fleet.remove(b);
    }//End of removeBoatMenu method

    private void expenseBoatMenu() {
        System.out.print("Which boat do you want to spend on?         : ");
        String name = in.nextLine();

        Boat b = findBoat(name);
        if (b == null) {
            System.out.println("Cannot find boat " + name);
            return;
        }//End of if statement

        System.out.print("How much do you want to spend?              : ");
        String line = in.nextLine();

        try {
            double amt = Double.parseDouble(line);
            if (b.addExpense(amt)) {
                System.out.printf("Expense authorized, $%.2f spent.\n",
                        b.getTotalExpenses());
            } else {
                System.out.printf("Expense not permitted, only $%.2f left to spend.\n",
                        b.getRemainingBudget());
            }//End of if and else conditions
        } catch (Exception e) {
            System.out.println("Invalid amount.");
        }//End of exception handling
    }//End of expenseBoatMenu method

    //Helpers

    private Boat findBoat(String name) {
        for (Boat b : fleet) {
            if (b.getName().equalsIgnoreCase(name)) return b;
        }//End of for loop
        return null;
    }//End of findBoat method

    //Main Entry

    public static void main(String[] args) {
        Main fm = new Main();
        fm.loadFromCSV();
        fm.run();
    }//End of main method
}//End of Main class
