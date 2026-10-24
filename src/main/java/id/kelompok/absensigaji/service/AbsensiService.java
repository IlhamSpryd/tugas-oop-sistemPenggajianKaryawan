package id.kelompok.absensigaji.service;

import id.kelompok.absensigaji.model.CatatanAbsensi;
import id.kelompok.absensigaji.model.StatusAbsensi;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Mengelola aturan pencatatan dan pencarian data absensi. */
public class AbsensiService {
    private final List<CatatanAbsensi> catatan = new ArrayList<>();

    public void catatAbsensi(String idKaryawan, LocalDate tanggal,
                             StatusAbsensi status, int jamLembur) {
        boolean sudahAda = catatan.stream().anyMatch(item ->
                item.getIdKaryawan().equalsIgnoreCase(idKaryawan.trim())
                        && item.getTanggal().equals(tanggal));
        if (sudahAda) {
            throw new IllegalArgumentException("Absensi karyawan pada tanggal tersebut sudah ada.");
        }
        catatan.add(new CatatanAbsensi(idKaryawan, tanggal, status, jamLembur));
    }

    public List<CatatanAbsensi> getSemuaCatatan() {
        return catatan.stream()
                .sorted(Comparator.comparing(CatatanAbsensi::getTanggal))
                .toList();
    }

    public List<CatatanAbsensi> getCatatan(String idKaryawan, YearMonth periode) {
        return catatan.stream()
                .filter(item -> item.getIdKaryawan().equalsIgnoreCase(idKaryawan.trim()))
                .filter(item -> YearMonth.from(item.getTanggal()).equals(periode))
                .sorted(Comparator.comparing(CatatanAbsensi::getTanggal))
                .toList();
    }

    public long hitungStatus(List<CatatanAbsensi> data, StatusAbsensi status) {
        return data.stream().filter(item -> item.getStatus() == status).count();
    }

    public int hitungTotalJamLembur(List<CatatanAbsensi> data) {
        return data.stream().mapToInt(CatatanAbsensi::getJamLembur).sum();
    }
}
