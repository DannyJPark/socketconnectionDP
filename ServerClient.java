package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
 
public class ServerClient {
    Socket socket; // 컴퓨터와 네트워크 통신을 위함
 
    public ServerClient(Socket socket) {// 생성자
        this.socket = socket;
        receive();
    }// client()
 
    /* 클라이언트로부터 메세지를 전달받는 메소드 */
    public void receive() {
        Runnable thread = new Runnable() {// 스레드 생성
 
            @Override
            public void run() {
                try {
                    while (true) {
                        /* 메세지 받기 위해 intPutStream */
                        InputStream in = socket.getInputStream();
                        byte[] buffer = new byte[512]; // 한번에 512byte만큼 전달 받을 수 있게 설정
                        int length = in.read(buffer);
                        while (length == -1)
                            throw new IOException();// 오류 발생 처리
                        /* getRemoteSocketAddress(): 현재 접속한 클라이언트의 아이피 주소와 같은 주소를 GET */
                        
                        //ServerMain.textArea.appendText(message);
                        System.out.println("[메세지 수신 성공]" + socket.getRemoteSocketAddress() + ":"
                                + Thread.currentThread().getName());
                        /* 전달 받은 값을 한글도 가능하도록 설정 */
                        String message = new String(buffer, 0, length, "UTF-8");
                        /* 전달 받은 메세지를 다른 클라이언트로 보낼 수 있도록 설정 */
                        for (ServerClient client : ServerMain.clients) {
                            client.send(message);
                        }
                    }
                } catch (Exception e) {
                    try {
                        System.out.println("[클라이언트 접속 종료]" + socket.getRemoteSocketAddress() + ":"
                                + Thread.currentThread().getName());
                        ServerMain.clients.remove(ServerClient.this);
                        socket.close();    
                    } catch (Exception e2) {// 오류가 발생 했을때 더욱 더 상세히 처리
                        e2.printStackTrace();
                    }
                }
            }
        };
        ServerMain.threadPool.submit(thread);// 만들어진 thread를 threadPool에 보낸다
    }// receive()
 
    /* 클라이어트로 메세지를 보내는 메소드 */
    public void send(String Message) {
        Runnable thread = new Runnable() {
 
            @Override
            public void run() {
                try {
                    /* 메세지를 보내기 위해 OutPutStream */
                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = Message.getBytes("UTF-8");
                    /* 메세지 송신에 성공하였다면 buffer 담긴 내용을 서버로 보내고, flush를 통해 성공된 부분까지 알려줌 */
                    out.write(buffer);
                    out.flush();
                } catch (Exception e) {
                    try {
                        System.out.println("[메세지 송신 오류]" + socket.getRemoteSocketAddress() + ":"
                                + Thread.currentThread().getName());
                        /* 오류가 발생했을때 해당 클라이언트를 제거 */
                        //ServerMain.clients.remove(ServerClient.this);
                        //socket.close();// 닫아버림
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
 
            }
        };
        ServerMain.threadPool.submit(thread);
    }// send()
}// client{}
 