package kr.ac.sejong.api.service;

public interface SocketService {
    boolean isConnected();

    boolean connect(String ip, int port);

    boolean sendMessage(String message);

    String receiveMessage();

    void disconnect();
}
