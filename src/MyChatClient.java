import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyChatClient implements ActionListener {
    Socket s = null;
    PrintWriter out = null;
    BufferedReader in = null;
    boolean b = false;
    Thread getThread = new Thread(new GetClient());
    private JFrame clientJFrame = null;
    private JButton connectJButton = null;
    private JButton sentJButton = null;
    private JLabel ipJLabel = null;
    private JTextField ipJTextField = null;
    private JLabel portJLabel = null;
    private JTextField portJTextField = null;
    private JTextArea contentJTextArea = new JTextArea();
    private JTextArea inTextArea = new JTextArea();
    private JScrollPane inScroll = new JScrollPane(inTextArea);
    private JScrollPane contentScroll = new JScrollPane(contentJTextArea);

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new MyChatClient();
    }

    //在构造函数中做界面
    public MyChatClient() {

        try {


            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

            UIManager.put("RootPane.setupButtonVisible", false);

            clientJFrame = new JFrame("Client");
            clientJFrame.setBounds(391, 90, 588, 563);
            clientJFrame.setLayout(null);

            connectJButton = new JButton("连接");
            connectJButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
            connectJButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            connectJButton.setForeground(Color.WHITE);
            connectJButton.addActionListener(this);

            sentJButton = new JButton("发  送");
            sentJButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
            sentJButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            sentJButton.setForeground(Color.WHITE);
            sentJButton.addActionListener(this);

            ipJLabel = new JLabel("    服务器ip：");
            ipJLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
            ipJTextField = new JTextField(15);
            ipJTextField.setFont(new Font("微软雅黑", Font.BOLD, 14));

            portJLabel = new JLabel("服务器port：");
            portJLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
            portJTextField = new JTextField(15);
            portJTextField.setFont(new Font("微软雅黑", Font.BOLD, 14));

            JPanel idjPanel = new JPanel();
            idjPanel.setLayout(new BorderLayout(10, 0));
            idjPanel.add(ipJLabel, BorderLayout.WEST);
            idjPanel.add(ipJTextField, BorderLayout.CENTER);

            JPanel passwordjPanel = new JPanel();
            passwordjPanel.setLayout(new BorderLayout(10, 0));
            passwordjPanel.add(portJLabel, BorderLayout.WEST);
            passwordjPanel.add(portJTextField, BorderLayout.CENTER);

            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout(10, 10));
            jPanel.add(idjPanel, BorderLayout.NORTH);
            jPanel.add(passwordjPanel, BorderLayout.CENTER);

            clientJFrame.add(jPanel);
            clientJFrame.add(contentScroll);
            clientJFrame.add(connectJButton);
            clientJFrame.add(inScroll);
            clientJFrame.add(sentJButton);

            contentJTextArea.setEditable(false);
            inScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            inScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            sentJButton.setBounds(450, 475, 80, 30);
            connectJButton.setBounds(450, 30, 80, 30);
            contentScroll.setBounds(30, 100, 510, 200);
            contentScroll.setBorder(new LineBorder(new Color(68, 109, 153), 2, false));
            inScroll.setBounds(30, 320, 510, 140);
            inScroll.setBorder(new LineBorder(new Color(68, 109, 153), 2, false));
            jPanel.setBounds(30, 15, 250, 67);

            clientJFrame.setVisible(true);

            clientJFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent arg0) {
                    cutConnect();
                    System.exit(0);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //连接到服务器的方法
    public void connect(String _ip, String _port) {
        if (!_ip.trim().equals("")) {
            if (!_port.trim().equals("")) {
                try {
                    s = new Socket(_ip, Integer.valueOf(_port).intValue());
                    contentJTextArea.setText("连接成功！" + '\n');
                    b = true;
                    out = new PrintWriter(s.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                } catch (Exception e) {
                    contentJTextArea.setText(contentJTextArea.getText() + "连接失败！可能是服务器没有启动或者ip、端口和服务器不一致。" + '\n');
                    System.out.println(e.getMessage());
                }
            } else {
                contentJTextArea.setText(contentJTextArea.getText() + "请输入服务器port！" + '\n');
            }
        } else {
            if (!_port.trim().equals("")) {
                contentJTextArea.setText(contentJTextArea.getText() + "请输入服务器ip！" + '\n');
            } else {
                contentJTextArea.setText(contentJTextArea.getText() + "请输入服务器ip和port！" + '\n');
            }
        }
    }

    //断开连接的方法
    public void cutConnect() {
        try {
            out.close(); // 关闭输出流
            s.close(); // 关闭Socket连接
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    //MyClient为实现ActionListener借口重写actionPerformed方法，用于对各个button的监听
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectJButton) {
            if (!b) {
                connect(ipJTextField.getText(), portJTextField.getText());
                getThread.start();
            } else {
                contentJTextArea.setText(contentJTextArea.getText() + "已连接服务器！不要再点我啦啦啦啦！！" + '\n');
            }
        }

        if (e.getSource() == sentJButton) {
            String str = inTextArea.getText().trim();
            inTextArea.setText("");
            if (!str.trim().equals("")) {
                contentJTextArea.setText(contentJTextArea.getText() + "I send：" + str + '\n');
                if (b) {
                    out.println(str);
                    out.flush();
                } else {
                    contentJTextArea.setText(contentJTextArea.getText() + "没有连接服务器！" + '\n');
                }
            }
        }
    }

    //接收服务器端发过来的信息
    private class GetClient implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    String str;
                    str = in.readLine();
                    contentJTextArea.setText(contentJTextArea.getText() + str + '\n');
                    System.out.println("get" + str);
                    if (str.equals("end"))
                        break;
                }
                in.close();
            } catch (Exception e) {

            }
        }
    }
}
