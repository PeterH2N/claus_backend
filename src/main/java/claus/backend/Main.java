package claus.backend;

import claus.backend.database.DB;
import claus.backend.domain.elements.CategoryDAO;
import claus.backend.domain.elements.ElementDAO;

import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        var con  = DB.getConnection();

        var tree = CategoryDAO.getCategoryTree(con, "Liga 2024");

        System.out.println(tree);

        DB.freeConnection(con);
        DB.cleanPool();
    }
}
