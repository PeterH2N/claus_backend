package claus.proto.domain.teams;

import claus.proto.domain.elements.CategoryDAO;
import claus.proto.elements.Category;
import claus.proto.teams.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO
{
    public static int addUser(Connection con, User user) throws SQLException
    {
        String sql = "INSERT INTO users(id, name, email, phone_number, birthdate, created_on) " +
                "VALUES(gen_random_uuid(), ?, ?, ?, ?, NOW())";

        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getEmail());
        pstmt.setString(3, user.getPhoneNumber());
        pstmt.setDate(4, user.getBirthDate());

        return pstmt.executeUpdate();
    }

    public static List<User> readDummyData() {
        ArrayList<User> users = new ArrayList<>();
        User user;

        String line;
        String del = ";";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // parsing csv with buffered reader
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CategoryDAO.class.getClassLoader().getResourceAsStream("dummy_data/users.csv"))) );

            br.readLine(); // first line is header
            while ((line = br.readLine()) != null) {
                String[] us = line.split(del, -1);
                user = new User();

                user.setName(us[0]);
                user.setEmail(us[1]);
                user.setPhoneNumber(us[2]);
                user.setBirthDate(new java.sql.Date(sdf.parse(us[3]).getTime()));

                users.add(user);
            }
        }
        catch (IOException | ParseException e)
        {
            throw new RuntimeException(e);
        }

        return users;
    }
}
