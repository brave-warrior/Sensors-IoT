#Intro
[Particle Photon](https://www.particle.io/) is cloud based microcontroller. It means that sketch compilation and flashing are executed in cloud.

#How to use
There is a good official [manual](https://docs.particle.io/guide/getting-started/intro/photon/). In order to use this project it's recommended to read at least [Getting Started](https://docs.particle.io/guide/getting-started/start/photon/) and [Flash Apps with Particle Build](https://docs.particle.io/guide/getting-started/build/photon/) chapters.

#How to build
Go to the file `sensor.ino` and change the string `serverUrl`. It should point to hostname of your server without schema (e.g. `myhome.come`, `134.23.34.002` etc.).

Then go Particle online [compiler](https://build.particle.io/build), copy the content of the file `sensor.ino`, assemble and flash the solution on your Particle Photon microcontroller.
