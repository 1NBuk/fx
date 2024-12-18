package org.example.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.fx.AddDataWindow.display;
import static org.example.fx.ViewDataWindow.vivodnutik;

//stage-главное окно
public class MainApp extends Application {
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);//само сообщение
        alert.showAndWait(); //показать уведомление
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Нытики");//заголовок окна
        //создаем кнопку
        Button createTable = new Button("Создать ноющую таблицу в базе данных");
        //логика кнопки
        createTable.setOnAction(event -> nutikTable());

        Button vision = new Button("Добавить данные");
        vision.setOnAction(event -> display());

        Button table = new Button("Показать данные из БД");
        table.setOnAction(event -> vivodnutik());

        //набрасать макет и этот макет отвечает за отступы между кнопками
        VBox layout = new VBox(5, createTable, vision, table);
        //создаем сцену
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void nutikTable() {
        String url = "jdbc:mysql://localhost:3306/example";
        String user = "root";
        String password = "root";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "CREATE TABLE IF NOT EXISTS nutik (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "firstname VARCHAR(255), " +
                    "lastname VARCHAR(255))"; // исправлена ошибка: добавлена закрывающая скобка
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            // выводится об успешном создании
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Таблица не создана");
        }
    }

    public static void main(String[] args) {
        launch(args);//на всякий случай, если не работает без него
    }
}
