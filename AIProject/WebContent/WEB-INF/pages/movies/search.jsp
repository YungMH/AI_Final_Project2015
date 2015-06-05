<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<h1>test hihi</h1>

<table border="1" >
      <tbody align="center">
			<tr>
			    <th>Movie id</th>
			    <th>Title</th>
			    <th>Genres</th>
			   	<th>User Id</th>
			    <th>Rating</th>			
			</tr>
            <s:iterator value="moviesContext.moviesRating" var="item" status="status">
                <tr>
                     <td><s:property value="#item.movieId" /></td>
                     <td><s:property value="#item.title" /></td>
                     <td><s:property value="#item.genres" /></td>
                     <td><s:property value="#item.userId" /></td>
                     <td><s:property value="#item.rating" /></td>
                </tr>        
            </s:iterator>
	  </tbody>
</table>
