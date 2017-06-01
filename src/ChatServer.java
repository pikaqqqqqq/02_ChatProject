import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by think on 2017/6/1.
 */
public class ChatServer {

    //1.05创建server端
    public static void main(String[] args){
        boolean started = false;
        try {
            ServerSocket ss = new ServerSocket(8888);
            started = true;
            while (started){
                boolean bConnected = false;
                Socket s = ss.accept();
System.out.println("a client connected");
                bConnected = true;
                //1.07接收
                DataInputStream dis = new DataInputStream(s.getInputStream());
                while (bConnected){
                    String str = dis.readUTF();
                    System.out.println(str);
                }
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //在一个项目中处理Exception也是很重要的
        }
    }


}
