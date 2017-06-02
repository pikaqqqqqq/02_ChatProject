import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MyChatServer implements ActionListener {
    ServerSocket ss = null;
    List<Client> clients = new ArrayList<Client>();
    private JFrame serverJFrame = null;
    private JLabel ipJLabel = null;
    private JLabel portJLabel = null;
    private JTextField portJTextField = null;
    private JTextField ipJTextField = null;
    private JTextArea inTextArea = new JTextArea();
    private JTextArea contentJTextArea = new JTextArea();
    private JButton connectJButton = null;
    private JButton sentJButton = null;
    private JScrollPane inScroll = new JScrollPane(inTextArea);
    private JScrollPane contentScroll = new JScrollPane(contentJTextArea);

    public static void main(String[] args) {
        new MyChatServer();
    }

    //构造函数实现界面
    public MyChatServer() {

        //BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
        //org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

        //UIManager.put("RootPane.setupButtonVisible", false);

        serverJFrame = new JFrame("Server");
        serverJFrame.setBounds(391, 90, 588, 563);
        serverJFrame.setLayout(null);

        connectJButton = new JButton("等待连接");
        //connectJButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        connectJButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        connectJButton.setForeground(Color.WHITE);
        connectJButton.addActionListener(this);

        sentJButton = new JButton("发  送");
        //sentJButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        sentJButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        sentJButton.setForeground(Color.WHITE);
        sentJButton.addActionListener(this);

        ipJLabel = new JLabel("    本机ip：");
        ipJLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        ipJTextField = new JTextField(15);
        //ipJTextField.setText(InetAddress.getLocalHost().getHostAddress());
        ipJTextField.setFont(new Font("微软雅黑", Font.BOLD, 14));

        portJLabel = new JLabel("本机端口：");
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

        serverJFrame.add(jPanel);
        serverJFrame.add(inScroll);
        serverJFrame.add(connectJButton);
        serverJFrame.add(contentScroll);
        serverJFrame.add(sentJButton);

        contentJTextArea.setEditable(false);
        sentJButton.setBounds(450, 475, 80, 30);
        contentScroll.setBounds(30, 100, 510, 200);
        inScroll.setBounds(30, 320, 510, 140);
        inScroll.setBorder(new LineBorder(new Color(68, 109, 153), 2, false));
        contentScroll.setBorder(new LineBorder(new Color(68, 109, 153), 2, false));
        connectJButton.setBounds(450, 30, 80, 30);
        jPanel.setBounds(30, 15, 250, 67);

        serverJFrame.setVisible(true);

        serverJFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                System.exit(0);
            }
        });

    }

    //Myserver为实现ActionListener借口重写actionPerformed方法，用于对各个button的监听
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == connectJButton) {
            Start start = new Start();
            Thread thread = new Thread(start);
            thread.start();

        }

        if (e.getSource() == sentJButton) {
            String str = inTextArea.getText().trim();
            String contentStr = "服务器说 :" + str;
            inTextArea.setText("");
            if (!str.trim().equals("")) {
                contentJTextArea.setText(contentJTextArea.getText() + contentStr + '\n');

                for (int i = 0; i < clients.size(); i++) {
                    Client c = clients.get(i);
                    c.sent(contentStr);
                }
            }
        }
    }

    //内部类实现Runnable接口，用于等待客户端的连接
    private class Start implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (!portJTextField.getText().trim().equals("")) {
                contentJTextArea.setText(contentJTextArea.getText() + "等待连接..." + '\n');
                try {
                    ss = new ServerSocket(Integer.valueOf(portJTextField.getText()).intValue());// 创建一个Socket服务器，监听5566端口
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                int num = 0;
                try {
                    while (true) { // 利用死循环不停的监听端口
                        Socket s;
                        s = ss.accept();// 利用Socket服务器的accept()方法获取客户端Socket对象。

                        num++;
                        System.out.println("第" + num + "个客户端成功连接！");
                        contentJTextArea.setText(contentJTextArea.getText() + "第" + num + "个客户端成功连接！" + '\n');
                        Client c = new Client(num, s); // 创建客户端处理线程对象
                        Thread t = new Thread(c); // 创建客户端处理线程
                        t.start(); // 启动线程
                        clients.add(c);

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    try {
                        ss.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                contentJTextArea.setText(contentJTextArea.getText() + "请输入端口号！" + '\n');
            }
        }
    }

    //内部类实现Runnable接口，用于接收和发送服务器收到的信息
    private class Client implements Runnable {
        int clientIndex = 0; // 保存客户端id
        Socket s = null; // 保存客户端Socket对象
        BufferedReader in = null;
        PrintWriter out = null;

        public Client(int num, Socket s) {
            clientIndex = num;
            this.s = s;
            try {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new PrintWriter(s.getOutputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void sent(String str) {
            out.println(str);
            out.flush();
            System.out.println("sent");
        }

        public void run() {
            try {
                while (true) {
                    String boolStr = in.readLine();
                    String sendStr = clientIndex + " has sent: " + boolStr;
                    for (int i = 0; i < clients.size(); i++) {
                        Client c = clients.get(i);
                        if (boolStr != null) {
                            if (clientIndex != (i + 1))
                                c.sent(sendStr);
                        }
                    }
                    System.out.println(sendStr);
                    contentJTextArea.setText(contentJTextArea.getText() + sendStr + '\n');
                    if (boolStr.equals("end"))
                        break;
                }
                in.close();
                out.close();
                s.close();
            } catch (Exception e) {
                System.out.println("Client closed!");
            }
        }
    }
}
