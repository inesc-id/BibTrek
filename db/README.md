# Bibliography database

This is the setup and demonstration guide for the graph database Neo4J used for bibliographic data.

It includes a database schema for publications and example queries and statements.

It also includes a client Java example application.

# Downloading and Installing Neo4J

In order to install Neo4J please access the following link: "https://neo4j.com/download/".

After you have completed your registration you will be able to download it and Neo4J's website will instruct you in installing the framework according to your Operating System.

# Files

In this folder we have a simple but rich ".nosql" example file which will allow you to set up the Neo4J Graph Database for our BibTrek application.

To do so follow these instructions:

(1): Run the Neo4J application you have installed previously
(2): Create a graph. If you have already done it, skip this step
  (a) In the starting menu press the "Add Button" button
  (b) Then choose "Create Local Graph"
  (c) Give it some name and password and create it
  (d) Press start on the database you have just created
(3): Open the Neo4J browser window on the same menu
(4): Open the "example.nosql" file with your favorite text editor.
(5): Copy the entirety of the file to the search bar on the Neo4J browser app

You have done it. You can now visualize the graph and query it freely

# Schema 

In this graph database we adhere to the following schema:

Our main node types are:
 (1) Paper: with attributes "year" and "library" for the year in which it was published and the library in which it was published in 
 (2) Author: with attributes "fname" and "surname" for its first and last name respectively
 (3) Subject: the subject of the paper. It's only attribute is its own subject name. The attribute's field name is "subject".
 (4) Institution: either an university, school or institute, or a company. It's only attribute is the institution's name. The attribute's field name is "iname".

Our main relation types are:
 (1) FOCUS_OF: Unidirectional relation between a paper and subject. A subject is the focus of a paper
 (2) ASSOCIATED: Unidirectional relation between an author and an institution. An author is/was associated with an institution during a certain year. The year in which they were associated constitutes an attribute for this edge
 (3) WROTE: Unidirectional relation between an author and a paper. An author wrote a certain paper whilst being part of an institution during a certain year
 (4) REFERENCES: Last but not least, a paper might reference another paper. This might either be an unidirectional or bidirectional relationship (whenever two papers reference each other).

# Examples queries

To query the system, after you've created the graph database, insert any of the queries below on the Neo4J Browser's search bar.

Let's start with some simple queries to the database:

(1) Get all the nodes and edges of the graph: "MATCH (n) RETURN n"
(2) Get the name of an author and the papers that the author has written. Let's exemplify this with Adi Shamir, one of the creators of the RSA cryptosystem: "MATCH (author:Author {fname:"Adi", surname:"Shamir"})-[:WROTE]->(papers) RETURN author, papers"

Finally to delete all of the graph database's nodes and edges use the following query: "MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n, r"
