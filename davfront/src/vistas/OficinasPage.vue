<template>
  <div class="oficinas_div">
    <h3>Oficinas</h3>
    <table>
      <thead>
      <tr>
        <th>Provincia</th>
        <th>País</th>
        <th>Número de Empleados</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="oficina in oficinas" :key="oficina.id">
        <td>{{ oficina.provincia }}</td>
        <td>{{ oficina.pais }}</td>
        <td>{{ oficina.empleados.length}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getAllOficinas } from '@/axios';
import Swal from "sweetalert2";

export default {
  name: 'OficinasPage',
  data() {
    return {
      oficinas: [],
    };
  },
  mounted() {
    this.fetchOficinas();
  },
  methods: {
    fetchOficinas() {
      getAllOficinas().then((response) => {
        this.oficinas = response.data.sort((a, b) =>{
          return a.provincia.localeCompare(b.provincia)
        });
      }).catch(() => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Ocurrió un error con la conexión. Por favor, inténtalo nuevamente.'
        });
      });
    },
  },
};
</script>

<style scoped>
.oficinas_div {
  padding: 20px;
  background-color: #f4f7fc;
  min-height: 100vh;
}

h3 {
  font-size: 2rem;
  color: #2A3D66;
  margin-bottom: 15px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 30px;
}

th, td {
  padding: 12px 15px;
  text-align: left;
  border: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
  font-weight: bold;
  color: #2A3D66;
}

td {
  background-color: #fff;
  color: #555;
}

tr {
  transition: background-color 0.3s ease;
}

tr:hover td{
  background-color: #f9f9f9;
}

/*responsive*/
@media (max-width: 768px) {
  h3 {
    font-size: 1.5rem;
    text-align: center;
  }

  table {
    font-size: 0.9rem;
  }

  th, td {
    padding: 10px;
  }
}
</style>