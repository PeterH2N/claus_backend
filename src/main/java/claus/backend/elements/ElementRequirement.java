package claus.backend.elements;

import java.util.ArrayList;

public class ElementRequirement
{
    private String categoryCode;
    private int amount;

    private int copID;

    public ElementRequirement(String categoryCode, int amount)
    {
        this.categoryCode = categoryCode;
        this.amount = amount;
    }

    public ElementRequirement() {}

    public String getCategoryCode()
    {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
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
        return copID;
    }

    public void setCopID(int copID)
    {
        this.copID = copID;
    }

}
