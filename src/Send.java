import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Send implements Runnable {
    BufferedReader console;
    DataOutputStream out;
    boolean flag = true;
    String name;

    public Send() {
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public Send(Socket client, String name) {
        this();
        this.name = name;
        try {
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            flag = false;
        }

    }

    // Получаем данные из консоли
    private String getMsgFromConsole() {
        try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    // отправить данные
    public void send(String msg) {
        if (null != msg && !msg.equals("")) {
            try {
                out.writeUTF(name + ": " + msg);
                Date now = new Date(System.currentTimeMillis());
                String time = new SimpleDateFormat("hh:mm:ss   yyyy/MM/dd  ").format(now);
                System.out.println(time);
                System.out.println(name + ":" + msg);
                out.flush();
            } catch (IOException ex1) {
                System.out.println(ex1.getMessage());
                try {
                    flag = false;
                    out.close();
                    console.close();
                } catch (IOException ex2) {
                    System.out.println(ex2.getMessage());
                }

            }
        } else {
            try {
                out.writeUTF(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        send("");
        while (flag) {
            send(getMsgFromConsole());
        }
    }
}
