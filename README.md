# 02_chatProject//TCP

* 1.02 创建一个窗口和输入框显示框
* 1.03 添加窗口关闭的三种方法：并排的监听器类、内部类、匿名类 
```java
关键代码：
       System.exit(0);
```
* 1.04 利用内部类相应 TextField 的回车监听，继承ActionListener的借口，重写ActionPerformed方法
* 1.05  创建server端 
```Java
关键代码：
       ServerSocket ss = new ServerSocket(8888);//服务端监听8888端口
       Socket s = ss.accept();
       //服务器端接收客户端，通过s.getInetAddress();方法可以拿到其ip，通过s.getPort();拿到其端口
```
* 1.06连接Server端
```java
关键代码：
       Socket s = new Socket("127.0.0.1",8888);//通过服务器IP和端口找到服务器，申请访问
```
* 1.07完成客户端与服务端的通话
```java
关键代码：
       *具体去了解DataOutputStream与DataInputStream*
```
