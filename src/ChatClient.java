import java.awt.*;

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
        setVisible(true);
        add(taContent,BorderLayout.NORTH);
        add(tfTxt,BorderLayout.SOUTH);
        System.out.print("creat branch1");
    }

}
