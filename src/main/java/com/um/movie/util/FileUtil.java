package com.um.movie.util;

import com.um.movie.model.Movie;
import com.um.movie.model.Ticket;

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
                writer.write(movie.getTitle() + ";" + movie.getGenre() + ";" +
                        movie.getDuration() + ";" + movie.getShowingDate() + ";" + movie.getImage());
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
                            split[0],
                            split[1],
                            Integer.parseInt(split[2]),
                            LocalDate.parse(split[3], formatter),
                            split[4]
                    );
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }


    // menyimpan data tiket ke file
    public static void saveTicketToFile(String ticketNum, String title, double total, String date, String time) {
        String filePath = "database/tickets.txt"; // Nama file untuk menyimpan data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(ticketNum + "," + title + "," + total + "," + date + "," + time);
            writer.newLine(); // Tambah baris baru setelah setiap entri
            System.out.println("Data saved to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Ticket> loadTicketsFromFile() {
        List<Ticket> tickets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("database/tickets.txt"))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String ticketNum = values[0];
                String title = values[1];
                double total = Double.parseDouble(values[2]);
                String date = values[3];
                String time = values[4];

                Ticket ticket = new Ticket(ticketNum, title, total, date, time);
                tickets.add(ticket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
