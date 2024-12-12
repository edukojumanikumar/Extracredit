<template>
  <div id="app">
    <SurveyForm />
  </div>
</template>

<script>
import SurveyForm from './components/SurveyForm.vue'; // Correct path to SurveyForm.vue

export default {
  name: 'App',
  components: {
    SurveyForm // Register the component
  }
};
</script>

<style>
/* Global styling here */
</style>
