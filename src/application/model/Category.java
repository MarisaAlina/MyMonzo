package application.model;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Category {

    BEAUTY("Beauty"),
    BIKE("Bike"),
    CLOTHING("Clothing"),
    DEBT_REDUCTION("Debt_Reduction"),
    EDUCATION("Education"),
    EVENTS("Events"),
    GARDENING("Gardening"),
    GIFTS("Gifts"),
    GROCERIES("Groceries"),
    GYM("Gym"),
    INSURANCE("Insurance"),
    MEDICAL("Medical"),
    PHONE("Phone"),
    RENT("Rent"),
    RESTAURANTS("Restaurants"),
    TFL("TFL"),
    TRAVELLING("Travelling"),
    UNDEFINED("undefined");

    private String name;

    private Category(String s) {
        this.name = s;
    }

    public String getCategoryByName() {
        return name;
    }

    private static final Map<String, Category> nameToValueMap = new HashMap<>();

    static {
        for (Category category: EnumSet.allOf(Category.class)) {
            nameToValueMap.put(category.getCategoryByName(), category);
        }
    }

    public static Category forName(String name) {
        return nameToValueMap.get(name);
    }

}
