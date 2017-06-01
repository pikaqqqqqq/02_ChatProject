import java.awt.*;

/**
 * Created by think on 2017/5/29.
 */
public class ChatClient extends Frame {

    public static void main(String[] args){
        new ChatClient().launchFrame();
    }

    public void launchFrame(){
        setSize(500,500);
        setLocation(500,150);
        setVisible(true);
        System.out.print("creat branch1");
    }

}
