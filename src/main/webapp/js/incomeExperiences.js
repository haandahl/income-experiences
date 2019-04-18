/*
    CSS for Enterprise Java project: Income Experiences
    Heidi Aandahl
    April 2019

    // todo - try to figure out how to get json from java to here
        //(stashing in a data-whatever attribute of an empty span didn't work -getElementById only picked up the curly brace
    // possible reference (utilizing gson and json: https://dzone.com/articles/integrating-chart-js-library
  */
const init = () => {
     // Sample of workable JSON:
     // let needsMapJson = JSON.parse("{\"id1\":{\"count\":0,\"description\":\"Severely unmet needs caused permanent harm.\"},\"id2\":{\"count\":1,\"description\":\"Unmet needs caused illness or decreased ability at work or school.\"},\"id3\":{\"count\":0,\"description\":\"Unmet needs caused discomfort.\"},\"id4\":{\"count\":0,\"description\":\"Needs were generally met.\"},\"id5\":{\"count\":0,\"description\":\"All needs were comfortably met.\"}}");

    putDataInCharts();

 }

const putDataInCharts = () => {

    let xhr = new XMLHttpRequest();

    // do I need true as 3rd param?  or is that old school?

    xhr.open("post", "chart-data");

    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let data = JSON.parse(xhr.responseText);
            console.table(data);

            // todo maybe separate data needed by each chart?

            // make the charts
            makeNeedsChart(data);

            // todo activate
            // makeGoalsChart(data);
            // makeIncomeSkewChart(data);
        }
    }

    xhr.send(null);
}

const makeNeedsChart = needsMapJson => {

    let label1 = needsMapJson["id1"].description;
    let label2 = needsMapJson["id2"].description;
    let label3 = needsMapJson["id3"].description;
    let label4 = needsMapJson["id4"].description;
    let label5 = needsMapJson["id5"].description;

    let data1 = needsMapJson["id1"].count;
    let data2 = needsMapJson["id2"].count;
    let data3 = needsMapJson["id3"].count;
    let data4 = needsMapJson["id4"].count;
    let data5 = needsMapJson["id5"].count;

    var ctx = document.getElementById('needsChart').getContext('2d');

    // controlling canvas size
    // resource: https://stackoverflow.com/questions/19847582/chart-js-canvas-resize  jcmiller11
    // that did not work; todo - try to shrink charts
    ctx.canvas.width = 300;
    ctx.canvas.height = 300;

    var myChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: [label1, label2, label3, label4, label5],
            datasets: [{
                label: '# of Households',
                data: [data1, data2, data3, data4, data5],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)'
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
    //removed from chart orange, rgba(255, 159, 64, 0.2), rgba(255, 159, 64, 1)

}

window.onload = init;
