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

    // Penyimpanan com.um.movie.database admin dari file ke Map
    private static Map<String, String> adminMap = new HashMap<>();

    // Memuat com.um.movie.database admin dari file ke map
    public static void loadAdmins() {
        String fileName = "database/admin.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    adminMap.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Verifikasi login
    public static boolean verifyLogin(String username, String password) {
        return adminMap.containsKey(username) && adminMap.get(username).equals(password);
    }

    // Menyimpan movie ke dalam file (append mode)
    public static void saveMoviesToFile(List<Movie> movies) {
        String fileName = "database/movies.txt"; // Nama file tetap
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { // Mode append
            for (Movie movie : movies) {
                writer.write(movie.getTitle() + ";" + movie.getGenre() + ";" +
                        movie.getDuration() + ";" + movie.getShowingDate() + ";" + movie.getImage() + ";" + movie.getCurrent());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Memuat movie dari file
    public static List<Movie> loadMoviesFromFile() {
        String fileName = "./database/movies.txt"; // Nama file tetap
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(";");
                if (split.length == 6) {
                    Movie movie = new Movie(
                            split[0],
                            split[1],
                            Integer.parseInt(split[2]),
                            LocalDate.parse(split[3]),
                            split[4]
                    );
                    movie.setCurrent(split[5]);
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Menghapus movie dari file
    public static void deleteMovieFromFile(String movieTitle) {
        String fileName = "database/movies.txt"; // Nama file tetap
        File inputFile = new File(fileName);
        File tempFile = new File("database/temp_movies.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(";");
                if (!split[0].equals(movieTitle)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace the original file with the updated one
        if (!inputFile.delete()) {
            System.out.println("Could not delete original file");
        } else if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temp file");
        }
    }



    // Menyimpan data tiket ke file (append mode)
    public static void saveTicketToFile(String ticketNum, String title, double total, String date, String time) {
        String filePath = "database/tickets.txt"; // Nama file tetap
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Mode append
            writer.write(ticketNum + "," + title + "," + total + "," + date + "," + time);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Memuat tiket dari file
    public static List<Ticket> loadTicketsFromFile() {
        String filePath = "database/tickets.txt";
        List<Ticket> tickets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 5) {
                    String ticketNum = values[0];
                    String title = values[1];
                    double total = Double.parseDouble(values[2]);
                    String date = values[3];
                    String time = values[4];

                    Ticket ticket = new Ticket(ticketNum, title, total, date, time);
                    tickets.add(ticket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Menghapus tiket dari file
    public static void deleteTicketFromFile(String ticketNum) {
        File inputFile = new File("database/tickets.txt");
        File tempFile = new File("database/tickets_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] values = currentLine.split(",");
                if (values[0].equals(ticketNum)) {
                    continue; // Skip line if ticketNum matches
                }
                writer.write(currentLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Delete the original file and rename the temp file
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }


}
