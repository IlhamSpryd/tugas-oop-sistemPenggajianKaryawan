package id.kelompok.absensigaji.payroll;

import id.kelompok.absensigaji.attendance.AbsensiService;
import id.kelompok.absensigaji.attendance.CatatanAbsensi;
import id.kelompok.absensigaji.attendance.StatusAbsensi;
import id.kelompok.absensigaji.employee.Karyawan;

import java.time.YearMonth;
import java.util.List;

/**
 * Layanan untuk menghitung kalkulasi penggajian karyawan berdasarkan data absensi dan kompensasi.
 * Mengimplementasikan business logic penggajian dengan Dependency Injection dan proteksi nilai non-negatif.
 *
 * Tim Pengembang & Kolaborasi:
 * - Penanggung Jawab / Author : Muhammad Andriansyah
 * - Reviewer Modul             : Ilham Sepriyadi & Ade Rizky
 */
public class PenggajianService {
    // Parameter simulasi penggajian yang disepakati bersama oleh tim
    public static final int HARI_KERJA_STANDAR = 26;          // Standar hari kerja bulanan
    public static final long TARIF_LEMBUR_PER_JAM = 20_000L;  // Kompensasi lembur Rp20.000 per jam

    // [Catatan Desain - Muhammad Andriansyah]:
    // Dependency Injection (DI) via Constructor: PenggajianService tidak membuat 'new AbsensiService()',
    // melainkan menerima instance dari luar (Main). Ini menjaga loose coupling antar modul.
    private final AbsensiService absensiService;

    /**
     * Konstruktor dengan Dependency Injection.
     *
     * [Diskusi Tim]:
     * - Andriansyah  : "Dengan nyuntik absensiService lewat constructor, class ini jadi gampang banget di-unit test."
     * - Ilham        : "Sepakat, nanti di Main.java gw tinggal inisialisasi: PENGGAJIAN = new PenggajianService(ABSENSI)."
     */
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

        // [Catatan Kolaborasi - Andriansyah & Ade Rizky]:
        // Membaca data absensi dari service milik Ade, lalu merekapitulasi jumlah hari berdasarkan status
        List<CatatanAbsensi> records = absensiService.getCatatan(karyawan.getId(), periode);
        int hadir = (int) absensiService.hitungStatus(records, StatusAbsensi.HADIR);
        int izin = (int) absensiService.hitungStatus(records, StatusAbsensi.IZIN);
        int sakit = (int) absensiService.hitungStatus(records, StatusAbsensi.SAKIT);
        int alpha = (int) absensiService.hitungStatus(records, StatusAbsensi.ALPHA);
        int totalJamLembur = absensiService.hitungTotalJamLembur(records);

        // [Logika Kalkulasi - Muhammad Andriansyah]:
        // 1. Potongan Alpha proporsional: round((Gaji Pokok / 26) * hariAlpha)
        // 2. Upah Lembur: totalJamLembur * Rp20.000
        // 3. Proteksi Gaji Bersih: Math.max(0, pendapatan - potongan) agar tidak pernah bernilai minus
        long potonganAlpha = Math.round((double) karyawan.getGajiPokokBulanan()
                / HARI_KERJA_STANDAR * alpha);
        long upahLembur = (long) totalJamLembur * TARIF_LEMBUR_PER_JAM;
        long totalPendapatan = karyawan.getGajiPokokBulanan()
                + karyawan.getTunjanganTetapBulanan() + upahLembur;
        long totalPotongan = potonganAlpha + potonganTambahan;

        // [Review Ilham & Ade]:
        // Ilham : "Ndri, Math.max(0L, ...) ini brilian banget buat nangkep edge case kalau potongan melebihi pendapatan."
        // Ade   : "Udah diverifikasi di TC-13, hasilnya aman Rp0."
        long gajiBersih = Math.max(0L, totalPendapatan - totalPotongan);

        return new HasilPenggajian(
                karyawan.getId(), karyawan.getNama(), periode.toString(),
                hadir, izin, sakit, alpha, totalJamLembur,
                karyawan.getGajiPokokBulanan(), karyawan.getTunjanganTetapBulanan(),
                upahLembur, potonganAlpha, potonganTambahan, gajiBersih);
    }
}
