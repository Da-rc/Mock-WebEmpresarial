<template>
  <div class="popup-logout" @click.self="closePopup">
    <div class="popup">
      <h3>Logout Successful</h3>
      <p>Te has desconectado correctamente</p>
      <button @click="closePopup" class="close-button">Close</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LogoutPopup',
  methods: {
    closePopup() {
      this.$emit('close');
    }
  }
};
</script>



<style scoped>
.popup-logout {
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
  text-align: center;
}

.popup h3 {
  margin-bottom: 20px;
  font-size: 20px;
}

.close-button {
  margin-top: 10px;
  padding: 5px 10px;
  background-color: #6c4675;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
}

.close-button:hover {
  background-color: #5a3b62;
}
</style>