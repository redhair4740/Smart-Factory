import { useAuthStore } from '@/store/authStore.js';
import { useMainStore } from '@/store/mainStore.js';

export const stores = {
    auth: useAuthStore(),
    main: useMainStore(),
};
