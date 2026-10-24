# Pembagian Tugas Kelompok

**Anggota:** Ilham Sepriyadi, Ade, dan Andri.

Prinsip pembagian: setiap anggota memiliki file utama yang jelas, menguji modulnya sendiri, dan menulis dokumentasi untuk kode yang dibuat. Semua anggota wajib memahami alur aplikasi secara keseluruhan karena dosen dapat menanyakan bagian di luar modul masing-masing.

## 1. Ilham Sepriyadi — Koordinator dan integrasi aplikasi

**Tanggung jawab utama**
- Mengelola repository, membuat issue/branch, meninjau Pull Request, dan menjaga agar `main` selalu dapat dikompilasi.
- Mengembangkan `Karyawan.java` dan mengintegrasikan menu di `Main.java`.
- Menjaga konsistensi nama class, atribut, method, format input, dan output antarmodul.
- Menulis pengantar proyek, cara menjalankan program, gambaran alur sistem, dan penjelasan hubungan antarclass.
- Mengumpulkan screenshot program berjalan secara menyeluruh.

**Berkas utama**
- `src/main/java/id/kelompok/absensigaji/model/Karyawan.java`
- `src/main/java/id/kelompok/absensigaji/app/Main.java`
- `README.md`
- `docs/DESAIN-OOP.md` (bagian gambaran arsitektur dan alur program)

**Kriteria selesai**
- Program dapat dikompilasi dan menu utama berfungsi.
- Data karyawan dapat ditambah dan ditampilkan.
- Menu absensi dan penggajian terhubung dengan service Ade dan Andri.
- README berisi instruksi build/run dan ruang lingkup yang jujur.

## 2. Ade — Modul absensi

**Tanggung jawab utama**
- Mengembangkan enum status absensi, model catatan absensi, dan service untuk memasukkan serta mencari catatan.
- Memvalidasi duplikasi absensi untuk karyawan yang sama pada tanggal yang sama.
- Memvalidasi jam lembur tidak negatif dan hanya dapat diberikan pada status Hadir.
- Menguji skenario absensi valid dan tidak valid.
- Menulis dokumentasi atribut, constructor, method, validasi, dan alur pencatatan absensi.
- Mengambil screenshot input absensi dan riwayat absensi.

**Berkas utama**
- `src/main/java/id/kelompok/absensigaji/model/StatusAbsensi.java`
- `src/main/java/id/kelompok/absensigaji/model/CatatanAbsensi.java`
- `src/main/java/id/kelompok/absensigaji/service/AbsensiService.java`
- `docs/DESAIN-OOP.md` (bagian model dan service absensi)
- `docs/TEST-CASE.md` (bagian absensi)

**Kriteria selesai**
- Empat status dapat direkam.
- Absensi ganda pada tanggal yang sama ditolak.
- Data dapat dicari berdasarkan ID karyawan dan bulan.
- Dokumentasi menjelaskan mengapa validasi diletakkan pada model/service.

## 3. Andri — Modul penggajian

**Tanggung jawab utama**
- Mengembangkan model hasil penggajian dan service perhitungan gaji.
- Menerapkan rumus gaji pokok, tunjangan, upah lembur, potongan alpha, potongan tambahan, dan gaji bersih.
- Menguji perhitungan dengan angka contoh secara manual dan mencocokkannya dengan output program.
- Menjelaskan setiap rumus dan asumsi simulasi; jangan menyatakan rumus sebagai aturan hukum payroll.
- Mengambil screenshot hasil perhitungan gaji.

**Berkas utama**
- `src/main/java/id/kelompok/absensigaji/model/HasilPenggajian.java`
- `src/main/java/id/kelompok/absensigaji/service/PenggajianService.java`
- `docs/DESAIN-OOP.md` (bagian hasil dan service penggajian)
- `docs/TEST-CASE.md` (bagian penggajian)

**Kriteria selesai**
- Rincian penggajian per periode dapat dihitung dari absensi.
- Perhitungan upah lembur dan potongan alpha sesuai aturan di README.
- Potongan tambahan negatif ditolak dan gaji bersih tidak negatif.
- Ada minimal dua contoh hitung manual di dokumentasi.

## Kolaborasi dan batas tanggung jawab

- Ilham tidak boleh mengubah aturan rumus penggajian sepihak; perubahan dibahas bertiga dan dicatat di README.
- Ade dan Andri harus menyepakati nama/signature class dan method dengan Ilham sebelum mengubah kontrak yang dipakai `Main`.
- Semua anggota menambahkan penjelasan kode yang dibuat sendiri. Ilham menyunting gaya akhir, bukan menulis seluruh dokumentasi sendirian.
- Setiap anggota membuat minimal satu commit bermakna dan minimal satu Pull Request yang ditinjau anggota lain.
- Semua anggota mencoba menjalankan aplikasi dari branch `main` sebelum final.

## Rencana urutan kerja

1. **Fondasi:** sepakati aturan, struktur package, nama class, signature method, dan alur menu.
2. **Modul paralel:** Ade mengerjakan absensi; Andri mengerjakan payroll; Ilham mengerjakan data karyawan dan menu integrasi.
3. **Integrasi:** merge Pull Request satu per satu, kompilasi ulang, dan perbaiki konflik.
4. **Validasi:** jalankan test case dan hitung manual hasil contoh.
5. **Dokumentasi dan bukti:** lengkapi penjelasan source code, screenshot, dan README.
6. **Latihan presentasi:** setiap anggota menjelaskan satu modul sendiri dan alur sistem secara keseluruhan.
