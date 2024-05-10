import { defineStore } from 'pinia';

export const useMainStore = defineStore('main', {
    state: () => ({
        someData: [],
        anotherValue: '',
    }),
    getters: {
        someData: () => state => state.someData,
        anotherValue: () => state => state.anotherValue,
    },
    actions: {
        fetchData() {
            // 假设fetchData是异步API调用
            const data = fetchData();
            this.setSomeData(data);
        },
        setSomeData(data) {
            this.$state.someData = data;
        },
        setAnotherValue(value) {
            this.$state.anotherValue = value;
        },
    },
});
