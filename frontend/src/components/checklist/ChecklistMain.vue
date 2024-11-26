<script setup>
import { ref, onMounted } from 'vue'
import { checklistView, registItem, deleteItem, findMyChecklist } from '@/api/checklist'
import { httpStatusCode } from '@/util/http-status'
import { searchDetailByCode } from '@/api/apt'
import { useAptInfoStore } from '@/stores/aptInfo'
import { useDetailInfoStore } from '@/stores/detailInfo'
import ChecklistApt from './ChecklistApt.vue'

const checklistId = ref(null)
const items = ref([])
const item = ref('')
const aptList = ref([])

const aptInfoStore = useAptInfoStore()
const detailInfoStore = useDetailInfoStore()

const showDetail = ref(false)
const selectedApt = ref(null)

const getAptById = (aptId) => {
  return aptList.value.find((apt) => apt.aptId === aptId)
}

const openDetailInfo = (aptId) => {
  searchDetailByCode(
    aptId,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        selectedApt.value = response.data
        showDetail.value = true
        const aptInfo = getAptById(aptId)
        aptInfoStore.setAptInfo(aptInfo)
        detailInfoStore.setDetailInfo(response.data)
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

const closeDetailInfo = () => {
  showDetail.value = false
  selectedApt.value = null
}

const getChecklist = async () => {
  await checklistView(
    (response) => {
      checklistId.value = response.data.checklistId
      if (response.data.items !== null) {
        items.value = response.data.items
      }
    },
    (error) => {
      console.log(error)
    }
  )
}

const doRegistItem = async () => {
  if (item.value.length > 0) {
    await registItem(
      {
        checklistId: checklistId.value,
        item: item.value
      },
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          item.value = ''
          getChecklist()
        }
      },
      (error) => {
        console.error(error)
      }
    )
  }
}

const doDeleteItem = async (itemId) => {
  await deleteItem(
    itemId,
    (response) => {
      if (response.status === httpStatusCode.NOCONTENT) {
        getChecklist()
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

const doFindMyChecklist = async () => {
  await findMyChecklist(
    (response) => {
      if (response.status === httpStatusCode.OK) {
        aptList.value = response.data
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

const deleteChecklistHandler = (aptId) => {
  aptList.value = aptList.value.filter((apt) => apt.aptId !== aptId)
  closeDetailInfo();
}

onMounted(() => {
  getChecklist()
  doFindMyChecklist()
})
</script>

<template>
  <div class="container mx-auto mt-4">
    <div class="md:flex md:justify-center">
      <div class="md:w-3/4">
        <div class="bg-white shadow-md rounded-md">
          <div class="bg-gray-200 px-4 py-2 rounded-t-md">Home Check</div>
          <div class="p-4">
            <div class="grid grid-cols-2 gap-4">
              <div class="max-h-[600px] overflow-auto">
                <!-- 체크리스트 항목 -->
                <h1 class="text-3xl font-medium  text-center ">✔️ CheckList</h1>
                <p class="mt-3 text-slate-500  text-center ">꼼꼼한 집 선택을 위해 체크리스트를 활용해보세요!</p>
                <div class="items-center mt-3 mb-3 ml-3">
                  <input type="text" class="border border-gray-300 rounded-md py-2 px-4 w-3/4 mr-2 focus:outline-none focus:ring-2 focus:ring-blue-500" v-model="item" @keypress.enter="doRegistItem" placeholder="체크리스트 항목 추가..." />
                  <button @click="doRegistItem" class="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">추가</button>
                </div>
                <div class="max-h-96 overflow-auto">
                  <div v-for="item in items" :key="item.itemId">
                    <div id="task" class="flex justify-between items-center border-b border-slate-200 py-3 px-2 border-l-4 border-l-transparent">
                      <div class="inline-flex items-center space-x-2">
                        <div>
                          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 text-slate-500">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 12.75l6 6 9-13.5" />
                          </svg>
                        </div>
                        <div class="text-slate-500">{{ item.item }}</div>
                      </div>
                      <div @click="doDeleteItem(item.itemId)">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-4 text-slate-500 hover:text-slate-700 hover:cursor-pointer">
                          <path stroke-linecap="round" stroke-linejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                        </svg>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- 집 리스트 -->
              <div class="list-group max-h-[600px] overflow-auto">
                <h1 class="text-3xl font-medium mb-4 text-center">작성 목록</h1>
                <a v-for="apt in aptList" :key="apt.aptId" href="#" class="list-group-item hover:bg-gray-100" @click.prevent="openDetailInfo(apt.aptId)">
                  <div :id="apt.aptId">{{ apt.aptName }}</div>
                  <div><small>도로명주소 : {{ apt.roadAddress }}</small></div>
                </a>
                <a v-if="aptList.length === 0" href="#" class="list-group-item list-group-item-action disabled">체크리스트 적용 아파트가 없습니다.</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50" v-if="showDetail" @click="closeDetailInfo">
    <div class="modal-content  w-[35vw] mx-auto " @click.stop>
      <div class="modal-header px-4 py-2">
        <h5 class="modal-title">{{ selectedApt.aptName }}</h5>
      </div>
      <div class="modal-body px-4 py-2" >
        <ChecklistApt @deleteChecklistApt="deleteChecklistHandler"/>
      </div>
      <div class="modal-footer"></div>
    </div>
  </div>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';


</style>
