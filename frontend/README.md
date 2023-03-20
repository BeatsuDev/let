# let

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

## Type Support for `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin) to make the TypeScript language service aware of `.vue` types.

If the standalone TypeScript plugin doesn't feel fast enough to you, Volar has also implemented a [Take Over Mode](https://github.com/johnsoncodehk/volar/discussions/471#discussioncomment-1361669) that is more performant. You can enable it by the following steps:

1. Disable the built-in TypeScript Extension
    1) Run `Extensions: Show Built-in Extensions` from VSCode's command palette
    2) Find `TypeScript and JavaScript Language Features`, right click and select `Disable (Workspace)`
2. Reload the VSCode window by running `Developer: Reload Window` from the command palette.

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Type-Check, Compile and Minify for Production

```sh
npm run build
```

### Run Unit Tests with [Vitest](https://vitest.dev/)

```sh
npm run test:unit
```

### Run End-to-End Tests with [Cypress](https://www.cypress.io/)

```sh
npm run test:e2e:dev
```

This runs the end-to-end tests against the Vite development server.
It is much faster than the production build.

But it's still recommended to test the production build with `test:e2e` before deploying (e.g. in CI environments):

```sh
npm run build
npm run test:e2e
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```

### Pre-commit hooks 
Run format and lint before commit


```shell
#!/bin/bash

# Change work-dir to git root
pushd $(git rev-parse --show-toplevel) > /dev/null
FILES=$(git diff --cached --name-only --diff-filter=ACM | grep "^frontend/")

### Frontend formatter and linter ###
if [ -n "$FILES" ]; then
    cd frontend
    npm run lint
    npm run formatter
    cd ..
    git add $FILES
fi

### Backend formatter ###

# Get the list of modified files
FILES=$(git diff --cached --name-only --diff-filter=ACM | grep "^backend/")

# Run Spotless only if there are modified files in the backend folder
if [ -n "$FILES" ]; then
    cd backend
  mvn spotless:apply --quiet
    cd ..
  git add $FILES
fi

# Reset work-dir
popd > /dev/null
``` 

### Using geolocations with api-ninjas 

1. Create an account on [api-ninjas](https://apilist.fun/api/ninjas-geolocation-api)
2. Create a .env.local file in the root of the project, by copying the .env.local.example file and fill in your api key

