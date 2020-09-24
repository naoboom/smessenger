import java.sql.*;

public class DBCon { //Data Base connection
    Connection connection;

    void connect() throws Exception{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:messenger.db");
        System.out.println("base be connected");
    }

    void writeMessageToDB(String dateTime, String userName, String message) throws SQLException {
        Statement st = connection.createStatement();
        String sql = "INSERT INTO messages (dateTime, userName, message)" +
                "VALUES('"+dateTime+"', '" + userName + "', '" + message + "') ";
        System.out.println(sql);
        st.executeUpdate(sql);
        st.close();
    }

    void readMessageFromDB() throws SQLException{
        Statement st = connection.createStatement();
        String query = "SELECT dateTime, userName, message FROM messages ";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id");
            int nameID = rs.getInt("nameID");
            String dateTime = rs.getString("dateTime");
            String message = rs.getString("message");
            System.out.println(id + "\t" + nameID + "\t" + dateTime + "\t" + message);
        }
        rs.close();
        st.close();
    }

    void close(){
        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
