package id.kelompok.absensigaji.attendance;

/**
 * Status absensi resmi yang diizinkan oleh sistem.
 * Menggunakan enum Java untuk menjamin Type Safety dan mencegah typo string.
 *
 * Tim Pengembang & Kolaborasi:
 * - Penanggung Jawab / Author : Ade Rizky
 * - Reviewer Modul             : Ilham Sepriyadi & Muhammad Andriansyah
 *
 * [Catatan Kolaborasi]:
 * - Ade Rizky    : "Kita sepakat pakai enum daripada String bebas, biar nggak ada input macam 'hadir', 'Hadir', 'hdr'."
 * - Ilham        : "Sip De, di menu konsol Main gw tinggal parsing opsi 1-4 langsung mapping ke enum ini."
 * - Andriansyah  : "Di payroll gw juga jauh lebih gampang waktu filter catatan pakai statusAbsensi == StatusAbsensi.ALPHA."
 */
public enum StatusAbsensi {
    HADIR,
    IZIN,
    SAKIT,
    ALPHA
}
