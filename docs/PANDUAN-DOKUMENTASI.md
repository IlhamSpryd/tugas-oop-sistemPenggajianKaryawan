# Panduan Dokumentasi Source Code

Dokumentasi adalah fokus utama penilaian menurut arahan tugas. Targetnya bukan sekadar panjang dokumen, melainkan membuktikan bahwa kelompok memahami desain, alur data, dan alasan setiap keputusan kode.

## Susunan laporan yang disarankan

1. **Identitas tugas:** mata kuliah, judul proyek, dosen, kelas, dan anggota kelompok.
2. **Latar belakang singkat:** masalah pencatatan absensi dan penghitungan gaji yang disimulasikan.
3. **Tujuan dan batasan:** aplikasi konsol, data sementara dalam memori, dan aturan gaji simulasi.
4. **Kebutuhan fungsional:** tambah karyawan, catat absensi, lihat riwayat, hitung gaji.
5. **Desain OOP:** peta class, tanggung jawab, atribut, constructor, method, dan hubungan antarclass.
6. **Penjelasan source code:** uraikan kode penting per class dan per method; gunakan cuplikan pendek yang relevan.
7. **Algoritma perhitungan:** rumus, urutan langkah, contoh hitung manual, dan hasil program.
8. **Pengujian:** skenario, input, hasil yang diharapkan, hasil aktual, dan status lulus/gagal.
9. **Bukti running:** screenshot yang dapat dibaca, disertai keterangan apa yang dibuktikan.
10. **Kesimpulan dan keterbatasan:** fitur yang telah berfungsi serta hal yang belum dibuat.

## Checklist penjelasan tiap class

- [ ] Menjelaskan peran class dengan bahasa sendiri.
- [ ] Menjelaskan setiap atribut penting dan alasan tipe datanya.
- [ ] Menjelaskan constructor dan validasi.
- [ ] Menjelaskan method utama, input, proses, dan output.
- [ ] Menghubungkan penjelasan ke konsep OOP yang nyata dalam kode.
- [ ] Menunjukkan alur pemanggilan dari `Main` sampai hasil akhir.
- [ ] Memasukkan setidaknya satu contoh input dan output.
- [ ] Menjelaskan kemungkinan error dan bagaimana program menanganinya.

## Kualitas screenshot

- Ambil screenshot langsung dari program yang benar-benar dijalankan.
- Pastikan terminal tidak terpotong dan teks terbaca.
- Gunakan data contoh yang konsisten dengan contoh perhitungan manual.
- Minimal tampilkan: menu utama, data karyawan, input/riwayat absensi, dan rincian penggajian.
- Jangan mengganti hasil aktual dengan hasil yang diharapkan atau mengedit output agar tampak berhasil.

## Contoh perhitungan manual

Misalnya gaji pokok Rp3.000.000, tunjangan Rp300.000, 2 hari Alpha, 5 jam lembur, potongan tambahan Rp50.000:

- Potongan Alpha = (Rp3.000.000 / 26) × 2 = Rp230.769 (pembulatan ke rupiah terdekat).
- Upah lembur = 5 × Rp20.000 = Rp100.000.
- Total pendapatan = Rp3.000.000 + Rp300.000 + Rp100.000 = Rp3.400.000.
- Total potongan = Rp230.769 + Rp50.000 = Rp280.769.
- Gaji bersih = Rp3.400.000 − Rp280.769 = Rp3.119.231.

Angka tersebut contoh untuk aturan simulasi dalam proyek, bukan perhitungan legal payroll.
