      (function(){
          var app = angular.module("pieChart", ['nvd3']);

          app.controller("pieChartController", function($scope, $http){
    
              $scope.options = {
                  chart: {
                      type: 'pieChart',
                      height: 500,
                      x: function(d){return d.key;},
                      y: function(d){return d.y;},
                      showLabels: true,
                      duration: 500,
                      labelThreshold: 0.01,
                      labelSunbeamLayout: true,
                      legend: {
                          margin: {
                              top: 5,
                              right: 35,
                              bottom: 5,
                              left: 0
                          }
                      }
                  }
              };

              $scope.refreshPieChart = function(query, rows, startDate, endDate) {

                var pieParam = "countries";

                $http({
                    method: "GET",
                    url: SOLR_URL + '/query?q=' + query + '+AND+dates%3A%5B' + startDate + '+TO+' + endDate + '%5D&rows=0&facet=true&facet.field=' + pieParam + '&facet.limit=' + rows + '&wt=json'
                })
                .then(function(response) {

                  console.log(response.data);
                  var docs = response.data.facet_counts.facet_fields[pieParam];
                  var others = "Others";
                  var resultingData = [];
                  
                  for(var i = 0; i < docs.length; i+=2) {
                    var jsonObject = {};
                    if(docs[i] == "")
                      jsonObject["key"] = others;  
                    else
                      jsonObject["key"] = docs[i];
                    jsonObject["y"] = docs[i + 1];
                    resultingData.push(jsonObject);
                  }

                  console.log(resultingData);
                  //console.log(JSON.stringify(resultingData));
                  $scope.data = resultingData;
                });
              };
          });
      })();
