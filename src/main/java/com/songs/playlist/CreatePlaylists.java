package com.songs.playlist;

import com.songs.SearchSongs;
import com.songs.Songs;
import com.user.User;
import utils.db.JukeboxDB;
import utils.exceptions.WrongOptionException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CreatePlaylists extends ViewPlaylist {

    public CreatePlaylists(){
        super();
    }


    public void createNewPlaylist() throws WrongOptionException {
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Playlist Name (Please Enter Single Word)");
            String playlistName = sc.next().trim();
            String q = "select * from playlists where playlistName = '" + playlistName +"'";
            ResultSet rs = db.selectFromTable(q);
            if(rs.next()) {
                System.out.println("Playlist Name Already Exists! Please Use different name");
                createNewPlaylist();
            } else {
                rs = db.selectFromTable("select userId from users where phone = '"+ User.getPhone()+"'");
                int userId;
                if(rs.next()){
                    userId = rs.getInt(1);
                }else{
                    throw new RuntimeException();
                }
                String query = "Insert into playlists(playlistName, userId) value('"+playlistName+"',"+userId+")";
                int row = db.insertIntoTable(query);
                if(row>0){
                    System.out.println("New Playlist "+ playlistName +" Created Successfully!");
                    updatePlaylistSongs(playlistName);
                }
                else
                    System.out.println("Failed to create new playlist");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
//            throw new RuntimeException();
        }
    }


    public void updatePlaylistSongs(String playlist) throws WrongOptionException {
        Scanner sc = new Scanner(System.in);
        try{

            String query = "select playlistId from playlists where playlistName = '" + playlist +"'";
            ResultSet rs = db.selectFromTable(query);
            if(rs.next()) {
                int playlistId = rs.getInt(1);

                List<Songs> currentPlaylistList = getPlaylist(playlistId);
                Songs.displaySongs(currentPlaylistList);

                System.out.println("Enter 1 to ADD songs to the playlist 2 to DELETE songs");

                int toAdd = sc.nextInt();
                if(toAdd == 1)
                    addSongsToPlaylist(playlistId,playlist);
                else if(toAdd == 2)
                    deleteSongsFromPlaylist(playlistId, playlist);
                else
                    throw new WrongOptionException("Please Choose From The Given Option Only!");

            } else {
                System.out.println("No Playlist Found By the Name! ");
                System.out.println("Do you want to create New Playlist? If Yes Press 1 or else press Anything");
                if (sc.nextInt() == 1)
                    createNewPlaylist();

            }

        }catch(IndexOutOfBoundsException e){
           throw new WrongOptionException("Please Choose From The Given Option Only!");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void deletePlaylist() throws WrongOptionException {
        try{

            Scanner sc = new Scanner(System.in);

            Map<String,Integer> playlists = getUserPlaylistNames();
            List<String> playlistName = new ArrayList<>();

            int counter = 0;
            for(Map.Entry<String,Integer> data : playlists.entrySet()) {
                playlistName.add(data.getKey());
                System.out.printf("%-4d %-10s\n", ++counter, data.getKey());
            }

            if(playlistName.size() == 0){
                System.out.println("You Have no Playlist! Please Create New One!");
                return;
            }

            System.out.println("Enter the Playlist No to DELETE The Complete Playlist");

            int option = sc.nextInt();
            int playlistId = playlists.get(playlistName.get(option-1));

            String query = "DELETE FROM playlistSongs Where playlistId = "+playlistId;
            int rows = db.deleteFromTable(query);
            ResultSet rs = db.selectFromTable("select * from playlistSongs where playlistId = "+playlistId);
            if(!(rs.next())){
                query = "DELETE FROM playlists where playlistId = "+playlistId;
                rows = db.deleteFromTable(query);
                if(rows > 0){
                    System.out.println("Playlist Deleted Successfully");
                }else {
                    System.out.println("Could not able to Delete the playlist! Please Try Again");
                }
            }else{
                System.out.println("Could not able to Delete the playlist! Please Try Again");
            }

        }catch (IndexOutOfBoundsException e){
            throw new WrongOptionException("Please select the songs from given option only");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void addSongsToPlaylist(int playlistId, String playlist){
        Scanner sc = new Scanner(System.in);
        try{
            SearchSongs search = new SearchSongs();
            List<Songs> currentSongPlaylist = search.getAllSongs();
            Songs.displaySongs(currentSongPlaylist);

            System.out.println("Enter all the songs no to add to playlist separated by commas.(For example : 1,3,4)");
            String selection = sc.next();
            String[] songList = selection.split(",");
            int totalSongsAdded = 0;

            for (String s : songList) {
                int selectedSong = Integer.parseInt(s);
                int songId = currentSongPlaylist.get(selectedSong - 1).getSongId();
                ResultSet isAlreadyAdded = db.selectFromTable("select * from playlistSongs where playlistId = '"+playlistId +"' and songId = '"+songId+"'" );
                if(isAlreadyAdded.next()){
                    System.out.println("Selected Song No " +selectedSong + " is already in your playlist");
                    continue;
                }
                String query = "Insert into playlistSongs(playlistId,songId) value(" + playlistId + "," + songId + ")";
                int row = db.insertIntoTable(query);
                totalSongsAdded += row;
            }

            System.out.println(totalSongsAdded + " Songs are added to playlist " + playlist);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void deleteSongsFromPlaylist(int playlistId, String playlist){
        Scanner sc = new Scanner(System.in);
        try{
            List<Songs> currentSongPlaylist = getPlaylist(playlistId);
            Songs.displaySongs(currentSongPlaylist);

            System.out.println("Enter all the songs no to DELETE FROM playlist separated by commas.(For example : 1,3,4)");
            String selection = sc.next();
            String[] songList = selection.split(",");
            int songsDeleted = 0;

            for (String s : songList) {
                int selectedSong = Integer.parseInt(s);
                int songId = currentSongPlaylist.get(selectedSong - 1).getSongId();
                ResultSet isAlreadyAdded = db.selectFromTable("select * from playlistSongs where playlistId = '"+playlistId +"' and songId = '"+songId+"'" );
                if(!(isAlreadyAdded.next())){
                    System.out.println("Selected Song No " +selectedSong + " is not in your playlist");
                    continue;
                }
                String query = "DELETE FROM playlistSongs where playlistId = "+playlistId +" and songId = "+songId;
                int row = db.deleteFromTable(query);
                songsDeleted += row;
            }

            System.out.println(songsDeleted + " Songs are DELETED FROM playlist " + playlist);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
