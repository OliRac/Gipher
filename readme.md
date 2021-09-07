# Gipher

The aim of the project is to build a platform which proposes some contents that a user can consume to express his feelings or his mood in a funnier and more expressive way. ​


## Objectives:​

Create a web application that lets users ​

- register to it​
- view gifs​
- mark gifs as their favorites​
- search for gifs​
- receive gif recommendations​

## Technologies

- VCS : Gitlab​
- Middleware : Spring Boot​
- Frond end : Angular​
- Data Store : MongoDB, MySQL​
- Testing : Karma, Jasmine, JUnit, Mockito​
- Containerization: Docker

## Installation

1. Right-click on build_images file in the root directory and choose run with PowerShell
wait until all images are built.
when you see Success! Press any key to continue ... 
means you are done here!

2. Run the command line on the root directory of the project and run: `docker-compose up`


3. Open your internet browser and enter: `http://localhost:/4200`


## Current Issues

- Recommendations will not populate on first login
- Recommendations will not get cleared when updating, causing the page to be eventually flooded with gifs.
- Scroll bar tends to disappear
