package ChatApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


//utiliser un client telnet

public class Myserver extends Thread{
    private int numerClient;
    
    private int secretNum;

    public static void main(String[] args) {
        new Myserver().start(); // va directement appelet la methode run

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
            secretNum = new Random().nextInt(1000);
            System.out.println("Secret serveur number : " + secretNum);

            //apres il va attendre serveur socket.accept() une connxion i.e. une instruction blockante
            //creation des socket

            while(true){
                Socket socket=ss.accept();//attend un client se connecte
                ++numerClient;
                new Conversation(socket, numerClient).start(); // apres on cree l'object convesation qui
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
                    System.out.println("Deviner le secret numero : ");
                    String req = br.readLine();
                    System.out.println("Le client "+ipClient+"a envoyer une requete :"+ req+ " ");
                    int num = 0;  // attender un message | requete
                    boolean test=false;
                    try{
                        num=Integer.parseInt(req);
                        test = true;

                    }catch (Exception e){
                        test = false;

                    }
                    if (test){
                        if (num > secretNum){
                            pw.println("Le tien est SUPERIEUR au secretNum");
                        } else if (num < secretNum) {
                            pw.println("Le tien est INFERIEURE au secretNum");
                        }else {
                            pw.println("BRAVO, vous avez gagne");
                            System.out.println("Le client "+ipClient.toString()+"a touver le secretNum :"+secretNum);
                        }
                    }else {
                        pw.println("Format incorrect");
                    }







                }





            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
