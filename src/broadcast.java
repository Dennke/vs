import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class broadcast {

    final static int PORT = 8080;

    public static void main(String[] args){
        broadcast(PORT);
    }

    public static void broadcast(int port){
        try(DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            String date = new Date().toString();
            DatagramPacket packet = new DatagramPacket(date.getBytes(), date.length(), getBroadcastAddress(), port);
            System.out.println("Sending a broadcast every 20 seconds");
            while (true) {
                date = new Date().toString();
                packet.setData(date.getBytes());
                packet.setLength(date.length());
                socket.send(packet);
                Thread.sleep(20000);
            }
        }
        catch(Exception e){
            System.err.println(e);
            }
    }


    public static InetAddress getBroadcastAddress() throws IOException {
        Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();

        while (interfaceEnumeration.hasMoreElements()) {
            NetworkInterface networkInterface = interfaceEnumeration.nextElement();
            if (!networkInterface.isLoopback() || networkInterface.getMTU() > 0) {
                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    if (interfaceAddress.getBroadcast() != null) {
                        return interfaceAddress.getBroadcast();
                    }
                }
            }
        }
        throw new NoSuchElementException("Couldn't find a broadcast address");
    }
}
