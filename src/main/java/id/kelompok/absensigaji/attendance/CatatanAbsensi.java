package id.kelompok.absensigaji.attendance;

import java.time.LocalDate;

/**
 * Merepresentasikan satu rekaman absensi seorang karyawan pada tanggal tertentu.
 *
 * Penanggung jawab: Ade
 */
public class CatatanAbsensi {
    private final String idKaryawan;
    private final LocalDate tanggal;
    private final StatusAbsensi status;
    private final int jamLembur;

    public CatatanAbsensi(String idKaryawan, LocalDate tanggal,
                          StatusAbsensi status, int jamLembur) {
        // TODO: Ade - Implementasikan validasi bisnis pencatatan absensi:
        // 1. idKaryawan tidak boleh null atau kosong (blank).
        // 2. tanggal dan status tidak boleh null (gunakan validasi eksplisit / Objects.requireNonNull).
        // 3. jamLembur tidak boleh bernilai negatif (< 0).
        // 4. jamLembur hanya boleh bernilai > 0 jika status == StatusAbsensi.HADIR.
        //    Jika status bukan HADIR dan jamLembur != 0, lemparkan IllegalArgumentException.
        this.idKaryawan = idKaryawan;
        this.tanggal = tanggal;
        this.status = status;
        this.jamLembur = jamLembur;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public StatusAbsensi getStatus() {
        return status;
    }

    public int getJamLembur() {
        return jamLembur;
    }

    @Override
    public String toString() {
        // TODO: Ade - Implementasikan representasi String untuk catatan absensi (misal: "tanggal | id | status | jam lembur")
        throw new UnsupportedOperationException("TODO: toString() CatatanAbsensi belum diimplementasikan.");
    }
}
