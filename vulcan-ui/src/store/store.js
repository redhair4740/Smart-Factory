import { useAuthStore } from '@/store/module/authStore.js';
import { useMainStore } from '@/store/module/mainStore.js';

export const stores = {
    auth: useAuthStore(),
    main: useMainStore(),
};
