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
        if (karyawan == null || periode == null) {
            throw new IllegalArgumentException("Karyawan dan periode wajib diisi.");
        }
        if (potonganTambahan < 0) {
            throw new IllegalArgumentException("Potongan tambahan tidak boleh negatif.");
        }

        List<CatatanAbsensi> records = absensiService.getCatatan(karyawan.getId(), periode);
        int hadir = (int) absensiService.hitungStatus(records, StatusAbsensi.HADIR);
        int izin = (int) absensiService.hitungStatus(records, StatusAbsensi.IZIN);
        int sakit = (int) absensiService.hitungStatus(records, StatusAbsensi.SAKIT);
        int alpha = (int) absensiService.hitungStatus(records, StatusAbsensi.ALPHA);
        int totalJamLembur = absensiService.hitungTotalJamLembur(records);

        long potonganAlpha = Math.round((double) karyawan.getGajiPokokBulanan()
                / HARI_KERJA_STANDAR * alpha);
        long upahLembur = (long) totalJamLembur * TARIF_LEMBUR_PER_JAM;
        long totalPendapatan = karyawan.getGajiPokokBulanan()
                + karyawan.getTunjanganTetapBulanan() + upahLembur;
        long totalPotongan = potonganAlpha + potonganTambahan;
        long gajiBersih = Math.max(0L, totalPendapatan - totalPotongan);

        return new HasilPenggajian(
                karyawan.getId(), karyawan.getNama(), periode.toString(),
                hadir, izin, sakit, alpha, totalJamLembur,
                karyawan.getGajiPokokBulanan(), karyawan.getTunjanganTetapBulanan(),
                upahLembur, potonganAlpha, potonganTambahan, gajiBersih);
    }
}
