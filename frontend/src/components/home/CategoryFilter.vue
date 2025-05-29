<template>
  <div class="category-filter">
    <button
        v-for="category in categories"
        :key="category"
        :class="['category-button', { active: selectedCategory === category }]"
        @click="selectCategory(category)"
    >
      {{ category }}
    </button>
  </div>
</template>

<script>
export default {
  name: 'CategoryFilter',
  props: {
    categories: {
      type: Array,
      required: true
    },
    selectedCategory: {
      type: String,
      required: true
    }
  },
  methods: {
    selectCategory(category) {
      this.$emit('category-selected', category);
    }
  }
}
</script>

<style scoped>
.category-filter {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin: 30px 0 20px 0;
}

.category-button {
  padding: 8px 16px;
  background-color: #f1f5f9;
  color: #475569;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.category-button:hover {
  background-color: #e2e8f0;
  color: #1e293b;
}

.category-button.active {
  background-color: #3662E3;
  color: white;
}

@media (max-width: 768px) {
  .category-filter {
    overflow-x: auto;
    padding-bottom: 10px;
    flex-wrap: nowrap;
  }

  .category-button {
    white-space: nowrap;
  }
}
</style>