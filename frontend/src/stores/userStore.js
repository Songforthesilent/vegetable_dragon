import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        username: null,
    }),
    getters: {
        isLoggedIn: (state) => !!state.username,
    },
    actions: {
        setUsername(name) {
            this.username = name;
        },
        clearUser() {
            this.username = null;
        }
    }
});
