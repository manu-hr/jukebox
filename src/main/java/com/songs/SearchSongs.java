package com.songs;

import utils.db.JukeboxDB;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchSongs {
    private final JukeboxDB db;

    public SearchSongs(){
        this.db = new JukeboxDB();
    }

    public List<Songs> getAllSongs() {

        List<Songs> currentSongPlayList = new ArrayList<>();
        try{

            String query = """
                   Select s.songId, s.songName, a.artistName, ab.albumName, g.genreName 
                    from songs s, artists a, albums ab, genres g 
                    where a.artistId = s.artistId and 
                    ab.albumId = s.albumId and 
                    g.genreId = s.genreId 
                    order by s.songName;
            """;
            ResultSet rs = db.selectFromTable(query.trim());

            while (rs.next()) {
                currentSongPlayList.add(new Songs(rs.getInt(1), rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5)));
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return currentSongPlayList;

    }

    public void search(String type, String name, int byFirstLetter){
        String filter = switch (type) {
            case "song" -> (byFirstLetter == 1) ? " AND s.songName like '" + name + "%'" : " AND s.songName like '%" + name + "%'";
            case "artist" -> (byFirstLetter == 1) ? " AND a.artistName like '" + name + "%'" : " AND a.artistName like '%" + name + "%'";
            case "album" -> (byFirstLetter == 1) ? " AND ab.albumName like '" + name + "%'" : " AND ab.albumName like '%" + name + "%'";
            case "genre" -> (byFirstLetter == 1) ? " AND g.genreName like '" + name + "%'" : " AND g.genreName like '%" + name + "%'";
            default -> "";
        };

        try{
            String query = """
                    SELECT
                        s.SongName, a.artistName, ab.albumName, g.genreName
                    FROM
                        songs s,
                        artists a,
                        albums ab,
                        genres g
                    WHERE
                        a.artistId = s.artistId
                            AND ab.albumId = s.albumId
                            AND g.genreId = s.genreId
                            """+ filter + """
                    ORDER BY songName;
                    """;

            ResultSet rs = db.selectFromTable(query.trim());
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.printf("%-20s",rsmd.getColumnName(i));
            }
            System.out.println();
            System.out.println("______________________________________________________________________________________________________________________");
            while (rs.next()) {
                //for Printing in table form
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = rs.getString(i);
                    System.out.printf("%-20s",columnValue );
                }
                System.out.println("");
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void viewAllSongs(List<Songs> songsList){
        int counter = 0;
        System.out.printf("%-20s\t\t%-20s\t\t%-20s\t\t%-20s\t\t%-20s\n","No.","Song Name","Artist","Album","Genre");
        System.out.println("____________________________________________________________________________________________________________________________");
        for(Songs song : songsList) {
            System.out.printf("%-20d\t\t%-20s\t\t%-20s\t\t%-20s\t\t%-20s\n",++counter,song.getSongName(),song.getArtistName(),song.getAlbumName(),song.getGenreName());
        }
        System.out.println();

    }





}
