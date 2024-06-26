package ChatApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//utiliser un client telnet

public class Myserver extends Thread{
    private int numerClient;
    private List<Conversation> clients;

    public static void main(String[] args) {
        new Myserver().start(); // va directement appeler la methode run

        /////////////////////////////////////////////
        //                                         //
        //     suite de l'application              //
        //        System.out.println("suite ");    //
        //                                         //
        /////////////////////////////////////////////
    }

    @Override
    public void run() {
        try {
            ServerSocket ss=new ServerSocket(1234); // le serveur est demarer sur le port 1234
            System.out.println("Demmarage du serveur ....");
            clients = new ArrayList<>();

            //apres il va attendre serveur socket.accept() une connxion i.e. une instruction blockante
            //creation des socket

            while(true){
                Socket socket=ss.accept();//attend un client se connecte
                ++numerClient;
                //new Conversation(socket, numerClient).start()

                Conversation conversation= new Conversation(socket, numerClient); // apres on cree l'object convesation qui
                conversation.start();
                clients.add(conversation);

                // nous permet de cree un nouveau thread
                /// start() il fait executer directement run()
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    class Conversation extends Thread{  //q chaque fois il y a une conexion, on cree un nouveau thread
        //chaqu'un a ses propre streams de communication independament des autres
        private Socket socket;
        private int num;
        public Conversation(Socket socket, int num) {
            this.socket=socket; this.num=num;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);

                OutputStream os=socket.getOutputStream();
                PrintWriter pw=new PrintWriter(os, true);
                String ipClient=socket.getRemoteSocketAddress().toString();
                /////////////////////////////////////////////////////////////

                System.out.println("Connexion du client numero : "+ num+ " IP= "+ipClient.toString());
                //pw.flush();
                pw.println("Bien venue vous etes le client numero "+ num);


                //communication client serveur
                while(true){
                    //System.out.println("Deviner le secret numero : ");

                    String req = br.readLine();
                    System.out.println("Le client "+ipClient+"a envoyer une requete :"+ req+ " ");
                    BroadCast(req, socket);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void BroadCast(String req, Socket socket) {
            try {
                for (Conversation client:clients){
                    if (client.socket != socket){
                        PrintWriter printWriter=new PrintWriter(client.socket.getOutputStream(), true);
                        printWriter.println(req);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
