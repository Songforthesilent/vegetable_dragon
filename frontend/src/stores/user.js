import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        username: '',
    }),
    actions: {
        setUsername(name) {
            this.username = name;
        },
        clearUsername() {
            this.username = '';
        },
    },
});
