package id.kelompok.absensigaji.attendance;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
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
        if (idKaryawan == null || idKaryawan.isBlank()) {
            throw new IllegalArgumentException("ID karyawan tidak boleh kosong.");
        }
        boolean sudahAda = daftarCatatan.stream().anyMatch(item ->
                item.getIdKaryawan().equalsIgnoreCase(idKaryawan.trim())
                        && item.getTanggal().equals(tanggal));
        if (sudahAda) {
            throw new IllegalArgumentException("Absensi karyawan pada tanggal tersebut sudah ada.");
        }
        daftarCatatan.add(new CatatanAbsensi(idKaryawan, tanggal, status, jamLembur));
    }

    /**
     * Mengambil seluruh riwayat absensi yang tersimpan dalam sistem.
     */
    public List<CatatanAbsensi> getSemuaCatatan() {
        return daftarCatatan.stream()
                .sorted(Comparator.comparing(CatatanAbsensi::getTanggal))
                .toList();
    }

    /**
     * Mengambil catatan absensi untuk karyawan tertentu pada periode bulan dan tahun yang ditentukan.
     */
    public List<CatatanAbsensi> getCatatan(String idKaryawan, YearMonth periode) {
        if (idKaryawan == null || periode == null) {
            return List.of();
        }
        return daftarCatatan.stream()
                .filter(item -> item.getIdKaryawan().equalsIgnoreCase(idKaryawan.trim()))
                .filter(item -> YearMonth.from(item.getTanggal()).equals(periode))
                .sorted(Comparator.comparing(CatatanAbsensi::getTanggal))
                .toList();
    }

    /**
     * Menghitung frekuensi kemunculan status tertentu pada daftar catatan absensi yang diberikan.
     */
    public long hitungStatus(List<CatatanAbsensi> data, StatusAbsensi status) {
        if (data == null || status == null) {
            return 0;
        }
        return data.stream().filter(item -> item.getStatus() == status).count();
    }

    /**
     * Menghitung total jam lembur yang tercatat pada kumpulan data absensi.
     */
    public int hitungTotalJamLembur(List<CatatanAbsensi> data) {
        if (data == null) {
            return 0;
        }
        return data.stream().mapToInt(CatatanAbsensi::getJamLembur).sum();
    }
}
