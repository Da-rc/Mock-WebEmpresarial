<template>
  <div>
    <h3>Empleados</h3>
    <table>
      <thead>
      <tr>
        <th>Nombre</th>
        <th>Apellidos</th>
        <th>DNI</th>
        <th>Área</th>
        <th>Localización Oficina</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="empleado in empleados" :key="empleado.id">
        <td>{{ empleado.nombre }}</td>
        <td>{{ empleado.apellidos }}</td>
        <td>{{ empleado.dni }}</td>
        <td>{{ empleado.area }}</td>
        <td>{{ empleado.oficina.provincia }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getAllEmpleados } from '@/axios';

export default {
  name: 'EmpleadosPage',
  data() {
    return {
      empleados: [],
    };
  },
  mounted() {
    this.fetchEmpleados();
  },
  methods: {
    fetchEmpleados() {
      getAllEmpleados().then((response) => {
        this.empleados = response.data;
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