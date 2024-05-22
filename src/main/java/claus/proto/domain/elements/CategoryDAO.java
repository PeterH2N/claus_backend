package claus.proto.domain.elements;

import claus.proto.database.DB;
import claus.proto.elements.Category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryDAO
{

    public static int addCategory(Connection con, Category cat) throws SQLException {
        String sql = "INSERT INTO categories(code, name, description, parent_code) " +
                "VALUES(?,?,?,?)";
        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, cat.getCode());
        pstmt.setString(2, cat.getName());
        pstmt.setString(3, cat.getDescription());
        pstmt.setString(4, cat.getParentCode());

        return pstmt.executeUpdate();
    }

    public static List<Category> getCategories(Connection con) throws SQLException
    {
        String sql = "SELECT * FROM categories";
        var stmt = con.createStatement();
        var rs = stmt.executeQuery(sql);

        ArrayList<Category> cats = new ArrayList<>();
        Category cat;

        while (rs.next()) {
            cat = new Category();
            cat.setCode(rs.getString("code"));
            cat.setName(rs.getString("name"));
            cat.setDescription(rs.getString("description"));
            cat.setParentCode(rs.getString("parent_code"));
            cats.add(cat);
        }

        return cats;

    }
    static List<Category> readFromCSV(String filepath) {
        ArrayList<Category> categories = new ArrayList<>();
        Category category;

        String line;
        String del = ";";
        // parsing csv with buffered reader
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CategoryDAO.class.getClassLoader().getResourceAsStream(filepath))) );

            br.readLine(); // first line is header
            while ((line = br.readLine()) != null) {
                String[] cs = line.split(del, -1);
                category = new Category();

                category.setCode(cs[0]);
                category.setName(cs[1]);
                category.setDescription(cs[2]);
                if (!cs[3].isEmpty())
                    category.setParentCode(cs[3]);

                categories.add(category);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return categories;
    }

    public static void addFromCSV(Connection con) throws SQLException
    {
        var cats = readFromCSV("elements/categories.csv");
        for (var cat : cats) {
            addCategory(con, cat);
        }
    }
}
