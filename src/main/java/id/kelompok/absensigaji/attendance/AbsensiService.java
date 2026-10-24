package id.kelompok.absensigaji.attendance;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Layanan untuk mengelola penyimpanan, validasi, dan penelusuran data absensi karyawan.
 *
 * Penanggung jawab: Ade
 */
public class AbsensiService {
    // Media penyimpanan data absensi dalam memori
    private final List<CatatanAbsensi> daftarCatatan = new ArrayList<>();

    /**
     * Mencatat data absensi baru ke dalam sistem.
     * Harus memvalidasi agar tidak terjadi duplikasi tanggal untuk karyawan yang sama.
     */
    public void catatAbsensi(String idKaryawan, LocalDate tanggal,
                             StatusAbsensi status, int jamLembur) {
        // TODO: Ade - Implementasikan pencatatan absensi:
        // 1. Periksa apakah sudah ada catatan untuk idKaryawan pada tanggal yang sama di daftarCatatan.
        //    Jika sudah ada, lemparkan IllegalArgumentException dengan pesan informatif.
        // 2. Buat objek CatatanAbsensi baru.
        // 3. Tambahkan ke dalam daftarCatatan.
    }

    /**
     * Mengambil seluruh riwayat absensi yang tersimpan dalam sistem.
     */
    public List<CatatanAbsensi> getSemuaCatatan() {
        // TODO: Ade - Kembalikan seluruh catatan absensi (disarankan mengembalikan List yang tidak dapat diubah / diurutkan berdasarkan tanggal)
        throw new UnsupportedOperationException("TODO: getSemuaCatatan() belum diimplementasikan.");
    }

    /**
     * Mengambil catatan absensi untuk karyawan tertentu pada periode bulan dan tahun yang ditentukan.
     */
    public List<CatatanAbsensi> getCatatan(String idKaryawan, YearMonth periode) {
        // TODO: Ade - Filter daftarCatatan berdasarkan kecocokan idKaryawan dan YearMonth periode.
        // Urutkan hasilnya berdasarkan tanggal.
        throw new UnsupportedOperationException("TODO: getCatatan() belum diimplementasikan.");
    }

    /**
     * Menghitung frekuensi kemunculan status tertentu pada daftar catatan absensi yang diberikan.
     */
    public long hitungStatus(List<CatatanAbsensi> data, StatusAbsensi status) {
        // TODO: Ade - Hitung jumlah item dalam list yang memiliki status sama dengan parameter status
        throw new UnsupportedOperationException("TODO: hitungStatus() belum diimplementasikan.");
    }

    /**
     * Menghitung total jam lembur yang tercatat pada kumpulan data absensi.
     */
    public int hitungTotalJamLembur(List<CatatanAbsensi> data) {
        // TODO: Ade - Jumlahkan seluruh jamLembur dari catatan absensi yang ada pada list data
        throw new UnsupportedOperationException("TODO: hitungTotalJamLembur() belum diimplementasikan.");
    }
}
