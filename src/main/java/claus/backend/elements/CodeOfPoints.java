package claus.backend.elements;

import java.util.ArrayList;

public class CodeOfPoints
{
    private int id;
    private String name;
    private ArrayList<Element> elements = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<ElementRequirement> elementRequirements = new ArrayList<>();

    public ArrayList<Element> getElements()
    {
        return elements;
    }

    public void setElements(ArrayList<Element> elements)
    {
        this.elements = elements;
    }

    public ArrayList<Category> getCategories()
    {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories)
    {
        this.categories = categories;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<ElementRequirement> getElementRequirements()
    {
        return elementRequirements;
    }

    public void setElementRequirements(ArrayList<ElementRequirement> elementRequirements)
    {
        this.elementRequirements = elementRequirements;
    }


}
