import fr.dgac.ivy.*;
import fr.irit.ens.$1reco.Stroke;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;

public class Reconnaissance {
    private static Stroke s;
//    private static Dictionnaire dico;

    public static void main (String[] args) throws IOException {

        File fichier =  new File("./dictionnaire.ser") ;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
            // désérialization de l'objet
            Dictionnaire dico = (Dictionnaire) ois.readObject();
            System.out.println(dico);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // dico.toString();
        // System.out.println(dico);

        final Ivy bus = new Ivy("test", "Hey", new IvyApplicationListener(){

            public void connect(IvyClient client) {

            }

            public void disconnect(IvyClient client) {

            }

            public void die(IvyClient client, int id, String msgarg) {

            }

            public void directMessage(IvyClient client, int id, String msgarg) {

            }
        });



        try {
            bus.start("127.255.255.255:2010");
            bus.sendToSelf(true);

            bus.bindMsg("Palette:MousePressed x=(.*) y=(.*)", new IvyMessageListener() {
                public void receive(IvyClient client, String[] args) {
                    try {
                        s = new Stroke();
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        s.addPoint(x, y);
                        bus.sendMsg("Palette:CreerEllipse x="+args[0]+" y="+args[1] +" longueur=4 hauteur=4 couleurFond=green couleurContour=green");
                    } catch (IvyException e) {
                        e.printStackTrace();
                    }
                }
            });

            bus.bindMsg("Palette:MouseReleased x=(.*) y=(.*)", new IvyMessageListener() {
                public void receive(IvyClient client, String[] args) {
                    try {
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        s.addPoint(x, y);
                        bus.sendMsg("Palette:CreerEllipse x="+args[0]+" y="+args[1] +" longueur=4 hauteur=4 couleurFond=red couleurContour=red");
                        s.normalize();
                        for (int i = 0; i < s.getPoints().size(); i++){
                            bus.sendMsg("Palette:CreerEllipse x="+String.valueOf((int)s.getPoints().get(i).getX())+" y="+String.valueOf((int)s.getPoints().get(i).getY()) +" longueur=4 hauteur=4 couleurFond=blue couleurContour=blue");
                        }

                    } catch (IvyException e) {
                        e.printStackTrace();
                    }
                }
            });
//
            bus.bindMsg("Palette:MouseDragged x=(.*) y=(.*)", new IvyMessageListener() {
                public void receive(IvyClient client, String[] args) {
                    try {
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        s.addPoint(x, y);
                        bus.sendMsg("Palette:CreerEllipse x="+args[0]+" y="+args[1] +" longueur=4 hauteur=4 couleurFond=grey couleurContour=grey");
                    } catch (IvyException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception ie) {
            ie.printStackTrace();
        }
    }
}
