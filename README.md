# BibTrek: A Graph Visualization Tool for Cybersecurity Research Publications

Hello! In this repository we present "BibTrek: A Graph Visualization Tool for
Cybersecurity Research Publications". Currently our application is just a prototype. However we plan on expanding it and implementing more functionalities that we will briefly describe below.

# Goal
We the increasing number of cyberattacks in the last few years it is becoming harder to find white-hat hackers capable of efectively countering the latest attacks.

The cutting edge techniques used for carrying these attacks are mostly found on scientific research papers. However the necessary knowledge to carry the most modern exploits is sometimes based on the work done by other authors. Because of this, we have to navigate through the references of publications in order for us to find details on previous attacks and identifying the most relevant paper for certain cybersecurity topic. Most of the time, the most relevant paper is the one with the most references to it.

With BibTrek we propose on creating a simple command line console program that fetches publications from various web based libraries and displays its information on a Neo4J graph database. Furthermore it relates information about the papers and its references, authors, the institutions involved and keywords, allowing us to easily navigate through the graph and finding the most appropriate publication for our research. This way we can easily learn more about the latest cyberattacks and twarth efectively.

# Sections

This repository is divided into 4 main sections. These sections are described below

- api/ <https://github.com/inesc-id/BibTrek/tree/master/api>

Here we list the available web based libraries for scientific research papers and elaborate on the available APIs used to extract information from them.

- app/ <https://github.com/inesc-id/BibTrek/tree/master/app>

In this folder we have the core of our prototype. We used Java to develop this application and Apache Maven in order to build it. We use multiple other repositories including the Apache Maven JSON and Neo4J repositories.

- article/ <https://github.com/inesc-id/BibTrek/tree/master/article>

Here we present a scientific research paper written about the BibTrek prototype. Here we introduce our work on this project and comment on its current functionalities and how they fare and further functionalities to develop in the future.

- db/ <https://github.com/inesc-id/BibTrek/tree/master/db>

This is a demo application that uses a previously created user generated database that gives us a brief example in how this application would interact with the Neo4J dataset. There are some aspects of this demo that be upgraded in the future, to provide a better understanding of the program to the users.

# Further Considerations

This project was developed by José Brás with the guidance of Professor Miguel Pardal during the developer's summer internship in association with "INESC-ID: Instituto de Engenharia de Sistemas e Computadores, Investigação e Desenvolvimento em Lisboa". This project was developed during the course of approximately 3 weeks. Needless is to say, that there is lots of work to do and further aspects of this program to develop in the future.



