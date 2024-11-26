<script setup>
import MainFooter from '@/components/layout/MainFooter.vue'
import { Vue3Lottie } from 'vue3-lottie'
import homeJson1 from '@/assets/json/house1'
import checkList1 from '@/assets/json/checklist1'
import draw1 from '@/assets/json/draw1'
import room1 from '@/assets/json/room1'
import room2 from '@/assets/json/room2'
</script>

<template>
  <main>
    <section>
      <div class="container mx-auto px-6 py-16 text-center">
        <div class="mx-auto max-w-xl">
          <h1 class="text-3xl font-bold text-gray-800 dark:text-white lg:text-4xl">
            체크인홈은 당신의 집을 찾고 <br />
            꾸미는 과정까지의 여정을 책임집니다.
          </h1>
          <p class="mt-6 text-gray-500 dark:text-gray-300">
            검색을 통해 아파트에 대한 정보를 얻어보세요!
          </p>

          <button
            class="mt-6 rounded-lg bg-blue-600 px-6 py-2.5 text-center text-sm font-medium capitalize leading-5 text-white hover:bg-blue-500 focus:outline-none lg:mx-0 lg:w-auto"
          >
            <router-link :to="{ name: 'aptMain' }">메물 검색 바로가기</router-link>
          </button>
        </div>

        <div class="mt-10 flex justify-center">
          <img
            class="h-96 w-full rounded-xl object-cover lg:w-4/5"
            src="https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/board/main1.jpg"
          />
        </div>
      </div>
    </section>

    <!-- component 새로운 디자인 -->
    <div class="w-full my-20 sticky px-0">
      <div class="bg-blue-100">
        <div
          class="py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl lg:py-20 bg-blue-100 rounded-xl px-6"
        >
          <div class="flex flex-col items-center justify-between w-full mb-10 lg:flex-row">
            <div class="mb-16 lg:mb-0 lg:max-w-lg lg:pr-5">
              <div class="max-w-xl mb-6">
                <h2
                  class="font-sans text-3xl font-bold tracking-tight text-black sm:text-4xl sm:leading-none max-w-lg mb-6"
                >
                  챗봇의 도움을 받아 <br />
                  방을 꾸며보세요!
                </h2>
                <p class="text-black text-base md:text-lg">
                  체크인홈의 스마트 챗봇은 당신의 취향에 맞춘 인테리어 아이디어를 제공합니다. 대화를
                  통해 나만의 공간을 만들어보세요!
                </p>
              </div>
            </div>
            <Vue3Lottie
              class="h-96 w-full rounded-lg object-cover"
              :animationData="room1"
              :height="200"
              :width="200"
              :speed="1"
            />
          </div>
        </div>
      </div>

      <div>
        <div
          class="px-6 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20"
        >
          <div class="flex flex-col items-center justify-between w-full mb-10 lg:flex-row">
            <Vue3Lottie
              class="h-96 w-full rounded-lg object-cover"
              :animationData="checkList1"
              :height="200"
              :width="200"
            />

            <div class="mb-16 lg:mb-0 lg:max-w-lg lg:pr-5">
              <div class="max-w-xl mb-6">
                <h2
                  class="font-sans text-3xl sm:mt-0 mt-6 font-bold tracking-tight text-black sm:text-4xl sm:leading-none max-w-lg mb-6"
                >
                  체크리스트를 통해 <br />
                  매물을 확인하세요!
                </h2>
                <p class="text-black text-base md:text-lg">
                  체크리스트 기능을 사용하여 관심 있는 매물을 꼼꼼하게 확인하고 관리하세요. 중요한
                  사항을 놓치지 마세요!
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="bg-blue-100">
        <div
          class="py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl lg:py-20 bg-blue-100 rounded-xl px-6"
        >
          <div class="flex flex-col items-center justify-between w-full mb-10 lg:flex-row">
            <div class="mb-16 lg:mb-0 lg:max-w-lg lg:pr-5">
              <div class="max-w-2xl mb-6">
                <h2
                  class="font-sans text-3xl font-bold tracking-tight text-black sm:text-4xl sm:leading-none max-w-lg mb-6"
                >
                  내가 원하는 지역을 <br />
                  직접 그려 매물 검색!
                </h2>
                <p class="text-black text-base md:text-lg">
                  지도를 이용해 원하는 지역을 직접 지정하여 매물을 검색해보세요. 직관적인
                  인터페이스로 쉽고 빠르게 매물을 찾을 수 있습니다.
                </p>
              </div>
            </div>
            <Vue3Lottie
              class="h-96 w-full rounded-lg object-cover"
              :animationData="draw1"
              :height="200"
              :width="200"
              :speed="0.7"
            />
          </div>
        </div>
      </div>
    </div>
  </main>
  <footer>
    <MainFooter />
  </footer>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
</style>
