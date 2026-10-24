# Sistem Absensi dan Penggajian Karyawan (Java OOP)

Proyek kelompok mata kuliah **Pemrograman Berorientasi Objek**. Aplikasi konsol ini mendemonstrasikan pencatatan absensi karyawan dan simulasi penggajian bulanan yang mencakup gaji pokok, tunjangan, lembur, dan potongan.

## Anggota kelompok

1. Ilham Sepriyadi — koordinator, integrasi aplikasi, data karyawan, dan dokumentasi utama.
2. Ade — modul absensi dan validasi pencatatan kehadiran.
3. Andri — modul perhitungan gaji, lembur, potongan, dan pengujian payroll.

Rincian pekerjaan ada di [`docs/PEMBAGIAN-TUGAS.md`](docs/PEMBAGIAN-TUGAS.md).

## Fitur

- Menambah dan melihat data karyawan.
- Mencatat status absensi: Hadir, Izin, Sakit, atau Alpha.
- Mencatat jam lembur untuk hari berstatus Hadir.
- Melihat riwayat absensi berdasarkan ID karyawan dan bulan.
- Menghitung rincian gaji bulanan: gaji pokok, tunjangan, upah lembur, potongan absensi, potongan tambahan, dan gaji bersih.
- Validasi ID karyawan, format tanggal, nilai gaji, jam lembur, dan duplikasi absensi pada tanggal yang sama.

## Teknologi

- Java 17 atau lebih baru.
- Java Standard Library saja; tidak memerlukan framework atau dependency eksternal.
- Penyimpanan masih di memori. Data akan hilang saat program ditutup; database dan penyimpanan permanen berada di luar ruang lingkup versi awal ini.

## Cara menjalankan

Pastikan Java sudah terpasang:

```bash
java -version
javac -version
```

Dari direktori utama repository, kompilasi semua file:

```bash
javac -d out $(find src/main/java -name "*.java")
```

Jalankan program:

```bash
java -cp out id.kelompok.absensigaji.app.Main
```

**Windows PowerShell:** jika perintah `find` tidak tersedia atau berbeda perilakunya, gunakan:

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out id.kelompok.absensigaji.app.Main
```

## Aturan simulasi penggajian

Aturan ini ditetapkan agar perilaku program konsisten dan mudah dijelaskan saat presentasi. Ini **simulasi tugas kuliah**, bukan kalkulator kepatuhan hukum ketenagakerjaan atau payroll produksi.

- Hari kerja standar untuk perhitungan potongan alpha: 26 hari per bulan.
- Potongan alpha = `(gaji pokok bulanan / 26) × jumlah hari Alpha`, dibulatkan ke rupiah terdekat.
- Izin dan sakit tidak mengurangi gaji dalam simulasi ini.
- Upah lembur = `jumlah jam lembur × Rp20.000`.
- Gaji bersih = `gaji pokok + tunjangan tetap + upah lembur − potongan alpha − potongan tambahan`.
- Gaji bersih tidak ditampilkan sebagai nilai negatif; batas minimumnya Rp0.
- Potongan tambahan dimasukkan saat menghitung payroll dan tidak disimpan sebagai data permanen.
- Absensi disaring berdasarkan bulan kalender yang dipilih.

Nilai 26 hari dan Rp20.000 per jam adalah parameter simulasi, bukan klaim bahwa aturan tersebut berlaku untuk semua perusahaan.

## Struktur proyek

```text
sistem-absensi-gaji-java/
├── README.md
├── .gitignore
├── src/main/java/id/kelompok/absensigaji/
│   ├── app/Main.java
│   ├── model/
│   │   ├── Karyawan.java
│   │   ├── StatusAbsensi.java
│   │   ├── CatatanAbsensi.java
│   │   └── HasilPenggajian.java
│   └── service/
│       ├── AbsensiService.java
│       └── PenggajianService.java
├── docs/
│   ├── PEMBAGIAN-TUGAS.md
│   ├── DESAIN-OOP.md
│   ├── PANDUAN-DOKUMENTASI.md
│   ├── TEST-CASE.md
│   └── PANDUAN-GITHUB.md
└── screenshots/
    └── README.md
```

## Konsep OOP yang ditunjukkan

- **Encapsulation:** atribut model dibuat `private` dan diakses melalui method yang sesuai.
- **Class dan object:** `Karyawan`, `CatatanAbsensi`, dan `HasilPenggajian` menjadi cetak biru objek domain.
- **Constructor dan validasi:** objek dicek agar tidak dibuat dengan nilai yang tidak masuk akal.
- **Enum:** `StatusAbsensi` membatasi status ke empat pilihan yang sah.
- **Separation of concerns:** `Main` menangani interaksi konsol, model menyimpan representasi data, dan service memuat aturan bisnis.

Jangan menyatakan bahwa program menggunakan inheritance atau polymorphism apabila kedua konsep tersebut belum benar-benar diterapkan di source code.

## Bukti pengujian dan dokumentasi

- Simpan screenshot hasil running yang terbaca di folder [`screenshots/`](screenshots/).
- Isi penjelasan per kelas, alur logika, rumus, validasi, dan hasil pengujian di folder [`docs/`](docs/).
- Gunakan [`docs/TEST-CASE.md`](docs/TEST-CASE.md) sebagai checklist minimum.
- Dokumentasi merupakan bagian penting penilaian. Jangan hanya menempelkan source code; jelaskan *mengapa* class dan method dibuat serta bagaimana data mengalir.

## Status

Starter project untuk dikembangkan dan ditinjau oleh seluruh anggota kelompok. Pastikan tim memahami aturan perhitungan dan mampu menjelaskan setiap bagian kode saat demonstrasi.
