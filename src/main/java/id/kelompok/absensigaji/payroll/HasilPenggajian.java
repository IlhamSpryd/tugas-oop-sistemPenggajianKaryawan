package id.kelompok.absensigaji.payroll;

/**
 * Menyimpan rincian hasil perhitungan gaji karyawan untuk satu periode tertentu.
 * Berfungsi sebagai Data Transfer Object (DTO) / Value Object yang menghubungkan
 * modul payroll dengan modul presentasi antarmuka (app).
 *
 * Tim Pengembang & Kolaborasi:
 * - Penanggung Jawab / Author : Muhammad Andriansyah
 * - Reviewer Modul            : Ilham Sepriyadi & Ade Rizky
 */
public class HasilPenggajian {
    // [Catatan Desain - Muhammad Andriansyah]:
    // DTO ini sengaja dibuat immutable (semua atribut private final dan hanya menyediakan getter).
    // Tujuannya agar hasil perhitungan yang telah diproses secara sah tidak dapat diubah-ubah lagi di lapisan UI.
    private final String idKaryawan;
    private final String namaKaryawan;
    private final String periode;
    private final int hariHadir;
    private final int hariIzin;
    private final int hariSakit;
    private final int hariAlpha;
    private final int totalJamLembur;
    private final long gajiPokok;
    private final long tunjangan;
    private final long upahLembur;
    private final long potonganAlpha;
    private final long potonganTambahan;
    private final long gajiBersih;

    /**
     * Menginstansiasi snapshot slip gaji lengkap.
     *
     * [Diskusi Tim]:
     * - Andriansyah  : "Semua komponen gw tampung di sini lengkap: rekapan absensi sampai breakdown rupiahnya."
     * - Ilham        : "Keren Ndri, dengan begini method tampilkanHasilGaji() di Main tinggal panggil getter
     *                   tanpa perlu kalkulasi tambahan lagi di level UI (Single Responsibility Principle)."
     * - Ade Rizky    : "Field hariHadir, hariAlpha, dll. juga langsung sinkron sama data absensi yang udah gw rekap."
     */
    public HasilPenggajian(String idKaryawan, String namaKaryawan, String periode,
                           int hariHadir, int hariIzin, int hariSakit, int hariAlpha,
                           int totalJamLembur, long gajiPokok, long tunjangan,
                           long upahLembur, long potonganAlpha,
                           long potonganTambahan, long gajiBersih) {
        // Inisialisasi rincian perhitungan penggajian
        this.idKaryawan = idKaryawan;
        this.namaKaryawan = namaKaryawan;
        this.periode = periode;
        this.hariHadir = hariHadir;
        this.hariIzin = hariIzin;
        this.hariSakit = hariSakit;
        this.hariAlpha = hariAlpha;
        this.totalJamLembur = totalJamLembur;
        this.gajiPokok = gajiPokok;
        this.tunjangan = tunjangan;
        this.upahLembur = upahLembur;
        this.potonganAlpha = potonganAlpha;
        this.potonganTambahan = potonganTambahan;
        this.gajiBersih = gajiBersih;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public String getPeriode() {
        return periode;
    }

    public int getHariHadir() {
        return hariHadir;
    }

    public int getHariIzin() {
        return hariIzin;
    }

    public int getHariSakit() {
        return hariSakit;
    }

    public int getHariAlpha() {
        return hariAlpha;
    }

    public int getTotalJamLembur() {
        return totalJamLembur;
    }

    public long getGajiPokok() {
        return gajiPokok;
    }

    public long getTunjangan() {
        return tunjangan;
    }

    public long getUpahLembur() {
        return upahLembur;
    }

    public long getPotonganAlpha() {
        return potonganAlpha;
    }

    public long getPotonganTambahan() {
        return potonganTambahan;
    }

    public long getGajiBersih() {
        return gajiBersih;
    }
}
