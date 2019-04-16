<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap 4 info from w3schools -->
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <!-- Chart.js -->
    <!-- Resource: https://www.jsdelivr.com/package/npm/chart.js?path=dist -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.bundle.min.js"></script>

    <!-- Project specific -->
    <link rel="stylesheet" href="<c:url value='/css/style.css' />" type="text/css" />
    <script src="<c:url value='/js/incomeExperiences.js'/>" ></script>

   <title>
        Income Experiences
    </title>
    <%-- todo erase if unused...Resources for getting CSS to all templated pages:
        Slack w/Kat Kohler 12/2/18
        https://stackoverflow.com/questions/14548998/adding-external-resources-css-javascript-images-etc-in-jsp/14571407
    <link href="<c:url value='/css/style.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/css/styleProject.css' />" rel="stylesheet" type="text/css" /> --%>
</head>

