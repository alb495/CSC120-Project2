import java.io.Serializable;

/**
 * Represents a boat in the fleet.
 * Stores type, name, year, model,
 * length, purchase price, and expenses.
 *
 * Supports CSV import/export and formatted printing.
 * @author Asher Berman
 * @version 2.1
 */
public class Boat implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Enum for allowed boat types */
    public enum BoatType {
        POWER, SAILING
    }//End of enumeration for BoatType

    private final BoatType type;
    private final String name;
    private final int year;
    private final String makeModel;
    private final int lengthFeet;
    private final double purchasePrice;

    /** Money spent on this boat (cumulative) */
    private double spent;

    /**
     * Creates a fully defined boat.
     */
    public Boat(BoatType type, String name, int year,
                String makeModel, int lengthFeet, double purchasePrice) {

        this.type = type;
        this.name = name;
        this.year = year;
        this.makeModel = makeModel;
        this.lengthFeet = lengthFeet;
        this.purchasePrice = purchasePrice;
        this.spent = 0.0;
    }//End of boat construction

    /** CSV → Boat factory method */
    public static Boat fromCSV(String csv) {
        try {
            String[] p = csv.split(",");
            if (p.length != 6) return null;

            BoatType type = BoatType.valueOf(p[0].trim().toUpperCase());
            String name = p[1].trim();
            int year = Integer.parseInt(p[2].trim());
            String make = p[3].trim();
            int len = Integer.parseInt(p[4].trim());
            double paid = Double.parseDouble(p[5].trim());

            return new Boat(type, name, year, make, len, paid);
        } catch (Exception e) {
            return null;
        }//End of exception handling
    }//End of fromCSV method

    /** Boat → CSV */
    public String toCSV() {
        return type + "," + name + "," + year + "," +
                makeModel + "," + lengthFeet + "," + purchasePrice;
    }//End of toCSV method

    /** Adds an expense if within remaining budget */
    public boolean addExpense(double amount) {
        if (amount <= getRemainingBudget()) {
            spent += amount;
            return true;
        }//End of if statement
        return false;
    }//End of addExpense method

    //Getters

    public BoatType getType() { return type; }
    public String getName() { return name; }
    public int getYear() { return year; }
    public String getMakeModel() { return makeModel; }
    public int getLengthFeet() { return lengthFeet; }
    public double getPurchasePrice() { return purchasePrice; }
    public double getTotalExpenses() { return spent; }
    public double getRemainingBudget() { return purchasePrice - spent; }

    //Print Formatting

    @Override
    public String toString() {
        return String.format(
                "    %-7s %-20s %4d %-12s %2d' : Paid $ %10.2f : Spent $ %10.2f",
                type,
                name,
                year,
                makeModel,
                lengthFeet,
                purchasePrice,
                spent
        );
    }//End of toString method
}//End of Boat class implementing Serializable
