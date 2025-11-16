/**
 * @author Asher Berman
 * @version V1.2
 */

/**
 * Represents a Boat with simple fields.
 */
public class Boat {

    private String type;
    private String name;
    private int year;
    private String makeModel;
    private int lengthFeet;
    private double purchasePrice;

    /**
     * Creates a boat with all required fields.
     */
    public Boat(String type, String name, int year, String makeModel,
                int lengthFeet, double purchasePrice) {

        this.type = type;
        this.name = name;
        this.year = year;
        this.makeModel = makeModel;
        this.lengthFeet = lengthFeet;
        this.purchasePrice = purchasePrice;
    }//End of Boat constructor method

    public String getType() { return type; }
    public String getName() { return name; }
    public int getYear() { return year; }
    public String getMakeModel() { return makeModel; }
    public int getLengthFeet() { return lengthFeet; }
    public double getPurchasePrice() { return purchasePrice; }

    /**
     * Returns formatted output aligned to fixed columns.
     */
    public String formatLine() {
        return String.format("%-10s %-15s %-6d %-15s %-6d $%10.2f",
                type, name, year, makeModel, lengthFeet, purchasePrice);
    }//End of formatLine method

    /**
     * Returns CSV representation.
     */
    public String toCSV() {
        return type + "," + name + "," + year + "," +
                makeModel + "," + lengthFeet + "," + purchasePrice;
    }//End of toCSV method
}//End of Boat class
