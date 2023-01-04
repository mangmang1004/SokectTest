package Project6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class MultiServer {
    HashMap clients; //서버에 접속한 클라이언트를 여기서 관리
    //클라이언트가 데이터를 입력하면, 멀티채팅서버는 HashMap에 저장된 모든 클라이언트에게 데이터를 전송한다.
    //이름이랑 outputstream을 가짐

    MultiServer(){
        clients = new HashMap();
        Collections.synchronizedMap(clients); //들어가는 순서대로 clients를 저장
    }

    public void start(){
        ServerSocket serverSocket = null; //서버소켓은 여기서만 만듬
        Socket socket = null; //클라이언트의 소켓을 가져옴

        try{
            serverSocket = new ServerSocket(4000);
            System.out.println("서버가 시작됐습니다.");

            while(true){
                socket = serverSocket.accept(); //클라이언트에서 소켓이 오는 걸 기다리고 있겠다.
                System.out.println("[" + socket.getInetAddress() + ":" +
                        socket.getPort() + "]" + "에서 접속하였습니다.");
                ServerReceiver thread = new ServerReceiver(socket); //오는지 안오는지 확인하기 위해서
                thread.start(); //실행(계속해서 실행)
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    } //start()

    void sendToAll(String msg) {
        Iterator it = clients.keySet().iterator(); // 위치값, 포인터, 처음 시작점
        //클라이언트가 추가될 때마다 생성되며 클라이언트의 입력을 서버에 접속된 모든 클라이언트에게 전송

        while (it.hasNext()) {
            try {
                DataOutputStream out = (DataOutputStream)clients.get(it.next()); // 그사람이 쓴 걸 가져오겠당...
                out.writeUTF(msg);
            } catch (IOException e) {
            }
        }//while
    }//sendToAll


    public static void main(String args[]){
        new MultiServer().start();
    }

    class ServerReceiver extends Thread{
        Socket socket;
        DataInputStream in; //들어오는 것을 받고
        DataOutputStream out; //나가는 것을 관리

        ServerReceiver(Socket socket) {
            this.socket = socket;
            try{
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream()); //클라이언트로 보내주는 거
            }catch (IOException e){
            }
        }

        public void run(){ //스레드는 start하면 무조건 run으로 온다.
            String name = "";

            try{
                name = in.readUTF(); //지금 데이터가 들어와 있는 것을 name에 저장
                sendToAll("#" +name+ "님이 들어오셨습니다");


                clients.put(name, out);
                //클라이언트가 새로 추가되었을 때 클라이언트의 이름을 Key로 클라이언트의 출력스트림을
                //hashmap인 clients에 저장해서 다른 클라이언트가 입력한 데이터를 전송하는데 사용하는 것을 알 수 있음
                System.out.println("현재 서버접속자 수는" + clients.size() + "입니다.");


                while(in != null){
                    sendToAll(in.readUTF());
                }
            }catch (IOException e){
                //ignore
            }finally {
                sendToAll("#" + name + "님이 나가셨습니다.");
                clients.remove(name); //나가면 client 삭제
                System.out.println("[" + socket.getInetAddress() + ":" +
                        socket.getPort() +"]" + "에서 접속을 종료하였습니다.");
                System.out.println("현재 서버 접속자 수는 " + clients.size() + "입니다. ");
            }//try
        }//run
    }//RecevierThread
}//class
