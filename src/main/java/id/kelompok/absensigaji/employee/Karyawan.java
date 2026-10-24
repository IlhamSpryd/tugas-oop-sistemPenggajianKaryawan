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
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID karyawan tidak boleh kosong.");
        }
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("Nama karyawan tidak boleh kosong.");
        }
        if (jabatan == null || jabatan.isBlank()) {
            throw new IllegalArgumentException("Jabatan tidak boleh kosong.");
        }
        if (gajiPokokBulanan < 0 || tunjanganTetapBulanan < 0) {
            throw new IllegalArgumentException("Gaji pokok dan tunjangan tidak boleh bernilai negatif.");
        }
        this.id = id.trim();
        this.nama = nama.trim();
        this.jabatan = jabatan.trim();
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
        return String.format("ID: %s | Nama: %s | Jabatan: %s | Gaji Pokok: Rp%,d | Tunjangan: Rp%,d",
                id, nama, jabatan, gajiPokokBulanan, tunjanganTetapBulanan);
    }
}
