package com.backendboys.battlerace.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class UDPClient implements Runnable {

    private final String hostname;
    private final int port;
    private DatagramSocket socket;

    private final List<IPacketListener> listeners = new ArrayList<>();

    public UDPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void addListener(IPacketListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IPacketListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[548];

        try {
            socket = new DatagramSocket();

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                System.out.println("message");
                String message = new String(request.getData(), 0, request.getLength());
                System.out.println(message);
                notifyGotPacket(message);
            }

        } catch (IOException e) {
            notifyErrorOccurred(e.getMessage());
        }
    }

    public void sendPacket(String message) {
        try {
            byte[] byteMsg = message.getBytes();

            DatagramPacket packet = new DatagramPacket(byteMsg, 0, byteMsg.length, InetAddress.getByName(hostname), port);
            socket.send(packet);

        } catch (Exception e) {
            notifyErrorOccurred(e.getMessage());
        }
    }

    public int getListeningPort() {
        System.out.println(socket.getLocalAddress());
        return socket.getLocalPort();
    }

    private void notifyGotPacket(String msg) {
        for (IPacketListener l : listeners) {
            l.gotPacket(msg);
        }
    }

    private void notifyErrorOccurred(String msg) {
        for (IPacketListener l : listeners) {
            l.UDPErrorOccurred(msg);
        }
    }

}
