# Guidebook-Code-Challenge
An android application that displays information received over the network.

## Problem
The challenge was to retrieve data from a given URL, parse the received JSON object, display the parsed objects in a recycler view, and display the image from an object's icon URL. 

## Solution
During my interview with Ian Whelan, we talked about libraries I could learn to use on Android. He mentioned `OkHttp` as a good library to get familiar with; so, I decided to use that to make the HTTP request. I then parse the JSON using a Key class into a list of objects, `GuideDataModel` which was used to hold the data. Once the JSON is parsed, a recycler view adapter is notified of the update and creates the necessary view holders. In the adapter, the guide's icon is loaded into the view using `Picasso`.

An alternative library I could have used for the HTTP request is `Retrofit` which uses `OkHttp` by default. 

`Picasso` was used for loading the image because of its simplicity and its ability to work with placeholder images.

## Improvements
The application handles some fallbacks such as not having an internet connection and not loading the guide objects or the venue object (which contains the state and city). Further improvements could be included to handle if the other pieces of data were missing. The design of layout was kept simple for the sake of the challenge, but there's room for improvement (possibly switching to a card/grid view.)
