<%@ page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>

<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payments.js"></script>
<link rel="stylesheet" href="Views/bootstrap.min.css">

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
			
				<h1>Payment Management</h1>
				
				<form id="formPayment" name="formPayment">
 						Payment code:
 						<input id="PaymentCode" name="PaymentCode" type="text" class="form-control form-control-sm">
 						<br> 
 						
 						Payment Type:
 						<input id="PaymentType" name="PaymentType" type="text" class="form-control form-control-sm">
 						<br> 
 						
 						Total Price:
 						<input id="TotalPrice" name="TotalPrice" type="text" class="form-control form-control-sm">
 						<br> 
 						
 						Payment Status:
 						<input id="PaymentStatus" name="PaymentStatus" type="text" class="form-control form-control-sm">
 						<br>
 						
 						Date:
 						<input id="Date" name="Date" type="text" class="form-control form-control-sm">
 						<br> 
 						
 						Time:
 						<input id="Time" name="Time" type="text" class="form-control form-control-sm">
 						<br>
 						
 						<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
 						<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
				</form>

				<br>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divPaymentsGrid">
					<%
							Payment paymentObjRead = new Payment();
							out.print(paymentObjRead.readPayment());
					%>
				</div>

			</div>
		</div>
	</div>
</body>
</html>