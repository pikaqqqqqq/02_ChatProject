import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by think on 2017/6/1.
 */
public class ChatServer {
    //1.05创建server端
    public static void main(String[] args){
        try {
            ServerSocket ss = new ServerSocket(8888);
            while (true){
                Socket s = ss.accept();
System.out.println("a client connected");

            }
        } catch (IOException e) {
            e.printStackTrace();
            //在一个项目中处理Exception也是很重要的
        }
    }
}
