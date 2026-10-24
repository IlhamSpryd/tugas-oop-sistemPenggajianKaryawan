package id.kelompok.absensigaji.employee;

/**
 * Merepresentasikan data dasar seorang karyawan.
 * Modul ini menjadi fondasi identitas karyawan untuk modul absensi dan penggajian.
 *
 * Penanggung jawab: Ilham Sepriyadi
 */
public class Karyawan {
    private final String id;
    private final String nama;
    private final String jabatan;
    private final long gajiPokokBulanan;
    private final long tunjanganTetapBulanan;

    public Karyawan(String id, String nama, String jabatan,
                    long gajiPokokBulanan, long tunjanganTetapBulanan) {
        // TODO: Ilham - Implementasikan validasi pembuatan objek Karyawan:
        // 1. id, nama, dan jabatan tidak boleh null atau kosong (blank).
        // 2. gajiPokokBulanan dan tunjanganTetapBulanan tidak boleh bernilai negatif (< 0).
        // 3. Normalisasi teks (trim) sebelum disimpan ke atribut.
        this.id = id;
        this.nama = nama;
        this.jabatan = jabatan;
        this.gajiPokokBulanan = gajiPokokBulanan;
        this.tunjanganTetapBulanan = tunjanganTetapBulanan;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public long getGajiPokokBulanan() {
        return gajiPokokBulanan;
    }

    public long getTunjanganTetapBulanan() {
        return tunjanganTetapBulanan;
    }

    @Override
    public String toString() {
        // TODO: Ilham - Implementasikan representasi String untuk menampilkan data karyawan dengan format rapi
        throw new UnsupportedOperationException("TODO: toString() Karyawan belum diimplementasikan.");
    }
}
