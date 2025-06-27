package com.example.test_app.repository;

import com.example.test_app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<com.example.test_app.model.Product, Long> {

    // JpaRepository sudah otomatis menyediakan banyak metode CRUD dasar, seperti:
    //
    // - save(Product product): Menyimpan atau memperbarui objek produk
    // - findById(Long id): Mencari produk berdasarkan ID-nya
    // - findAll(): Mengambil semua produk yang ada
    // - delete(Product product): Menghapus produk
    // ...dan banyak lagi metode lainnya yang bisa langsung digunakan.
    // --- Contoh HQL Query sesuai persyaratan tes "Semua query harus menggunakan
    // HQL" ---
    // HQL Query untuk mencari produk berdasarkan nama (pencarian sebagian dan tidak
    // case-sensitive)
    // 'p' adalah alias untuk Entity Product
    // LOWER(p.name) dan LOWER(CONCAT('%', :name, '%')) digunakan agar pencarian
    // tidak case-sensitive
    // :name adalah placeholder untuk parameter yang akan datang dari argumen metode
    // findProductsByNameContaining
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findProductsByNameContaining(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.price > :minPrice ORDER BY p.price DESC")
    List<Product> findProductsPricierThan(@Param("minPrice") double minPrice);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.price < :maxPrice")
    long countProductsCheaperThan(@Param("maxPrice") double maxPrice);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) = LOWER(:name)")
    Optional<Product> findByNameWithHQL(@Param("name") String name);
}