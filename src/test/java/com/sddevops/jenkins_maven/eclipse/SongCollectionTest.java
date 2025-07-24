/**
 * 
 */
package com.sddevops.jenkins_maven.eclipse;

import static org.junit.jupiter.api.Assertions.*;			

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;

class SongCollectionTest {
	private SongCollection sc;
	private Song s1;
	private Song s2;
	private Song s3;
	private Song s4;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() {
		sc=new SongCollection();
		s1 = new Song("001","good 4 u","Olivia Rodrigo",3.59);
		s2 = new Song("002","Peaches","Justin Bieber",3.18);
		s3 = new Song("003","MONTERO","Lil Nas",2.3);
		s4 = new Song("004","bad guy","billie eilish",3.14);
		sc.addSong(s1);
		sc.addSong(s2);
		sc.addSong(s3);
		sc.addSong(s4);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		 // No cleanup necessary after each test
	}

	/**
	 * Test method for {@link com.sddevops.junit_maven.eclipse.SongCollection#getSongs()}.
	 */
	@Test
	void testGetSongs() {
		List<Song> testSc=sc.getSongs();
		//Assert that Song Collection is equals to Song Collection Size 4
		assertEquals(4, 0);
		//Act
		sc.addSong(s1);
		//Assert that Song Collection is equals to Song Collection Size 4 + 1
		assertEquals(5, testSc.size());
	}

	/**
	 * Test method for {@link com.sddevops.junit_maven.eclipse.SongCollection#addSong(com.sddevops.junit_maven.eclipse.Song)}.
	 */
	

	/**
	 * Test method for {@link com.sddevops.junit_maven.eclipse.SongCollection#sortSongsByTitle()}.
	 */
	@Test
	void testSortSongsByTitle() {
		List<Song> sortedSongList = sc.sortSongsByTitle();
		assertEquals("MONTERO", sortedSongList.get(0).getTitle());
		assertEquals("Peaches", sortedSongList.get(1).getTitle());
		assertEquals("bad guy", sortedSongList.get(2).getTitle());
		assertEquals("good 4 u", sortedSongList.get(3).getTitle());
	}

	/**
	 * Test method for {@link com.sddevops.junit_maven.eclipse.SongCollection#sortSongsBySongLength()}.
	 */
	@Test
	void testSortSongsBySongLength() {
		List<Song> sortedSongByLengthList = sc.sortSongsBySongLength();
		assertEquals(3.59, sortedSongByLengthList.get(0).getSongLength());
		assertEquals(3.18, sortedSongByLengthList.get(1).getSongLength());
		assertEquals(3.14, sortedSongByLengthList.get(2).getSongLength());
		assertEquals(2.3, sortedSongByLengthList.get(3).getSongLength());
	}

	/**
	 * Test method for {@link com.sddevops.junit_maven.eclipse.SongCollection#findSongsById(java.lang.String)}.
	 */
	@Test
	void testFindSongsById() {
		Song song = sc.findSongsById("004");
		assertEquals("billie eilish", song.getArtiste());
		assertNull(sc.findSongsById("193"));
	}

	/**
	 * Test method for {@link com.sddevops.junit_maven.eclipse.SongCollection#findSongByTitle(java.lang.String)}.
	 */
	@Test
	void testFindSongByTitle() {
		Song song = sc.findSongByTitle("MONTERO");
		assertEquals("Lil Nas", song.getArtiste());
		assertNull(sc.findSongByTitle("doesn't exist"));
	}
	
	
	//########### below codes are for elearning lesson 9  ############
	@Test
	void testFetchSongOfTheDay(){
		String mockJson = """
				{
					"id": "001",
					"title": "Mock Song",
					"artiste": "Mock Artist",
					"songLength": 4.25
				}
				""";
		
		SongCollection collection = spy(new SongCollection());
		doReturn(mockJson).when(collection).fetchSongJson();
		Song result = collection.fetchSongOfTheDay(); 
		
		assertNotNull(result);
		assertEquals("001", result.getId());
		assertEquals("Mock Artist", result.getArtiste());
		assertEquals(4.25, result.getSongLength());
	}
	
	@Test
	void testInvalidFetchSongOfTheDay() {
		SongCollection collection = spy(new SongCollection());
		doReturn(null).when(collection).fetchSongJson();
		Song result = collection.fetchSongOfTheDay();
		
		assertNull(result);
	}
	
	@Test
	void testExceptionHandlingInFetchSongOfTheDay() {
		SongCollection collection = spy(new SongCollection());
		doThrow(new RuntimeException("API failed")).when(collection).fetchSongJson();
		
		Song result = collection.fetchSongOfTheDay();
		assertNull(result);
		assertEquals(0, collection.getSongs().size());
	}
	
	
	// test case 1: artist is Taylor Swift and should abbreviate to "TS"
	@Test
	void testFetchSongOfTheDay_TaylorSwift() {
		String mockJson = """
				{
					"id": "002", 
					"title": "Love Story", 
					"artiste": "Taylor Swift", 
					"songLength": 3.55
				}
				
				
				""";
		
		SongCollection collection = spy(new SongCollection(3));
		doReturn(mockJson).when(collection).fetchSongJson();
		
		Song result = collection.fetchSongOfTheDay();
		
		assertNotNull(result);
		assertEquals("002", result.getId());
		assertEquals("Love Story", result.getTitle());
		assertEquals("TS", result.getArtiste());
		assertEquals(3.55, result.getSongLength());
	} 
	
	// test case 2: artist is Bruno Mars and should abbreviate to "BM"
	@Test
	void testFetchSongOfTheDay_BrunoMars() {
		String mockJson = """
				{
					"id": "003", 
					"title": "Grenade", 
					"artiste": "Bruno Mars", 
					"songLength": 3.85
				}
				
				
				""";
		
		SongCollection collection = spy(new SongCollection(3));
		doReturn(mockJson).when(collection).fetchSongJson();
		
		Song result = collection.fetchSongOfTheDay();
		
		assertNotNull(result);
		assertEquals("003", result.getId());
		assertEquals("Grenade", result.getTitle());
		assertEquals("BM", result.getArtiste());
		assertEquals(3.85, result.getSongLength());
	} 
	
	
	
	
	
	
	

}
