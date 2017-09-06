package jonesrandom.firebasesimplecrud;

/**
 * Created by JonesRandom on 9/6/17.
 * https://masx-dev.blogspot.com
 */

public class Barang {

    private String Nama;
    private String Keterangan;
    private String Harga;
    private String Node;

    public Barang() {
    }

    public Barang(String nama, String keterangan, String harga) {
        Nama = nama;
        Keterangan = keterangan;
        Harga = harga;
    }

    public String getNama() {
        return Nama;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public String getHarga() {
        return Harga;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public void setHarga(String harga) {
        Harga = harga;
    }

    public String getNode() {
        return Node;
    }

    public void setNode(String node) {
        Node = node;
    }
}
