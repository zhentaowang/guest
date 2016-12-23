define([
  'jquery'
], function($) {

  var initDynamic = function() {
      // Button send
      $(".conf-request-send").off("click");
      $(".conf-request-send").on("click", function(e) {
          e.preventDefault();
          var $root = $(this).parents("article");
          var group = $root.data("group");
          var name = $root.data("name");
          var version = $root.data("version");
          sendSampleRequest(group, name, version, $(this).data("conf-request-type"));
      });

      // Button clear
      $(".conf-request-clear").off("click");
      $(".conf-request-clear").on("click", function(e) {
          e.preventDefault();
          var $root = $(this).parents("article");
          var group = $root.data("group");
          var name = $root.data("name");
          var version = $root.data("version");
          clearSampleRequest(group, name, version);
      });
  }; // initDynamic

  function sendSampleRequest(group, name, version, type)
  {
      var $root = $('article[data-group="' + group + '"][data-name="' + name + '"][data-version="' + version + '"]');

      // Optional header
      var header = {};
      $root.find(".conf-request-header:checked").each(function(i, element) {
          var group = $(element).data("conf-request-header-group-id");
          $root.find("[data-conf-request-header-group=\"" + group + "\"]").each(function(i, element) {
            var key = $(element).data("conf-request-header-name");
            var value = element.value;
            if ( ! element.optional && element.defaultValue !== '') {
                value = element.defaultValue;
            }
            header[key] = value;
          });
      });

      // create JSON dictionary of parameters
      var param = {};
      var paramType = {};
      $root.find(".conf-request-param:checked").each(function(i, element) {
          var group = $(element).data("conf-request-param-group-id");
          $root.find("[data-conf-request-param-group=\"" + group + "\"]").each(function(i, element) {
            var key = $(element).data("conf-request-param-name");
            var value = element.value;
            if ( ! element.optional && element.defaultValue !== '') {
                value = element.defaultValue;
            }
            param[key] = value;
            paramType[key] = $(element).next().text();
          });
      });

      // grab user-inputted URL
      var url = $root.find(".conf-request-url").val();

      // Insert url parameter
      var pattern = pathToRegexp(url, null);
      var matches = pattern.exec(url);
      for (var i = 1; i < matches.length; i++) {
          var key = matches[i].substr(1);
          if (param[key] !== undefined) {
              url = url.replace(matches[i], encodeURIComponent(param[key]));

              // remove URL parameters from list
              delete param[key];
          }
      } // for

      $root.find(".conf-request-response").fadeTo(250, 1);
      $root.find(".conf-request-response-json").html("Loading...");
      refreshScrollSpy();

      _.each( param, function( val, key ) {
          var t = paramType[ key ].toLowerCase();
          if ( t === 'object' || t === 'array' ) {
              try {
                  param[ key ] = JSON.parse( val );
              } catch (e) {
              }
          }
      });

      // send AJAX request, catch success or error callback
      var ajaxRequest = {
          url        : url,
          headers    : header,
          data       : param,
          type       : type.toUpperCase(),
          success    : displaySuccess,
          error      : displayError
      };

      $.ajax(ajaxRequest);


      function displaySuccess(data, status, jqXHR) {
          var jsonResponse;
          try {
              jsonResponse = JSON.parse(jqXHR.responseText);
              jsonResponse = JSON.stringify(jsonResponse, null, 4);
          } catch (e) {
              jsonResponse = data;
          }
          $root.find(".conf-request-response-json").html(jsonResponse);
          refreshScrollSpy();
      };

      function displayError(jqXHR, textStatus, error) {
          var message = "Error " + jqXHR.status + ": " + error;
          var jsonResponse;
          try {
              jsonResponse = JSON.parse(jqXHR.responseText);
              jsonResponse = JSON.stringify(jsonResponse, null, 4);
          } catch (e) {
              jsonResponse = escape(jqXHR.responseText);
          }

          if (jsonResponse)
              message += "<br>" + jsonResponse;

          // flicker on previous error to make clear that there is a new response
          if($root.find(".conf-request-response").is(":visible"))
              $root.find(".conf-request-response").fadeTo(1, 0.1);

          $root.find(".conf-request-response").fadeTo(250, 1);
          $root.find(".conf-request-response-json").html(message);
          refreshScrollSpy();
      };
  }

  function clearSampleRequest(group, name, version)
  {
      var $root = $('article[data-group="' + group + '"][data-name="' + name + '"][data-version="' + version + '"]');

      // hide conf response
      $root.find(".conf-request-response-json").html("");
      $root.find(".conf-request-response").hide();

      // reset value of parameters
      $root.find(".conf-request-param").each(function(i, element) {
          element.value = "";
      });

      // restore default URL
      var $urlElement = $root.find(".conf-request-url");
      $urlElement.val($urlElement.prop("defaultValue"));

      refreshScrollSpy();
  }

  function refreshScrollSpy()
  {
      $('[data-spy="scroll"]').each(function () {
          $(this).scrollspy("refresh");
      });
  }

  function escapeHtml(str) {
      var div = document.createElement("div");
      div.appendChild(document.createTextNode(str));
      return div.innerHTML;
  }

  /**
   * Exports.
   */
  return {
      initDynamic: initDynamic
  };

});
