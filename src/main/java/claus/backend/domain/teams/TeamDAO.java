package claus.backend.domain.teams;

import claus.backend.domain.elements.CategoryDAO;
import claus.backend.DBObjects.teams.Team;
import claus.backend.DBObjects.teams.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TeamDAO {
    public static int addTeam(Connection con, Team team) throws SQLException {
        String sql = "INSERT INTO teams(id, name) " +
                "VALUES(gen_random_uuid(), ?)";

        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, team.getName());

        return pstmt.executeUpdate();
    }

    public static Team getTeam(Connection con, UUID teamID) throws SQLException
    {
        String sql = "SELECT * FROM teams WHERE id = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setObject(1, teamID);

        return getTeam(pstmt.executeQuery());
    }

    public static Team getTeam(ResultSet rs) throws SQLException
    {
        Team team = new Team();
        team.setId(rs.getObject("id", UUID.class));
        team.setName(rs.getString("name"));
        return team;
    }

    public static int addGymnastToTeam(Connection con, UUID teamID, UUID gymnastID) throws SQLException {
        String sql = "INSERT INTO team_gymnast_relations(team_id, user_id) " +
                "VALUES(?,?)";

        var pstmt = con.prepareStatement(sql);

        pstmt.setObject(1, teamID);
        pstmt.setObject(2, gymnastID);

        return pstmt.executeUpdate();
    }

    public static int addGymnastToTeam(Connection con, UUID teamID, String gymnastName) throws SQLException {
        String sql = "SELECT id FROM users WHERE NAME=?";

        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, gymnastName);

        var rs = pstmt.executeQuery();
        rs.next();
        UUID gymnastID = rs.getObject("id", UUID.class);

        return addGymnastToTeam(con, teamID, gymnastID);
    }

    public static int addCoachToTeam(Connection con, UUID teamID, UUID coachID) throws SQLException {
        String sql = "INSERT INTO team_coach_relations(team_id, user_id) " +
                "VALUES(?,?)";

        var pstmt = con.prepareStatement(sql);

        pstmt.setObject(1, teamID);
        pstmt.setObject(2, coachID);

        return pstmt.executeUpdate();

    }

    public static int addCoachToTeam(Connection con, UUID teamID, String coachName) throws SQLException {
        String sql = "SELECT id FROM users WHERE name=?";

        var pstmt = con.prepareStatement(sql);

        pstmt.setString(1, coachName);

        var rs = pstmt.executeQuery();
        rs.next();
        UUID coachID = rs.getObject("id", UUID.class);

        return addCoachToTeam(con, teamID, coachID);
    }

    public static List<User> getGymnasts(Connection con, UUID teamID) throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT user_id FROM team_gymnast_relations " +
                "WHERE team_id = ?";

        var pstmt = con.prepareStatement(sql);

        pstmt.setObject(1, teamID);

        var rs = pstmt.executeQuery();
        while(rs.next()) {
            UUID userID = rs.getObject("user_id", UUID.class);
            users.add(UserDAO.getUser(con, userID));
        }

        return users;
    }

    public static List<User> getCoaches(Connection con, UUID teamID) throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT user_id FROM team_coach_relations " +
                "WHERE team_id = ?";

        var pstmt = con.prepareStatement(sql);

        pstmt.setObject(1, teamID);

        var rs = pstmt.executeQuery();
        while(rs.next()) {
            UUID userID = rs.getObject("user_id", UUID.class);
            users.add(UserDAO.getUser(con, userID));
        }

        return users;
    }

    public static List<Team> getTeamsFromCoach(Connection con, UUID coachID) throws SQLException
    {
        String sql = "SELECT team_id FROM team_coach_relations WHERE user_id = ?";
        var pstmt = con.prepareStatement(sql);
        pstmt.setObject(1, coachID);
        var rs = pstmt.executeQuery();
        ArrayList<Team> teams = new ArrayList<>();
        while (rs.next()) {
            teams.add(getTeam(con, rs.getObject("team_id", UUID.class)));
        }

        return teams;

    }

    public static void addDummyData(Connection con) {
        String line;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(CategoryDAO.class.getClassLoader().getResourceAsStream("dummy_data/teams.csv"))));

            br.readLine();
            while ((line = br.readLine()) != null) {
                // whole team string
                String[] ts = line.split(";", -1);
                String teamName = ts[0];

                // add team to database
                Team team = new Team();
                team.setName(teamName);
                addTeam(con, team);

                // get team UUID
                String sql = "SELECT id FROM teams WHERE name=?";
                var pstmt = con.prepareStatement(sql);

                pstmt.setString(1, teamName);
                var rs = pstmt.executeQuery();
                rs.next();
                UUID teamID = rs.getObject("id", UUID.class);

                // gymnasts string
                String[] gs = ts[1].split(",", -1);
                // coaches string
                String[] cs = ts[2].split(",", -1);

                // upload gymnasts to database
                for (String gymnast : gs) {
                    addGymnastToTeam(con, teamID, gymnast);
                }
                // upload coached to database
                for (String coach : cs) {
                    addCoachToTeam(con, teamID, coach);
                }
            }


        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
