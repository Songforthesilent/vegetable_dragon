<template>
  <div v-if="show" class="modal">
    <p>{{ title }}</p>
    <slot></slot>
    <div class="modal-buttons">
      <slot name="buttons">
        <button class="confirm-btn" @click="$emit('confirm')">확인</button>
        <button class="cancel-btn" @click="$emit('cancel')">취소</button>
      </slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    show: {
      type: Boolean,
      required: true
    },
    title: {
      type: String,
      default: ''
    }
  }
};
</script>

<style scoped>
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.2);
  width: 350px;
  text-align: center;
  z-index: 1000;
}

.modal p {
  font-size: 16px;
  color: #444;
  margin-bottom: 15px;
}

.modal-buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 15px;
}

.confirm-btn {
  width: 100px;
  padding: 10px;
  background-color: #3662E3;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.confirm-btn:hover {
  background-color: #2d3c89;
}

.cancel-btn {
  width: 100px;
  padding: 10px;
  background-color: #eee;
  color: #444;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.cancel-btn:hover {
  background-color: #ddd;
}
</style>
