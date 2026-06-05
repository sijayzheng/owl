<template>
  <div :style="`height:${height}`">
    <iframe :id="iframeId" style="width: 100%; height: 100%; border: 0" :src="src" />
  </div>
</template>

<script setup lang="ts">
defineProps({
  src: propTypes.string.def('/'),
  iframeId: propTypes.string.isRequired,
})
const height = ref(`${document.documentElement.clientHeight - 94.5}px`)
</script>
