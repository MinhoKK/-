<template>
  <div class="max-w mx-auto p-4">
    <!-- Chat Container -->
    <div class="bg-slate-50 rounded-lg shadow-md p-4 max-w-[700px] max-h-[800px]">
      <!-- Chat Header -->
      <div class="flex items-center mb-4">
        <div class="ml-3">
          <p class="text-xl font-medium">🏠 인테리어에 대한 모든 것을 물어보세요!</p>
          <p class="text-gray-500">Online</p>
        </div>
      </div>

      <!-- Chat Messages -->
      <div class="space-y-4 chat-messages" style="max-height: 400px; overflow-y: auto">
        <!-- Chat Messages -->
        <div
          v-for="(message, index) in messages"
          :key="index"
          :id="'message-' + index"
          :class="
            message.sender === 'assistant' ? 'flex items-start' : 'flex items-end justify-end'
          "
        >
          <div v-if="message.sender === 'assistant'" class="ml-3 bg-gray-100 p-3 rounded-lg">
            <p class="text-lg text-gray-800" v-html="formatMessage(message.text)"></p>
            <div v-if="message.images">
              <div v-for="(image, imgIndex) in message.images" :key="imgIndex" class="mt-2">
                <p v-if="message.titles && message.titles[imgIndex]" class="font-bold">
                  <a
                    :href="message.urls[imgIndex]"
                    target="_blank"
                    class="text-blue-500 hover:underline"
                    >{{ message.titles[imgIndex] }}</a
                  >
                </p>
                <img :src="image" alt="Assistant Image" class="rounded-lg max-w-xs" />
              </div>
            </div>
          </div>
          <div v-else class="bg-blue-500 p-3 rounded-lg">
            <p class="text-lg text-white" v-html="formatMessage(message.text)"></p>
          </div>
          <img
            v-if="message.sender !== 'assistant'"
            :src="userAvatar"
            :alt="message.sender + ' Avatar'"
            class="w-8 h-8 rounded-full ml-3"
          />
        </div>
      </div>

      <div
        v-if="waitingForResponse"
        class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50"
      >
        <div class="p-4 rounded-lg">
          <Vue3Lottie
            class="h-96 w-96"
            :animationData="loading3"
            :height="200"
            :width="200"
            :speed="1"
          />
        </div>
      </div>

      <!-- Chat Input -->
      <div class="mt-4 flex items-center">
        <input
          type="text"
          placeholder="Type your message..."
          class="flex-1 py-2 px-3 rounded-full bg-gray-100 focus:outline-none"
          v-model="newMessage"
          @keyup.enter="sendMessage"
          :disabled="waitingForResponse"
        />
        <button
          class="bg-blue-500 text-white px-4 py-2 rounded-full ml-3 hover:bg-blue-600"
          @click="sendMessage"
          :disabled="waitingForResponse"
        >
          Send
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Vue3Lottie } from 'vue3-lottie'
import { ref, nextTick, onMounted } from 'vue'
import axios from 'axios'
import loading1 from '/src/assets/json/loading1.json'
import loading2 from '/src/assets/json/loading2.json'
import loading3 from '/src/assets/json/loading3.json'

const messages = ref([{ sender: 'assistant', text: '안녕하세요. 무엇을 도와드릴까요?' }])

const newMessage = ref('')

const userAvatar = 'https://pbs.twimg.com/profile_images/1707101905111990272/Z66vixO-_normal.jpg'

const waitingForResponse = ref(false) // AI 응답을 기다리고 있는지 여부를 추적하는 변수

async function sendMessage() {
  if (newMessage.value.trim() === '') return
  const query = newMessage.value
  waitingForResponse.value = true // 응답을 기다리고 있다고 표시
  messages.value.push({ sender: 'user', text: newMessage.value })

  scrollToMessage(messages.value.length - 1)

  newMessage.value = '' // 지워주기
  await getAIResponse(query)

  waitingForResponse.value = false // 응답을 받은 후에 다시 false로 설정
}

async function getAIResponse(userMessage) {
  try {
    const response = await axios.post('http://localhost:5001/chat', { query: userMessage })
    const aiReply = response.data.answer
    const aiImages = response.data.images
    const aiTitles = response.data.titles
    const aiUrls = response.data.urls
    messages.value.push({
      sender: 'assistant',
      text: aiReply,
      images: aiImages,
      titles: aiTitles,
      urls: aiUrls
    })
    scrollToMessage(messages.value.length - 1)
  } catch (error) {
    console.error('Error fetching AI response:', error)
    messages.value.push({
      sender: 'assistant',
      text: "I'm sorry, something went wrong. Please try again later."
    })
    scrollToMessage(messages.value.length - 1)
  }
}

async function scrollToMessage(messageIndex) {
  await nextTick()
  const messageElement = document.getElementById(`message-${messageIndex}`)
  if (messageElement) {
    messageElement.scrollIntoView({ behavior: 'smooth', block: 'start' })
  }
}

function formatMessage(text) {
  return text.replace(/\n/g, '<br>')
}

onMounted(() => {
  messages.value = [{ sender: 'assistant', text: '안녕하세요. 무엇을 도와드릴까요?' }]
})
</script>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';

.max-w {
  max-width: 66%;
}
</style>
