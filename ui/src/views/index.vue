<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>
      this is the index page
    </h3>
  </div>
</template>
<script setup lang="ts">

const msg = ref('Hello')
</script>
