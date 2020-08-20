<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Administrace IB</title>
    
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script> 
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-route.js"></script>
 
      <script src="<c:url value='/resources/js/adminAng.js'/>" charset="utf-8"></script>
       
       <script src="<c:url value='/resources/js/smart-table.min.js' />"></script>
       
      <link href="<c:url value='/resources/css/my-bootstrap.css' />"  rel="stylesheet"></link> 
       
      <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
      <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      
      <script src="<c:url value='/resources/js/notify.js' />"></script>
          
</head>

<body ng-app="adminApp">
 
 <nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#/">IB</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#/">Hlavní strana</a></li>
        <li><a href="#country">Země</a></li>
        <li><a href="#accounttype">Typy účtu</a></li>
        <li><a href="#currency">Měny</a></li>
        <li><a href="#bank">Banky</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="<c:url value="/logout" />"><span class="glyphicon glyphicon-log-in"></span> Odhlášení</a></li>
      </ul>
    </div>
  </div>
</nav>
  
<div class="container-fluid text-center">
  <div class="row content">
    <div class="col-sm-2 sidenav">
    </div>
    <div class="col-sm-8 text-left" >
    
    <div ng-view></div>
    
    </div>
    <div class="col-sm-2 sidenav">
    </div>
  </div>
</div>

<footer class="container-fluid text-center">
  <p>Internet Banking demo - Ladislav Štěrba 2016-2020</p>
</footer>
 
  
</body>
</html>