package id.kelompok.absensigaji.attendance;

import java.time.LocalDate;
import java.util.Objects;

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
        if (idKaryawan == null || idKaryawan.isBlank()) {
            throw new IllegalArgumentException("ID karyawan tidak boleh kosong.");
        }
        this.idKaryawan = idKaryawan.trim();
        this.tanggal = Objects.requireNonNull(tanggal, "Tanggal harus diisi.");
        this.status = Objects.requireNonNull(status, "Status absensi harus diisi.");
        if (jamLembur < 0) {
            throw new IllegalArgumentException("Jam lembur tidak boleh negatif.");
        }
        if (status != StatusAbsensi.HADIR && jamLembur != 0) {
            throw new IllegalArgumentException("Jam lembur hanya dapat dicatat untuk status HADIR.");
        }
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
        return String.format("%s | %s | %s | Lembur: %d jam",
                tanggal, idKaryawan, status, jamLembur);
    }
}
