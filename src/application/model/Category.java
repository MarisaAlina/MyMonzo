package application.model;

// https://christianpf.com/basic-personal-budget-categories/

public enum Category {

    RESTAURANTS("Restaurants"),
    GROCERIES("Groceries"),
    RENT("Rent"),
    PHONE("Phone"),
    GYM("Gym"),
    BEAUTY_BODY("Beauty_Body"),
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
    EDUCATION("Education");

    private String name;

    private Category(String s) {
        name = s;
    }

    public String toString(){
        return this.name;
    }
}
