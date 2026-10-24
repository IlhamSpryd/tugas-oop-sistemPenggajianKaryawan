@echo off
setlocal

rem Prioritaskan JDK Java 21 BlueJ agar tidak bentrok dengan JRE lama
if exist "C:\Program Files\BlueJ\jdk\bin\javac.exe" (
    set "JAVAC_CMD=C:\Program Files\BlueJ\jdk\bin\javac.exe"
    set "JAVA_CMD=C:\Program Files\BlueJ\jdk\bin\java.exe"
) else (
    set "JAVAC_CMD=javac"
    set "JAVA_CMD=java"
)

echo [1/2] Mengompilasi program...
if not exist "out" mkdir out

dir /s /b src\main\java\*.java > sources.txt
"%JAVAC_CMD%" -d out @sources.txt
del sources.txt

if %ERRORLEVEL% neq 0 (
    echo [ERROR] Kompilasi gagal.
    pause
    exit /b %ERRORLEVEL%
)

echo [2/2] Menjalankan Sistem Absensi dan Penggajian Karyawan...
echo ==========================================================
"%JAVA_CMD%" -cp out id.kelompok.absensigaji.app.Main
pause
