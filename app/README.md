# BibTrek application

This is prototype for a bibliography exploration tool using graphs datastores and visualization.

# Build

- This application was developed using the Java Programming language.
- The Java version used was 1.8.0
- For building the application used Apache Maven
- It also requires the use of the JSON and Neo4J Drivers maven repositories
- We use the "exec-maven-plugin" by codehaus to run the program on its install phase

# Packages

Here we present a brief description of the main packages of the Java application:

- **dblp**: Here we have packages and classes that interact with the DBLP database. Sub packages include
  - **exceptions**: exceptions related to the package and its sub packages
  - **parsing**: classes that parse the information fetched from the dblp database
  - **search**: classes that search for either authors and/or publications
  - **writer**: classes that write the retrieved information into a file with information to be added to the Neo4J database
- **exceptions**: exceptions of the whole application
- **http**: package with HTTP request and response functions
- **main**: package with the main program classes
- **neo4j**: package with classes that interact with the Neo4J database
- **nosql**: package with the Neo4J Cypher queries files that will be used to add information to the Neo4J database. It is currently subdivided into:
  - **dblp**: ".nosql" files retrieved from the DBLP database
    - **processed**: ".nosql" files that were already processed and added to the Neo4J graph database
- **sanitizer**: package with classes that sanitize queries
- **utils**: package with several utility classes and functions that are used throughout the application

# Querying DBLP

There are three types of queries done to the DBLP. 

- **Querying by author**: The first one we insert the name of the author and the application queries the database using the following URL: <https://dblp.org/search/author/api?q=miguel+pardal&format=json>. The results are then displayed on the console.

- **Querying by publication**: We insert the name of a publication and we query the database using the following URL: <https://dblp.org/search/publ/api?q=spectre&format=json>

- **Querying by author publications**: We insert the name of the author in the console terminal. The application uses the following URL to query DBLP: <https://dblp.org/search/author/api?q=miguel+pardal&format=json>. The available authors are displayed in the terminal. We pick one of the displayed authors and the following query is done regarding their publications: <https://dblp.org/search/publ/api?q=miguel+pardal&format=json>. These results are displayed in JSON. However, this might get publications from wrong authors. We are considering in the future switching this particular query to an XML using the author's ID on DBLP because, using the type of URL query displayed below, we can get the publications of an author from his particular profile instead of using the DBLP search function. The author ID is available, after using the DBLP website search for an author, under the share icon section, example: <https://dblp.org/pid/77/8922>. Unfortunately, this way of querying is not available in JSON therefore we are required to use XML. Here is the query mentioned above using XML and the PID: <https://dblp.org/pid/77/8922.xml>.

# Future Work

Like we have previously mentioned in the article written in <https://github.com/inesc-id/BibTrek/tree/master/article> there is lots of functionalities to be added in a near future. This functionalities include:

- Fetching data from more than one database, this includes:
  - ArXiV
  - IEEE Xplore

- Dynamically add attributes fetched from the DBLP database, since we adhere to a fixed node attribute structure

- Add more types of nodes to the Neo4J database apart from the Authors and Publications nodes being currently added

- Infer better relations between the nodes being added to the database. Current relations only include the "written by" relation between a user and a publication node.

To read more about the future work to be done in this article please refer to the article written by the developer linked above.

# Running the application

To run the application simply use the command below in the app/ directory:
    
    mvn clean
    mvn compile
    mvn install 





