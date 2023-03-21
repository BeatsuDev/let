<script setup lang="ts">

</script>

<template>
<form>
      <div class="row" id="row-1">
        <div class="col">
          <h3><label for="title">Tittel</label></h3>
          <input class="input-text" type="text" id="title" placeholder="Rød rose - snart døende" />
        </div>
        <div class="col">
          <h3><label for="price">Pris (kr)</label></h3>
          <input class="input-text" type="text" id="price" placeholder="249.99" />
        </div>
      </div>

      <div class="row" id="row-2">
        <div class="col">
          <h3><label for="place">Sted</label></h3>
          <input class="input-text" type="text" id="place" placeholder="Kardemomme By, Norge" />
        </div>
        <div class="col">
          <h3><label for="category">Kategori</label></h3>
          <input class="input-text" type="text" id="category" placeholder="Planter" />
        </div>
      </div>

      <div class="row" id="row-3">
        <div class="col">
          <h3><label for="summary">Kort beskrivelse</label></h3>
          <textarea class="input-text" name="summary" id="summary"></textarea>
        </div>
      </div>

      <div class="row" id="row-4">
        <div class="col">
          <h3><label for="description">Detaljert beskrivelse</label></h3>
          <textarea class="input-text" name="description" id="description"></textarea>
        </div>
      </div>

      <div class="row" id="row-5">
        <div class="col">
          <h3><label for="images">Last opp bilder</label></h3>
          <input class="input-text" type="file" id="images" multiple />
        </div>
      </div>

      <div class="row" id="row-6">
        <div class="col">
          <button class="button button-black button-screaming" type="submit">
            Publiser annonse
          </button>
        </div>
      </div>
    </form>
</template>

<style scoped>
label {
  display: block;
  margin-bottom: 0.2rem;
}

input,
textarea {
  width: 100%;
  resize: none;
}

.col {
  padding: 0 0.4rem;
}

.row {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  margin-bottom: 1rem;
}

.row > .col {
  grid-column: 1 / 6;
}

#row-1 > .col:first-child {
  grid-column: 1 / 5;
}

#row-1 > .col:last-child {
  grid-column: 5 / 5;
}

#row-2 > .col:first-child {
  grid-column: 1 / 4;
}

#row-2 > .col:last-child {
  grid-column: 4 / 6;
}

#row-6 > .col:first-child {
  grid-column: 6 / 6;
}

#description {
  height: 200px;
}

#images {
  cursor: pointer;
}
</style>