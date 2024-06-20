package claus.backend;

import claus.backend.DBObjects.DAO;
import claus.backend.DBObjects.elements.Category;
import claus.backend.database.DB;
import claus.backend.domain.elements.CategoryDAO;
import claus.backend.domain.elements.ElementDAO;

import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        var con  = DB.getConnection();

        var cat = new Category();
        cat.setCode("codetest");
        cat.setName("nametest");
        cat.setDescription("desctest");
        cat.setCopID(55);
        cat.setParentCode("parenttest");

        DAO.add(con, cat);

        DB.freeConnection(con);
        DB.cleanPool();
    }
}
