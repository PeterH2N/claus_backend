package claus.backend.domain.teams;

import claus.backend.domain.elements.CategoryDAO;
import claus.backend.DBObjects.teams.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserDAO {
    public static int addUser(Connection con, User user) throws SQLException {
        String sql = "INSERT INTO users(id, name, email, phone_number, birthdate, created_on) " +
                "VALUES(gen_random_uuid(), ?, ?, ?, ?, NOW())";

        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getEmail());
        pstmt.setString(3, user.getPhoneNumber());
        pstmt.setDate(4, user.getBirthDate());

        return pstmt.executeUpdate();
    }

    public static User getUser(Connection con, UUID userID) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        var pstmt = con.prepareStatement(sql);

        pstmt.setObject(1, userID);

        var rs = pstmt.executeQuery();
        rs.next();
        return getUser(rs);
    }

    static User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getObject("id", UUID.class));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setBirthDate(rs.getDate("birthdate"));
        user.setCreatedOn(rs.getTimestamp("created_on"));

        return user;
    }

    public static List<User> readDummyData() {
        ArrayList<User> users = new ArrayList<>();
        User user;

        String line;
        String del = ";";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // parsing csv with buffered reader
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CategoryDAO.class.getClassLoader().getResourceAsStream("dummy_data/users.csv"))));

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
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return users;
    }


}
