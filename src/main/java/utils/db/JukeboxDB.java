package utils.db;

import java.sql.*;

public class JukeboxDB {
    Connection connection;

    public JukeboxDB(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","root@123");
            if(this.connection == null)
                System.out.println("Connection To The Database Failed!");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public ResultSet selectFromTable(String query){
        ResultSet rs;
        try{
            Statement st = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(query);
        }catch(SQLException e){
            System.out.println(e);
            return null;
        }
        return rs;
    }

    public int insertIntoTable(String query){
        int rows=0;
        try{
            Statement st = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rows = st.executeUpdate(query);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
        return rows;
    }

    public int deleteFromTable(String query){
        int rows=0;
        try{
            Statement st = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rows = st.executeUpdate(query);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
        return rows;
    }



    public int updateTable(String query){
        int rows=0;
        try{
            Statement st = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rows = st.executeUpdate(query);
        }catch(SQLException e){
            System.out.println(e);
            throw new RuntimeException();
        }
        return rows;
    }


}
