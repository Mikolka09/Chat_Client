import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable {
    DataInputStream in;
    boolean flag = true;

    public Receive(Socket client) {
        try {
            in = new DataInputStream(client.getInputStream());
        } catch (IOException ex1) {
            System.out.println(ex1.getMessage());
            flag = false;
            try {
                in.close();
            } catch (IOException ex2) {
                System.out.println(ex2.getMessage());
            }
        }
    }

    // Получаем данные
    public String receive() {
        String msg = "";
        try {
            msg = in.readUTF();
        } catch (IOException ex1) {
            System.out.println(ex1.getMessage());
            flag = false;
            try {
                in.close();
            } catch (IOException ex2) {
                System.out.println(ex2.getMessage());
            }
        }
        return msg;
    }

    @Override
    public void run() {
        while (flag) {
            System.out.println(receive());
        }
    }
}
