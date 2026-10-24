package id.kelompok.absensigaji;

import id.kelompok.absensigaji.attendance.AbsensiService;
import id.kelompok.absensigaji.attendance.CatatanAbsensi;
import id.kelompok.absensigaji.attendance.StatusAbsensi;
import id.kelompok.absensigaji.employee.Karyawan;
import id.kelompok.absensigaji.payroll.HasilPenggajian;
import id.kelompok.absensigaji.payroll.PenggajianService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class SystemTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       MENJALANKAN VERIFIKASI TEST CASE (TC)      ");
        System.out.println("==================================================");

        testTC01();
        testTC02();
        testTC03();
        testTC04();
        testTC05();
        testTC06();
        testTC07();
        testTC08();
        testTC09();
        testTC10();
        testTC11();
        testTC12();
        testTC13();
        testTC14();
        testTC15();

        System.out.println("==================================================");
        System.out.printf("TOTAL HASIL: %d LULUS, %d GAGAL (DARI %d TEST CASE)%n",
                passed, failed, (passed + failed));
        System.out.println("==================================================");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void assertTrue(String tcId, String scenario, boolean condition, String detail) {
        if (condition) {
            System.out.printf("[%s] LULUS: %s (%s)%n", tcId, scenario, detail);
            passed++;
        } else {
            System.err.printf("[%s] GAGAL: %s (%s)%n", tcId, scenario, detail);
            failed++;
        }
    }

    private static void testTC01() {
        Karyawan k = new Karyawan("K001", "Budi", "Staff", 3_000_000L, 300_000L);
        boolean ok = "K001".equals(k.getId())
                && "Budi".equals(k.getNama())
                && "Staff".equals(k.getJabatan())
                && k.getGajiPokokBulanan() == 3_000_000L
                && k.getTunjanganTetapBulanan() == 300_000L
                && k.toString().contains("K001");
        assertTrue("TC-01", "Tambah karyawan dengan data valid", ok, "Objek Karyawan berhasil dibuat dan tersimpan");
    }

    private static void testTC02() {
        List<Karyawan> repo = new ArrayList<>();
        repo.add(new Karyawan("K001", "Budi", "Staff", 3_000_000L, 300_000L));
        String newId = "K001";
        boolean duplicate = repo.stream().anyMatch(item -> item.getId().equalsIgnoreCase(newId.trim()));
        assertTrue("TC-02", "Tambah karyawan dengan ID duplikat", duplicate, "Input kedua terdeteksi duplikat");
    }

    private static void testTC03() {
        boolean thrownPokok = false;
        try {
            new Karyawan("K002", "Negatif", "Staff", -1_000_000L, 300_000L);
        } catch (IllegalArgumentException e) {
            thrownPokok = true;
        }

        boolean thrownTunjangan = false;
        try {
            new Karyawan("K002", "Negatif", "Staff", 1_000_000L, -50_000L);
        } catch (IllegalArgumentException e) {
            thrownTunjangan = true;
        }

        assertTrue("TC-03", "Tambah karyawan dengan nilai gaji negatif", thrownPokok && thrownTunjangan, "Dilempar IllegalArgumentException");
    }

    private static void testTC04() {
        AbsensiService service = new AbsensiService();
        service.catatAbsensi("K001", LocalDate.of(2026, 10, 1), StatusAbsensi.HADIR, 3);
        List<CatatanAbsensi> list = service.getSemuaCatatan();
        boolean ok = list.size() == 1
                && list.get(0).getStatus() == StatusAbsensi.HADIR
                && list.get(0).getJamLembur() == 3;
        assertTrue("TC-04", "Catat absensi HADIR dengan jam lembur", ok, "Tersimpan status HADIR dan lembur 3 jam");
    }

    private static void testTC05() {
        AbsensiService service = new AbsensiService();
        service.catatAbsensi("K001", LocalDate.of(2026, 10, 2), StatusAbsensi.IZIN, 0);
        List<CatatanAbsensi> list = service.getSemuaCatatan();
        boolean ok = list.size() == 1
                && list.get(0).getStatus() == StatusAbsensi.IZIN
                && list.get(0).getJamLembur() == 0;
        assertTrue("TC-05", "Catat absensi non-HADIR dengan lembur 0", ok, "Tersimpan status IZIN dan lembur 0 jam");
    }

    private static void testTC06() {
        AbsensiService service = new AbsensiService();
        boolean thrown = false;
        try {
            service.catatAbsensi("K001", LocalDate.of(2026, 10, 3), StatusAbsensi.SAKIT, 2);
        } catch (IllegalArgumentException e) {
            thrown = e.getMessage().contains("HADIR");
        }
        assertTrue("TC-06", "Catat absensi non-HADIR tetapi lembur > 0", thrown, "Ditolak dengan IllegalArgumentException");
    }

    private static void testTC07() {
        AbsensiService service = new AbsensiService();
        service.catatAbsensi("K001", LocalDate.of(2026, 10, 1), StatusAbsensi.HADIR, 0);
        boolean thrown = false;
        try {
            service.catatAbsensi("K001", LocalDate.of(2026, 10, 1), StatusAbsensi.HADIR, 2);
        } catch (IllegalArgumentException e) {
            thrown = e.getMessage().contains("sudah ada");
        }
        assertTrue("TC-07", "Catat absensi duplikat pada tanggal yang sama", thrown, "Ditolak karena absensi tanggal tersebut sudah ada");
    }

    private static void testTC08() {
        boolean thrown1 = false;
        try {
            LocalDate.parse("24/10/2026");
        } catch (DateTimeParseException e) {
            thrown1 = true;
        }

        boolean thrown2 = false;
        try {
            LocalDate.parse("2026-13-45");
        } catch (DateTimeParseException e) {
            thrown2 = true;
        }
        assertTrue("TC-08", "Catat absensi dengan format tanggal salah", thrown1 && thrown2, "Ditangkap DateTimeParseException");
    }

    private static void testTC09() {
        AbsensiService service = new AbsensiService();
        service.catatAbsensi("K001", LocalDate.of(2026, 10, 15), StatusAbsensi.HADIR, 1);
        service.catatAbsensi("K001", LocalDate.of(2026, 10, 1), StatusAbsensi.HADIR, 0);
        service.catatAbsensi("K002", LocalDate.of(2026, 10, 5), StatusAbsensi.HADIR, 0);
        service.catatAbsensi("K001", LocalDate.of(2026, 11, 1), StatusAbsensi.HADIR, 0);

        List<CatatanAbsensi> k1Oct = service.getCatatan("K001", YearMonth.of(2026, 10));
        boolean ok = k1Oct.size() == 2
                && k1Oct.get(0).getTanggal().equals(LocalDate.of(2026, 10, 1))
                && k1Oct.get(1).getTanggal().equals(LocalDate.of(2026, 10, 15));
        assertTrue("TC-09", "Query riwayat absensi berdasarkan ID & periode", ok, "Filter dan urutan catatan akurat");
    }

    private static void testTC10() {
        AbsensiService absensi = new AbsensiService();
        PenggajianService payroll = new PenggajianService(absensi);
        Karyawan k = new Karyawan("K001", "Budi", "Staff", 3_000_000L, 300_000L);
        for (int i = 1; i <= 20; i++) {
            absensi.catatAbsensi("K001", LocalDate.of(2026, 10, i), StatusAbsensi.HADIR, 0);
        }

        HasilPenggajian hasil = payroll.hitungGaji(k, YearMonth.of(2026, 10), 0L);
        boolean ok = hasil.getHariHadir() == 20
                && hasil.getHariAlpha() == 0
                && hasil.getUpahLembur() == 0
                && hasil.getPotonganAlpha() == 0
                && hasil.getGajiBersih() == 3_300_000L;
        assertTrue("TC-10", "Hitung gaji: kehadiran penuh tanpa lembur & potongan", ok, "Gaji bersih = Rp3.300.000");
    }

    private static void testTC11() {
        AbsensiService absensi = new AbsensiService();
        PenggajianService payroll = new PenggajianService(absensi);
        Karyawan k = new Karyawan("K001", "Budi", "Staff", 3_000_000L, 300_000L);

        // 20 hari Hadir, total lembur 5 jam
        for (int i = 1; i <= 19; i++) {
            absensi.catatAbsensi("K001", LocalDate.of(2026, 10, i), StatusAbsensi.HADIR, 0);
        }
        absensi.catatAbsensi("K001", LocalDate.of(2026, 10, 20), StatusAbsensi.HADIR, 5);
        absensi.catatAbsensi("K001", LocalDate.of(2026, 10, 21), StatusAbsensi.IZIN, 0);
        absensi.catatAbsensi("K001", LocalDate.of(2026, 10, 22), StatusAbsensi.SAKIT, 0);
        absensi.catatAbsensi("K001", LocalDate.of(2026, 10, 23), StatusAbsensi.ALPHA, 0);
        absensi.catatAbsensi("K001", LocalDate.of(2026, 10, 24), StatusAbsensi.ALPHA, 0);

        HasilPenggajian hasil = payroll.hitungGaji(k, YearMonth.of(2026, 10), 50_000L);
        boolean ok = hasil.getUpahLembur() == 100_000L
                && hasil.getPotonganAlpha() == 230_769L
                && hasil.getGajiBersih() == 3_119_231L;
        assertTrue("TC-11", "Hitung gaji: ada lembur dan potongan Alpha", ok,
                String.format("Lembur: Rp%,d, Alpha: Rp%,d, Bersih: Rp%,d",
                        hasil.getUpahLembur(), hasil.getPotonganAlpha(), hasil.getGajiBersih()));
    }

    private static void testTC12() {
        AbsensiService absensi = new AbsensiService();
        PenggajianService payroll = new PenggajianService(absensi);
        Karyawan k = new Karyawan("K001", "Budi", "Staff", 3_000_000L, 300_000L);
        boolean thrown = false;
        try {
            payroll.hitungGaji(k, YearMonth.of(2026, 10), -100_000L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("TC-12", "Hitung gaji: input potongan tambahan negatif", thrown, "Ditolak dengan IllegalArgumentException");
    }

    private static void testTC13() {
        AbsensiService absensi = new AbsensiService();
        PenggajianService payroll = new PenggajianService(absensi);
        Karyawan k = new Karyawan("K002", "Siti", "Magang", 2_000_000L, 0L);

        // Pendapatan 2.000.000, potongan tambahan 2.500.000
        HasilPenggajian hasil = payroll.hitungGaji(k, YearMonth.of(2026, 10), 2_500_000L);
        boolean ok = hasil.getGajiBersih() == 0L;
        assertTrue("TC-13", "Hitung gaji: total potongan melebihi pendapatan", ok, "Gaji bersih tidak negatif (Rp0)");
    }

    private static void testTC14() {
        AbsensiService absensi = new AbsensiService();
        PenggajianService payroll = new PenggajianService(absensi);
        Karyawan k = new Karyawan("K001", "Budi", "Staff", 3_000_000L, 300_000L);

        // Belum ada data absensi untuk 2026-11
        HasilPenggajian hasil = payroll.hitungGaji(k, YearMonth.of(2026, 11), 0L);
        boolean ok = hasil.getHariHadir() == 0
                && hasil.getHariIzin() == 0
                && hasil.getHariSakit() == 0
                && hasil.getHariAlpha() == 0
                && hasil.getTotalJamLembur() == 0
                && hasil.getGajiBersih() == 3_300_000L;
        assertTrue("TC-14", "Hitung gaji: periode tanpa rekaman absensi", ok, "Gaji bersih dihitung sesuai standar tanpa potongan");
    }

    private static void testTC15() {
        AbsensiService absensi = new AbsensiService();
        PenggajianService payroll = new PenggajianService(absensi);

        // Alur lengkap end-to-end
        Karyawan k = new Karyawan("K001", "Budi", "Staff", 3_000_000L, 300_000L);
        absensi.catatAbsensi(k.getId(), LocalDate.of(2026, 10, 1), StatusAbsensi.HADIR, 2);
        absensi.catatAbsensi(k.getId(), LocalDate.of(2026, 10, 2), StatusAbsensi.IZIN, 0);
        absensi.catatAbsensi(k.getId(), LocalDate.of(2026, 10, 3), StatusAbsensi.ALPHA, 0);

        HasilPenggajian hasil = payroll.hitungGaji(k, YearMonth.of(2026, 10), 0L);
        long expectedAlpha = Math.round((double) 3_000_000L / 26 * 1); // 115385
        long expectedLembur = 2 * 20_000L; // 40000
        long expectedBersih = 3_000_000L + 300_000L + expectedLembur - expectedAlpha; // 3224615

        boolean ok = hasil.getIdKaryawan().equals("K001")
                && hasil.getHariHadir() == 1
                && hasil.getHariIzin() == 1
                && hasil.getHariAlpha() == 1
                && hasil.getTotalJamLembur() == 2
                && hasil.getUpahLembur() == expectedLembur
                && hasil.getPotonganAlpha() == expectedAlpha
                && hasil.getGajiBersih() == expectedBersih;
        assertTrue("TC-15", "Alur integrasi end-to-end lengkap", ok,
                String.format("Gaji bersih: Rp%,d (alpha: Rp%,d, lembur: Rp%,d)",
                        hasil.getGajiBersih(), hasil.getPotonganAlpha(), hasil.getUpahLembur()));
    }
}
