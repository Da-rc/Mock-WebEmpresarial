<template>
  <div class="popup-login" @click.self="closePopup">
    <div class="popup">
      <h3>Login</h3>
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
          .then((userCredential) => {
            const user = userCredential.user;
            console.log('logueado', user);
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

.popup {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  max-width: 400px;
  width: 90%;
}

.popup h3 {
  margin-bottom: 20px;
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
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
  background-color: #6c4675;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #5a3b62;
}

.close-button {
  margin-top: 10px;
  padding: 5px 10px;
  background-color: #ccc;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.close-button:hover {
  background-color: #bbb;
}
</style>