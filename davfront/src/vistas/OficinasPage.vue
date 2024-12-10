<template>
  <div>
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
        this.oficinas = response.data;
      });
    },
  },
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 8px 12px;
  text-align: left;
  border: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
}
</style>