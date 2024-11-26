<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import {
  registChecklistApt,
  checklistAptView,
  checklistApt,
  changeItemChecked,
  deletechecklistApt
} from '@/api/checklist'
import { httpStatusCode } from '@/util/http-status'
import { useAptInfoStore } from '@/stores/aptInfo'

const props = defineProps({
  aptId: String
})

const emit = defineEmits(['deleteChecklistApt']); // deleteChecklistApt 이벤트를 발생시키는 emit 정의

const deleteChecklist = () => {
  // deleteChecklistApt 이벤트 발생 및 aptId 전달
  console.log("하위에서 전달")
  emit('deleteChecklistApt', aptInfo.value.aptId,);
  console.log(aptInfo.value.aptId);
}

const aptInfoStore = useAptInfoStore()
const aptInfo = computed(() => aptInfoStore.aptInfo)

const checklistAptId = ref(null)
const checklistAptItems = ref([])

// 체크리스트 템플릿 적용하기
const doRegistChecklistApt = async () => {
  await registChecklistApt(
    aptInfo.value.aptId,
    (response) => {
      if (response.status === httpStatusCode.CREATE) {
        checklistAptId.value = response.data
        doChecklistAptView()
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

// 체크리스트 적용 제거하기
const doDeletechecklistApt = async () => {
  await deletechecklistApt(
    checklistAptId.value,
    (response) => {
      if (response.status === httpStatusCode.NOCONTENT) {
        
        deleteChecklist(); // emit
        checklistAptId.value = null
        checklistAptItems.value = []
        console.log('삭제 완료')
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

// 체크리스트 요소 조회
const doChecklistAptView = async () => {
  await checklistAptView(
    checklistAptId.value,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        checklistAptItems.value = response.data
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

const doChecklistApt = async () => {
  await checklistApt(
    aptInfo.value.aptId,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        checklistAptId.value = response.data.checklistAptId
      }
    },
    (error) => {
      checklistAptId.value = null
      console.error(error)
    }
  )
}

// 체크하기
const doChangeItemChecked = async (itemId) => {
  await changeItemChecked(
    itemId,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        doChecklistAptView()
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

// 해당 아파트에 적용된 체크리스트 여부 확인
// 있으면 checklistAptId 값 할당
onMounted(() => {
  doChecklistApt()
})

// checklistAptId 값이 바뀌면 매치되는 체크리스트 가져오기
watch(checklistAptId, (newValue) => {
  if (newValue !== null) {
    doChecklistAptView()
  }
})

watch(
  () => aptInfoStore.aptInfo,
  () => {
    doChecklistApt()
  }
)
</script>

<template>
  <div class="w-[35vw] mx-auto  bg-white rounded-lg shadow-md p-6 dark:bg-gray-950">
    <h2 class="text-2xl font-semibold mb-4">My Checklist</h2>
    <div v-if="checklistAptId === null">
      <button @click="doRegistChecklistApt">체크리스트 생성</button>
    </div>
    <div v-else class="space-y-3">
      <button @click="doDeletechecklistApt">삭제</button>
      <div v-for="item in checklistAptItems" :key="item.itemId" class="flex items-center space-x-3">
        <div class="relative flex cursor-pointer items-center rounded-full p-3">
          <input
            :id="'task' + item.itemId"
            class="before:content[''] peer relative h-5 w-5 cursor-pointer appearance-none rounded-md border border-blue-gray-200 transition-all before:absolute before:top-2/4 before:left-2/4 before:block before:h-12 before:w-12 before:-translate-y-2/4 before:-translate-x-2/4 before:rounded-full before:bg-blue-gray-500 before:opacity-0 before:transition-opacity checked:bg-blue-300 checked:bg-blue-300 checked:before:bg-blue-300 hover:before:opacity-10"
            type="checkbox"
            :checked="item.checked"
            @change="doChangeItemChecked(item.itemId)"
          />
          <label
            :for="'task' + item.itemId"
            class="pointer-events-none absolute top-2/4 left-2/4 -translate-y-2/4 -translate-x-2/4 text-white opacity-0 transition-opacity peer-checked:opacity-100"
          >
            <svg
              v-if="item.checked"
              xmlns="http://www.w3.org/2000/svg"
              class="h-3.5 w-3.5"
              viewBox="0 0 20 20"
              fill="currentColor"
              stroke="currentColor"
              stroke-width="1"
            >
              <path
                fill-rule="evenodd"
                d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                clip-rule="evenodd"
              ></path>
            </svg>
          </label>
        </div>
        <label
          :class="{
            'text-gray-700 dark:text-gray-400': !item.checked,
            'line-through': item.checked
          }"
        >
          {{ item.item }}
        </label>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
</style>
