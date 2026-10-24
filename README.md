# Sistem Absensi dan Penggajian Karyawan (Java OOP)

Proyek kelompok mata kuliah **Pemrograman Berorientasi Objek**. Proyek ini dirancang sebagai kerangka kerja pembelajaran (*learning skeleton/framework*) bertaraf industri untuk mengimplementasikan sistem pencatatan kehadiran karyawan dan simulasi penggajian bulanan berbasis konsol menggunakan bahasa pemrograman Java murni.

---

## Anggota Kelompok & Kepemilikan Modul

1. **Ilham Sepriyadi** — Koordinator proyek, integrasi sistem, modul `employee` (`Karyawan.java`), menu konsol `app` (`Main.java`), dan dokumentasi arsitektur.
2. **Ade** — Modul `attendance` (`StatusAbsensi.java`, `CatatanAbsensi.java`, `AbsensiService.java`), validasi kehadiran, anti-duplikasi, dan pengujian modul absensi.
3. **Andri** — Modul `payroll` (`HasilPenggajian.java`, `PenggajianService.java`), logika kalkulasi gaji, upah lembur, potongan alpha, dan pengujian payroll.

Rincian batas kepemilikan dan panduan alur kerja Git tersedia di [`docs/PEMBAGIAN-TUGAS.md`](docs/PEMBAGIAN-TUGAS.md).

---

## Struktur Repositori

```text
sistem-absensi-gaji-java/
├── README.md
├── .gitignore
├── src/
│   └── main/
│       └── java/
│           └── id/kelompok/absensigaji/
│               ├── app/
│               │   └── Main.java
│               ├── employee/
│               │   └── Karyawan.java
│               ├── attendance/
│               │   ├── StatusAbsensi.java
│               │   ├── CatatanAbsensi.java
│               │   └── AbsensiService.java
│               └── payroll/
│                   ├── HasilPenggajian.java
│                   └── PenggajianService.java
├── docs/
│   ├── PEMBAGIAN-TUGAS.md
│   ├── DESAIN-OOP.md
│   ├── KONTRAK-ANTARMODUL.md
│   ├── PANDUAN-DOKUMENTASI.md
│   └── TEST-CASE.md
├── screenshots/
│   └── README.md
└── .github/
    └── pull_request_template.md
```

---

## Indeks Dokumentasi

- [**docs/PEMBAGIAN-TUGAS.md**](docs/PEMBAGIAN-TUGAS.md): Matriks kepemilikan file, batas tanggung jawab anggota tim, branch fitur, dan panduan alur kerja Git/GitHub.
- [**docs/KONTRAK-ANTARMODUL.md**](docs/KONTRAK-ANTARMODUL.md): Definisi API publik, signature method minimum yang disepakati, aliran data antarmodul, dan tata kelola perubahan kontrak.
- [**docs/DESAIN-OOP.md**](docs/DESAIN-OOP.md): Penjelasan pilar OOP (Encapsulation, Class & Object, Separation of Concerns), peta class, relasi antarmodul, dan alasan pemilihan arsitektur *package-by-feature*.
- [**docs/PANDUAN-DOKUMENTASI.md**](docs/PANDUAN-DOKUMENTASI.md): Pedoman penulisan penjelasan kode dan walkthrough contoh perhitungan manual simulasi gaji.
- [**docs/TEST-CASE.md**](docs/TEST-CASE.md): Matriks 15 skenario pengujian unit dan integrasi beserta penanggung jawabnya.
- [**.github/pull_request_template.md**](.github/pull_request_template.md): Template Pull Request standar untuk proses code review antaranggota.

---

## Aturan Simulasi Penggajian

Aturan ini ditetapkan sebagai skenario simulasi akademik untuk diimplementasikan oleh Andri:
- **Hari kerja standar bulanan:** 26 hari per bulan.
- **Potongan Alpha:**
  $$\text{Potongan Alpha} = \text{round}\left(\frac{\text{Gaji Pokok}}{26}\right) \times \text{Jumlah Hari Alpha}$$
- **Izin dan Sakit:** Tidak memotong gaji pada simulasi ini.
- **Upah Lembur:** $\text{Jumlah Jam Lembur} \times \text{Rp20.000}$ (hanya dapat diperoleh jika berstatus `HADIR`).
- **Gaji Bersih:** $\max(0, \text{Gaji Pokok} + \text{Tunjangan Tetap} + \text{Upah Lembur} - \text{Potongan Alpha} - \text{Potongan Tambahan})$.

---

## Cara Kompilasi dan Menjalankan

Proyek ini dibangun menggunakan **Java Standard Library (JDK 17+)** tanpa ketergantungan library eksternal.

### 1. Kompilasi (Linux / macOS / PowerShell)
Dari direktori utama repositori:
```bash
javac -d out $(find src/main/java -name "*.java")
```
*Untuk Windows PowerShell:*
```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
```

### 2. Menjalankan Program
```bash
java -cp out id.kelompok.absensigaji.app.Main
```

---

## Status Proyek Saat Ini

> [!NOTE]
> Seluruh modul sistem telah selesai diimplementasikan secara penuh dan berhasil diverifikasi:
> - **Modul Employee & App (Ilham Sepriyadi):** Entitas `Karyawan`, validasi nilai, navigasi menu konsol interaktif `Main`, dan orkestrasi antarmodul.
> - **Modul Attendance (Ade):** Enum `StatusAbsensi`, model `CatatanAbsensi`, serta layanan `AbsensiService` dengan validasi anti-duplikasi harian dan filter periode.
> - **Modul Payroll (Andri):** DTO `HasilPenggajian` dan layanan `PenggajianService` dengan kalkulasi proporsional potongan Alpha (standar 26 hari kerja), upah lembur, dan proteksi gaji bersih non-negatif.
> - **Pengujian:** Seluruh 15 skenario pengujian unit dan integrasi (**TC-01** s/d **TC-15**) pada `docs/TEST-CASE.md` telah dijalankan dan berstatus **Lulus** (15/15).
> - **Bukti Visual:** Tangkapan layar terminal asli telah didokumentasikan pada folder `screenshots/`.
