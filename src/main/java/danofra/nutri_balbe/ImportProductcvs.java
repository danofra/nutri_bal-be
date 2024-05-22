package danofra.nutri_balbe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class ImportProductcvs {

    public static void main(String[] args) {
        String csvFile = "src/assets/elenco_prodotti.csv";
        String line = "";
        String csvSplitBy = ";"; // Assicurati che questo sia il delimitatore corretto
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("env.properties"));
            System.out.println("Loading properties successful.");
        } catch (IOException e) {
            System.err.println("Failed to load properties file.");
            e.printStackTrace();
            return;
        }

        String url = props.getProperty("SERVER_URL");
        if (url == null || url.isEmpty()) {
            System.err.println("The database connection URL was not found or is empty.");
            return;
        }
        System.out.println("Connection URL: " + url);

        String user = props.getProperty("SERVER_NAME");
        String password = props.getProperty("SERVER_PASSWORD");

        try (Connection connection = DriverManager.getConnection(url, user, password);
             BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            String sql = "INSERT INTO product (name, category, description, image, kcal) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Ignora la prima riga (intestazione)
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Salta le righe vuote
                }

                String[] data = line.split(csvSplitBy);

                if (data.length != 5) {
                    System.err.println("Invalid line format: " + line);
                    continue; // Salta le righe mal formattate
                }

                try {
                    preparedStatement.setString(1, data[0].trim());
                    preparedStatement.setString(2, data[1].trim());
                    preparedStatement.setString(3, data[2].trim());
                    preparedStatement.setString(4, data[3].trim());
                    preparedStatement.setInt(5, Integer.parseInt(data[4].trim()));
                    preparedStatement.executeUpdate();
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing integer value for line: " + line);
                    e.printStackTrace();
                }
            }

            System.out.println("Data imported successfully.");
        } catch (SQLException e) {
            System.err.println("Database error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading CSV file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred.");
            e.printStackTrace();
        }
    }
}

