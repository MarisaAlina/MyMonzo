package application.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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


    private static final Map<String, Category> nameToValueMap = new HashMap<>();

    static {
        for (Category category: EnumSet.allOf(Category.class)) {
            nameToValueMap.put(category.name(), category);
        }
    }

    public static Category forName(String name) {
        return nameToValueMap.get(name);
    }
}
