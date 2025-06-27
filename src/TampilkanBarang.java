import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TampilkanBarang {

    // Databse
    private static final String DB_URL = "jdbc:mysql://localhost:3306/toko";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = ""; 

    public static void main(String[] args) {
        String sql = "SELECT * FROM tampilan_barang_lengkap";

        System.out.println("=== Menampilkan Data Barang dari VIEW (tampilan_barang_lengkap) ===");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("%-12s | %-25s | %-12s | %-7s | %-15s\n", "KODE", "NAMA BARANG", "HARGA", "STOK", "TOTAL NILAI");
        System.out.println("---------------------------------------------------------------------------------");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean dataDitemukan = false;
            while (rs.next()) {
                dataDitemukan = true;
                String kode = rs.getString("kode");
                String nama = rs.getString("nama");
                int harga = rs.getInt("harga");
                int stok = rs.getInt("stok");
                long totalNilai = rs.getLong("total_nilai");

                System.out.printf("%-12s | %-25s | Rp %-9d | %-7d | Rp %-12d\n",
                        kode, nama, harga, stok, totalNilai);
            }

            if (!dataDitemukan) {
                System.out.println("... Tidak ada data barang untuk ditampilkan ...");
            }

        } catch (SQLException e) {
            System.err.println(">> ERROR: Gagal mengambil data dari database.");
            System.err.println("Pesan Error: " + e.getMessage());
        }
        System.out.println("---------------------------------------------------------------------------------");
    }
}