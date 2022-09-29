import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("Пожалуйста, введите НИК");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();

        Socket client = new Socket("localhost", 8888);
        // Клиент отправляет
        new Thread(new Send(client, name)).start();
        // Клиент получает
        new Thread(new Receive(client)).start();
    }
}
