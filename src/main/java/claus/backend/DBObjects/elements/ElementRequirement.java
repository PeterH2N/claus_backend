package claus.backend.DBObjects.elements;

import claus.backend.DBObjects.DBObject;

public class ElementRequirement implements DBObject
{
    private String category_code;
    private int amount;
    private int cop_id;

    public ElementRequirement(String categoryCode, int amount)
    {
        this.category_code = categoryCode;
        this.amount = amount;
    }

    public ElementRequirement() {}

    public String getCategoryCode()
    {
        return category_code;
    }

    public void setCategoryCode(String categoryCode)
    {
        this.category_code = categoryCode;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
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
    public String getTableName()
    {
        return "element_requirements";
    }
}
