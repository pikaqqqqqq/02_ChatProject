# 02_ChatProject//TCP

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
* 1.07完成客户端与服务端的通话，
```
***具体去了解DataOutputStream与DataInputStream***
```
* 1.09&1.091处理Exception
```
java.io.EOFException(end of file Exception)文件读取结束（常见的Exception)
BindException端口占用
```
* 1.10连接多个客户端，使用单独的线程,每一个客户端都需要一个线程去处理
```
这里是为了使用线程而使用线程，除此之外还有更好的方法去解决一边监听接收客户端，一边处理客户端信息的问题————异步的模型

***具体了解异步模型,什么是异步式的网络编程、异步io、Io完成接口??***
```
* 1.13以前写过的chat系统，对比两种写法的不同
```
使用的流不同，造成了乱码问题
```
