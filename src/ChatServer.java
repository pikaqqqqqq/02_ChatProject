import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by think on 2017/6/1.
 */
public class ChatServer {

    //1.05创建server端
    public static void main(String[] args) {
        boolean started = false;
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream dis = null;

        try {
            ss = new ServerSocket(8888);
        }catch (BindException e){
            System.out.println("端口被占用！！");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            started = true;
            while (started) {
                boolean bConnected = false;
                s = ss.accept();
                System.out.println("a client connected");
                bConnected = true;
                //1.07接收
                dis = new DataInputStream(s.getInputStream());
                while (bConnected) {
                    //1.09java.io.EOFException(end of file Exception)文件读取结束（常见的Exception）
                    String str = dis.readUTF();
                    System.out.println(str);
                }
            }
        } catch (EOFException e) {
            //e.printStackTrace();
            System.out.println("client have been closed");
            //在一个项目中处理Exception也是很重要的
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                //1.09处理server端Exception的一般解决方法
                if (dis != null) dis.close();
                if (s != null) s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
