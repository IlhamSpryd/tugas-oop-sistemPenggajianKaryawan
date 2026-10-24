# Panduan Kolaborasi GitHub

## Aturan dasar

- `main` adalah branch stabil; jangan mengerjakan fitur langsung di `main`.
- Satu pekerjaan/f fitur = satu branch dan satu Pull Request (PR).
- Pull Request harus punya deskripsi, daftar pengujian, dan minimal satu reviewer dari kelompok.
- Commit harus menjelaskan perubahan, misalnya `feat: add attendance validation`.
- Hindari mengedit file yang sama bersamaan tanpa komunikasi, terutama `Main.java` dan dokumen integrasi.

## Branch yang disarankan

- `feat/employee-menu-ilham`
- `feat/attendance-module-ade`
- `feat/payroll-module-andri`
- `docs/source-code-explanation`

## Langkah membuat repository di GitHub

1. Login ke GitHub dengan akun kelompok/koordinator.
2. Pilih **New repository**.
3. Isi nama: `sistem-absensi-gaji-java`.
4. Pilih **Public** jika dosen membutuhkan akses lewat tautan; pilih **Private** bila belum ingin dipublikasikan. Pastikan dosen dan semua anggota mendapat akses yang diperlukan.
5. Jangan centang pembuatan README atau `.gitignore` jika mengunggah paket starter ini, karena file tersebut sudah tersedia.
6. Buat repository kosong, kemudian jalankan perintah berikut dari direktori hasil ekstrak ZIP.

## Mengunggah proyek awal

```bash
git init
git add .
git commit -m "chore: add Java attendance and payroll starter"
git branch -M main
git remote add origin https://github.com/IlhamSpryd/sistem-absensi-gaji-java.git
git push -u origin main
```

Jika repository GitHub telah dibuat dengan nama berbeda, ganti URL pada `git remote add origin` sesuai URL repository yang benar.

## Cara kerja setiap anggota

Clone repository sekali:

```bash
git clone https://github.com/IlhamSpryd/sistem-absensi-gaji-java.git
cd sistem-absensi-gaji-java
```

Sebelum mengerjakan branch baru:

```bash
git switch main
git pull origin main
git switch -c feat/nama-fitur-nama-anggota
```

Setelah perubahan selesai:

```bash
git status
git add path/berkas-yang-diubah
git commit -m "feat: jelaskan perubahan"
git push -u origin feat/nama-fitur-nama-anggota
```

Buka GitHub, buat Pull Request ke `main`, minta anggota lain meninjau, lalu merge setelah kompilasi dan pengujian lolos. Setelah merge:

```bash
git switch main
git pull origin main
```

## Tambahkan kolaborator

Di repository GitHub: **Settings → Collaborators** (atau menu akses yang setara), tambahkan akun GitHub Ade dan Andri. Minta username GitHub mereka secara langsung; jangan menebak username hanya dari nama depan.

## Template deskripsi Pull Request

```text
## Perubahan
- Apa yang ditambahkan/diubah?

## Alasan
- Mengapa perubahan ini diperlukan?

## Pengujian
- [ ] javac berhasil
- [ ] Skenario valid diuji
- [ ] Skenario error/validasi diuji
- [ ] Dokumentasi diperbarui

## Catatan reviewer
- Bagian yang perlu diperhatikan:
```
