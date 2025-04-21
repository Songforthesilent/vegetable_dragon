<template>
  <div class="vote-section">
    <button @click="vote('agree')">찬성 👍 ({{ votes.agree }})</button>
    <button @click="vote('disagree')">반대 👎 ({{ votes.disagree }})</button>
  </div>
</template>

<script>
export default {
  props: {
    postId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      votes: {
        agree: 0,
        disagree: 0
      }
    }
  },
  methods: {
    vote(type) {
      fetch(`http://localhost:8081/board/vote/${this.postId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ voteType: type })
      })
          .then((res) => res.json())
          .then((data) => {
            this.votes = data;
          })
          .catch((err) => console.error(err));
    },
    fetchVotes() {
      fetch(`http://localhost:8081/board/vote/${this.postId}`)
          .then((res) => res.json())
          .then((data) => {
            this.votes = data;
          })
          .catch((err) => console.error(err));
    }
  },
  mounted() {
    this.fetchVotes();
  }
}
</script>

<style scoped>
.vote-section {
  margin: 20px 0;
}
button {
  margin: 5px;
  padding: 10px 20px;
  border-radius: 8px;
  background-color: #3A4CA4;
  color: white;
  border: none;
  cursor: pointer;
}
</style>
