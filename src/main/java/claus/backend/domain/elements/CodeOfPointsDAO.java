package claus.backend.domain.elements;

import claus.backend.DBObjects.elements.Category;
import claus.backend.DBObjects.elements.CodeOfPoints;
import claus.backend.DBObjects.elements.Element;
import claus.backend.DBObjects.elements.ElementRequirement;

import java.sql.Connection;
import java.sql.SQLException;

public class CodeOfPointsDAO
{
    /**
     * Adds a new CoP into the database by its name
     * @param con connection to the database
     * @param name name of the CoP
     * @return number of rows affected
     */
    static int add(Connection con, String name) throws SQLException
    {
        String sql = "INSERT INTO code_of_points(name) " +
                "VALUES(?)";
        var pstmt = con.prepareStatement(sql);
        pstmt.setString(1, name);

        return pstmt.executeUpdate();
    }

    public static int getID(Connection con, String name) throws SQLException
    {
        String sql = "SELECT id FROM code_of_points WHERE name = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setString(1, name);

        var rs = pstmt.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    public static void add(Connection con, CodeOfPoints cop) throws SQLException
    {
        // if not already in db
        String sql = "SELECT id FROM code_of_points WHERE name = ?";

        var pstmt = con.prepareStatement(sql);
        pstmt.setString(1, cop.getName());

        var rs = pstmt.executeQuery();
        if (!rs.next())
            // add entry in cop table
            add(con, cop.getName());

        // get id
        sql = "SELECT id FROM code_of_points WHERE name = ?";
        pstmt = con.prepareStatement(sql);

        pstmt.setString(1, cop.getName());
        rs = pstmt.executeQuery();
        rs.next();
        cop.setId(rs.getInt("id"));

        // add categories
        for (Category cat : cop.getCategories()) {
            cat.setCopID(cop.getId());
            CategoryDAO.add(con, cat);
        }

        // add elements
        for (Element elem : cop.getElements()) {
            elem.setCopID(cop.getId());
            ElementDAO.add(con, elem);
        }

        // add requirements
        for (ElementRequirement elemreq : cop.getElementRequirements()) {
            elemreq.setCopID(cop.getId());
            ElementRequirementDao.add(con, elemreq);
        }

    }
}
