<template>
  <div class="flex h-screen">
    <!-- 왼쪽 화면 -->
    <div class="flex-1 p-4">
      <slot name="left"></slot>
    </div>

    <!-- 오른쪽 화면 -->
    <div class="w-[257px] bg-gray-100 p-4">
      <slot name="right"></slot>
    </div>
  </div>
</template>
