<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<!-- <link href="css/therapy.css" rel="stylesheet"> -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery.blockUI.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/riversoft-core-lib.js"/>"></script>
	<script src="js/bootstrap.min.js"></script>
  </head>
  <body>
    

    <div class="container-fluid center">
      <tiles:insertAttribute name="content" />
    </div>  
  
       
  </body>
</html>