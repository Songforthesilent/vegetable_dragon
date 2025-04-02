import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BestTopics from '@/views/board/BestTopics.vue';
import BoardList from '@/views/board/BoardList.vue'
import BoardWrite from "@/views/board/BoardWrite.vue";
import BoardDetail from '@/views/board/BoardDetail.vue';
import UserRegister from '@/views/UserRegister.vue';

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
        meta: { title: '게시판' }
    },
    {
        path: '/board/write',
        name: 'BoardWrite',
        component: BoardWrite,
        meta: { title: '게시글 작성' }
    },
    {
        path: '/best-topics',
        name: 'BestTopics',
        component: BestTopics,
        meta: { title: 'BestTopics' }
    },
    {
        path: '/board/view/:id',
        name: 'BoardDetail',
        component: BoardDetail,
        meta: { title: ' '}
    },
    {
        path: '/register',
        name: 'UserRegister',
        component: UserRegister,
        meta: { title: 'UserRegiser' }
    }

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
