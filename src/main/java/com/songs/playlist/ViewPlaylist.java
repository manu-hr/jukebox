package com.songs.playlist;

import com.songs.Songs;
import com.user.User;
import utils.db.JukeboxDB;
import utils.exceptions.WrongOptionException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ViewPlaylist {
    protected final JukeboxDB db;

    public ViewPlaylist() {
        this.db = new JukeboxDB();
    }

    public Map<String,Integer> getUserPlaylistNames(){
        Map<String,Integer> userPlaylistNames = new HashMap<>();
        try{
            ResultSet rs = db.selectFromTable("Select userId from users where phone = '"+ User.getPhone()+"'");
            if(rs.next()){
                int userId = rs.getInt(1);
                rs = db.selectFromTable("select playlistId, playlistName from playlists where userId = "+userId + " Order By playlistName");
                while(rs.next()){
                    userPlaylistNames.put(rs.getString(2), rs.getInt(1));
                }
            }else{
                throw new RuntimeException();
            }

        }catch (SQLException e){
            System.out.println(e);
        }

        return userPlaylistNames;
    }

    public List<Songs> getPlaylist(int playlistId) {
        List<Songs> playlistSongs = new ArrayList<>();
        try{
            String query = """
                    SELECT distinct s.songId, s.songName, ab.albumName, a.artistName , g.genreName \s
                    	from\s
                    		songs s,
                            genres g,
                            albums ab,
                            artists a,
                            playlistSongs p
                    	where\s
                    		s.genreId=g.genreId
                    		and p.playlistId =""" + playlistId + """
                            and p.songId = s.songId
                            and s.artistId = a.artistId
                            and s.albumId = ab.albumId\s
                    """;
            ResultSet rs = db.selectFromTable(query);
            while(rs.next()){
                playlistSongs.add(new Songs(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getString(5)));
            }

        }catch (SQLException e){
            System.out.println(e);
        }
        return playlistSongs;
    }

    public void displayPlaylistNames() throws WrongOptionException {
        try{

            Scanner sc = new Scanner(System.in);

            Map<String,Integer> playlists = getUserPlaylistNames();
            List<String> playlistName = new ArrayList<>();

            int counter = 0;
            for(Map.Entry<String,Integer> data : playlists.entrySet()) {
                playlistName.add(data.getKey());
                System.out.printf("%-8d %-10s\n", ++counter, data.getKey());
            }

            if(playlistName.size() == 0){
                System.out.println("You Have no Playlist! Please Create New One!");
                return;
            }

            System.out.println("Enter the Playlist No to view the respective playlist songs");

            int option = sc.nextInt();
            int playlistId = playlists.get(playlistName.get(option-1));

            List<Songs> currentListToPlay = getPlaylist( playlistId);
            Songs.displaySongs(currentListToPlay);

        }catch (IndexOutOfBoundsException e){
            throw new WrongOptionException("Selected Wrong Option");
        }


    }

}
