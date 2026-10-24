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
 * Titik masuk utama aplikasi (Main Entry Point) dan antarmuka menu konsol.
 * Mengintegrasikan modul employee (Ilham), attendance (Ade), dan payroll (Andri).
 *
 * Penanggung jawab: Ilham Sepriyadi
 */
public class Main {
    private static final Scanner INPUT = new Scanner(System.in);

    // Repository in-memory untuk menyimpan data karyawan
    private static final List<Karyawan> KARYAWAN = new ArrayList<>();

    // Inisialisasi service antarmodul
    private static final AbsensiService ABSENSI = new AbsensiService();
    private static final PenggajianService PENGGAJIAN = new PenggajianService(ABSENSI);

    public static void main(String[] args) {
        boolean berjalan = true;
        System.out.println("========================================");
        System.out.println(" SISTEM ABSENSI DAN PENGGAJIAN KARYAWAN");
        System.out.println("========================================");

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
                    System.out.println("Program selesai. Data pada versi ini disimpan di memori.");
                }
                default -> System.out.println("Pilihan tidak valid. Silakan pilih nomor menu yang tersedia.");
            }
            System.out.println();
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\n--- MENU UTAMA ---");
        System.out.println("1. Tambah data karyawan");
        System.out.println("2. Lihat daftar karyawan");
        System.out.println("3. Input absensi dan lembur");
        System.out.println("4. Lihat riwayat absensi per karyawan/bulan");
        System.out.println("5. Hitung gaji bulanan");
        System.out.println("0. Keluar");
    }

    private static void tambahKaryawan() {
        System.out.println("\n--- TAMBAH KARYAWAN ---");
        // TODO: Ilham - Implementasikan alur penambahan karyawan:
        // 1. Minta input id, nama, jabatan, gajiPokok, dan tunjangan.
        // 2. Periksa apakah id sudah terdaftar menggunakan cariKaryawan(id).
        // 3. Instansiasi objek Karyawan baru dan simpan ke koleksi KARYAWAN.
        // 4. Tangani kemungkinan IllegalArgumentException dengan pesan ramah pengguna.
        System.out.println("[TODO] Fitur tambahKaryawan belum diimplementasikan.");
    }

    private static void tampilkanKaryawan() {
        System.out.println("\n--- DAFTAR KARYAWAN ---");
        // TODO: Ilham - Implementasikan penayangan daftar seluruh karyawan:
        // 1. Periksa apakah koleksi KARYAWAN kosong.
        // 2. Jika tidak kosong, lakukan iterasi dan cetak data setiap karyawan ke layar.
        System.out.println("[TODO] Fitur tampilkanKaryawan belum diimplementasikan.");
    }

    private static void inputAbsensi() {
        System.out.println("\n--- INPUT ABSENSI ---");
        // TODO: Ilham - Implementasikan alur input absensi harian:
        // 1. Validasi apakah ada karyawan terdaftar.
        // 2. Minta input ID karyawan dan pastikan karyawan ditemukan lewat cariKaryawan(id).
        // 3. Minta input tanggal absensi (format YYYY-MM-DD).
        // 4. Minta pilihan status absensi (1. Hadir, 2. Izin, 3. Sakit, 4. Alpha).
        // 5. Jika status Hadir, minta input jam lembur (0 jika tidak lembur).
        // 6. Panggil ABSENSI.catatAbsensi(id, tanggal, status, jamLembur).
        // 7. Tangani kemungkinan DateTimeParseException dan IllegalArgumentException.
        System.out.println("[TODO] Fitur inputAbsensi belum diimplementasikan.");
    }

    private static void tampilkanRiwayatAbsensi() {
        System.out.println("\n--- RIWAYAT ABSENSI ---");
        // TODO: Ilham - Implementasikan penayangan riwayat absensi:
        // 1. Minta input ID karyawan dan validasi keberadaannya.
        // 2. Minta input periode (format YYYY-MM).
        // 3. Ambil data catatan melalui ABSENSI.getCatatan(id, periode).
        // 4. Tampilkan daftar catatan absensi ke layar.
        System.out.println("[TODO] Fitur tampilkanRiwayatAbsensi belum diimplementasikan.");
    }

    private static void hitungGaji() {
        System.out.println("\n--- HITUNG GAJI ---");
        // TODO: Ilham - Implementasikan alur kalkulasi gaji:
        // 1. Minta input ID karyawan dan pastikan karyawan ditemukan lewat cariKaryawan(id).
        // 2. Minta input periode (format YYYY-MM).
        // 3. Minta input potongan tambahan (nominal rupiah, >= 0).
        // 4. Panggil PENGGAJIAN.hitungGaji(karyawan, periode, potonganTambahan).
        // 5. Cetak rincian hasil perhitungan menggunakan method tampilkanHasilGaji(hasil).
        System.out.println("[TODO] Fitur hitungGaji belum diimplementasikan.");
    }

    private static void tampilkanHasilGaji(HasilPenggajian hasil) {
        // TODO: Ilham - Format dan cetak ringkasan slip gaji karyawan secara rapi ke konsol.
    }

    private static Karyawan cariKaryawan(String id) {
        // TODO: Ilham - Cari karyawan berdasarkan ID pada koleksi KARYAWAN (case-insensitive)
        throw new UnsupportedOperationException("TODO: cariKaryawan() belum diimplementasikan.");
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
