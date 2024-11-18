package activeRecord;

public class Personne {

    private int id;
    private String nom, prenom;

    public Personne(String n, String p) {
        this.nom = n;
        this.prenom = p;
        this.id = -1;
    }
}
