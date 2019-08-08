package application.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

import javax.annotation.Generated;

// ORMLite Annotation: http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Class-Setup

@DatabaseTable
public class LineItem {

    @Generated("id")
    private String id;

    @DatabaseField
    private Double value;

    @DatabaseField
    private String date;

    @DatabaseField
    private String description;

    @DatabaseField
    private String transactionType;

    @DatabaseField
    private Category category;

    private static final Logger LOGGER = LoggerFactory.getLogger(LineItem.class);

    public LineItem(String date, String description, String transactionType, double value) {
        this.value = value;
        this.date = date;
        this.description = description;
        this.transactionType = transactionType;
    }

    public LineItem(String date, String description, String transactionType, double value, Category category) {
        this.date = date;
        this.value = value;
        this.description = description;
        this.category = category;
    }

    // When an object is returned from a query
    // ORMLite constructs the object using Java reflection and a constructor call
    public LineItem() {
    }

    public LineItem classifier(LineItem lineItem) {
        String description = lineItem.getDescription();
        switch (description) {
            case "TFL":
                LOGGER.info("Classifying {} to {}", lineItem.toString(), Category.TFL);
                lineItem.category = Category.TFL;
            case "Restaurants":
                LOGGER.info("Classifying {} to {}", lineItem.toString(), Category.RESTAURANTS);
            case "Rent":
                LOGGER.info("Classifying {} to {}", lineItem.toString(), Category.RENT);
            case "Travelling":
                LOGGER.info("Classifying {} to {}", lineItem.toString(), Category.TRAVELLING);
            default:
                LOGGER.info("{} not classified", lineItem.toString());
                lineItem.category = Category.TO_BE_DEFINED;
        }
        return lineItem;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", category=" + category +
                '}';
    }
}
