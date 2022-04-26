
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base>
  
    <jsp:body>
        <div class="container is-fluid mt-6 mb-6">
    		<div class="columns is-centered is-mobile">
      			<div class="column is-two-fifths-desktop is-full-mobile is-two-thirds-tablet">
        			<div class="box has-text-left">
    					<div class="content">
    						<h1 class="is-1">AdBook App</h1>
    							<p>This app allow you store contact information.</p>
									<p>Already have an account? <a href="${pageContext.request.contextPath}/login" >Log In</a></p>
									<p>Don't have an account? <a href="${pageContext.request.contextPath}/signup" >Sign up</a> </p>
							</div>
						</div>
					</div>
				</div>
			</div>
    </jsp:body>
</t:base>