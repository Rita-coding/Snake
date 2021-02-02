package snake;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private final String tableName = "highscore";
    private final Connection conn;
    private final ArrayList<HighScore> highScores;
    
    public Database(){
        Connection c = null;
        try {
            c = ConnectionFactory.getConnection();
        } catch (Exception ex) { System.out.println("No connection");}
        this.conn = c;
        highScores = new ArrayList<>();
        //loadHighScores();
    }
    
    public ArrayList<HighScore> getHighScores(){
        loadHighScores();
        if(highScores.size()>10){
            ArrayList<HighScore> topTen = new ArrayList<>();     
            for(int i =0; i<10; i++){
                topTen.add(highScores.get(i));
            }
        return topTen;
        }
        return this.highScores;
    }

    public void loadHighScores(){
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName +
                   " ORDER BY Score DESC ");
            while (rs.next()){
                String name = rs.getString("PlayerName");
                int score = rs.getInt("Score");
                highScores.add(new HighScore(name,score));
                
            }
        } catch (Exception e){ System.out.println("loadHighScores error");}
    }
    
    
    
    public int storeToDatabase(String name, int score){
        try (Statement stmt = conn.createStatement()){
            String s = "INSERT INTO " + tableName + 
                    " (PlayerName, Score) " + 
                    "VALUES('" + name + "',"  + score + 
                    ") ON DUPLICATE KEY UPDATE Score=" + score;
            
            return stmt.executeUpdate(s);
        } catch (Exception e){
            System.out.println("storeToDatabase error");
        }
        return 0;
    }
}
