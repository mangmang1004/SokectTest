package Project6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient {
    Screen screen; //이거를 전역변수로 사용
    static Login login = new Login();
    public static void main(String args[]) {
//        if (args.length != 1) {
//            System.out.println("들어가 있는 것이 없습니다.");
//            System.exit(0);
//        }
        login.setVisible(true); //client에서 로그인을 띄우도록 만든거다:)
    }

    public void mainRunner() {
        try {
            screen = new Screen();
            String serverIP = "127.0.0.1";
            //소켓을 생성하여 연결을 요청한다.
            Socket socket = new Socket("127.0.0.1", 4000);
            System.out.println("서버에 연결되었습니다");
            Thread sender = new Thread(new ClientSender(socket, login.id, screen));
            Thread receiver = new Thread(new ClientReceiver(socket));

            sender.start();
            receiver.start();

        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    } //소켓을 연결하는 부분

    static class ClientSender extends Thread {
        Socket socket;
        static DataOutputStream out;
        String name;
        Screen screen;


        ClientSender(Socket socket, String name, Screen screen) {
            this.socket = socket;
            this.name = name;
            this.screen = screen;

            try {
                out = new DataOutputStream(socket.getOutputStream());
                this.name = name;
            } catch (Exception e) {
            }

            screen.setVisible(true);
        }

        public void run() {
            Scanner scanner = new Scanner(System.in);
            try {
                if (out != null) {
                    out.writeUTF(name);
                }
//                while (out != null) {
////                    //out.writeUTF("[" + name + "]" + scanner.nextLine());
////                    if(Board.isOutputReal == true){
////                        System.out.println("이거 되냐?");
////                        out.writeUTF(Board.output);
////                        Board.isOutputReal = false;
////                    }
//
//                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }// run();

    }

    class ClientReceiver extends Thread {
        Socket socket;
        DataInputStream in;

        ClientReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
            } catch (Exception e) {
            }
        }

        public void run() {
            while (in != null) {
                try {
 //                   System.out.println(in.readUTF());
                      screen.paintXY(in.readUTF());
                } catch (IOException e) {
                }
            }
        }
    }
}
