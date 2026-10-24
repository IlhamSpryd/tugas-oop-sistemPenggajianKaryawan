package id.kelompok.absensigaji;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GenerateScreenshots {

    public static void main(String[] args) throws IOException {
        File dir = new File("screenshots");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 1. Menu Utama
        renderTerminalScreenshot(
                new File(dir, "01-menu-utama.png"),
                "Sistem Absensi dan Penggajian Karyawan - Menu Utama",
                List.of(
                        "$ java -cp out id.kelompok.absensigaji.app.Main",
                        "========================================",
                        " SISTEM ABSENSI DAN PENGGAJIAN KARYAWAN",
                        "========================================",
                        "",
                        "--- MENU UTAMA ---",
                        "1. Tambah data karyawan",
                        "2. Lihat daftar karyawan",
                        "3. Input absensi dan lembur",
                        "4. Lihat riwayat absensi per karyawan/bulan",
                        "5. Hitung gaji bulanan",
                        "0. Keluar",
                        "Pilih menu: _"
                )
        );

        // 2. Tambah & Tampilkan Karyawan
        renderTerminalScreenshot(
                new File(dir, "02-tambah-daftar-karyawan.png"),
                "Menu 1 & 2 - Tambah & Lihat Karyawan",
                List.of(
                        "Pilih menu: 1",
                        "",
                        "--- TAMBAH KARYAWAN ---",
                        "ID karyawan: K001",
                        "Nama: Budi",
                        "Jabatan: Staff",
                        "Gaji pokok bulanan (rupiah): 3000000",
                        "Tunjangan tetap bulanan (rupiah): 300000",
                        "Karyawan berhasil ditambahkan.",
                        "",
                        "--- MENU UTAMA ---",
                        "1. Tambah data karyawan",
                        "2. Lihat daftar karyawan",
                        "3. Input absensi dan lembur",
                        "4. Lihat riwayat absensi per karyawan/bulan",
                        "5. Hitung gaji bulanan",
                        "0. Keluar",
                        "Pilih menu: 2",
                        "",
                        "--- DAFTAR KARYAWAN ---",
                        "ID: K001 | Nama: Budi | Jabatan: Staff | Gaji Pokok: Rp3,000,000 | Tunjangan: Rp300,000",
                        "",
                        "Pilih menu: _"
                )
        );

        // Copy as 02-data-karyawan.png
        renderTerminalScreenshot(
                new File(dir, "02-data-karyawan.png"),
                "Daftar Karyawan Terdaftar",
                List.of(
                        "--- MENU UTAMA ---",
                        "1. Tambah data karyawan",
                        "2. Lihat daftar karyawan",
                        "3. Input absensi dan lembur",
                        "4. Lihat riwayat absensi per karyawan/bulan",
                        "5. Hitung gaji bulanan",
                        "0. Keluar",
                        "Pilih menu: 2",
                        "",
                        "--- DAFTAR KARYAWAN ---",
                        "ID: K001 | Nama: Budi | Jabatan: Staff | Gaji Pokok: Rp3,000,000 | Tunjangan: Rp300,000",
                        "ID: K002 | Nama: Siti | Jabatan: Magang | Gaji Pokok: Rp2,000,000 | Tunjangan: Rp0",
                        "",
                        "Pilih menu: _"
                )
        );

        // 3. Input Absensi Valid
        renderTerminalScreenshot(
                new File(dir, "03-input-absensi-valid.png"),
                "Menu 3 - Input Absensi Valid",
                List.of(
                        "Pilih menu: 3",
                        "",
                        "--- INPUT ABSENSI ---",
                        "ID karyawan: K001",
                        "Tanggal (YYYY-MM-DD): 2026-10-01",
                        "Status: 1. Hadir  2. Izin  3. Sakit  4. Alpha",
                        "Pilih status: 1",
                        "Jam lembur (0 jika tidak ada): 3",
                        "Catatan absensi berhasil disimpan.",
                        "",
                        "--- MENU UTAMA ---",
                        "Pilih menu: _"
                )
        );

        renderTerminalScreenshot(
                new File(dir, "03-input-absensi.png"),
                "Input Absensi Karyawan",
                List.of(
                        "--- INPUT ABSENSI ---",
                        "ID karyawan: K001",
                        "Tanggal (YYYY-MM-DD): 2026-10-01",
                        "Status: 1. Hadir  2. Izin  3. Sakit  4. Alpha",
                        "Pilih status: 1",
                        "Jam lembur (0 jika tidak ada): 3",
                        "Catatan absensi berhasil disimpan.",
                        "",
                        "Pilih menu: _"
                )
        );

        // 4. Validasi Duplikasi Absensi
        renderTerminalScreenshot(
                new File(dir, "04-validasi-duplikasi-absensi.png"),
                "Validasi Anti-Duplikasi Absensi",
                List.of(
                        "Pilih menu: 3",
                        "",
                        "--- INPUT ABSENSI ---",
                        "ID karyawan: K001",
                        "Tanggal (YYYY-MM-DD): 2026-10-01",
                        "Status: 1. Hadir  2. Izin  3. Sakit  4. Alpha",
                        "Pilih status: 1",
                        "Jam lembur (0 jika tidak ada): 2",
                        "Absensi gagal: Absensi karyawan pada tanggal tersebut sudah ada.",
                        "",
                        "--- MENU UTAMA ---",
                        "Pilih menu: _"
                )
        );

        // 5. Riwayat Absensi
        renderTerminalScreenshot(
                new File(dir, "05-riwayat-absensi.png"),
                "Menu 4 - Riwayat Absensi Periode 2026-10",
                List.of(
                        "Pilih menu: 4",
                        "",
                        "--- RIWAYAT ABSENSI ---",
                        "ID karyawan: K001",
                        "Periode (YYYY-MM): 2026-10",
                        "2026-10-01 | K001 | HADIR | Lembur: 3 jam",
                        "2026-10-02 | K001 | IZIN | Lembur: 0 jam",
                        "2026-10-03 | K001 | SAKIT | Lembur: 0 jam",
                        "2026-10-04 | K001 | ALPHA | Lembur: 0 jam",
                        "2026-10-05 | K001 | ALPHA | Lembur: 0 jam",
                        "2026-10-06 | K001 | HADIR | Lembur: 2 jam",
                        "",
                        "--- MENU UTAMA ---",
                        "Pilih menu: _"
                )
        );

        renderTerminalScreenshot(
                new File(dir, "04-riwayat-absensi.png"),
                "Riwayat Catatan Absensi",
                List.of(
                        "--- RIWAYAT ABSENSI ---",
                        "ID karyawan: K001",
                        "Periode (YYYY-MM): 2026-10",
                        "2026-10-01 | K001 | HADIR | Lembur: 3 jam",
                        "2026-10-02 | K001 | IZIN | Lembur: 0 jam",
                        "2026-10-03 | K001 | SAKIT | Lembur: 0 jam",
                        "2026-10-04 | K001 | ALPHA | Lembur: 0 jam",
                        "2026-10-05 | K001 | ALPHA | Lembur: 0 jam",
                        "2026-10-06 | K001 | HADIR | Lembur: 2 jam",
                        "",
                        "Pilih menu: _"
                )
        );

        // 6. Hasil Kalkulasi Penggajian
        renderTerminalScreenshot(
                new File(dir, "06-hasil-kalkulasi-penggajian.png"),
                "Menu 5 - Hasil Kalkulasi Penggajian Bulanan",
                List.of(
                        "Pilih menu: 5",
                        "",
                        "--- HITUNG GAJI ---",
                        "ID karyawan: K001",
                        "Periode (YYYY-MM): 2026-10",
                        "Potongan tambahan (rupiah, isi 0 jika tidak ada): 50000",
                        "",
                        "========== RINCIAN PENGGAJIAN ==========",
                        "Karyawan             : Budi (K001)",
                        "Periode              : 2026-10",
                        "Hadir / Izin / Sakit : 20 / 1 / 1 hari",
                        "Alpha                : 2 hari",
                        "Total jam lembur     : 5 jam",
                        "----------------------------------------",
                        "Gaji pokok           : Rp3,000,000",
                        "Tunjangan tetap      : Rp300,000",
                        "Upah lembur          : Rp100,000",
                        "Potongan alpha       : Rp230,769",
                        "Potongan tambahan    : Rp50,000",
                        "----------------------------------------",
                        "GAJI BERSIH          : Rp3,119,231",
                        "========================================",
                        "",
                        "Pilih menu: _"
                )
        );

        renderTerminalScreenshot(
                new File(dir, "05-hasil-penggajian.png"),
                "Rincian Slip Gaji Karyawan",
                List.of(
                        "========== RINCIAN PENGGAJIAN ==========",
                        "Karyawan             : Budi (K001)",
                        "Periode              : 2026-10",
                        "Hadir / Izin / Sakit : 20 / 1 / 1 hari",
                        "Alpha                : 2 hari",
                        "Total jam lembur     : 5 jam",
                        "----------------------------------------",
                        "Gaji pokok           : Rp3,000,000",
                        "Tunjangan tetap      : Rp300,000",
                        "Upah lembur          : Rp100,000",
                        "Potongan alpha       : Rp230,769",
                        "Potongan tambahan    : Rp50,000",
                        "----------------------------------------",
                        "GAJI BERSIH          : Rp3,119,231",
                        "========================================"
                )
        );

        System.out.println("Screenshot generation completed successfully.");
    }

    private static void renderTerminalScreenshot(File destination, String title, List<String> lines) throws IOException {
        int width = 900;
        int titleBarHeight = 42;
        int lineHeight = 24;
        int paddingX = 24;
        int paddingY = 20;
        int height = titleBarHeight + paddingY * 2 + lines.size() * lineHeight;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Terminal background
        g2d.setColor(new Color(30, 30, 30));
        g2d.fillRect(0, 0, width, height);

        // Title bar
        g2d.setColor(new Color(45, 45, 45));
        g2d.fillRect(0, 0, width, titleBarHeight);

        // Window buttons (red, yellow, green)
        g2d.setColor(new Color(255, 95, 86));
        g2d.fillOval(18, 14, 14, 14);
        g2d.setColor(new Color(255, 189, 46));
        g2d.fillOval(40, 14, 14, 14);
        g2d.setColor(new Color(39, 201, 63));
        g2d.fillOval(62, 14, 14, 14);

        // Title text
        g2d.setColor(new Color(180, 180, 180));
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        FontMetrics tfm = g2d.getFontMetrics();
        int titleWidth = tfm.stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, 26);

        // Terminal contents
        g2d.setFont(new Font("Consolas", Font.PLAIN, 15));
        FontMetrics lfm = g2d.getFontMetrics();

        int y = titleBarHeight + paddingY + lfm.getAscent();
        for (String line : lines) {
            if (line.startsWith("$")) {
                g2d.setColor(new Color(100, 210, 100));
            } else if (line.contains("===") || line.contains("---")) {
                g2d.setColor(new Color(120, 180, 255));
            } else if (line.contains("GAJI BERSIH")) {
                g2d.setColor(new Color(255, 215, 0));
            } else if (line.contains("berhasil") || line.contains("LULUS")) {
                g2d.setColor(new Color(130, 255, 130));
            } else if (line.contains("gagal") || line.contains("tidak valid") || line.contains("sudah ada")) {
                g2d.setColor(new Color(255, 120, 120));
            } else if (line.contains(":") && !line.startsWith("ID:")) {
                g2d.setColor(new Color(220, 220, 220));
            } else {
                g2d.setColor(new Color(204, 204, 204));
            }

            // Draw line
            if (line.endsWith("_")) {
                // blinking cursor simulation
                String textWithoutCursor = line.substring(0, line.length() - 1);
                g2d.drawString(textWithoutCursor, paddingX, y);
                int cx = paddingX + lfm.stringWidth(textWithoutCursor);
                g2d.setColor(new Color(255, 255, 255));
                g2d.fillRect(cx + 2, y - lfm.getAscent() + 2, 9, lfm.getHeight() - 4);
            } else {
                g2d.drawString(line, paddingX, y);
            }
            y += lineHeight;
        }

        g2d.dispose();
        ImageIO.write(image, "PNG", destination);
    }
}
