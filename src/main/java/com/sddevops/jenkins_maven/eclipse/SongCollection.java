package com.sddevops.jenkins_maven.eclipse;

import java.util.*;
import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class SongCollection {

    private ArrayList<Song> songs = new ArrayList<>();
    private int capacity;

    public SongCollection() {
        this.capacity = 20;
    }

    public SongCollection(int capacity) {
        this.capacity = capacity;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
    	if(songs.size() != capacity) {
    		songs.add(song);
    	}
    }
    
    public List<Song> sortSongsByTitle() {         
        Collections.sort(songs, Song.titleComparator);         
        return songs;     
    } 
    
    public List<Song> sortSongsBySongLength() {         
        Collections.sort(songs, Song.songLengthComparator);         
        return songs;     
    } 
    
    public Song findSongsById(String id) {
    	for (Song s : songs) { 		      
            if(s.getId().equals(id)) return s;
       }
    	return null;
    }

    public Song findSongByTitle(String title) {
    	for (Song s : songs) { 		      
            if(s.getTitle().equals(title)) return s;
       }
    	return null;
    }
    
    protected String fetchSongJson() {
    	String urlString = "https://mocki.io/v1/6cc1ae14-5c41-4787-9750-83bc535af1a5";
    	try {
    		URL url = new URL(urlString);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    	
    	if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	    	BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    	StringBuilder response = new StringBuilder();
	    	String inputLine;
	    	while ((inputLine = in.readLine()) != null) {
	    		response.append(inputLine);
	    		}
	    	in.close();
	    	return response.toString();
    			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    		return null;
    	}
    
    public Song fetchSongOfTheDay() {
    	try {
	    	String jsonStr = fetchSongJson();
	    	if (jsonStr == null) return null;
    		JSONObject json = new JSONObject(jsonStr);
	    	Song song = new Song(
	    			json.getString("id"),
	    			json.getString("title"),
	    			json.getString("artiste"),
	    			json.getDouble("songLength")
	    	);
	    	if (song.getArtiste().equals("Taylor Swift")) {
	    		song.setArtiste("TS");
	    		this.addSong(song);
	    	}else if (song.getArtiste().equals("Bruno Mars")) {
	    		song.setArtiste("BM");
	    		this.addSong(song);
	    	}
	    	return song;
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		return null;
	    	}
    	}
}
