package ihm.tydrichova.upmc.fr.ihmclient.model;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Magdalena on 5/11/2018.
 */

public class Client implements Comparable<Client>{
    private String login;
    private String password;
    private HashMap<Plat, Integer> platsWithNotes;
    public static final Integer DID_NOT_VOTE = -1;

    public Client(String login, String password, HashMap<Plat, Integer> plats){
        this.login = login;
        this.password = password;
        this.platsWithNotes = plats;
    }

    public Client(String login, String password){
        this(login, password, new HashMap<Plat, Integer>());
    }

    public HashMap getPlatsWithNotes(){
        return platsWithNotes;
    }

    public void addPlat(Plat plat, int note){
        platsWithNotes.put(plat, note);
    }

    public void addPlat(Plat plat){
        platsWithNotes.put(plat, DID_NOT_VOTE);
    }

    public String getLogin(){
        return login;
    }

    public boolean passwordCorrect(String password){
        return this.password.equals(password);
    }

    @Override
    public int compareTo(@NonNull Client o) {
        return this.login.compareTo(o.login);
    }
}
