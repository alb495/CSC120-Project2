import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

/**
 * FleetManager using only CSC120 topics:
 * Arrays, Strings, loops, conditionals, and simple file I/O.
 */
public class FleetManager {

    private static final int MAX_BOATS = 200;
    private static Boat[] fleet = new Boat[MAX_BOATS];
    private static int fleetSize = 0;

    /**
     * Reads fleet data from a CSV file.
     */
    public static void loadFromCSV(String filename) {
        try {
            Scanner fs = new Scanner(new File(filename));

            while (fs.hasNextLine() && fleetSize < MAX_BOATS) {
                String line = fs.nextLine();
                String[] p = line.split(",");

                if (p.length == 6) {
                    String type = p[0];
                    String name = p[1];
                    int year = Integer.parseInt(p[2]);
                    String mm = p[3];
                    int len = Integer.parseInt(p[4]);
                    double price = Double.parseDouble(p[5]);

                    fleet[fleetSize] =
                        new Boat(type, name, year, mm, len, price);

                    fleetSize++;
                }//End of if p is 6 condition
            }//End of double check while loop

            fs.close();
        }//End of try loop
        catch (Exception e) {
            System.out.println("Could not load initial fleet data.");
        }//End of exception handling
    }//End of loadFromCSV method

    /**
     * Writes fleet data to CSV file.
     */
    public static void saveToCSV(String filename) {
        try {
            PrintWriter out = new PrintWriter(filename);

            for (int i = 0; i < fleetSize; i++) {
                out.println(fleet[i].toCSV());
            }//End of for loop

            out.close();
        }//End of try loop
        catch (Exception e) {
            System.out.println("Could not save CSV.");
        }//End of exception handling
    }//End of saveToCSV method

    /**
     * Prints formatted fleet list.
     */
    public static void printFleet() {
        System.out.println("\nTYPE       NAME            YEAR   MAKE/MODEL      LEN    PRICE");
        System.out.println("-----------------------------------------------------------------------");

        for (int i = 0; i < fleetSize; i++) {
            System.out.println(fleet[i].formatLine());
        }//End of print loop
    }//End of printFleet method

    /**
     * Adds a boat with validation.
     */
    public static void addBoat(Scanner in) {

        if (fleetSize >= MAX_BOATS) {
            System.out.println("Fleet is full.");
            return;
        }//End of max fleet size check if condition

        System.out.print("Enter type: ");
        String type = in.nextLine().trim();
        while (type.length() == 0) {
            System.out.print("Type cannot be blank. Enter type: ");
            type = in.nextLine().trim();
        }//End of while type does not exist loop

        System.out.print("Enter name: ");
        String name = in.nextLine().trim();
        while (name.length() == 0) {
            System.out.print("Name cannot be blank. Enter name: ");
            name = in.nextLine().trim();
        }//End of while name does not exist loop

        int year = readInt(in, "Enter year: ");

        System.out.print("Enter make/model: ");
        String mm = in.nextLine().trim();
        while (mm.length() == 0) {
            System.out.print("Model cannot be blank. Enter make/model: ");
            mm = in.nextLine().trim();
        }//End of while mm does not exist loop

        int len = readInt(in, "Enter length in feet: ");
        double price = readDouble(in, "Enter purchase price: ");

        fleet[fleetSize] = new Boat(type, name, year, mm, len, price);
        fleetSize++;

        System.out.println("Boat added successfully.");
    }//End of add boat method

    /**
     * Forces user to enter valid integer.
     */
    private static int readInt(Scanner in, String prompt) {
        System.out.print(prompt);
        String text = in.nextLine();

        while (!text.matches("-?\\d+")) {
            System.out.print("Invalid number. " + prompt);
            text = in.nextLine();
        }//End of while text does not match loop

        return Integer.parseInt(text);
    }//End of readInt method

    /**
     * Forces user to enter valid double.
     */
    private static double readDouble(Scanner in, String prompt) {
        System.out.print(prompt);
        String text = in.nextLine();

        while (!text.matches("-?\\d+(\\.\\d+)?")) {
            System.out.print("Invalid number. " + prompt);
            text = in.nextLine();
        }//End of while text does not match loop

        return Double.parseDouble(text);
    }//End of readDouble method

    /**
     * Main program loop.
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        loadFromCSV("FleetData.csv");

        boolean run = true;

        while (run) {
            System.out.println("\n--- Fleet Menu ---");
            System.out.println("1. List boats");
            System.out.println("2. Add boat");
            System.out.println("3. Save and exit");
            System.out.print("Choose (1-3): ");

            String ch = in.nextLine().trim();

            if (ch.equals("1")) {
                printFleet();
            }//if condition
            else if (ch.equals("2")) {
                addBoat(in);
            }//First else if condition
            else if (ch.equals("3")) {
                saveToCSV("FleetData.csv");
                run = false;
            }//Second else if condition
            else {
                System.out.println("Invalid option. Try again.");
            }//End of else condition
        }//End of while loop

        System.out.println("\nExiting Fleet Manager.");
        in.close();
    }//End of the main method
}//End of the FleetManager class
