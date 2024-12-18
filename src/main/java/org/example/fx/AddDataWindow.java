package org.example.fx;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class AddDataWindow {
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);//само сообщение
        alert.showAndWait(); //показать уведомление
    }
    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Добавление нытиков в бд");

        TextField id = new TextField();
        id.setPromptText("Введите ID нытика");

        TextField name = new TextField();
        name.setPromptText("Введите имя нытика");

        TextField last = new TextField();
        last.setPromptText("Введите фамилию нытика");

        Button saveData = new Button("Сохранить");
        saveData.setOnAction(e -> {
            String idText = id.getText();
            int idTest = idText.isEmpty() ? 0 : Integer.parseInt(idText);
            String nameText = name.getText();
            String lastText = last.getText();

            Data data = new Data(idTest, nameText, lastText);
            saveData(data);
            window.close();
        });
        VBox layout = new VBox(5, id, name, last, saveData);
        //создаем сцену
        Scene scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.show();
    }
    public static void saveData(Data data) {
        String url = "jdbc:mysql://localhost:3306/example";
        String user = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query;
            if (data.id() == 0) {
                query = "insert into nutik(firstname, lastname) values (?, ?)";
            }
            else {
                query = "insert into nutik(id, firstname, lastname) values (?, ?, ?)";
            }
            PreparedStatement statement = conn.prepareStatement(query);

            if (data.id() == 0) {
                statement.setString(1, data.FirstName());
                statement.setString(2, data.LastName());
            } else {
                statement.setInt(1, data.id());
                statement.setString(2, data.FirstName());
                statement.setString(3, data.LastName());
            }
            statement.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Добавили нытиков");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не добавили");
        }
    }
}
