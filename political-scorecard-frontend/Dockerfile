# Use an official Node.js runtime as a parent image
FROM node:21-alpine

LABEL authors="Dustin Etts - dustinetts@gmail.com"
# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json (if available)
COPY package.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code to the working directory
COPY . .

# Start the application
CMD ["npm", "run", "start"]