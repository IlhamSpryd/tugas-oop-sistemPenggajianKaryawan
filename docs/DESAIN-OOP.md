# Desain Berorientasi Objek (OOP Design)

Dokumen ini memaparkan arsitektur perangkat lunak, peran setiap class, hubungan antarclass, penerapan pilar-pilar OOP, serta alasan teknis pemilihan struktur package dalam proyek **Sistem Absensi dan Penggajian Karyawan**.

---

## 1. Arsitektur Package & Alasan Pemilihan Struktur

Struktur kode diorganisasikan berbasis fitur/domain (*Package-by-Feature/Domain*), bukan berdasarkan lapisan teknis generik (*layer* seperti `model` vs `service`):

```text
id.kelompok.absensigaji/
├── app/          # Antarmuka pengguna dan orchestrator aplikasi
├── employee/     # Domain data master karyawan
├── attendance/   # Domain pencatatan dan pengelolaan absensi
└── payroll/      # Domain kalkulasi kompensasi dan penggajian
```

### Mengapa Struktur Ini Dipilih?
1. **Pemisahan Tanggung Jawab & Kepemilikan (Code Ownership):** Dalam tim tiga orang, pembagian berbasis domain memetakan modul langsung ke penanggung jawab:
   - `employee` & `app` -> **Ilham**
   - `attendance` -> **Ade**
   - `payroll` -> **Andri**
2. **Minimasi Konflik Git (Merge Conflicts):** Jika semua model digabung dalam folder `model/` dan semua service dalam `service/`, setiap anggota akan sering mengubah file pada folder yang sama. Dengan pemisahan package domain, Ade dapat bekerja leluasa di `attendance/` dan Andri di `payroll/` tanpa saling menimpa.
3. **High Cohesion, Loose Coupling:** Setiap modul hanya mengekspos API publik yang disepakati, menjaga ketergantungan antarmodul tetap bersih dan satu arah (*Directed Acyclic Graph*).

---

## 2. Peta Class & Tanggung Jawab

| Package | Class / Enum | Penanggung Jawab | Tanggung Jawab Utama |
|---|---|---|---|
| `employee` | `Karyawan` | Ilham | Merepresentasikan entitas data karyawan (ID, nama, jabatan, gaji pokok, tunjangan). |
| `attendance` | `StatusAbsensi` | Ade | Enum pendefinisi status absensi resmi (`HADIR`, `IZIN`, `SAKIT`, `ALPHA`). |
| `attendance` | `CatatanAbsensi` | Ade | Entitas pencatat satu kejadian kehadiran karyawan pada tanggal tertentu beserta jam lembur. |
| `attendance` | `AbsensiService` | Ade | Mengelola repositori memori absensi, validasi anti-duplikasi harian, dan query riwayat per periode. |
| `payroll` | `HasilPenggajian` | Andri | Value object / DTO penyimpan rincian hasil perhitungan gaji bulanan. |
| `payroll` | `PenggajianService` | Andri | Menjalankan logika bisnis perhitungan slip gaji berbasis aturan simulasi. |
| `app` | `Main` | Ilham | Mengelola menu interaktif konsol, input pengguna, dan mengorkestrasi pemanggilan antarmodul. |

---

## 3. Rincian Class, Atribut, dan Method

### A. `employee.Karyawan`
- **Tujuan:** Menampung identitas dan informasi kompensasi dasar seorang pekerja.
- **Atribut:**
  - `private final String id`: Identitas unik karyawan.
  - `private final String nama`: Nama lengkap karyawan.
  - `private final String jabatan`: Posisi / peran karyawan di perusahaan.
  - `private final long gajiPokokBulanan`: Nominal upah pokok bulanan (>= 0).
  - `private final long tunjanganTetapBulanan`: Nominal tunjangan tetap bulanan (>= 0).
- **Constructor & Encapsulation:**
  - `public Karyawan(String id, String nama, String jabatan, long gajiPokokBulanan, long tunjanganTetapBulanan)`
  - Memastikan atribut tidak bernilai `null`, teks tidak kosong (*blank*), dan nilai numerik non-negatif.
  - Seluruh field bersifat `private final` sehingga objek bersifat *immutable* setelah terbentuk. Akses pembacaan hanya melalui getter.

### B. `attendance.StatusAbsensi`
- **Tujuan:** Membatasi kemungkinan nilai status absensi agar terbebas dari kesalahan pengetikan (*type-safety*).
- **Nilai Enum:** `HADIR`, `IZIN`, `SAKIT`, `ALPHA`.

### C. `attendance.CatatanAbsensi`
- **Tujuan:** Merepresentasikan bukti presensi kerja untuk seorang karyawan pada hari tertentu.
- **Atribut:**
  - `private final String idKaryawan`: ID karyawan bersangkutan.
  - `private final LocalDate tanggal`: Tanggal kehadiran.
  - `private final StatusAbsensi status`: Status absensi pada hari tersebut.
  - `private final int jamLembur`: Jumlah jam kerja lembur (hanya sah jika status `HADIR`).
- **Constructor & Validasi:**
  - Memastikan `tanggal` dan `status` tidak null.
  - Memvalidasi bahwa jam lembur tidak bernilai negatif, dan jika status bukan `HADIR`, jam lembur wajib bernilai 0.

### D. `attendance.AbsensiService`
- **Tujuan:** Menyediakan layanan pengelolaan koleksi absensi.
- **Atribut:** `private final List<CatatanAbsensi> daftarCatatan` sebagai penyimpanan memori.
- **Method Publik:**
  - `void catatAbsensi(...)`: Memvalidasi bahwa belum ada entri untuk `idKaryawan` pada `tanggal` yang sama, lalu menyimpan catatan.
  - `List<CatatanAbsensi> getCatatan(String idKaryawan, YearMonth periode)`: Mengambil riwayat absensi seorang karyawan pada bulan kalender tertentu.
  - `long hitungStatus(List<CatatanAbsensi> data, StatusAbsensi status)`: Menghitung total hari berdasarkan status.
  - `int hitungTotalJamLembur(List<CatatanAbsensi> data)`: Menjumlahkan seluruh jam lembur pada data yang diberikan.

### E. `payroll.HasilPenggajian`
- **Tujuan:** Menyimpan seluruh rincian komponen pendapatan, potongan, dan hasil bersih gaji bulanan secara terstruktur.
- **Atribut:**
  - Identitas: `idKaryawan`, `namaKaryawan`, `periode`.
  - Rekap absensi: `hariHadir`, `hariIzin`, `hariSakit`, `hariAlpha`, `totalJamLembur`.
  - Komponen finansial: `gajiPokok`, `tunjangan`, `upahLembur`, `potonganAlpha`, `potonganTambahan`, `gajiBersih`.
- **Karakteristik:** Bersifat *read-only* value object, menjamin hasil kalkulasi tidak diubah secara sengaja di lapisan antarmuka.

### F. `payroll.PenggajianService`
- **Tujuan:** Mengeksekusi proses bisnis perhitungan gaji sesuai aturan simulasi.
- **Konstanta:**
  - `HARI_KERJA_STANDAR = 26`
  - `TARIF_LEMBUR_PER_JAM = 20_000L`
- **Dependensi:** Menerima instance `AbsensiService` melalui *Constructor Dependency Injection*.
- **Method Utama:**
  - `HasilPenggajian hitungGaji(Karyawan karyawan, YearMonth periode, long potonganTambahan)`

### G. `app.Main`
- **Tujuan:** Antarmuka baris perintah (*CLI*), validasi parsing masukan terminal, penanganan pesan kesalahan, dan penghubung antarmodul.

---

## 4. Hubungan Antarclass (Class Relationships)

```text
┌─────────────────┐       creates & calls       ┌─────────────────────┐
│                 │────────────────────────────►│   AbsensiService    │
│                 │                             └──────────┬──────────┘
│                 │       creates & calls                  │ manages
│      Main       │────────────────────────┐               ▼
│                 │                        │    ┌─────────────────────┐
│                 │       stores           ▼    │   CatatanAbsensi    │
│                 │──────────────► ┌───────────────┐└──────────┬──────────┘
└─────────────────┘                │ PenggajianSvc │           │ uses
         │                         └───────┬───────┘           ▼
         │ stores                          │ uses       ┌─────────────┐
         ▼                                 │            │StatusAbsensi│
┌─────────────────┐                        ▼            └─────────────┘
│    Karyawan     │◄───────────────────────┤
└─────────────────┘                        ▼ returns
                                   ┌─────────────────┐
                                   │ HasilPenggajian │
                                   └─────────────────┘
```

1. **Dependency Injection:** `PenggajianService` bergantung pada `AbsensiService` yang disuntikkan saat pembuatan objek di `Main`.
2. **Aggregation:** `AbsensiService` memiliki dan mengelola kumpulan objek `CatatanAbsensi`. `Main` mengelola kumpulan objek `Karyawan`.
3. **Association:** `CatatanAbsensi` menggunakan enum `StatusAbsensi`. `PenggajianService.hitungGaji(...)` menggunakan objek `Karyawan` dan mengembalikan objek `HasilPenggajian`.

---

## 5. Penerapan Pilar & Konsep OOP

### A. Encapsulation (Enkapsulasi)
- Semua atribut pada model domain (`Karyawan`, `CatatanAbsensi`, `HasilPenggajian`) dideklarasikan `private final`.
- Akses ke state objek hanya dimungkinkan melalui method getter publik.
- Konstruktor melakukan validasi ketat sebelum objek dapat terbentuk, mencegah adanya objek dalam status tidak valid (*invalid state*).

### B. Class & Object
- Class seperti `Karyawan` dan `CatatanAbsensi` adalah cetak biru (*blueprint*).
- Setiap karyawan yang didaftarkan atau absensi yang dicatat membentuk objek (*instance*) independen di memori heap dengan nilai atributnya masing-masing.

### C. Separation of Concerns (Pemisahan Tanggung Jawab)
- Modul `employee` hanya mengurusi data master karyawan.
- Modul `attendance` murni mengelola data presensi dan riwayatnya.
- Modul `payroll` murni memproses kalkulasi finansial.
- Modul `app` murni menangani tampilan menu dan penerimaan input.

### D. Mengapa Belum Menggunakan Inheritance dan Polymorphism?
- Dalam versi awal sistem absensi dan penggajian ini, seluruh karyawan diperlakukan dengan model kompensasi seragam (gaji pokok bulanan + tunjangan).
- **Prinsip Kejujuran Akademik:** Kita tidak membuat kelas induk abstrak `Karyawan` dengan turunan `KaryawanTetap` dan `KaryawanKontrak` atau interface polimorfik jika domain permasalahan saat ini belum membutuhkannya.
- Menerapkan pewarisan tanpa kebutuhan nyata adalah bentuk *overengineering*. Jika di masa mendatang dosen atau sistem mensyaratkan jenis karyawan berbeda dengan rumus gaji berbeda, inheritance dan polymorphism dapat ditambahkan secara elegan melalui subclass `Karyawan` atau interface `SkemaPenggajian`.

---

## 6. Aturan Rumus Penggajian Simulasi

Aturan perhitungan disepakati sebagai simulasi akademik:
1. **Hari Kerja Standar:** 26 hari per bulan.
2. **Potongan Alpha:**
   $$\text{Potongan Alpha} = \text{round}\left(\frac{\text{Gaji Pokok}}{26}\right) \times \text{Jumlah Hari Alpha}$$
   *(Hari Izin dan Sakit tidak memotong gaji dalam simulasi ini).*
3. **Upah Lembur:**
   $$\text{Upah Lembur} = \text{Total Jam Lembur} \times \text{Rp20.000}$$
4. **Total Pendapatan:**
   $$\text{Total Pendapatan} = \text{Gaji Pokok} + \text{Tunjangan Tetap} + \text{Upah Lembur}$$
5. **Total Potongan:**
   $$\text{Total Potongan} = \text{Potongan Alpha} + \text{Potongan Tambahan}$$
6. **Gaji Bersih:**
   $$\text{Gaji Bersih} = \max(0, \text{Total Pendapatan} - \text{Total Potongan})$$
