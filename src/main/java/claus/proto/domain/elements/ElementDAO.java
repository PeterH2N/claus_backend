package claus.proto.domain.elements;

import claus.proto.elements.Category;
import claus.proto.elements.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElementDAO
{

    public static int addElement(Connection con, Element element) throws SQLException
    {
        String sql = "INSERT INTO elements(code, name, description, difficulty, category_code) " +
                "VALUES(?,?,?,?,?)";

        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, element.getCode());
        pstmt.setString(2, element.getName());
        pstmt.setString(3, element.getDescription());
        pstmt.setDouble(4, element.getDifficulty());
        pstmt.setString(5, element.getCategoryCode());

        return pstmt.executeUpdate();

    }

    public static List<Element> getElements(Connection con) throws SQLException
    {
        String sql = "SELECT * FROM elements";
        var stmt = con.createStatement();
        var rs = stmt.executeQuery(sql);
        ArrayList<Element> elems = new ArrayList<>();
        Element elem;

        while (rs.next()) {
            elem = new Element();
            elem.setCode(rs.getString("code"));
            elem.setName(rs.getString("name"));
            elem.setDescription(rs.getString("description"));
            elem.setDifficulty(rs.getDouble("difficulty"));
            elem.setCategoryCode(rs.getString("category_code"));
            elems.add(elem);
        }

        return elems;
    }


    static List<Element> readFromCSV(String filepath) {
        ArrayList<Element> elements = new ArrayList<>();
        Element element;

        String line;
        String del = ";";
        // parsing csv with buffered reader
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CategoryDAO.class.getClassLoader().getResourceAsStream(filepath))) );

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
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return elements;
    }

    public static void addFromCSV(Connection con) throws SQLException
    {
        var elems = readFromCSV("elements/elements.csv");
        for (var elem : elems) {
            addElement(con, elem);
        }
    }
}
