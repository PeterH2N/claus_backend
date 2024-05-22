package claus.proto.elements;

public class Category
{

    String code;
    String name;
    String description;
    String parentCode;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getParentCode()
    {
        return parentCode;
    }

    public void setParentCode(String parentCode)
    {
        this.parentCode = parentCode;
    }

    @Override
    public String toString()
    {
        StringBuilder sb  = new StringBuilder();
        sb.append(code + ", ");
        sb.append(name + ", ");

        return sb.toString();
    }
}
