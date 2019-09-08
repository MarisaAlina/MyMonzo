package application.model;

import java.util.HashSet;

public enum Category {

    RESTAURANTS("Restaurants"),
    GROCERIES("Groceries"),
    RENT("Rent"),
    PHONE("Phone"),
    GYM("Gym"),
    BEAUTY("Beauty"),
    TRAVELLING("Travelling"),
    CLOTHING("Clothing"),
    TFL("TFL"),
    BIKE("Bike"),
    MEDICAL("Medical"),
    INSURANCE("Insurance"),
    GARDENING("Gardening"),
    DEBT_REDUCTION("Debt_Reduction"),
    EVENTS("Events"),
    GIFTS("Gifts"),
    EDUCATION("Education"),
    UNDEFINED("undefined");

    private String name;

    private Category(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }

    public static boolean contains(String input) {
        for (Category c : Category.values()) {
            if (c.name().equals(input)) {
                return true;
            }
        }
        return false;
    }

//    public HashSet<String> getEnums() {
//        HashSet<String> values = new HashSet<>();
//        for (Category c : Category.values()) {
//            values.add(c.name());
//        }
//        return values;
//    }
}
