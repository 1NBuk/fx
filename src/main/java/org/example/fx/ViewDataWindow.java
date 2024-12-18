package org.example.fx;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.*;


public class ViewDataWindow {
    public static void vivodnutik() {
        Stage window = new Stage();
        window.setTitle("Вывод нытиков");

        TableView<Data> tableView = new TableView<>();
        //первый столбец
        TableColumn <Data, Integer> id = new TableColumn<>("ID");
        //вызов id из data
        id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().id()));
        //второй столбец
        TableColumn <Data, String> firstname = new TableColumn<>("Имя");
        firstname.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().FirstName()));
        //третий столбец
        TableColumn <Data, String> lastname = new TableColumn<>("Фамилия");
        lastname.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().LastName()));

        //создали, а теперь добавляем в таблицу
        tableView.getColumns().addAll(id, firstname, lastname);
        //берем значения из бд
        tableView.setItems(fetchtoDB());

        Scene scene = new Scene(tableView);
        window.setScene(scene);
        window.showAndWait();
    }
    //позволяет из данные передавать в класс=>список
    private static ObservableList<Data> fetchtoDB() {
        ObservableList<Data> dataList = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost:3306/example";
        String user = "root";
        String password = "root";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "select * from nutik";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dataList.add(new Data(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
