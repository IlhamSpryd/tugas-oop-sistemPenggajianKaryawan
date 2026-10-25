package id.kelompok.absensigaji.employee;

/**
 * Merepresentasikan data dasar seorang karyawan.
 * Modul ini menjadi fondasi identitas karyawan untuk modul absensi dan penggajian.
 *
 * Tim Pengembang & Kolaborasi:
 * - Penanggung Jawab / Author : Ilham Sepriyadi
 * - Reviewer Modul             : Muhammad Andriansyah & Ade Rizky
 */
public class Karyawan {
    // [Catatan Desain - Ilham Sepriyadi]:
    // Seluruh atribut dibuat 'private final' demi menjamin prinsip Enkapsulasi & Immutability.
    // Sekali objek Karyawan dibuat, state di dalamnya tidak boleh diubah (read-only via getter).
    private final String id;
    private final String nama;
    private final String jabatan;
    private final long gajiPokokBulanan;       // Tipe 'long' dipilih untuk mencegah floating-point rounding issue
    private final long tunjanganTetapBulanan;

    /**
     * Konstruktor utama dengan validasi integritas data (Defensive Programming).
     *
     * [Diskusi Tim]:
     * - Ilham        : "Constructor ini jadi penjaga gerbang. Kalau data masukan cacat, langsung lempar IllegalArgumentException."
     * - Andriansyah  : "Setuju Ham, validasi gaji non-negatif di sini bikin modul payroll gw tenang gak bakal dapet angka minus dari awal."
     * - Ade Rizky    : "Bagus juga ada trim() dan isBlank() check, jadi data ID yang masuk ke modul absensi gw udah bersih."
     */
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
