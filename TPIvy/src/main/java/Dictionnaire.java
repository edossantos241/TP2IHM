import fr.irit.ens.$1reco.Stroke;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Dictionnaire extends HashMap<Stroke, String> implements Serializable {

    private Stroke s;
    private String nom;

    public Dictionnaire() {

    }

    public void add(Stroke s, String nom) {
        this.put(s, nom);
    }

    public String toString(){
        for(Map.Entry mapentry : this.entrySet()){
            System.out.println("Key = "+mapentry.getKey()+" Valeur = "+mapentry.getValue());
        }
        return null;
    }
}