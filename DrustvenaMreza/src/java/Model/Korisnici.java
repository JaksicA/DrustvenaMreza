package Model;

/**
 *
 * @author Jaksa
 */
public class Korisnici {
    
    private int id;
    private String ime;
    private String prezime;
    private int godine;
    private String pol;
    private String prijatelji;

    public Korisnici() {
    }

    public Korisnici(int id, String ime, String prezime, int godine, String pol, String prijatelji) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.godine = godine;
        this.pol = pol;
        this.prijatelji = prijatelji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getGodine() {
        return godine;
    }

    public void setGodine(int godine) {
        this.godine = godine;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getPrijatelji() {
        return prijatelji;
    }

    public void setPrijatelji(String prijatelji) {
        this.prijatelji = prijatelji;
    }
    
    
    
}
