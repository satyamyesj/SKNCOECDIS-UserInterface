package User;


import DatabaseAccessObjects.QueryObjects.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserInterface{

    static public String interfaceName="STUDENT_AND_EMPLOYEE_INTERFACE";    
    
    static public InetAddress ipAddress;
    static public int portAddress;
    
    static public Socket socket;
    static public PrintWriter printWriter;
    static public BufferedReader bufferedReader;
    static public ObjectOutputStream objOut;
    static public ObjectInputStream objIn;
    static public DataOutputStream dataOut;
    static public DataInputStream dataIn;
    static public User user;

    public UserInterface() throws UnknownHostException {
        ipAddress=InetAddress.getByName("localhost");
        portAddress=8090;
        
        socket = null;
        printWriter = null;
        bufferedReader = null;
        objOut = null;
        objIn = null;
        dataOut = null;
        dataIn = null;
    }

    public void connectToServer() {
        try {
            UserInterface.socket = new Socket(UserInterface.ipAddress, UserInterface.portAddress);
            UserInterface.printWriter = new PrintWriter(UserInterface.socket.getOutputStream(), true);
            UserInterface.bufferedReader = new BufferedReader(new InputStreamReader(UserInterface.socket.getInputStream()));
            UserInterface.objOut = new ObjectOutputStream(UserInterface.socket.getOutputStream());
            UserInterface.objIn = new ObjectInputStream(UserInterface.socket.getInputStream());
            UserInterface.dataOut = new DataOutputStream(UserInterface.socket.getOutputStream());
            UserInterface.dataIn = new DataInputStream(UserInterface.socket.getInputStream());
        } catch (IOException ex) {
          
        }
    }

    public void closeConnection() throws IOException {
        try {
            UserInterface.objOut.close();
            UserInterface.objIn.close();
            UserInterface.dataIn.close();
            UserInterface.dataOut.close();
            UserInterface.bufferedReader.close();
            UserInterface.printWriter.close();
            UserInterface.socket.close();
        } catch (IOException e) {
           
        }
    }
}
