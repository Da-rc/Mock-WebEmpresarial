
<template>
  <div class="dashboard-div">
    <h2>{{ pageTitle }}</h2>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas gravida dui lacinia aliquam consequat.
      Proin aliquam ex nec consequat scelerisque. Integer lacus ante, tristique maximus turpis vitae, tristique congue ex.
      Sed ultrices erat massa, vitae porttitor turpis ultricies id. Pellentesque dapibus urna a malesuada ullamcorper. Duis at euismod tortor.
      Sed at eleifend mauris. Aliquam imperdiet, leo quis condimentum scelerisque, metus turpis sodales lorem, eu sodales arcu lacus quis elit.
      In viverra risus eget lorem semper, id eleifend mi vestibulum. Nunc dolor ipsum, tincidunt non sem eu, gravida aliquet leo. Curabitur vitae faucibus augue.
      Aliquam interdum id metus ac lacinia. Aenean consequat vitae risus id dignissim. </p>
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
.dashboard-div {
  padding: 20px;
  background-color: #f4f7fc;
  min-height: 100vh;
}

h2 {
  font-size: 2.5rem;
  font-weight: bold;
  color: #2A3D66;
  margin-bottom: 15px;
}

p {
  font-size: 1.1rem;
  line-height: 1.8;
  color: #555;
  margin-bottom: 30px;
  text-align: justify;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

/* responsive */
@media (max-width: 768px) {
  h2{
    font-size: 2rem;
  }

  p{
    font-size: 1rem;
    margin-left: 20px;
    margin-right: 20px;
  }
}
</style>