package id.kelompok.absensigaji.attendance;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Merepresentasikan satu rekaman absensi seorang karyawan pada tanggal tertentu.
 * Mengusung konsep Value Object / Immutability demi konsistensi data riwayat kehadiran.
 *
 * Tim Pengembang & Kolaborasi:
 * - Penanggung Jawab / Author : Ade Rizky
 * - Reviewer Modul             : Muhammad Andriansyah & Ilham Sepriyadi
 */
public class CatatanAbsensi {
    // [Catatan Desain - Ade Rizky]:
    // Semua atribut bersifat private final agar tidak ada manipulasi data kehadiran pasca-input.
    private final String idKaryawan;
    private final LocalDate tanggal;
    private final StatusAbsensi status;
    private final int jamLembur;

    /**
     * Konstruktor dengan validasi integritas aturan bisnis absensi.
     *
     * [Diskusi Review Tim]:
     * - Ade Rizky    : "Di sini gw pasang aturan bisnis: lembur HANYA sah kalau statusnya HADIR.
     *                   Kalau izin/sakit/alpha tapi ada jam lemburnya, sistem bakal langsung nolak."
     * - Andriansyah  : "Mantap De! Dengan validasi ketat ini, modul payroll gw gak bakal kecolongan
     *                   ngitung upah lembur buat orang yang statusnya lagi SAKIT atau ALPHA."
     * - Ilham        : "Udah gw tes di skenario TC-06, eksepsinya terbukti tertangkap dengan mulus."
     */
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
