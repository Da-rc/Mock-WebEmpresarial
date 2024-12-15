<template>
  <div class="popup" v-if="isVisible">
    <div class="popup-content">
      <h3>Crear Empleado</h3>
      <form @submit.prevent="createEmpleado">
        <div class="form-group">
          <label for="nombre">Nombre</label>
          <input type="text" id="nombre" v-model="empleado.nombre" required />
        </div>
        <div class="form-group">
          <label for="apellidos">Apellidos</label>
          <input type="text" id="apellidos" v-model="empleado.apellidos" required />
        </div>
        <div class="form-group">
          <label for="dni">DNI</label>
          <input type="text" id="dni" v-model="empleado.dni" required />
        </div>
        <div class="form-group">
          <label for="area">Área</label>
          <input type="text" id="area" v-model="empleado.area" required />
        </div>
        <div class="form-group">
          <label for="oficina">Oficina</label>
          <select id="oficina" v-model="empleado.oficina">
            <option v-for="oficina in oficinas" :key="oficina.id" :value="oficina">
              {{ oficina.provincia }}
            </option>
          </select>
        </div>
        <button type="submit">Crear Empleado</button>
        <button @click="closePopup" type="button">Cancelar</button>
      </form>
    </div>
  </div>
</template>

<script>
import { getAllOficinas } from '@/axios';
import { crearEmpleado } from '@/axios';
import Swal from "sweetalert2";

export default {
  props: ['isVisible', 'onClose'],
  data() {
    return {
      empleado: {
        nombre: '',
        apellidos: '',
        dni: '',
        area: '',
        oficina: null,
      },
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
    closePopup() {
      this.onClose();
    },
    createEmpleado() {

      const nuevoEmpleado = {
        nombre: this.empleado.nombre,
        apellidos: this.empleado.apellidos,
        dni: this.empleado.dni,
        area: this.empleado.area,
        oficina: this.empleado.oficina,
      };
      crearEmpleado(nuevoEmpleado).then(() => {
        this.closePopup(); // Cerrar popup después de crear el empleado
        this.$emit('refresh');
        Swal.fire(
            'Empleado creado',
            `Se ha creado el empleado ${nuevoEmpleado.nombre} ${nuevoEmpleado.apellidos} correctamente`,
            'sucess'
        );
      }).catch((error) => {
        if (error.response.status === 401) {
          Swal.fire('Alert', 'Debe estar logueado para ejecutar esta acción', 'Alert');
        }else{
          console.error('Error al crear el empleado:', error);
          Swal.fire('Error', 'Hubo un problema al crear el empleado', 'error');
        }
      });
    },
  },
};
</script>

<style scoped>
.popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.popup-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  width: 80%;
  max-width: 500px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  padding: 10px 20px;
  background-color: #6c4675;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #5a3b62;
}

button[type="button"] {
  background-color: #ccc;
}

button[type="button"]:hover {
  background-color: #bbb;
}
</style>