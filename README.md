# 🚀 Test Aplikasi Karyawan Full-stack #
Aplikasi ini adalah implementasi sistem Master Karyawan berbasis web, yang terdiri dari bagian Backend (Spring Boot) dan Frontend (React). Dirancang untuk mengelola data karyawan dan jabatan (posisi) dengan fitur CRUD (Create, Read, Update, Delete) yang diimplementasikan melalui REST API.

<br>
<br>

## 🎯 Daftar Isi ##
1. Spesifikasi Proyek

2. Struktur Proyek

3. Persiapan Lingkungan Pengembangan

4. Panduan Setup & Jalankan Aplikasi

    4.1. Setup & Jalankan Backend (Spring Boot)

    4.2. Setup & Jalankan Frontend (React)

5. Endpoints API Backend

6. Pengujian

7. Langkah Selanjutnya (Pengembangan Lanjutan)

<br>
<br>

## 1. 📋 Spesifikasi Proyek ##
- Arsitektur: Full-stack Web Application (Client-Server)

- Frontend: React.js dengan Tailwind CSS

    - Controller diimplementasikan dalam file JavaScript (komponen React).

- Backend: Spring Boot

    - Persistensi data menggunakan JPA dengan Hibernate.

    - Semua query database diimplementasikan menggunakan HQL.

- Komunikasi: REST API (JSON)

    - Dilengkapi dengan konfigurasi CORS untuk memungkinkan komunikasi lintas domain antara Frontend dan Backend.

- Database: H2 Database (in-memory, untuk pengembangan)

<br>
<br>

## 2. 📁 Struktur Proyek ##
Proyek ini memiliki dua folder utama:
```
├── backend/     # Proyek Spring Boot 
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/test_app/
│   │   │   │       ├── BackendApplication.java # Kelas aplikasi utama
│   │   │   │       ├── controller/
│   │   │   │       │   ├── EmployeeController.java
│   │   │   │       │   └── PositionController.java
│   │   │   │       ├── model/
│   │   │   │       │   ├── Employee.java
│   │   │   │       │   └── Position.java
│   │   │   │       └── repository/
│   │   │   │           ├── EmployeeRepository.java
│   │   │   │           └── PositionRepository.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── pom.xml
└── frontend/                 # Proyek React Frontend (nama folder bisa disesuaikan, misal 'web')
    ├── public/
    ├── src/
    │   ├── App.jsx
    │   └── index.css
    ├── index.html
    ├── package.json
    ├── postcss.config.js
    └── tailwind.config.js
```

<br>
<br>

## 3. 🛠️ Persiapan Lingkungan Pengembangan ##
Pastikan perangkat lunak berikut terinstal di sistem Anda:

- Java Development Kit (JDK): Versi 17 atau lebih tinggi.

    - Verifikasi: java -version dan javac -version

- Node.js & npm: Node.js akan menginstal npm secara otomatis.

    - Verifikasi: node -v dan npm -v

- Maven: Digunakan untuk membangun proyek Spring Boot.

    - Verifikasi: mvn --version (jika tidak ditemukan, gunakan ./mvnw dari root folder backend)

- IDE: IntelliJ IDEA, VS Code (dengan ekstensi Java & React), atau Eclipse.

- Opsional: Postman atau Insomnia untuk pengujian API.

<br>
<br>

## 4. 🚀 Panduan Setup & Jalankan Aplikasi ##
Ikuti langkah-langkah di bawah ini untuk menjalankan aplikasi Backend dan Frontend.

<br>

### 4.1. Setup & Jalankan Backend (Spring Boot) ###

<br>

**1. Navigasi ke Direktori Backend:**
Buka terminal dan masuk ke folder backend proyek Anda.

``` cd path/to/your/project/backend ```

<br>

**2. Perbarui Nama Paket (Jika Belum):**
Pastikan semua file Java Anda (.java di model, repository, controller, dan kelas aplikasi utama) memiliki deklarasi paket yang sesuai dengan struktur folder Anda. Berdasarkan diskusi kita, nama paket yang benar adalah com.example.test_app.

Contoh:

```
package com.example.test_app.model;
import com.example.test_app.model.Employee;
```
<br>

**3. Konfigurasi Database (H2) & Port:**
Pastikan file backend/src/main/resources/application.properties berisi konfigurasi berikut untuk H2 Database (in-memory) dan port server:

```
server.port=3001
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
<br>

**4. Konfigurasi CORS:**
Pastikan kelas aplikasi utama Anda (misal BackendApplication.java atau TestAppApplication.java) memiliki konfigurasi CORS yang di-@Bean untuk mengizinkan permintaan dari frontend.

```
// ... di dalam kelas utama aplikasi Spring Boot Anda
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
                    .allowedOrigins("*") // Untuk development, izinkan semua origin
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
        }
    };
}
```
<br>

**5. Jalankan Aplikasi Backend:**
Dari terminal di folder backend, jalankan:
```
./mvnw spring-boot:run

(Untuk Windows: .\mvnw spring-boot:run)
```

Aplikasi akan berjalan di http://localhost:3001. Anda akan melihat log Hibernate membuat tabel t1_position dan t2_employee.

<br>
<br>

### 4.2. Setup & Jalankan Frontend (React) ###

<br>

**1. Navigasi ke Direktori Frontend:**
Buka terminal baru dan masuk ke folder frontend proyek Anda (atau nama folder frontend yang Anda gunakan).
```
cd path/to/your/project/frontend
```
<br>

**2. Instal Dependensi NPM:**
Instal semua dependensi proyek React, termasuk Tailwind CSS dan PostCSS:
```
npm install -D tailwindcss postcss autoprefixer @tailwindcss/postcss
```
Kemudian, jalankan instalasi utama:
```
npm install
```
<br>

**3. Konfigurasi Tailwind CSS:**
<br>

- postcss.config.js: Pastikan file ini di root folder frontend Anda berisi:
```
module.exports = {
  plugins: {
    'tailwindcss': {},
    'autoprefixer': {},
  },
};
```

<br>

- tailwind.config.js: Pastikan bagian content memindai file React Anda:
```
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
```

<br>

- src/index.css (atau src/main.css): Pastikan file CSS utama Anda berisi:
```
@tailwind base;
@tailwind components;
@tailwind utilities;
```

<br>

**4. Implementasi Komponen React (App.jsx):**
Ganti konten file src/App.jsx dengan kode yang telah disediakan, yang menangani fetching data dari http://localhost:3001/api/employees dan menampilkan daftar karyawan.

<br>

**5. Jalankan Aplikasi Frontend:**
Dari terminal di folder frontend, jalankan:
```
npm run dev
```
<br>
- Aplikasi akan berjalan di http://localhost:5173 (atau port lain yang ditunjukkan oleh Vite).

<br>
<br>

## 5. 🔌 Endpoints API Backend ##
Berikut adalah ringkasan endpoint utama yang tersedia di backend Anda untuk pengujian menggunakan Postman/Insomnia:

<br>

**Untuk Position (Jabatan) - Base URL: /api/positions**
- GET /api/positions: Mengambil semua posisi aktif.

- GET /api/positions/{id}: Mengambil posisi berdasarkan ID.

- GET /api/positions/search?name={nama_posisi}: Mencari posisi aktif berdasarkan nama.

- POST /api/positions: Membuat posisi baru (body JSON: {"code": "...", "name": "..."}).

- PUT /api/positions/{id}: Memperbarui posisi (body JSON: {"id": ..., "code": "...", "name": "...", "isDelete": 0}).

- DELETE /api/positions/{id}: Melakukan soft delete pada posisi.

<br>

**Untuk Employee (Karyawan) - Base URL: /api/employees**
- GET /api/employees: Mengambil semua karyawan aktif.

- GET /api/employees/{id}: Mengambil karyawan berdasarkan ID.

- GET /api/employees/search?name={nama_karyawan}: Mencari karyawan aktif berdasarkan nama.

- POST /api/employees: Membuat karyawan baru (body JSON mencakup name, birthDate, position (id), idNumber, gender).

- PUT /api/employees/{id}: Memperbarui karyawan (body JSON mencakup semua field).

- DELETE /api/employees/{id}: Melakukan soft delete pada karyawan.

- GET /api/employees/positions-for-dropdown: Mengambil daftar posisi aktif untuk dropdown.

<br>
<br>

## 6. ✅ Pengujian ##
1. Mulai backend Spring Boot di satu terminal.

2. Mulai frontend React di terminal lain.

3. Akses frontend di browser Anda (http://localhost:5173).

4. Gunakan Postman/Insomnia untuk menguji endpoint POST/PUT/DELETE di backend.

<br>
<br>

## 7. 💡 Langkah Selanjutnya (Pengembangan Lanjutan) ##
- Implementasikan form tambah/edit karyawan dan jabatan di frontend.

- Tambahkan validasi sisi frontend pada form.

- Implementasikan fitur pencarian yang lengkap di frontend.

- Tambahkan fitur pagination atau infinite scrolling.

- Perbaiki tampilan UI/UX agar lebih sesuai dengan mockup.

- Gunakan library manajemen state yang lebih canggih di React (misal Redux, Zustand, Context API).

- Pertimbangkan penggunaan DTO (Data Transfer Object) di backend untuk memisahkan model domain dari representasi API.
