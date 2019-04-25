/*
    CSS for Enterprise Java project: Income Experiences
    Heidi Aandahl
    April 2019
    TODO - refactor, maybe make colors in more meaningful sequence (red scary, green good)
  */
const init = () => {
    putDataInCharts();
}

const putDataInCharts = () => {

    let xhr = new XMLHttpRequest();

    xhr.open("post", "chart-data");

    xhr.onreadystatechange = () => {
        if(xhr.readyState == 4) {
            let data = JSON.parse(xhr.responseText);
            console.table(data);

            makeNeedsChart(data);
            makeGoalsChart(data);
            makeIncomeSkewChart(data);
        }
    }

    xhr.send(null);
}

const makeNeedsChart = data => {

    let label1 = formatLabel(data["needs"].id1.description, 20);
    let label2 = formatLabel(data["needs"].id2.description, 20);
    let label3 = formatLabel(data["needs"].id3.description, 20);
    let label4 = formatLabel(data["needs"].id4.description, 20);
    let label5 = formatLabel(data["needs"].id5.description, 20);

    let data1 = data["needs"].id1.count;
    let data2 = data["needs"].id2.count;
    let data3 = data["needs"].id3.count;
    let data4 = data["needs"].id4.count;
    let data5 = data["needs"].id5.count;

    var ctx = document.getElementById('needsChart').getContext('2d');

    // resource for controlling canvas size: https://stackoverflow.com/questions/19847582/chart-js-canvas-resize  jcmiller11
    ctx.canvas.width = 250;

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

const makeGoalsChart = data => {

    let label1 = formatLabel(data["goals"].id1.description, 20);
    let label2 = formatLabel(data["goals"].id2.description, 20);
    let label3 = formatLabel(data["goals"].id3.description, 20);
    let label4 = formatLabel(data["goals"].id4.description, 20);
    let label5 = formatLabel(data["goals"].id5.description, 20);

    let data1 = data["goals"].id1.count;
    let data2 = data["goals"].id2.count;
    let data3 = data["goals"].id3.count;
    let data4 = data["goals"].id4.count;
    let data5 = data["goals"].id5.count;

    var ctx = document.getElementById('goalsChart').getContext('2d');

    // resource for controlling canvas size: https://stackoverflow.com/questions/19847582/chart-js-canvas-resize  jcmiller11
    ctx.canvas.width = 250;

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

const makeIncomeSkewChart = data => {

    let label1 = formatLabel(data["incomeSkew"].id1.description, 20);
    let label2 = formatLabel(data["incomeSkew"].id2.description, 20);
    let label3 = formatLabel(data["incomeSkew"].id3.description, 20);

    let data1 = data["incomeSkew"].id1.count;
    let data2 = data["incomeSkew"].id2.count;
    let data3 = data["incomeSkew"].id3.count;

    var ctx = document.getElementById('incomeSkewChart').getContext('2d');

    // resource for controlling canvas size: https://stackoverflow.com/questions/19847582/chart-js-canvas-resize  jcmiller11
    ctx.canvas.width = 250;

    var myChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: [label1, label2, label3],
            datasets: [{
                label: '# of Households',
                data: [data1, data2, data3],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)'
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
