import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by think on 2017/6/2.
 */
public class ChatClientPlus extends Frame {


    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;

    TextArea taContent = new TextArea();
    TextField tfTxt = new TextField();

    Thread tRecv = new Thread(new RecvThread());

    public static void main(String[] args) {
        new ChatClientPlus().launchFrame();
    }

    public void launchFrame() {
        setSize(500, 500);
        setLocation(500, 150);
        add(taContent, BorderLayout.NORTH);
        add(tfTxt, BorderLayout.SOUTH);
        this.pack();
        //添加窗口关闭的三种方法：并排的监听器类、内部类、匿名类
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disConnect();
                System.exit(0);
            }
        });
        tfTxt.addActionListener(new TFListener());
        setVisible(true);
        connect();

        tRecv.start();
    }

    //1.06连接Server端
    public void connect() {
        try {
            s = new Socket("127.0.0.1", 8888);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            System.out.println("connected");
            bConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //1.07发送
    public void sent(String str) {
        try {

            dos.writeUTF(str);
            dos.flush();
            //dos.close();//有bug，先去锻炼回来再改
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void disConnect() {

        try {
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try {
            bConnected = false;
            tRecv.join();//线程停止：最先考虑join()方法
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            try {
                dos.close();
                dis.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        */

    }

    //1.04添加回车监听，完成文字传送
    private class TFListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();
            //taContent.setText(str);
            tfTxt.setText("");
            sent(str);
        }
    }

    //客户端接收来自服务端的消息
    private class RecvThread implements Runnable {

        @Override
        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    //System.out.println("recv: " + str);
                    taContent.setText(taContent.getText() + str + '\n');
                }
            } catch (SocketException e) {
                System.out.println("退出了！！Bye");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
