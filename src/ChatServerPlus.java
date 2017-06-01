import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by think on 2017/6/1.
 */
public class ChatServerPlus {
    boolean started = false;
    ServerSocket ss = null;

    public static void main(String[] args){
        new ChatServerPlus().start();
        //静态的方法里不能new一个内部类,内部类需要通过包含这个内部类的类去访问
    }

    public void start(){
        try {
            ss = new ServerSocket(8888);
            started = true;
        }catch (BindException e){
            System.out.println("端口被占用！！请关闭该程序或者修改端口。");
            System.exit(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (started) {
                Socket s = ss.accept();//accept()是一个阻塞性的方法
                Client c = new Client(s);
                System.out.println("a client connected");
                new Thread(c).start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //1.10连接多个客户端,使用单独的线程,每一个客户端都需要一个线程去处理
    private class Client implements Runnable{//常见的名字
        private Socket s = null;
        private DataInputStream dis = null;
        private boolean bConnected = false;
        public Client(Socket s){
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (bConnected){
                    String str = dis.readUTF();
                    System.out.println(str);
                }
            }catch (EOFException e) {
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
}
