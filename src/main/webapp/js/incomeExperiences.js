/*
    CSS for Enterprise Java project: Income Experiences
    Heidi Aandahl
    April 2019

    // todo - try to figure out how to get json from java to here
        //(stashing in a data-whatever attribute of an empty span didn't work -getElementById only picked up the curly brace
    // possible reference (utilizing gson and json: https://dzone.com/articles/integrating-chart-js-library
  */
const init = () => {
    // todo refer to this sample of working JSON (see also bottom of file for js project 2 work w/jsoan, for ref
    // let needsMapJson = JSON.parse("[{\"description\":\"Severely unmet needs\", \"count\":0}, {\"description\":\"Severely unmet needs\", \"count\":0}]");

    // trouble accessing the id...
    //let needsMapJson = JSON.parse("{\"1\":{\"count\":0,\"description\":\"Severely unmet needs caused permanent harm.\"},\"2\":{\"count\":1,\"description\":\"Unmet needs caused illness or decreased ability at work or school.\"},\"3\":{\"count\":0,\"description\":\"Unmet needs caused discomfort.\"},\"4\":{\"count\":0,\"description\":\"Needs were generally met.\"},\"5\":{\"count\":0,\"description\":\"All needs were comfortably met.\"}}");

    // todo how about this one
    let needsMapJson = JSON.parse("{\"id1\":{\"count\":0,\"description\":\"Severely unmet needs caused permanent harm.\"},\"id2\":{\"count\":1,\"description\":\"Unmet needs caused illness or decreased ability at work or school.\"},\"id3\":{\"count\":0,\"description\":\"Unmet needs caused discomfort.\"},\"id4\":{\"count\":0,\"description\":\"Needs were generally met.\"},\"id5\":{\"count\":0,\"description\":\"All needs were comfortably met.\"}}");
    console.table(needsMapJson);

    makeNeedsChart(needsMapJson);
}

const makeNeedsChart = needsMapJson => {
    // worked
    // let label1 = needsMapJson[0].description;

    //trial
    // 1 in quotes didn't work //SyntaxError: missing name after . operator[Learn More]
    //let label1 = needsMapJson.1.description;
    //SyntaxError: unexpected token: numeric literal[Learn More]

    let label1 = needsMapJson["id1"].description;


    // todo erase placeholders & finish all data needed
        let label2 = "second";
        let label3 = "third";
        let label4 = "fourth";
        let label5 = "fifth";

    // worked
    //    let data1 = needsMapJson[0].count;
    //let data1 = needsMapJson.1.count;

    let data1 = needsMapJson["id1"].count;
    //ReferenceError: label1 is not defined[Learn More] incomeExperiences.js:46:22
    //     makeNeedsChart http://localhost:8080/incomeexperiences/js/incomeExperiences.js:46
    //     init http://localhost:8080/incomeexperiences/js/incomeExperiences.js:18


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

// todo delete when done w/ref

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
