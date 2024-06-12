package claus.backend;

import claus.backend.database.DB;
import claus.backend.domain.elements.CategoryDAO;
import claus.backend.domain.elements.CodeOfPointsDAO;
import claus.backend.domain.elements.ElementDAO;
import claus.backend.domain.elements.ElementRequirementDao;
import claus.backend.elements.CodeOfPoints;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

public class InsertCSVData
{
    public static void insertCoP(String path) throws SQLException
    {
        Connection con = DB.getConnection();
        String[] splitPath = path.split("/");
        String name = splitPath[splitPath.length - 1];

        CodeOfPoints cop = new CodeOfPoints();
        cop.setName(name);
        cop.setCategories(new ArrayList<>(CategoryDAO.readFromCSV(path)));
        cop.setElements(new ArrayList<>(ElementDAO.readFromCSV(path)));
        cop.setElementRequirements(new ArrayList<>(ElementRequirementDao.readFromCSV(path)));

        CodeOfPointsDAO.add(con, cop);
    }

    public static void main(String[] args) throws SQLException
    {
        insertCoP("CoPs/Liga 2024");
    }

}
