# Pembagian Tugas & Tata Kelola Kolaborasi Git

Dokumen ini mendefinisikan pembagian tugas, matriks kepemilikan file (*code ownership*), batas tanggung jawab teknis, serta *workflow* kolaborasi Git/GitHub untuk tim proyek **Sistem Absensi dan Penggajian Karyawan**.

---

## 1. Matriks Kepemilikan File & Batas Tanggung Jawab

| Anggota Tim | Modul / Area | File Utama yang Dikelola | Batas Tanggung Jawab Teknis |
|---|---|---|---|
| **Ilham Sepriyadi** | `app` & `employee` | - `src/main/java/id/kelompok/absensigaji/app/Main.java`<br>- `src/main/java/id/kelompok/absensigaji/employee/Karyawan.java`<br>- `README.md`<br>- `docs/PEMBAGIAN-TUGAS.md`<br>- `docs/KONTRAK-ANTARMODUL.md` | - Implementasi entitas `Karyawan` dan validasi datanya.<br>- Menghubungkan seluruh modul pada menu konsol `Main`.<br>- Mengelola alur program, koordinasi repository, dan integrasi antarmodul.<br>- Menulis dokumentasi sistem dan pengujian integrasi *end-to-end*. |
| **Ade** | `attendance` | - `src/main/java/id/kelompok/absensigaji/attendance/StatusAbsensi.java`<br>- `src/main/java/id/kelompok/absensigaji/attendance/CatatanAbsensi.java`<br>- `src/main/java/id/kelompok/absensigaji/attendance/AbsensiService.java` | - Implementasi enum status, model catatan kehadiran, dan service absensi.<br>- Validasi anti-duplikasi absensi pada tanggal yang sama per karyawan.<br>- Validasi jam lembur hanya untuk status `HADIR`.<br>- Dokumentasi modul absensi dan pengujian unit absensi. |
| **Andri** | `payroll` | - `src/main/java/id/kelompok/absensigaji/payroll/HasilPenggajian.java`<br>- `src/main/java/id/kelompok/absensigaji/payroll/PenggajianService.java` | - Implementasi model rincian payroll dan service perhitungan gaji.<br>- Kalkulasi upah lembur, proporsional potongan Alpha (standar 26 hari kerja), dan gaji bersih non-negatif.<br>- Dokumentasi modul penggajian dan contoh hitung manual.<br>- Pengujian unit kalkulasi payroll. |

> [!IMPORTANT]
> Setiap anggota bertanggung jawab atas kode dan dokumentasi modulnya masing-masing. Tidak ada anggota yang hanya menulis dokumentasi tanpa kontribusi teknis. Anggota dilarang mengubah file milik rekan lain tanpa koordinasi dan persetujuan via Pull Request.

---

## 2. Rincian Tanggung Jawab Anggota

### A. Ilham Sepriyadi — Koordinator, Employee, dan Integrasi Aplikasi
- **Implementasi:**
  - `employee/Karyawan.java`: Konstruktor, validasi masukan (ID unik, nama, jabatan, nominal gaji non-negatif), dan format `toString()`.
  - `app/Main.java`: Inisialisasi service, menu navigasi terminal, interaksi I/O, penanganan eksepsi konsol, dan orkestrasinya dengan modul Ade dan Andri.
- **Dokumentasi & Pengujian:**
  - Menjaga kejelasan `README.md`, `docs/PEMBAGIAN-TUGAS.md`, dan `docs/KONTRAK-ANTARMODUL.md`.
  - Menguji skenario TC-01 s/d TC-03 (karyawan) dan TC-15 (integrasi *end-to-end*).
  - Melakukan review dan merge Pull Request dari Ade dan Andri ke `main`.

### B. Ade — Modul Absensi (Attendance)
- **Implementasi:**
  - `attendance/StatusAbsensi.java`: Enum status (`HADIR`, `IZIN`, `SAKIT`, `ALPHA`).
  - `attendance/CatatanAbsensi.java`: Konstruktor, validasi parameter, integritas status dan jam lembur.
  - `attendance/AbsensiService.java`: Penyimpanan riwayat absensi, pencegahan duplikasi per hari, filter berdasarkan ID dan periode (`YearMonth`), serta utilitas rekapitulasi status dan lembur.
- **Dokumentasi & Pengujian:**
  - Menulis dokumentasi teknis modul absensi pada `docs/DESAIN-OOP.md`.
  - Menguji skenario TC-04 s/d TC-08 (absensi) pada `docs/TEST-CASE.md`.

### C. Andri — Modul Penggajian (Payroll)
- **Implementasi:**
  - `payroll/HasilPenggajian.java`: Value object penyimpan seluruh komponen rincian kompensasi dan potongan.
  - `payroll/PenggajianService.java`: Logika kalkulasi gaji berdasarkan aturan simulasi (potongan alpha dengan pembagi 26 hari, tarif lembur Rp20.000/jam, proteksi gaji bersih >= Rp0).
- **Dokumentasi & Pengujian:**
  - Menulis dokumentasi teknis dan contoh perhitungan manual pada `docs/DESAIN-OOP.md` dan `docs/PANDUAN-DOKUMENTASI.md`.
  - Menguji skenario TC-09 s/d TC-14 (kalkulasi payroll) pada `docs/TEST-CASE.md`.

---

## 3. Workflow Kolaborasi Git & GitHub

Untuk mencegah konflik merge dan memastikan stabilitas kode seperti di industri perangkat lunak, tim menggunakan aturan berikut:

### Aturan Utama
1. **Branch `main` Dilindungi:** Branch `main` selalu berisi versi stabil yang wajib berhasil dikompilasi (`javac`). Tidak ada anggota yang diperbolehkan melakukan *direct push* ke `main`.
2. **Feature Branching:** Setiap fitur baru atau perbaikan dikerjakan pada branch terpisah yang dibuat dari `main` terbaru.
3. **Pull Request (PR) & Code Review:** Semua perubahan digabungkan ke `main` hanya melalui Pull Request. PR wajib ditinjau dan disetujui (*approved*) oleh minimal satu anggota lain sebelum dimerge.
4. **Verifikasi Kompilasi Sebelum Merge:** Jangan menggabungkan kode yang menyebabkan error kompilasi atau melanggar kontrak antarmodul.

### Branch Awal yang Direkomendasikan
- **Ilham:** `feat/employee-and-app-ilham`
- **Ade:** `feat/attendance-module-ade`
- **Andri:** `feat/payroll-module-andri`

### Standar Pesan Commit (Conventional Commits)
Gunakan format pesan commit yang deskriptif dan konsisten:
- `feat: add employee model validation`
- `feat: implement attendance record storage and duplicate check`
- `feat: implement payroll calculation logic`
- `docs: update oop design and manual payroll calculation`
- `test: verify duplicate attendance prevention`
- `fix: resolve negative net salary edge case`

---

## 4. Panduan Langkah Kerja Mandiri Tiap Anggota

### Langkah 1: Sinkronisasi Repository Lokal
```bash
git switch main
git pull origin main
```

### Langkah 2: Membuat Branch Fitur
```bash
# Contoh untuk Ade:
git switch -c feat/attendance-module-ade

# Contoh untuk Andri:
git switch -c feat/payroll-module-andri

# Contoh untuk Ilham:
git switch -c feat/employee-and-app-ilham
```

### Langkah 3: Mengimplementasikan Kode & Menguji Kompilasi
Lakukan perubahan hanya pada file yang menjadi tanggung jawab Anda. Selalu uji kompilasi secara lokal:
```bash
javac -d out $(find src/main/java -name "*.java")
```

### Langkah 4: Melakukan Commit dan Push
```bash
git status
git add src/main/java/id/kelompok/absensigaji/...
git commit -m "feat: implement ..."
git push -u origin feat/nama-branch-anda
```

### Langkah 5: Membuat Pull Request di GitHub
1. Buka repository di GitHub.
2. Klik tombol **Compare & pull request**.
3. Isi deskripsi PR mengikuti template pada `.github/pull_request_template.md`.
4. Tandai rekan tim sebagai **Reviewer**.
5. Setelah disetujui dan lulus kompilasi, lakukan merge ke `main`.
6. Kembali ke branch `main` lokal dan tarik perubahan terbaru (`git pull origin main`).
