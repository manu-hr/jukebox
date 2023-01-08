package com.songs;

import java.util.List;

public class Songs {

    private int songId;
    private String songName;
    private String albumName;
    private String artistName;
    private String genreName;

    public Songs(int songId, String songName, String albumName, String artistName, String genreName) {
        this.songId = songId;
        this.songName = songName;
        this.albumName = albumName;
        this.artistName = artistName;
        this.genreName = genreName;
    }

    public int getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getGenreName() {
        return genreName;
    }

    public static void displaySongs(List<Songs> songsList){
        int counter = 0;
        System.out.printf("%-20s\t\t%-20s\t\t%-20s\t\t%-20s\t\t%-20s\n","No.","Song Name","Artist","Album","Genre");
        System.out.println("____________________________________________________________________________________________________________________________");
        for(Songs song : songsList) {
            System.out.printf("%-20d\t\t%-20s\t\t%-20s\t\t%-20s\t\t%-20s\n",++counter,song.getSongName(),song.getArtistName(),song.getAlbumName(),song.getGenreName());
        }
    }

    @Override
    public String toString() {
        return "Songs{" +
                "songName='" + songName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
