package org.example.lab4;
import java.util.ArrayList;   // A resizable array (collection)
import java.util.Calendar;  // Used for handling dates and times
import java.io.*;           // Input/Output classes (File, PrintWriter)
import java.util.Scanner;   // Used to read text from files or console

public class PlayList {
    private String name;
    private ArrayList<Song> songs;
    private Calendar creationDate;  // Gets the current system date/time

    public PlayList() {
        name = null;
        songs = new ArrayList<>();
        creationDate = Calendar.getInstance();
    }

    public void readPlayList(File inputFile) {
        try {
            Scanner in = new Scanner(inputFile);

            name = in.nextLine();

            String[] date = in.nextLine().split("/");
            creationDate = Calendar.getInstance();
            creationDate.set(
                    Integer.parseInt(date[2]),
                    Integer.parseInt(date[0]) - 1,
                    Integer.parseInt(date[1])
            );

            while (in.hasNextLine()) {
                String title = in.nextLine();
                String artist = in.nextLine();
                // WRAPPER CLASS: Using Integer.parseInt to convert a String into a primitive int
                int duration = Integer.parseInt(in.nextLine());
                // ADDING TO LIST: Creating a new Song object and storing it in our ArrayList
                songs.add(new Song(title, artist, duration));
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Song getSong(int index) {
        return (index >= 0 && index < songs.size()) ? songs.get(index) : null;
    }

    public void addSong(Song song) {
        if (song != null) songs.add(song);
    }

    public Song removeSong(int index) {
        return (index >= 0 && index < songs.size()) ? songs.remove(index) : null;
    }

    public int size() {
        return songs.size();
    }

    public void clearPlayList() {
        name = null;
        songs.clear();
        creationDate = Calendar.getInstance();
    }

    // FORMATTING: Converting a complex object (Calendar) into a readable String
    public String getCreationDateString() {
        return (creationDate.get(Calendar.MONTH) + 1) + "/"
                + creationDate.get(Calendar.DAY_OF_MONTH) + "/"
                + creationDate.get(Calendar.YEAR);
    }

    @Override
    public String toString() {
        String output = name + "\n" + getCreationDateString() + "\n";
        for (Song s : songs) {
            output += s.toString() + "\n";
        }
        return output;
    }

    public void writePlayList(File outputFile) {
        try {
            PrintWriter out = new PrintWriter(outputFile);
            out.print(toString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
