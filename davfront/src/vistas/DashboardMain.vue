
<template>
  <div>
    <h2>{{ pageTitle }}</h2>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'DashboardMain',
  data() {
    return {
      pageTitle: 'Dashboard',
    };
  },
};
</script>

<style>
h2 {
  color: #6c4675;
}
</style>