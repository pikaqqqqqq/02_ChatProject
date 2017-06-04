# 02_chatProject

* 1.02 创建一个窗口和输入框显示框
* 1.03 添加窗口关闭的三种方法：并排的监听器类、内部类、匿名类 
  关键代码：System.exit(0);
* 1.04 利用内部类相应 TextField 的回车监听，继承ActionListener的借口，重写ActionPerformed方法
* 1.05  创建server端
  关键代码：
           ServerSocket ss = new ServerSocket(8888);//服务端监听8888端口
           Socket s = ss.accept();
  
