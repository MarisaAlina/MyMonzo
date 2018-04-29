package application.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.annotation.Generated;
import java.util.Date;

// ORMLite Annotation: http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Class-Setup

@DatabaseTable
public class LineItem {

    @Generated("id")
    private String id;

    @DatabaseField
    private int value;
    @DatabaseField
    private Date date;
    @DatabaseField
    private String description;
    @DatabaseField
    private Category category;


    public LineItem(int value, Date date, String description, Category category) {
        this.value = value;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    // When an object is returned from a query, ORMLite constructs the object using Java reflection and a constructor needs to be called.
    public LineItem() {
    }


    public Category classifier(LineItem lineItem){
        String description = lineItem.getDescription();
        switch (description) {
            case "TFL": return Category.TFL;
            default: return Category.TO_BE_DEFINED;
        }
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    @Override
    public String toString() {
        return "LineItem{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", category=" + category +
                '}';
    }
}
