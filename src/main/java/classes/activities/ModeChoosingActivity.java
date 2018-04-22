package classes.activities;

import classes.HolderClient;
import classes.HolderConnection;
import classes.HolderServer;
import classes.Main;
import classes.encryption.IPConverter;
import classes.internet.Connector;
import classes.keeping.Constants;
import classes.keeping.ConstantsKeeper;
import classes.utils.ApoThread;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class ModeChoosingActivity {
    static String ip;

    public static Scene getScene() {
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

        try {
            HttpServer server1 = HttpServer.create(new InetSocketAddress(8000), 0);
            server1.createContext("/test", new MyHandler());
            server1.setExecutor(null); // creates a default executor
            server1.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        VBox root = new VBox(20, server, client, message);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(400, 600);

        return new Scene(root, 600, 600);
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            System.out.println(Arrays.toString(t.getRequestHeaders().entrySet().toArray()));
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
