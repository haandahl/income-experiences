/*
    CSS for Enterprise Java project: Income Experiences
    Heidi Aandahl
    April 2019
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
            // ^^^ wait, is the DATA coming up null now?

            // todo activate
            // makeGoalsChart(data);
            // makeIncomeSkewChart(data);
        }
    }

    xhr.send(null);
}

const makeNeedsChart = needsMapJson => {

    // todo - figure out how to preserve the labels on teh graphs.  I made them responsive but that cuts off the labels!


    let label1 = formatLabel(needsMapJson["id1"].description, 20);
    let label2 = formatLabel(needsMapJson["id2"].description, 20);
    let label3 = formatLabel(needsMapJson["id3"].description, 20);
    let label4 = formatLabel(needsMapJson["id4"].description, 20);
    let label5 = formatLabel(needsMapJson["id5"].description, 20);

    let data1 = needsMapJson["id1"].count;
    let data2 = needsMapJson["id2"].count;
    let data3 = needsMapJson["id3"].count;
    let data4 = needsMapJson["id4"].count;
    let data5 = needsMapJson["id5"].count;

    var ctx = document.getElementById('needsChart').getContext('2d');

    // controlling canvas size todo - figure out if this actually IS now limiting canvas size, overriding css?
    // resource: https://stackoverflow.com/questions/19847582/chart-js-canvas-resize  jcmiller11
    // that did not work; todo - try to shrink charts
    ctx.canvas.width = 400;
    //ctx.canvas.height = 900;

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
            responsive: true,
            maintainAspectRatio: false,
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

/*
    Source: https://stackoverflow.com/questions/21409717/chart-js-and-long-labels
    Response by Fermin Arellano
    Takes a string phrase and breaks it into separate phrases
    no bigger than 'maxwidth', breaks are made at complete words.
*/
function formatLabel(str, maxwidth){
    var sections = [];
    var words = str.split(" ");
    var temp = "";

    words.forEach(function(item, index){
        if(temp.length > 0)
        {
            var concat = temp + ' ' + item;

            if(concat.length > maxwidth){
                sections.push(temp);
                temp = "";
            }
            else{
                if(index == (words.length-1))
                {
                    sections.push(concat);
                    return;
                }
                else{
                    temp = concat;
                    return;
                }
            }
        }

        if(index == (words.length-1))
        {
            sections.push(item);
            return;
        }

        if(item.length < maxwidth) {
            temp = item;
        }
        else {
            sections.push(item);
        }

    });

    return sections;
}

window.onload = init;
