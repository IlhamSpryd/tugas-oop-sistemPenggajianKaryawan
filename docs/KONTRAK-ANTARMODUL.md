# Kontrak Antarmodul (Inter-Module Contract)

Dokumen ini mendefinisikan batas modul, signature method publik yang disepakati, aliran data antarbagian, serta protokol tata kelola perubahan. Kontrak ini wajib disepakati oleh seluruh anggota tim (**Ilham Sepriyadi**, **Ade**, dan **Andri**) sebelum mulai mengimplementasikan logika bisnis pada branch masing-masing.

---

## 1. Prinsip Desain & Batas Modul

1. **Pragmatis & Sesuai Kebutuhan:** Sistem tidak menggunakan abstraksi, interface berlapis, atau design pattern rumit hanya untuk terlihat canggih. Struktur class dirancang langsung sesuai tanggung jawab domain masalah.
2. **Kemandirian Modul (High Cohesion, Loose Coupling):** Setiap package (`employee`, `attendance`, `payroll`, `app`) memiliki batasan yang tegas.
3. **Bebas Ketergantungan Melingkar (Zero Circular Dependency):**
   - Modul `employee` tidak bergantung pada modul mana pun.
   - Modul `attendance` hanya bergantung pada representasi identitas (`String idKaryawan`) dan tidak mengimpor package `payroll` maupun `employee`.
   - Modul `payroll` bergantung pada `employee` (untuk data gaji) dan `attendance` (untuk rekap kehadiran).
   - Modul `app` mengintegrasikan ketiga modul di atas.

### Diagram Aliran Dependensi
```text
           ┌────────────────────────┐
           │      app (Ilham)       │
           │       Main.java        │
           └───┬────────┬───────┬───┘
               │        │       │
      ┌────────┘        │       └────────┐
      ▼                 ▼                ▼
┌───────────┐     ┌───────────┐    ┌───────────┐
│ employee  │◄────┤  payroll  ├───►│attendance │
│  (Ilham)  │     │  (Andri)  │    │   (Ade)   │
└───────────┘     └───────────┘    └───────────┘
```

---

## 2. Spesifikasi Pertukaran Data Antarmodul

### A. Data Karyawan yang Digunakan Modul Absensi (Ade)
Modul absensi **tidak membutuhkan seluruh objek `Karyawan`**. Modul absensi hanya mencatat dan memvalidasi kehadiran berdasarkan referensi identitas:
- `idKaryawan` (`String`): ID unik karyawan (contoh: `"K001"`).
- **Alasan Desain:** Dengan hanya menggunakan `idKaryawan`, modul absensi tidak perlu mengetahui besaran gaji, jabatan, atau struktur internal `Karyawan`. Ini mencegah kopling yang tidak perlu (*loose coupling*).

### B. Data Karyawan dan Absensi yang Diperlukan Modul Payroll (Andri)
Untuk menghitung slip penggajian bulanan, modul payroll membutuhkan:
1. **Dari `Karyawan` (`employee`):**
   - `getId()` (`String`): Identitas karyawan.
   - `getNama()` (`String`): Nama lengkap karyawan untuk slip.
   - `getGajiPokokBulanan()` (`long`): Basis kalkulasi proporsional potongan Alpha.
   - `getTunjanganTetapBulanan()` (`long`): Komponen pendapatan tetap.
2. **Dari `AbsensiService` (`attendance`):**
   - Rekap absensi per karyawan untuk periode kalender tertentu (`YearMonth`).
   - Perhitungan jumlah hari kehadiran berdasarkan status:
     - `hariHadir` (`StatusAbsensi.HADIR`)
     - `hariIzin` (`StatusAbsensi.IZIN`)
     - `hariSakit` (`StatusAbsensi.SAKIT`)
     - `hariAlpha` (`StatusAbsensi.ALPHA`)
   - `totalJamLembur` (`int`): Akumulasi jam lembur pada bulan tersebut.

---

## 3. Spesifikasi Signature Publik Minimum

### A. Modul Employee — Penanggung Jawab: Ilham Sepriyadi
```java
package id.kelompok.absensigaji.employee;

public class Karyawan {
    // Constructor
    public Karyawan(String id, String nama, String jabatan,
                    long gajiPokokBulanan, long tunjanganTetapBulanan);

    // Method Publik (Getter)
    public String getId();
    public String getNama();
    public String getJabatan();
    public long getGajiPokokBulanan();
    public long getTunjanganTetapBulanan();
    public String toString();
}
```

### B. Modul Attendance — Penanggung Jawab: Ade
```java
package id.kelompok.absensigaji.attendance;

public enum StatusAbsensi {
    HADIR, IZIN, SAKIT, ALPHA
}

public class CatatanAbsensi {
    // Constructor
    public CatatanAbsensi(String idKaryawan, LocalDate tanggal,
                          StatusAbsensi status, int jamLembur);

    // Method Publik (Getter)
    public String getIdKaryawan();
    public LocalDate getTanggal();
    public StatusAbsensi getStatus();
    public int getJamLembur();
    public String toString();
}

public class AbsensiService {
    // Pencatatan data baru (validasi duplikasi per tanggal)
    public void catatAbsensi(String idKaryawan, LocalDate tanggal,
                             StatusAbsensi status, int jamLembur);

    // Pencarian dan rekap
    public List<CatatanAbsensi> getSemuaCatatan();
    public List<CatatanAbsensi> getCatatan(String idKaryawan, YearMonth periode);
    public long hitungStatus(List<CatatanAbsensi> data, StatusAbsensi status);
    public int hitungTotalJamLembur(List<CatatanAbsensi> data);
}
```

### C. Modul Payroll — Penanggung Jawab: Andri
```java
package id.kelompok.absensigaji.payroll;

public class HasilPenggajian {
    // Constructor
    public HasilPenggajian(String idKaryawan, String namaKaryawan, String periode,
                           int hariHadir, int hariIzin, int hariSakit, int hariAlpha,
                           int totalJamLembur, long gajiPokok, long tunjangan,
                           long upahLembur, long potonganAlpha,
                           long potonganTambahan, long gajiBersih);

    // Method Publik (Getter)
    public String getIdKaryawan();
    public String getNamaKaryawan();
    public String getPeriode();
    public int getHariHadir();
    public int getHariIzin();
    public int getHariSakit();
    public int getHariAlpha();
    public int getTotalJamLembur();
    public long getGajiPokok();
    public long getTunjangan();
    public long getUpahLembur();
    public long getPotonganAlpha();
    public long getPotonganTambahan();
    public long getGajiBersih();
}

public class PenggajianService {
    // Konstanta aturan simulasi
    public static final int HARI_KERJA_STANDAR = 26;
    public static final long TARIF_LEMBUR_PER_JAM = 20_000L;

    // Dependency Injection via Constructor
    public PenggajianService(AbsensiService absensiService);

    // Method Utama Perhitungan Payroll
    public HasilPenggajian hitungGaji(Karyawan karyawan, YearMonth periode,
                                      long potonganTambahan);
}
```

---

## 4. Cara Modul App (`Main`) Menghubungkan Antarmodul

Modul `app/Main.java` bertindak sebagai *orchestrator* dan antarmuka pengguna:
1. **Instansiasi Service:**
   ```java
   private static final AbsensiService ABSENSI = new AbsensiService();
   private static final PenggajianService PENGGAJIAN = new PenggajianService(ABSENSI);
   ```
   `Main` menyuntikkan (*injects*) instance `ABSENSI` ke constructor `PENGGAJIAN`.
2. **Penyimpanan Karyawan:**
   `Main` memegang koleksi lokal `List<Karyawan>` dan method pencarian `cariKaryawan(String id)`.
3. **Alur Absensi:**
   `Main` memvalidasi keberadaan `Karyawan`, lalu memanggil `ABSENSI.catatAbsensi(...)`.
4. **Alur Penggajian:**
   `Main` memanggil `PENGGAJIAN.hitungGaji(karyawan, periode, potonganTambahan)`, lalu menerima objek `HasilPenggajian` untuk dicetak ke konsol.

---

## 5. Tata Kelola Perubahan Signature & Komunikasi Tim

1. **Hak Cipta dan Tanggung Jawab Modul:**
   - Signature pada `employee/` dan `app/` hanya boleh diubah oleh **Ilham**.
   - Signature pada `attendance/` hanya boleh diubah oleh **Ade**.
   - Signature pada `payroll/` hanya boleh diubah oleh **Andri**.
2. **Prosedur Pengajuan Perubahan (Change Request):**
   - Jika salah satu anggota membutuhkan perubahan method (misalnya: parameter baru, perubahan tipe return, atau penambahan method baru), anggota tersebut **tidak boleh** langsung mengubah file rekan modul lain.
   - Anggota pemohon wajib berdiskusi dengan pemilik modul terkait.
   - Setelah disepakati:
     1. Perbarui dokumen `docs/KONTRAK-ANTARMODUL.md`.
     2. Pemilik modul mengubah file miliknya pada branch fiturnya dan mengajukan Pull Request.
     3. Anggota pengguna memperbarui kodenya setelah PR tersebut di-merge ke `main`.
3. **Prinsip Backward Compatibility:**
   - Sebisa mungkin hindari menghapus atau mengubah signature yang sudah digunakan modul lain.
   - Jika perlu menambahkan fungsionalitas, lebih baik buat *overload method* baru daripada merusak method yang sudah ada.
