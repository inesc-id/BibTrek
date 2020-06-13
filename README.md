# BibTrek: A Graph Visualization Tool for Cybersecurity Research Publications

Hello! In this repository we present **BibTrek**, a graph visualization tool for
Cybersecurity research publications. Currently our application is just a prototype. However we plan on expanding it and implementing more functionalities that we will briefly describe below.

## Goal
With the increasing number of cyberattacks in the last few years it is becoming harder to find white-hat hackers capable of efectively countering the latest attacks. The cutting edge techniques used for carrying these attacks are mostly found on scientific research papers, authored by other researchers. Because of this, we have to navigate through the references of publications to find details on previous attacks and identify the most relevant paper for certain cybersecurity topic. Most of the time, the most relevant paper is the one with the most references to it.

With **BibTrek** we created a simple console program that fetches publications from various web-based digital libraries and displays the information on a [Neo4J graph database](https://neo4j.com/). Furthermore it relates information about the papers and its references, authors, the institutions involved and keywords, allowing us to easily navigate through the graph and finding the most appropriate publication for our research. This way we can easily learn more about the latest cyberattacks and, hopefully, twarth them effectively.

## Sections

This repository is divided into four main sections, as described below:

- [api/](https://github.com/inesc-id/BibTrek/tree/master/api)

List of available web-based digital libraries for scientific research papers and the available APIs used to extract information from them.

- [app/](https://github.com/inesc-id/BibTrek/tree/master/app)

BibTrek prototype. We used Java to develop this application and Apache Maven to build it. We use multiple other repositories including the Apache Maven JSON and Neo4J repositories.

- [article/](https://github.com/inesc-id/BibTrek/tree/master/article)

Technical report written about the BibTrek prototype. In it we introduce our work on this project and comment on its current functionalities and functionalities to develop in the future.

- [db/](https://github.com/inesc-id/BibTrek/tree/master/db)

This is a demo application that uses a previously created user generated database that gives us a brief example in how this application would interact with the Neo4J dataset. There are some aspects of this demo that be upgraded in the future, to provide a better understanding of the program to the users.

## Further Considerations

This project was developed by José Brás with the guidance of [Miguel Pardal](http://web.tecnico.ulisboa.pt/miguel.pardal/) during the developer's summer internship in association with [INESC-ID: Instituto de Engenharia de Sistemas e Computadores, Investigação e Desenvolvimento em Lisboa](https://www.inesc-id.pt/). This project was developed during the course of approximately 3 weeks. Needless is to say, that there is lots of work to do and further aspects of this program to develop in the future.

### Contact

For inquiries about the tool, please contact [Miguel Pardal](http://web.tecnico.ulisboa.pt/miguel.pardal/).

### Links to similar tools

- [Connected Papers](https://www.connectedpapers.com/) (added on 2020-06-13)
