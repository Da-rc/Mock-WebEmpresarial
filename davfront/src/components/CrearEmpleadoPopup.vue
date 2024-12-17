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
        <div class="form-buttons">
        <button type="submit" class="submit-btn">
          <i class="pi pi-plus-circle" />
          Crear Empleado</button>
        <button @click="closePopup" type="button" class="cancel-btn">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { getAllOficinas } from '@/axios';
import { crearEmpleado } from '@/axios';
import Swal from "sweetalert2";
import 'primeicons/primeicons.css'

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
      /*if (!this.validarDNINIE(this.empleado.dni)) {
        Swal.fire(
            'Error de Validación',
            'El DNI/NIE proporcionado no es válido. Por favor, revisa el formato.',
            'error'
        );
        return;
      }*/

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
            'success'
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
    /* COMENTADO PORQUE VAN A SER DATOS FICTICIOS
    validarDNINIE(dniNie) {
      const letrasDNI = "TRWAGMYFPDXBNJZSQVHLCKE"; // Letras válidas para DNI y NIE
      let numero, letraCalculada, letraUsuario;

      // Validar patrón (DNI: 8 dígitos + letra / NIE: Letra + 7 dígitos + letra)
      const dniNiePattern = /^[XYZ]?\d{7,8}[A-Z]$/;
      if (!dniNiePattern.test(dniNie)) return false;

      // Si es NIE, reemplazar la letra inicial por su valor numérico:
      const niePrefix = { X: 0, Y: 1, Z: 2 };
      if (dniNie.charAt(0).toUpperCase() in niePrefix) {
        numero = dniNie.replace(
            dniNie.charAt(0),
            niePrefix[dniNie.charAt(0).toUpperCase()]
        );
      } else {
        numero = dniNie.substring(0, dniNie.length - 1);
      }

      // Calcular la letra correspondiente
      letraCalculada = letrasDNI[numero % 23];
      letraUsuario = dniNie.slice(-1).toUpperCase();

      return letraCalculada === letraUsuario;
    },*/
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
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  width: 80%;
  max-width: 450px;
  animation: fadeIn 0.3s ease-in-out;
  overflow-y: auto;
}

@keyframes fadeIn {
  from {
    transform: scale(0.9);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

h3 {
  text-align: center;
  font-size: 1.5rem;
  margin-bottom: 15px;
  color: #333;
  font-weight: 600;
}

.form-group {
  margin-bottom: 12px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 600;
  color: #555;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  outline: none;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group select:focus {
  border-color: #6c4675;
  box-shadow: 0 0 3px rgba(108, 70, 117, 0.3);
}

.form-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-btn {
  background-color: #333;
  color: #fff;
}

.submit-btn:hover {
  background-color: #2A3D66;
}

.cancel-btn {
  background-color: #e0e0e0;
  color: #333;
}

.cancel-btn:hover {
  background-color: #d1d1d1;
}

/* responsive */
@media (max-width: 480px) {
  .popup-content {
    width: 95%;
    padding: 15px;
  }

  .popup-title {
    font-size: 1.2rem;
  }

  .form-group input,
  .form-group select {
    padding: 8px;
    font-size: 0.9rem;
  }

  button {
    padding: 8px 15px;
    font-size: 0.9rem;
  }
}
</style>