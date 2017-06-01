import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by think on 2017/5/29.
 */
public class ChatClient extends Frame {

    Socket s;
    DataOutputStream dos;
    TextArea taContent = new TextArea();
    TextField tfTxt = new TextField();

    public static void main(String[] args) {
        new ChatClient().launchFrame();
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
    }

    //1.06连接Server端
    public void connect() {
        try {
            s = new Socket("127.0.0.1", 8888);
            dos = new DataOutputStream(s.getOutputStream());
            System.out.println("connected");
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

    public void disConnect(){
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //1.04添加回车监听，完成文字传送
    private class TFListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTxt.getText().trim();
            taContent.setText(str);
            tfTxt.setText("");
            sent(str);
        }
    }

}
