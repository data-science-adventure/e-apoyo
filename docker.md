# Login into Docker hub

docker login

# Create docker image

npm run java:docker

# rename default image label

docker tag eapoyo danimaniarqsoft/eapoyo:1.0

# Push image to docker hub

docker push danimaniarqsoft/eapoyo:1.0

# Running the project

docker compose -f src/main/docker/app.yml up
