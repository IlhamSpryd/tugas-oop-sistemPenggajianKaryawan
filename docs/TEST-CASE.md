# Test Case Minimum

Isi kolom hasil aktual setelah program dijalankan dan simpan bukti yang relevan. Jangan menandai lulus sebelum benar-benar dites.

| ID | Modul | Skenario | Hasil yang diharapkan | Hasil aktual/status |
|---|---|---|---|---|
| TC-01 | Karyawan | Tambah karyawan dengan data valid | Data berhasil dibuat dan dapat dilihat | Belum diuji |
| TC-02 | Karyawan | ID karyawan duplikat | Data kedua ditolak | Belum diuji |
| TC-03 | Karyawan | Gaji pokok negatif | Input ditolak atau diminta ulang | Belum diuji |
| TC-04 | Absensi | Catat Hadir dengan 2 jam lembur | Catatan tersimpan dan jam lembur tercatat | Belum diuji |
| TC-05 | Absensi | Catat Izin/Sakit/Alpha | Status tersimpan, jam lembur 0 | Belum diuji |
| TC-06 | Absensi | Catat absensi kedua pada ID dan tanggal yang sama | Duplikasi ditolak | Belum diuji |
| TC-07 | Absensi | Format tanggal salah | Pesan error ditampilkan dan program tetap berjalan | Belum diuji |
| TC-08 | Absensi | ID karyawan tidak dikenal | Catatan tidak dibuat | Belum diuji |
| TC-09 | Payroll | Tidak ada Alpha dan tidak ada lembur/potongan tambahan | Gaji bersih = gaji pokok + tunjangan | Belum diuji |
| TC-10 | Payroll | 2 Alpha, gaji pokok Rp3.000.000 | Potongan Alpha sekitar Rp230.769 | Belum diuji |
| TC-11 | Payroll | 5 jam lembur | Upah lembur Rp100.000 | Belum diuji |
| TC-12 | Payroll | Gaji terkena potongan sangat besar | Gaji bersih minimal Rp0, bukan negatif | Belum diuji |
| TC-13 | Payroll | Periode tanpa catatan absensi | Ringkasan absensi 0; gaji mengikuti aturan simulasi | Belum diuji |
| TC-14 | Payroll | Potongan tambahan negatif | Nilai negatif ditolak | Belum diuji |
| TC-15 | Integrasi | Alur tambah karyawan → absensi → hitung gaji | Data mengalir dengan benar dari awal sampai akhir | Belum diuji |

## Skenario demo yang direkomendasikan

1. Tambahkan karyawan `K001`, nama `Budi`, jabatan `Staff`, gaji pokok `3000000`, tunjangan `300000`.
2. Catat beberapa absensi pada satu bulan yang sama: Hadir dengan 2 jam lembur, Hadir dengan 3 jam lembur, dan 2 hari Alpha.
3. Hitung gaji pada periode tersebut dengan potongan tambahan `50000`.
4. Cocokkan rincian output dengan perhitungan manual di `PANDUAN-DOKUMENTASI.md`.
5. Ambil screenshot setiap langkah yang relevan.

Gunakan tanggal valid yang sesuai dengan bulan pilihan saat demo, dan jangan menggunakan ulang ID/tanggal yang sudah dicatat karena sistem menolak duplikasi.
