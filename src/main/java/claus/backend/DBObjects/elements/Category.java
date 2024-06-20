package claus.backend.DBObjects.elements;

import claus.backend.DBObjects.DBObject;

import java.util.ArrayList;

public class Category implements DBObject
{

    String code;
    String name;
    String description;
    String parent_code;
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

    public String getParentCode() {
        return parent_code;
    }

    public void setParentCode(String parentCode) {
        this.parent_code = parentCode;
    }

    public int getCopID()
    {
        return cop_id;
    }

    public void setCopID(int coDPD)
    {
        cop_id = coDPD;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(code).append(", ");
        sb.append(name).append(", ");

        return sb.toString();
    }

    @Override
    public String getTableName()
    {
        return "categories";
    }

    /*public static class ElementListByCategory {
        private final Category category;

        private final ArrayList<Element> elements;

        public ElementListByCategory(Category category, ArrayList<Element> elements)
        {
            this.category = category;
            this.elements = elements;
        }

        public Category getCategory()
        {
            return category;
        }

        public ArrayList<Element> getElements()
        {
            return elements;
        }

        @Override
        public String toString()
        {
            var builder = new StringBuilder();
            builder.append(category.code).append(" - ").append(category.name).append("\n");
            for (var elem : elements) {
                builder.append("    ");
                builder.append(elem.name);
                builder.append("\n");
            }
            return builder.toString();
        }
    }*/
}
