package org.example.lab4;


import javafx.application.Platform;
import javafx.fxml.FXML;        // Allows Java to link code to an .fxml layout file
import javafx.geometry.Insets;
import javafx.scene.control.*;  // Buttons, Labels, ListViews, etc.
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

public class Mp3Controller {


    private PlayList playList = new PlayList();
    private File songFile;



    //Label to display the currently "playing" song.
    @FXML
    private Label lblTitle;

    @FXML
    private Label lblArtist;

    @FXML
    private Label lblDuration;

    //ListView control to show the play list of songs.s
    @FXML
    private ListView<String> lstPlayList;


    /***+
     * Called when the loadList button is clicked.
     */
    @FXML
    protected void onLoadListClick() {
        // Allow the user to choose the file with the playlist titles and pathnames.
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        songFile = chooser.showOpenDialog(new Stage());

        //If user did not Cancel, load the ArrayList and the ListView Control
        if (songFile != null) {
            //**** Problem #1 - Call the PlayList method to read the songs from the songFile
            try {
                // This moves the data from the physical file into my ArrayList in memory.
                playList.readPlayList(songFile);
            } catch (Exception e) {
                System.out.println("Error reading file: " + e.getMessage());
            }

            //Load the song titles into the lstPlayList control.
            //**** Problem #2 - Uncomment out the next lines. Iterate through the PlayList
            /*
            for () {
                Song s = playList.getSong(i);
                lstPlayList.getItems().add(s.toString('/'));
            }
            */
            for (int i = 0; i < playList.size(); i++) {
                Song s = playList.getSong(i);
                // Note: Ensure your Song class has a toString(char delimiter) method
                lstPlayList.getItems().add(s.toString());
            }
        }

        //Enable single selection mode (no multiple selection)
        lstPlayList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * +
     * Called when the Play button is clicked
     */
    @FXML
    protected void onPlayClick() {
        //Update the lblNowPlaying Label with the currently selected song title

        //Get a reference to the object that holds the displayed list of songs within the ListView control
        //and then call the method to get the selected item.  This is done in one statement.
        String selectedItem = lstPlayList.getSelectionModel().getSelectedItem();

        //**** Problem #3  Just a look how I use the String split method
        // SPLITTING: The '/' acts as a marker to cut the string into an array of smaller strings.
        String [] songParts = selectedItem.split("/");
        lblTitle.setText(songParts[0]);
        lblArtist.setText(songParts[1]);
        lblDuration.setText(songParts[2]);
    }

    private String getTextInput(String prompt) {
        TextInputDialog txtInput = new TextInputDialog();
        //Get the new song title
        txtInput.setHeaderText(prompt);
        txtInput.showAndWait();
        return txtInput.getEditor().getText();
    }

    /**
     * +
     * Called when the Add button is clicked
     */
    @FXML
    protected void onAddSongClick() {

        String songTitle;
        String songArtist;
        int songDuration = 0;

        songTitle = getTextInput("What is the song title");
        if (songTitle != null) {
            songArtist = getTextInput("What is the name of the song artist");

            if (songArtist != null) {
                String temp = getTextInput("What is the song duration in seconds");
                if (temp != null) {
                    songDuration = Integer.parseInt(temp);
                    Song s = new Song(songTitle, songArtist, songDuration);
                    playList.addSong(s);
                    lstPlayList.getItems().add(s.toString());

                }
            }
        }

    }


    /**
     * +
     * Called when the save button is clicked
     */
    @FXML
    protected void onSaveListClick() {

        //Create a new song file if we are starting a new playlist
        if (songFile == null) {
            songFile = new File("songs" + ((int) (Math.random() * 100)) + ".txt");
        }
        //#### Problem 4 - Call the method to save the PlayList in the songFile

        try {
            // PERSISTENCE: Send the current state of my 'playList' object back to the File.
            // This ensures my changes (like added songs) are kept after the program closes.
            playList.writePlayList(songFile);
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }

        //Inform the user that the file was saved.
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Song File " + songFile.getName() + " was saved");
        a.show();
    }

    /**
     * +
     * Called when the sort button is clicked
     */
    @FXML
    protected void onSortListClick() {
        //Clear the ListView and add reload the song titles
        lstPlayList.getItems().clear();

        //#### Problem 5 - Call the method to PlayList sort method
        for (int i = 0; i < playList.size(); i++) {
            Song s = playList.getSong(i);
            // FORMATTING: Manually building a string with delimiters for the display.
            lstPlayList.getItems().add(s.getTitle() + "/" + s.getArtist() + "/" + s.getDuration());
        }

        //Load the song titles into the lstPlayList control.
        for (int i = 0; i < playList.size(); i++) {
            Song s = playList.getSong(i);

            //#### Problem #6 - Uncomment out the line. Create a string with the title, artist and duration separated by a
            // "/" and pass it to the add method
            //lstPlayList.getItems().add();
            lstPlayList.getItems().add(s.toString('/'));
        }
    }


    /**
     * +
     * Called when the Close button is clicked
     */
    @FXML
    protected void onCloseClick() {
        javafx.application.Platform.exit();
    }

}