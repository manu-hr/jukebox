import com.songs.playlist.CreatePlaylists;
import com.songs.playsong.PlaySongs;
import com.songs.SearchSongs;
import com.user.User;
import utils.exceptions.WrongOptionException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class JukeboxImpl {

    private boolean getUserStatus() {

        boolean userStatus = false;

            Scanner sc = new Scanner(System.in);
            User user = new User();
            System.out.println("NEW TO HERE ?\n\tIF YES, ENTER 1 to SIGN UP\n\tOR ELSE, ENTER 2 to SIGN IN.");
            int option =sc.nextInt();
            switch (option) {
                case 1 -> {
                    userStatus = user.userRegistration();
                }
                case 2 ->{
                    userStatus = user.userLogin();
                }
                default -> {
                    System.out.println("Not a valid option you have selected!");
                    userStatus = getUserStatus();
                }
            }


        return userStatus;
    }

    private void displayMenu(){
        loop : while(true){
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("******************************************************************** ");
                System.out.print(" MENU OPTIONS ");
                System.out.println("********************************************************************");
                System.out.println("Enter 1 to Play Songs\nEnter 2 to Playlist Options\nEnter 3 to Search songs\nEnter 4 to exit");
                switch (scanner.nextInt()) {
                    case 1 -> {
                        playSongMenuList();
                    }
                    case 2 ->{
                        createPlaylistMenuOptions();
                    }
                    case 3 -> {
                        searchMenuOptions();
                    }
                    case 4 -> {
                        break loop;
                    }
                    default -> {
                        System.out.println("Not a valid option you have selected!");
                        displayMenu();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(e);
                displayMenu();
            }

        }


    }

    private void searchMenuOptions() throws InputMismatchException {

        Scanner sc = new Scanner(System.in);
        int byFirstLetter = 0;
        SearchSongs search = new SearchSongs();
        System.out.println("Search by\n1.Song Name\n2.Artist\n3.Album\n4.Genre");

        switch (sc.nextInt()){
            case 1 ->{
                System.out.println("Enter 1 to search By First Character\nEnter 2 to search in a Pattern");
                byFirstLetter = sc.nextInt();
                if(byFirstLetter == 1){
                    System.out.println("Enter a letter to search with");
                    String c = sc.next();
                    search.search("song", c, byFirstLetter);
                    break;
                }

                System.out.println("Enter a Song name to search");
                search.search("song",sc.next(), byFirstLetter);
            }
            case 2 ->{
                System.out.println("Enter 1 to search By First Character\nEnter 2 to search in a Pattern");
                byFirstLetter = sc.nextInt();
                if(byFirstLetter == 1){
                    System.out.println("Enter a letter to search with");
                    String c = sc.next();
                    search.search("artist", c, byFirstLetter);
                    break;

                }
                System.out.println("Enter a Artist name to search");
                search.search("artist",sc.next(), byFirstLetter);
            }
            case 3 ->{
                System.out.println("Enter 1 to search By First Character\nEnter 2 to search in a Pattern");
                byFirstLetter = sc.nextInt();
                if(byFirstLetter == 1){
                    System.out.println("Enter a letter to search with");
                    String c = sc.next();
                    search.search("album", c, byFirstLetter);
                    break;

                }
                System.out.println("Enter a Album name to search");
                search.search("album",sc.next(), byFirstLetter);
            }
            case 4 ->{
                System.out.println("Enter 1 to search By First Character\nEnter 2 to search in a Pattern");
                byFirstLetter = sc.nextInt();
                if(byFirstLetter == 1){
                    System.out.println("Enter a letter to search with");
                    String c = sc.next();
                    search.search("genre", c, byFirstLetter);
                    break;

                }
                System.out.println("Enter a Genre Type to search");
                search.search("genre",sc.next(), byFirstLetter);
            }
            default -> {
                System.out.println("Not a valid option you have selected!");

            }

        }

    }

    private void createPlaylistMenuOptions(){
        Scanner sc = new Scanner(System.in);
        CreatePlaylists playlist = new CreatePlaylists();
        System.out.println("1.Create Playlist\n2.Update Playlist\n3.View Playlists\n4.Delete Playlist");
        try{
            switch (sc.nextInt()){
                case 1 -> playlist.createNewPlaylist();
                case 2 ->{
                    System.out.println("Enter your playlist name");
                    String playlistName = sc.next();
                    playlist.updatePlaylistSongs(playlistName.trim());
                }
                case 3 -> playlist.displayPlaylistNames();
                case 4 -> playlist.deletePlaylist();
                default -> System.out.println("Invalid Option");
            }

        }catch (WrongOptionException e){
            System.out.println(e.getMessage());
        }


    }

    private void playSongMenuList(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 to play All songs\nEnter 2 to play by playlist\nEnter 3 to play a particular song");
        PlaySongs play = new PlaySongs();
        try{
            switch (sc.nextInt()) {
                case 1 -> play.playAllSongs();
                case 2 -> play.playPlaylist();
                case 3 -> play.playParticularSong();
                default -> System.out.println("Invalid Choice");

            }
        }catch (WrongOptionException e){
            System.out.println(e.getMessage());
        }

    }


    public static void main(String[] args) {
        System.out.print("******************************************************************** ");
        System.out.print(" WELCOME USER! ");
        System.out.println("********************************************************************");
        JukeboxImpl jukeboxImpl = new JukeboxImpl();
        boolean userStatus = jukeboxImpl.getUserStatus();

        if(userStatus)
            jukeboxImpl.displayMenu();
        else
            System.out.println("FAILED TO AUTHENTICATE THE USER!");
    }

}
