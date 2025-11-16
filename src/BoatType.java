/**
 * BoatType enumeration: the two allowed boat types for this project.
 *
 * Author: Asher Berman
 * Version: 1.0
 */
public enum BoatType {
    SAILING,
    POWER;

    /**
     * Parse a string into a BoatType, case-insensitive.
     * Returns null for an unrecognized value.
     *
     * @param s input string
     * @return BoatType or null
     */
    public static BoatType fromString(String s) {
        if (s == null) return null;
        s = s.trim().toUpperCase();
        if (s.equals("SAILING") || s.equals("S")) return SAILING;
        if (s.equals("POWER") || s.equals("P")) return POWER;
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}