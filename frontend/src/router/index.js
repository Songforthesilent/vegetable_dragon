import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BestTopics from '@/views/board/BestTopics.vue';
import BoardList from '@/views/board/BoardList.vue'
import BoardWrite from "@/views/board/BoardWrite.vue";
<<<<<<< HEAD
import BoardDetail from '@/views/board/BoardDetail.vue';
import UserRegister from '@/views/UserRegister.vue';
import UserLogin from '@/views/UserLogin.vue'
=======
import BoardDetail from '@/views/board/BoardDetail.vue'
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

const routes = [
    {
        path: '/',
        name: 'PageHome',
        component: PageHome,
        meta: { tile: 'PageHome' }
    },
    {
        path: '/about',
        name: 'PageAbout',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/PageAbout.vue'),
        meta: { title: 'About' }
    },
    {
        path: '/board/list',
        name: 'BoardList',
        component: BoardList,
<<<<<<< HEAD
        meta: { title: '게시판', content: '다양한 주제의 게시글을 확인하세요'}
=======
        meta: { title: '게시판' }
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    },
    {
        path: '/board/write',
        name: 'BoardWrite',
        component: BoardWrite,
<<<<<<< HEAD
        meta: { title: '게시글 작성', content: '' }
=======
        meta: { title: '게시글 작성' }
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    },
    {
        path: '/best-topics',
        name: 'BestTopics',
        component: BestTopics,
<<<<<<< HEAD
        meta: { title: 'Best Topics', content: '가장 인기 있는 게시글을 확인하세요'}
=======
        meta: { title: 'BestTopics' }
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    },
    {
        path: '/board/view/:id',
        name: 'BoardDetail',
        component: BoardDetail,
        meta: { title: ' '}
<<<<<<< HEAD
    },
    {
        path: '/register',
        name: 'UserRegister',
        component: UserRegister,
        meta: { title: '' }
    },
    {
        path: '/login',
        name: 'UserLogin',
        component: UserLogin,
        meta: { title: '로그인하기' }
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    }

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

<<<<<<< HEAD
export default router
=======
export default router
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
