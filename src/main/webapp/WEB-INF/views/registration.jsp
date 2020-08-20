<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
     <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrace nového klienta</title>
    
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script> 
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-route.js"></script>
 
      <script src="<c:url value='/resources/js/noclientAng.js'/>" charset="utf-8"></script>
       
      <link href="<c:url value='/resources/css/my-bootstrap.css' />"  rel="stylesheet"></link> 
       
      <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
      <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      
      <script src="<c:url value='/resources/js/notify.js' />"></script>
</head>

<body ng-app="noclientApp">
    <div ng-view></div>
</body>
</html>