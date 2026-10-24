package id.kelompok.absensigaji.payroll;

import id.kelompok.absensigaji.attendance.AbsensiService;
import id.kelompok.absensigaji.attendance.CatatanAbsensi;
import id.kelompok.absensigaji.attendance.StatusAbsensi;
import id.kelompok.absensigaji.employee.Karyawan;

import java.time.YearMonth;
import java.util.List;

/**
 * Layanan untuk menghitung kalkulasi penggajian karyawan berdasarkan data absensi dan kompensasi.
 *
 * Penanggung jawab: Andri
 */
public class PenggajianService {
    // Parameter simulasi penggajian
    public static final int HARI_KERJA_STANDAR = 26;
    public static final long TARIF_LEMBUR_PER_JAM = 20_000L;

    // Dependensi ke modul absensi (Ade) untuk membaca rekap kehadiran
    private final AbsensiService absensiService;

    public PenggajianService(AbsensiService absensiService) {
        this.absensiService = absensiService;
    }

    /**
     * Menghitung rincian gaji bulanan seorang karyawan untuk periode tertentu.
     *
     * @param karyawan         objek Karyawan yang akan dihitung gajinya (tidak boleh null)
     * @param periode          periode bulan dan tahun penggajian (tidak boleh null)
     * @param potonganTambahan potongan non-kehadiran (tidak boleh negatif)
     * @return objek HasilPenggajian berisi rincian lengkap komponen gaji
     */
    public HasilPenggajian hitungGaji(Karyawan karyawan, YearMonth periode,
                                      long potonganTambahan) {
        // TODO: Andri - Implementasikan logika perhitungan gaji bulanan:
        // 1. Validasi input:
        //    - Pastikan karyawan != null dan periode != null (lemparkan IllegalArgumentException jika null).
        //    - Pastikan potonganTambahan >= 0 (lemparkan IllegalArgumentException jika negatif).
        // 2. Ambil data catatan absensi dari absensiService:
        //    List<CatatanAbsensi> records = absensiService.getCatatan(karyawan.getId(), periode);
        // 3. Hitung jumlah kehadiran berdasarkan status menggunakan absensiService.hitungStatus:
        //    - hariHadir (StatusAbsensi.HADIR)
        //    - hariIzin (StatusAbsensi.IZIN)
        //    - hariSakit (StatusAbsensi.SAKIT)
        //    - hariAlpha (StatusAbsensi.ALPHA)
        //    - totalJamLembur menggunakan absensiService.hitungTotalJamLembur(records)
        // 4. Hitung rincian finansial sesuai aturan simulasi kelompok:
        //    - potonganAlpha = Math.round((double) karyawan.getGajiPokokBulanan() / HARI_KERJA_STANDAR * hariAlpha)
        //    - upahLembur = (long) totalJamLembur * TARIF_LEMBUR_PER_JAM
        //    - totalPendapatan = karyawan.getGajiPokokBulanan() + karyawan.getTunjanganTetapBulanan() + upahLembur
        //    - totalPotongan = potonganAlpha + potonganTambahan
        //    - gajiBersih = Math.max(0L, totalPendapatan - totalPotongan)
        // 5. Buat dan kembalikan objek HasilPenggajian dengan semua rincian yang telah dihitung.

        throw new UnsupportedOperationException("TODO: hitungGaji() belum diimplementasikan.");
    }
}
