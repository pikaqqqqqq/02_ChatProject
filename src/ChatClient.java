import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by think on 2017/5/29.
 */
public class ChatClient extends Frame {

    TextArea taContent = new TextArea();
    TextField tfTxt = new TextField();

    public static void main(String[] args){
        new ChatClient().launchFrame();
    }

    public void launchFrame(){
        setSize(500,500);
        setLocation(500,150);
        add(taContent,BorderLayout.NORTH);
        add(tfTxt,BorderLayout.SOUTH);
        this.pack();
        //添加窗口关闭的三种方法：并排的监听器类、内部类、匿名类
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);

        System.out.print("creat branch1");
    }


}
