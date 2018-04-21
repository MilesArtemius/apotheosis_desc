package classes;

/*import de.javawi.jstun.attribute.ChangeRequest;
import de.javawi.jstun.attribute.MappedAddress;
import de.javawi.jstun.attribute.MessageAttribute;
import de.javawi.jstun.header.MessageHeader;*/
import classes.encryption.IPConverter;
import classes.internet.Connector;
import classes.keeping.Constants;
import classes.keeping.ConstantsKeeper;
import classes.utils.ApoThread;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    String ip;

    @Override
    public void start(Stage primaryStage) {
        Button server = new Button();
        server.setId("server_button");
        server.setText("I'm server");
        Button client = new Button();
        client.setId("client_button");
        client.setText("I'm client");
        Text message = new Text();
        message.setStyle("-fx-text-fill: green; -fx-font-size: 20;");
        message.setId("message_string");

        server.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HolderServer server = new HolderServer();
                server.connect();
                HolderConnection.keep(server);
            }
        });

        client.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HolderClient client = new HolderClient();
                client.connect();
                HolderConnection.keep(client);
            }
        });

        ApoThread getServerIp = new ApoThread(false) {
            @Override
            public void onRun() {
                ip = Connector.primalIpCheck("http://checkip.amazonaws.com");
                if (ip != null) return;
                ip = Connector.primalIpCheck("http://bot.whatismyipaddress.com");
            }

            @Override
            public void onComplete() {
                if (ip == null) {
                    Dialog<String> ipManual = new Dialog<>();
                    ip = ipManual.showAndWait().get();
                }
                message.setText(IPConverter.encode(ip));
                ConstantsKeeper.get().setConstant(Constants.PUBLIC_IP, ip);
            }
        };
        getServerIp.start();

        ConstantsKeeper.get().setConstant(Constants.IS_SERVER, String.valueOf(true));

        VBox root = new VBox(20, server, client, message);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(400, 600);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
