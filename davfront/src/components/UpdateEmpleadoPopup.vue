<template>
  <div class="popup" v-if="isVisible">
    <div class="popup-content">
      <h3>Editar Empleado</h3>
      <form @submit.prevent="editarEmpleado">
        <div class="form-group">
          <label for="nombre">Nombre</label>
          <input type="text" id="nombre" v-model="empleadoEditar.nombre" required />
        </div>
        <div class="form-group">
          <label for="apellidos">Apellidos</label>
          <input type="text" id="apellidos" v-model="empleadoEditar.apellidos" required />
        </div>
        <div class="form-group">
          <label for="dni">DNI</label>
          <input type="text" id="dni" v-model="empleadoEditar.dni" required />
        </div>
        <div class="form-group">
          <label for="area">Área</label>
          <input type="text" id="area" v-model="empleadoEditar.area" required />
        </div>
        <div class="form-group">
          <label for="oficina">Oficina</label>
          <select id="oficina" v-model="empleadoEditar.oficina">
            <option v-for="oficina in oficinas" :key="oficina.id" :value="oficina">
              {{ oficina.provincia }}
            </option>
          </select>
        </div>
        <button type="submit">Editar Empleado</button>
        <button @click="closePopup" type="button">Cancelar</button>
      </form>
    </div>
  </div>
</template>

<script>

import {getAllOficinas, updateEmpleado} from "@/axios";
import Swal from "sweetalert2";

export default {
  props: ['isVisible', 'onClose', 'empleado'],
  data() {
    return {
      empleadoEditar: { ...this.empleado},
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
    editarEmpleado() {
      updateEmpleado(this.empleadoEditar).then(() => {
        this.closePopup();
        this.$emit('refresh');
        Swal.fire(
            'Empleado actualizado',
            `Se ha actualizado el empleado ${this.empleadoEditar.nombre} ${this.empleadoEditar.apellidos} correctamente`,
            'sucess'
        );
      }).catch((error) => {
        if (error.response.status === 401) {
          Swal.fire('Alert', 'Debe estar logueado para ejecutar esta acción', 'Alert');
        }else {
          console.error('Error al actualizar el empleado:', error);
          Swal.fire('Error', 'Hubo un problema al actualizar el empleado', 'error');
        }
      })
    }
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