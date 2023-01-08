package com.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.db.JukeboxDB;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;


public class JukeboxDBTest {
    JukeboxDB db;

    @Before
    public void setup(){
        db = new JukeboxDB();

    }

    @After
    public void tearDown(){
        db = null;
    }

    @Test
    public void selectingPlaylistSongsGivenCorrectTableName(){
        String query = "Select * from playlistSongs where playlistId = 3";
        ResultSet rs = db.selectFromTable(query);
        assertNotNull(rs);
    }

    @Test
    public void selectingPlaylistSongsGivenIncorrectTableName() {
        String query = "Select * from PlaylistsSong where playlistId = 3";
        ResultSet rs = db.selectFromTable(query);
        assertNull(rs);
    }
}
