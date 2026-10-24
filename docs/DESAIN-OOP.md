# Desain OOP dan Penjelasan Source Code

Dokumen ini perlu dilengkapi oleh seluruh anggota. Jangan berhenti pada definisi umum OOP; hubungkan konsep dengan class, atribut, dan method yang benar-benar ada di repository.

## 1. Gambaran alur sistem

1. `Main` menerima input pengguna melalui terminal.
2. Data identitas dan gaji disimpan sebagai objek `Karyawan`.
3. `AbsensiService` memvalidasi dan menyimpan objek `CatatanAbsensi`.
4. `PenggajianService` mengambil catatan pada periode yang dipilih, menghitung ringkasan absensi, lalu menerapkan rumus penggajian.
5. Hasil dikembalikan sebagai objek `HasilPenggajian` dan ditampilkan oleh `Main`.

## 2. Peta class

| Class | Tanggung jawab | Anggota penanggung jawab |
|---|---|---|
| `Main` | Menu konsol, input/output, menghubungkan model dan service | Ilham |
| `Karyawan` | Menyimpan ID, nama, jabatan, gaji pokok, tunjangan | Ilham |
| `StatusAbsensi` | Membatasi nilai status absensi | Ade |
| `CatatanAbsensi` | Mewakili absensi seorang karyawan pada satu tanggal | Ade |
| `AbsensiService` | Validasi duplikasi, pencarian periode, ringkasan absensi | Ade |
| `HasilPenggajian` | Menyimpan rincian hasil kalkulasi payroll | Andri |
| `PenggajianService` | Menghitung lembur, potongan, dan gaji bersih | Andri |

## 3. Format penjelasan untuk setiap class

Untuk masing-masing class, isi bagian berikut:

### `NamaClass`
- **Tujuan:** masalah apa yang ditangani class ini?
- **Atribut:** nama atribut, tipe data, dan maknanya.
- **Constructor:** parameter yang diterima dan validasi yang dilakukan.
- **Method penting:** parameter, nilai balik, dan efeknya.
- **Alasan desain:** mengapa tanggung jawab ini diletakkan di class ini, bukan class lain?
- **Contoh alur:** contoh input dan output nyata.
- **Konsep OOP:** jelaskan konsep yang benar-benar terlihat dalam kode.

## 4. Konsep OOP pada implementasi

### Encapsulation
Atribut model seperti `Karyawan.id` dibuat `private`, sehingga kode luar tidak mengakses field secara langsung. Data dibaca melalui getter. Constructor memvalidasi nilai sebelum objek terbentuk.

### Class dan object
`Karyawan` adalah class; setiap data karyawan yang dibuat melalui `new Karyawan(...)` merupakan object dengan nilai atributnya sendiri.

### Constructor dan validasi
Constructor pada model memastikan data dasar tidak kosong atau negatif. Validasi yang berkaitan dengan kumpulan data, seperti absensi ganda, ditempatkan di `AbsensiService` karena service mengetahui catatan yang telah tersimpan.

### Enum
`StatusAbsensi` membatasi status pada `HADIR`, `IZIN`, `SAKIT`, dan `ALPHA`; ini mengurangi salah ketik string bebas.

### Separation of concerns
`Main` mengurus komunikasi dengan pengguna, model merepresentasikan data, dan service memuat aturan bisnis. Pemisahan ini membuat source code lebih mudah diperiksa dan diuji.

### Batas klaim
Versi awal ini tidak menerapkan inheritance atau polymorphism sebagai fitur utama. Jangan menulis bahwa semua pilar OOP telah digunakan jika source code tidak menunjukkan implementasi tersebut. Jika dosen mensyaratkan inheritance/polymorphism, diskusikan kebutuhan class abstrak/interface sebelum menambahkannya.

## 5. Rumus dan asumsi

Salin rumus yang ada di README, lalu beri contoh angka dan hasil kalkulasi manual. Tandai dengan jelas bahwa ini adalah aturan simulasi yang disepakati kelompok.

## 6. Hal yang harus dilengkapi sebelum dikumpulkan

- Tambahkan penjelasan baris atau blok kode yang penting, khususnya `Main`, `AbsensiService`, dan `PenggajianService`.
- Sertakan diagram class sederhana jika diminta dosen.
- Cantumkan hasil uji dan screenshot yang benar-benar diambil dari program yang dijalankan.
- Pastikan nama, signature method, dan rumus di dokumen sama dengan source code final.
