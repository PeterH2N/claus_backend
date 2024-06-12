package claus.backend.domain.elements;

import claus.backend.elements.Category;
import claus.backend.elements.Element;
import claus.backend.elements.ElementRequirement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElementRequirementDao
{
    public static List<ElementRequirement> readFromCSV(String filepath) {
        ArrayList<ElementRequirement> reqs = new ArrayList<>();

        String line;
        String del = ";";
        // parsing csv with buffered reader
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CategoryDAO.class.getClassLoader().getResourceAsStream(filepath + "/requirements.csv"))));

            br.readLine(); // first line is header
            while ((line = br.readLine()) != null) {
                String[] ers = line.split(del, -1);

                String categoryCode = ers[0];
                int amount = Integer.parseInt(ers[1]);

                reqs.add(new ElementRequirement(categoryCode, amount));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reqs;
    }

    static int add(Connection con, ElementRequirement elementRequirement) throws SQLException
    {
        String sql = "INSERT INTO element_requirements(cop_id, category_code, amount) " +
                "VALUES(?, ?, ?)";
        var pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, elementRequirement.getCopID());
        pstmt.setString(2, elementRequirement.getCategoryCode());
        pstmt.setInt(3, elementRequirement.getAmount());

        return pstmt.executeUpdate();
    }

    static ElementRequirement get(ResultSet rs) throws SQLException
    {
        ElementRequirement elementRequirement = new ElementRequirement();
        elementRequirement.setCategoryCode(rs.getString("category_code"));
        elementRequirement.setAmount(rs.getInt("amount"));
        elementRequirement.setCopID(rs.getInt("cop_id"));

        return elementRequirement;
    }

    public static List<ElementRequirement> getBycopID(Connection con, int copID) throws SQLException
    {
        ArrayList<ElementRequirement> reqs = new ArrayList<>();

        String sql = "SELECT * FROM element_requirements WHERE cop_id = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, copID);
        var rs = pstmt.executeQuery();

        while (rs.next()) {
            reqs.add(get(rs));
        }
        return reqs;
    }
}
