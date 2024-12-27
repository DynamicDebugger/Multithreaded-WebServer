import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer(){
        // return new Consumer<Socket>(){
        //     @Override
        //     void accept(Socket clientSocket){
        //         try {
        //             PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
        //             toClient.println("Hello from client");
        //             toClient.close(); 
        //             clientSocket.close();
        //         } catch (IOException e) {
        //             e.printStackTrace();
        //         }             
        //     }
        // };
        
        return (clientSocket ->{
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from client");
                toClient.close(); 
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public static void main(String[] args) {
        Server server = new Server();
        int port = 8010;
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
