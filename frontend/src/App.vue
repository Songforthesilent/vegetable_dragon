<template>
  <PageHeader/> <!-- 헤더 컴포넌트 -->
  <router-view/>  <!-- 페이지 이동이 표시될 곳 -->
  <PageFooter/> <!-- 푸터 컴포넌트 -->
</template>

<script>
import PageHeader from '@/components/PageHeader'
import PageFooter from '@/components/PageFooter'
import { useUserStore } from '@/stores/user'
import { getSessionUser } from "@/api/auth";

export default {
  name: 'App',
  components: {
    PageFooter,
    PageHeader,
  },
  async mounted() {
    try {
      const res = await getSessionUser(); // 세션 확인
      const store = useUserStore();
      store.setUsername(res.data); // 세션에 저장된 사용자 이름 저장
    } catch (error) {
      console.log('세션 없음 또는 만료됨');
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>