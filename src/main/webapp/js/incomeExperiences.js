/*
    CSS for Enterprise Java project: Income Experiences
    Heidi Aandahl
    April 2019

    // todo - figure out how best to get data into chart
    // possible reference (utilizing gson and json: https://dzone.com/articles/integrating-chart-js-library
    // another idea, just stick data in the jsp
 */
const init = () => {
    /* ****************** none of this worked b/c info didn't make it from span to JSOn
    let needsDataSpan = document.getElementById("needsData");
    let needsData = needsDataSpan.getAttribute("data-needsMap");
    console.log("needsData");
    console.log(needsData);
    console.table(needsData); // just prints one curly brace!
    //let needsJson = JSON.parse(needsData);

    //SyntaxError: JSON.parse: end of data while reading object contents at line 1 column 2 of the JSON data[Learn More]
     */
    // let needsMapJson = JSON.parse("{\"1\":{\"Severely unmet needs caused permanent harm.\":0},\"2\":{\"Unmet needs caused illness or decreased ability at work or school.\":1},\"3\":{\"Unmet needs caused discomfort.\":0},\"4\":{\"Needs were generally met.\":0},\"5\":{\"All needs were comfortably met.\":0}}");
   // todo try this and see if getValue and getName work ... if so then need to re-do data chunk in servlet
    //let needsMapJson = JSON.parse("[{\"Severely unmet needs caused permanent harm.\":0}, {\"Unmet needs caused illness or decreased ability at work or school.\":1}, {\"Unmet needs caused discomfort.\":0}, {\"Needs were generally met.\":0}, {\"All needs were comfortably met.\":0}]");
    // todo try totally alternate:
    // TODO note this JSON seems to be parsing
    let needsMapJson = JSON.parse("[{\"description\":\"Severely unmet needs\", \"count\":0}, {\"description\":\"Severely unmet needs\", \"count\":0}]");
    console.table(needsMapJson);
    // todo let's see if i can work w/json string direclty.. if so i can then find a way to access it from the servlet, probably json


    // project2 sample data:
    /*
    {"weatherObservation":{"elevation":283,"lng":-89.53333333333333,"observation":"KC29 171255Z AUTO 10008KT 7SM SCT055 BKN110 10/07 A2991 RMK AO1 T00950069","ICAO":"KC29","clouds":"scattered clouds","dewPoint":"6.9","cloudsCode":"SCT","datetime":"2019-04-17 12:55:00","countryCode":"US","temperature":"9.5","humidity":83,"stationName":"MIDDLETON","weatherCondition":"n/a","windDirection":100,"windSpeed":"08","lat":43.11666666666667}}
     */
    //project2 sample code:
    /*
    let celciusTemp = data.weatherObservation.temperature;
     */
    // so i DID use dot notation

    /*
    MORE sample code from proj 2:
    let data = JSON.parse(xhr.responseText);

            // gather location info from web service
            let latitude = data.postalCodes[0].lat;
            let longitude = data.postalCodes[0].lng;
            let city = data.postalCodes[0].placeName;
     */

    makeNeedsChart(needsMapJson);

}

const makeNeedsChart = needsMapJson => {

    //TypeError: needsMapJson.getName is not a function[Learn More]


    // let label1 = needsMapJson.getName(0);
    //let label1 = needsMapJson[0].getProperty("description");

    // TypeError: needsMapJson.getValue is not a function[Learn More] incomeExperiences.js:34:18
    //     makeNeedsChart http://localhost:8080/incomeexperiences/js/incomeExperiences.js:34
    //     init http://localhost:8080/incomeexperiences/js/incomeExperiences.js:25

    let label1 = needsMapJson[0].description;

    //let label2 = needsMapJson.getName(1);
    //let label3 = needsMapJson.getName(2);
    //let label4 = needsMapJson.getName(3);
    //let label5 = needsMapJson.getName(4);
    /*
        let label1 = "first";
    */
        let label2 = "second";
        let label3 = "third";
        let label4 = "fourth";
        let label5 = "fifth";


    // let data1 = needsMapJson[0].getProperty("count");

    let data1 = needsMapJson[0].count;
    // put labels in as vars

    // put data in as vars

    var ctx = document.getElementById('needsChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [label1, label2, label3, label4, label5, 'Orange'],
            datasets: [{
                label: '# of Votes',
                data: [data1, 19, 3, 5, 2, 3],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    console.log("myChart...");
    console.table(myChart);
}



window.onload = init;