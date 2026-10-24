# Matriks Uji Coba Sistem (Test Case Specification)

Dokumen ini memuat daftar skenario pengujian unit dan integrasi untuk memastikan setiap modul memenuhi aturan bisnis dan validasi input. 

> [!NOTE]
> Saat ini kode berada dalam tahap **kerangka kerja kosong (skeleton/TODO)** untuk pembelajaran. Seluruh status pengujian berstatus **Belum Diuji** dan tidak boleh ditandai lulus sebelum implementasi selesai dan benar-benar diuji melalui eksekusi program secara langsung.

---

## 1. Tabel Test Case

| ID Pengujian | Modul | Skenario Pengujian | Input Data | Hasil yang Diharapkan | Hasil Aktual | Status | Penanggung Jawab |
|---|---|---|---|---|---|---|---|
| **TC-01** | Employee | Tambah karyawan dengan data valid | ID: `"K001"`, Nama: `"Budi"`, Jabatan: `"Staff"`, Gaji: `3000000`, Tunjangan: `300000` | Objek `Karyawan` berhasil dibuat dan tersimpan di memori | Belum diuji (kerangka kosong) | Belum Diuji | Ilham Sepriyadi |
| **TC-02** | Employee | Tambah karyawan dengan ID duplikat | Input ID `"K001"` dua kali dengan nama berbeda | Input kedua ditolak dengan pesan bahwa ID sudah terdaftar | Belum diuji (kerangka kosong) | Belum Diuji | Ilham Sepriyadi |
| **TC-03** | Employee | Tambah karyawan dengan nilai gaji negatif | Gaji Pokok: `-1000000` atau Tunjangan: `-50000` | Input ditolak oleh validasi; dilempar `IllegalArgumentException` | Belum diuji (kerangka kosong) | Belum Diuji | Ilham Sepriyadi |
| **TC-04** | Attendance | Catat absensi HADIR dengan jam lembur | ID: `"K001"`, Tanggal: `2026-10-01`, Status: `HADIR`, Lembur: `3` | Catatan tersimpan dengan status `HADIR` dan lembur 3 jam | Belum diuji (kerangka kosong) | Belum Diuji | Ade |
| **TC-05** | Attendance | Catat absensi non-HADIR dengan lembur 0 | ID: `"K001"`, Tanggal: `2026-10-02`, Status: `IZIN`, Lembur: `0` | Catatan tersimpan dengan status `IZIN` dan lembur 0 jam | Belum diuji (kerangka kosong) | Belum Diuji | Ade |
| **TC-06** | Attendance | Catat absensi non-HADIR tetapi menyertakan lembur > 0 | ID: `"K001"`, Tanggal: `2026-10-03`, Status: `SAKIT`, Lembur: `2` | Ditolak dengan `IllegalArgumentException`: Lembur hanya untuk status HADIR | Belum diuji (kerangka kosong) | Belum Diuji | Ade |
| **TC-07** | Attendance | Catat absensi duplikat pada tanggal yang sama | ID: `"K001"`, Tanggal: `2026-10-01` diinput ulang | Ditolak dengan pesan: Absensi karyawan pada tanggal tersebut sudah ada | Belum diuji (kerangka kosong) | Belum Diuji | Ade |
| **TC-08** | Attendance | Catat absensi dengan format tanggal salah | Tanggal: `"24/10/2026"` atau `"2026-13-45"` | Ditangkap oleh penanganan format; program menampilkan pesan error dan tidak crash | Belum diuji (kerangka kosong) | Belum Diuji | Ade |
| **TC-09** | Attendance | Query riwayat absensi berdasarkan ID & periode | ID: `"K001"`, Periode: `2026-10` | Menampilkan hanya catatan karyawan `"K001"` pada bulan Oktober 2026 secara terurut | Belum diuji (kerangka kosong) | Belum Diuji | Ade |
| **TC-10** | Payroll | Hitung gaji: kehadiran penuh tanpa lembur & potongan tambahan | Karyawan: Pokok `3000000`, Tunjangan `300000`. Hadir: 20, Alpha: 0, Lembur: 0. Potongan tambahan: `0` | Gaji bersih = Rp3.300.000 | Belum diuji (kerangka kosong) | Belum Diuji | Andri |
| **TC-11** | Payroll | Hitung gaji: ada lembur dan potongan Alpha | Gaji Pokok: `3000000`, Tunjangan: `300000`. Lembur: 5 jam, Alpha: 2 hari. Potongan tambahan: `50000` | Lembur: Rp100.000. Potongan Alpha: Rp230.769. Gaji bersih: Rp3.119.231 | Belum diuji (kerangka kosong) | Belum Diuji | Andri |
| **TC-12** | Payroll | Hitung gaji: input potongan tambahan negatif | Potongan tambahan: `-100000` | Ditolak dengan `IllegalArgumentException` | Belum diuji (kerangka kosong) | Belum Diuji | Andri |
| **TC-13** | Payroll | Hitung gaji: total potongan melebihi pendapatan | Pendapatan: Rp2.000.000. Total potongan: Rp2.500.000 | Gaji bersih minimal Rp0 (tidak bernilai negatif) | Belum diuji (kerangka kosong) | Belum Diuji | Andri |
| **TC-14** | Payroll | Hitung gaji: periode tanpa rekaman absensi | ID `"K001"`, Periode: `2026-11` (belum ada absensi) | Hadir/Izin/Sakit/Alpha/Lembur = 0. Gaji bersih dihitung sesuai aturan simulasi | Belum diuji (kerangka kosong) | Belum Diuji | Andri |
| **TC-15** | Integrasi | Alur lengkap tambah karyawan -> input absensi -> hitung gaji bulanan | Input data K001 -> input 3 absensi -> hitung gaji periode terkait | Data mengalir utuh antarmodul dan slip gaji konsol tercetak akurat | Belum diuji (kerangka kosong) | Belum Diuji | Ilham Sepriyadi |

---

## 2. Petunjuk Pelaksanaan Uji Saat Implementasi Selesai

Setiap anggota wajib:
1. Menjalankan pengujian unit mandiri pada branch masing-masing sebelum membuat Pull Request.
2. Memperbarui kolom **Hasil Aktual** dan **Status** pada tabel ini di branch fitur.
3. Melampirkan bukti screenshot terminal ke folder `screenshots/`.
4. Menyertakan hasil pengujian pada deskripsi Pull Request (`.github/pull_request_template.md`).
