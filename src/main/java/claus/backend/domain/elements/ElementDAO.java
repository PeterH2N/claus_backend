package claus.backend.domain.elements;

import claus.backend.elements.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElementDAO {

    public static int add(Connection con, Element element) throws SQLException {
        String sql = "INSERT INTO elements(code, name, description, difficulty, category_code, cop_id) " +
                "VALUES(?,?,?,?,?,?)";

        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, element.getCode());
        pstmt.setString(2, element.getName());
        pstmt.setString(3, element.getDescription());
        pstmt.setDouble(4, element.getDifficulty());
        pstmt.setString(5, element.getCategoryCode());
        pstmt.setInt(6, element.getCopID());

        return pstmt.executeUpdate();

    }

    public static int update(Connection con, Element element) throws SQLException {
        String sql = "UPDATE elements " +
                "SET name = ?, description = ?, difficulty = ?, category_code = ? " +
                "WHERE code = ? AND WHERE cop_id = ?";
        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, element.getName());
        pstmt.setString(2, element.getDescription());
        pstmt.setDouble(3, element.getDifficulty());
        pstmt.setString(4, element.getCategoryCode());
        pstmt.setString(6, element.getCode());
        pstmt.setInt(5, element.getCopID());

        return pstmt.executeUpdate();
    }

    public static int remove(Connection con, String code, int cop_id) throws SQLException {
        String sql = "DELETE FROM elements WHERE code = ? AND cop_id = ?";

        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, code);
        pstmt.setInt(2, cop_id);

        return pstmt.executeUpdate();
    }

    public static List<Element> getAllByCoP(Connection con, int CoPID) throws SQLException {
        String sql = "SELECT * FROM elements WHERE cop_id = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, CoPID);
        var rs = pstmt.executeQuery();
        ArrayList<Element> elems = new ArrayList<>();
        Element elem;

        while (rs.next()) {
            elem = new Element();
            elem.setCode(rs.getString("code"));
            elem.setName(rs.getString("name"));
            elem.setDescription(rs.getString("description"));
            elem.setDifficulty(rs.getDouble("difficulty"));
            elem.setCategoryCode(rs.getString("category_code"));
            elem.setCopID(rs.getInt("cop_id"));
            elems.add(elem);
        }

        return elems;
    }

    static List<Element> get(ResultSet rs) throws SQLException
    {
        ArrayList<Element> elems = new ArrayList<>();
        Element elem;

        while (rs.next()) {
            elem = new Element();
            elem.setCode(rs.getString("code"));
            elem.setName(rs.getString("name"));
            elem.setDescription(rs.getString("description"));
            elem.setDifficulty(rs.getDouble("difficulty"));
            elem.setCategoryCode(rs.getString("category_code"));
            elem.setCopID(rs.getInt("cop_id"));
            elems.add(elem);
        }

        return elems;
    }

    public static List<Element> getByCategory(Connection con, String copName, String categoryCode) throws SQLException {
        int CoPID = CodeOfPointsDAO.getID(con, copName);

        String sql = "SELECT * FROM elements WHERE cop_id = ? AND category_code LIKE ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, CoPID);
        pstmt.setString(2, categoryCode + "%");
        var rs = pstmt.executeQuery();

        return get(rs);
    }

    public static List<Element> getBySpecificCategory(Connection con, String copName, String categoryCode) throws SQLException {
        int CoPID = CodeOfPointsDAO.getID(con, copName);

        String sql = "SELECT * FROM elements WHERE cop_id = ? AND category_code = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, CoPID);
        pstmt.setString(2, categoryCode);
        var rs = pstmt.executeQuery();

        return get(rs);
    }

    public static List<Element> getAllByCoP(Connection con, String copName) throws SQLException {

        int CoPID = CodeOfPointsDAO.getID(con, copName);

        String sql = "SELECT * FROM elements WHERE cop_id = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, CoPID);
        var rs = pstmt.executeQuery();

        return get(rs);
    }
    public static List<Element> getAll(Connection con) throws SQLException {
        String sql = "SELECT * FROM elements";
        var stmt = con.createStatement();
        var rs = stmt.executeQuery(sql);

        return get(rs);
    }

    public static Element get(Connection con, String copName, String code) throws SQLException
    {
        int CoPID = CodeOfPointsDAO.getID(con, copName);

        String sql = "SELECT * FROM elements WHERE code = ? AND cop_id = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setString(1, code);
        pstmt.setInt(2, CoPID);
        var rs = pstmt.executeQuery();
        rs.next();

        Element elem = new Element();

        elem.setCode(code);
        elem.setName(rs.getString("name"));
        elem.setDescription(rs.getString("description"));
        elem.setDifficulty(rs.getDouble("difficulty"));
        elem.setCategoryCode(rs.getString("category_code"));
        elem.setCopID(rs.getInt("cop_id"));

        return elem;

    }

    public static List<Element> readFromCSV(String filepath) {
        ArrayList<Element> elements = new ArrayList<>();
        Element element;

        String line;
        String del = ";";
        // parsing csv with buffered reader
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CategoryDAO.class.getClassLoader().getResourceAsStream(filepath +  "/elements.csv"))));

            br.readLine(); // first line is header
            while ((line = br.readLine()) != null) {
                String[] es = line.split(del, -1);
                element = new Element();
                element.setCode(es[0]);
                element.setName(es[1]);
                element.setDescription(es[2]);
                element.setDifficulty(Double.parseDouble(es[3]));
                element.setCategoryCode(es[4]);

                elements.add(element);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return elements;
    }
}
