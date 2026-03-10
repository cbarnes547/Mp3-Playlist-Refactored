package org.example.lab4;

public class Song {
    // Private variables (fields) that store the song's data
    // 'private' means they can't be changed directly from outside this class
    private String title;
    private String artist;
    private int duration;

    // Default Constructor: Creates a "blank" song with empty/zero values
    public Song() {
        title = null;
        artist = null;  // null means the String points to nothing
        duration = 0;
    }

    // OVERLOADED CONSTRUCTOR: Takes ARGUMENTS to set the initial state of the object
    // 'this' is a REFERENCE used to distinguish between fields and local parameters
    public Song(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }
    // ACCESSOR (GETTER): A method that allows safe access to a private field
    // the private data without being able to change it directly
    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public int getDuration() {
        return duration;
    }
    // method that returns the song info separated by a character you choose
    // Example: If delimiter is ',', it returns "Title,Artist,180"
    public String toString(char delimiter) {
        return title + delimiter + artist + delimiter + duration;
    }

    @Override
    public String toString() {
        return title + "\n" + artist + "\n" + duration;
    }
}
