package claus.backend.elements;

import java.util.ArrayList;

public class Category {

    String code;
    String name;
    String description;
    String parentCode;

    int copID;

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
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public int getCopID()
    {
        return copID;
    }

    public void setCopID(int coDPD)
    {
        copID = coDPD;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(code).append(", ");
        sb.append(name).append(", ");

        return sb.toString();
    }

    public static class ElementListByCategory {
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
    }
}
