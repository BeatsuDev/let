# Let.no - e-commerce marketplace

Let.no is a website created for the Full-stack application development subject's semester project at NTNU.
Let.no allows users to simply buy and sell items. The website is built with Vue 3, Vite, and the backend is built with
Spring Boot.

## Developer section 

### Before you start

The frontend is formatted with [Prettier](https://prettier.io/), and linted with [ESLint](https://eslint.org/), and the
backend is formatted with
Spotless. To ensure that your code is formatted correctly, you should ensure to format your code before committing. To
do this easily, you can add this
hook inm your `.git/hooks/pre-commit` file:

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

### Contributing

To contribute to the project, you should first fork the repository, and then clone it to your local machine. You should
then create a new branch for your feature, and when you are done, you should create a pull request to the main
repository. 
