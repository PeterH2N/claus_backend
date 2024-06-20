package claus.backend.DBObjects.elements;

import claus.backend.DBObjects.DBObject;

public class Element implements DBObject
{
    String code;
    String name;
    String description;
    double difficulty;
    String category_code;
    int cop_id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategoryCode() {
        return category_code;
    }

    public void setCategoryCode(String categoryCode) {
        this.category_code = categoryCode;
    }

    public int getCopID()
    {
        return cop_id;
    }

    public void setCopID(int copID)
    {
        this.cop_id = copID;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public String getTableName()
    {
        return "elements";
    }
}
