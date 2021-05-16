package Controllers;

import Components.Game;
import Components.ProviderCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ProviderController implements Initializable {

    @FXML
    private Text provNameLabel;
    public GridPane gamesGridPane;
    public Button newOfferButton;

    public AnchorPane welcomeAnchorPane;
    public AnchorPane newOfferAnchorPane;
    public AnchorPane changeOfferAnchorPane;

    public TextField gameNameTextField;
    public TextField priceTextField;
    public TextField descriptionTextField;
    public DatePicker startDateDatePicker;
    public DatePicker endDateDatePicker;
    public ChoiceBox<String> choiceBoxo;
    public ListView<String> gameListListView;

    ProviderCollection providerCollection;
    ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData() {

        list.removeAll();

        String a = "true";
        String b = "false";

        list.addAll(a,b);

        choiceBoxo.getItems().addAll(list);

    }

    public void setNameLabel(String name) {
        provNameLabel.setText(name);
    }

    public void newOfferClick() {

        newOfferAnchorPane.setVisible(true);
        welcomeAnchorPane.setVisible(false);
        changeOfferAnchorPane.setVisible(false);
    }

    public void seeGamesClick() {

        welcomeAnchorPane.setVisible(true);
        newOfferAnchorPane.setVisible(false);
        changeOfferAnchorPane.setVisible(false);
    }

    public void changeOfferClick() {

        changeOfferAnchorPane.setVisible(true);
        newOfferAnchorPane.setVisible(false);
        welcomeAnchorPane.setVisible(false);

    }

    public void changeThisOfferButtonClick() {

        providerCollection = new ProviderCollection(provNameLabel.getText());

        //remove current prov from dtb
        providerCollection.getProviderDTB().removeProvider(providerCollection.getCurrentProvider());
        //remove this game from current provider
        providerCollection.getCurrentProvider().removeGame(gameListListView.getSelectionModel().getSelectedItem());
        //add current provider to dtb
        providerCollection.getProviderDTB().addProvider(providerCollection.getCurrentProvider());
        //updateDTB
        providerCollection.getProviderDTB().printProviders();

        this.setGamesGridPane(providerCollection.getCurrentProvider().getStringGameArray());
        this.setGameListListView(providerCollection.getCurrentProvider().getStringGameArray());

        newOfferClick();

    }

    public void makeNewOfferButtonClick() {

        String gName;
        double price;
        Date startDate;
        Date endDate;
        boolean rent;
        String description;

        Calendar cal = Calendar.getInstance();

        gName = gameNameTextField.getText();
        price = Double.parseDouble(priceTextField.getText());
        startDate = java.sql.Date.valueOf(startDateDatePicker.getValue());

        cal.setTime(startDate);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        startDate = cal.getTime();

        endDate = java.sql.Date.valueOf(endDateDatePicker.getValue());

        cal.setTime(endDate);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        endDate = cal.getTime();

        rent = Boolean.parseBoolean(choiceBoxo.getValue());
        description = descriptionTextField.getText();

        Game addedGame = new Game(gName, price, description, startDate, endDate, rent);
        System.out.println(addedGame);

        providerCollection = new ProviderCollection(provNameLabel.getText());
        //remove current prov from dtb
        providerCollection.getProviderDTB().removeProvider(providerCollection.getCurrentProvider());
        //add this game from current provider
        providerCollection.getCurrentProvider().addGame(addedGame);
        //add current provider to dtb
        providerCollection.getProviderDTB().addProvider(providerCollection.getCurrentProvider());
        //updateDatabase
        providerCollection.getProviderDTB().printProviders();

        this.setGamesGridPane(providerCollection.getCurrentProvider().getStringGameArray());
        this.setGameListListView(providerCollection.getCurrentProvider().getStringGameArray());

    }

    public void setGamesGridPane(ArrayList<String> from) {

        int n = 0;
        int m = 0;

        for (Node pane: gamesGridPane.getChildren()) {
            GridPane.setHalignment(pane, HPos.CENTER);
            GridPane.setValignment(pane, VPos.CENTER);
        }

        gamesGridPane.getChildren().clear();

        for (String i: from) {

            Text a = new Text();
            a.setText(i);
            a.setFont(new Font("Verdana",20));
            a.setFill(Color.WHITESMOKE);

            gamesGridPane.add(a, m, n);

            m++;
            if (m == 2) {
                n++;
                m = 0;
            }

        }
    }

    public void setGameListListView(ArrayList<String> from) {

        ObservableList<String> observableList = FXCollections.observableArrayList();

        observableList.removeAll();

        observableList.addAll(from);

        gameListListView.getItems().clear();
        gameListListView.getItems().addAll(observableList);

    }

}
