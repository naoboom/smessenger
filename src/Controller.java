import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TreeView<String> treeMembers;;

    @FXML
    private ListView<String> lstMessages ;


    @FXML
    private TextField fldMessage;

    @FXML
    private TextField fldServerURL;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnSend;
    @FXML
    private TextField fldNick;

    @FXML
    private TextField fldStatusMember;

    @FXML
    private Button btnOK;

    @FXML
    void initialize() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Client client = new Client("localhost", 3456);
        btnOK.setOnAction(actionEvent -> {
            client.setUserName(fldNick.getText());
        //    myStatus = fldStatusMember.getText();
        });

        btnConnect.setOnAction(actionEvent -> {
            String[] serverURL = fldServerURL.getText().split(":");
            String result = "";
            if (serverURL[0] != null) {
                client.setHostname(serverURL[0]);
                client.setPort(serverURL[1]);
                result = client.execute();
            }
                client.messages.add(result);


        });
        lstMessages.setItems(client.messages);
        btnSend.setOnAction(actionEvent -> {
            LocalDateTime now = LocalDateTime.now();
            String message =  dtf.format(now) + " [" + client.getUserName() + "]: " + fldMessage.getText();
            client.messages.add(message);
            client.setMessage(fldMessage.getText());
            fldMessage.setText("");
        });



    }


}
