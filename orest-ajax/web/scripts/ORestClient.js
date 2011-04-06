// console logging
// if browser supports console then use it
// otherwise define a no-op function
if (!window.console) console = {};
console.log = console.log || function(){};


function RestClient(rootUri) {
   this.rootUri = rootUri;
}

RestClient.prototype.constructor = RestClient;

RestClient.prototype.getCollection = function(uri, displayFunction) {

   uri = stripUri(uri);

   function processResponse(responseText) {

      // get the response text (which should be JSON)
      var text = responseText;

      // parse it
      var collection = JSON.parse(text);

      // extract array from deserialised object
      var array = collection[uri][0]["id"];

      // since a lot of binding/serialisation frameworks drop the array if there is only one element we need to test for this...
      if(array instanceof Array) {
         // pass the resulting object to the display function
         displayFunction(array);

      } else {
         // Probably got back a single value so stick it in an array
         var tempArray = new Array();
         tempArray[0] = array;
         // pass the resulting object to the display function
         displayFunction(tempArray);
      }

   }

   this.sendRequest(uri, "GET", null, processResponse)

}

RestClient.prototype.get = function(uri, displayFunction) {

   uri = stripUri(uri);

   function processResponse(responseText) {
      // get the response text (which should be JSON)
      var text = responseText;

      // parse it
      var deserialised = JSON.parse(text);

      var root = "";

      // the deserialised object will contain a single root element that contains the actual object so extract it
      for (var p in deserialised) {
         root = p;
         break;
      }

      var resource = deserialised[root];

      // pass the resulting object to the display function
      displayFunction(resource);
   }

   this.sendRequest(uri, "GET", null, processResponse)

}

RestClient.prototype.remove = function(uri, responseProcessor) {
   uri = stripUri(uri);
   this.sendRequest(uri, "DELETE", null, responseProcessor);
}

RestClient.prototype.update = function(uri, resource, responseProcessor) {
   uri = stripUri(uri);

   // first add a root element to the object since orest expects roots
   resource = addRootElement(resource);
   
   // serialise resource to JSON
   var data = JSON.stringify(resource);

   this.sendRequest(uri, "PUT", data, responseProcessor);
}

RestClient.prototype.create = function(uri, resource, responseProcessor) {
   uri = stripUri(uri);

   // first add a root element to the object since orest expects roots
   resource = addRootElement(resource);
   
   // serialise resource to JSON
   var data = JSON.stringify(resource);

   this.sendRequest(uri, "POST", data, responseProcessor);
}

RestClient.prototype.sendRequest = function(uri, method, body, responseProcessor) {

   var fullUri = this.rootUri + uri;

   var httpRequest = new XMLHttpRequest();

   httpRequest.open(method, fullUri);

   // set content type to JSON
   httpRequest.setRequestHeader("content-type", "application/json");

   // define the function for handling the response
   httpRequest.onreadystatechange = function () {

      // if the server returns success codes
      if ( httpRequest.readyState == 4) {
         if(httpRequest.status == 200 ) {

            if(responseProcessor != null) {
               responseProcessor(httpRequest.responseText);
            }
            
         } else if(httpRequest.status != 200) {
            // server returned an error code so display the details
            document.write(httpRequest.responseText)
         }
      }
   };

   // suppress caching to ensure we get up-to-date results
   httpRequest.setRequestHeader("Cache-Control", "no-cache");

   // send the request
   httpRequest.send(body);
}

/**
 * Wraps creates a wrapper object with a single property that is the class name
 * of the wrapped object that contains the wrapped object.
 *
 * Basically when the wrapper is converted to JSON it will now product a root
 * element that contains the actual data.
 *
 * This is necessary since the orest framework expects root elementsso it knows
 * how to deserialise the data.
 */
function addRootElement(resource) {

   // cludgy way of getting contructor name of an object
   var root = /(\w+)\(/.exec(resource.constructor.toString())[1].toLowerCase();

   // create a wrapper object that contains root element
   var o = new Object();

   // add property using root as name and resource as value
   o[root] = resource;

   // return the wrapper
   return o;
}

function stripUri(uri) {
   var regex = /(.+)\/$/;

   var hasTrailingSlash = regex.test(uri);
   if(hasTrailingSlash) {
      uri = regex.exec(uri)[1];
   }

   return uri;
}