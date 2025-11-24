import java.io.Serializable;
//End of imports

/**
 * Represents a single boat in the fleet, storing type, name, specifications,
 * purchase price, and tracked expenses. Supports CSV serialization and
 * controlled expense addition with budget limits.
 *
 * @author : Asher Berman
 * @version : 2.1
 */
public class Boat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Enum representing the two supported types of boats.
     */
    public enum BoatType { POWER, SAILING }

    private final BoatType type;
    private final String name;
    private final int year;
    private final String makeModel;
    private final int lengthFeet;
    private final double purchasePrice;

    private double spent;

    /**
     * Constructs a new Boat.
     *
     * @param type          The type of boat (POWER or SAILING)
     * @param name          The name of the boat
     * @param year          The manufacturing year of the boat
     * @param makeModel     The boat's make or model
     * @param lengthFeet    Length of the boat in feet
     * @param purchasePrice Initial purchase price of the boat
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
    }//End of Boat defenition

    // ---------- CSV → Boat ----------

    /**
     * Creates a Boat object from a CSV-formatted line.
     * Expected format:
     *     TYPE,NAME,YEAR,MAKEMODEL,LENGTH,PRICE
     *
     * @param csv A comma-separated string describing the boat
     * @return A Boat object if parsing succeeds, otherwise null
     */
    public static Boat fromCSV(String csv) {
        try {
            String[] p = csv.split(",");
            if (p.length != 6) return null;

            BoatType type = BoatType.valueOf(p[0].trim().toUpperCase());
            String name = p[1].trim();
            int year = Integer.parseInt(p[2].trim());
            String makeModel = p[3].trim();
            int length = Integer.parseInt(p[4].trim());
            double paid = Double.parseDouble(p[5].trim());

            return new Boat(type, name, year, makeModel, length, paid);
        } catch (Exception e) {
            return null;
        }//End of exception handling
    }//End of fromCSV method

    // ---------- Boat → CSV ----------

    /**
     * Converts this boat into a CSV-formatted string.
     *
     * @return CSV representation of this Boat
     */
    public String toCSV() {
        return type + "," + name + "," + year + "," +
                makeModel + "," + lengthFeet + "," + purchasePrice;
    }//End of toCSV method

    // ---------- Expense Logic ----------

    /**
     * Attempts to add an expense for this boat, but only if it does not exceed
     * the remaining budget (purchase price minus total spent).
     *
     * @param amount The amount to spend
     * @return true if the expense was approved and applied, false otherwise
     */
    public boolean addExpense(double amount) {
        if (amount <= 0) return false;

        if (amount <= getRemainingBudget()) {
            spent += amount;
            return true;
        }//End of expense balance check over 0 condition
        return false;
    }//End of addExpense method

    /**
     * Computes how much budget remains before reaching the purchase price.
     *
     * @return Remaining allowable spending before hitting the purchase price
     */
    public double getRemainingBudget() {
        return purchasePrice - spent;
    }//End of getRemainingBudget method

    // ---------- Getters ----------

    /**
     * @return The boat's type
     */
    public BoatType getType() { return type; }

    /**
     * @return The boat's name
     */
    public String getName() { return name; }

    /**
     * @return The boat's manufacturing year
     */
    public int getYear() { return year; }

    /**
     * @return The make or model of the boat
     */
    public String getMakeModel() { return makeModel; }

    /**
     * @return The boat length in feet
     */
    public int getLengthFeet() { return lengthFeet; }

    /**
     * @return The original purchase price
     */
    public double getPurchasePrice() { return purchasePrice; }

    /**
     * @return Total approved expenses already applied
     */
    public double getTotalExpenses() { return spent; }

    // ---------- Pretty Print ----------

    /**
     * Produces a formatted printable description of the boat, including
     * type, name, year, model, length, purchase cost, and total expenses.
     *
     * @return A formatted multi-field string describing the boat
     */
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
}//End of Boat implementation class
