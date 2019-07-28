package application.model;

// https://christianpf.com/basic-personal-budget-categories/

public enum Category {

    RESTAURANTS("Restaurants"),
    GROCERIES("Groceries"),
    RENT("Rent"),
    PHONE("Phone"),
    GYM("Gym"),
    BEAUTY("Beauty"),
    VACATION("Vacation"),
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
    TO_BE_DEFINED("To_be_defined");

    private String name;

    private Category(String s) {
        name = s;
    }

    public String toString(){
        return this.name;
    }
}
