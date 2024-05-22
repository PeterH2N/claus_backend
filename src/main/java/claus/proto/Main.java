package claus.proto;

import claus.proto.database.DB;
import claus.proto.domain.elements.CategoryDAO;
import claus.proto.domain.elements.ElementDAO;
import claus.proto.domain.teams.UserDAO;
import claus.proto.teams.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {
        try {
            DB.beginTransaction();

            DB.endTransaction();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}