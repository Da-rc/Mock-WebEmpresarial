<template>
  <div>
    <h3>Empleados</h3>
    <button @click="openPopup" class="add-button">Añadir Empleado</button>
    <table>
      <thead>
      <tr>
        <th>Nombre</th>
        <th>Apellidos</th>
        <th>DNI</th>
        <th>Área</th>
        <th>Localización Oficina</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="empleado in empleados" :key="empleado.id">
        <td>{{ empleado.nombre }}</td>
        <td>{{ empleado.apellidos }}</td>
        <td>{{ empleado.dni }}</td>
        <td>{{ empleado.area }}</td>
        <td>{{ empleado.oficina && empleado.oficina.provincia ? empleado.oficina.provincia : '' }}</td>
        <td>
          <button @click="editEmpleado(empleado.id)">Editar</button>
          <button @click="deleteEmpleado(empleado.id)">Eliminar</button>
        </td>
      </tr>
      </tbody>
    </table>

    <crear-empleado-popup
        v-if="showPopup"
        :isVisible="showPopup"
        :onClose="closePopup"
        @refresh="fetchEmpleados"
    />

  </div>
</template>

<script>
import { getAllEmpleados} from '@/axios';
import CrearEmpleadoPopup from '../components/CrearEmpleadoPopup.vue';

export default {
  name: 'EmpleadosPage',
  components: {
    CrearEmpleadoPopup,
  },
  data() {
    return {
      empleados: [],
      showPopup: false,
      isAuthenticated: false,
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
    openPopup() {
      this.showPopup = true;
    },
    closePopup() {
      this.showPopup = false;
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