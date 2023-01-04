import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientTest {
    int socketPort = 1111;
    int bufferSize = 1024;

    public void fncClient(){
        Socket socket;

        try{

            //Socket 생성
            socket = new Socket();

            //연결 시도
            socket.connect(new InetSocketAddress("localhost", socketPort));

            //데이터 보내는 부분
            OutputStream output = socket.getOutputStream();

            String msgStr = "test message";
            byte[] bytes = msgStr.getBytes();

            //보내기
            output.write(bytes);
            output.flush();

            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

