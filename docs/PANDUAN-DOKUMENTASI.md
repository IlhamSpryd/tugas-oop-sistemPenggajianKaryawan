# Panduan Penulisan Dokumentasi Teknis

Dokumentasi merupakan salah satu komponen penilaian utama dalam tugas ini. Dokumen ini adalah pedoman bagi setiap anggota tim (**Ilham**, **Ade**, dan **Andri**) agar mampu menjelaskan kode yang dibuat secara mendalam dan profesional saat penyusunan laporan maupun presentasi di hadapan dosen.

---

## 1. Prinsip Utama: Jangan Sekadar Menyalin Kode

1. **Jelaskan "Mengapa" (*Why*), Bukan Hanya "Apa" (*What*):** Jangan hanya menuliskan "Method ini mengembalikan nilai gaji". Jelaskan mengapa tipe data `long` dipilih alih-alih `double` (untuk mencegah galat aproksimasi floating-point pada nominal uang), mengapa atribut dibuat `private final`, dan mengapa validasi diletakkan di constructor atau service.
2. **Bahasa Sendiri yang Lugas:** Tuliskan penjelasan menggunakan pemahaman mandiri. Setiap anggota wajib menguasai seluruh alur kelas yang menjadi tanggung jawabnya.
3. **Dokumentasikan Alur Data (*Data Flow*):** Tunjukkan bagaimana data mengalir dari input konsol, divalidasi oleh entitas, diolah oleh service, hingga dikembalikan sebagai laporan.

---

## 2. Struktur Standar Penjelasan Class

Setiap anggota wajib mengikuti format penjelasan berikut saat mendokumentasikan class modulnya:

```markdown
### `NamaClass`
- **Penanggung Jawab:** [Nama Anggota]
- **Tujuan Domain:** Masalah apa di dunia nyata yang ditangani oleh class ini?
- **Struktur Atribut:**
  - `namaAtribut` (`TipeData`): Deskripsi kegunaan atribut dan alasan pemilihan tipe datanya.
- **Constructor & Invarian:**
  - Parameter masukan apa saja yang diterima?
  - Validasi apa yang dilakukan untuk menjaga objek selalu dalam kondisi sah (*valid state*)?
- **Method Utama:**
  - `namaMethod(param...) -> ReturnType`
  - *Input:* Parameter dan batasannya.
  - *Proses / Algoritma:* Tahapan logika langkah demi langkah.
  - *Output:* Nilai kembalian atau dampak terhadap state sistem.
- **Alasan Desain & Konsep OOP:**
  - Konsep OOP apa yang terlihat nyata (Encapsulation, Immutability, Separation of Concerns)?
  - Mengapa method diletakkan di class ini, bukan di class lain?
- **Contoh Skenario:** Contoh input konkret dan output hasil eksekusinya.
- **Penanganan Kesalahan (Error Handling):** Eksepsi apa yang mungkin dilempar (`IllegalArgumentException`, dll.) dan bagaimana dampaknya bagi pengguna.
```

---

## 3. Pedoman Khusus per Modul

### A. Modul Employee & App (Ilham Sepriyadi)
- Jelaskan bagaimana objek `Karyawan` menjadi data master acuan untuk absensi dan penggajian.
- Uraikan bagaimana `Main.java` menangani parsing string dari terminal menjadi tipe tanggal (`LocalDate`) atau angka non-negatif tanpa menyebabkan program crash.
- Jelaskan bagaimana dependency injection diterapkan secara manual pada inisialisasi:
  `PenggajianService PENGGAJIAN = new PenggajianService(ABSENSI);`

### B. Modul Attendance (Ade)
- Jelaskan alasan mengapa status absensi menggunakan `enum StatusAbsensi` dibandingkan string biasa (keamanan tipe data, pembatasan opsi, pencegahan *typo*).
- Jelaskan logika validasi anti-duplikasi absensi pada `AbsensiService.catatAbsensi`: mengapa pengecekan ini diletakkan di service, bukan di dalam class `CatatanAbsensi` (karena entitas `CatatanAbsensi` hanya tahu dirinya sendiri, sedangkan `AbsensiService` memegang seluruh catatan riwayat).
- Jelaskan aturan bisnis jam lembur: mengapa jam lembur wajib 0 jika karyawan berstatus `IZIN`, `SAKIT`, atau `ALPHA`.

### C. Modul Payroll (Andri)
- Uraikan rumus simulasi penggajian langkah demi langkah:
  1. Cara menghitung hari hadir, izin, sakit, alpha, dan akumulasi lembur dari list catatan absensi.
  2. Formula proporsional potongan Alpha dengan pembagi standar 26 hari kerja:
     $$\text{potonganAlpha} = \text{round}\left(\frac{\text{gajiPokok}}{26}\right) \times \text{hariAlpha}$$
  3. Formula upah lembur: $\text{lembur} \times \text{Rp20.000}$.
  4. Total pendapatan dan total potongan.
  5. Pengamanan nilai gaji bersih agar tidak bernilai minus ($\max(0, \text{pendapatan} - \text{potongan})$).
- Sertakan minimal dua contoh perhitungan manual dengan angka nyata (lihat contoh di bawah).

---

## 4. Standar Contoh Perhitungan Manual (Wajib untuk Andri)

Contoh kasus uji manual yang dapat dimasukkan ke dalam laporan:

### Kasus A: Karyawan Budi (Staff)
- **Data Karyawan:** Gaji Pokok = Rp3.000.000, Tunjangan Tetap = Rp300.000.
- **Absensi Periode 2026-10:**
  - Hadir: 20 hari
  - Jam Lembur: 5 jam
  - Izin: 1 hari
  - Sakit: 1 hari
  - Alpha: 2 hari
- **Potongan Tambahan:** Rp50.000 (misal: denda/kas internal).
- **Langkah Kalkulasi:**
  1. Potongan Alpha = $\text{round}(3.000.000 / 26) \times 2 = 115.385 \times 2 = \text{Rp230.769}$ (atau $\text{round}(3.000.000 \times 2 / 26) = \text{Rp230.769}$).
  2. Upah Lembur = $5 \times \text{Rp20.000} = \text{Rp100.000}$.
  3. Total Pendapatan = $3.000.000 + 300.000 + 100.000 = \text{Rp3.400.000}$.
  4. Total Potongan = $230.769 + 50.000 = \text{Rp280.769}$.
  5. Gaji Bersih = $3.400.000 - 280.769 = \text{Rp3.119.231}$.

### Kasus B: Potongan Melebihi Pendapatan (Batas Minimum Rp0)
- **Data Karyawan:** Gaji Pokok = Rp2.000.000, Tunjangan = Rp0.
- **Absensi:** 15 hari Alpha, 0 lembur.
- **Potongan Tambahan:** Rp1.500.000.
- **Langkah Kalkulasi:**
  1. Potongan Alpha = $\text{round}(2.000.000 / 26) \times 15 = 76.923 \times 15 = \text{Rp1.153.846}$.
  2. Total Pendapatan = $\text{Rp2.000.000}$.
  3. Total Potongan = $1.153.846 + 1.500.000 = \text{Rp2.653.846}$.
  4. Gaji Bersih = $\max(0, 2.000.000 - 2.653.846) = \text{Rp0}$ (bukan minus).

---

## 5. Standar Bukti Screenshot Program

- Screenshot wajib diambil dari terminal aplikasi yang benar-benar dijalankan.
- Tidak boleh memodifikasi output teks atau membuat gambar tiruan.
- Nama file tangkapan layar disimpan di folder `screenshots/`:
  - `01-menu-utama.png`
  - `02-tambah-daftar-karyawan.png`
  - `03-input-absensi-valid.png`
  - `04-validasi-duplikasi-absensi.png`
  - `05-riwayat-absensi.png`
  - `06-hasil-kalkulasi-penggajian.png`
