<script setup lang="ts"></script>

<template>
  <div class="wrapper">
    <h1>Admin Panel</h1>
    <div class="container">
      <div class="left-container">
        <div class="form-container">
          <h2>Lag Ny Kategori</h2>
          <form>
            <div class="form-group">
              <input class="input-text" type="text" id="name" placeholder="Elektronikk" />
            </div>
            <div class="form-group">
              <button class="button button-black button-screaming" type="submit">
                Lag Kategori
              </button>
            </div>
          </form>
        </div>
      </div>

      <div class="right-container">
        <h2>Kategorier</h2>
        <div class="category-container">
          <div class="category">
            <div class="category-name">Elektronikk</div>
            <div class="category-actions">
              <button id="delete-category" class="button button-screaming">Slett</button>
            </div>
          </div>
          <div class="category">
            <div class="category-name">Elektronikk</div>
            <div class="category-actions">
              <button id="delete-category" class="button button-screaming">Slett</button>
            </div>
          </div>
          <div class="category">
            <div class="category-name">Elektronikk</div>
            <div class="category-actions">
              <button id="delete-category" class="button button-screaming">Slett</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
h1 {
  text-align: center;
  margin-bottom: 2rem;
}

.wrapper {
  margin: 0.5rem auto;
  max-width: 1200px;
  padding: 0.5rem 5rem;
}

.container {
  display: flex;
  flex-direction: row;
}

.left-container,
.right-container {
  flex: 1;
  padding: 1rem;
}

form button {
  margin-top: 0.4rem;
}

form input {
  width: 100%;
  margin-top: 1rem;
}

.category-container {
  margin-top: 1rem;
  border: 1px solid black;
  border-radius: 0.5rem;
  height: 300px;
}

.category {
  display: flex;
  padding: 0.4rem 1rem;
}

.category .category-name {
  flex: 1;
  font-size: 1.1rem;
}

.category #delete-category {
  transition-duration: 120ms;
}

.category #delete-category:hover {
  background-color: red;
  color: white;
  border-radius: 0;
}

@media (max-width: 900px) {
  .container {
    flex-direction: column;
  }

  .left-container,
  .right-container {
    padding: 0;
  }

  .right-container {
    margin-top: 3rem;
  }
}
</style>
