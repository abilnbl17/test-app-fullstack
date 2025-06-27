import React, { useState, useEffect } from "react";

function App() {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const response = await fetch("http://localhost:3001/api/employees");

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setEmployees(data);
      } catch (error) {
        setError(error);
        console.error("Error fetching employees:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchEmployees();
  }, []); Array kosong berarti useEffect ini hanya berjalan sekali setelah render pertama

  const getGenderLabel = (gender) => {
    if (gender === 1) return "Pria";
    if (gender === 2) return "Wanita";
    return "Lainnya";
  };

 
  const handleAddEmployee = () => {
    alert("Fungsi Tambah Karyawan akan diimplementasikan di sini.");
  
  };

  const handleEditEmployee = (employeeId) => {
    alert(
      `Fungsi Edit Karyawan ID: ${employeeId} akan diimplementasikan di sini.`
    );

  };

  const handleDeleteEmployee = async (employeeId) => {
    if (
      window.confirm(
        `Apakah Anda yakin ingin menghapus karyawan ID: ${employeeId}?`
      )
    ) {
      try {
      
        const response = await fetch(
          `http://localhost:3001/api/employees/${employeeId}`,
          {
            method: "DELETE",
          }
        );
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
     
        setEmployees(employees.filter((emp) => emp.id !== employeeId));
        alert("Karyawan berhasil dihapus (soft delete).");
      } catch (error) {
        console.error("Error deleting employee:", error);
        alert("Gagal menghapus karyawan. Lihat konsol untuk detail.");
      }
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen bg-gray-100">
        <p className="text-lg text-gray-700">Memuat data karyawan...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex justify-center items-center h-screen bg-red-100 text-red-700">
        <p className="text-lg">Error: {error.message}</p>
      </div>
    );
  }

  return (
    <div className="container mx-auto p-6 bg-white shadow-lg rounded-lg mt-10">
      <h1 className="text-3xl font-bold text-gray-800 mb-6 text-center">
        Master Karyawan
      </h1>

      <div className="flex justify-end mb-4">
        <button
          onClick={handleAddEmployee}
          className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md shadow-md transition duration-300 ease-in-out"
        >
          + Tambah Karyawan
        </button>
      </div>

      {employees.length === 0 ? (
        <p className="text-center text-gray-600 text-lg">
          Tidak ada data karyawan yang ditemukan.
        </p>
      ) : (
        <div className="overflow-x-auto">
          <table className="min-w-full bg-white border border-gray-300 rounded-lg overflow-hidden">
            <thead className="bg-gray-200">
              <tr>
                <th className="py-3 px-4 text-left text-sm font-semibold text-gray-700 border-b">
                  No
                </th>
                <th className="py-3 px-4 text-left text-sm font-semibold text-gray-700 border-b">
                  Nama
                </th>
                <th className="py-3 px-4 text-left text-sm font-semibold text-gray-700 border-b">
                  Tanggal Lahir
                </th>
                <th className="py-3 px-4 text-left text-sm font-semibold text-gray-700 border-b">
                  Jabatan
                </th>
                <th className="py-3 px-4 text-left text-sm font-semibold text-gray-700 border-b">
                  NIP
                </th>
                <th className="py-3 px-4 text-left text-sm font-semibold text-gray-700 border-b">
                  Jenis Kelamin
                </th>
                <th className="py-3 px-4 text-left text-sm font-semibold text-gray-700 border-b">
                  Aksi
                </th>
              </tr>
            </thead>
            <tbody>
              {employees.map((employee, index) => (
                <tr key={employee.id} className="hover:bg-gray-50 border-b">
                  <td className="py-3 px-4 text-sm text-gray-800">
                    {index + 1}
                  </td>
                  <td className="py-3 px-4 text-sm text-gray-800">
                    {employee.name}
                  </td>
                  <td className="py-3 px-4 text-sm text-gray-800">
                    {employee.birthDate}
                  </td>
                  <td className="py-3 px-4 text-sm text-gray-800">
                    {employee.position ? employee.position.name : "N/A"}
                  </td>
                  <td className="py-3 px-4 text-sm text-gray-800">
                    {employee.idNumber}
                  </td>
                  <td className="py-3 px-4 text-sm text-gray-800">
                    {getGenderLabel(employee.gender)}
                  </td>
                  <td className="py-3 px-4 text-sm">
                    <button
                      onClick={() => handleEditEmployee(employee.id)}
                      className="bg-yellow-500 hover:bg-yellow-600 text-white py-1 px-3 rounded-md mr-2 text-xs transition duration-300 ease-in-out"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDeleteEmployee(employee.id)}
                      className="bg-red-500 hover:bg-red-600 text-white py-1 px-3 rounded-md text-xs transition duration-300 ease-in-out"
                    >
                      Hapus
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default App;
