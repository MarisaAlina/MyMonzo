package application.model;

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

    public String toString(){
        return this.name;
    }
}
