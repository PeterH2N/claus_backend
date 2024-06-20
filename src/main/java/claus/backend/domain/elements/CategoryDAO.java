package claus.backend.domain.elements;

import claus.backend.DBObjects.elements.Category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryDAO{


    public static int add(Connection con, Category cat) throws SQLException {
        String sql = "INSERT INTO categories(code, name, description, parent_code, cop_id) " +
                "VALUES(?,?,?,?,?)";
        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, cat.getCode());
        pstmt.setString(2, cat.getName());
        pstmt.setString(3, cat.getDescription());
        pstmt.setString(4, cat.getParentCode());
        pstmt.setInt(5, cat.getCopID());

        return pstmt.executeUpdate();
    }

    public static int update(Connection con, Category cat) throws SQLException {
        String sql = "UPDATE categories " +
                "SET name = ?, description = ?, parent_code = ? " +
                "WHERE code = ?";

        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, cat.getName());
        pstmt.setString(2, cat.getDescription());
        pstmt.setString(3, cat.getParentCode());
        pstmt.setString(4, cat.getCode());

        return pstmt.executeUpdate();
    }

    public static int remove(Connection con, String code) throws SQLException {
        String sql = "DELETE FROM categories WHERE code = ?";
        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, code);

        return pstmt.executeUpdate();
    }

    public static List<Category> getAllChildren(Connection con, String copName, String categoryCode) throws SQLException {

        int copID = CodeOfPointsDAO.getID(con, copName);
        String sql = "SELECT * FROM categories WHERE parent_code LIKE ? AND cop_id = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setString(1, categoryCode + "%");
        pstmt.setInt(2, copID);

        ArrayList<Category> cats = new ArrayList<>();
        Category cat;

        var rs = pstmt.executeQuery();

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

    public static List<Category> getChildren(Connection con, String copName, String categoryCode) throws SQLException {

        int copID = CodeOfPointsDAO.getID(con, copName);
        String sql = "SELECT * FROM categories WHERE parent_code = ? AND cop_id = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setString(1, categoryCode);
        pstmt.setInt(2, copID);

        ArrayList<Category> cats = new ArrayList<>();
        Category cat;

        var rs = pstmt.executeQuery();

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

    public static List<Category> getAll(Connection con, String copName) throws SQLException {
        int copID = CodeOfPointsDAO.getID(con, copName);
        String sql = "SELECT * FROM categories WHERE cop_id = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, copID);
        var rs = pstmt.executeQuery();

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

    public static List<Category> readFromCSV(String filepath) {
        ArrayList<Category> categories = new ArrayList<>();
        Category category;

        String line;
        String del = ";";
        // parsing csv with buffered reader
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CategoryDAO.class.getClassLoader().getResourceAsStream(filepath + "/categories.csv"))));

            br.readLine(); // first line is header
            while ((line = br.readLine()) != null) {
                String[] cs = line.split(del, -1);
                category = new Category();

                category.setCode(cs[0]);
                category.setName(cs[1]);
                category.setDescription(cs[2]);
                category.setParentCode(cs[3]);

                categories.add(category);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    /*public static void uploadFromCSV(Connection con, String filepath) throws SQLException {
        int addedCount = 0;
        var cats = readFromCSV(filepath);
        for (var cat : cats) {
            // if category is already in database we update it
            String sql = "SELECT name FROM categories WHERE code = ?";
            var pstmt = con.prepareStatement(sql);
            pstmt.setString(1, cat.getCode());
            if (pstmt.execute()) {
                update(con, cat);
            }
            else {
                add(con, cat);
                addedCount++;
            }
        }
        System.out.println("Added " + addedCount + " categories");
    }*/
}
