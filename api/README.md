# Bibliography Application Programmer Interfaces

This is a list of existing and publicly accessible APIs for bibliography databases.

# ACM Digital Library

The ACM Digital Library can be found here: <https://dl.acm.org/>

Unfortunately ACM Digital Library does not offer any API to the developers.
Not only that but the use of crawlers is forbidden as it is written in the Terms of Service. You can find them in the following link: <https://libraries.acm.org/digital-library/policies>
The ToS reads:

    Under no circumstances are the following actions permitted:

    Sharing access to ACM DL materials with individuals or organizations outside the subscribing institution
    Using scripts or spiders to automatically download articles or harvest metadata from the ACM Digital Library. This activity is a serious violation of  ACM’s DL usage policy and will result in the temporary or permanent termination of download rights for the subscribing institution.

Due to this setback we will not be able to use the ACM Digital Library as a source of data to our BibTrek application.

# IEEE Xplore

IEEE Xplore can be found in the following link: <https://ieeexplore.ieee.org/Xplore/home.jsp>

Xplore offers a very rich API to its developers. Firstly it requires us to obtain a key for us to be able to use it. First you have to register yourself in the IEEE Xplore website in the following link: <https://developer.ieee.org/member/register>. After registering you will be able to apply for a key. 

The documentation for the API is displayed here: <https://developer.ieee.org/docs>.

Besides this features we are also offered a vast array of SDKs. These SDKs include the:
  - Java SDK: <https://developer.ieee.org/Java_Software_Development_Kit>
  - PHP SDK: <https://developer.ieee.org/PHP_Software_Development_Kit>
  - Python SDK: <https://developer.ieee.org/Python_Software_Development_Kit>

As we can see, for example, in the Java SDK, we are able to do a vast array o queries with the database attributes in mind. Such examples of queries include the following Java routines:

  - findByAbstractText()
  - findByArticleTitle()
  - findByAuthorText()

These subroutines are very handy because they allow us to find an article using the abstract, its title and its author as keys.
Besides this we can also use sort and filter methods like:
  
  - setMaxRecords(): Sets the maximum size of the result set returned
  - setResultsFilter(): Reduces the result set based on the parameter matching the value passed
  - setSortByField(): Sets the field used for sorting

Finally we can also format our API responses in different formats. For example, we can do it either in JSON or XML using:

  - formatApiResponseAsJson(): Returns the ApiResponse as a JSON string
  - formatApiResponseAsXml(): Returns the ApiResponse as an XML string

# DBLP 

DBLP can be found in the following link: <https://dblp.uni-trier.de/>

DBLP API allows us to query its database through HTTP requests like is showed in this link in the table below: <https://dblp.uni-trier.de/faq/How+to+use+the+dblp+search+API>. 

As they show us to query an author, publication or venue the API is the following: 
  - <http://dblp.org/search/publ/api?q=some_publication>
  - <http://dblp.org/search/author/api?q=some_author>
  - <http://dblp.org/search/venue/api?q=some_venue>

We can combine these search queries shown above with the attributes of the table shown: <https://dblp.uni-trier.de/faq/How+to+use+the+dblp+search+API> using "&" either for format, number of hits, etc...

In the following link is also showed how the results are proccessed from the database: <https://dblp.org/faq/How+can+I+fetch+all+publications+of+one+specific+author>. The results can be proccessed in several ways, including xml, rss and many others. In this same link they showed us to ways of using the API. We used the 2nd one. Although it wasn't the one that they reccomend it was the most useful for us.

I'll exemplify how you query all the publications for Professor Miguel Pardal (the mastermind behind the BibTrek project) and display them in the "json" format below (the one used for this project). We do all this combining the information from the links above.
  - Searching for authors named "Miguel Pardal" and displaying them in the json format: <https://dblp.org/search/author/api?q=miguel+pardal&format=json>. This format queries for all authors named "Miguel Pardal" using: "/search/author/api?q=miguel_pardal"; and displays the information in json using: "&format=json".
  - Searching for all of "Miguel Pardal" publications in the json format: <https://dblp.org/search/publ/api?q=author=miguel+pardal&format=json>. This format queries for all publications of the author "Miguel Pardal" using: "search/publ/api?q=author=miguel_pardal" and displays it in the json format using "&format=json". You can also, like I said before combine this queries with more attributes displayed in the table of the first link for the DBLP topic.
  - You can also search for an author's publications using the following query as well: <https://dblp.org/search/publ/api?q=miguel+pardal&format=json>
Let's do a final query where we search for all publications about the "Rowhammer" attack. You just query using an HTTP client module the following: <https://dblp.uni-trier.de/search/publ/api?q=rowhammer&format=json>; This queries the system for a publication about the rowhammer attack: "search/publ/api?q=rowhammer"; and receives the response on the json format: "&format=json".
  - <https://dblp.org/pers/xx/p/Pardal:Miguel_L=>

DBLP also allows us to crawl its website as it can be read here: <https://dblp.org/faq/Am+I+allowed+to+crawl+the+dblp+website>

Finally there are numerous python wrappers under the GPL license avaible throughout github. Scholrly would be the most popular example: <https://github.com/scholrly/dblp-python>

# Google Scholar 

Google Scholar: <https://scholar.google.pt/>

Unfortunately Google Scholar does not allow us to either crawl its website or use an API to query its database.
Surprisingly the use of a crawler on the Scholar website is against Google's Terms of Service.
There are some discussions about this topic on the Stack Overflow forums on which the links can be found below:
  
  - <https://code.google.com/archive/p/google-ajax-apis/issues/109>
  - <https://academia.stackexchange.com/questions/34970/how-to-get-permission-from-google-to-use-google-scholar-data-if-needed>
  - <https://academia.stackexchange.com/questions/2567/api-eula-and-scraping-for-google-scholar>

Google's terms of service and privacy policy can be found below as well. These apply to all of their services, Google Scholar being one of them.

  - <https://policies.google.com/terms?hl=en>
  - <https://policies.google.com/privacy?hl=en>

The terms of service read :
  
    Don’t misuse our Services. For example, don’t interfere with our Services or try to access them using a method other than the interface and the instructions that we provide. You may use our Services only as permitted by law, including applicable export and re-export control laws and regulations. We may suspend or stop providing our Services to you if you do not comply with our terms or policies or if we are investigating suspected misconduct.

Since no API is provided, we assume we cannot use any other service to query their services other than their interface.
Finally Google Scholar's "robots.txt" which can be found here: <https://scholar.google.ca/robots.txt>; disallows bots. Which means we cannot crawl their website.

# ArXiv

Arxiv can be found in the following link: <https://arxiv.org/>

ArXiv allows the developers to use an API. The API calls are made using HTTP with GET and POST requests.
ArXiv responses use the Atom 1.0 format. This format is an easily readable format extended from XML.
In the website we are pointed to several languages that easily call the API and parse the Atom 1.0 results. These languages are: Perl, Python, Ruby and PHP. ArXiv also includes examples using the API for each one of this languages in the links below:

  - Perl: <https://arxiv.org/help/api#perl_simple_example>
  - Python: <https://arxiv.org/help/api#python_simple_example>
  - Ruby: <https://arxiv.org/help/api#ruby_simple_example>
  - PHP: <https://arxiv.org/help/api#php_simple_example>

It is also worth mentioning that ArXiv terms of service can be found in the following link: <https://arxiv.org/help/api/tou>; and information about bulk data access can be found here: <https://arxiv.org/help/bulk_data>

We are also allowed the use of crawlers to some extent. The "robots.txt" file can be found here: <https://arxiv.org/robots.txt>.
