package sercandevops.com.notsepeteekle.data;

public class Notlar {

    private int id;
    private String notIcerik;
    private long notTarih;
    private long notEklenmeTarihi;
    private int tamamlandi;

        public Notlar(){

        }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotIcerik() {
        return notIcerik;
    }

    public void setNotIcerik(String notIcerik) {
        this.notIcerik = notIcerik;
    }

    public long getNotTarih() {
        return notTarih;
    }

    public void setNotTarih(long notTarih) {
        this.notTarih = notTarih;
    }

    public int getTamamlandi() {
        return tamamlandi;
    }

    public void setTamamlandi(int tamamlandi) {
        this.tamamlandi = tamamlandi;
    }

    public long getNotEklenmeTarihi() {
        return notEklenmeTarihi;
    }

    public void setNotEklenmeTarihi(long notEklenmeTarihi) {
        this.notEklenmeTarihi = notEklenmeTarihi;
    }
}
