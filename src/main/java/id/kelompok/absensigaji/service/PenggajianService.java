package id.kelompok.absensigaji.service;

import id.kelompok.absensigaji.model.CatatanAbsensi;
import id.kelompok.absensigaji.model.HasilPenggajian;
import id.kelompok.absensigaji.model.Karyawan;
import id.kelompok.absensigaji.model.StatusAbsensi;

import java.time.YearMonth;
import java.util.List;

/** Menghitung gaji berdasarkan aturan simulasi yang ditulis di README.md. */
public class PenggajianService {
    public static final int HARI_KERJA_STANDAR = 26;
    public static final long TARIF_LEMBUR_PER_JAM = 20_000L;

    private final AbsensiService absensiService;

    public PenggajianService(AbsensiService absensiService) {
        this.absensiService = absensiService;
    }

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
