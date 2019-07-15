# Bibliography Application Programmer Interfaces

This is a list of existing and publicly accessible APIs for bibliography databases.

# ACM Digital Library

The ACM Digital Library can be found here: "https://dl.acm.org/"

Unfortunately ACM Digital Library does not offer any API to the developers.
Not only that but the use of crawlers is forbidden as it is written in the Terms of Service. You can find them in the following link: "https://libraries.acm.org/digital-library/policies"
The ToS reads:

    Under no circumstances are the following actions permitted:

    Sharing access to ACM DL materials with individuals or organizations outside the subscribing institution
    Using scripts or spiders to automatically download articles or harvest metadata from the ACM Digital Library. This activity is a serious violation of  ACM’s DL usage policy and will result in the temporary or permanent termination of download rights for the subscribing institution.

Due to this setback we will not be able to use the ACM Digital Library as a source of data to our BibTrek application.

# IEEE Xplore

IEEE Xplore can be found in the following link: "https://ieeexplore.ieee.org/Xplore/home.jsp"

Xplore offers a very rich API to its developers. Firstly it requires us to obtain a key for us to be able to use it. First you have to register yourself in the IEEE Xplore website in the following link: "https://developer.ieee.org/member/register". After registering you will be able to apply for a key. 

The documentation for the API is displayed here: "https://developer.ieee.org/docs".

Besides this features we are also offered a vast array of SDKs. These SDKs include the:
  - Java SDK: "https://developer.ieee.org/Java_Software_Development_Kit"
  - PHP SDK: "https://developer.ieee.org/PHP_Software_Development_Kit"
  - Python SDK: "https://developer.ieee.org/Python_Software_Development_Kit"

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
