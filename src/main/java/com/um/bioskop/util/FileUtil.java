package com.um.bioskop.util;

import com.um.movie.model.Movie;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    //penyimpanan database admin dari file ke Map
    private static Map<String, String> adminMap = new HashMap<>();

    //memuat database admin dari database admin dari file ke map
    public static void loadAdmins(){
        try(BufferedReader reader = new BufferedReader(new FileReader("admin.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(";");
                if(parts.length == 2){
                    adminMap.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //verifikasi login
    public static boolean verifyLogin(String username, String password){
        return adminMap.containsKey(username) && adminMap.get(username).equals(password);
    }



    // menyimpan movie ke dalam file
    public static void saveMoviesToFile(List<Movie> movies){
        String fileName = "movies_" + LocalDate.now() + ".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for(Movie movie : movies){
                writer.write(movie.getId() + ";" + movie.getTitle() + ";" + movie.getGenre() + ";" +
                        movie.getDuration() + ";" + movie.getShowingDate());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Movie> loadMoviesFromFile(){
        String fileName = "movies_" + LocalDate.now() + ".txt";
        List<Movie> movies = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            while ((line = reader.readLine()) != null){
                String[] split = line.split(";");
                if(split.length == 5){
                    Movie movie = new Movie(
                            Integer.parseInt(split[0]),
                            split[1],
                            split[2],
                            Integer.parseInt(split[3]),
                            LocalDate.parse(split[4], formatter)
                    );
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
