package application.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.*;

import javax.annotation.Generated;

/*
- ORMLite Annotation: http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Class-Setup
- https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
 */

@DatabaseTable
public class LineItem {

    @Generated("id")
    private String id;

    @DatabaseField
    private SimpleDoubleProperty amount;

    @DatabaseField
    private SimpleStringProperty date;

    @DatabaseField
    private SimpleStringProperty description;

    @DatabaseField
    private SimpleStringProperty transactionType;

    @DatabaseField
    private SimpleObjectProperty<Category> category;

    private static final Logger LOGGER = LoggerFactory.getLogger(LineItem.class);

    public LineItem() {
    }

    public LineItem(SimpleStringProperty date, SimpleStringProperty description, SimpleStringProperty transactionType, SimpleDoubleProperty amount) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.transactionType = transactionType;
    }

    public LineItem(String date, String description, Double amount, Category category) {
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleDoubleProperty(amount);
        this.description = new SimpleStringProperty(description);
        this.category = new SimpleObjectProperty<Category>(category);
    }


    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getTransactionType() {
        return transactionType.get();
    }

    public SimpleStringProperty transactionTypeProperty() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType.set(transactionType);
    }

    public Category getCategory() {
        return category.get();
    }

    public SimpleObjectProperty<Category> categoryProperty() {
        return category;
    }

    public void setCategory(Category category) {
        this.category.set(category);
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", category=" + category +
                '}';
    }


    public LineItem classifier(LineItem lineItem) {

        String description = lineItem.getDescription();

        switch (description) {
            case "TFL":
                if (description.contains("TFL")) {
                    lineItem.setCategory(Category.TFL);
                    LOGGER.info("Classifying {} to {}", lineItem.getDescription(), Category.TFL);
                }
            case "Restaurants":
                LOGGER.info("Classifying {} to {}", lineItem.getDescription(), Category.RESTAURANTS);
            default:
                lineItem.setCategory(Category.TO_BE_DEFINED);
                LOGGER.info("{} not classified", lineItem.getDescription());
        }
        return lineItem;
    }
}

