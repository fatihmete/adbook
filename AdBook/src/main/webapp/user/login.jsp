<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"
%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base>
	<jsp:body>
    	<div class="container is-fluid mt-6 mb-6">
    		<div class="columns is-centered is-mobile">
      			<div class="column is-two-fifths-desktop is-full-mobile is-two-thirds-tablet">
        			<div class="box has-text-left">
    					<div class="content">
    						<h1 class="is-1">Login</h1>
    							<p>Please enter your credentials to login</p>
    							<form id="loginForm"> 
							        <div class="field">
							  			<p class="control has-icons-left has-icons-right">
							    			<input class="input" type="email" placeholder="Email" name="email" required>
											    <span class="icon is-small is-left">
											      <i class="fas fa-envelope"></i>
											    </span>
											
						  				</p>
									</div>
									<div class="field">
									  <p class="control has-icons-left">
									    <input class="input" type="password" placeholder="Password" name="password" required>
									    <span class="icon is-small is-left">
									      <i class="fas fa-lock"></i>
									    </span>
									  </p>
									</div>
									<div class="field">
									  <p class="control">
									    <button class="button is-success" name="loginButton">
									      Login
									  </p>
								</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		<script>
		$(document).ready(function() {
			
			var loginForm = $("#loginForm")
			loginForm.validate({
				rules:{
					email :{
						required : true,
						email : true,
					},
					password : {
						required : true,
						minlength : 6
					}
				},
				errorClass : "is-danger",
				submitHandler: function(form){
					$.ajax({
						
					    url : "${path}" + "/login",
					    type : 'POST',
					    data : {
					        'email' : $('input[name="email"]').val(),
					        'password' : $('input[name="password"]').val()
					    },
					    dataType:'json',
					    success : function(data) {              
					    	Swal.fire({
					    		  title: 'Success',
					    		  text: 'You are succesfully login, redirecting.',
					    		  icon: 'success'
					    		})
					    		setTimeout(
								  function() 
								  {
									  window.location.href = "${path}" + "/address-book";
								  }, 2000);
					    		
					    },
					    error : function(request,error)
					    {
					    	Swal.fire({
					    		  title: 'Error!',
					    		  text: 'Please check your email and password.',
					    		  icon: 'error'
					    		})
					    }
					});
					
				}
			});
			
		})
		
		</script>
    </jsp:body>
</t:base>