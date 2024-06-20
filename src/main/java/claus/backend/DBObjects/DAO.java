package claus.backend.DBObjects;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

public class DAO
{
    public static <T extends DBObject> void add(Connection con, T obj)
    {
        String table = obj.getTableName();

        try {
            Class c = Class.forName(obj.getClass().getName());
            Field[] fields = c.getDeclaredFields();

            // make sql string;
            StringBuilder sqlB = new StringBuilder();
            sqlB.append("INSERT INTO ").append(table).append("(");
            for (int i = 0; i < fields.length; i++) {
                sqlB.append(fields[i].getName());
                if (i < fields.length - 1)
                    sqlB.append(", ");

            }

            sqlB.append(") VALUES(");
            for (int i = 0; i < fields.length; i++) {
                sqlB.append("?");
                if (i < fields.length - 1)
                    sqlB.append(", ");
            }
            sqlB.append(");");

            var pstmt = con.prepareStatement(sqlB.toString());

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                pstmt.setObject(i + 1, fields[i].get(obj));
            }
            pstmt.executeUpdate();


        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }

    /*public static<T extends DBObject> T get() {

    }*/
}
