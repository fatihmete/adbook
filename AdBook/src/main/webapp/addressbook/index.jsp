<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"
%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base>
	<jsp:body>
    	<div class="container is-fluid mt-6 mb-6">
    		<div class="columns is-centered is-mobile">
      			<div class="column">
        			<div class="box has-text-left">
        			  <button class="button is-primary js-modal-trigger" onClick="openCreateEntryModal()"><i class="fas fa-add"></i>&nbsp;New Entry</button>
        			    <button class="button is-primary" onClick="updateTable()"><i class="fas fa-refresh"></i>&nbsp;Refresh</button>
        			    </div>
        			    <div class="box has-text-left">
	        			    <div class="table-container" id="addressBookTable">
	    					<table class="table" class="display" style="width:100%" >
		    					<thead>
						            <tr>
						                <th>ID</th>
						                <th>Name</th>
						                <th>Surname</th>
						                <th>Phone</th>
						                <th>Age</th>
						                <th>Address</th>
						                <th>Created At</th>
						           		<th>#</th>
						                <th>#</th>
						           
						            </tr>
							    </thead>
						        <tbody id="addressBookTableBody">
						        	
						        </tbody>
		    				</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- New Entry and Edit Modal -->
		<div class="modal" id="entryModal">
		  <div class="modal-background"></div>
		  <div class="modal-card">
		    <header class="modal-card-head">
		      <p class="modal-card-title" id="entryModalTitle">New Entry</p>
		      <button class="delete" aria-label="close"></button>
		    </header>
		    
		    	<section class="modal-card-body">
		    	<form id="addressEntryForm"> 
				      	<div class="field">
						  <label class="label">Name</label>
						  <div class="control">
						    <input class="input" type="text" name="name" placeholder="Name">
						  </div>
						</div>
						<div class="field">
						  <label class="label">Surname</label>
						  <div class="control">
						    <input class="input" type="text" name="surname" placeholder="Surname">
						  </div>
						</div>
						<div class="field">
						  <label class="label">Phone</label>
						  <div class="control">
						    <input class="input" type="text" name="phone" placeholder="Phone">
						  </div>
						</div>
						<div class="field">
						  <label class="label">Age</label>
						  <div class="control">
						    <input class="input" type="text" name="age" placeholder="Age">
						  </div>
						</div>
						<div class="field">
						  <label class="label">Address</label>
						  <div class="control">
						    <textarea class="textarea" name="address" placeholder="Address"></textarea>
						  </div>
						</div>
						<input type="hidden" name="updateEntryId"/>
						</form>
				    </section>
				    
				    <footer class="modal-card-foot">
				      <button class="button is-success" type="submit" id="entryModalCreateButton" onClick="createEntry()">Save</button>
				      <button class="button is-success" type="submit" id="entryModalUpdateButton" onClick="updateEntry()">Update</button>
				      <button class="button" type="button" name="closeModalButton">Cancel</button>
				    </footer>
		   		
		   		 </div>
		    
		  </div>
		</div>
		<!-- Delete Modal -->
		<div class="modal" id="deleteModal">
		  <div class="modal-background"></div>
		  <div class="modal-card">
		    <header class="modal-card-head">
		      <p class="modal-card-title">Delete entry</p>
		      <button class="delete" aria-label="close"></button>
		    </header>
		    <section class="modal-card-body">
		    	<input type="hidden" name="entryId"/>
		      This address book entry will be deleted. Are you sure?
		    </section>
		    <footer class="modal-card-foot">
		      <button class="button is-danger" onClick="deleteEntry()" name="deleteButton">Delete</button>
		      <button class="button" name="closeModalButton">Cancel</button>
		    </footer>
		  </div>
		  
		</div>
		<script>
		var addressBookData;
		$(document).ready(function() {
			updateTable();
		});
		
		$('button[name="closeModalButton"]').click(function(){
			closeModal();
		});

		function closeModal(){
			$(".modal").removeClass("is-active");
		}
		
		function updateTable(){
			$("#addressBookTable").hide();
			$("#addressBookTableBody").empty();
			$.ajax({

			    url : "${path}" + "/address-book/all",
			    type : 'GET',

			    dataType:'json',
			    success : function(data) {    
			    	window.addressBookData = data;
			    	if (data!==null){
				    	data.forEach(function(item){
				    		$("#addressBookTableBody").append(createRow(item));
				    	});
				    	$("#addressBookTable").show();
			    	}
			    		
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
		
		function openUpdateEntryModal(id){
			var entry = window.addressBookData.filter(function(a){ return a.id === id});
			if(entry.length>0) {
				resetAddressEntryFormValidation();
				$('input[name="name"]').val(entry[0].name)
		        $('input[name="surname"]').val(entry[0].surname)
		        $('input[name="phone"]').val(entry[0].phone)
		        $('input[name="age"]').val(entry[0].age)
		        $('textarea[name="address"]').val(entry[0].address)
		        $('input[name="updateEntryId"]').val(id)
		        
		        $("#entryModalTitle").text("Update Entry")
		        $("#entryModalCreateButton").hide();
				$("#entryModalUpdateButton").show();
		        $("#entryModal").addClass("is-active");

			}
			
		}
		function addressEntryValidationRules(){
			return {
				name : {
					required : true,
					minlength : 3
				},
				surname : {
					required : true,
					minlength: 3,
				},
				phone : {
					required : true,
					minlength : 3
				},
				age : {
					required: true,
					number : true
				},
				address : {
					required : true,
					minlength : 10,
				}
				
			}
		}
		function validateAddressEntryForm(){
			var validator = $("#addressEntryForm").validate({
				
				rules : addressEntryValidationRules(),
				errorClass : "is-danger",
				validClass : "",
				submitHandler: function(form){
					return false;
				},
					
			});
			if (validator.element('input[name="name"]')===true && 
				validator.element('input[name="surname"]')===true && 
				validator.element('input[name="phone"]')===true && 
				validator.element('input[name="age"]')===true && 
				validator.element('textarea[name="address"]')===true ){
				
				return true
				
			}else{
				return false
			}
			
		}
		
		function resetAddressEntryFormValidation(){
			var validator = $("#addressEntryForm").validate()
			validator.resetForm();
			validator.destroy();
			
			$('input[name="name"]').removeClass("is-danger");
	        $('input[name="surname"]').removeClass("is-danger");
	        $('input[name="phone"]').removeClass("is-danger");
	        $('input[name="age"]').removeClass("is-danger");
	        $('textarea[name="address"]').removeClass("is-danger");
	        $('input[name="updateEntryId"]').removeClass("is-danger");
		}
		function updateEntry() {
			
			if(validateAddressEntryForm()===true){

					$.ajax({
						
					    url : "${path}" +"/address-book/update",
					    type : 'POST',
					    data : {
					        'name' : $('input[name="name"]').val(),
					        'surname' : $('input[name="surname"]').val(),
					        'phone' : $('input[name="phone"]').val(),
					        'age' : $('input[name="age"]').val(),
					        'address' : $('textarea[name="address"]').val(),
					        'updateEntryId' : $('input[name="updateEntryId"]').val(),
					    },
					    dataType:'json',
					    success : function(data) {              
					    	Swal.fire({
					    		  title: 'Success',
					    		  text: 'Entry successfully updated.',
					    		  icon: 'success'
					    		});
					    	closeModal();
					    	updateTable();
					    	resetForm();
					    		
					    },
					    error : function(request,error) {
					    	Swal.fire({
					    		  title: 'Error!',
					    		  text: 'Entry can\'t update.',
					    		  icon: 'error'
					    		});
					    }
					});
					
			}
			
				
		}
		
		function openDeleteEntryModal(id){
			$("#deleteModal").addClass("is-active");
			$('input[name="entryId"]').val(id);
		}
		
		function deleteEntry(){
			$.ajax({
				url : "${path}" + "/address-book/delete",
				type : "POST",
				data : { "entryId" : $('input[name="entryId"]').val()},
				dataType:'json',
			    success : function(data) {              
			    	Swal.fire({
			    		  title: 'Success',
			    		  text: 'Enty successfully deleted.',
			    		  icon: 'success'
			    		});
			    	closeModal();
			    	updateTable();
			    		
			    },
			    error : function(request,error)
			    {
			    	Swal.fire({
			    		  title: 'Error!',
			    		  text: 'Please check fields.',
			    		  icon: 'error'
			    		})
			    }
				
			});
		}
		function openCreateEntryModal()
		{
			resetAddressEntryFormValidation();
			$("#entryModalTitle").text("New Entry");
			$("#entryModalCreateButton").show();
			$("#entryModalUpdateButton").hide();

			resetForm();
			$("#entryModal").addClass("is-active");
		}
		function createEntry(){
			if(validateAddressEntryForm()===true){
					$.ajax({
		
					    url : "${path}" +"/address-book/create",
					    type : 'POST',
					    data : {
					        'name' : $('input[name="name"]').val(),
					        'surname' : $('input[name="surname"]').val(),
					        'phone' : $('input[name="phone"]').val(),
					        'age' : $('input[name="age"]').val(),
					        'address' : $('textarea[name="address"]').val(),
		
					    },
					    dataType:'json',
					    success : function(data) {              
					    	Swal.fire({
					    		  title: 'Success',
					    		  text: 'New address enty successfully created.',
					    		  icon: 'success'
					    		});
					    	closeModal();
					    	updateTable();
					    	resetForm();
					    		
					    },
					    error : function(request,error)
					    {
					    	Swal.fire({
					    		  title: 'Error!',
					    		  text: 'Please check fields.',
					    		  icon: 'error'
					    		})
					    }
					});
				}
			
		}
		
		function createRow(row){
			is_disabled = (row.user_id == ${user.id}) ? "" : "disabled"
			return "<tr>"+
		        "<td>"+ row.id +"</td>"+
		        "<td>"+ row.name +"</td>"+
		        "<td>"+ row.surname +"</td>"+
		        "<td>"+ row.phone +"</td>"+
		        "<td>"+ row.age +"</td>"+
		        "<td>"+ row.address +"</td>"+
		        "<td>"+ row.created_at +"</td>"+
		        "<td><button class=\"button is-danger is-small\" onClick=\"openDeleteEntryModal(" + row.id + ")\" "+ is_disabled + "><span class=\"icon is-small\"><i class=\"fas fa-times\"></i></span></button></td>"+
		        "<td><button class=\"button is-warning is-small\" onClick=\"openUpdateEntryModal(" + row.id + ")\" "+ is_disabled + "><span class=\"icon is-small\"><i class=\"fas fa-edit\"></i></span></button></td>"
		}
		
		function resetForm(){
			$('input[name="name"]').val("")
	        $('input[name="surname"]').val("")
	        $('input[name="phone"]').val("")
	        $('input[name="age"]').val("")
	        $('input[name="entryId"]').val("")
	        $('input[name="updateEntryId"]').val("")
	        $('textarea[name="address"]').val("")
				
		}	
		</script>
    </jsp:body>
</t:base>