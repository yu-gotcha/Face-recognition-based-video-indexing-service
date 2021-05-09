package kr.ac.sejong.api.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

@Service
public class SocketServiceImpl {
    public static final int MESSAGE_MAXIMUM_LENGTH = 100;

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public boolean isConnected() {return this.socket.isConnected();}

    public boolean connect(String ip, int port){
        this.socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(ip, port);

        try{
            this.socket.connect(socketAddress);
            this.inputStream = this.socket.getInputStream();
            this.outputStream = this.socket.getOutputStream();
        }catch (IOException e) {return false;}

        return true;
    }

    public boolean sendMessage(String message){
        if(!this.socket.isConnected()) return false;

        try {
            this.outputStream.write(message.getBytes());
        } catch (IOException e) { return false; }

        return true;
    }

    public String receiveMessage() {
        if(!this.socket.isConnected()) return null;

        byte bytes[] = new byte[MESSAGE_MAXIMUM_LENGTH];
        int bytesLength;

        try {
            if ((bytesLength = this.inputStream.read(bytes, 0, MESSAGE_MAXIMUM_LENGTH)) == 0) return null;
        } catch (IOException e) { return null; }

        return new String(bytes, 0, bytesLength);
    }

    public void disconnect() {
        if (this.socket.isClosed()) return;

        try {
            this.socket.close();
        } catch (IOException ignored) { }
    }
}
