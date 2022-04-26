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
    						<h1 class="is-1">Register</h1>
    							<p>Please enter your informations to register.</p>
    							<form id="signupForm"> 
	    							<div class="field">
								  			<p class="control has-icons-left has-icons-right">
								    			<input class="input" type="text" placeholder="Name" name="name">
												    <span class="icon is-small is-left">
												      <i class="fas fa-user"></i>
												    </span>
												    
							  				</p>
										</div>
								        <div class="field">
								  			<p class="control has-icons-left has-icons-right">
								    			<input class="input" type="email" placeholder="Email" name="email">
												    <span class="icon is-small is-left">
												      <i class="fas fa-envelope"></i>
												    </span>
											
							  				</p>
										</div>
										<div class="field">
										  <p class="control has-icons-left">
										    <input class="input" type="password" placeholder="Password" name="password" >
										    <span class="icon is-small is-left">
										      <i class="fas fa-lock"></i>
										    </span>
										  </p>
										</div>
										<div class="field">
										  <p class="control">
										    <button class="button is-success" name="loginButton">
										      Sign up
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
			var signupForm = $("#signupForm")
			signupForm.validate({
				rules:{
					name : {
						required : true,
						minlength : 3
					},
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
						
					    url : "${path}" +"/signup",
					    type : 'POST',
					    data : {
					    	'name' : $('input[name="name"]').val(),
					        'email' : $('input[name="email"]').val(),
					        'password' : $('input[name="password"]').val()
					    },
					    dataType:'json',
					    success : function(data) {              
					    	Swal.fire({
					    		  title: 'Success',
					    		  text: 'You are succesfully register, redirecting.',
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
					    		  text: 'Please check your informations.',
					    		  icon: 'error'
					    		})
					    }
					});
				}
			});
			
		} );

		</script>
    </jsp:body>
</t:base>