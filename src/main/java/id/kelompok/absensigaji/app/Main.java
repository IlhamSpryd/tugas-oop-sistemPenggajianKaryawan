package id.kelompok.absensigaji.app;

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
import java.util.Scanner;

/**
 * Titik masuk utama aplikasi (Main Entry Point) dan antarmuka menu konsol (CLI).
 * Bertindak sebagai Orchestrator yang mengintegrasikan ketiga modul:
 * - Modul employee (Ilham Sepriyadi)
 * - Modul attendance (Ade Rizky)
 * - Modul payroll (Muhammad Andriansyah)
 *
 * Tim Pengembang & Kolaborasi:
 * - Penanggung Jawab / Author : Ilham Sepriyadi
 * - Reviewer Modul             : Ade Rizky & Muhammad Andriansyah
 */
public class Main {
    private static final Scanner INPUT = new Scanner(System.in);

    // Repository in-memory untuk menyimpan master data karyawan (Kelolaan Ilham)
    private static final List<Karyawan> KARYAWAN = new ArrayList<>();

    // [Catatan Arsitektur - Ilham Sepriyadi]:
    // Di sini kita menerapkan Manual Dependency Injection:
    // 1. Instansiasi ABSENSI (Modul Ade Rizky) sebagai repositori riwayat kehadiran.
    // 2. Suntikkan ABSENSI ke dalam konstruktor PENGGAJIAN (Modul Muhammad Andriansyah).
    // Dengan pola ini, PenggajianService tidak terikat secara kaku (loose coupling).
    //
    // [Review Muhammad Andriansyah & Ade Rizky]:
    // Andriansyah : "Pola DI manual ini bersih banget Ham, modularitas tetap terjaga tanpa ribet konfigurasi framework."
    // Ade Rizky   : "Semua state absensi konsisten karena cuma ada 1 instance ABSENSI yang dipakai bersama."
    private static final AbsensiService ABSENSI = new AbsensiService();
    private static final PenggajianService PENGGAJIAN = new PenggajianService(ABSENSI);

    public static void main(String[] args) {
        boolean berjalan = true;
        System.out.println("SISTEM ABSENSI DAN PENGGAJIAN KARYAWAN");

        while (berjalan) {
            tampilkanMenu();
            String pilihan = bacaTeks("Pilih menu: ");
            switch (pilihan) {
                case "1" -> tambahKaryawan();
                case "2" -> tampilkanKaryawan();
                case "3" -> inputAbsensi();
                case "4" -> tampilkanRiwayatAbsensi();
                case "5" -> hitungGaji();
                case "0" -> {
                    berjalan = false;
                    System.out.println("Program selesai");
                }
                default -> System.out.println("Pilihan tidak valid");
            }
            System.out.println();
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\nMENU UTAMA");
        System.out.println("1. Tambah data karyawan");
        System.out.println("2. Lihat daftar karyawan");
        System.out.println("3. Input absensi dan lembur");
        System.out.println("4. Lihat riwayat absensi per karyawan/bulan");
        System.out.println("5. Hitung gaji bulanan");
        System.out.println("0. Keluar");
    }

    private static void tambahKaryawan() {
        System.out.println("\nTAMBAH KARYAWAN");
        String id = bacaTeks("ID karyawan: ");
        if (cariKaryawan(id) != null) {
            System.out.println("ID tersebut sudah digunakan.");
            return;
        }
        String nama = bacaTeks("Nama: ");
        String jabatan = bacaTeks("Jabatan: ");
        long gajiPokok = bacaLongNonNegatif("Gaji pokok bulanan (rupiah): ");
        long tunjangan = bacaLongNonNegatif("Tunjangan tetap bulanan (rupiah): ");
        try {
            Karyawan karyawan = new Karyawan(id, nama, jabatan, gajiPokok, tunjangan);
            KARYAWAN.add(karyawan);
            System.out.println("Karyawan berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Data tidak valid: " + e.getMessage());
        }
    }

    private static void tampilkanKaryawan() {
        System.out.println("\nDAFTAR KARYAWAN");
        if (KARYAWAN.isEmpty()) {
            System.out.println("Belum ada data karyawan.");
            return;
        }
        for (Karyawan karyawan : KARYAWAN) {
            System.out.println(karyawan);
        }
    }

    private static void inputAbsensi() {
        System.out.println("\nINPUT ABSENSI");
        if (KARYAWAN.isEmpty()) {
            System.out.println("Tambahkan karyawan terlebih dahulu.");
            return;
        }
        String id = bacaTeks("ID karyawan: ");
        if (cariKaryawan(id) == null) {
            System.out.println("ID karyawan tidak ditemukan.");
            return;
        }

        LocalDate tanggal;
        try {
            tanggal = LocalDate.parse(bacaTeks("Tanggal (YYYY-MM-DD): "));
        } catch (DateTimeParseException e) {
            System.out.println("Format tanggal tidak valid. Contoh: 2026-10-24.");
            return;
        }

        System.out.println("Status: 1. Hadir  2. Izin  3. Sakit  4. Alpha");
        StatusAbsensi status = switch (bacaTeks("Pilih status: ")) {
            case "1" -> StatusAbsensi.HADIR;
            case "2" -> StatusAbsensi.IZIN;
            case "3" -> StatusAbsensi.SAKIT;
            case "4" -> StatusAbsensi.ALPHA;
            default -> null;
        };
        if (status == null) {
            System.out.println("Status tidak valid.");
            return;
        }
        int jamLembur = status == StatusAbsensi.HADIR
                ? bacaIntNonNegatif("Jam lembur (0 jika tidak ada): ") : 0;
        try {
            // [Integrasi Ilham -> Ade Rizky]:
            // Memanggil service absensi milik Ade. Validasi duplikasi tanggal dan jam lembur
            // akan melempar IllegalArgumentException yang ditangkap di sini agar program tidak crash.
            ABSENSI.catatAbsensi(id, tanggal, status, jamLembur);
            System.out.println("Catatan absensi berhasil disimpan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Absensi gagal: " + e.getMessage());
        }
    }

    private static void tampilkanRiwayatAbsensi() {
        System.out.println("\nRIWAYAT ABSENSI");
        String id = bacaTeks("ID karyawan: ");
        if (cariKaryawan(id) == null) {
            System.out.println("ID karyawan tidak ditemukan.");
            return;
        }
        YearMonth periode = bacaPeriode();
        if (periode == null) return;
        List<CatatanAbsensi> records = ABSENSI.getCatatan(id, periode);
        if (records.isEmpty()) {
            System.out.println("Tidak ada catatan absensi untuk periode tersebut.");
            return;
        }
        records.forEach(System.out::println);
    }

    private static void hitungGaji() {
        System.out.println("\nHITUNG GAJI");
        String id = bacaTeks("ID karyawan: ");
        Karyawan karyawan = cariKaryawan(id);
        if (karyawan == null) {
            System.out.println("ID karyawan tidak ditemukan.");
            return;
        }
        YearMonth periode = bacaPeriode();
        if (periode == null) return;
        long potonganTambahan = bacaLongNonNegatif("Potongan tambahan (rupiah, isi 0 jika tidak ada): ");
        try {
            // [Integrasi Ilham -> Muhammad Andriansyah]:
            // Memanggil service payroll milik Andri. Seluruh kalkulasi ditangani di PenggajianService
            // dan hasilnya dikembalikan sebagai Value Object HasilPenggajian.
            HasilPenggajian hasil = PENGGAJIAN.hitungGaji(karyawan, periode, potonganTambahan);
            tampilkanHasilGaji(hasil);
        } catch (IllegalArgumentException e) {
            System.out.println("Perhitungan gagal: " + e.getMessage());
        }
    }

    private static void tampilkanHasilGaji(HasilPenggajian hasil) {
        System.out.println("\nRINCIAN PENGGAJIAN");
        System.out.printf("Karyawan             : %s (%s)%n", hasil.getNamaKaryawan(), hasil.getIdKaryawan());
        System.out.printf("Periode              : %s%n", hasil.getPeriode());
        System.out.printf("Hadir / Izin / Sakit : %d / %d / %d hari%n",
                hasil.getHariHadir(), hasil.getHariIzin(), hasil.getHariSakit());
        System.out.printf("Alpha                : %d hari%n", hasil.getHariAlpha());
        System.out.printf("Total jam lembur     : %d jam%n", hasil.getTotalJamLembur());
        System.out.printf("Gaji pokok           : Rp%,d%n", hasil.getGajiPokok());
        System.out.printf("Tunjangan tetap      : Rp%,d%n", hasil.getTunjangan());
        System.out.printf("Upah lembur          : Rp%,d%n", hasil.getUpahLembur());
        System.out.printf("Potongan alpha       : Rp%,d%n", hasil.getPotonganAlpha());
        System.out.printf("Potongan tambahan    : Rp%,d%n", hasil.getPotonganTambahan());
        System.out.printf("GAJI BERSIH          : Rp%,d%n", hasil.getGajiBersih());
    }

    private static Karyawan cariKaryawan(String id) {
        if (id == null) return null;
        return KARYAWAN.stream()
                .filter(k -> k.getId().equalsIgnoreCase(id.trim()))
                .findFirst().orElse(null);
    }

    // --- Helper methods untuk input konsol ---

    private static YearMonth bacaPeriode() {
        try {
            return YearMonth.parse(bacaTeks("Periode (YYYY-MM): "));
        } catch (DateTimeParseException e) {
            System.out.println("Format periode tidak valid. Contoh format yang benar: 2026-10.");
            return null;
        }
    }

    private static String bacaTeks(String prompt) {
        System.out.print(prompt);
        return INPUT.nextLine().trim();
    }

    private static long bacaLongNonNegatif(String prompt) {
        while (true) {
            try {
                long nilai = Long.parseLong(bacaTeks(prompt));
                if (nilai >= 0) return nilai;
            } catch (NumberFormatException ignored) {
                // Diabaikan, minta input ulang
            }
            System.out.println("Masukkan bilangan bulat nol atau lebih.");
        }
    }

    private static int bacaIntNonNegatif(String prompt) {
        while (true) {
            try {
                int nilai = Integer.parseInt(bacaTeks(prompt));
                if (nilai >= 0) return nilai;
            } catch (NumberFormatException ignored) {
                // Diabaikan, minta input ulang
            }
            System.out.println("Masukkan bilangan bulat nol atau lebih.");
        }
    }
}
