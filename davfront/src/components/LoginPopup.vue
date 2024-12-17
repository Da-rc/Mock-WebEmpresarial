<template>
  <div class="popup-login" @click.self="closePopup">
    <div class="popup-content">
      <h3>Iniciar Sesión</h3>
      <form @submit.prevent="validateLogin">
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" v-model="loginData.email" required />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="loginData.password" required />
        </div>
        <p v-if="loginError" class="error-message">{{ loginError }}</p>
        <button type="submit" class="submit-button">Validate</button>
      </form>
      <button @click="closePopup" class="close-button">Close</button>
    </div>
  </div>
</template>

<script>
import { auth } from '@/firebase';
import { signInWithEmailAndPassword } from 'firebase/auth';
import Swal from "sweetalert2";

export default {
  name: 'LoginPopup',
  data() {
    return {
      loginData: {
        email: '',
        password: '',
      },
      loginError: '',
    };
  },
  methods: {
    closePopup() {
      this.$emit('close');
    },
    validateLogin() {
      const { email, password } = this.loginData;

      signInWithEmailAndPassword(auth, email, password)
          .then(() => {
            this.closePopup();
            Swal.fire(
                'Login',
                `Se ha conectado correctamente`,
                'sucess'
            );
          })
          .catch((error) => {
            console.error('Login error:', error.code, error.message);
            this.loginError = 'Invalid email or password. Please try again.';
          });
    },
  },
};
</script>

<style scoped>
/* Estilos del popup */
.popup-login {
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
  background: white;
  padding: 25px;
  border-radius: 10px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
  max-width: 400px;
  width: 90%;
  text-align: center;
}

.popup-content h3 {
  margin-bottom: 15px;
  color: #333;
  font-size: 1.5rem;
}

.form-group {
  margin-bottom: 15px;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 600;
  color: #555;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.submit-button {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  border-color: #6c4675;
  outline: none;
}

.error-message {
  color: #e74c3c;
  font-size: 0.9rem;
  margin-bottom: 10px;
  text-align: left;
}

.submit-button,
.close-button {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
  font-size: 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.submit-button {
  background-color: #333;
  color: #fff;
  font-weight: bold;
}

.submit-button:hover {
  background-color: #2A3D66;
}

.close-button {
  background-color: #ccc;
  color: #333;
}

.close-button:hover {
  background-color: #bbb;
}

/* responsive */
@media (max-width: 480px) {
  .popup-content {
    padding: 15px;
    border-radius: 8px;
  }
  .popup-content h3 {
    font-size: 1.3rem;
  }
  .form-group input {
    font-size: 0.9rem;
  }
  .submit-button,
  .close-button {
    padding: 8px;
    font-size: 0.9rem;
  }
}

</style>