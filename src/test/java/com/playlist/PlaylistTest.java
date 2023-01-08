package com.playlist;

import com.songs.SearchSongs;
import com.songs.Songs;
import com.songs.playlist.CreatePlaylists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PlaylistTest {
    CreatePlaylists playlist;
    List<Songs> currentSongPlayList;

    @Before
    public void setup(){
        playlist = new CreatePlaylists();
        currentSongPlayList = new ArrayList<>();
    }

    @After
    public void tearDown(){
        playlist = null;
    }

    @Test
    public void getPlaylistTest(){
        currentSongPlayList = playlist.getPlaylist(3);
        assertEquals(4,currentSongPlayList.size());
    }

    @Test
    public void getPlaylistTestGivenWrongId(){
        currentSongPlayList = playlist.getPlaylist(100);
        assertEquals(0,currentSongPlayList.size());
    }
}
