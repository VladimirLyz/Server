package com.server;

import com.server.User.GoogleUser;
import com.server.User.SimpleUser;
import com.server.User.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class Server {

    private ServerSocket serverSocket;
    PrintStream logger;
    Getter getter;
    TreeMap<Integer, Photo> photos = new TreeMap<>();
    TreeMap<Integer, User> users = new TreeMap<>();

    BufferedImage getFullImage(int hash)
    {
        Photo q = photos.getOrDefault(hash, null);
        if(q == null) return null;
        return q.getImage();
    }

    int getHashImage(Photo p)
    {
        return p.getImage().hashCode();
    }

    void solveRequest(Socket socket) throws IOException {
        Scanner socketScanner = new Scanner(socket.getInputStream());
        PrintWriter socketWriter = new PrintWriter(socket.getOutputStream());
        String req = socketScanner.next();
        logger.println("Server getted: " + req);
        switch (req)
        {
            case "getFullImage": {
                int hash = socketScanner.nextInt();

                // check it
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(getFullImage(hash), "jpg", baos);
                baos.flush();
                byte[] arr = baos.toByteArray();
                //

                socket.getOutputStream().write(arr);
                socket.getOutputStream().flush();
                break;
            }
            /*case "getHashImage": {
                break;
            }*/
            case "signUp": {
                String method = socketScanner.next();
                if(method.equals("google"))
                {
                    GoogleUser t = new GoogleUser(socketScanner.next());
                    if(users.getOrDefault(t.getHashToken(), null) != null) {
                        logger.println("Google User with token: " + t.getToken() + " already registered!");
                        break;
                    }
                    users.put(t.getHashToken(), t);
                    logger.println("Google User with token: " + t.getToken() + " registered!");
                }else if(method.equals("simple"))
                {
                    SimpleUser user = new SimpleUser(socketScanner.next(), socketScanner.next());
                    if(users.getOrDefault(user.getHashLogin(), null) != null) {
                        logger.println("Simple User with login " + user.getLogin() + " already registered!");
                        break;
                    }
                    users.put(user.getHashLogin(), user);
                    logger.println("Simple User with login " + user.getLogin() + " registered!");
                }
                break;
            }
            case "addPhoto": {
                //check it
                BufferedImage img = ImageIO.read(socket.getInputStream());
                Photo p = null;
                p = new Photo(img, 0, users.get(socketScanner.next().hashCode()));
                photos.put(p.getHash(), p);
                break;
            }
            case "getPhotoToCompare" : {
                break;
            }
            case "changePhotoRating": {
                break;
            }
            case "getPhotoRating":{
                break;
            }
            default: {
                logger.println("Incorrect request: " + req);
                break;
            }
        }
        socketScanner.close();
        socketWriter.close();
    }

    class Getter extends Thread {
        @Override
        public void run() {
            logger.println("Server started");
            while(!isInterrupted())
            {
                try {
                    Socket socket = serverSocket.accept();
                    solveRequest(socket);
                    socket.close();
                } catch (IOException e) {
                    if(!(e instanceof SocketException))
                        e.printStackTrace();
                }
            }
        }
    }

    Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        logger = System.out;
    }

    void start() {
        getter = new Getter();
        getter.start();
    }

    void stop() throws IOException {
        getter.interrupt();
        serverSocket.close();
    }
}
