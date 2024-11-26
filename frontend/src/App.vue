<script setup>
import MainNav from '@/components/layout/MainNav.vue'
import ChatbotBtn from './components/chatbot/ChatbotBtn.vue'
</script>

<template>
  <header>
    <MainNav />
  </header>
  <body>
    <RouterView />
  </body>
  <ChatbotBtn />
</template>

<style scoped>
body {
  padding-top: 75px;
}
</style>
