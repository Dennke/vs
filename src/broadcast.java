import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.NoSuchElementException;

public class broadcast {

    final static int PORT = 8080;
    final static String IPADDRESS = "192.168.178.42";

    public static void main(String[] args) throws IOException, InterruptedException {
        broadcast(IPADDRESS, PORT);
    }

    public static void broadcast(String ipAddress, int port) throws IOException, InterruptedException {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);
        String date = new Date().toString();
        DatagramPacket packet = new DatagramPacket(date.getBytes(), date.length(), getBroadcastAddress(ipAddress), port);
        System.out.println("Sending a broadcast every 20 seconds");
        while(true){
            date = new Date().toString();
            packet.setData(date.getBytes());
            packet.setLength(date.length());
            socket.send(packet);
            Thread.sleep(20000);
        }
    }


    public static InetAddress getBroadcastAddress(String ipAddress) throws UnknownHostException, SocketException {
        InetAddress address = InetAddress.getByName(ipAddress);
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(address);
        for(InterfaceAddress add : networkInterface.getInterfaceAddresses()){
            if(add.getBroadcast() == null)
                continue;
            return (add.getBroadcast());
        }
        throw new NoSuchElementException("Couldn't find a broadcast address");
    }
}
