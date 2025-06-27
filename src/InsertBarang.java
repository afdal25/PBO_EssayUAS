import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class InsertBarang {

    // Database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/toko";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = ""; 
    

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Program Insert Data Barang ===");

            System.out.print("Masukkan Kode Barang (Contoh: B001): ");
            String kode = scanner.nextLine();

            System.out.print("Masukkan Nama Barang: ");
            String nama = scanner.nextLine();

            int harga = 0;
            int stok = 0;

            try {
                System.out.print("Masukkan Harga Barang (angka): ");
                harga = scanner.nextInt();

                System.out.print("Masukkan Stok Barang (angka): ");
                stok = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("\n>> ERROR: Input harga dan stok harus berupa angka. Program berhenti.");
                return; // Keluar dari program jika input salah
            }

            String sql = "{CALL insert_barang(?, ?, ?, ?)}";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 CallableStatement cstmt = conn.prepareCall(sql)) {

                cstmt.setString(1, kode);
                cstmt.setString(2, nama);
                cstmt.setInt(3, harga);
                cstmt.setInt(4, stok);

                cstmt.executeUpdate();

                System.out.println("\n>> SUKSES: Data barang berhasil dimasukkan.");
                System.out.println(">> TRIGGER secara otomatis telah mencatat log insert.");

            } catch (SQLException e) {
                System.err.println("\n>> ERROR: Gagal terhubung atau melakukan insert ke database.");
                System.err.println("Pesan Error: " + e.getMessage());
                System.err.println("Pastikan username/password dan nama database sudah benar, dan service MySQL/XAMPP sudah berjalan.");
            }
        }
    }
}